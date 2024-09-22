package ekm.RESTNode.server.services;

import ekm.tools.tools.web.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServiceAddResponse implements IService<AddResData,String> {
    @Autowired
    DBRestMessage dbRestMessage;

    @Override
    public ResponseEntity<String> handle(AddResData responseData) {
        dbRestMessage
                .get(responseData.serviceName, responseData.uuid)
                .setResponse(responseData.response);
        return ResponseEntity.ok("");
    }
}
