package ekm.RESTNode.server.services;

import ekm.tools.tools.web.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServiceGetRequest implements IService<String, String> {
    @Autowired
    DBRestMessage dbRestMessage;

    @Override
    public ResponseEntity<String> handle(String serviceName) {
        String resJSON = "";
        if(dbRestMessage.isHaveAny(serviceName)){
            resJSON = dbRestMessage.getFirst(serviceName).toJSON();
        }
        return ResponseEntity.ok(resJSON);
    }
}
