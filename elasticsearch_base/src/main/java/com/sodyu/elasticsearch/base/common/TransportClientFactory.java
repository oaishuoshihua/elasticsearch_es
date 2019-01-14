package com.sodyu.elasticsearch.base.common;

import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuhp on 2018/10/18
 **/
public class TransportClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(TransportClientFactory.class);

    /**
     * 默认集群
     *
     * @return
     */
    public static TransportClient getDefaultClient() {
        TransportClient client = null;
        try {
            client = EsClient.getTransportClient(TransportClientUrlEnum.DEFAULT.name());
        } catch (Exception e) {
            logger.error("获取默认集群transportClient报错", e);
        }
        return client;
    }

    /**
     * 测试集群
     *
     * @return
     */
    public static TransportClient getTestClient() {
        TransportClient client = null;
        try {
            client = EsClient.getTransportClient(TransportClientUrlEnum.TEST.name());
        } catch (Exception e) {
            logger.error("获取Test集群transportClient报错", e);
        }
        return client;
    }
}
