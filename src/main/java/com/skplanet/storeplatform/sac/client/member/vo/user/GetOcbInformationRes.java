package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 회원 OCB 정보 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetOcbInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key
	 */
	private String userKey;

	/**
	 * 인증수단 코드 - OR003400 : 비인증 - OR003401 : 카드번호인증 - OR003402 : 주민번호인증
	 */
	private String ocbAuthMtdCd;

	/**
	 * 사용 여부
	 */
	private String useYn;

	/**
	 * 암호화된 OCB 번호
	 */
	private String ocbNo;

	/**
	 * 사용자 시작일자 (YYYYMMDD)
	 */
	private String useStartDt;

	/**
	 * 사용 종료일자 (YYYYMMDD)
	 */
	private String useEndDt;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getOcbAuthMtdCd() {
		return this.ocbAuthMtdCd;
	}

	public void setOcbAuthMtdCd(String ocbAuthMtdCd) {
		this.ocbAuthMtdCd = ocbAuthMtdCd;
	}

	public String getUseYn() {
		return this.useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getOcbNo() {
		return this.ocbNo;
	}

	public void setOcbNo(String ocbNo) {
		this.ocbNo = ocbNo;
	}

	public String getUseStartDt() {
		return this.useStartDt;
	}

	public void setUseStartDt(String useStartDt) {
		this.useStartDt = useStartDt;
	}

	public String getUseEndDt() {
		return this.useEndDt;
	}

	public void setUseEndDt(String useEndDt) {
		this.useEndDt = useEndDt;
	}

}
