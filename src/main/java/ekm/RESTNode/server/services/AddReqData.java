package ekm.RESTNode.server.services;

public class AddReqData {
    public String serviceName;
    public Request request;
    public String path;
    public final String requestType;

    public AddReqData(String serviceName, Request request, String path, String requestType) {
        this.serviceName = serviceName;
        this.request = request;
        this.path = path;
        this.requestType = requestType;
    }
}
