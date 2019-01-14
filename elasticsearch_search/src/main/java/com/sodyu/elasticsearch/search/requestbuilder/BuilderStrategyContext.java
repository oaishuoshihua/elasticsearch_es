package com.sodyu.elasticsearch.search.requestbuilder;


import com.sodyu.elasticsearch.search.common.enums.RequestMappingBuilderEnum;
import com.sodyu.elasticsearch.search.request.ICustomRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by sodyu on 2016/10/10
 **/
public class BuilderStrategyContext {
    private static final Logger logger = LoggerFactory.getLogger(BuilderStrategyContext.class);
    ICustomRequest request;

    public BuilderStrategyContext(ICustomRequest request) {
        this.request = request;
    }

    public SearchRequest createRequest() {
        SearchRequest searchRequest = null;
        try {
            Class<ISearchBuilder> clazz = RequestMappingBuilderEnum.getClassByName(request.getClass().getSimpleName());
            if (clazz != null) {
                Constructor<ISearchBuilder> constructor = clazz.getConstructor(request.getClass());
                ISearchBuilder t = constructor.newInstance(request);
                searchRequest = t.build();
            } else {
                logger.error("没有自定义请求实体与构造对象的对应关系");
            }
        } catch (NoSuchMethodException e) {
            logger.error("转换request结果报错NoSuchMethodException", e);
        } catch (InstantiationException e) {
            logger.error("转换request结果报错InstantiationException", e);
        } catch (IllegalAccessException e) {
            logger.error("转换request结果报错IllegalAccessException", e);
        } catch (InvocationTargetException e) {
            logger.error("转换request结果报错InvocationTargetException", e);
        }
        return searchRequest;
    }
}
