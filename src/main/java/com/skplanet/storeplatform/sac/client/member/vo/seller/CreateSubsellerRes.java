package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브계정 등록/수정
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateSubsellerRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 서브계정 키. */
	private String subSellerKey; // INSD_SELLERMBR_NO

	public String getSubSellerKey() {
		return this.subSellerKey;
	}

	public void setSubSellerKey(String subSellerKey) {
		this.subSellerKey = subSellerKey;
	}

}
