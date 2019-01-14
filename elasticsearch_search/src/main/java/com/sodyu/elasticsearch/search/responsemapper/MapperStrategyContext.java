package com.sodyu.elasticsearch.search.responsemapper;


import com.sodyu.elasticsearch.search.common.enums.ResponseMappingResultEnum;
import com.sodyu.elasticsearch.search.response.ICustomResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by sodyu on 2016/10/10
 **/
public class MapperStrategyContext {
    private static final Logger logger = LoggerFactory.getLogger(MapperStrategyContext.class);
    SearchResponse response;

    public MapperStrategyContext(SearchResponse response) {
        this.response = response;
    }




    public ICustomResponse convertResponse(String responseName) {
        ICustomResponse customResponse = null;
        try {
            Class clazz = ResponseMappingResultEnum.getClassByName(responseName);
            if (clazz != null) {
                Constructor<IResultMapper> constructor = clazz.getConstructor(SearchResponse.class);
                IResultMapper t = constructor.newInstance(response);
                Object result = t.mapper();
                customResponse = (ICustomResponse)result;
            } else {
                logger.error("没有自定义response实体与构造对象的对应关系");
            }
        } catch (NoSuchMethodException e) {
            logger.error("转换response结果报错NoSuchMethodException", e);
        } catch (InstantiationException e) {
            logger.error("转换response结果报错InstantiationException", e);
        } catch (IllegalAccessException e) {
            logger.error("转换response结果报错IllegalAccessException", e);
        } catch (InvocationTargetException e) {
            logger.error("转换response结果报错InvocationTargetException", e);
        }
        return customResponse;
    }
}
