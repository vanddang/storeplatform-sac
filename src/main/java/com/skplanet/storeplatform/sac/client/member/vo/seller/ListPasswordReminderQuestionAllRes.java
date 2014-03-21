/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrPwdHint;

/**
 * [RESPONSE]Password 보안 질문 조회 All.
 * 
 * Updated on : 2014. 3. 20. Updated by : 김다슬, 인크로스.
 */
public class ListPasswordReminderQuestionAllRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/** 비밀번호 힌트 Value Object 목록. */
	private List<SellerMbrPwdHint> sellerMbrPwdHintList;

	/**
	 * @return the sellerMbrPwdHintList
	 */
	public List<SellerMbrPwdHint> getSellerMbrPwdHintList() {
		return this.sellerMbrPwdHintList;
	}

	/**
	 * @param sellerMbrPwdHintList
	 *            the sellerMbrPwdHintList to set
	 */
	public void setSellerMbrPwdHintList(List<SellerMbrPwdHint> sellerMbrPwdHintList) {
		this.sellerMbrPwdHintList = sellerMbrPwdHintList;
	}
}
