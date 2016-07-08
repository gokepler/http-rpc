package io.http.rpc.client;

import io.http.rpc.serialize.SerializeScheme;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by manbu on 7/2/16.
 */
public class ClientBuilder {

    public static ObjProxyBuilder proxyBuilder(String baseUrl){
        return new ObjProxyBuilder(baseUrl);
    }

    public static class ObjProxyBuilder {

        private String url;
        private String namespace;

        public ObjProxyBuilder(String url) {
            this.url = url;
        }

        @SuppressWarnings("unchecked")
        public <T> T build(Class<T> clazz, SerializeScheme serializeScheme) throws ServiceProxyException {

            if(StringUtils.isEmpty(namespace)) {
                namespace = clazz.getName();
            }

            ServiceProxyFactory factory = new ServiceProxyFactory();
            factory.setSerializeScheme(serializeScheme);
            factory.setUrl(url);

            return factory.build(clazz);

        }

        public <T> T build(Class<T> clazz) throws ServiceProxyException {

            return build(clazz, null);

        }
    }
}
