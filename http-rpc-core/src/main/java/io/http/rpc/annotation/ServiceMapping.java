package io.http.rpc.annotation;

import java.lang.annotation.*;

/**
 * Created by manbu on 7/2/16.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceMapping {

    /**
     *
     * name of service
     *
     */
    String value() default "";

}
