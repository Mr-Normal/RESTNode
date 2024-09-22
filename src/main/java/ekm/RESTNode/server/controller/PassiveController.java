package ekm.RESTNode.server.controller;

import ekm.RESTNode.server.services.*;
import ekm.tools.tools.parsing.text.SimpleParse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PassiveController {
    @Autowired
    ServiceAddRequest serviceAddRequest;
    @Autowired
    ServiceGetRequest serviceGetRequest;
    @Autowired
    ServiceAddResponse serviceAddResponse;

    @GetMapping("/api/{serviceName}/get/request")
    public ResponseEntity<String> getServiceConsume(
            @PathVariable String serviceName
    ) {
        return serviceGetRequest.handle(serviceName);
    }

    @PostMapping("/api/{serviceName}/add/response/{uuid}")
    public ResponseEntity<String> getServiceConsume(
            @PathVariable String serviceName,
            @PathVariable String uuid,
            @RequestBody byte[] requestBody,
            @RequestHeader MultiValueMap<String, String> headers
    ) {
        AddResData addResData = new AddResData(
                serviceName,
                uuid,
                new Response(
                        requestBody,
                        headers
                )
        );
        return serviceAddResponse.handle(addResData);
    }

    @GetMapping("/api/{serviceName}/add/request/**")
    public ResponseEntity<byte[]> getAllRequests(
            @RequestBody byte[] requestBody,
            @RequestHeader Map<String, String> headers,
            HttpServletRequest httpServletRequest,
            @PathVariable String serviceName
    ) {
        return getResponseAll(requestBody, headers, httpServletRequest, "GET", serviceName);
    }

    @PostMapping("/api/{serviceName}/add/request/**")
    public ResponseEntity<byte[]> postAllRequests(
            @RequestBody byte[] requestBody,
            @RequestHeader Map<String, String> headers,
            HttpServletRequest httpServletRequest,
            @PathVariable String serviceName
    ) {
        return getResponseAll(requestBody, headers, httpServletRequest, "POST", serviceName);
    }

    private ResponseEntity<byte[]> getResponseAll(
            byte[] requestBody,
            Map<String, String> headers,
            HttpServletRequest httpServletRequest,
            String requestType,
            String serviceName
    ) {
        Request request = new Request(requestBody, headers);
        AddReqData data = new AddReqData(
                serviceName,
                request,
                SimpleParse.after("add/request/", httpServletRequest.getRequestURI()),
                requestType
        );
        return serviceAddRequest.handle(data);
    }

}
