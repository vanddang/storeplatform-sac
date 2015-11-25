package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] CI Infra 회원키 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraSearchUserInfoSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 통합 서비스 번호.
	 */
	private String imSvcNo;

	/**
	 * 트랜잭션 번호.
	 */
	private String trxNo;

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
