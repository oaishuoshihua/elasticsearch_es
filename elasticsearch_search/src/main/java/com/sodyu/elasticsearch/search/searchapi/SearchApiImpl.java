package com.sodyu.elasticsearch.search.searchapi;

import com.alibaba.fastjson.JSON;
import com.sodyu.elasticsearch.search.request.DefaultRequest;
import com.sodyu.elasticsearch.search.request.ICustomRequest;
import com.sodyu.elasticsearch.search.response.DefaultResponse;
import com.sodyu.elasticsearch.search.response.ICustomResponse;
import com.sodyu.elasticsearch.search.service.DefaultSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by sodyu on 2016/10/15
 * 此处为soa对外接口，此处可以用做controller作为简单的demo示例
 **/
@Controller
@RequestMapping("/search")
public class SearchApiImpl implements SearchApi {
    @Autowired
    DefaultSearchService defaultSearchService;

    @Override
    public ICustomResponse bussinessFirstSearch(ICustomRequest request) {
        return null;
    }

    @Override
    public ICustomResponse bussinessTwoSearch(ICustomRequest request) {
        return null;
    }

    @RequestMapping("/defaultsearch")
    public  @ResponseBody
    String defaultSearchDemo(DefaultRequest request) {
        return JSON.toJSONString(defaultSearch(request));
    }

    @Override
    public  @ResponseBody
    DefaultResponse defaultSearch(DefaultRequest request) {
        return defaultSearchService.search(request);
    }

}
