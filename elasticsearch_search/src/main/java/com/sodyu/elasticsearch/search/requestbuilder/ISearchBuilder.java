package com.sodyu.elasticsearch.search.requestbuilder;

import org.elasticsearch.action.search.SearchRequest;

/**
 * Created by sodyu on 2016/9/28
 **/
public interface ISearchBuilder {
    SearchRequest build();
}
