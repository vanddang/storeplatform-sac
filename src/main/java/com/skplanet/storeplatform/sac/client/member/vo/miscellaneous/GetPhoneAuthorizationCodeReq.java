package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 휴대폰 인증 SMS 발송
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetPhoneAuthorizationCodeReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 수신자 휴대폰 번호.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String recvMdn;

	/**
	 * 통신사명.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String carrier;

	/**
	 * 요청측ID.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String srcId;

	/**
	 * 텔레서비스ID.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String teleSvcId;

	/**
	 * @return the recvMdn
	 */
	public String getRecvMdn() {
		return this.recvMdn;
	}

	/**
	 * @param recvMdn
	 *            the recvMdn to set
	 */
	public void setRecvMdn(String recvMdn) {
		this.recvMdn = recvMdn;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return this.carrier;
	}

	/**
	 * @param carrier
	 *            the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return String : srcId
	 */
	public String getSrcId() {
		return this.srcId;
	}

	/**
	 * @param srcId
	 */
	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	/**
	 * @return String : teleSvcId
	 */
	public String getTeleSvcId() {
		return this.teleSvcId;
	}

	/**
	 * @param teleSvcId
	 */
	public void setTeleSvcId(String teleSvcId) {
		this.teleSvcId = teleSvcId;
	}

}
