package com.sodyu.elasticsearch.base.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by sodyu on 2016/9/21
 **/
public class SycnHttpClient {
    private static Logger logger = LoggerFactory.getLogger(SycnHttpClient.class);
    private static final int  SOCKT_OUT_TIME = 2000;//获取数据超时时间
    private static final int  CONNECT_OUT_TIME = 1000;//连接超时时间
    private static final int  GET_CONNECT_OUT_TIME = 1000;//从连接池获取连接超时时间
    private static final int  MAX_CONNECT_TOTAL = 200;
    private static CloseableHttpClient httpclient;
    static {
        init();
    }


    public static CloseableHttpClient getHttpClient(){
        return httpclient;
    }

    private static void init(){
        httpclient = HttpClientBuilder.create().setConnectionManager(configHttpClientConnectionManager()).setDefaultRequestConfig(configRequestConfig()).build();
    }

    private static PoolingHttpClientConnectionManager configHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_CONNECT_TOTAL);
        return connectionManager;
    }

    private static RequestConfig configRequestConfig(){
        return  RequestConfig.custom()
                .setConnectionRequestTimeout(GET_CONNECT_OUT_TIME)
                .setConnectTimeout(CONNECT_OUT_TIME)
                .setSocketTimeout(SOCKT_OUT_TIME).build();
    }
}
