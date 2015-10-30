package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * [REQUEST] CI Infra 회원키 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraSearchUserInfoRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 통합 서비스 번호.
	 */
	private String imSvcNo;

	/**
	 * 트랜잭션 번호.
	 */
	private String trxNo;

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
	 * <pre>
	 * 통합 서비스 번호 리턴.
	 * </pre>
	 * 
	 * @return imSvcNo
	 */
	public String getImSvcNo() {
		return this.imSvcNo;
	}

	/**
	 * <pre>
	 * 통합 서비스 번호 셋팅.
	 * </pre>
	 * 
	 * @param imSvcNo
	 *            String
	 */
	public void setImSvcNo(String imSvcNo) {
		this.imSvcNo = imSvcNo;
	}

	/**
	 * <pre>
	 * 트랜잭션 번호 리턴.
	 * </pre>
	 * 
	 * @return trxNo
	 */
	public String getTrxNo() {
		return this.trxNo;
	}

	/**
	 * <pre>
	 * 트랜잭션 번호 셋팅.
	 * </pre>
	 * 
	 * @param trxNo
	 *            String
	 */
	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo;
	}

}
