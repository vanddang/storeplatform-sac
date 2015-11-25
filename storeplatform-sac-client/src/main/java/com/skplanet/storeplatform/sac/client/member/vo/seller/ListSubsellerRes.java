package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SubSellerMbrSac;

/**
 * 서브계정 목록 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListSubsellerRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 ID. */
	private String sellerId;

	/** 판매자 키. */
	private String sellerKey;

	/** 서브계정 카운트. */
	private int subAccountCount;

	/** 서브계정 Value Object 목록. */
	private List<SubSellerMbrSac> subSellerList;

	/** 로그인시간. */
	private String regDate;

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return this.sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the subAccountCount
	 */
	public int getSubAccountCount() {
		return this.subAccountCount;
	}

	/**
	 * @param subAccountCount
	 *            the subAccountCount to set
	 */
	public void setSubAccountCount(int subAccountCount) {
		this.subAccountCount = subAccountCount;
	}

	/**
	 * @return the subSellerList
	 */
	public List<SubSellerMbrSac> getSubSellerList() {
		return this.subSellerList;
	}

	/**
	 * @param subSellerList
	 *            the subSellerList to set
	 */
	public void setSubSellerList(List<SubSellerMbrSac> subSellerList) {
		this.subSellerList = subSellerList;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * @param regDate
	 *            the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
