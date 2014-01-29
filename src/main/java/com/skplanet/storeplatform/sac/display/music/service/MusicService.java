package com.skplanet.storeplatform.sac.display.music.service;

import com.skplanet.storeplatform.sac.display.music.vo.MusicDetailComposite;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetailParam;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailRes;

import java.util.Map;

/**
 * 음악 상세 보기
 *
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
public interface MusicService {

    public MusicDetailComposite getMusicDetail(MusicDetailParam param);

}
