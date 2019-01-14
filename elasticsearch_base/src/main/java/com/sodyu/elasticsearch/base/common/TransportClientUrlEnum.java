package com.sodyu.elasticsearch.base.common;


import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuhp on 2018/10/18
 **/
public enum TransportClientUrlEnum {

    DEFAULT("default", "http://192.168.197.134:9200", ""),
    TEST("test", "http://10.32.210.138:5370", "");

    private String type;
    private String url;
    private String authorization;

    TransportClientUrlEnum(String type, String url, String auth) {
        this.type = type;
        this.url = url;
        this.authorization = auth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public Map<String,String> header(){
        HashMap<String,String> header = Maps.newHashMap();
        header.put("Authorization", this.getAuthorization());
        return header;
    }
}
