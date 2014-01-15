package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;

/**
 * 판매자회원 ID 찾기
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchIdRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 정보 Value Object 목록. */
	private List<SellerMbr> sellerMbr;// 판매자정보

	public List<SellerMbr> getSellerMbr() {
		return this.sellerMbr;
	}

	public void setSellerMbr(List<SellerMbr> sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

}
