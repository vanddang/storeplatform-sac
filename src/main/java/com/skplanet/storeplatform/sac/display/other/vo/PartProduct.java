/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;

import java.util.List;

/**
 * <p>
 * PartProduct
 * </p>
 * Updated on : 2014. 03. 27 Updated by : 정희원, SK 플래닛.
 */
public class PartProduct extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String prodId;
    private String prodNm;
    private Integer prodAmt;
    private String prodCase;
    private String prodKind;
    private String parentProdId;
    private String parentTopMenuId;
    private List<Point> pointList;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public Integer getProdAmt() {
        return prodAmt;
    }

    public void setProdAmt(Integer prodAmt) {
        this.prodAmt = prodAmt;
    }

    public String getProdCase() {
        return prodCase;
    }

    public void setProdCase(String prodCase) {
        this.prodCase = prodCase;
    }

    public String getProdKind() {
        return prodKind;
    }

    public void setProdKind(String prodKind) {
        this.prodKind = prodKind;
    }

    public String getParentProdId() {
        return parentProdId;
    }

    public void setParentProdId(String parentProdId) {
        this.parentProdId = parentProdId;
    }

    public String getParentTopMenuId() {
        return parentTopMenuId;
    }

    public void setParentTopMenuId(String parentTopMenuId) {
        this.parentTopMenuId = parentTopMenuId;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }
}
