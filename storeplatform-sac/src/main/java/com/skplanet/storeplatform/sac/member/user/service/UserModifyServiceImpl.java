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

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.repository.MemberCommonRepository;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import com.skplanet.storeplatform.sac.member.domain.UserClauseAgree;
import com.skplanet.storeplatform.sac.member.domain.UserMember;
import com.skplanet.storeplatform.sac.member.repository.UserClauseAgreeRepository;
import com.skplanet.storeplatform.sac.member.repository.UserMemberRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 회원 정보 수정 서비스 (CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 24. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserModifyServiceImpl implements UserModifyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserModifyServiceImpl.class);

    @Autowired
    private MemberCommonComponent mcc;

    @Autowired
    private MemberCommonRepository mcr;

    @Autowired
    private UserSCI userSCI;

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private UserMemberRepository memberRepository;

    @Autowired
    private UserClauseAgreeRepository clauseAgreeRepository;

    @Override
    public ModifyRes modUser(SacRequestHeader sacHeader, ModifyReq req) {

        ModifyRes response = new ModifyRes();

        /**
         * 회원 정보 조회.
         */
        try{
            List<KeySearch> keySearchList = new ArrayList<KeySearch>();
            KeySearch keySchUserKey = new KeySearch();
            keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
            keySchUserKey.setKeyString(req.getUserKey());
            keySearchList.add(keySchUserKey);
            SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
            CommonRequest commonRequest = new CommonRequest();
            searchExtentUserRequest.setCommonRequest(commonRequest);
            searchExtentUserRequest.setKeySearchList(keySearchList);
            searchExtentUserRequest.setUserInfoYn(MemberConstants.USE_Y);
            SearchExtentUserResponse res = this.userSCI.searchExtentUser(searchExtentUserRequest);

            /**
             * 변경 가능 상테 체크 (매인, 서브상태 정상인 회원)
             */
            if(!StringUtils.equalsIgnoreCase(res.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_NORMAL)
                    || !StringUtils.equalsIgnoreCase(res.getUserMbr().getUserSubStatus(), MemberConstants.SUB_STATUS_NORMAL)){
                throw new StorePlatformException("SAC_MEM_2001", res.getUserMbr().getUserMainStatus(),
                        res.getUserMbr().getUserSubStatus());
            }

        } catch (StorePlatformException ex) {
            if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
                    || StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
                throw new StorePlatformException("SAC_MEM_0003", "userKey", req.getUserKey());
            }else{
                throw ex;
            }
        }

        /**
         * SC 회원 수정.
         */
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        /**
         * 공통 정보 setting.
         */
        updateUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

        /**
         * 사용자 기본정보 setting.
         */
        updateUserRequest.setUserMbr(this.getUserMbr(req));

        /**
         * SC 사용자 회원 기본정보 수정 요청.
         */
        UpdateUserResponse updateUserResponse = this.userSCI.updateUser(updateUserRequest);
        if (updateUserResponse.getUserKey() == null || StringUtils.equals(updateUserResponse.getUserKey(), "")) {
            throw new StorePlatformException("SAC_MEM_0002", "userKey");
        }

        response.setUserKey(updateUserResponse.getUserKey());

        return response;
    }

    @Override
    public ModifyPasswordRes modPassword(SacRequestHeader sacHeader, ModifyPasswordReq req) {

        /**
         * 회원 정보 조회.
         */
        UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

        /**
         * 비밀 번호 유효성 체크
         *  1. 길이 체크
         *  2. 공백 체크 > 기타 비밀번호 제약사항
         *  3. 사용할수 없는 특수문자( `, ~, \ ) 체크
         *  4. 숫자만 구성 체크
         *  5. 문자만 구성 체크
         *  6. 특수문자만 구성 체크 (숫자와 문자가 아닌 경우)
         *  7. 숫자없이 구성 (특수문자+문자) 체크 > 기타 비밀번호 제약 사항
         *  8. 문자없이 구성 (특수문자+숫자) 체크 > 기타 비밀번호 제약 사항
         *  9. 숫자/문자 연속 3자 이상 체크 > 기타 비밀번호 제약사항 체크
         *  10. 비밀번호에 ID일부 문자 포함
         *
         *  비밀번호 제약 사항 (One Id 사이트 동일)
         *  비밀번호는 6~20자의 영문 대소문자,숫자,특수기호만 사용할 수 있습니다.(공백입력불가)
         *  사용 가능한 특수기호 : !@#$%^&*()-_+=|[]{}'";:/?.>,<
         *  3자 이상 연속 영문/숫자 조합은 불가,
         *  아이디에 포함된 문자/숫자와 연속 3자 이상 동일한 비밀번호는 설정이 불가합니다.
         */
        String newPassword = req.getNewPassword();
        // 1. 길이 체크
        if(newPassword.length() < 6 || newPassword.length() > 20){
            throw new StorePlatformException("SAC_MEM_1407", userInfo.getUserKey());
        }
        // 2. 공백 체크 > 기타 비밀번호 제약사항
        if (newPassword.indexOf(' ') > 0){
            throw new StorePlatformException("SAC_MEM_1412", userInfo.getUserKey());
        }
        // 3. 사용할수 없는 특수문자( `, ~, \ ) 체크
        if(!Pattern.matches("^[0-9A-Za-z!@#$%^&*()-_+=|\\[\\]\\{\\}'\";:/?.>,<]+$", newPassword)){
            throw new StorePlatformException("SAC_MEM_1411", userInfo.getUserKey());
        }
        // 4. 숫자만 구성 체크
        if(Pattern.matches("^[0-9]*$", newPassword)) {
            throw new StorePlatformException("SAC_MEM_1409", userInfo.getUserKey());
            // 5. 문자만 구성 체크
        }else if(Pattern.matches("^[A-Za-z]*$", newPassword)) {
            throw new StorePlatformException("SAC_MEM_1410", userInfo.getUserKey());
            // 6. 특수문자만 구성 체크 (숫자와 문자가 아닌 경우)
        }else if(Pattern.matches("^[^0-9A-Za-z]*$", newPassword)){
            throw new StorePlatformException("SAC_MEM_1415", userInfo.getUserKey());
            // 조합 패스워드
        }else{
            // 7. 숫자없이 구성 (특수문자+문자) 체크 > 기타 비밀번호 제약 사항
            if(Pattern.matches("^[^0-9]*$", newPassword)) {
                throw new StorePlatformException("SAC_MEM_1412", userInfo.getUserKey());
            }
            // 8. 문자없이 구성 (특수문자+숫자) 체크 > 기타 비밀번호 제약 사항
            if(Pattern.matches("^[^A-Za-z]*$", newPassword)) {
                throw new StorePlatformException("SAC_MEM_1412", userInfo.getUserKey());
            }
        }
        // 다른 제약 사항 체크
        for(int i=0; i<newPassword.length()-2 ; i++){
            // 9. 숫자/문자 연속 3자 이상 체크 > 기타 비밀번호 제약사항 체크
            if( (newPassword.charAt(i+1)-newPassword.charAt(i)==1 && newPassword.charAt(i+2)-newPassword.charAt(i+1)==1)
                    || (newPassword.charAt(i)-newPassword.charAt(i+1)==1 && newPassword.charAt(i+1)-newPassword.charAt(i+2)==1)){
                throw new StorePlatformException("SAC_MEM_1412", userInfo.getUserKey());
            }
            // 10. 비밀번호에 ID일부 문자 포함
            if(userInfo.getUserId().indexOf(newPassword.substring(i, i + 3)) > -1){
                throw new StorePlatformException("SAC_MEM_1408", userInfo.getUserKey());
            }
        }

        /**
         * SC 회원 사용자키, 비밀번호 일치 여부 확인
         */
        CheckUserPwdRequest chkUserPwdRequest = new CheckUserPwdRequest();
        chkUserPwdRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
        chkUserPwdRequest.setUserKey(userInfo.getUserKey());
        chkUserPwdRequest.setUserPw(req.getOldPassword());
        chkUserPwdRequest.setIsDormant(userInfo.getIsDormant());
        CheckUserPwdResponse chkUserPwdResponse = this.userSCI.checkUserPwd(chkUserPwdRequest);
        if( chkUserPwdResponse.getUserKey() == null || chkUserPwdResponse.getUserKey().length() <=0 ){
            throw new StorePlatformException("SAC_MEM_1406", userInfo.getUserKey());
        }

        /**
         * SC 회원 비밀번호 변경 요청.
         */
        this.modPasswordUser(sacHeader, req, userInfo.getIsDormant());

        /**
         * 결과 setting.
         */
        ModifyPasswordRes response = new ModifyPasswordRes();
        response.setUserKey(req.getUserKey());

        return response;
    }

    @Override
    public ModifyEmailRes modEmail(SacRequestHeader sacHeader, ModifyEmailReq req) {
        List<KeySearch> keySearchList = new ArrayList<KeySearch>();
        KeySearch keySchUserKey = new KeySearch();

        /** 회원 정보 조회. */
        try{
            keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
            keySchUserKey.setKeyString(req.getUserKey());
            keySearchList.add(keySchUserKey);
            SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
            CommonRequest commonRequest = new CommonRequest();
            searchExtentUserRequest.setCommonRequest(commonRequest);
            searchExtentUserRequest.setKeySearchList(keySearchList);
            searchExtentUserRequest.setUserInfoYn(MemberConstants.USE_Y);
            SearchExtentUserResponse res = this.userSCI.searchExtentUser(searchExtentUserRequest);

            /**
             * 변경 가능 상테 체크 (매인, 서브상태 정상인 회원)
             */
            if(!StringUtils.equalsIgnoreCase(res.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_NORMAL)
                    || !StringUtils.equalsIgnoreCase(res.getUserMbr().getUserSubStatus(), MemberConstants.SUB_STATUS_NORMAL)){
                throw new StorePlatformException("SAC_MEM_2001", res.getUserMbr().getUserMainStatus(),
                        res.getUserMbr().getUserSubStatus());
            }

        } catch (StorePlatformException ex) {
            if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
                    || StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
                throw new StorePlatformException("SAC_MEM_0003", "userKey", req.getUserKey());
            }else{
                throw ex;
            }
        }

        /**
         * 변경할 이메일 주소 중복 체크
         */
        keySearchList = new ArrayList<KeySearch>();
        keySchUserKey = new KeySearch();
        keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_EMAIL_ADDR);
        keySchUserKey.setKeyString(req.getNewEmail());
        keySearchList.add(keySchUserKey);

        CheckDuplicationRequest chkDupReq = new CheckDuplicationRequest();
        chkDupReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
        chkDupReq.setKeySearchList(keySearchList);
        CheckDuplicationResponse chkDupRes = this.userSCI.checkDuplication(chkDupReq);

        if (StringUtil.isNotEmpty(chkDupRes.getUserID())) {
            throw new StorePlatformException("SAC_MEM_1404");
        }

        /**
         * 사용자 기본정보 setting.
         */
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
        UserMbr userMbr = new UserMbr();
        userMbr.setUserKey(req.getUserKey()); // 사용자 Key
        userMbr.setUserEmail(req.getNewEmail()); // 신규 이메일
        updateUserRequest.setUserMbr(userMbr);

        /**
         * SC 사용자 회원 기본정보 수정 요청.
         */
        UpdateUserResponse updateUserResponse = this.userSCI.updateUser(updateUserRequest);

        /**
         * 결과 setting.
         */
        ModifyEmailRes response = new ModifyEmailRes();
        response.setUserKey(updateUserResponse.getUserKey());

        return response;
    }

    @Override
    @Transactional("transactionManagerForScMember")
    public void _mergeTermsAgreement(SacRequestHeader sacHeader, String userKey, List<AgreementInfo> agreeList) {

        UserMember member = memberRepository.findOne(userKey);

        if(member == null || !member.isAvailable())
            throw new StorePlatformException("SAC_MEM_0003", "userKey", userKey);

        List<UserClauseAgree> userClauseAgrees = clauseAgreeRepository.findByInsdUsermbrNo(userKey);
        ImmutableMap<String, UserClauseAgree> userAgreeMap = Maps.uniqueIndex(userClauseAgrees, new Function<UserClauseAgree, String>() {
            @Nullable
            @Override
            public String apply(UserClauseAgree input) {
                return input.getClauseId();
            }
        });

        for (AgreementInfo agreementInfo : agreeList) {
            UserClauseAgree userClauseAgree = userAgreeMap.get(agreementInfo.getExtraAgreementId());

            Clause clause = mcr.getClauseItemInfo(agreementInfo.getExtraAgreementId());
            if(clause == null)
                throw new StorePlatformException("SAC_MEM_1105", agreementInfo.getExtraAgreementId());

            String lastVer = clause.getClauseVer(),
                    mandAgreeYn = clause.getMandAgreeYn();

            if(userClauseAgree == null) {
                // NEW
                UserClauseAgree newAgree = new UserClauseAgree();
                newAgree.setMember(member);
                newAgree.setClauseId(agreementInfo.getExtraAgreementId());
                newAgree.setAgreeYn(agreementInfo.getIsExtraAgreement());
                newAgree.setMandAgreeYn(mandAgreeYn);
                newAgree.setClauseVer(!Strings.isNullOrEmpty(agreementInfo.getExtraAgreementVersion()) ?
                                                            agreementInfo.getExtraAgreementVersion() : lastVer);
                clauseAgreeRepository.save(newAgree);
            }
            else {
                // UPDATE
                userClauseAgree.setMandAgreeYn(mandAgreeYn);
                userClauseAgree.setMaxClauseVer(agreementInfo.getExtraAgreementVersion());
                if(Strings.isNullOrEmpty(userClauseAgree.getClauseVer()))
                    userClauseAgree.setClauseVer(lastVer);
            }
        }
    }

    @Override
    public CreateRealNameRes regRealName(SacRequestHeader sacHeader, CreateRealNameReq req) {

        /** 회원 정보 조회. (실명 인증정보) */
        try{
            List<KeySearch> keySearchList = new ArrayList<KeySearch>();
            KeySearch keySchUserKey = new KeySearch();
            keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
            keySchUserKey.setKeyString(req.getUserKey());
            keySearchList.add(keySchUserKey);
            SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
            CommonRequest commonRequest = new CommonRequest();
            searchExtentUserRequest.setCommonRequest(commonRequest);
            searchExtentUserRequest.setKeySearchList(keySearchList);
            searchExtentUserRequest.setUserInfoYn(MemberConstants.USE_Y);
            SearchExtentUserResponse res = this.userSCI.searchExtentUser(searchExtentUserRequest);

            /**
             * 변경 가능 상테 체크 (매인, 서브상태 정상인 회원)
             */
            if(!StringUtils.equalsIgnoreCase(res.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_NORMAL)
                    || !StringUtils.equalsIgnoreCase(res.getUserMbr().getUserSubStatus(), MemberConstants.SUB_STATUS_NORMAL)){
                throw new StorePlatformException("SAC_MEM_2001", res.getUserMbr().getUserMainStatus(),
                        res.getUserMbr().getUserSubStatus());
            }
        } catch (StorePlatformException ex) {
            if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
                    || StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
                throw new StorePlatformException("SAC_MEM_0003", "userKey", req.getUserKey());
            }else{
                throw ex;
            }
        }

        /**
         * SC 실명인증 연동.
         */
        String userKey = this.modRealName(sacHeader, req);

        /**
         * 결과 정보 setting.
         */
        CreateRealNameRes response = new CreateRealNameRes();
        response.setUserKey(userKey);

        return response;
    }

    /**
     * <pre>
     * 사용자 기본정보 setting..
     * </pre>
     *
     * @param req
     *            ModifyReq
     * @return UserMbr
     */
    private UserMbr getUserMbr(ModifyReq req) {

        UserMbr userMbr = new UserMbr();
        userMbr.setUserKey(req.getUserKey());

        /**
         * 이메일 수신여부
         */
        if (StringUtils.isNotBlank(req.getIsRecvEmail())) {
            userMbr.setIsRecvEmail(req.getIsRecvEmail());
        }

        /**
         * 사용자 성별 (Sync 대상)
         */
        if (StringUtils.isNotBlank(req.getUserSex())) {
            userMbr.setUserSex(req.getUserSex());
        }

        /**
         * 사용자 생년월일 (Sync 대상)
         */
        if (StringUtils.isNotBlank(req.getUserBirthDay())) {
            userMbr.setUserBirthDay(req.getUserBirthDay());
        }

        /**
         * 사용자 업데이트 이메일.
         */
        if (StringUtils.isNotBlank(req.getUserUpdEmail())) {
            userMbr.setUserUpdEmail(req.getUserUpdEmail());
        }

        LOGGER.debug("## SC Request 사용자 기본정보 : {}", userMbr);

        return userMbr;
    }

    /**
     * <pre>
     * 실명인증 연동 처리.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     * @return String (userKey)
     */
    private String modRealName(SacRequestHeader sacHeader, CreateRealNameReq req) {

        /**
         * 실명인증 대상 여부에 따라 분기 처리. (OWN=본인, PARENT=법정대리인, CORP=법인)
         */
        if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_OWN)) {

            /**
             * 실명인증 기본 setting.
             */
            UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
            updateRealNameRequest = new UpdateRealNameRequest();
            updateRealNameRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
            updateRealNameRequest.setIsOwn(req.getIsOwn());
            updateRealNameRequest.setIsRealName(MemberConstants.USE_Y);
            updateRealNameRequest.setUserKey(req.getUserKey());

            /**
             * 실명인증 (본인)
             */
            MbrAuth mbrAuth = new MbrAuth();
            mbrAuth.setIsRealName(MemberConstants.USE_Y); // 실명인증 여부
            mbrAuth.setBirthDay(req.getUserBirthDay()); // 생년월일
            mbrAuth.setTelecom(req.getDeviceTelecom()); // 이동 통신사
            mbrAuth.setPhone(req.getUserPhone()); // 사용자 휴대폰
            mbrAuth.setName(req.getUserName()); // 사용자 이름
            mbrAuth.setSex(req.getUserSex()); // 사용자 성별
            mbrAuth.setCi(req.getUserCi()); // CI
            mbrAuth.setDi(req.getUserDi()); // DI
            mbrAuth.setRealNameSite(sacHeader.getTenantHeader().getSystemId()); // 실명인증 사이트 코드
            mbrAuth.setRealNameDate(req.getRealNameDate()); // 실명인증 일시
            mbrAuth.setRealNameMethod(req.getRealNameMethod()); // 실명인증 수단코드
            mbrAuth.setIsDomestic(this.convertIsDomestic(req.getResident())); // 내외국인 구분 (Y : 내국인, N : 외국인)
            updateRealNameRequest.setUserMbrAuth(mbrAuth);

            /**
             * SC 실명인증정보 수정 연동.
             */
            UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
            if (updateRealNameResponse.getUserKey() == null
                    || StringUtils.equals(updateRealNameResponse.getUserKey(), "")) {
                throw new StorePlatformException("SAC_MEM_0002", "userKey");
            }

            return updateRealNameResponse.getUserKey();

        } else if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_PARENT)) { // 법정대리인

            if (StringUtils.isBlank(req.getUserBirthDay())) {
                throw new StorePlatformException("SAC_MEM_0002", "userBirthDay");
            }

            if (StringUtils.isBlank(req.getParentBirthDay())) {
                throw new StorePlatformException("SAC_MEM_0002", "parentBirthDay");
            }

            /**
             * 법정대리인 나이 유효성 체크.
             */
            this.mcc.checkParentBirth(req.getUserBirthDay(), req.getParentBirthDay());

            /**
             * 실명인증 기본 setting.
             */
            UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
            updateRealNameRequest = new UpdateRealNameRequest();
            updateRealNameRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
            updateRealNameRequest.setIsOwn(req.getIsOwn());
            updateRealNameRequest.setIsRealName(MemberConstants.USE_Y);
            updateRealNameRequest.setUserKey(req.getUserKey());

            /**
             * 실명인증 (법정대리인)
             */
            MbrLglAgent mbrLglAgent = new MbrLglAgent();
            mbrLglAgent.setIsParent(MemberConstants.USE_Y); // 법정대리인 여부
            mbrLglAgent.setParentBirthDay(req.getParentBirthDay()); // 법정대리인 생년월일
            mbrLglAgent.setParentMDN(req.getUserPhone()); // 법정대리인 전화번호
            mbrLglAgent.setParentTelecom(req.getDeviceTelecom()); // 법정대리인 이동 통신사
            mbrLglAgent.setParentCI(req.getUserCi()); // 법정대리인 CI
            mbrLglAgent.setParentName(req.getUserName()); // 법정대리인 이름
            mbrLglAgent.setParentType(req.getParentType()); // 법정대리인 관계코드
            mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 이메일
            mbrLglAgent.setParentDate(req.getRealNameDate()); // 법정대리인 실명인증 일시
            mbrLglAgent.setParentRealNameSite(sacHeader.getTenantHeader().getSystemId()); // 법정대리인 실명인증 사이트 코드
            mbrLglAgent.setParentRealNameMethod(req.getRealNameMethod()); // 법정대리인 실명인증 수단코드
            mbrLglAgent.setIsDomestic(this.convertIsDomestic(req.getResident())); // 내외국인 구분 (Y : 내국인, N : 외국인)
            updateRealNameRequest.setMbrLglAgent(mbrLglAgent);

            /**
             * SC 실명인증정보 수정 연동.
             */
            UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
            if (updateRealNameResponse.getUserKey() == null
                    || StringUtils.equals(updateRealNameResponse.getUserKey(), "")) {
                throw new StorePlatformException("SAC_MEM_0002", "userKey");
            }

            return updateRealNameResponse.getUserKey();

        } else { // 법인

            /**
             * 실명인증 기본 setting.
             */
            UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
            updateRealNameRequest = new UpdateRealNameRequest();
            updateRealNameRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
            updateRealNameRequest.setIsOwn(MemberConstants.AUTH_TYPE_OWN);
            updateRealNameRequest.setIsRealName(MemberConstants.USE_Y);
            updateRealNameRequest.setUserKey(req.getUserKey());

            /**
             * 실명인증 (본인)
             */
            MbrAuth mbrAuth = new MbrAuth();
            mbrAuth.setIsRealName(MemberConstants.USE_Y); // 실명인증 여부
            mbrAuth.setCi(" "); // CI 값은 필수인데... ' ' 공백으로 넣기로 SC와 협의함.
            mbrAuth.setBirthDay(req.getUserBirthDay()); // 생년월일
            mbrAuth.setIsDomestic(this.convertIsDomestic(req.getResident())); // 내외국인 구분 (Y : 내국인, N : 외국인)
            mbrAuth.setSex(req.getUserSex()); // 사용자 성별
            updateRealNameRequest.setUserMbrAuth(mbrAuth);

            /**
             * SC 실명인증정보 수정 연동.
             */
            UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
            if (updateRealNameResponse.getUserKey() == null
                    || StringUtils.equals(updateRealNameResponse.getUserKey(), "")) {
                throw new StorePlatformException("SAC_MEM_0002", "userKey");
            }

            return updateRealNameResponse.getUserKey();

        }

    }

    /**
     * <pre>
     * 약관동의 정보를 등록 또는 수정하는 기능을 제공한다.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param userKey
     *            사용자 Key
     * @param agreementList
     *            약관 동의 정보 리스트
     */
    private void modAgreement(SacRequestHeader sacHeader, String userKey, List<AgreementInfo> agreementList,
                              String methodType) {

        /**
         * 회원정보조회
         */
        DetailReq detailReq = new DetailReq();
        detailReq.setUserKey(userKey);
        SearchExtentReq searchExtent = new SearchExtentReq();
        searchExtent.setUserInfoYn(MemberConstants.USE_Y);

        if (MemberConstants.TYPE_MODIFY.equals(methodType)) {
            searchExtent.setAgreementInfoYn(MemberConstants.USE_Y);
        }

        detailReq.setSearchExtent(searchExtent);
        DetailV2Res detailRes = this.userSearchService.detailV2(sacHeader, detailReq);

        // 약관 수정일 경우 약관버전이 없을 때(조건) 기존에 등록된 약관과 파라미터로 입력된 약관 리스트를 비교해서
        // 기존에 등록된 약관코드 일 경우 약관 버전을 기존에 등록된 약관 버전으로 사용.
        // 수정에서 기존에 등록하지 않고 신규로 입력된 약관은 아래의 약관 맵핑정보 셋팅에서 최종 유효한 버전으로 맵핑 함.
        if (MemberConstants.TYPE_MODIFY.equals(methodType)) {
            List<Agreement> userCurrentAgreementList = detailRes.getAgreementList();

            // DB에 약관 정보가 있을 경우.
            if (userCurrentAgreementList != null && userCurrentAgreementList.size() > 0) {

                for (AgreementInfo agreementInfo : agreementList) {
                    for (Agreement userCurrentAgreement : userCurrentAgreementList) {

                        if (StringUtils.isNotBlank(agreementInfo.getExtraAgreementId())
                                && StringUtils.isNotBlank(userCurrentAgreement.getExtraAgreementId())) {

                            if (agreementInfo.getExtraAgreementId().equals(userCurrentAgreement.getExtraAgreementId())) {

                                // 수정으로 넘어온 약관의 버전 정보가 없을 경우, DB의 약관 버전 정보를 셋팅.
                                if (StringUtils.isBlank(agreementInfo.getExtraAgreementVersion())) {

                                    if (StringUtils.isNotBlank(userCurrentAgreement.getExtraAgreementVersion())) {
                                        agreementInfo.setExtraAgreementVersion(userCurrentAgreement
                                                .getExtraAgreementVersion());
                                    }

                                } else {
                                    // 수정으로 넘어온 약관의 버전 정보가 있을 경우, DB의 약관 버전 정보와 비교하여 높은 버전으로 유지.
                                    try {
                                        if (StringUtils.isNotBlank(userCurrentAgreement.getExtraAgreementVersion())
                                                && StringUtils.isNotBlank(agreementInfo.getExtraAgreementVersion())) {
                                            if (Double.parseDouble(userCurrentAgreement.getExtraAgreementVersion()) > Double
                                                    .parseDouble(agreementInfo.getExtraAgreementVersion())) {
                                                agreementInfo.setExtraAgreementVersion(userCurrentAgreement
                                                        .getExtraAgreementVersion());
                                            }
                                        }
                                    } catch (Exception ex) {
                                        // 약관 버전 정보가 Double 타입이 아닌경우. 로그 출력만하고 pass
                                        LOGGER.info("약관 버전이 Double 형식이 아닙니다. [{}]", ex);
                                    }
                                }
                                break;

                            }

                        }

                    }
                }

            }
        }

        /**
         * 약관 맵핑정보 세팅.
         */
        agreementList = this.mcc.getClauseMappingInfo(agreementList);

        UpdateAgreementRequest updateAgreementRequest = new UpdateAgreementRequest();
        updateAgreementRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
        updateAgreementRequest.setUserKey(userKey);

        /**
         * 약관 동의 리스트 setting.
         */
        List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
        for (AgreementInfo info : agreementList) {
            MbrClauseAgree mbrClauseAgree = new MbrClauseAgree();
            mbrClauseAgree.setExtraAgreementID(info.getExtraAgreementId());
            mbrClauseAgree.setExtraAgreementVersion(info.getExtraAgreementVersion());
            mbrClauseAgree.setIsExtraAgreement(info.getIsExtraAgreement());
            mbrClauseAgree.setIsMandatory(info.getMandAgreeYn());
            mbrClauseAgree.setRegDate(DateUtil.getToday("yyyyMMddHHmmss"));
            mbrClauseAgreeList.add(mbrClauseAgree);
        }
        updateAgreementRequest.setMbrClauseAgreeList(mbrClauseAgreeList);
        updateAgreementRequest.setIsDormant(detailRes.getUserInfo().getIsDormant());
        /**
         * SC 약관동의 정보를 등록 또는 수정 연동.
         */
        this.userSCI.updateAgreement(updateAgreementRequest);

    }

    /**
     * <pre>
     * SC에 사용하는 정보로 내외국인 여부 Converting.
     * </pre>
     *
     * @param resident
     *            실명인증 회원코드 (local : 내국인, foreign : 외국인)
     * @return 내외국인 여부 (Y or N)
     */
    private String convertIsDomestic(String resident) {
        if (StringUtils.equals(resident, "local")) {
            return MemberConstants.USE_Y; // 내국인
        } else if (StringUtils.equals(resident, "foreign")) {
            return MemberConstants.USE_N; // 외국인
        } else {
            return "";
        }
    }

    /**
     * <pre>
     * SC 회원 비밀번호 관련 필드 변경 요청.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     */
    private void modPasswordUser(SacRequestHeader sacHeader, ModifyPasswordReq req, String isDormant) {

        UpdatePasswordUserRequest updatePasswordUserRequest = new UpdatePasswordUserRequest();
        updatePasswordUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

        MbrPwd mbrPwd = new MbrPwd();
        mbrPwd.setMemberKey(req.getUserKey()); // 사용자 Key
        mbrPwd.setOldPW(req.getOldPassword()); // 기존 패스워드
        mbrPwd.setMemberPW(req.getNewPassword()); // 신규 패스워드
        mbrPwd.setPwRegDate(DateUtil.getToday("yyyyMMddHHmmss")); // 비밀번호 변경 일시
        updatePasswordUserRequest.setMbrPwd(mbrPwd);
        updatePasswordUserRequest.setIsDormant(isDormant);

        /**
         * SC 회원 비밀번호 변경 요청.
         */
        this.userSCI.updatePasswordUser(updatePasswordUserRequest);

    }

    /**
     * <pre>
     * 성인 인증 정보 초기화.
     * </pre>
     *
     * @param sacHeader
     *            공통 헤더
     * @param req
     *            Request Value Object
     * @return Response Value Object
     */
    @Override
    public InitRealNameRes initRealName(SacRequestHeader sacHeader, InitRealNameReq req) {
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

        /**
         * 회원 정보 조회 - 성인 인증 정보 조회
         */
        List<KeySearch> keySearchList = new ArrayList<KeySearch>();
        KeySearch keySchUserKey = new KeySearch();
        keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
        keySchUserKey.setKeyString(req.getUserKey());
        keySearchList.add(keySchUserKey);
        SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
        CommonRequest commonRequest = new CommonRequest();
        searchExtentUserRequest.setCommonRequest(commonRequest);
        searchExtentUserRequest.setKeySearchList(keySearchList);
        searchExtentUserRequest.setMbrAuthInfoYn(MemberConstants.USE_Y);
        SearchExtentUserResponse detailRes = this.userSCI.searchExtentUser(searchExtentUserRequest);

        /**
         * 성인인증 정보가 존재하지 않을 경우
         */
        if (StringUtil.equals(detailRes.getMbrAuth().getIsRealName(), Constant.TYPE_YN_N)) {
            throw new StorePlatformException("SAC_MEM_0002", "성인 인증");
        }

        /**
         * 성인인증 초기화 기본 req setting.
         */
        UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
        updateRealNameRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
        updateRealNameRequest.setUserKey(req.getUserKey());

        /**
         * SC 사용자 회원 실명인증 초기화.
         */
        UpdateRealNameResponse updateRealNameResponse = this.userSCI.initRealName(updateRealNameRequest);

        InitRealNameRes res = new InitRealNameRes();
        res.setUserKey(updateRealNameResponse.getUserKey());

        return res;
    }

    /**
     * <pre>
     * 2.1.56. 소셜 계정 등록/수정.
     * </pre>
     *
     * @param header
     *            SacRequestHeader
     * @param req
     *            CreateSocialAccountSacReq
     * @return CreateSocialAccountSacRes
     */
    @Override
    @Deprecated
    public CreateSocialAccountSacRes regSocialAccount(SacRequestHeader header, CreateSocialAccountSacReq req) {

        CommonRequest commonRequest = this.mcc.getSCCommonRequest(header);

        // 1.회원 정보 조회
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.setCommonRequest(commonRequest);
        DetailReq detailReq = new DetailReq();
        detailReq.setUserKey(req.getUserKey());
        SearchExtentReq searchExtent = new SearchExtentReq();
        searchExtent.setUserInfoYn(MemberConstants.USE_Y);
        detailReq.setSearchExtent(searchExtent);
        DetailV2Res detailRes = this.userSearchService.detailV2(header, detailReq);

        List<MbrMangItemPtcr> remPtcrList = null;
        List<UserExtraInfo> userExtraInfos = null;
        MbrMangItemPtcr mbrMangItemPtcr = null;

        if (detailRes.getUserInfo() != null && detailRes.getUserInfo().getUserExtraInfoList() != null) {
            userExtraInfos = detailRes.getUserInfo().getUserExtraInfoList();
            remPtcrList = new ArrayList<MbrMangItemPtcr>();
            for (UserExtraInfo userExtraInfo : userExtraInfos) {
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_ID, userExtraInfo.getExtraProfile())) {
                    if (StringUtils.isBlank(req.getSocialAcctId())) {
                        mbrMangItemPtcr = new MbrMangItemPtcr();
                        mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_ID);
                        remPtcrList.add(mbrMangItemPtcr);
                    }
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID, userExtraInfo.getExtraProfile())) {
                    if (StringUtils.isBlank(req.getSocialAcctIntId())) {
                        mbrMangItemPtcr = new MbrMangItemPtcr();
                        mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID);
                        remPtcrList.add(mbrMangItemPtcr);
                    }
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TOKEN, userExtraInfo.getExtraProfile())) {
                    if (StringUtils.isBlank(req.getSocialAcctToken())) {
                        mbrMangItemPtcr = new MbrMangItemPtcr();
                        mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TOKEN);
                        remPtcrList.add(mbrMangItemPtcr);
                    }
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE, userExtraInfo.getExtraProfile())) {
                    if (StringUtils.isBlank(req.getSocialAcctType())) {
                        mbrMangItemPtcr = new MbrMangItemPtcr();
                        mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE);
                        remPtcrList.add(mbrMangItemPtcr);
                    }
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_EMAIL, userExtraInfo.getExtraProfile())) {
                    if (StringUtils.isBlank(req.getSocialEmail())) {
                        mbrMangItemPtcr = new MbrMangItemPtcr();
                        mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_EMAIL);
                        remPtcrList.add(mbrMangItemPtcr);
                    }
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_EXPIRED_TIME, userExtraInfo.getExtraProfile())) {
                    if (StringUtils.isBlank(req.getSocialExpiredTime())) {
                        mbrMangItemPtcr = new MbrMangItemPtcr();
                        mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_EXPIRED_TIME);
                        remPtcrList.add(mbrMangItemPtcr);
                    }
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_REF_TOKEN, userExtraInfo.getExtraProfile())) {
                    if (StringUtils.isBlank(req.getSocialRefToken())) {
                        mbrMangItemPtcr = new MbrMangItemPtcr();
                        mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_REF_TOKEN);
                        remPtcrList.add(mbrMangItemPtcr);
                    }
                }
            }
        }

        // 2. 회원 부가속성 삭제 (토큰,아이디 삭제)
        RemoveManagementRequest removeManagementRequest = new RemoveManagementRequest();
        removeManagementRequest.setCommonRequest(commonRequest);
        removeManagementRequest.setUserKey(req.getUserKey());
        removeManagementRequest.setMbrMangItemPtcr(remPtcrList);
