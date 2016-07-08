package io.http.rpc.client;

import io.http.rpc.RequestBean;
import io.http.rpc.annotation.ServiceMapping;
import io.http.rpc.serialize.SerializeScheme;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by manbu on 7/3/16.
 */
public class ProxyExecutor implements MethodInterceptor {

    private CoreServiceInvoker serviceInvoker;

    private String namespace;

    private SerializeScheme serializeScheme;

    private String url;

    public ProxyExecutor(CoreServiceInvoker serviceInvoker, SerializeScheme serializeScheme) {
        this.serviceInvoker = serviceInvoker;
        this.serializeScheme = serializeScheme;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] parameters, MethodProxy methodProxy) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            return methodProxy.invokeSuper(o, parameters);
        }

        int length = parameters.length;

        byte[][] parameterData = new byte[length][];

        for (int i = 0; i < length; i++) {
            parameterData[i] = serializeScheme.serialize(parameters[i]);
        }

        String name = "";

        ServiceMapping mapping = method.getAnnotation(ServiceMapping.class);
        if(mapping != null) {
            name = mapping.value();
        }

        if(StringUtils.isEmpty(name)) {
            name = method.getName();
        }

        RequestBean requestBean = new RequestBean(namespace, name, parameterData);

        byte[] rtv = serviceInvoker.invoke(url, requestBean);

        return serializeScheme.deserialize(rtv, method.getReturnType());
    }
}
