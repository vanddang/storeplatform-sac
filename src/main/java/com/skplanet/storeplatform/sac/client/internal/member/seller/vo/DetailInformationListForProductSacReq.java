/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.seller.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE]상품상세의 판매자 정보 목록 조회.
 * 
 * Updated on : 2014. 6. 10. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationListForProductSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 판매자 Key 목록.
	 */
	private List<String> sellerKeyList;

	/**
	 * @return the sellerKeyList
	 */
	public List<String> getSellerKeyList() {
		return this.sellerKeyList;
	}

	/**
	 * @param sellerKeyList
	 *            the sellerKeyList to set
	 */
	public void setSellerKeyList(List<String> sellerKeyList) {
		this.sellerKeyList = sellerKeyList;
	}

}
