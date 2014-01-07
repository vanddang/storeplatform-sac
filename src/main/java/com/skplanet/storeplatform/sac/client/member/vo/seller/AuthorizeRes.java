package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	SellerInfo sellerInfo;

	public SellerInfo getSellerInfo() {
		return this.sellerInfo;
	}

	public void setSellerInfo(SellerInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

}
