package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ListSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;

/**
 * 판매자 회원 서브계정 등록/수정/삭제/조회 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 한서구, 부르칸.
 */
@Service
public class SellerSubServiceImpl implements SellerSubService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSubServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Autowired
	private MemberCommonComponent commonComponent;

	/**
	 * <pre>
	 * 판매자 서브계정 등록/수정.
	 * </pre>
	 * 
	 * @param CreateSubsellerReq
	 * @return CreateSubsellerRes
	 */
	@Override
	public CreateSubsellerRes createSubseller(SacRequestHeader header, CreateSubsellerReq req) {

		CreateSubSellerRequest schReq = new CreateSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr sellerMbr = new com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr();
		sellerMbr.setParentSellerKey(req.getSellerKey()); // US201401231325534800000164
														  // IF1023501437920130904104346
		sellerMbr.setSellerID(req.getSubSellerID());
		sellerMbr.setSellerKey(req.getSubSellerKey());
		sellerMbr.setSubSellerMemo(req.getSubSellerMemo());
		sellerMbr.setSellerEmail(req.getSubSellerEmail());
		sellerMbr.setRightProfileList(req.getSubSellerCateList());
		sellerMbr.setSellerPhone(req.getSubSellerPhone());
		sellerMbr.setSellerPhoneCountry(req.getSubSellerPhoneCountry());
		schReq.setSellerMbr(sellerMbr);
		schReq.setIsNew(req.getIsNew());

		if (req.getMemberPW() != null && req.getOldPW() != null) {
			MbrPwd mbrPwd = new MbrPwd();
			mbrPwd.setMemberPW(req.getMemberPW());
			mbrPwd.setOldPW(req.getOldPW());
			schReq.setMbrPwd(mbrPwd);
		}
		if (req.getIsNew().equals("Y")) {
			MbrPwd mbrPwd = new MbrPwd();
			mbrPwd.setMemberPW(req.getMemberPW());
			schReq.setMbrPwd(mbrPwd);
		}

		CreateSubSellerResponse schRes = this.sellerSCI.createSubSeller(schReq);

		LOGGER.info("---------" + schRes.getSellerKey());
		CreateSubsellerRes response = new CreateSubsellerRes();

		response.setSubSellerKey(schRes.getSellerKey());

		return response;
	}

	/**
	 * <pre>
	 * 판매자 서브계정 삭제.
	 * </pre>
	 * 
	 * @param RemoveSubsellerReq
	 * @return RemoveSubsellerRes
	 */
	@Override
	public RemoveSubsellerRes removeSubseller(SacRequestHeader header, RemoveSubsellerReq req) {

		RemoveSubSellerRequest schReq = new RemoveSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		schReq.setParentSellerKey(req.getSellerKey());
		// 최종 vo 에 값 셋팅
		List<String> removeKeyList = req.getSubSellerKey();
		schReq.setSellerKeyList(removeKeyList);

		RemoveSubSellerResponse schRes = this.sellerSCI.removeSubSeller(schReq);

		RemoveSubsellerRes response = new RemoveSubsellerRes();

		response.setRemoveCnt(schRes.getDeletedNumber());

		return response;
	}

	/**
	 * <pre>
	 * 서브계정 목록 조회.
	 * </pre>
	 * 
	 * @param ListSubsellerReq
	 * @return ListSubsellerRes
	 */
	@Override
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req) {

		SearchSubSellerListRequest schReq = new SearchSubSellerListRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setParentSellerKey(req.getSellerKey());

		SearchSubSellerListResponse schRes = this.sellerSCI.searchSubSellerList(schReq);

		ListSubsellerRes response = new ListSubsellerRes();
		response.setSellerID(schRes.getSellerID());
		response.setSellerKey(schRes.getSellerKey());
		response.setSubAccountCount(schRes.getSubAccountCount());
		response.setSubSellerList(this.sellerMbrList(schRes.getSubSellerList()));// 판매자 정보 리스트

		return response;
	}

	/**
	 * <pre>
	 * 서브계정 상세 조회.
	 * </pre>
	 * 
	 * @param DetailSubsellerReq
	 * @return DetailSubsellerRes
	 */
	@Override
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, DetailSubsellerReq req) {

		SearchSubSellerRequest schReq = new SearchSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setSellerKey(req.getSellerKey());

		SearchSubSellerResponse schRes = this.sellerSCI.searchSubSeller(schReq);
		if (schRes.getSellerMbr().getParentSellerKey() == null)
			throw new StorePlatformException("SAC_MEM_2201");

		DetailSubsellerRes response = new DetailSubsellerRes();
		response.setSellerMbr(this.sellerMbr(schRes.getSellerMbr()));

		return response;
	}

	/**
	 * <pre>
	 * TODO 판매자 정보.
	 * </pre>
	 * 
	 * @return
	 */
	private List<SellerMbr> sellerMbrList(
			List<com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr> sellerMbr) {

		List<SellerMbr> sList = new ArrayList<SellerMbr>();
		SellerMbr sellerMbrRes = null;
		if (sellerMbr != null)
			for (int i = 0; i < sellerMbr.size(); i++) {
				sellerMbrRes = new SellerMbr();
				sellerMbrRes.setSellerKey(sellerMbr.get(i).getSellerKey());
				sellerMbrRes.setSellerId(sellerMbr.get(i).getSellerID());
				sellerMbrRes.setSellerEmail(sellerMbr.get(i).getSellerEmail());
				sellerMbrRes.setSellerPhone(sellerMbr.get(i).getSellerPhone());
				sellerMbrRes.setSellerPhoneCountry(sellerMbr.get(i).getSellerPhoneCountry());
				sellerMbrRes.setRightProfile(sellerMbr.get(i).getRightProfileList());
				sList.add(sellerMbrRes);
			}

		return sList;
	}

	/**
	 * <pre>
	 * TODO 판매자 정보.
	 * </pre>
	 * 
	 * @return
	 */
	private SellerMbr sellerMbr(com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr sellerMbr) {
		// 판매자 정보
		SellerMbr sellerMbrRes = new SellerMbr();
		if (sellerMbr != null) {
			sellerMbrRes.setSellerId(sellerMbr.getSellerID());
			sellerMbrRes.setRegDate(sellerMbr.getRegDate());
			sellerMbrRes.setSellerEmail(sellerMbr.getSellerEmail());
			sellerMbrRes.setRightProfile(sellerMbr.getRightProfileList());
		}
		return sellerMbrRes;
	}

}
