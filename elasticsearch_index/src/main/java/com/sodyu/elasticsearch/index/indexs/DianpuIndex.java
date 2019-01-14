package com.sodyu.elasticsearch.index.indexs;

import com.sodyu.elasticsearch.index.annotation.IndexField;
import com.sodyu.elasticsearch.index.enums.IndexFieldType;

/**
 * Created by yuhp on 2018/10/23
 **/
public class DianpuIndex extends ParentIndex {


    @IndexField(dataType = IndexFieldType.Keyword)
    private String shopId;

    @IndexField(dataType = IndexFieldType.Text)
    private String shopName;

    @IndexField
    private Long saleCount;

    @IndexField
    private String telephone;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Long saleCount) {
        this.saleCount = saleCount;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
