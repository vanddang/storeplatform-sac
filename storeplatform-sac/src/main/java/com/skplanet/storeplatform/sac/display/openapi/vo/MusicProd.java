package com.skplanet.storeplatform.sac.display.openapi.vo;

import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

public class MusicProd {
    private String prodId;
    private String prodStatusCd;
    private String metaClsfCd;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdStatusCd() {
        return prodStatusCd;
    }

    public void setProdStatusCd(String prodStatusCd) {
        this.prodStatusCd = prodStatusCd;
    }

    public String getMetaClsfCd() {
        return metaClsfCd;
    }

    public void setMetaClsfCd(String metaClsfCd) {
        this.metaClsfCd = metaClsfCd;
    }

    public String getProdType() {

        if (metaClsfCd == null) {
            return null;
        }

        if (DisplayConstants.DP_MUSIC_MP3_META_CLASS_CD.equals(metaClsfCd)) {
            return "mp3";
        }
        else if (DisplayConstants.DP_MUSIC_NORMAL_COLORRING_META_CLASS_CD.equals(metaClsfCd)) {
            return "ringShort";
        }
        else if (DisplayConstants.DP_MUSIC_LONG_COLORRING_META_CLASS_CD.equals(metaClsfCd)) {
            return "ringLong";
        }
        else if (DisplayConstants.DP_MUSIC_NORMAL_BELL_META_CLASS_CD.equals(metaClsfCd)) {
            return "bellNormalQuality";
        }
        else if (DisplayConstants.DP_MUSIC_HIGH_QUALITY_BELL_META_CLASS_CD.equals(metaClsfCd)) {
            return "bellHighQuality";
        }
        return "Unknown";
    }
}
