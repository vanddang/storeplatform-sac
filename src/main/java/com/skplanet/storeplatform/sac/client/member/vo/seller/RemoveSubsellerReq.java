package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브계정 삭제
 * 
 * Updated on : 2014. 1. 20. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RemoveSubsellerReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	@NotBlank
	private String sellerKey;

	/** 삭제할 서브계정 키 목록. */

	private List<String> subSellerKey;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public List<String> getSubSellerKey() {
		return this.subSellerKey;
	}

	public void setSubSellerKey(List<String> subSellerKey) {
		this.subSellerKey = subSellerKey;
	}

}
