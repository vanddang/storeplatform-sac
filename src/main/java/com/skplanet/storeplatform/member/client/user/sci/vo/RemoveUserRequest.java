/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 사용자 탈퇴 요청을 위한 value object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class RemoveUserRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/** 사용자 Key. */
	private String userKey;

	/**
	 * <pre>
	 * 탈퇴유형 코드.
	 * 
	 * US010701 ID 전환
	 * US010702 사용자선택
	 * US010703 프로비저닝
	 * US010704 단말이동
	 * US010705 가입승인만료
	 * </pre>
	 * */
	private String secedeTypeCode;

	/**
	 * <pre>
	 * 탈퇴사유 코드.
	 * 
	 * US010401 ID 변경
	 * US010402 판매 성과 부진
	 * US010403 정산에 대한 불신
	 * US010404 장기간 부재 (군입대, 유학)
	 * US010405 마켓 변경
	 * US010406 시스템 장애
	 * US010407 개인정보 보안문제
	 * US010409 기타
	 * US010410 미방문
	 * US010411 서비스 이용 불편
	 * US010412 컨텐츠 부족
	 * </pre>
	 * */
	private String secedeReasonCode;

	/** 탈퇴사유 내용. */
	private String secedeReasonMessage;

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 설정한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 탈퇴유형 코드를 리턴한다.
	 * 
	 * @return secedeTypeCode - 탈퇴유형 코드
	 */
	public String getSecedeTypeCode() {
		return this.secedeTypeCode;
	}

	/**
	 * 탈퇴유형 코드를 설정한다.
	 * 
	 * @param secedeTypeCode
	 *            탈퇴유형 코드
	 */
	public void setSecedeTypeCode(String secedeTypeCode) {
		this.secedeTypeCode = secedeTypeCode;
	}

	/**
	 * 탈퇴사유 코드를 리턴한다.
	 * 
	 * @return secedeReasonCode - 탈퇴사유 코드
	 */
	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	/**
	 * 탈퇴사유 코드를 설정한다.
	 * 
	 * @param secedeReasonCode
	 *            탈퇴사유 코드
	 */
	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	/**
	 * 탈퇴사유 내용를 리턴한다.
	 * 
	 * @return secedeReasonMessage - 탈퇴사유 내용
	 */
	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	/**
	 * 탈퇴사유 내용를 설정한다.
	 * 
	 * @param secedeReasonMessage
	 *            탈퇴사유 내용
	 */
	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return isDormant
	 */
	public String getIsDormant() {
		return this.isDormant;
	}

	/**
	 * @param isDormant
	 *            String
	 */
	public void setIsDormant(String isDormant) {
		this.isDormant = isDormant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
