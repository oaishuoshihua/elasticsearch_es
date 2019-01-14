package com.sodyu.elasticsearch.base.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

/**
 * Created by sodyu on 2016/9/21
 **/
public class HttpClientFactory {

    /**
     * 获取异步httpclient
     * @return
     */
    public static CloseableHttpAsyncClient getHttpAsycnClient(){
        return AsycnHttpClient.getHttpClient();
    }

    /**
     * 获取同步httpclient
     * @return
     */
    public static CloseableHttpClient getHttpSycnClient(){
        return SycnHttpClient.getHttpClient();
    }
}
