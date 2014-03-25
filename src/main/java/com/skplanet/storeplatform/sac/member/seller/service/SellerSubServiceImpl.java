package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
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
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.SubSellerMbrSac;
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
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 판매자 회원 서브계정 등록/수정/삭제/조회 기능 항목들.
 * 
 * Updated on : 2014. 1. 13. Updated by : 한서구, 부르칸.
 */
@Service
public class SellerSubServiceImpl implements SellerSubService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSubServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI; // 회원 Component 판매자 기능 Interface.

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트

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
	@Override
	public CreateSubsellerRes createSubseller(SacRequestHeader header, CreateSubsellerReq req) {

		CreateSubSellerRequest schReq = new CreateSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setIsNew(MemberConstants.USE_Y);

		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setParentSellerKey(req.getSellerKey());
		sellerMbr.setSellerID(req.getSubSellerId());
		sellerMbr.setSubSellerMemo(req.getSubSellerMemo());
		sellerMbr.setSellerEmail(req.getSubSellerEmail());
		sellerMbr.setRightProfileList(req.getSubSellerCateList());
		sellerMbr.setSellerPhone(req.getSubSellerPhone());
		sellerMbr.setSellerPhoneCountry(req.getSubSellerPhoneCountry());
		schReq.setSellerMbr(sellerMbr);

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberPW(req.getSubSellerPw());
		schReq.setMbrPwd(mbrPwd);

		CreateSubSellerResponse schRes = this.sellerSCI.createSubSeller(schReq);

		LOGGER.info(schRes.getSellerKey());
		CreateSubsellerRes response = new CreateSubsellerRes();

		response.setSubSellerKey(schRes.getSellerKey());

		return response;
	}

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
	@Override
	public UpdateSubsellerRes updateSubseller(SacRequestHeader header, UpdateSubsellerReq req) {

		CreateSubSellerRequest schReq = new CreateSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setParentSellerKey(req.getSellerKey());
		sellerMbr.setSellerKey(req.getSubSellerKey());
		sellerMbr.setSubSellerMemo(req.getSubSellerMemo());
		sellerMbr.setSellerEmail(req.getSubSellerEmail());
		sellerMbr.setRightProfileList(req.getSubSellerCateList());
		sellerMbr.setSellerPhone(req.getSubSellerPhone());
		sellerMbr.setSellerPhoneCountry(req.getSubSellerPhoneCountry());
		schReq.setSellerMbr(sellerMbr);

		MbrPwd mbrPwd = null;
		if (StringUtils.isNotBlank(req.getSubSellerPw())) {
			mbrPwd = new MbrPwd();
			mbrPwd.setMemberPW(req.getSubSellerPw());
		}

		schReq.setMbrPwd(mbrPwd);
		schReq.setIsNew(MemberConstants.USE_N);

		CreateSubSellerResponse schRes = this.sellerSCI.createSubSeller(schReq);

		LOGGER.info(schRes.getSellerKey());
		UpdateSubsellerRes response = new UpdateSubsellerRes();

		response.setSubSellerKey(schRes.getSellerKey());

		return response;
	}

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
	@Override
	public RemoveSubsellerRes removeSubseller(SacRequestHeader header, RemoveSubsellerReq req) {

		RemoveSubSellerRequest schReq = new RemoveSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		schReq.setParentSellerKey(req.getSellerKey());
		schReq.setSellerKeyList(req.getSubSellerKeyList());

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
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ListSubsellerReq
	 * @return ListSubsellerRes
	 */
	@Override
	public ListSubsellerRes listSubseller(SacRequestHeader header, ListSubsellerReq req) {

		SearchSubSellerListRequest schReq = new SearchSubSellerListRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setParentSellerKey(req.getSellerKey());
		schReq.setLoginSort(req.getLoginSort());

		SearchSubSellerListResponse schRes = this.sellerSCI.searchSubSellerList(schReq);

		ListSubsellerRes response = new ListSubsellerRes();
		response.setSellerId(schRes.getSellerID());
		response.setSellerKey(schRes.getSellerKey());
		response.setSubAccountCount(schRes.getSubAccountCount());
		response.setSubSellerList(this.sellerMbrList(schRes.getSubSellerList()));
		return response;
	}

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
	@Override
	public DetailSubsellerRes detailSubseller(SacRequestHeader header, DetailSubsellerReq req) {

		LOGGER.debug("############ SellerSubServiceImpl.detailSubseller() [START] ############");

		SearchSubSellerRequest schReq = new SearchSubSellerRequest();

		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		schReq.setSellerKey(req.getSubSellerKey());

		SearchSubSellerResponse schRes = this.sellerSCI.searchSubSeller(schReq);
		if (StringUtils.isBlank(schRes.getSellerMbr().getParentSellerKey()))
			throw new StorePlatformException("SAC_MEM_2201");

		DetailSubsellerRes response = new DetailSubsellerRes();
		response.setSubSellerMbr(this.sellerMbr(schRes.getSellerMbr()));

		LOGGER.debug("############ SellerSubServiceImpl.detailSubseller() [END] ############");
		return response;
	}

	/**
	 * <pre>
	 * 판매자 정보 리스트.
	 * </pre>
	 * 
	 * @param sellerMbr
	 *            SellerMbr
	 * @return List<SellerMbr>
	 */
	private List<SubSellerMbrSac> sellerMbrList(List<SellerMbr> sellerMbr) {

		List<SubSellerMbrSac> sList = new ArrayList<SubSellerMbrSac>();
		SubSellerMbrSac subSellerMbrSac = null;
		if (sellerMbr != null)
			for (int i = 0; i < sellerMbr.size(); i++) {
				subSellerMbrSac = new SubSellerMbrSac();
				subSellerMbrSac.setSubSellerKey(sellerMbr.get(i).getSellerKey());
				subSellerMbrSac.setSubSellerId(sellerMbr.get(i).getSellerID());
				subSellerMbrSac.setSubSellerEmail(sellerMbr.get(i).getSellerEmail());
				subSellerMbrSac.setSubSellerPhone(sellerMbr.get(i).getSellerPhone());
				subSellerMbrSac.setSubSellerPhoneCountry(sellerMbr.get(i).getSellerPhoneCountry());
				subSellerMbrSac.setSubSellerCateList(sellerMbr.get(i).getRightProfileList());
				subSellerMbrSac.setSubSellerLoginDttm(sellerMbr.get(i).getLoginDate());
				sList.add(subSellerMbrSac);
			}

		return sList;
	}

	/**
	 * <pre>
	 * 판매자 정보.
	 * </pre>
	 * 
	 * @param sellerMbr
	 *            SellerMbr
	 * @return SellerMbr
	 */
	private SubSellerMbrSac sellerMbr(SellerMbr sellerMbr) {
		// 판매자 정보
		SubSellerMbrSac sellerMbrRes = new SubSellerMbrSac();
		if (sellerMbr != null) {
			sellerMbrRes.setSubSellerId(sellerMbr.getSellerID());
			sellerMbrRes.setSubRegDate(sellerMbr.getRegDate());
			sellerMbrRes.setSubSellerEmail(sellerMbr.getSellerEmail());
			sellerMbrRes.setSubSellerCateList(sellerMbr.getRightProfileList());
			sellerMbrRes.setSubSellerMemo(sellerMbr.getSubSellerMemo());
			sellerMbrRes.setSubSellerPhoneCountry(sellerMbr.getSellerPhoneCountry());
			sellerMbrRes.setSubSellerPhone(sellerMbr.getSellerPhone());
			sellerMbrRes.setSubSellerKey(sellerMbr.getSellerKey());
			sellerMbrRes.setSubSellerLoginDttm(sellerMbr.getLoginDate());
		}
		return sellerMbrRes;
	}

}