//        this.userSCI.removeManagement(removeManagementRequest);

        // 3.부가속성 등록/수정
        UpdateManagementRequest updateManagementRequest = new UpdateManagementRequest();
        List<MbrMangItemPtcr> ptcrList = new ArrayList<MbrMangItemPtcr>();
        MbrMangItemPtcr ptcr = null;

        ptcr = new MbrMangItemPtcr();
        ptcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE);
        ptcr.setExtraProfileValue(req.getSocialAcctType());
        ptcrList.add(ptcr);
        if (StringUtils.isNotBlank(req.getSocialAcctId())) {
            ptcr = new MbrMangItemPtcr();
            ptcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_ID);
            ptcr.setExtraProfileValue(req.getSocialAcctId());
            ptcrList.add(ptcr);
        }
        ptcr = new MbrMangItemPtcr();
        ptcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID);
        ptcr.setExtraProfileValue(req.getSocialAcctIntId());
        ptcrList.add(ptcr);
        if (StringUtils.isNotBlank(req.getSocialEmail())) {
            ptcr = new MbrMangItemPtcr();
            ptcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_EMAIL);
            ptcr.setExtraProfileValue(req.getSocialEmail());
            ptcrList.add(ptcr);
        }
        ptcr = new MbrMangItemPtcr();
        ptcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TOKEN);
        ptcr.setExtraProfileValue(req.getSocialAcctToken());
        ptcrList.add(ptcr);
        if (StringUtils.isNotBlank(req.getSocialRefToken())) {
            ptcr = new MbrMangItemPtcr();
            ptcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_REF_TOKEN);
            ptcr.setExtraProfileValue(req.getSocialRefToken());
            ptcrList.add(ptcr);
        }
        if (StringUtils.isNotBlank(req.getSocialExpiredTime())) {
            ptcr = new MbrMangItemPtcr();
            ptcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_EXPIRED_TIME);
            ptcr.setExtraProfileValue(req.getSocialExpiredTime());
            ptcrList.add(ptcr);
        }

        updateManagementRequest.setUserKey(req.getUserKey());
        updateManagementRequest.setMbrMangItemPtcr(ptcrList);
        updateManagementRequest.setCommonRequest(commonRequest);
