/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 판매자 탈퇴 요청 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class RemoveSellerRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자 키. */
	private String sellerKey;

	// ENTRY_BOLTER_PATH_CD <- 추가해야한다

	/** 탈퇴 경로코드. */
	private String secedePathCd; // ENTRY_BOLTER_PATH_CD 가입 퇄퇴 경로 코드 ##### 전환 쪽에서 사용

	/**
	 * 탈퇴 경로코드를 리턴한다.
	 * 
	 * @return secedePathCd - 탈퇴 경로코드
	 */
	public String getSecedePathCd() {
		return this.secedePathCd;
	}

	/**
	 * 탈퇴 경로코드를 설정한다.
	 * 
	 * @param secedePathCd
	 *            탈퇴 경로코드
	 */
	public void setSecedePathCd(String secedePathCd) {
		this.secedePathCd = secedePathCd;
	}

	/**
	 * <pre>
	 * 탈퇴사유 코드. 
	 * US010401 : ID 변경
	 * US010402 : 판매 성과 부진
	 * US010403 : 정산에 대한 불신
	 * US010404 : 장기간 부재 (군입대, 유학)
	 * US010405 : 마켓 변경
	 * US010406 : 시스템 장애
	 * US010407 : 개인정보 보안문제
	 * US010409 : 기타
	 * US010410 : 미방문
	 * US010411 : 서비스 이용 불편
	 * US010412 : 컨텐츠 부족
	 * US010413 : 회비 부담
	 * </pre>
	 */
	private String secedeReasonCode;

	/** 탈퇴사유 메시지. */
	private String secedeReasonMessage;

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

	/**
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 키
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
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
	 * 탈퇴사유 메시지를 리턴한다.
	 * 
	 * @return secedeReasonMessage - 탈퇴사유 메시지
	 */
	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	/**
	 * 탈퇴사유 메시지를 설정한다.
	 * 
	 * @param secedeReasonMessage
	 *            탈퇴사유 메시지
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
