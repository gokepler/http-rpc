package io.http.rpc.client;

import io.http.rpc.core.annotation.ServiceNamespace;
import io.http.rpc.core.serialize.SerializeScheme;
import net.sf.cglib.proxy.Enhancer;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by manbu on 7/7/16.
 */
public abstract class ProxyBuilder {

    protected String url;

    protected CoreServiceInvoker serviceInvoker;

    protected SerializeScheme serializeScheme;

    public ProxyBuilder() {}

    public ProxyBuilder(CoreServiceInvoker serviceInvoker, SerializeScheme serializeScheme) {
        this.serviceInvoker = serviceInvoker;
        this.serializeScheme = serializeScheme;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setServiceInvoker(CoreServiceInvoker serviceInvoker) {
        this.serviceInvoker = serviceInvoker;
    }

    public void setSerializeScheme(SerializeScheme serializeScheme) {
        this.serializeScheme = serializeScheme;
    }

    public CoreServiceInvoker getServiceInvoker() {
        return serviceInvoker;
    }

    public SerializeScheme getSerializeScheme() {
        return serializeScheme;
    }

    public String getUrl() {
        return url;
    }

    @SuppressWarnings("unchecked")
    protected <T> T proxy(Class<T> serviceInterface) {

        String namespace = "";

        ServiceNamespace serviceNamespace = serviceInterface.getAnnotation(ServiceNamespace.class);

        if(serviceNamespace != null) {
            namespace = serviceNamespace.value();
        }

        if(StringUtils.isEmpty(namespace)) {
            namespace = serviceInterface.getName();
        }

        ProxyExecutor proxyExecutor = new ProxyExecutor(serviceInvoker, serializeScheme);

        proxyExecutor.setNamespace(namespace);
        proxyExecutor.setUrl(url);

        Object proxy = Enhancer.create(null, new Class[] { serviceInterface }, proxyExecutor);

        return (T) proxy;
    }

}
