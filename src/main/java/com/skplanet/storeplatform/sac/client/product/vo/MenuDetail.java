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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.annotate.JsonRawValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>
 * MenuDetail
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MenuDetail extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 테넌트 ID
     */
    private String tenantId;

    /**
     * 시스템 ID
     */
    private String systemId;

    /**
     * 메뉴 Key
     */
    private String menuKey;

    /**
     * 상위 메뉴 Key
     */
    private String upMenuKey;

    /**
     * 노출 순서
     */
    private Integer expoOrd;

    /**
     * 식별자
     */
    private String keyType;

    /**
     * 메뉴명
     */
    private String menuName;

    /**
     * 썸네일 이미지 URL
     */
    private String imgPath;

    /**
     * 주입 변수
     */
    @JsonRawValue
    private Object urlParam;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getUpMenuKey() {
        return upMenuKey;
    }

    public void setUpMenuKey(String upMenuKey) {
        this.upMenuKey = upMenuKey;
    }

    public Integer getExpoOrd() {
        return expoOrd;
    }

    public void setExpoOrd(Integer expoOrd) {
        this.expoOrd = expoOrd;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Object getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(Object urlParam) {
        this.urlParam = urlParam;
    }
}
