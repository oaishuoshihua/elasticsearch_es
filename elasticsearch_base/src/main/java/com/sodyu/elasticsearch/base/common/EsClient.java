package com.sodyu.elasticsearch.base.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by sodyu on 2016/9/19
 **/
public class EsClient {

    private static Logger logger = LoggerFactory.getLogger(EsClient.class);

    //存放不同集群的transportClient
    private static final Map<String, TransportClient> transportClientMap = Maps.newConcurrentMap();

    /**
     * 默认集群
     *
     * @return
     */
    public static TransportClient getTransportClient(String type) throws Exception {
        String url = TransportClientUrlEnum.valueOf(type).getUrl();
        if (transportClientMap.get(url) == null) {
            synchronized (EsClient.class) {
                if (transportClientMap.get(url) == null) {
                    Map<String, String> header = TransportClientUrlEnum.valueOf(type).header();
                    transportClientMap.put(url, client(url, header));
                }
            }
        }
        return transportClientMap.get(url);
    }


    private static TransportClient client(String clusterHost, Map<String, String> header) {
        TransportClient transportClient = null;
        try {
            CatApi catApi = new CatApi(clusterHost, header);
            String cluster_name = getClusterName(catApi);
            Map<String, String> nodes = getNodeIpAndPort(catApi);
            if (StringUtils.isBlank(cluster_name) || MapUtils.isEmpty(nodes)) {
                logger.error("url：{} 获取集群名称以及节点为空", clusterHost);
            }
            logger.info("初始化集群 {}， 节点{}， transportclient", cluster_name, JSON.toJSONString(nodes));
            transportClient = new PreBuiltXPackTransportClient(getSettings(cluster_name));
            for (Map.Entry<String, String> itemEntry : nodes.entrySet()) {
                transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(itemEntry.getKey()), NumberUtils.toInt(itemEntry.getValue(), 9300)));
            }

        } catch (UnknownHostException e) {
            logger.error("url：{} 获取transportClient报错", clusterHost, e);
        }
        return transportClient;
    }

    private static Settings getSettings(String cluster_name) {
        return Settings.builder()
                .put(EsConstant.XPACK_SECURITY_TRANCSPORT_SSL_ENABLED, Boolean.FALSE)
                .put(EsConstant.XPACK_SECURITY_USER, "elastic:changeme")
                .put(EsConstant.CLUSTER_NAME, cluster_name)
                .put(EsConstant.TRANSPORT_SNIFF, Boolean.TRUE)
                .build();
    }

    /**
     * 获取集群几点信息
     *
     * @param catApi
     * @return
     */
    private static Map<String, String> getNodeIpAndPort(CatApi catApi) {
        Map<String, String> nodes = Maps.newHashMap();
        JSONArray nodeArray = catApi.nodes();
        if (nodeArray != null) {
            for (int i = 0; i < nodeArray.size(); i++) {
                nodes.put(nodeArray.getJSONObject(i).getString(EsConstant.IP), nodeArray.getJSONObject(i).getString(EsConstant.PORT));
            }

        }
        return nodes;
    }

    /**
     * 获取集群名称
     *
     * @param catApi
     * @return
     */
    private static String getClusterName(CatApi catApi) {
        String clusterName = null;
        JSONObject cluster = catApi.cluster();
        if (cluster != null) {
            clusterName = cluster.getString(EsConstant.CLUSTER_NAME_CAT_API);
        }
        return clusterName;
    }
}
