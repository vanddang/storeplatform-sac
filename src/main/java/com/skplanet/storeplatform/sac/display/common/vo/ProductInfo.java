/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.vo;

import com.skplanet.storeplatform.sac.display.common.ContentType;
import com.skplanet.storeplatform.sac.display.common.MetaRingBellType;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.VodType;

/**
 * <p>
 * 상품 기본정보
 * </p>
 * Updated on : 2014. 07. 08 Updated by : 정희원, SK 플래닛.
 */
public class ProductInfo {

    private String prodId;
    private String svcGrpCd;
    private String svcTypeCd;
    private String metaClsfCd;
    private String contentsTypeCd;
    private String topMenuId;
    private String partParentClsfCd;
    private ContentType contentType;
    private ProductType productType;
    private Object subType;
    private boolean series = false;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getSvcGrpCd() {
        return svcGrpCd;
    }

    public void setSvcGrpCd(String svcGrpCd) {
        this.svcGrpCd = svcGrpCd;
    }

    public String getSvcTypeCd() {
        return svcTypeCd;
    }

    public void setSvcTypeCd(String svcTypeCd) {
        this.svcTypeCd = svcTypeCd;
    }

    public String getMetaClsfCd() {
        return metaClsfCd;
    }

    public void setMetaClsfCd(String metaClsfCd) {
        this.metaClsfCd = metaClsfCd;
    }

    public String getContentsTypeCd() {
        return contentsTypeCd;
    }

    public void setContentsTypeCd(String contentsTypeCd) {
        this.contentsTypeCd = contentsTypeCd;
    }

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }

    public String getPartParentClsfCd() {
        return partParentClsfCd;
    }

    public void setPartParentClsfCd(String partParentClsfCd) {
        this.partParentClsfCd = partParentClsfCd;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setSubType(Object subType) {
        this.subType = subType;
    }

    public boolean isSeries() {
        return series;
    }

    public void setSeries(boolean series) {
        this.series = series;
    }

    public MetaRingBellType getMetaRingBellType() {
        if(subType instanceof MetaRingBellType)
            return (MetaRingBellType) subType;
        else
            return null;
    }

    public VodType getVodType() {
        if(subType instanceof VodType)
            return (VodType) subType;
        else
            return null;
    }

    /**
     * 인앱상품 여부 조회. (In-App Purchase가 맞으나. In-App Billing으로 사용중임)
     * @return true 이면 인앱상품
     */
    public boolean isIap() {
        return productType == ProductType.App && "PD012302".equals(partParentClsfCd);
    }
}
