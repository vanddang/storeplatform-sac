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

import com.skplanet.storeplatform.external.client.ocb.sci.OcbSCI;
import com.skplanet.storeplatform.external.client.ocb.vo.SearchOcbPointEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import com.skplanet.storeplatform.sac.member.domain.shared.UserOcb;
import com.skplanet.storeplatform.sac.member.repository.UserOcbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * OcbService
 * - OCB서비스 JPA버전
 * </p>
 * Updated on : 2016. 02. 01 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional(value = "transactionManagerForMember", readOnly = true)
public class OcbService {

    @Autowired
    private UserMemberService memberService;

    @Autowired
    private UserOcbRepository ocbRepository;

    @Autowired
    private OcbSCI ocbSCI;

    /**
     * 유저의 OCB정보를 조회한다.
     * @param userKey 유저 식별자
     * @return 응답
     */
    public List<UserOcb> find(String userKey) {

        memberService.findByUserKeyAndTransitRepo(userKey);
        return ocbRepository.findByUserKey(userKey);
    }

    @Transactional("transactionManagerForScMember")
    public void merge(String userKey, String ocbNo, String authMtdCd, String regId) {

        UserMember member = memberService.findByUserKeyAndActive(userKey);

        checkOcbCard(ocbNo);

        UserOcb newOcb = new UserOcb();
        newOcb.setMember(member);
        newOcb.setOcbAuthMtdCd(authMtdCd);
        newOcb.setOcbNo(ocbNo);
        newOcb.setRegId(regId);
        newOcb.setUseStartDt(new Date());
        newOcb.setUseEndDt(DateUtils.parseDate("99991231115959"));
        newOcb.setUseYn("Y");

        ocbRepository.updateDisableAll(userKey);
        ocbRepository.save(newOcb);
    }

    @Transactional("transactionManagerForScMember")
    public void delete(String userKey, String ocbNo) {

        memberService.findByUserKeyAndActive(userKey);

        UserOcb ocb = ocbRepository.findByUserKeyAndOcbNo(userKey, ocbNo);
        if(ocb == null)
            throw new StorePlatformException("SAC_MEM_1700", ocbNo);

        ocbRepository.updateDisable(userKey, ocbNo);
    }

    /**
     * OCB연동하여 카드 번호가 유효한지 확인한다.
     * @param ocbNo
     */
    private void checkOcbCard(String ocbNo) {
        /**
         * OCB 연동시에 대시가 없는 상태로 연동 되기 때문에 데이타를 변환함.
         */
        String ocbCardNumber = ocbNo.replaceAll("-", "");

        /**
         * OCB 카드번호 정보 조회 연동
         */
        SearchOcbPointEcReq searchOcbPointEcReq = new SearchOcbPointEcReq();
        searchOcbPointEcReq.setOcbCardNum(ocbCardNumber); // OCB 카드 번호
        this.ocbSCI.searchOcbPoint(searchOcbPointEcReq);
    }
}
