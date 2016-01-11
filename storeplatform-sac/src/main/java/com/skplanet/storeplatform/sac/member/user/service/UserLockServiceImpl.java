/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.sac.client.member.vo.user.LockAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.LockAccountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 회원 계정 잠금 서비스 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 2. 10. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserLockServiceImpl implements UserLockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLockServiceImpl.class);

    @Autowired
    private MemberCommonComponent mcc;

    @Autowired
    private UserSCI userSCI;

    @Override
    public LockAccountSacRes lockAccount(SacRequestHeader sacHeader, LockAccountSacReq req) {

        /** 1. 미동의 회원 체크및 회원 정보 조회. */
        CheckDuplicationResponse checkDuplicationResponse = this.checkDisAgree(sacHeader, req);

        LockAccountSacRes response = new LockAccountSacRes();

        /** 2. 이미 잠겨 있으면 리턴 셋팅 */
        if(checkDuplicationResponse.getUserMbr().getLoginStatusCode().equals(MemberConstants.USER_LOGIN_STATUS_PAUSE)){
            response.setUserId(req.getUserId());
            /** 3. 잠겨 있지 않으면 회원 계정 잠금 */
        }else {
            this.modLoginStatus(sacHeader, req.getUserId());
            response.setUserId(req.getUserId());
        }

        return response;
    }

    /**
     * <pre>
     * 미동의 회원 체크및 회원 정보 조회.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     * @return CheckDuplicationResponse
     */
    private CheckDuplicationResponse checkDisAgree(SacRequestHeader sacHeader, LockAccountSacReq req) {

        List<KeySearch> keySearchList = new ArrayList<KeySearch>();
        KeySearch keySchUserKey = new KeySearch();
        keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
        keySchUserKey.setKeyString(req.getUserId());
        keySearchList.add(keySchUserKey);

        /** SC 회원 조회. */
        CheckDuplicationRequest chkDupReq = new CheckDuplicationRequest();
        chkDupReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
        chkDupReq.setKeySearchList(keySearchList);
        CheckDuplicationResponse chkDupRes = this.userSCI.checkDuplication(chkDupReq);

        if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {
            throw new StorePlatformException("SAC_MEM_0003", "userId", req.getUserId());
        }

        return chkDupRes;

    }

    /**
     * <pre>
     * 회원 계정 잠금 업데이트.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param userId
     *            사용자 아이디
     */
    public void modLoginStatus(SacRequestHeader sacHeader, String userId) {

        UpdateStatusUserRequest updStatusUserReq = new UpdateStatusUserRequest();

        List<KeySearch> keySearchList = new ArrayList<KeySearch>();
        KeySearch key = new KeySearch();
        key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
        key.setKeyString(userId);
        keySearchList.add(key);

        updStatusUserReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
        updStatusUserReq.setKeySearchList(keySearchList);
        updStatusUserReq.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_PAUSE);

        this.userSCI.updateStatus(updStatusUserReq);
        LOGGER.debug("## 회원 계정 잠금 DB 설정 완료!!");

    }

}
