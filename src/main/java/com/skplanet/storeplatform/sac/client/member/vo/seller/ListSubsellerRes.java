package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.SubSeller;

/**
 * 사용자 부가정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListSubsellerRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String masSellerKey;
	private int subSellerListSize;

	public String getMasSellerKey() {
		return this.masSellerKey;
	}

	public void setMasSellerKey(String masSellerKey) {
		this.masSellerKey = masSellerKey;
	}

	public int getSubSellerListSize() {
		return this.subSellerListSize;
	}

	public void setSubSellerListSize(int subSellerListSize) {
		this.subSellerListSize = subSellerListSize;
	}

	List<SubSeller> subSellerList;

	public List<SubSeller> getSubSellerList() {
		return this.subSellerList;
	}

	public void setSubSellerList(List<SubSeller> subSellerList) {
		this.subSellerList = subSellerList;
	}

	List<SellerRight> sellerRightList;

	public List<SellerRight> getSellerRightList() {
		return this.sellerRightList;
	}

	public void setSellerRightList(List<SellerRight> sellerRightList) {
		this.sellerRightList = sellerRightList;
	}

}
