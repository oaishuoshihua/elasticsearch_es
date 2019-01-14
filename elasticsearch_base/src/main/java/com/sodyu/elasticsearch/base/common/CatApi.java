package com.sodyu.elasticsearch.base.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sodyu.elasticsearch.base.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by sodyu on 2016/9/27
 **/
public class CatApi {
    private static final Logger logger = LoggerFactory.getLogger(CatApi.class);
    private final static String CLUSTER = "{0}?format=json";
    private final static String CAT_NODES = "{0}/_cat/nodes?h=ip,port&format=json";

    private String host;
    private Map<String, String> header = Maps.newHashMap();
    private Map<String, String> params = Maps.newHashMap();

    public CatApi(String host, Map<String, String> header) {
        this.host = host;
        if (header != null) {
            this.header.putAll(header);
        }
    }

    public CatApi(String host, Map<String, String> header, Map<String, String> params) {
        this.host = host;
        if (header != null) {
            this.header.putAll(header);
        }

        if (params != null) {
            this.params.putAll(params);
        }
    }

    /**
     * 获取集群名称
     *
     * @return
     */
    public JSONObject cluster() {
        JSONObject obj = null;
        try {
            String url = MessageFormat.format(CLUSTER, this.host);
            String json = HttpClientUtil.doGet(url, params, header, Boolean.FALSE);
            obj = JSONObject.parseObject(json);
        } catch (Exception e) {
            logger.error("Cat.cluster.host=[{}]", host, e);
        }
        return obj;
    }

    /**
     * 获取集群中节点信息
     *
     * @return
     */
    public JSONArray nodes() {
        JSONArray arr = null;
        try {
            String url = MessageFormat.format(CAT_NODES, this.host);
            String json = HttpClientUtil.doGet(url, params, header, Boolean.FALSE);
            arr = JSONArray.parseArray(json);
        } catch (Exception e) {
            logger.error("Cat.nodes.host=[{}]", host, e);
        }
        return arr;
    }
}
