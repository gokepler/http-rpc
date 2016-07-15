package io.http.rpc.client;

import io.http.rpc.core.RequestBean;
import io.http.rpc.core.ServiceInvokeException;
import io.http.rpc.core.serialize.SerializeScheme;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by manbu on 7/6/16.
 */
public class ProxyCoreServiceInvoker implements CoreServiceInvoker {

    private static final OkHttpClient client = new OkHttpClient();

    public static final MediaType TYPE = MediaType.parse("application/octet-stream; charset=utf-8");

    private SerializeScheme serializeScheme;

    public ProxyCoreServiceInvoker(SerializeScheme serializeScheme) {
        this.serializeScheme = serializeScheme;
    }

    @Override
    public byte[] invoke(String url, RequestBean bean) {

        byte[] data = serializeScheme.serialize(bean);

        RequestBody body = RequestBody.create(TYPE, data);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {

            Response response = client.newCall(request).execute();

            return response.body().bytes();

        } catch (IOException e) {

            throw new ServiceInvokeException("Invoke exception." + bean, e);
        }
    }
}
