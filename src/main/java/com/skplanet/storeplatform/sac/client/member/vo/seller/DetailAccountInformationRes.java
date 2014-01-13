package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;

/**
 * 판매자회원 정산 정보 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailAccountInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	List<Document> document;
	List<ExtraRight> extraRight;
	SellerAccount sellerAccount;
	private String sellerKey;
	SellerMbr sellerMbr;

	public List<Document> getDocument() {
		return this.document;
	}

	public void setDocument(List<Document> document) {
		this.document = document;
	}

	public List<ExtraRight> getExtraRight() {
		return this.extraRight;
	}

	public void setExtraRight(List<ExtraRight> extraRight) {
		this.extraRight = extraRight;
	}

	public SellerAccount getSellerAccount() {
		return this.sellerAccount;
	}

	public void setSellerAccount(SellerAccount sellerAccount) {
		this.sellerAccount = sellerAccount;
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