//        this.userSCI.updateManagement(updateManagementRequest);

        // 4.소셜이력 등록
        CreateSocialAccountRequest createSocialAccountRequest = new CreateSocialAccountRequest();
        createSocialAccountRequest.setCommonRequest(commonRequest);
        createSocialAccountRequest.setSocialAcctIntId(req.getSocialAcctIntId());
        createSocialAccountRequest.setSocialAcctType(req.getSocialAcctType());
        createSocialAccountRequest.setUserKey(req.getUserKey());

        CreateSocialAccountResponse createSocialAccountResponse = this.userSCI
                .createSocialAccount(createSocialAccountRequest);

        // 5. 응답값 셋팅
        CreateSocialAccountSacRes res = new CreateSocialAccountSacRes();
        res.setUserKey(createSocialAccountResponse.getUserKey());

        return res;
    }

    /**
     * <pre>
     * 2.1.57. 소셜 계정 삭제.
     * </pre>
     *
     * @param header
     *            SacRequestHeader
     * @param req
     *            RemoveSocialAccountSacReq
     * @return RemoveSocialAccountSacRes
     */
    @Override
    public RemoveSocialAccountSacRes removeSocialAccount(SacRequestHeader header, RemoveSocialAccountSacReq req) {

        CommonRequest commonRequest = this.mcc.getSCCommonRequest(header);

        // 1.회원 정보 조회
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.setCommonRequest(commonRequest);
        DetailReq detailReq = new DetailReq();
        detailReq.setUserKey(req.getUserKey());
        SearchExtentReq searchExtent = new SearchExtentReq();
        searchExtent.setUserInfoYn(MemberConstants.USE_Y);
        detailReq.setSearchExtent(searchExtent);
        DetailV2Res detailRes = this.userSearchService.detailV2(header, detailReq);

        List<MbrMangItemPtcr> ptcrList = null;
        List<UserExtraInfo> userExtraInfos = null;
        MbrMangItemPtcr mbrMangItemPtcr = null;

        if (detailRes.getUserInfo() != null && detailRes.getUserInfo().getUserExtraInfoList() != null) {
            userExtraInfos = detailRes.getUserInfo().getUserExtraInfoList();
            ptcrList = new ArrayList<MbrMangItemPtcr>();
            for (UserExtraInfo userExtraInfo : userExtraInfos) {
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_ID, userExtraInfo.getExtraProfile())) {
                    mbrMangItemPtcr = new MbrMangItemPtcr();
                    mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_ID);
                    ptcrList.add(mbrMangItemPtcr);
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID, userExtraInfo.getExtraProfile())) {
                    mbrMangItemPtcr = new MbrMangItemPtcr();
                    mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID);
                    ptcrList.add(mbrMangItemPtcr);
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TOKEN, userExtraInfo.getExtraProfile())) {
                    mbrMangItemPtcr = new MbrMangItemPtcr();
                    mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TOKEN);
                    ptcrList.add(mbrMangItemPtcr);
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE, userExtraInfo.getExtraProfile())) {
                    mbrMangItemPtcr = new MbrMangItemPtcr();
                    mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE);
                    ptcrList.add(mbrMangItemPtcr);
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_EMAIL, userExtraInfo.getExtraProfile())) {
                    mbrMangItemPtcr = new MbrMangItemPtcr();
                    mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_EMAIL);
                    ptcrList.add(mbrMangItemPtcr);
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_EXPIRED_TIME, userExtraInfo.getExtraProfile())) {
                    mbrMangItemPtcr = new MbrMangItemPtcr();
                    mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_EXPIRED_TIME);
                    ptcrList.add(mbrMangItemPtcr);
                }
                if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_REF_TOKEN, userExtraInfo.getExtraProfile())) {
                    mbrMangItemPtcr = new MbrMangItemPtcr();
                    mbrMangItemPtcr.setExtraProfile(MemberConstants.USER_EXTRA_SOCIAL_REF_TOKEN);
                    ptcrList.add(mbrMangItemPtcr);
                }
            }
        }

        // 2. 회원 부가속성 삭제 (토큰,아이디 삭제)
        RemoveManagementRequest removeManagementRequest = new RemoveManagementRequest();
        removeManagementRequest.setCommonRequest(commonRequest);

        removeManagementRequest.setUserKey(req.getUserKey());
        removeManagementRequest.setMbrMangItemPtcr(ptcrList);
        RemoveManagementResponse removeManagementResponse = this.userSCI.removeManagement(removeManagementRequest);

        // 3. 응답 셋팅
        RemoveSocialAccountSacRes res = new RemoveSocialAccountSacRes();
        res.setUserKey(removeManagementResponse.getUserKey());

        return res;
    }

    /**
     * <pre>
     * 2.1.62.	배송지 정보 등록/수정.
     * </pre>
     *
     * @param sacHeader
     *            SacRequestHeader
     * @param req
     *            CreateDeliveryInfoSacReq
     * @return CreateDeliveryInfoSacRes
     */
    @Override
    public CreateDeliveryInfoSacRes createDeliveryInfo(SacRequestHeader sacHeader, CreateDeliveryInfoSacReq req) {

        CreateDeliveryInfoSacRes res = new CreateDeliveryInfoSacRes();

        // 배송지 등록/수정 공통 셋팅
        CreateDeliveryInfoRequest scReq = new CreateDeliveryInfoRequest();
        scReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
        scReq.setUserKey(req.getUserKey());
        scReq.setDeliveryTypeCd(req.getDeliveryTypeCd());
        scReq.setDeliveryNm(req.getDeliveryNm());
        scReq.setReceiverNm(req.getReceiverNm());
        scReq.setSenderNm(req.getSenderNm());
        scReq.setZip(req.getZip());
        scReq.setAddr(req.getAddr());
        scReq.setDtlAddr(req.getDtlAddr());
        scReq.setConnTelNo(req.getConnTelNo());
        scReq.setDeliveryMsg(req.getDeliveryMsg());
        // 배송지 시퀀스가 있으면 셋팅
        if (!StringUtils.isBlank(req.getDeliverySeq())) {
            scReq.setDeliverySeq(req.getDeliverySeq());
        }

        try {
            // 등록 및 수정 진행
            this.userSCI.createDeliveryInfo(scReq);
            res.setUserKey(req.getUserKey());
        } catch (StorePlatformException spe) {
            LOGGER.info("배송지 정보 등록/수정 실패 [{}]", req.getUserKey());
            throw spe;
        }

        return res;

    }

    /**
     * <pre>
     * 2.1.63.	배송지 정보 삭제.
     * </pre>
     *
     * @param sacHeader
     *            SacRequestHeader
     * @param req
     *            RemoveDeliveryInfoSacReq
     * @return RemoveDeliveryInfoSacRes
     */
    @Override
    public RemoveDeliveryInfoSacRes removeDeliveryInfo(SacRequestHeader sacHeader, RemoveDeliveryInfoSacReq req) {

        RemoveDeliveryInfoSacRes res = new RemoveDeliveryInfoSacRes();

        try {
            // 배송지 삭제 셋팅
            RemoveDeliveryInfoRequest scReq = new RemoveDeliveryInfoRequest();
            scReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
            scReq.setUserKey(req.getUserKey());
            scReq.setDeliverySeq(req.getDeliverySeq());
            // 삭제 진행
            RemoveDeliveryInfoResponse scRes = this.userSCI.removeDeliveryInfo(scReq);
            res.setUserKey(scRes.getUserKey());
        } catch (StorePlatformException spe) {
            LOGGER.info("배송지 삭제 실패 [{}]", req.getUserKey());
            throw spe;
        }

        return res;

    }

    /**
     * <pre>
     * 2.1.68. ID 변경 [신규 규격].
     * </pre>
     *
     * @param sacHeader
     *            SacRequestHeader
     * @param req
     *            ModifyIdSacReq
     * @return ModifyIdSacRes
     */
    @Override
    public ModifyIdSacRes modifyId(SacRequestHeader sacHeader, ModifyIdSacReq req) {

        CommonRequest commonRequest = this.mcc.getSCCommonRequest(sacHeader);

        /** 1. 사용자 타입에 따른 오류 */
        if( StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_MOBILE)
                || StringUtils.equals(req.getUserType(), MemberConstants.USER_TYPE_ONEID)){
            throw new StorePlatformException("SAC_MEM_1403");
        }
        if( StringUtils.equals(req.getNewUserType(), MemberConstants.USER_TYPE_MOBILE)
                || StringUtils.equals(req.getNewUserType(), MemberConstants.USER_TYPE_IDPID)
                || StringUtils.equals(req.getNewUserType(), MemberConstants.USER_TYPE_ONEID)){
            throw new StorePlatformException("SAC_MEM_1403");
        }

        /** 2. 그외의 사용자면 회원 정보 조회. */
        List<KeySearch> keySearchList = new ArrayList<KeySearch>();
        KeySearch keySchUserKey = new KeySearch();

        try{
            keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
            keySchUserKey.setKeyString(req.getUserKey());
            keySearchList.add(keySchUserKey);
            SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
            searchExtentUserRequest.setCommonRequest(commonRequest);
            searchExtentUserRequest.setKeySearchList(keySearchList);
            searchExtentUserRequest.setUserInfoYn(MemberConstants.USE_Y);
            SearchExtentUserResponse searchUserRes = this.userSCI.searchExtentUser(searchExtentUserRequest);

            /** 2-1. 변경 가능 상테(매인, 서브상태 정상인 회원)가 아니면 오류. */
            if(!StringUtils.equalsIgnoreCase(searchUserRes.getUserMbr().getUserMainStatus(), MemberConstants.MAIN_STATUS_NORMAL)
                    || !StringUtils.equalsIgnoreCase(searchUserRes.getUserMbr().getUserSubStatus(), MemberConstants.SUB_STATUS_NORMAL)){
                throw new StorePlatformException("SAC_MEM_2001", searchUserRes.getUserMbr().getUserMainStatus(),
                        searchUserRes.getUserMbr().getUserSubStatus());
            }
        } catch (StorePlatformException ex) {
            /** 2-2. 아이디가 없거나 회원 정보 조회시 exception 오류. */
            if (StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
                    || StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
                throw new StorePlatformException("SAC_MEM_0003", "userKey", req.getUserKey());
            }else{
                throw ex;
            }
        }

        /** 3. userId, userAuthToken 로 인증시도 실패시 오류.  */
        CheckUserAuthTokenRequest chkUserAuthTkReq = new CheckUserAuthTokenRequest();
        chkUserAuthTkReq.setCommonRequest(commonRequest);
        chkUserAuthTkReq.setUserKey(req.getUserKey());
        chkUserAuthTkReq.setUserAuthToken(req.getUserAuthToken());
        CheckUserAuthTokenResponse chkUserAuthTkRes =  this.userSCI.checkUserAuthToken(chkUserAuthTkReq);

        if( chkUserAuthTkRes.getUserKey() == null || chkUserAuthTkRes.getUserKey().length() <= 0 ){
            throw new StorePlatformException("SAC_MEM_1204");
        }

        /** 4. userAuthToken 인증이 되었으면 ID변경 */
        ModifyIdSacRes res = new ModifyIdSacRes();

        try {
            // ID 변경 셋팅
            ModifyIdRequest scReq = new ModifyIdRequest();
            scReq.setCommonRequest(commonRequest);
            scReq.setUserKey(req.getUserKey());
            scReq.setUserId(req.getUserId());
            scReq.setUserType(req.getUserType());
            scReq.setUserAuthToken(req.getUserAuthToken());
            scReq.setNewUserId(req.getNewUserId());
            scReq.setNewUserType(req.getNewUserType());
            scReq.setNewUserAuthToken(req.getNewUserAuthToken());

            // ID 변경 진행
            ModifyIdResponse scRes = this.userSCI.modifyId(scReq);
            res.setUserKey(scRes.getUserKey());
        } catch (StorePlatformException spe) {
            LOGGER.info("ID 변경 실패 [{}]", req.getUserKey());
            throw spe;
        }

        return res;

    }

}
