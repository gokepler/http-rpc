package io.http.rpc.server;

import io.http.rpc.ServiceInvokeException;
import io.http.rpc.serialize.SerializeScheme;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by manbu on 7/3/16.
 */
public class PerformanceUtil {

    public static byte[] invoke(Object bean, Method method, byte[][] parameterData, SerializeScheme serializeScheme) {

        int parameterCount = parameterData.length;

        if(method.getParameterCount() != parameterData.length) {

            throw new ServiceInvokeException("parameter is not match.");
        }

        Parameter[] parameters = method.getParameters();

        Object[] parameter = new Object[parameterCount];

        for (int i = 0; i < parameterCount; i++) {

            Class<?> cls = parameters[i].getType();

            Object object = serializeScheme.deserialize(parameterData[i], cls);

            parameter[i] = object;
        }

        try {

            Object rtv = method.invoke(bean, parameter);

            return serializeScheme.serialize(rtv);

        } catch (IllegalAccessException | InvocationTargetException e) {

            throw new ServiceInvokeException("service invoke error.", e);
        }

    }

}
