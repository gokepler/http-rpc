package io.http.rpc.serialize;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Created by manbu on 7/2/16.
 */
public final class ProtoSerializeUtils {

    @SuppressWarnings("unchecked")
    public static <T> byte[] encode(T object) {

        Schema<T> schema = RuntimeSchema.getSchema((Class<T>) object.getClass());

        LinkedBuffer buffer = LinkedBuffer.allocate(512);

        return ProtobufIOUtil.toByteArray(object, schema,  buffer);
    }

    @SuppressWarnings("unchecked")
    public static <T> T decode(byte[] data, Class<T> clazz) {

        Schema<T> schema = RuntimeSchema.getSchema(clazz);

        T m = schema.newMessage();

        ProtobufIOUtil.mergeFrom(data, m, schema);

        return m;
    }

}
