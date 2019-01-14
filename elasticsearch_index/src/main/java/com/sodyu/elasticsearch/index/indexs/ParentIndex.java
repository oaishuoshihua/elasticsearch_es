package com.sodyu.elasticsearch.index.indexs;

import com.sodyu.elasticsearch.index.annotation.IndexId;

/**
 * Created by yuhp on 2018/10/23
 **/
public abstract class ParentIndex implements IndexType {
    @IndexId
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
