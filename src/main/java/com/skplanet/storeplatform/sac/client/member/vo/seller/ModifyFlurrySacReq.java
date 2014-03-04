package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.34. Flurry 수정 [REQUEST]
 * 
 * Updated on : 2014. 3. 4. Updated by : Rejoice, Burkhan
 */
public class ModifyFlurrySacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<FlurryAuth> flurryAuthList;

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
	 * Flurry 정보.
	 * 
	 * Updated on : 2014. 3. 4. Updated by : Rejoice, Burkhan
	 */
	public class FlurryAuth {

		/** 토큰. */
		public String authToken;
		/** Access 코드. */
		public String accessCode;

		/**
		 * @return the authToken
		 */
		public String getAuthToken() {
			return this.authToken;
		}

		/**
		 * @param authToken
		 *            the authToken to set
		 */
		public void setAuthToken(String authToken) {
			this.authToken = authToken;
		}

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
