package ekm.RESTNode.config;

import ekm.RESTNode.client.ServiceData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "node")
public class ConfigNode {
    private List<ServiceData> active;
    private Passive passive;
    private boolean isServer;

    public boolean getIsServer(){
        return isServer;
    }

    public void setIsServer(boolean isServer){
        this.isServer = isServer;
    }

    // Геттеры и сеттеры
    public List<ServiceData> getActive() {
        return active;
    }

    public void setActive(List<ServiceData> active) {
        this.active = active;
    }

    public Passive getPassive() {
        return passive;
    }

    public void setPassive(Passive passive) {
        this.passive = passive;
    }
}