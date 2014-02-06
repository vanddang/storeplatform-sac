package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 탈퇴
 * 
 * Updated on : 2014. 1. 14. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class WithdrawReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String sellerKey;
	@NotBlank
	private String secedeReasonCode;
	@NotBlank
	private String secedeReasonMessage;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
	}

}
