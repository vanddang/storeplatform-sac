package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 탈퇴 사유 목록 조회 탈퇴사유 리스트
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SecedeReson extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 탈퇴 사유 코드. */
	private String secedeReasonCode;
	/** 탈퇴 사유 메세지. */
	private String secedeReasonMessage;

	/**
	 * @return the secedeReasonCode
	 */
	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	/**
	 * @param secedeReasonCode
	 *            the secedeReasonCode to set
	 */
	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	/**
	 * @return the secedeReasonMessage
	 */
	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	/**
	 * @param secedeReasonMessage
	 *            the secedeReasonMessage to set
	 */
	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
	}

}
