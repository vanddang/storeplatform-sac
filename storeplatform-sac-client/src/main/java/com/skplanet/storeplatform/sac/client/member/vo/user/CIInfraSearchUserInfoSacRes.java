package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] CI Infra 회원키 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CIInfraSearchUserInfoSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 통합 서비스 번호.
	 */
	private String imSvcNo = "";

	/**
	 * 트랜잭션 번호.
	 */
	private String trxNo = "";

	/**
	 * 통합ID.
	 */
	private String userId = "";

	/**
	 * OneId가 발행하는 회원 Identity Key.
	 */
	private String imMbrNo = "";

	/**
	 * Tstore가 관리하는 회원 Identity Key.
	 */
	private String userKey = "";

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

	/**
	 * <pre>
	 * 통합ID 리턴.
	 * </pre>
	 * 
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * <pre>
	 * 통합ID 셋팅.
	 * </pre>
	 * 
	 * @param userId
	 *            String
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * <pre>
	 * OneId가 발행하는 회원 Identity Key 리턴.
	 * </pre>
	 * 
	 * @return imMbrNo
	 */
	public String getImMbrNo() {
		return this.imMbrNo;
	}

	/**
	 * <pre>
	 * OneId가 발행하는 회원 Identity Key 셋팅.
	 * </pre>
	 * 
	 * @param imMbrNo
	 *            String
	 */
	public void setImMbrNo(String imMbrNo) {
		this.imMbrNo = imMbrNo;
	}

	/**
	 * <pre>
	 * Tstore가 관리하는 회원 Identity Key 리턴.
	 * </pre>
	 * 
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * <pre>
	 * Tstore가 관리하는 회원 Identity Key 셋팅.
	 * </pre>
	 * 
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
