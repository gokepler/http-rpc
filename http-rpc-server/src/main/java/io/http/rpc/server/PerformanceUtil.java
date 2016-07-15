package io.http.rpc.server;

import io.http.rpc.core.ServiceInvokeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by manbu on 7/3/16.
 */
public class PerformanceUtil {

    public static Object invoke(Object bean, Method method, Object[] parameterData) {

        int parameterCount = parameterData.length;

        if(method.getParameterCount() != parameterData.length) {

            throw new ServiceInvokeException("service parameter count is not match.");
        }

        Parameter[] parameters = method.getParameters();

        Object[] parameter = new Object[parameterCount];

        for (int i = 0; i < parameterCount; i++) {

            Class<?> cls = parameters[i].getType();

            if(parameterData[i] == null) {

                parameter[i] = null;

                continue;
            }

            if(cls.equals(parameterData[i].getClass())) {

                parameter[i] = parameterData[i];

            } else {

                throw new ServiceInvokeException("service parameter type is not match.");
            }
        }

        try {

            return method.invoke(bean, parameter);

        } catch (IllegalAccessException | InvocationTargetException e) {

            throw new ServiceInvokeException("service invoke error.", e);
        }

    }

}
