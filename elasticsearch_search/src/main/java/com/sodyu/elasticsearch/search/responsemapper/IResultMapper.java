package com.sodyu.elasticsearch.search.responsemapper;

/**
 * Created by sodyu on 2016/10/10
 **/
public interface IResultMapper<SearchResponse> {
    SearchResponse mapper();
}
