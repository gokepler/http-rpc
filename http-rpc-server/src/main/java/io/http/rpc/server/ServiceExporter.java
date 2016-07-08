package io.http.rpc.server;

import io.http.rpc.serialize.SerializeScheme;
import org.springframework.web.HttpRequestHandler;

/**
 *
 * service exporter
 *
 * Created by manbu on 7/8/16.
 */
public interface ServiceExporter extends HttpRequestHandler {

    ServiceRegistrationCenter getRegistrationCenter();

    SerializeScheme getSerializeScheme();

}
