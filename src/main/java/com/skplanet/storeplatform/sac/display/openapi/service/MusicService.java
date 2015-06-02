package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.display.openapi.vo.MusicProd;

public interface MusicService {
    public MusicProd getDetailBySongId(String tenantId, String outsdContentsId);
}
