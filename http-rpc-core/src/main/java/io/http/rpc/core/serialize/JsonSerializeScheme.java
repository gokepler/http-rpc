package io.http.rpc.core.serialize;

import java.nio.charset.Charset;

/**
 * Created by manbu on 7/26/16.
 */
public class JsonSerializeScheme implements SerializeScheme {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Override
    public <T> byte[] serialize(T bean) {

        String json = ObjectMapperUtils.toJSON(bean);

        return json.getBytes(CHARSET);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {

        String json = new String(data, CHARSET);

        return ObjectMapperUtils.fromJSON(json, clazz);
    }
}
