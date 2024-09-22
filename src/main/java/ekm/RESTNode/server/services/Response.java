package ekm.RESTNode.server.services;

import ekm.requests.JsonBody;
import org.springframework.util.MultiValueMap;

public class Response extends JsonBody {
    public byte[] body;
    public MultiValueMap<String, String> headers;

    public Response(byte[] body, MultiValueMap<String, String> headers) {
        this.body = body;
        this.headers = headers;
    }
}
