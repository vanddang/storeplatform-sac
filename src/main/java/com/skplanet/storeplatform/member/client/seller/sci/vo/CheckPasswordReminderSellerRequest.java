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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

// TODO: Auto-generated Javadoc
/**
 * 판매자회원 비밀번호 보안질문 확인 요청 Value Object.
 * 
 * Updated on : 2013. 12. 16. Updated by : wisestone_mikepark
 */
public class CheckPasswordReminderSellerRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자회원 ID. */
	private String sellerID; // SELLERMBR_ID 판매자회원 id

	/**
	 * 비밀번호 보안질문 Value Object.<br>
	 * 직접 입력인 경우 QuestionMessage 에 값을 넣어주세요 <br>
	 * ex) 직접입력 questionID (QUESTION4) => QuestionMessage 필수<br>
	 * ex) 외코드 questionID (QUESTION1,QUESTION2,QUESTION3) => AnswerString 필수<br>
	 */
	private List<PWReminder> pWReminderList;

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
	 * 판매자회원 ID를 리턴한다.
	 * 
	 * @return sellerID - 판매자회원 ID
	 */
	public String getSellerID() {
		return this.sellerID;
	}

	/**
	 * 판매자회원 ID를 설정한다.
	 * 
	 * @param sellerID
	 *            판매자회원 ID
	 */
	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}

	/**
	 * 비밀번호 보안질문 Value Object를 리턴한다.
	 * 
	 * @return pWReminderList - 비밀번호 보안질문 Value Object
	 */
	public List<PWReminder> getPWReminderList() {
		return this.pWReminderList;
	}

	/**
	 * 비밀번호 보안질문 Value Object를 설정한다.
	 * 
	 * @param pWReminderList
	 *            비밀번호 보안질문 Value Object
	 */
	public void setPWReminderList(List<PWReminder> pWReminderList) {
		this.pWReminderList = pWReminderList;
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
