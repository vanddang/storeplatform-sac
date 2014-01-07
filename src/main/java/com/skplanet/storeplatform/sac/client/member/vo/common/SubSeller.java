package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class SubSeller extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String sellerKey;
	private String sellerId;
	private String sellerPw;
	private String sellerEmail;
	private String sellerPhoneCountry;
	private String sellerPhone;
	private String sellerMemo;
	private String lastLoginDttm;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerPw() {
		return this.sellerPw;
	}

	public void setSellerPw(String sellerPw) {
		this.sellerPw = sellerPw;
	}

	public String getSellerEmail() {
		return this.sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSellerPhoneCountry() {
		return this.sellerPhoneCountry;
	}

	public void setSellerPhoneCountry(String sellerPhoneCountry) {
		this.sellerPhoneCountry = sellerPhoneCountry;
	}

	public String getSellerPhone() {
		return this.sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerMemo() {
		return this.sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	public String getLastLoginDttm() {
		return this.lastLoginDttm;
	}

	public void setLastLoginDttm(String lastLoginDttm) {
		this.lastLoginDttm = lastLoginDttm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	List<SellerRight> sellerRightList;

	public List<SellerRight> getSellerRightList() {
		return this.sellerRightList;
	}

	public void setSellerRightList(List<SellerRight> sellerRightList) {
		this.sellerRightList = sellerRightList;
	}

}
