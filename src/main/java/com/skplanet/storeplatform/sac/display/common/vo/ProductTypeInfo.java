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

import com.skplanet.storeplatform.sac.display.common.EbookComicType;
import com.skplanet.storeplatform.sac.display.common.MetaRingBellType;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.VodType;

/**
 * <p>
 * 상품 기본정보
 * </p>
 * Updated on : 2014. 07. 24 Updated by : 정희원, SK 플래닛.
 */
public class ProductTypeInfo {

    protected ProductType productType;
    protected boolean series = false;
    protected Object subType;

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public boolean isSeries() {
        return series;
    }

    public void setSeries(boolean series) {
        this.series = series;
    }

    public void setSubType(Object subType) {
        this.subType = subType;
    }

    public Object getSubType() {
        return subType;
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

    public EbookComicType getEbookComicType() {
        if(subType instanceof EbookComicType)
            return (EbookComicType) subType;
        else
            return null;
    }

}
