/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

import java.util.List;

/**
 * <p>
 * DownloadSupportService
 * </p>
 * Updated on : 2014. 07. 10 Updated by : 정희원, SK 플래닛.
 */
public interface DownloadSupportService {

    /**
     * 다운로드 결과를 로그에 남긴다.
     * @param userKey
     * @param deviceKey
     * @param prodId
     * @param encryptionList
     * @param elapTime
     */
    void logDownloadResult(String userKey, String deviceKey, String prodId, List<Encryption> encryptionList, long elapTime);

    /**
     * Encryption을 생성한다.
     * @param metaInfo
     * @param prchProdId
     * @return
     */
    Encryption generateEncryption(MetaInfo metaInfo, String prchProdId);

    /**
     * Encryption을 생성한다.
     * (for Vod) Tstore 4.0 FHD 지원 여부 추가
     * @param metaInfo
     * @param prchProdId
     * @param supportFhdVideo
     * @return
     */
    Encryption generateEncryption(MetaInfo metaInfo, String prchProdId, boolean supportFhdVideo);

    void createUserDownloadInfo(String mdn, String aid, String tenantId, String prodId);

    /**
     * 구매에서 조회한 DRM 정보를 기반으로 사용만료일을 매핑한다.
     * @param metaInfo purchaseId, purchaseProdId, systemId, tenantId, userKey가 매핑된 상태여야 한다.
     */
    void mapPurchaseDrmInfo(MetaInfo metaInfo);

    /**
     * 구매 경로가 Tfreemium인지 검사한다.
     * @param prchsReqPathCd: 구매 경로 코드
     * @return Tfreemium 구매이면 true, 그외 false
     */
    boolean isTfreemiumPurchase(String prchsReqPathCd);
}
