package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface SellerSubService {

	/** 판매자 서브계정 등록. */
	public CreateSubsellerRes createSubseller(SacRequestHeader header, CreateSubsellerReq req);

}
