package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrAuth;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrClauseAgreeList;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrLglAgent;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrPwd;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;

/**
 * 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	List<ExtraRight> extraRightList;

	MbrAuth mbrAuth;

	List<MbrClauseAgreeList> mbrClauseAgreeList;

	MbrLglAgent mbrLglAgent;

	MbrPwd mbrPwd;

	private String sellerKey;

	SellerMbr sellerMbr;

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

	public List<MbrClauseAgreeList> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	public void setMbrClauseAgreeList(List<MbrClauseAgreeList> mbrClauseAgreeList) {
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
