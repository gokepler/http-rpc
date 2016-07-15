package io.http.rpc.core.serialize;

/**
 * Created by manbu on 7/2/16.
 */
public class SerializeException extends RuntimeException {

    private static final long serialVersionUID = -547750670624988463L;

    public SerializeException(String message, Exception exception) {
        super(message, exception);
    }

}
