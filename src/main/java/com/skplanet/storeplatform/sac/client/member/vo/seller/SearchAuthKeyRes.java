package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;

/**
 * [RESPONSE] 판매자 회원 인증키 조회.
 * 
 * Updated on : 2014. 3. 20. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchAuthKeyRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 판매자 Key (INSD_SELLERMBR_NO).
	 */
	private String sellerKey;

	/**
	 * 판매자 정보.
	 */
	private SellerMbrSac sellerMbr;

	/**
	 * 법정 대리인 정보.
	 */
	private MbrLglAgent mbrLglAgent;

	/**
	 * 판매자 멀티미디어 정보 목록.
	 */
	private List<ExtraRight> extraRightList;

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
	 * @return the sellerMbr
	 */
	public SellerMbrSac getSellerMbr() {
		return this.sellerMbr;
	}

	/**
	 * @param sellerMbr
	 *            the sellerMbr to set
	 */
	public void setSellerMbr(SellerMbrSac sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

	/**
	 * @return the mbrLglAgent
	 */
	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	/**
	 * @param mbrLglAgent
	 *            the mbrLglAgent to set
	 */
	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
	}

	/**
	 * @return the extraRightList
	 */
	public List<ExtraRight> getExtraRightList() {
		return this.extraRightList;
	}

	/**
	 * @param extraRightList
	 *            the extraRightList to set
	 */
	public void setExtraRightList(List<ExtraRight> extraRightList) {
		this.extraRightList = extraRightList;
	}

}
