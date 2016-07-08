package io.http.rpc.server;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by manbu on 7/6/16.
 */
public class RPCInvokeServlet extends HttpServlet {

    private static final long serialVersionUID = -367437489370011591L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        InputStream inputStream = req.getInputStream();

        byte[] data = IOUtils.toByteArray(inputStream);

        WebApplicationContext applicationContext =
                WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());

        HttpCoreService coreService = applicationContext.getBean(HttpCoreService.class);

        byte[] rtv = coreService.execute(data);

        resp.getOutputStream().write(rtv);
    }


}
