package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.FlurryAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;

/**
 * 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 정보. */
	private SellerMbrSac sellerMbr;

	/** 법정대리인 정보. */
	private MbrLglAgent mbrLglAgent;

	/** 판매자 멀티미디어정보 목록. */
	private List<ExtraRight> extraRightList;

	/** 탭권한 목록. */
	private List<TabAuthSac> tabAuthList;

	/** Flurry 인증정보 목록. */
	private List<FlurryAuth> flurryAuthList;

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

	/**
	 * @return the tabAuthList
	 */
	public List<TabAuthSac> getTabAuthList() {
		return this.tabAuthList;
	}

	/**
	 * @param tabAuthList
	 *            the tabAuthList to set
	 */
	public void setTabAuthList(List<TabAuthSac> tabAuthList) {
		this.tabAuthList = tabAuthList;
	}

	/**
	 * @return the flurryAuthList
	 */
	public List<FlurryAuth> getFlurryAuthList() {
		return this.flurryAuthList;
	}

	/**
	 * @param flurryAuthList
	 *            the flurryAuthList to set
	 */
	public void setFlurryAuthList(List<FlurryAuth> flurryAuthList) {
		this.flurryAuthList = flurryAuthList;
	}

	/**
	 * 화면 권한 코드.
	 * 
	 * Updated on : 2014. 3. 24. Updated by : Rejoice, Burkhan
	 */
	public static class TabAuthSac {
		/** 탭 코드. */
		private String tabCode;

		/**
		 * @return the tabCode
		 */
		public String getTabCode() {
			return this.tabCode;
		}

		/**
		 * @param tabCode
		 *            the tabCode to set
		 */
		public void setTabCode(String tabCode) {
			this.tabCode = tabCode;
		}

	}
}
