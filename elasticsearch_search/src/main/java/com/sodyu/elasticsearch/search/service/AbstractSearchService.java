package com.sodyu.elasticsearch.search.service;

import com.sodyu.elasticsearch.search.request.ICustomRequest;
import com.sodyu.elasticsearch.search.response.ICustomResponse;

/**
 * Created by sodyu on 2016/10/10
 **/
public abstract class AbstractSearchService<T extends ICustomRequest, K extends ICustomResponse> implements ISearchService<T, K> {


}
