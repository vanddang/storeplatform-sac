/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.vo;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * SearchProductListParam
 * </p>
 * Updated on : 2015. 07. 01 Updated by : 정희원, SK 플래닛.
 */
public class SearchProductListParam {

    private String tenantId;
    private String langCd;
    private String deviceModelCd;
    private List<String> prodIdList;
    private Set<String> filterProdGradeCd;
    private Boolean filter19plus;

    public SearchProductListParam() {}

    public SearchProductListParam(String tenantId, String langCd, String deviceModelCd, List<String> prodIdList) {
        this.tenantId = tenantId;
        this.langCd = langCd;
        this.deviceModelCd = deviceModelCd;
        this.prodIdList = prodIdList;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public List<String> getProdIdList() {
        return prodIdList;
    }

    public void setProdIdList(List<String> prodIdList) {
        this.prodIdList = prodIdList;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }

    public Set<String> getFilterProdGradeCd() {
        return filterProdGradeCd;
    }

    public void setFilterProdGradeCd(Set<String> filterProdGradeCd) {
        this.filterProdGradeCd = filterProdGradeCd;
    }

    public Boolean getFilter19plus() {
        return filter19plus;
    }

    public void setFilter19plus(Boolean filter19plus) {
        this.filter19plus = filter19plus;
    }

    public void setFilterProdGradeCdStr(String param) {
        String[] strs = StringUtils.split(param, "+");
        this.filterProdGradeCd = Sets.newHashSet(strs);
    }

}
