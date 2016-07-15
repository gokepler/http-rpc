package io.http.rpc.core.serialize;

/**
 * Created by manbu on 7/6/16.
 */
public class ProtoSerializeScheme implements SerializeScheme {

    @Override
    public <T> byte[] serialize(T bean) {

        if(bean == null) { return new byte[0]; }

        return ProtoSerializeUtils.encode(bean);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {

        if(data.length == 0) { return null; }

        return ProtoSerializeUtils.decode(data, clazz);
    }
}
