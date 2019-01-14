package com.sodyu.elasticsearch.index.annotation;



import com.sodyu.elasticsearch.index.enums.IndexFieldType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by sodyu on 2016/10/9.
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface IndexField {
    IndexFieldType dataType() default IndexFieldType.Default;
    String name() default "";
    boolean store() default true;
}
