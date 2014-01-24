package com.skplanet.storeplatform.sac.display.app.vo;

/**
 * ImageSourceReq
 * Updated on : 2014. 01. 24 Updated by : 정희원, SK 플래닛.
 */
public class ImageSourceReq {
    private String episodeId;
    private String imgCd;
    private String langCd;

    public ImageSourceReq() {}
    public ImageSourceReq(String episodeId, String imgCd, String langCd) {
        this.episodeId = episodeId;
        this.imgCd = imgCd;
        this.langCd = langCd;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String getImgCd() {
        return imgCd;
    }

    public void setImgCd(String imgCd) {
        this.imgCd = imgCd;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }
}
