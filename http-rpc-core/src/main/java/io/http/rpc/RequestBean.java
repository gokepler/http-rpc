package io.http.rpc;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by manbu on 7/6/16.
 */
public final class RequestBean implements Serializable {

    private static final long serialVersionUID = -2875964084808518400L;

    private String namespace;
    private String name;

    private byte[][] parameters;

    public RequestBean() {}

    public RequestBean(String namespace, String name, byte[][] parameters) {
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

    public byte[][] getParameters() {
        return parameters;
    }

    public void setParameters(byte[][] parameters) {
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
