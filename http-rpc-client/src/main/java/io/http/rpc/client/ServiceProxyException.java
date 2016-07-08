package io.http.rpc.client;

/**
 * Created by manbu on 7/3/16.
 */
public class ServiceProxyException extends Exception {

    private static final long serialVersionUID = 4049328529666351567L;

    public ServiceProxyException(String message, Exception e) {
        super(message, e);
    }

}
