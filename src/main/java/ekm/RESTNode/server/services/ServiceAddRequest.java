package ekm.RESTNode.server.services;

import ekm.tools.tools.web.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceAddRequest implements IService<AddReqData, byte[]> {
    @Autowired
    DBRestMessage dbRestMessage;

    public ResponseEntity<byte[]> handle(AddReqData data){
        UUID requestID = dbRestMessage.add(data.serviceName, data.request, data.path, data.requestType);
        dbRestMessage.waitUntilResponse(data.serviceName, requestID.toString(), 120);
        Response response = dbRestMessage.remove(data.serviceName, requestID.toString()).response;
        return new ResponseEntity<byte[]>(response.body, response.headers,200);
    }
}
