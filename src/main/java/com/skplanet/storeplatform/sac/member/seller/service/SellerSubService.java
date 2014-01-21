package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface SellerSubService {

	/** 판매자 서브계정 등록/수정. */
	public CreateSubsellerRes createSubseller(SacRequestHeader header, CreateSubsellerReq req);

	/** 판매자 서브계정 삭제. */
	public RemoveSubsellerRes removeSubseller(SacRequestHeader header, RemoveSubsellerReq req);

	/** 판매자 서브계정 삭제. */
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req);

}
