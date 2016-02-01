/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST]userKey, deviceKey 이용하여 회원 결제페이지 노출 정보 조회
 * 
 * Updated on : 2016. 1. 29. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserPayplanetSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String skpAgreementYn;

	private String ocbCardNumber;

	private String ocbAuthMethodCode;

	public String getSkpAgreementYn() {
		return this.skpAgreementYn;
	}

	public void setSkpAgreementYn(String skpAgreementYn) {
		this.skpAgreementYn = skpAgreementYn;
	}

	public String getOcbCardNumber() {
		return this.ocbCardNumber;
	}

	public void setOcbCardNumber(String ocbCardNumber) {
		this.ocbCardNumber = ocbCardNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return String : ocbAuthMethodCode
	 */
	public String getOcbAuthMethodCode() {
		return this.ocbAuthMethodCode;
	}

	/**
	 * @param ocbAuthMethodCode
	 *            String : the ocbAuthMethodCode to set
	 */
	public void setOcbAuthMethodCode(String ocbAuthMethodCode) {
		this.ocbAuthMethodCode = ocbAuthMethodCode;
	}

}
