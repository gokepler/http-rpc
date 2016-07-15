package io.http.rpc.client;

import io.http.rpc.core.serialize.ProtoSerializeScheme;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by manbu on 7/7/16.
 */
public class ServiceProxyFactory extends ProxyBuilder implements InitializingBean {

    public ServiceProxyFactory() {}

    @SuppressWarnings("unchecked")
    public <T> T build(Class<T> clazz) throws ServiceProxyException {

        defaultInit();

        return super.proxy(clazz);
    }

    public void defaultInit() {

        if(serializeScheme == null) {
            serializeScheme = new ProtoSerializeScheme();
        }

        if(serviceInvoker == null) {
            serviceInvoker = new ProxyCoreServiceInvoker(serializeScheme);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        defaultInit();
    }
}
