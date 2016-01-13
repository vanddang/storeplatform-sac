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
 * Updated on : 2015. 12. 10. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationListForProductSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 상품상세의 판매자 요청 정보 리스트.
	 */
	private List<SellerMbrSac> sellerMbrSacList;

	/**
	 * @return the sellerMbrSacList
	 */
	public List<SellerMbrSac> getSellerMbrSacList() {
		return this.sellerMbrSacList;
	}

	/**
	 * @param sellerMbrSacList
	 *            the sellerMbrSacList to set
	 */
	public void setSellerMbrSacList(List<SellerMbrSac> sellerMbrSacList) {
		this.sellerMbrSacList = sellerMbrSacList;
	}

}
