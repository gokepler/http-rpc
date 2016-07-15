package io.http.rpc.core;

/**
 * HTTP rpc core service.
 *
 * Created by manbu on 7/1/16.
 */
public interface CoreServiceInvoker {

    /**
     * core service.
     * @param namespace 命名空间
     * @param name 服务名
     * @param parameters 参数列表
     *
     * */
    Object invoke(String namespace, String name, Object[] parameters);

}
