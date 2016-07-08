package io.http.rpc.serialize;

/**
 * Created by manbu on 7/6/16.
 */
public class ProtoSerializeScheme implements SerializeScheme {

    @Override
    public <T> byte[] serialize(T bean) {

        return ProtoSerializeUtils.encode(bean);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {

        return ProtoSerializeUtils.decode(data, clazz);
    }
}
