package com.skplanet.storeplatform.sac.display.app.vo;

/**
 * ImageSourceReq
 * Updated on : 2014. 01. 24 Updated by : 정희원, SK 플래닛.
 */
public class ImageSourceReq {
    private String channelId;
    private String[] imgCds;
    private String langCd;

    public ImageSourceReq() {}

    public ImageSourceReq(String channelId, String[] imgCds, String langCd) {
        this.channelId = channelId;
        this.imgCds = imgCds;
        this.langCd = langCd;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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
