package io.http.rpc.core.serialize;

/**
 *
 * serialize and deserialize scheme
 *
 * Created by manbu on 7/6/16.
 */
public interface SerializeScheme {

    /**
     *
     * 序列化
     *
     * */
    <T> byte[] serialize(T bean);

    /**
     *
     * 反序化
     *
     * */
    <T> T deserialize(byte[] data, Class<T> clazz);

}
