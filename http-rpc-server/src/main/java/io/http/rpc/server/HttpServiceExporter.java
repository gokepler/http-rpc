package io.http.rpc.server;


import io.http.rpc.core.serialize.ProtoSerializeScheme;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by manbu on 7/7/16.
 */
public class HttpServiceExporter extends AbstractServiceExporter implements InitializingBean {

    /**
     *
     * Processes the incoming http request and response
     *
     * */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!"POST".equals(request.getMethod())) {

            throw new HttpRequestMethodNotSupportedException(request.getMethod(),
                    new String[] {"POST"}, "HttpServiceExporter only supports POST requests");
        }

        super.invoke(request, response);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(registrationCenter == null) {
            registrationCenter = new ServiceRegistrationCenter();
        }

        if(serializeScheme == null) {
            serializeScheme = new ProtoSerializeScheme();
        }

    }
}
