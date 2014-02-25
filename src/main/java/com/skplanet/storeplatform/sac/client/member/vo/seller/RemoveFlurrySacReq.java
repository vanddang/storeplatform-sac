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
	private List<FlurryAuth> flurryAuthList;

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return this.sessionKey;
	}

	/**
	 * @param sessionKey
	 *            the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the flurryAuthList
	 */
	public List<FlurryAuth> getFlurryAuthList() {
		return this.flurryAuthList;
	}

	/**
	 * @param flurryAuthList
	 *            the flurryAuthList to set
	 */
	public void setFlurryAuthList(List<FlurryAuth> flurryAuthList) {
		this.flurryAuthList = flurryAuthList;
	}

	/**
	 * FlurryAuth 정보
	 * 
	 * Updated on : 2014. 2. 25. Updated by : Rejoice, Burkhan
	 */
	public static class FlurryAuth {

		/** Access 코드. */
		@NotBlank
		private String accessCode;

		/**
		 * @return the accessCode
		 */
		public String getAccessCode() {
			return this.accessCode;
		}

		/**
		 * @param accessCode
		 *            the accessCode to set
		 */
		public void setAccessCode(String accessCode) {
			this.accessCode = accessCode;
		}

	}
}
