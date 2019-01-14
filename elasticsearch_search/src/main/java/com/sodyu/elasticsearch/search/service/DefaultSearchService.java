package com.sodyu.elasticsearch.search.service;

import com.alibaba.fastjson.JSON;

import com.sodyu.elasticsearch.base.common.TransportClientFactory;
import com.sodyu.elasticsearch.search.request.DefaultRequest;
import com.sodyu.elasticsearch.search.requestbuilder.BuilderStrategyContext;
import com.sodyu.elasticsearch.search.response.DefaultResponse;
import com.sodyu.elasticsearch.search.responsemapper.MapperStrategyContext;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;


/**
 * Created by sodyu on 2016/9/27
 **/
@Service
public class DefaultSearchService extends AbstractSearchService<DefaultRequest, DefaultResponse> {
    @Override
    public DefaultResponse search(DefaultRequest t) {
        BuilderStrategyContext context = new BuilderStrategyContext(t);
        SearchRequest request = context.createRequest();
        SearchResponse response = TransportClientFactory.getDefaultClient().search(request).actionGet();
        MapperStrategyContext resultContext = new MapperStrategyContext(response);
        DefaultResponse defaultResponse = (DefaultResponse) resultContext.convertResponse(DefaultResponse.class.getSimpleName());
        return defaultResponse;
    }

    public static void main(String[] args) {
        DefaultSearchService search = new DefaultSearchService();
        DefaultResponse response = search.search(new DefaultRequest());
        System.out.println(JSON.toJSONString(response));
    }

}
