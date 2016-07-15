package io.http.rpc.client.auto;

import io.http.rpc.client.CoreServiceInvoker;
import io.http.rpc.client.ProxyBuilder;
import io.http.rpc.core.serialize.SerializeScheme;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by manbu on 7/6/16.
 */
public class ServiceFactoryBean<T> extends ProxyBuilder implements FactoryBean<T> {

    private Class<T> serviceInterface;

    public ServiceFactoryBean(CoreServiceInvoker serviceInvoker, SerializeScheme serializeScheme) {
        super(serviceInvoker, serializeScheme);
    }

    public ServiceFactoryBean() {}

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {

        return super.proxy(serviceInterface);
    }

    public void setServiceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Class<T> getObjectType() {
        return this.serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
