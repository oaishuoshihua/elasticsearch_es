package com.sodyu.elasticsearch.search.responsemapper;

import org.elasticsearch.action.search.SearchResponse;

/**
 * Created by sodyu on 2016/10/10
 **/
public abstract class BaseResponseMapper<Response> implements IResultMapper<Response> {

    SearchResponse response;

    BaseResponseMapper(SearchResponse response){
        this.response = response;
    }
}
