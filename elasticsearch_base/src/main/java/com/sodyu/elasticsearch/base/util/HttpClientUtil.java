package com.sodyu.elasticsearch.base.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by sodyu on 2016/9/21
 **/
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * get请求
     *
     * @param url    请求url
     * @param datas  请求数据
     * @param header 请求头
     * @param ayscn  是否异步
     * @return
     */
    public static String doGet(String url, Map<String, String> datas, Map<String, String> header, boolean ayscn) {
        String result;
        if (ayscn) {
            result = asycnGetMethord(url, datas, header);
        } else {
            result = sycnGetMethord(url, datas, header);
        }
        return result;
    }


    /**
     * post请求
     *
     * @param url    请求url
     * @param datas  请求数据
     * @param header 请求头
     * @param ayscn  是否异步
     * @return
     */
    public static String doPost(String url, Map<String, String> datas, Map<String, String> header, boolean ayscn) {
        String result;
        if (ayscn) {
            result = asycnPostMethord(url, datas, header);
        } else {
            result = sycnPostMethord(url, datas, header);
        }
        return result;
    }

    /**
     * 同步get方法
     *
     * @param url    请求url
     * @param datas  请求数据
     * @param header 请求头
     * @return
     */
    private static String sycnGetMethord(String url, Map<String, String> datas, Map<String, String> header) {
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        if (MapUtils.isNotEmpty(header)) {
            header.forEach((k, v) -> {
                httpGet.setHeader(k, v);
            });
        }
        List<NameValuePair> params = Lists.newArrayList();
        if (MapUtils.isNotEmpty(datas)) {
            datas.forEach((k, v) -> {
                params.add(new BasicNameValuePair(k, v));
            });
        }
        String paramUrlEncodedStr = null;
        try {
            paramUrlEncodedStr = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (IOException e) {
            logger.error("methord: {}, url:{} 请求参数转换出错", HttpMethod.GET.name(), url, e);
        }

        if (StringUtils.isNotBlank(paramUrlEncodedStr)) {
            httpGet.setURI(URI.create(httpGet.getURI().toString() + "?" + paramUrlEncodedStr));
        }

        HttpClient client = HttpClientFactory.getHttpSycnClient();
        try {
            HttpResponse response = client.execute(httpGet);
            if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            logger.error("methord: {}, url:{} 获取 response结果错误", HttpMethod.GET.name(), url, e);
        }
        return result;
    }


    /**
     * 异步get方法
     *
     * @param url    请求url
     * @param datas  请求数据
     * @param header 请求头
     * @return
     */
    private static String asycnGetMethord(String url, Map<String, String> datas, Map<String, String> header) {
        Map<String, String> resultMap = Maps.newHashMap();
        HttpGet httpGet = new HttpGet(url);
        if (MapUtils.isNotEmpty(header)) {
            header.forEach((k, v) -> {
                httpGet.setHeader(k, v);
            });
        }
        List<NameValuePair> params = Lists.newArrayList();
        if (MapUtils.isNotEmpty(datas)) {
            datas.forEach((k, v) -> {
                params.add(new BasicNameValuePair(k, v));
            });
        }
        String paramUrlEncodedStr = null;
        try {
            paramUrlEncodedStr = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (IOException e) {
            logger.error("methord: {}, url:{}, url请求参数转换出错", HttpMethod.GET.name(), url, e);
        }

        if (StringUtils.isNotBlank(paramUrlEncodedStr)) {
            httpGet.setURI(URI.create(httpGet.getURI().toString() + "?" + paramUrlEncodedStr));
        }

        HttpAsyncClient client = HttpClientFactory.getHttpAsycnClient();
        try {
            CustomCallBack customCallBack = new CustomCallBack(resultMap, url, HttpMethod.GET.name());
            client.execute(httpGet, customCallBack);
        } catch (Exception e) {
            logger.error("methord: {}, url:{} 获取response结果错误", HttpMethod.GET.name(), url, e);
        }
        return resultMap.getOrDefault("response", "");
    }


    /**
     * 同步post方法
     *
     * @param url    请求url
     * @param datas  请求数据
     * @param header 请求头
     * @return
     */
    private static String sycnPostMethord(String url, Map<String, String> datas, Map<String, String> header) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        if (MapUtils.isNotEmpty(header)) {
            header.forEach((k, v) -> {
                httpPost.setHeader(k, v);
            });
        }
        List<NameValuePair> params = Lists.newArrayList();
        if (MapUtils.isNotEmpty(datas)) {
            datas.forEach((k, v) -> {
                params.add(new BasicNameValuePair(k, v));
            });
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, CharsetUtil.UTF_8));
            HttpClient client = HttpClientFactory.getHttpSycnClient();
            HttpResponse response = client.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            logger.error("methord: {}, url:{}获取 response结果错误", HttpMethod.POST.name(), url, e);
        }
        return result;
    }


    /**
     * 异步post方法
     *
     * @param url    请求url
     * @param datas  请求数据
     * @param header 请求头
     * @return
     */
    private static String asycnPostMethord(String url, Map<String, String> datas, Map<String, String> header) {
        Map<String, String> resultMap = Maps.newHashMap();
        HttpPost httpPost = new HttpPost(url);
        if (MapUtils.isNotEmpty(header)) {
            header.forEach((k, v) -> {
                httpPost.setHeader(k, v);
            });
        }
        List<NameValuePair> params = Lists.newArrayList();
        if (MapUtils.isNotEmpty(datas)) {
            datas.forEach((k, v) -> {
                params.add(new BasicNameValuePair(k, v));
            });
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, CharsetUtil.UTF_8));
            HttpAsyncClient client = HttpClientFactory.getHttpAsycnClient();
            CustomCallBack customCallBack = new CustomCallBack(resultMap, url, HttpMethod.POST.name());
            client.execute(httpPost, customCallBack);
        } catch (Exception e) {
            logger.error("methord: {}, url:{} 获取response结果错误", HttpMethod.POST.name(), url, e);
        }
        return resultMap.getOrDefault("response", "");
    }




}

class CustomCallBack implements FutureCallback<HttpResponse> {
    private static Logger logger = LoggerFactory.getLogger(CustomCallBack.class);
    Map<String, String> map;
    String url = "";
    String methordType;

    CustomCallBack(Map<String, String> resultMap, String url, String methordType) {
        this.map = resultMap;
        this.url = url;
        this.methordType = methordType;
    }

    @Override
    public void completed(HttpResponse result) {
        if (result != null && result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                map.put("response", EntityUtils.toString(result.getEntity()));
            } catch (IOException e) {
                logger.error("解析结果错误，methord: {}, url:{}", methordType, url, e);
            }
        }
    }

    @Override
    public void failed(Exception ex) {
        logger.error("methord: {}, url:{}, 获取response结果失败", methordType, url, ex);
    }

    @Override
    public void cancelled() {
        logger.info("取消方法, methord: {}, url:{}", methordType, url);
    }
}