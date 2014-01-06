package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchIdRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	List<SellerId> sllerIdList;
	private String sellerName;
	private String regDate;
	private String sellerEmail;

	public List<SellerId> getSllerIdList() {
		return this.sllerIdList;
	}

	public void setSllerIdList(List<SellerId> sllerIdList) {
		this.sllerIdList = sllerIdList;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getSellerEmail() {
		return this.sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

}
