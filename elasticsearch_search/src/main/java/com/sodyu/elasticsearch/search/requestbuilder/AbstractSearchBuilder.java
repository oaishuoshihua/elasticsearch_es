package com.sodyu.elasticsearch.search.requestbuilder;


import com.sodyu.elasticsearch.search.request.ICustomRequest;
import org.elasticsearch.action.search.SearchRequest;

/**
 * Created by sodyu on 2016/9/28
 **/
public abstract class AbstractSearchBuilder<Request extends ICustomRequest> implements ISearchBuilder {
    private Request defaultRequest;

    AbstractSearchBuilder(Request defaultRequest) {
        this.defaultRequest = defaultRequest;
    }
    protected abstract void queryBuilder(SearchRequest srequest);

    protected abstract void filterBuilder(SearchRequest srequest);

    protected abstract void fetchSourceBuilder(SearchRequest srequest);

    protected abstract void storedFieldBuilder(SearchRequest srequest);

    protected abstract void sortBuilder(SearchRequest srequest);

    protected abstract void statBuilder(SearchRequest srequest);

    protected abstract void pageBuilder(SearchRequest srequest);

}
