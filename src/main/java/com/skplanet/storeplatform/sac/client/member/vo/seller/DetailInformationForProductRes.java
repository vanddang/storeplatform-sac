package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;

/**
 * App 상세 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationForProductRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String sellerClass;
	private String isDomestic;

	public String getSellerClass() {
		return this.sellerClass;
	}

	public void setSellerClass(String sellerClass) {
		this.sellerClass = sellerClass;
	}

	public String getIsDomestic() {
		return this.isDomestic;
	}

	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	/** 판매자 정보 Value List. */
	private List<SellerMbrSac> sellerMbrList;

	public List<SellerMbrSac> getSellerMbrList() {
		return this.sellerMbrList;
	}

	public void setSellerMbrList(List<SellerMbrSac> sellerMbrList) {
		this.sellerMbrList = sellerMbrList;
	}

}
