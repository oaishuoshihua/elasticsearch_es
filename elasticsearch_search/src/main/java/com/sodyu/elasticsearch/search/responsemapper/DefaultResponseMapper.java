package com.sodyu.elasticsearch.search.responsemapper;

import com.google.common.collect.Lists;
import com.sodyu.elasticsearch.search.common.entity.Field;
import com.sodyu.elasticsearch.search.response.DefaultResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.List;
import java.util.Map;

/**
 * Created by sodyu on 2016/10/10
 **/
public class DefaultResponseMapper extends BaseResponseMapper<DefaultResponse> {
    public DefaultResponseMapper(SearchResponse response) {
        super(response);
    }

    @Override
    public DefaultResponse mapper() {
        DefaultResponse defaultResponse = new DefaultResponse();

        if(response != null){
            List<Field> fields = Lists.newArrayList();
            defaultResponse.setFields(fields);
            SearchHits searchHits = response.getHits();
            for (int i = 0; i < searchHits.getTotalHits() ; i++) {
                SearchHit searchHit = searchHits.getHits()[i];
                Map<String,Object> values = searchHit.getSource();
                values.forEach((k,v)->{
                    Field field = new Field();
                    field.setFieldName(k);
                    field.setFieldValue(String.valueOf(v));
                    fields.add(field);
                });
            }
        }
        return defaultResponse;
    }
}
