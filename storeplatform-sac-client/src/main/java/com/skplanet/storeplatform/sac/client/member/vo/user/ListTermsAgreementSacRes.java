package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;

/**
 * [RESPONSE] Store의 약관에 대한 동의 목록 조회하는 기능을 제공한다.
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListTermsAgreementSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

    public ListTermsAgreementSacRes() {}

    public ListTermsAgreementSacRes(String userKey, List<Agreement> agreementList) {
        this.userKey = userKey;
        this.agreementList = agreementList;
    }

    private String userKey;

	/* 약관동의 리스트 */
	private List<Agreement> agreementList;

	public List<Agreement> getAgreementList() {
		return this.agreementList;
	}

	public void setAgreementList(List<Agreement> agreementList) {
		this.agreementList = agreementList;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
