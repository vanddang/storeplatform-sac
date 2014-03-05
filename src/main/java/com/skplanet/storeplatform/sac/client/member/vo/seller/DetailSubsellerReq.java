package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브계정 상세 조회
 * 
 * Updated on : 2014. 1. 23. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailSubsellerReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String subSellerKey;

	public String getSubSellerKey() {
		return this.subSellerKey;
	}

	public void setSubSellerKey(String subSellerKey) {
		this.subSellerKey = subSellerKey;
	}

}
