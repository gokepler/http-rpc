package io.http.rpc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by manbu on 7/8/16.
 */
public interface ServiceExecution {

    /**
     *
     * 服务执行
     * @param inputStream 请求流
     * @param outputStream 响应流
     *
     * */
    void execute(InputStream inputStream, OutputStream outputStream) throws IOException;

}
