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
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Transient;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.*;

/**
 * <p>
 * ProductBasicInfo
 * [c25d] 초기 데이터
 * </p>
 * Updated on : 2015. 07. 01 Updated by : 정희원, SK 플래닛.
 */
public class ProductBaseInfo {

    private String topMenuId;
    private String svcGrpCd;
    private String contentsTypeCd;
    private String chnlId;
    private Boolean plus19;
    private String prodGrdCd;

    ///// 아래는 Optional한 필드임 /////
    private String metaClsfCd;
    private String svcTpCd;
    private String partParentClsfCd;
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

    public Boolean getPlus19() {
        return plus19;
    }

    public void setPlus19(Boolean plus19) {
        this.plus19 = plus19;
    }

    public void setPlus19Yn(String plus19Yn) {
        if(plus19Yn == null)
            return;

        this.plus19 = plus19Yn.equals("Y");
    }

    public String getProdGrdCd() {
        return prodGrdCd;
    }

    public void setProdGrdCd(String prodGrdCd) {
        this.prodGrdCd = prodGrdCd;
    }

    /**
     * IAP 상품인지 여부
     * @return IAP상품이면 true
     */
    @Transient
    public boolean isIapProduct() {
        return DP_PART_CHILD_CLSF_CD.equals(partParentClsfCd);
    }

    /**
     * 모상품인 경우 IAP(자상품)이 존재하는지 여부
     * @return 자상품이 존재하는 경우 true
     */
    @Transient
    public boolean hasIapProduct() {
        return DP_PART_PARENT_CLSF_CD.equals(partParentClsfCd);
    }

    /**
     * 시리즈 상품인지 여부
     * @return 시리즈 상품이면 true
     */
    @Transient
    public boolean isSeries() {
        return SET_SERIES_META.contains(metaClsfCd);
    }

    @Transient
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
            return ProductType.Voucher;
        }

        throw new StorePlatformException("SAC_DSP_0025", svcGrpCd, svcTpCd, metaClsfCd);
    }
}
