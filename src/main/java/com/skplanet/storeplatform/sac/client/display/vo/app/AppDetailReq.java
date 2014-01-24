package com.skplanet.storeplatform.sac.client.display.vo.app;

/**
 * 앱 상품 상세 Request
 *
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
public class AppDetailReq {

    private String tenantId;
    private String langCd;
    private String episodeId;

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }
}
