package ekm.RESTNode.server.services;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DBRestMessage {
    private final Map<String, Map<String, Message>> services = new ConcurrentHashMap<>();

    public Message remove(String serviceName, String uuid) {
        return services.get(serviceName).remove(uuid);
    }

    public boolean isHaveUnprocessed(String serviceName) {
        if (!services.containsKey(serviceName)) {
            return false;
        } else {
            Map<String, Message> messageMap = services.get(serviceName);
            if (messageMap == null) {
                return false;
            } else {
                if (messageMap.size() < 1) {
                    return false;
                } else {
                    for (Map.Entry<String, Message> entry : services.get(serviceName).entrySet()) {
                        if (!entry.getValue().isProceed.get()) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
    }

    public Message getFirst(String serviceName) {
        if (isHaveUnprocessed(serviceName)) {
            Map<String, Message> messageMap = services.get(serviceName);
            for (Map.Entry<String, Message> entry : messageMap.entrySet()) {
                if (!entry.getValue().isProceed.get()) {
                    return entry.getValue();
                }
            }
        }
        throw new IllegalArgumentException("Не удалось найти записи для сервиса " + serviceName);
    }

    public Message get(String serviceName, String uuid) {
        return services.get(serviceName).get(uuid);
    }

    public UUID add(String serviceName, Request request, String path, String type) {
        UUID uuid = UUID.randomUUID();
        Message message = new Message(uuid.toString(), serviceName, request, path, type);
        if (!services.containsKey(serviceName)) {
            services.put(serviceName, new ConcurrentHashMap<>());
        }
        services.get(serviceName).put(uuid.toString(), message);
        return uuid;
    }

    @SneakyThrows
    public void waitUntilResponse(String serviceName, String uuid, float timeoutSec) {
        long expiredTime = (long) (timeoutSec * 1000) + currentTime();
        Message message = services.get(serviceName).get(uuid);
        while (message.response == null || expiredTime <= currentTime()) {
            Thread.sleep(100);
        }
        if (expiredTime <= currentTime()) {
            throw new IllegalStateException("Timeout after " + timeoutSec + " seconds");
        }
    }

    private long currentTime() {
        return new Date().getTime();
    }
}
