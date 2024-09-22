package ekm.RESTNode.config;

public class Passive {
    private String host;
    private int port;
    private EndpointsPassive endpoints;

    // Геттеры и сеттеры
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public EndpointsPassive getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(EndpointsPassive endpoints) {
        this.endpoints = endpoints;
    }
}