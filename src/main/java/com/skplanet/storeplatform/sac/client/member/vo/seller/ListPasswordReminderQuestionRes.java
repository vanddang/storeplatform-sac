package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrPwdHint;

/**
 * Password 보안 질문 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListPasswordReminderQuestionRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 비밀번호 힌트 Value Object 목록. */
	private List<SellerMbrPwdHint> sellerMbrPwdHintList;

	/** 언어 코드. */
	private String languageCode;

	public List<SellerMbrPwdHint> getSellerMbrPwdHintList() {
		return this.sellerMbrPwdHintList;
	}

	public void setSellerMbrPwdHintList(List<SellerMbrPwdHint> sellerMbrPwdHintList) {
		this.sellerMbrPwdHintList = sellerMbrPwdHintList;
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

}
