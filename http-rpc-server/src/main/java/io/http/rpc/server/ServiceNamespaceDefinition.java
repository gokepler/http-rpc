package io.http.rpc.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manbu on 7/3/16.
 */
public class ServiceNamespaceDefinition {

    private Class<?> serviceClass;

    private String namespace;
    private Map<String, ServiceDefinition> serviceDefinitionMap;

    public ServiceNamespaceDefinition() {
        serviceDefinitionMap = new HashMap<>();
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void registerServiceMapping(ServiceDefinition definition) {

        serviceDefinitionMap.put(definition.getName(), definition);
    }

    public ServiceDefinition getServiceDefinition(String name) {

        return serviceDefinitionMap.get(name);
    }

}
