package com.skplanet.storeplatform.sac.display.music.vo;

/**
 * 음악 상세보기 요청 VO
 *
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
public class MusicDetailParam {

    private String episodeId;
    private String langCd;
    private String tenantId;

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }
}
