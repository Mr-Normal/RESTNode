package ekm.RESTNode.client;

import ekm.RESTNode.config.ConfigNode;
import ekm.RESTNode.server.services.Message;
import ekm.requests.HttpRequest;
import ekm.tools.tools.parsing.json.GSON;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Configuration
public class Agent {
    private final NodeAPI passiveNodeAPI;
    private final ConfigNode configNode;
    private AtomicBoolean isProcessed = new AtomicBoolean(false);

    @Autowired
    public Agent(ConfigNode configNode) {
        this.configNode = configNode;
        passiveNodeAPI = new NodeAPI(configNode.getPassive().getHost(), configNode.getPassive().getPort());
    }

    @PostConstruct()
    void init() {

    }

    private Message getRequest(String serviceName) {
        String messageJSON = passiveNodeAPI.getRequest(getPassiveEndpointRequest(serviceName)).GET().getBody();
        return GSON.gson.fromJson(messageJSON, Message.class);
    }

    private String getPassiveEndpointRequest(String serviceName) {
        return setPathVars(serviceName, null, configNode.getPassive().getEndpoints().getGetRequest());
    }

    private ResponseEntity<byte[]> handleRequest(Message message) {
        ServiceData serviceData = getServiceData(message.serviceName);
        HttpRequest request = new HttpRequest(serviceData.getHost(), serviceData.getPort(), message.path);
        ResponseEntity<byte[]> responseEntity;
        return switch (message.type.toUpperCase(Locale.ROOT)) {
            case "GET" -> request.GET(byte[].class);
            case "POST" -> request.POST(message.request.body, byte[].class);
            default -> throw new IllegalArgumentException("Не найдено обработчика для типа запроса " + message.type);
        };
    }

    private ServiceData getServiceData(String serviceName) {
        for (ServiceData serData : configNode.getActive()) {
            if (serData.getName().equalsIgnoreCase(serviceName)) {
                return serData;
            }
        }
        throw new IllegalStateException("Не найдена информации в конфиге для сервиса " + serviceName);
    }

    private void sendResponse(ResponseEntity<byte[]> responseEntity, Message request) {
        passiveNodeAPI
                .getRequest(setPathVars(request.serviceName, request.uuid, configNode.getPassive().getEndpoints().getAddResponse()))
                .POST(responseEntity.getBody(), String.class);
    }

    private String setPathVars(String serviceName, String uuid, String path) {
        String res = path.replaceFirst("\\{serviceName}", serviceName);
        if (uuid != null) {
            res = res.replaceFirst("\\{uuid}", uuid);
        }
        return res;
    }

    @Scheduled(fixedDelay = 1000)
    public void handleNext() {
        if(!isProcessed.get()){
            if(!configNode.getIsServer()){
                for (ServiceData serviceData : configNode.getActive()) {
                    Message request = getRequest(serviceData.getName());
                    if(request!=null){
                        log.info("Processed: " + serviceData.getName());
                        isProcessed.set(true);
                        sendResponse(handleRequest(request), request);
                        isProcessed.set(false);
                    }
                }
            }
        }
    }

}
