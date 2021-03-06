package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.UpdateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.UpdateSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 판매자 회원 서브계정 등록/수정/삭제/조회 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 한서구, 부르칸.
 */
public interface SellerSubService {

	/**
	 * <pre>
	 * 서브계정 등록.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateSubsellerReq
	 * @return CreateSubsellerRes
	 */
	public CreateSubsellerRes regSubseller(SacRequestHeader header, CreateSubsellerReq req);

	/**
	 * <pre>
	 * 서브계정 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            UpdateSubsellerReq
	 * @return UpdateSubsellerRes
	 */
	public UpdateSubsellerRes modSubseller(SacRequestHeader header, UpdateSubsellerReq req);

	/**
	 * <pre>
	 * 서브계정 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            RemoveSubsellerReq
	 * @return RemoveSubsellerRes
	 */
	public RemoveSubsellerRes remSubseller(SacRequestHeader header, RemoveSubsellerReq req);

	/**
	 * <pre>
	 * 서브계정 목록 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ListSubsellerReq
	 * @return ListSubsellerRes
	 */
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req);

	/**
	 * <pre>
	 * 서브계정 상세 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DetailSubsellerReq
	 * @return DetailSubsellerRes
	 */
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, DetailSubsellerReq req);

}
