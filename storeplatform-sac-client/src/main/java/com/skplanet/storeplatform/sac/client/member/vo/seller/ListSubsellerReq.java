package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브계정 목록 조회
 * 
 * Updated on : 2014. 1. 20. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListSubsellerReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	@NotBlank
	private String sellerKey;
	/** 정렬 구분. */
	private String loginSort;

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
	 * @return the loginSort
	 */
	public String getLoginSort() {
		return this.loginSort;
	}

	/**
	 * @param loginSort
	 *            the loginSort to set
	 */
	public void setLoginSort(String loginSort) {
		this.loginSort = loginSort;
	}

}
