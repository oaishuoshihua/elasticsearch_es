package com.sodyu.elasticsearch.search.searchapi;


import com.sodyu.elasticsearch.search.request.DefaultRequest;
import com.sodyu.elasticsearch.search.request.ICustomRequest;
import com.sodyu.elasticsearch.search.response.DefaultResponse;
import com.sodyu.elasticsearch.search.response.ICustomResponse;

/**
 * Created by sodyu on 2016/10/15
 **/
public interface SearchApi {
    ICustomResponse bussinessFirstSearch(ICustomRequest request);
    ICustomResponse bussinessTwoSearch(ICustomRequest request);
    DefaultResponse defaultSearch(DefaultRequest request);
}
