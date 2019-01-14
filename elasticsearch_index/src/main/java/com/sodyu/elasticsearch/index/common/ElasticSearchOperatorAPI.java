package com.sodyu.elasticsearch.index.common;

import com.sodyu.elasticsearch.index.indexs.IndexType;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by yuhp on 2017/11/30.
 */
public class ElasticSearchOperatorAPI<T extends IndexType> {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchOperatorAPI.class);
    private static TransportClient client = null;
    private RequestBuilder builder = null;

    public ElasticSearchOperatorAPI(RequestBuilder builder, TransportClient client) {
        this.builder = builder;
        this.client = client;
    }



    public  void index(T type) {
        ActionFuture<IndexResponse> result = client.index(builder.indexRequest(type));
        IndexResponse response=result.actionGet();
        if (response == null || !response.status().equals(RestStatus.CREATED)) {
            logger.warn("ElasticSearchOperatorAPI.index fail {}", response != null ? response.status().name() : null);
        }
    }

    public void indexBulk(List<T> type) {
        ActionFuture<BulkResponse> result = client.bulk(builder.indexRequest(type));
        BulkResponse response=result.actionGet();
        if (response == null|| response.hasFailures()) {
            logger.warn("ElasticSearchOperatorAPI.bulk.index fail {}", response.buildFailureMessage());
        }
    }

    public  void update(T type) {
        ActionFuture<UpdateResponse> result = client.update(builder.updateRequest(type));
        UpdateResponse response=result.actionGet();
//        if (response == null || !response.status().equals(RestStatus.OK)) {
//            logger.warn("ElasticSearchOperatorAPI.update fail {}", response != null ? response.status().name() : null);
//        }
    }

    public void updateBulk(List<T> type) {
        ActionFuture<BulkResponse> result = client.bulk(builder.updateRequest(type));
        BulkResponse response=result.actionGet();
        if (response == null|| response.hasFailures()) {
            logger.warn("ElasticSearchOperatorAPI.bulk.update fail {}", response.buildFailureMessage());
        }
    }

    public  void updateUpsert(T type,boolean upsert) {
        ActionFuture<UpdateResponse> result = client.update(builder.updateRequest(type,upsert));
        UpdateResponse response=result.actionGet();
//        if (response == null || !response.status().equals(RestStatus.OK)) {
//            logger.warn("ElasticSearchOperatorAPI.update fail {}", response != null ? response.status().name() : null);
//        }
    }

    public void updateUpsertBulk(List<T> type,boolean upsert) {
        ActionFuture<BulkResponse> result = client.bulk(builder.updateRequest(type,upsert));
        BulkResponse response=result.actionGet();
        if (response == null|| response.hasFailures()) {
            logger.warn("ElasticSearchOperatorAPI.bulk.update fail {}", response.buildFailureMessage());
        }
    }

    public  void delete(T type) {
        ActionFuture<DeleteResponse> result = client.delete(builder.deleteRequest(type));
        DeleteResponse response=result.actionGet();
//        if (response == null || !response.status().equals(RestStatus.OK)) {
//            logger.warn("ElasticSearchOperatorAPI.delete fail {}", response != null ? response.status().name() : null);
//        }
    }

    public void deleteBulk(List<T> type) {
        ActionFuture<BulkResponse> result = client.bulk(builder.deleteRequest(type));
        BulkResponse response=result.actionGet();
        if (response == null|| response.hasFailures()) {
            logger.warn("ElasticSearchOperatorAPI.bulk.delete fail {}", response.buildFailureMessage());
        }
    }



    /**
     * 使用bulkprocessor插入
     * @param type
     */
    public void indexByProcess(List<T> type){
        try {
            BulkProcessor bulkProcessor = getProcess();
            for(T item : type){
                bulkProcessor.add(builder.indexRequest(item));
            }

        } catch (Exception e) {
            logger.error("bulkprocessor插入出错", e);
        }
    }

    /**
     * 使用bulkprocessor插入或更新
     * @param type
     */
    public void upsertByProcess(List<T> type){
        try {
            BulkProcessor bulkProcessor = getProcess();
            for(T item : type){
                bulkProcessor.add(builder.updateRequest(item));
            }

        } catch (Exception e) {
            logger.error("bulkprocessor插入或更新出错", e);
        }
    }

    /**
     * 使用bulkprocessor更新
     * @param type
     */
    public void updateByProcess(List<T> type){
        try {
            BulkProcessor bulkProcessor = getProcess();
            for(T item : type){
                bulkProcessor.add(builder.updateRequest(item));
            }

        } catch (Exception e) {
            logger.error("bulkprocessor更新出错", e);
        }
    }


    /**
     * 使用bulkprocessor删除
     * @param type
     */
    public void deleteByProcess(List<T> type){
        try {
            BulkProcessor bulkProcessor = getProcess();
            for(T item : type){
                bulkProcessor.add(builder.deleteRequest(item));
            }

        } catch (Exception e) {
            logger.error("bulkprocessor删除出错", e);
        }
    }


    public  BulkProcessor getProcess(){
        BulkProcessor processor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {

            }
        }).setBulkActions(1000).setConcurrentRequests(1).build();
        return processor;
    }
}
