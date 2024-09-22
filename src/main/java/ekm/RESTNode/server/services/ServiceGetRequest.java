package ekm.RESTNode.server.services;

import ekm.tools.tools.web.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceGetRequest implements IService<String, String> {
    @Autowired
    DBRestMessage dbRestMessage;

    @Override
    public ResponseEntity<String> handle(String serviceName) {
        String resJSON = "";
        if(dbRestMessage.isHaveUnprocessed(serviceName)){
            try {
                Message message = dbRestMessage.getFirst(serviceName);
                message.isProceed.set(true);
                resJSON = message.toJSON();
            }catch (Exception e){
                log.info(e.getMessage());
            }
        }
        return ResponseEntity.ok(resJSON);
    }
}
