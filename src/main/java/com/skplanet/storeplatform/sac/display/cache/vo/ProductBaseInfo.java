/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
//import com.skplanet.storeplatform.sac.common.support.redis.OptionalField;
import com.skplanet.storeplatform.sac.common.support.redis.OptionalField;
import com.skplanet.storeplatform.sac.display.common.EbookComicType;
import com.skplanet.storeplatform.sac.display.common.MetaRingBellType;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.VodType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.ProductTypeInfo;
import org.apache.commons.lang.StringUtils;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.*;

/**
 * <p>
 * ProductBasicInfo
 * - @Optional 적용하자.
 * </p>
 * Updated on : 2015. 07. 01 Updated by : 정희원, SK 플래닛.
 */
public class ProductBaseInfo {

    private String topMenuId;
    private String svcGrpCd;
    private String contentsTypeCd;
    private String chnlId;

    @OptionalField
    private String metaClsfCd;
    @OptionalField
    private String svcTpCd;
    @OptionalField
    private String partParentClsfCd;
    @OptionalField
    private String catId;

    public String getChnlId() {
        return chnlId;
    }

    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
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

    public void setPartParentClsfCd(String partParentClsfCd) {
        this.partParentClsfCd = partParentClsfCd;
    }

    public String getPartParentClsfCd() {
        return partParentClsfCd;
    }

    public String getSvcGrpCd() {
        return svcGrpCd;
    }

    public void setSvcGrpCd(String svcGrpCd) {
        this.svcGrpCd = svcGrpCd;
    }

    public String getSvcTpCd() {
        return svcTpCd;
    }

    public void setSvcTpCd(String svcTpCd) {
        this.svcTpCd = svcTpCd;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    /**
     * IAP 상품인지 여부
     * @return IAP상품이면 true
     */
    public boolean isIapProduct() {
        return DP_PART_CHILD_CLSF_CD.equals(partParentClsfCd);
    }

    /**
     * 모상품인 경우 IAP(자상품)이 존재하는지 여부
     * @return 자상품이 존재하는 경우 true
     */
    public boolean hasIapProduct() {
        return DP_PART_PARENT_CLSF_CD.equals(partParentClsfCd);
    }

    /**
     * 시리즈 상품인지 여부
     * @return 시리즈 상품이면 true
     */
    public boolean isSeries() {
        return SET_SERIES_META.contains(metaClsfCd);
    }

    public ProductType getProductType() {

        String q = StringUtils.join(new String[]{svcGrpCd, svcTpCd, metaClsfCd}, ".");

        if (q.startsWith("DP000201")) {
            return DP_PART_CHILD_CLSF_CD.equals(partParentClsfCd) ? ProductType.InApp : ProductType.App;
        } else if (q.startsWith("DP000203.DP001111")) {
            return ProductType.Music;
        } else if (q.startsWith("DP000208")) {
            return ProductType.Album;
        } else if (q.startsWith("DP000203.DP001115")) {
            if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId))
                return ProductType.VodTv;
            else
                return ProductType.VodMovie;
        } else if (q.matches("DP000203\\.DP001116.*")) {
            if ("DP13".equals(topMenuId))
                return ProductType.Ebook;
            else if("DP14".equals(topMenuId))
                return ProductType.Comic;
            else if ("DP26".equals(topMenuId))
                return ProductType.Webtoon;
        } else if (q.matches("(DP000204|DP000203)\\..*\\.CT(30|31|32|33)")) {
            return ProductType.RingBell;
        } else if (q.startsWith("DP000206")) {
            return ProductType.Shopping;
        } else if (q.startsWith("DP000207")) {
            return ProductType.Freepass;
        }

        throw new StorePlatformException("SAC_DSP_0025", svcGrpCd, svcTpCd, metaClsfCd);
    }
}
