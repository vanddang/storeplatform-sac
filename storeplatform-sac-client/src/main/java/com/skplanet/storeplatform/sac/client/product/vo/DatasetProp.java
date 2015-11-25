/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * DatasetProp
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DatasetProp extends CommonInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 데이터셋ID
     */
    private String id;

    /**
     * 데이터셋 타이틀
     */
    private String title;

    /**
     * 데이터셋 호출 API Template (CCS IF 기준)
     */
    private String url;

    /**
     * 스마트 오퍼링 리스트 아이디
     */
    private String smartOfrListId;

    /**
     * 데이터셋 호출 API에 조합될 파라미터
     * TODO JSONParse에서 문제 없는지 확인
     */
    private Map<String, String> urlParam;

    /**
     * Item 랜딩에 사용될 호출 API Template (CCS IF 기준)
     */
    private String itemLndUrl;

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmartOfrListId() {
		return smartOfrListId;
	}

	public void setSmartOfrListId(String smartOfrListId) {
		this.smartOfrListId = smartOfrListId;
	}

	public Map<String, String> getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(Map<String, String> urlParam) {
        this.urlParam = urlParam;
    }

    public String getItemLndUrl() {
        return itemLndUrl;
    }

    public void setItemLndUrl(String itemLndUrl) {
        this.itemLndUrl = itemLndUrl;
    }
}
