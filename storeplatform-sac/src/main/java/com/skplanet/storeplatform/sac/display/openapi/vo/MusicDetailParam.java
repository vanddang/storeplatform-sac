package com.skplanet.storeplatform.sac.display.openapi.vo;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.MusicDetailSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public class MusicDetailParam {
    private String tenantId;
    private String id;
    private String idType;

    public MusicDetailParam(SacRequestHeader sacRequestHeader, MusicDetailSacReq musicDetailSacReq) {
        tenantId = sacRequestHeader.getTenantHeader().getTenantId();
        id = musicDetailSacReq.getId();
        idType = musicDetailSacReq.getIdType();
    }

    public boolean isIdType(String idType) {
        return this.idType.equals(idType);
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }
}
