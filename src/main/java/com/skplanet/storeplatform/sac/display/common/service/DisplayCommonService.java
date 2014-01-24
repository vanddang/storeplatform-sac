package com.skplanet.storeplatform.sac.display.common.service;

import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;

import java.util.List;

/**
 * 전시 공통 서비스
 *
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
public interface DisplayCommonService {

    /**
     * 배치 완료 기준일시를 리턴.
     * @param tenantId 테넌트ID
     * @param listId 목록ID(혹은 배치ID)
     * @return 기준일시를 yyyyMMddHHmmss 형태의 문자열로 리턴. 만족하는 데이터가 없는 경우 null
     */
    public String getBatchStandardDateString(String tenantId, String listId);

    /**
     * 메뉴 목록을 조회한다.
     * @param prodId 상품ID
     * @param langCd 언어코드
     * @return
     */
    public List<MenuItem> getMenuItemList(String prodId, String langCd);

}
