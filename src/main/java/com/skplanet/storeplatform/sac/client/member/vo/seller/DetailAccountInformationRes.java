package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraDocument;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccountInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerInfo;

/**
 * 사용자 부가정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailAccountInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	SellerInfo sellerInfo;

	public SellerInfo getSellerInfo() {
		return this.sellerInfo;
	}

	public void setSellerInfo(SellerInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	SellerAccountInfo sellerAccountInfo;

	public SellerAccountInfo getSellerAccountInfo() {
		return this.sellerAccountInfo;
	}

	public void setSellerAccountInfo(SellerAccountInfo sellerAccountInfo) {
		this.sellerAccountInfo = sellerAccountInfo;
	}

	List<ExtraDocument> extraDocumentList;

	public List<ExtraDocument> getExtraDocumentList() {
		return this.extraDocumentList;
	}

	public void setExtraDocumentList(List<ExtraDocument> extraDocumentList) {
		this.extraDocumentList = extraDocumentList;
	}

	private String regDate;
	private String appoveDate;

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getAppoveDate() {
		return this.appoveDate;
	}

	public void setAppoveDate(String appoveDate) {
		this.appoveDate = appoveDate;
	}

}
