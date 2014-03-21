package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.FlurryAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrClauseAgree;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrPwd;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.member.vo.common.TabAuth;

/**
 * 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 Key (INSD_SELLERMBR_NO). */
	private String sellerKey; //

	/** 판매자 정보. */
	private SellerMbrSac sellerMbr;

	/** 판매자 정보 목록. */
	private List<SellerMbrSac> sellerMbrList;

	/** 비밀번호 Value Object. */
	private MbrPwd mbrPwd; // PW변경일시 ( TB_US_SELLERMBR_PWD 테이블에서 가져오기) TODO ????

	/** 실명인증 판매자 정보. */
	private MbrAuth mbrAuth;

	/** 법정대리인 정보. */
	private MbrLglAgent mbrLglAgent;

	/** 약관동의 Value Object 목록. */
	private List<MbrClauseAgree> mbrClauseAgreeList; // 이용약관 TB_US_SELLERMBR_CLAUSE_AGREE

	/**
	 * 판매자 멀티미디어정보 목록. US_판매자회원_멀티미디어_권한 - TB_US_SELLERMBR_MULTIMDA_AUTH (bp 사인경우만)
	 */
	private List<ExtraRight> extraRightList;

	/** 탭권한 목록. */
	private List<TabAuth> tabAuthList;

	/** Flurry 인증정보 목록. */
	private List<FlurryAuth> flurryAuthList;

	/** 판매자 정보 리스트 목록. */
	private Map<String, List<SellerMbrSac>> sellerMbrListMap;

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
	 * @return the sellerMbrList
	 */
	public List<SellerMbrSac> getSellerMbrList() {
		return this.sellerMbrList;
	}

	/**
	 * @param sellerMbrList
	 *            the sellerMbrList to set
	 */
	public void setSellerMbrList(List<SellerMbrSac> sellerMbrList) {
		this.sellerMbrList = sellerMbrList;
	}

	/**
	 * @return the mbrPwd
	 */
	public MbrPwd getMbrPwd() {
		return this.mbrPwd;
	}

	/**
	 * @param mbrPwd
	 *            the mbrPwd to set
	 */
	public void setMbrPwd(MbrPwd mbrPwd) {
		this.mbrPwd = mbrPwd;
	}

	/**
	 * @return the mbrAuth
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * @param mbrAuth
	 *            the mbrAuth to set
	 */
	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
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
	 * @return the mbrClauseAgreeList
	 */
	public List<MbrClauseAgree> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	/**
	 * @param mbrClauseAgreeList
	 *            the mbrClauseAgreeList to set
	 */
	public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
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
	public List<TabAuth> getTabAuthList() {
		return this.tabAuthList;
	}

	/**
	 * @param tabAuthList
	 *            the tabAuthList to set
	 */
	public void setTabAuthList(List<TabAuth> tabAuthList) {
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
