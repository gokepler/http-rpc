package io.http.rpc.annotation;

import java.lang.annotation.*;

/**
 * Created by manbu on 7/2/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceNamespace {

    /**
     *
     * namespace of service
     *
     */
    String value() default "";

}
