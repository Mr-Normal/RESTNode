package ekm.RESTNode.client;

import ekm.requests.API;

public class NodeAPI extends API {
    private final String host;
    private final int port;

    public NodeAPI(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String getHostName() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

}
