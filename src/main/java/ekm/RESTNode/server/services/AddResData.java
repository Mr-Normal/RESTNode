package ekm.RESTNode.server.services;

public class AddResData {
    public String serviceName;
    public String uuid;
    public Response response;

    public AddResData(String serviceName, String uuid, Response response) {
        this.serviceName = serviceName;
        this.uuid = uuid;
        this.response = response;
    }
}
