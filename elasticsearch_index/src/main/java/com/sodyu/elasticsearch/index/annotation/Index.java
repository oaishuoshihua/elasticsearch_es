package com.sodyu.elasticsearch.index.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by sodyu on 2016/10/9.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Index {

    String name() default "";//索引名称
    String typeName() default "";//索引类型
}
