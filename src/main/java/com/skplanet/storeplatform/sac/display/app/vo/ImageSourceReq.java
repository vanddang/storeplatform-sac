package com.skplanet.storeplatform.sac.display.app.vo;

import java.util.List;

/**
 * ImageSourceReq
 * Updated on : 2014. 01. 24 Updated by : 정희원, SK 플래닛.
 */
public class ImageSourceReq {
    private String episodeId;
    private String[] imgCds;
    private String langCd;

    public ImageSourceReq() {}

    public ImageSourceReq(String episodeId, String[] imgCds, String langCd) {
        this.episodeId = episodeId;
        this.imgCds = imgCds;
        this.langCd = langCd;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String[] getImgCds() {
        return imgCds;
    }

    public void setImgCds(String[] imgCds) {
        this.imgCds = imgCds;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }
}
