package com.skplanet.storeplatform.sac.client.internal.member.seller.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 2. 12. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

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
