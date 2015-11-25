package com.skplanet.storeplatform.sac.client.internal.member.seller.vo;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 2. 12. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 정보 리스트 Value Object 목록. */
	private Map<String, List<SellerMbrSac>> sellerMbrListMap;

	/**
	 * @return the sellerMbrListMap
	 */
	public Map<String, List<SellerMbrSac>> getSellerMbrListMap() {
		return this.sellerMbrListMap;
	}

	/**
	 * @param sellerMbrListMap
	 *            the sellerMbrListMap to set
	 */
	public void setSellerMbrListMap(Map<String, List<SellerMbrSac>> sellerMbrListMap) {
		this.sellerMbrListMap = sellerMbrListMap;
	}

}
