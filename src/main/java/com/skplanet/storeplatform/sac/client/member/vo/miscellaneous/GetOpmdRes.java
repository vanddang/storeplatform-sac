package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] OPMD 모회선 번호 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetOpmdRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 모회선 번호.
	 */
	private String msisdn;

	// EC에서 내려주는 정보 - 공통모듈에 제공하기 위한 파라미터
	/**
	 * 고객의 자번호(OPMD MDN).
	 */
	private String opmdMdn;
	/**
	 * 고객 자번호(OPMD MND) 일시정지 상태.
	 */
	private String pauseYN;

	/**
	 * 고객의 모번호 서비스관리번호.
	 */
	private String mobileSvcMngNum;

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return this.msisdn;
	}

	/**
	 * @param msisdn
	 *            the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the opmdMdn
	 */
	public String getOpmdMdn() {
		return this.opmdMdn;
	}

	/**
	 * @param opmdMdn
	 *            the opmdMdn to set
	 */
	public void setOpmdMdn(String opmdMdn) {
		this.opmdMdn = opmdMdn;
	}

	/**
	 * @return the pauseYN
	 */
	public String getPauseYN() {
		return this.pauseYN;
	}

	/**
	 * @param pauseYN
	 *            the pauseYN to set
	 */
	public void setPauseYN(String pauseYN) {
		this.pauseYN = pauseYN;
	}

	/**
	 * @return the mobileSvcMngNum
	 */
	public String getMobileSvcMngNum() {
		return this.mobileSvcMngNum;
	}

	/**
	 * @param mobileSvcMngNum
	 *            the mobileSvcMngNum to set
	 */
	public void setMobileSvcMngNum(String mobileSvcMngNum) {
		this.mobileSvcMngNum = mobileSvcMngNum;
	}

}
