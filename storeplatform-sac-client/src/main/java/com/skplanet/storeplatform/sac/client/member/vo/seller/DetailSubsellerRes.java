package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SubSellerMbrSac;

/**
 * 서브계정 상세 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailSubsellerRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자정보 Value Object. */
	private SubSellerMbrSac subSellerMbr;

	/**
	 * @return the subSellerMbr
	 */
	public SubSellerMbrSac getSubSellerMbr() {
		return this.subSellerMbr;
	}

	/**
	 * @param subSellerMbr
	 *            the subSellerMbr to set
	 */
	public void setSubSellerMbr(SubSellerMbrSac subSellerMbr) {
		this.subSellerMbr = subSellerMbr;
	}

}
