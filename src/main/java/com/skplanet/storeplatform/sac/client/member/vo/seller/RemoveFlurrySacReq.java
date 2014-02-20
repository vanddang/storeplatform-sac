package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.30. Flurry 삭제 [REQUEST]
 * 
 * Updated on : 2014. 2. 20. Updated by : Rejoice, Burkhan
 */
public class RemoveFlurrySacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String sessionKey;
	@NotBlank
	private String sellerKey;

	/** Flurry List. */
	public List<FlurryAuth> getFlurryAuthList() {
		return this.flurryAuthList;
	}

	public String getSessionKey() {
		return this.sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	private List<FlurryAuth> flurryAuthList;

	public void setFlurryAuthList(List<FlurryAuth> flurryAuthList) {
		this.flurryAuthList = flurryAuthList;
	}

	public static class FlurryAuth {

		/** Access 코드. */
		@NotBlank
		private String accessCode;

		public String getAccessCode() {
			return this.accessCode;
		}

		public void setAccessCode(String accessCode) {
			this.accessCode = accessCode;
		}

	}
}
