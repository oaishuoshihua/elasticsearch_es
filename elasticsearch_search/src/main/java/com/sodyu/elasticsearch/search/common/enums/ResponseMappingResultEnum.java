package com.sodyu.elasticsearch.search.common.enums;

import com.sodyu.elasticsearch.search.responsemapper.DefaultResponseMapper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sodyu on 2016/10/10
 **/
public enum ResponseMappingResultEnum {

    DEFAULT("DefaultResponse", DefaultResponseMapper.class);

    private String className;
    private Class<?> mapperClass;

    ResponseMappingResultEnum(String className, Class<?> mapperClass) {
        this.className = className;
        this.mapperClass = mapperClass;
    }

    public static Class getClassByName(String className) {
        Class clazz = null;
        if (StringUtils.isNotBlank(className)) {
            for (ResponseMappingResultEnum item : ResponseMappingResultEnum.values()){
                if (item.className.equalsIgnoreCase(className)) {
                    clazz = item.mapperClass;
                    break;
                }
            }
        }
        return clazz;
    }
}
