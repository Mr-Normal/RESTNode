package ekm.RESTNode.server.services;

import ekm.requests.JsonBody;

import java.util.Map;

public class Request extends JsonBody {
    public final byte[] body;
    public final Map<String, String> headers;

    public Request(byte[] body, Map<String, String> headers) {
        this.body = body;
        this.headers = headers;
    }
}
