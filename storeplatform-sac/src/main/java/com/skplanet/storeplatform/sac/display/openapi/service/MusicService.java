package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetailParam;

public interface MusicService {
    public MusicDetail getMusicDetail(MusicDetailParam musicDetailParam);
}
