package com.skplanet.storeplatform.sac.client.display.vo.app;

/**
 * 앱 상품 상세 Request
 *
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
public class AppDetailReq {

    private String seedApp;
    private String episodeId;

    public String getSeedApp() {
        return seedApp;
    }

    public void setSeedApp(String seedApp) {
        this.seedApp = seedApp;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }
}
