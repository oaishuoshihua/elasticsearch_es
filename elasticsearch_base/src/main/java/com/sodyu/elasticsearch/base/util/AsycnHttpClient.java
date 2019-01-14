package com.sodyu.elasticsearch.base.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sodyu on 2016/9/21
 **/
public class AsycnHttpClient {

    private static Logger logger = LoggerFactory.getLogger(AsycnHttpClient.class);
    private static final int  SOCKT_OUT_TIME = 2000;//获取数据超时时间
    private static final int  CONNECT_OUT_TIME = 1000;//连接超时时间
    private static final int  GET_CONNECT_OUT_TIME = 1000;//从连接池获取连接超时时间
    private static final int  MAX_CONNECT_TOTAL = 200;
    private static CloseableHttpAsyncClient httpclient;
    static {
        init();
    }


    public static CloseableHttpAsyncClient getHttpClient(){
        return httpclient;
    }

    private static void init(){
        httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(configRequestConfig()).setConnectionManager(configHttpClientConnectionManager()).build();
    }

    private static PoolingNHttpClientConnectionManager configHttpClientConnectionManager(){
        IOReactorConfig reactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setConnectTimeout(CONNECT_OUT_TIME).setSoTimeout(SOCKT_OUT_TIME).build();

        ConnectingIOReactor reactor = null;
        try {
            reactor = new DefaultConnectingIOReactor(reactorConfig);
        } catch (IOReactorException e) {
            logger.error("SE.buildConnectionManager", e);
        }
        PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(reactor);
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
