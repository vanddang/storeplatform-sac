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
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.TabAuth;

/**
 * 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** 판매자 정보 Value Object. */
	private SellerMbr sellerMbr;

	/** 판매자 정보 Value List. */
	private List<SellerMbr> sellerMbrList;

	/** 비밀번호 Value Object. */
	private MbrPwd mbrPwd; // PW변경일시 ( TB_US_SELLERMBR_PWD 테이블에서 가져오기)

	/** 실명인증 Value Object. */
	private MbrAuth mbrAuth;

	/** 법정대리인 Value Object. */
	private MbrLglAgent mbrLglAgent;

	/** 약관동의 Value Object 목록. */
	private List<MbrClauseAgree> mbrClauseAgreeList; // 이용약관 TB_US_SELLERMBR_CLAUSE_AGREE

	/** 판매자 멀티미디어정보 Value Object 목록. */
	private List<ExtraRight> extraRightList; // US_판매자회원_멀티미디어_권한 TB_US_SELLERMBR_MULTIMDA_AUTH (bp 사인경우만)

	/** 판매자 비밀번호 보안질문 확인 Value Object 목록. */
	// private List<PWReminder> pWReminderList;

	/** 탭권한 Value Object 목록. */
	private List<TabAuth> tabAuthList;

	/** Flurry 인증정보 Value Object 목록. */
	private List<FlurryAuth> flurryAuthList;

	/** 판매자 정보 리스트 Value Object 목록. */
	private Map<String, List<SellerMbr>> sellerMbrListMap;

	public List<SellerMbr> getSellerMbrList() {
		return this.sellerMbrList;
	}

	public void setSellerMbrList(List<SellerMbr> sellerMbrList) {
		this.sellerMbrList = sellerMbrList;
	}

	public Map<String, List<SellerMbr>> getSellerMbrListMap() {
		return this.sellerMbrListMap;
	}

	public void setSellerMbrListMap(Map<String, List<SellerMbr>> sellerMbrListMap) {
		this.sellerMbrListMap = sellerMbrListMap;
	}

	public List<FlurryAuth> getFlurryAuthList() {
		return this.flurryAuthList;
	}

	public void setFlurryAuthList(List<FlurryAuth> flurryAuthList) {
		this.flurryAuthList = flurryAuthList;
	}

	public List<TabAuth> getTabAuthList() {
		return this.tabAuthList;
	}

	public void setTabAuthList(List<TabAuth> tabAuthList) {
		this.tabAuthList = tabAuthList;
	}

	public List<ExtraRight> getExtraRightList() {
		return this.extraRightList;
	}

	public void setExtraRightList(List<ExtraRight> extraRightList) {
		this.extraRightList = extraRightList;
	}

	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	public List<MbrClauseAgree> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
	}

	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
	}

	public MbrPwd getMbrPwd() {
		return this.mbrPwd;
	}

	public void setMbrPwd(MbrPwd mbrPwd) {
		this.mbrPwd = mbrPwd;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public SellerMbr getSellerMbr() {
		return this.sellerMbr;
	}

	public void setSellerMbr(SellerMbr sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

}
