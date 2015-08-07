package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetailParam;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetailSendSmsParam;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetailSendSmsRes;

public interface MusicService {
    public MusicDetail getMusicDetail(MusicDetailParam musicDetailParam);
    public MusicDetailSendSmsRes musicDetailSendSms(MusicDetailSendSmsParam musicDetailSendSmsParam);
}
