package com.sodyu.elasticsearch.search.common.enums;

import com.sodyu.elasticsearch.search.requestbuilder.DefaultSearchBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sodyu on 2016/10/10
 **/
public enum RequestMappingBuilderEnum {

    DEFAULT("DefaultRequest", DefaultSearchBuilder.class);

    private String className;
    private Class<?> builderClass;

    RequestMappingBuilderEnum(String className, Class<?> builderClass) {
        this.className = className;
        this.builderClass = builderClass;
    }

    public static Class getClassByName(String className) {
        Class clazz = null;
        if (StringUtils.isNotBlank(className)) {
            for (RequestMappingBuilderEnum item : RequestMappingBuilderEnum.values()){
                if (item.className.equalsIgnoreCase(className)) {
                    clazz = item.builderClass;
                    break;
                }
            }
        }
        return clazz;
    }
}
