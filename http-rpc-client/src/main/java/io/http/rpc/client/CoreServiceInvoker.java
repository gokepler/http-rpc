package io.http.rpc.client;

import io.http.rpc.RequestBean;

/**
 * Created by manbu on 7/7/16.
 */
public interface CoreServiceInvoker {

    /**
     *
     * invoke service with url and request bean
     *
     * */
    byte[] invoke(String url, RequestBean bean);

}
