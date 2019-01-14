package com.sodyu.elasticsearch.search.response;


import com.sodyu.elasticsearch.search.common.entity.Field;

import java.util.List;

/**
 * Created by sodyu on 2016/10/10
 **/
public class DefaultResponse implements ICustomResponse {

    private List<Field> fields;

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
