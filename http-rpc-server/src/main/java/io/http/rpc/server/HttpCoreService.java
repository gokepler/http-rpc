package io.http.rpc.server;

import io.http.rpc.CoreServiceInvoker;
import io.http.rpc.RequestBean;
import io.http.rpc.ServiceInvokeException;
import io.http.rpc.serialize.ProtoSerializeScheme;
import io.http.rpc.serialize.SerializeScheme;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * Created by manbu on 7/1/16.
 */
public class HttpCoreService implements CoreServiceInvoker, InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private SerializeScheme serializeScheme;

    private ServiceRegistrationCenter registrationCenter;

    private static final Log logger = LogFactory.getLog(HttpCoreService.class);

    @Override
    public byte[] invoke(String namespace, String name, byte[][] parameterData) {

        ServiceDefinition serviceDefinition
                = registrationCenter.getServiceDefinition(namespace, name);

        if(serviceDefinition != null) {

            Class<?> cls = serviceDefinition.getServiceClass();

            Method method = serviceDefinition.getMethod();

            Object bean = applicationContext.getBean(cls);

            return PerformanceUtil.invoke(bean, method, parameterData, serializeScheme);

        } else {

            throw new ServiceInvokeException("service namespace:["+namespace+"] name:["+name+"] not found!");
        }
    }

    public byte[] execute(byte[] data) {

        RequestBean bean = serializeScheme.deserialize(data, RequestBean.class);

        return invoke(bean.getNamespace(), bean.getName(), bean.getParameters());
    }

    public void setRegistrationCenter(ServiceRegistrationCenter registrationCenter) {
        this.registrationCenter = registrationCenter;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setSerializeScheme(SerializeScheme serializeScheme) {
        this.serializeScheme = serializeScheme;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if(serializeScheme == null) {
            serializeScheme = applicationContext.getBean(SerializeScheme.class);
        }

        if(serializeScheme == null) {
            serializeScheme = new ProtoSerializeScheme();
        }
    }
}
