package io.http.rpc.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manbu on 7/3/16.
 */
public class ServiceRegistrationCenter {

    private final static Log logger = LogFactory.getLog(ServiceRegistrationCenter.class);

    private Map<String, ServiceNamespaceDefinition> serviceNamespaceDefinitions = new HashMap<>();

    public void registerService(String namespace, String name, Class<?> clazz, Method method) {

        ServiceNamespaceDefinition namespaceDefinition = serviceNamespaceDefinitions.get(namespace);

        if(namespaceDefinition == null) {

            namespaceDefinition = new ServiceNamespaceDefinition();
            namespaceDefinition.setNamespace(namespace);
            namespaceDefinition.setServiceClass(clazz);

            namespaceDefinition.registerServiceMapping(new ServiceDefinition(namespace, name, clazz, method));

        } else {

            namespaceDefinition.registerServiceMapping(new ServiceDefinition(namespace, name, clazz, method));
        }

        serviceNamespaceDefinitions.put(namespace, namespaceDefinition);

        logger.info("register service namespace: [" + namespace + "], name: [" + name + "], on class: ["+clazz+"], method: ["+method+"]");

    }

    public ServiceDefinition getServiceDefinition(String namespace, String name) {

        ServiceNamespaceDefinition namespaceDefinition = serviceNamespaceDefinitions.get(namespace);

        if(namespaceDefinition == null) { return null; }

        return namespaceDefinition.getServiceDefinition(name);
    }

}
