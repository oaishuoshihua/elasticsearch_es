package com.sodyu.elasticsearch.search.requestbuilder;

import com.sodyu.elasticsearch.search.request.DefaultRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * Created by sodyu on 2016/9/28
 **/
public class DefaultSearchBuilder extends AbstractSearchBuilder<DefaultRequest> {


    public DefaultSearchBuilder(DefaultRequest defaultRequest) {
        super(defaultRequest);
    }

    @Override
    public SearchRequest build() {
        SearchRequest srequest = new SearchRequest();
        srequest.source(new SearchSourceBuilder());
        srequest.indices("test").types("type");
        queryBuilder(srequest);
        filterBuilder(srequest);
        fetchSourceBuilder(srequest);
        storedFieldBuilder(srequest);
        sortBuilder(srequest);
        statBuilder(srequest);
        pageBuilder(srequest);
        return srequest;
    }


    @Override
    protected void queryBuilder(SearchRequest srequest) {
        srequest.source().query(QueryBuilders.termQuery("name","test"));
    }

    @Override
    protected void filterBuilder(SearchRequest srequest) {

    }

    @Override
    protected void fetchSourceBuilder(SearchRequest srequest) {

    }

    @Override
    protected void storedFieldBuilder(SearchRequest srequest) {

    }

    @Override
    protected void sortBuilder(SearchRequest srequest) {

    }

    @Override
    protected void statBuilder(SearchRequest srequest) {

    }

    @Override
    protected void pageBuilder(SearchRequest srequest) {

    }
}
