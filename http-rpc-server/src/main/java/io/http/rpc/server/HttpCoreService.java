package io.http.rpc.server;

import io.http.rpc.core.CoreServiceInvoker;
import io.http.rpc.core.RequestBean;
import io.http.rpc.core.ServiceInvokeException;
import io.http.rpc.core.serialize.SerializeScheme;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * Created by manbu on 7/1/16.
 */
public class HttpCoreService implements CoreServiceInvoker {

    private ApplicationContext applicationContext;

    private SerializeScheme serializeScheme;

    private ServiceRegistrationCenter registrationCenter;

    private static final Log logger = LogFactory.getLog(HttpCoreService.class);

    @Override
    public Object invoke(String namespace, String name, Object[] parameterData) {

        ServiceDefinition serviceDefinition
                = registrationCenter.getServiceDefinition(namespace, name);

        if(serviceDefinition != null) {

            Class<?> cls = serviceDefinition.getServiceClass();

            Method method = serviceDefinition.getMethod();

            Object bean = applicationContext.getBean(cls);

            return PerformanceUtil.invoke(bean, method, parameterData);

        } else {

            throw new ServiceInvokeException("service namespace:["+namespace+"] name:["+name+"] not found!");
        }
    }

    public byte[] execute(byte[] data) {

        RequestBean bean = serializeScheme.deserialize(data, RequestBean.class);

        Object rtv = invoke(bean.getNamespace(), bean.getName(), bean.getParameters());

        return serializeScheme.serialize(rtv);
    }

    public void setRegistrationCenter(ServiceRegistrationCenter registrationCenter) {
        this.registrationCenter = registrationCenter;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setSerializeScheme(SerializeScheme serializeScheme) {
        this.serializeScheme = serializeScheme;
    }
}
