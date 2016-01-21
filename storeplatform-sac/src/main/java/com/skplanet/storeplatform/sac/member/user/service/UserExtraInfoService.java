package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckAdditionalInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckAdditionalInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

import java.util.List;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 인터페이스
 *
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 */
public interface UserExtraInfoService {

    /**
     * 등록/수정
     *
     * @param userKey
     * @param reqInfos
     * @return
     */
    void modifyExtraInfo(String userKey, List<UserExtraInfo> reqInfos);

    void modifyExtraInfo(String userKey, String itemId, String value);

    /**
     * 조회
     *
     * @param userKey
     * @return
     */
    List<UserExtraInfo> findExtraInfo(String userKey);

    /**
     * 부가정보 속성 조회
     * @param userKey
     * @param itemId
     * @return
     */
    String getExtraInfoValue(String userKey, String itemId);

    /**
     * 사용자 부가정보 삭제
     * @param userKey
     * @param infoKeyList
     */
    void removeExtraInfo(String userKey, List<String> infoKeyList);

}
