package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Flurry 인증정보 Value Object
 * 
 * Updated on : 2014. 2. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class FlurryAuth extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** Access 코드. */
	private String accessCode; // ACC_CD

	/** 인증 토큰. */
	private String authToken; // AUTH_TOKEN

	/** 등록일시. */
	private String regDate; // REG_DT 등록일시

	/** 수정일시. */
	private String updateDate; // UPD_DT 수정일시

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getAccessCode() {
		return this.accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getAuthToken() {
		return this.authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
