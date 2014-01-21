package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;

/**
 * 서브계정 목록 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListSubsellerRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 ID. */
	private String sellerID;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** 서브계정 카운트. */
	private int subAccountCount;

	/** 서브계정 Value Object 목록. */
	private List<SellerMbr> subSellerList;

	public String getSellerID() {
		return this.sellerID;
	}

	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public int getSubAccountCount() {
		return this.subAccountCount;
	}

	public void setSubAccountCount(int subAccountCount) {
		this.subAccountCount = subAccountCount;
	}

	public List<SellerMbr> getSubSellerList() {
		return this.subSellerList;
	}

	public void setSubSellerList(List<SellerMbr> subSellerList) {
		this.subSellerList = subSellerList;
	}

}
