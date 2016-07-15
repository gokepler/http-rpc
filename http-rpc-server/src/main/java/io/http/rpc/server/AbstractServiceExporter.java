package io.http.rpc.server;

import io.http.rpc.core.serialize.SerializeScheme;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.remoting.support.RemotingSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by manbu on 7/7/16.
 */
public abstract class AbstractServiceExporter extends RemotingSupport implements ServiceExecution, ServiceExporter, ApplicationContextAware {

    private ApplicationContext applicationContext;

    protected ServiceRegistrationCenter registrationCenter;

    protected SerializeScheme serializeScheme;

    public void invoke(HttpServletRequest request, HttpServletResponse response) throws IOException {

        this.execute(request.getInputStream(), response.getOutputStream());
    }

    @Override
    public void execute(InputStream inputStream, OutputStream outputStream) throws IOException {

        byte[] data = IOUtils.toByteArray(inputStream);

        HttpCoreService coreService = new HttpCoreService();
        coreService.setApplicationContext(applicationContext);
        coreService.setRegistrationCenter(getRegistrationCenter());
        coreService.setSerializeScheme(getSerializeScheme());

        byte[] rtv = coreService.execute(data);

        outputStream.write(rtv);
    }

    public void setRegistrationCenter(ServiceRegistrationCenter registrationCenter) {
        this.registrationCenter = registrationCenter;
    }

    public ServiceRegistrationCenter getRegistrationCenter() {
        return registrationCenter;
    }

    @Override
    public SerializeScheme getSerializeScheme() {
        return serializeScheme;
    }

    public void setSerializeScheme(SerializeScheme serializeScheme) {
        this.serializeScheme = serializeScheme;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
