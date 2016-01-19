package com.skplanet.storeplatform.purchase.common.service;

import com.skplanet.storeplatform.purchase.client.common.vo.ExtraInfo;

/**
 *
 * 구매 추가 정보 테이블(TB_PR_PRCHS_EXTRA_INFO) 저장/조회
 *
 * Updated on : 2016. 1. 15. Updated by : eastaim, SK planet.
 */
public interface PurchaseExtraInfoService {

    /**
     * 구매 추가 정보 생성.
     * @param extraInfo
     */
	public void createExtraInfo(ExtraInfo extraInfo);

    /**
     * 구매 추가 정보 수정.
     * @param extraInfo
     */
    public void modifyExtraInfo(ExtraInfo extraInfo);
}
