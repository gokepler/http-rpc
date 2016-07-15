package io.http.rpc.core;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by manbu on 7/6/16.
 */
public final class RequestBean implements Serializable {

    private static final long serialVersionUID = -1894439850764453993L;
    private String namespace;
    private String name;

    private Object[] parameters;

    public RequestBean() {}

    public RequestBean(String namespace, String name, Object[] parameters) {
        this.namespace = namespace;
        this.name = name;
        this.parameters = parameters;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "RequestBean{" +
                "namespace='" + namespace + '\'' +
                ", name='" + name + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
