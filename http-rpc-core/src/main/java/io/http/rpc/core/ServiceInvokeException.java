package io.http.rpc.core;

/**
 * Created by manbu on 7/3/16.
 */
public class ServiceInvokeException extends RuntimeException {

    public ServiceInvokeException(String message) {
        super(message);
    }

    public ServiceInvokeException(String message, Exception e) {
        super(message, e);
    }

}
