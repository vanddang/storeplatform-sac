package com.skplanet.storeplatform.sac.display.app.service;

import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetailParam;
import com.skplanet.storeplatform.sac.display.app.vo.UpdateHistory;

import java.util.List;

/**
 * AppService
 *
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
public interface AppService {

    /**
     * 앱 상세 정보 조회
     * @param request
     * @return
     */
    public AppDetailRes getAppDetail(AppDetailParam request);

    /**
     * 업데이트 히스토리 갯수 조회
     * @param channelId
     * @param offset
     * @param count
     * @return
     */
    public List<UpdateHistory> getUpdateList(String channelId, Integer offset, Integer count);

    /**
     * 업데이트 히스토리의 전체 갯수 조회
     * @param channelId
     * @return
     */
    public int getUpdateCount(String channelId);

}
