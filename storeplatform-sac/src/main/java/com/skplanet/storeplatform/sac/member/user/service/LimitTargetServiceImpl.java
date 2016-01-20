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
import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyValueRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyValueResponse;
import com.skplanet.storeplatform.sac.member.domain.mbr.UserLimitTarget;
import com.skplanet.storeplatform.sac.member.repository.UserLimitTargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 서비스 제한 정책
 * </p>
 * Updated on : 2015. 1. 7. Updated by : 임근대, SKP.
 */
@Service
@Transactional("transactionManagerForScMember")
public class LimitTargetServiceImpl implements LimitTargetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LimitTargetServiceImpl.class);

    @Autowired
    UserLimitTargetRepository limitTargetRepository;

    /** The message source accessor. */
    @Autowired
    @Qualifier("scMember")
    private MessageSourceAccessor messageSourceAccessor;

    /**
     * <pre>
     *  메시지 프로퍼티에서 메시지 참조.
     * </pre>
     *
     * @param code
     *            fail 코드
     * @param fail
     *            에러메세지
     * @return String 결과 메세지
     */
    private String getMessage(String code, String fail) {
        String msg = this.messageSourceAccessor.getMessage(code, null, fail, LocaleContextHolder.getLocale());
        LOGGER.debug(msg);
        return msg;
    }


    /**
     * <pre>
     * 에러 메시지.
     * </pre>
     *
     * @param resultCode
     *            에러코드
     * @param resultMessage
     *            에러메세지
     * @return CommonResponse 결과 메세지
     */
    private CommonResponse getErrorResponse(String resultCode, String resultMessage) {
        CommonResponse commonResponse;
        commonResponse = new CommonResponse();
        commonResponse.setResultCode(this.getMessage(resultCode, ""));
        commonResponse.setResultMessage(this.getMessage(resultMessage, ""));
        return commonResponse;
    }


    @Override
    public List<LimitTarget> searchPolicyList(String limtPolicyKey, List<String> limtPolicyCdList) {
        List<UserLimitTarget> userLimitTargets = limitTargetRepository.findByLimitPolicyKeyAndLimtPolicyCdIn(limtPolicyKey, limtPolicyCdList);

        List<LimitTarget> convertedLimitTargetList = new ArrayList<LimitTarget>();
        for (UserLimitTarget userLimitTarget : userLimitTargets) {
            convertedLimitTargetList.add(userLimitTarget.convertToVo());
        }

        return convertedLimitTargetList;
    }

	/**
	 * <pre>
	 * 제한 정책 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param searchPolicyRequest
	 *            제한 정책 목록 조회 요청 Value Object
	 * @return searchPolicyResponse - 제한 정책 목록 조회 응답 Value Object
	 */
	@Override
	public SearchPolicyResponse searchPolicyList(SearchPolicyRequest searchPolicyRequest) {
		LOGGER.debug("### searchPolicyRequest : {}", searchPolicyRequest);

		SearchPolicyResponse searchPolicyResponse = new SearchPolicyResponse();

		List<LimitTarget> limitTargetList = searchPolicyList(searchPolicyRequest.getLimitPolicyKey(), searchPolicyRequest.getLimitPolicyCodeList());

		if (limitTargetList == null || limitTargetList.size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchPolicyResponse.setLimitTargetList(limitTargetList);
		searchPolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success", "response.ResultMessage.success"));
		return searchPolicyResponse;
	}

    /**
     * <pre>
     * 제한 정책정보를 등록/수정하는 기능을 제공한다.
     * </pre>
     *
     * @param updatePolicyRequest 제한 정책정보 등록/수정 요청 Value Object
     * @return updatePolicyResponse - 제한 정책정보 등록/수정 응답 Value Object
     */
    @Override
    public UpdatePolicyResponse updatePolicy(UpdatePolicyRequest updatePolicyRequest) {
        LOGGER.debug("### updatePolicyRequest : {}", updatePolicyRequest);

        UpdatePolicyResponse updatePolicyResponse = new UpdatePolicyResponse();
        Integer row = 0;

        List<LimitTarget> limitTargetList = updatePolicyRequest.getLimitTargetList();
        List<LimitTarget> limitCodeList;
        limitCodeList = new ArrayList<LimitTarget>();

        for (int i = 0; i < limitTargetList.size(); i++) {
            LimitTarget limitTarget = limitTargetList.get(i);
            //limitTarget.setTenantID(updatePolicyRequest.getCommonRequest().getTenantID());

			/*
             * if (limitTarget.getLimitTargetNo() == null) { int seq =
			 * this.commonDAO.queryForObject("User.getLimitSequence", null, Integer.class); String tempKey =
			 * Integer.toString(seq); limitTarget.setLimitTargetNo(tempKey); }
			 */
            LOGGER.debug(">>>> >>> UserServiceImpl before updatePolicy : {}", limitTarget);
            //row = this.commonDAO.update("User.updatePolicy", limitTarget);
			row = saveLimitPolicy(limitTarget);

            LOGGER.debug("### row : {}", row);
            if (row <= 0) {
                throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
            }
            limitCodeList.add(limitTarget);
        }

        updatePolicyResponse.setLimitTargetList(limitCodeList);
        updatePolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success", "response.ResultMessage.success"));
        return updatePolicyResponse;
    }

    @Override
    public Integer saveLimitPolicy(LimitTarget limitTarget) {
        limitTargetRepository.saveLimitPolicy(UserLimitTarget.createUserLimitTargetFromVo(limitTarget));
        return 1;
    }

	/**
	 * <pre>
	 * 제한 정책정보 삭제하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param removePolicyRequest
	 *            제한 정책정보 삭제 요청 Value Object
	 * @return removePolicyResponse - 제한 정책정보 삭제 응답 Value Object
	 */
	@Override
	public RemovePolicyResponse removePolicy(RemovePolicyRequest removePolicyRequest) {
		LOGGER.debug("### removePolicy : {}", removePolicyRequest);

		RemovePolicyResponse removePolicyResponse = new RemovePolicyResponse();
		Integer row = 0;

		List<LimitTarget> limitTargetList = removePolicyRequest.getLimitTargetList();
		List<String> limitCodeList;
		limitCodeList = new ArrayList<String>();

		for (int i = 0; i < limitTargetList.size(); i++) {
			LimitTarget limitTargetNo = limitTargetList.get(i);
			//limitTargetNo.setTenantID(removePolicyRequest.getCommonRequest().getTenantID());

			LOGGER.debug(">>>> >>> UserServiceImpl before removePolicy : {}", limitTargetNo);
			//row = this.commonDAO.delete("User.removePolicy", limitTargetNo);
            row = limitTargetRepository.removePolicy(limitTargetNo.getLimitPolicyKey(), limitTargetNo.getLimitPolicyCode());
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}
			limitCodeList.add(limitTargetNo.getLimitPolicyCode());
		}

		removePolicyResponse.setLimitPolicyCodeList(limitCodeList);
		removePolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success", "response.ResultMessage.success"));
		return removePolicyResponse;
	}

	/**
	 * <pre>
	 * 제한 정책key 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param updatePolicyKeyRequest
	 *            제한 정책정보 key 수정 요청 Value Object
	 * @return updatePolicyKeyResponse - 제한 정책정보 key 수정 응답 Value Object
	 */
	@Override
	public UpdatePolicyKeyResponse updatePolicyKey(UpdatePolicyKeyRequest updatePolicyKeyRequest) {
		LOGGER.debug("### updatePolicyKeyRequest : {}", updatePolicyKeyRequest);

		UpdatePolicyKeyResponse updatePolicyKeyResponse = new UpdatePolicyKeyResponse();

		//Integer row = this.commonDAO.update("User.updatePolicyKey", updatePolicyKeyRequest);
        Integer row = limitTargetRepository.updateLimitPolicyKey(updatePolicyKeyRequest.getOldLimitPolicyKey(), updatePolicyKeyRequest.getNewLimitPolicyKey());

        LOGGER.debug("### row : {}", row);

		updatePolicyKeyResponse.setUpdateCount(row);
		updatePolicyKeyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success", "response.ResultMessage.success"));
		return updatePolicyKeyResponse;
	}

	/**
	 * <pre>
	 * 제한 정책 value 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param updatePolicyValueRequest
	 *            제한 정책정보 Value 수정 요청 Value Object
	 * @return updatePolicyValueResponse - 제한 정책정보 Value 수정 응답 Value Object
	 */
	@Override
	public UpdatePolicyValueResponse updatePolicyValue(UpdatePolicyValueRequest updatePolicyValueRequest) {
		LOGGER.debug("### updatePolicyValueRequest : {}", updatePolicyValueRequest);
		UpdatePolicyValueResponse updatePolicyValueResponse = new UpdatePolicyValueResponse();

        Integer row = limitTargetRepository.updatePolicyApplyValue(updatePolicyValueRequest.getOldApplyValue(), updatePolicyValueRequest.getNewApplyValue());
		//Integer row = this.commonDAO.update("User.updatePolicyValue", updatePolicyValueRequest);

		LOGGER.debug("### row : {}", row);

		updatePolicyValueResponse.setUpdateCount(row);

		updatePolicyValueResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success", "response.ResultMessage.success"));
		return updatePolicyValueResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.member.user.service.UserService#updatePolicyHistory(com.skplanet.storeplatform.member
	 * .client.common.vo.UpdatePolicyRequest)
	 */
	@Override
	public UpdatePolicyResponse updatePolicyHistory(UpdatePolicyRequest updatePolicyRequest) {
		UpdatePolicyResponse updatePolicyResponse = new UpdatePolicyResponse();
		Integer row = 0;

		List<LimitTarget> limitTargetList = updatePolicyRequest.getLimitTargetList();
		List<LimitTarget> limitCodeList;
		limitCodeList = new ArrayList<LimitTarget>();

		for (int i = 0; i < limitTargetList.size(); i++) {
			LimitTarget limitTarget = limitTargetList.get(i);
			//limitTarget.setTenantID(updatePolicyRequest.getCommonRequest().getTenantID());
			//row = this.commonDAO.update("User.updatePolicyHistory", limitTarget);
            row = limitTargetRepository.updateLimitPolicyHistory(Integer.parseInt(limitTarget.getLimitTargetNo()), limitTarget.getUpdateID());
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			limitCodeList.add(limitTarget);
		}

		updatePolicyResponse.setLimitTargetList(limitCodeList);
		updatePolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success", "response.ResultMessage.success"));
		return updatePolicyResponse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.member.user.service.UserService#insertPolicy(com.skplanet.storeplatform.member.client
	 * .common.vo.UpdatePolicyRequest)
	 */
	@Override
	public UpdatePolicyResponse insertPolicy(UpdatePolicyRequest updatePolicyRequest) {
		UpdatePolicyResponse updatePolicyResponse = new UpdatePolicyResponse();

		List<LimitTarget> limitTargetList = updatePolicyRequest.getLimitTargetList();
		List<LimitTarget> limitCodeList;
		limitCodeList = new ArrayList<LimitTarget>();

		for (int i = 0; i < limitTargetList.size(); i++) {
			Integer row = 0;
			LimitTarget limitTarget = limitTargetList.get(i);
			//limitTarget.setTenantID(updatePolicyRequest.getCommonRequest().getTenantID());
			//row = this.commonDAO.update("User.insertPolicy", limitTarget);
            limitTargetRepository.saveLimitPolicy(UserLimitTarget.createUserLimitTargetFromVo(limitTarget));
			row = 1;
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			limitCodeList.add(limitTarget);
		}

		updatePolicyResponse.setLimitTargetList(limitCodeList);
		updatePolicyResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success", "response.ResultMessage.success"));
		return updatePolicyResponse;
	}
}
