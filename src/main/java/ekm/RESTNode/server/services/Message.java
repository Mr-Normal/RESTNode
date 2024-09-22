package ekm.RESTNode.server.services;

import ekm.requests.JsonBody;

public class Message extends JsonBody {
    public final String uuid;
    public final String serviceName;
    public final Request request;
    public Response response;
    public final String path;
    public final String type;

    public Message(String uuid, String serviceName, Request request, String path, String type) {
        this.uuid = uuid;
        this.serviceName = serviceName;
        this.request = request;
        this.path = path;
        this.type = type;
    }

    public void setResponse(Response response){
        this.response = response;
    }
}
