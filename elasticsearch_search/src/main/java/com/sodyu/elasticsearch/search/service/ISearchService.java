package com.sodyu.elasticsearch.search.service;

/**
 * Created by sodyu on 2016/10/10
 **/
public interface ISearchService<Request,Response> {
    Response search(Request t);
}
