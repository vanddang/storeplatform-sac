package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브계정 등록/수정
 * 
 * Updated on : 2014. 1. 20. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateSubsellerReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	private String sellerKey;

	/** 서브계정 키. */
	private String subSellerKey;

	/** 서브계정 ID. */
	private String subSellerID;

	/** 서브계정 비밀번호. */
	private String subSellerPW;

	/** 서브계정 메모. */
	private String subSellerMemo;

	/** 서브계정 권한리스트. */
	private String subSellerCateList;

	/** 서브계정 Email. */
	private String subSellerEmail; // EMAIL

	/** 서브계정 무선 국가 번호. */
	private String subSellerPhoneCountry; // WILS_NATION_NO // 무선 국가 번호

	/** 서브계정 무선 전화번호. */
	private String subSellerPhone; // WILS_TEL_NO 무선 전화번호

	/** 신규 등록여부 Example : Y/N. */
	private String isNew;

	/** 이전 비밀번호. */
	private String oldPW; // OldPW

	/** 비밀번호. */
	private String memberPW; // PWD

	public String getOldPW() {
		return this.oldPW;
	}

	public void setOldPW(String oldPW) {
		this.oldPW = oldPW;
	}

	public String getMemberPW() {
		return this.memberPW;
	}

	public void setMemberPW(String memberPW) {
		this.memberPW = memberPW;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getSubSellerKey() {
		return this.subSellerKey;
	}

	public void setSubSellerKey(String subSellerKey) {
		this.subSellerKey = subSellerKey;
	}

	public String getSubSellerID() {
		return this.subSellerID;
	}

	public void setSubSellerID(String subSellerID) {
		this.subSellerID = subSellerID;
	}

	public String getSubSellerPW() {
		return this.subSellerPW;
	}

	public void setSubSellerPW(String subSellerPW) {
		this.subSellerPW = subSellerPW;
	}

	public String getSubSellerMemo() {
		return this.subSellerMemo;
	}

	public void setSubSellerMemo(String subSellerMemo) {
		this.subSellerMemo = subSellerMemo;
	}

	public String getSubSellerCateList() {
		return this.subSellerCateList;
	}

	public void setSubSellerCateList(String subSellerCateList) {
		this.subSellerCateList = subSellerCateList;
	}

	public String getSubSellerEmail() {
		return this.subSellerEmail;
	}

	public void setSubSellerEmail(String subSellerEmail) {
		this.subSellerEmail = subSellerEmail;
	}

	public String getSubSellerPhoneCountry() {
		return this.subSellerPhoneCountry;
	}

	public void setSubSellerPhoneCountry(String subSellerPhoneCountry) {
		this.subSellerPhoneCountry = subSellerPhoneCountry;
	}

	public String getSubSellerPhone() {
		return this.subSellerPhone;
	}

	public void setSubSellerPhone(String subSellerPhone) {
		this.subSellerPhone = subSellerPhone;
	}

	public String getIsNew() {
		return this.isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

}
