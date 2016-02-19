package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckUserMarketPinReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckUserMarketPinRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateUserMarketPinReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateUserMarketPinRes;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMarketPin;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import com.skplanet.storeplatform.sac.member.repository.UserMarketPinRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserMarketPinServiceImpl implements UserMarketPinService {

    @Autowired
    UserMarketPinRepository userMarketPinRepository;

    @Autowired
    UserMemberService userMemberService;

    private static Integer MAX_FAIL_CNT = 5;

    @Override
    @Transactional("transactionManagerForScMember")
    public CreateUserMarketPinRes createMarketPin(CreateUserMarketPinReq createMarketPinReq) {
        //회원 유효성 체크
        UserMember userMember = userMemberService.findByUserKeyAndActive(createMarketPinReq.getUserKey());
        UserMarketPin userMarketPin = new UserMarketPin(userMember, createMarketPinReq.getPinNo(), 0);

        userMarketPinRepository.save(userMarketPin);
        return new CreateUserMarketPinRes(userMarketPin.getMember().getInsdUsermbrNo());
    }

    @Override
    @Transactional("transactionManagerForScMember")
    public CheckUserMarketPinRes checkMarketPin(CheckUserMarketPinReq checkMarketPinReq) {
        //회원 유효성 체크
        userMemberService.findByUserKeyAndActive(checkMarketPinReq.getUserKey());
        UserMarketPin userMarketPin = userMarketPinRepository.findOne(checkMarketPinReq.getUserKey());

        // Market Pin 미설정
        if(userMarketPin == null || StringUtils.isEmpty(userMarketPin.getPinNo())) {
            throw new StorePlatformException("SAC_MEM_3701");
        }

        checkAuthLimit(userMarketPin);

        CheckUserMarketPinRes res;
        if (StringUtils.equals(checkMarketPinReq.getPinNo(), userMarketPin.getPinNo())) {
           // Market Pin 확인성공 -> 실패 횟수 = 0
            userMarketPin.setAuthFailCnt(0);
            userMarketPinRepository.save(userMarketPin);
        } else {
            // Market Pin 실패 -> 실패 횟수 ++
            userMarketPin.setAuthFailCnt(userMarketPin.getAuthFailCnt()+1);
            userMarketPinRepository.save(userMarketPin);
        }
        res = new CheckUserMarketPinRes(checkMarketPinReq.getUserKey(), userMarketPin.getAuthFailCnt());
        return res;
    }

    private void checkAuthLimit(UserMarketPin userMarketPin) {
        if(userMarketPin.getAuthFailCnt() >= MAX_FAIL_CNT) {
            // Market Pin 5회 실패, "Market PIN 인증 횟수 초과" 오류 발생
            throw new StorePlatformException("SAC_MEM_3702");
        }
    }

    @Transactional("transactionManagerForScMember")
    public boolean transferMarketPin(String prevInsdUsermbrNo, String newInsdUsermbrNo) {
        boolean isSuccessTransfer = false;
        UserMarketPin newUserMarketPin = userMarketPinRepository.findOne(newInsdUsermbrNo);
        // 이관될 새로운 회원키에 Market Pin 이 존재할 경우 삭제
        if(newUserMarketPin != null) userMarketPinRepository.remove(newUserMarketPin);

        UserMarketPin prevUserMarketPin = userMarketPinRepository.findOne(prevInsdUsermbrNo);
        if(prevUserMarketPin != null) {
            prevUserMarketPin.getMember().setInsdUsermbrNo(newInsdUsermbrNo);
            isSuccessTransfer = true;
        }

        return isSuccessTransfer;
    }
}
