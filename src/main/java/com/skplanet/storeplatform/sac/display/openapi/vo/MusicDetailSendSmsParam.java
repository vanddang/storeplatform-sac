package com.skplanet.storeplatform.sac.display.openapi.vo;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.MusicDetailSendSmsSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public class MusicDetailSendSmsParam {
    private String tenantId;
    private String mdn;
    private String channelId;

    public MusicDetailSendSmsParam(SacRequestHeader sacRequestHeader, MusicDetailSendSmsSacReq musicDetailSendSmsSacReq) {
        tenantId = sacRequestHeader.getTenantHeader().getTenantId();
        mdn = musicDetailSendSmsSacReq.getMdn();
        channelId = musicDetailSendSmsSacReq.getChannelId();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getMdn() {
        return mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
