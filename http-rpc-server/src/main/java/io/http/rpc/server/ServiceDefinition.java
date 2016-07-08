package io.http.rpc.server;

import java.lang.reflect.Method;

/**
 * Created by manbu on 7/3/16.
 */
public class ServiceDefinition {

    private String namespace;
    private String name;

    private Class<?> serviceClass;
    private Method method;

    public ServiceDefinition(String namespace, String name, Class<?> serviceClass, Method method) {
        this.namespace = namespace;
        this.name = name;
        this.serviceClass = serviceClass;

        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

}
