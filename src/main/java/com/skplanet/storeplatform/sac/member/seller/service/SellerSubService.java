package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateBySubsellerIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateBySubsellerIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 판매자 회원 서브계정 등록/수정/삭제/조회 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 한서구, 부르칸.
 */
public interface SellerSubService {

	/** 판매자 서브계정 등록/수정. */
	public CreateSubsellerRes createSubseller(SacRequestHeader header, CreateSubsellerReq req);

	/** 판매자 서브계정 삭제. */
	public RemoveSubsellerRes removeSubseller(SacRequestHeader header, RemoveSubsellerReq req);

	/** 서브계정 목록 조회. */
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req);

	/** 서브계정 상세 조회. */
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, DetailSubsellerReq req);

	/** 판매자 회원 ID/Email 중복조회 */
	public DuplicateBySubsellerIdRes duplicateBySubsellerId(SacRequestHeader header, DuplicateBySubsellerIdReq req)
			throws Exception;

}
