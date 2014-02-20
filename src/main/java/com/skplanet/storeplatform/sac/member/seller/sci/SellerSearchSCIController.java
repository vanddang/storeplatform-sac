package com.skplanet.storeplatform.sac.member.seller.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSearchService;

/**
 * 판매자 회원 조회 관련 기능
 * 
 * Updated on : 2014. 1. 8. Updated by : 한서구, 부르칸.
 */
@LocalSCI
@RequestMapping(value = "/member/seller/sci")
public class SellerSearchSCIController implements SellerSearchSCI {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SellerSearchService sellerSearchService;

	@Autowired
	private SellerSCI sellerSCI;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MemberCommonComponent commonComponent;

	/**
	 * <pre>
	 * 판매자회원 기본정보조회.
	 * </pre>
	 * 
	 * @param req
	 * @return DetailInformationRes
	 */
	@Override
	@RequestMapping(value = "/detailInformation", method = RequestMethod.POST)
	public @ResponseBody
	DetailInformationSacRes detailInformation(@Validated DetailInformationSacReq req) {

		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		SearchMbrSellerRequest schReq = new SearchMbrSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(requestHeader));

		KeySearch keySearch = new KeySearch();

		String sellerId = StringUtil.nvl(req.getSellerId(), "");
		String sellerKey = StringUtil.nvl(req.getSellerKey(), "");
		String sellerBizNumber = StringUtil.nvl(req.getSellerBizNumber(), "");

		if (sellerId.equals("") && sellerKey.equals("") && sellerBizNumber.equals(""))
			throw new StorePlatformException("SAC_MEM_0001", "sellerBizNumber,sellerKey,sellerId");

		if (!sellerKey.equals("")) {
			keySearch.setKeyString(req.getSellerKey());
			keySearch.setKeyType("INSD_SELLERMBR_NO");
		} else if (!sellerId.equals("")) {
			keySearch.setKeyString(req.getSellerId());
			keySearch.setKeyType("SELLERMBR_ID");
		} else {
			this.LOGGER.debug("==>>[SAC] getSellerBizNumber.toString() : {}", req.getSellerBizNumber());
			keySearch.setKeyString(req.getSellerBizNumber());
			keySearch.setKeyType("BIZ_REG_NO");
		}

		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		schReq.setKeySearchList(list);

		SearchMbrSellerResponse schRes = this.sellerSCI.searchMbrSeller(schReq);

		DetailInformationSacRes response = new DetailInformationSacRes();
		response.setSellerMbr(this.sellerMbrList(schRes.getSellerMbr()));// 판매자 정보

		return response;
	}

	/**
	 * <pre>
	 * TODO 판매자 정보.
	 * </pre>
	 * 
	 * @return
	 */
	private List<SellerMbrSac> sellerMbrList(
			List<com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr> sellerMbr) {

		List<SellerMbrSac> sList = new ArrayList<SellerMbrSac>();
		SellerMbrSac sellerMbrRes = null;
		if (sellerMbr != null)
			for (int i = 0; i < sellerMbr.size(); i++) {
				sellerMbrRes = new SellerMbrSac();
				sellerMbrRes.setApproveDate(sellerMbr.get(i).getApproveDate());
				sellerMbrRes.setBizGrade(sellerMbr.get(i).getBizGrade());

				sellerMbrRes.setSellerKey(sellerMbr.get(i).getSellerKey());
				sellerMbrRes.setSellerId(sellerMbr.get(i).getSellerID());
				sellerMbrRes.setSellerClass(sellerMbr.get(i).getSellerClass());
				sellerMbrRes.setCharger(sellerMbr.get(i).getCharger());
				sellerMbrRes.setSellerCompany(sellerMbr.get(i).getSellerCompany());
				sellerMbrRes.setSellerNickName(sellerMbr.get(i).getSellerNickName());
				sellerMbrRes.setSellerBizNumber(sellerMbr.get(i).getSellerBizNumber());

				sellerMbrRes.setSellerName(sellerMbr.get(i).getSellerName());
				sellerMbrRes.setRepPhone(sellerMbr.get(i).getRepPhone());
				sellerMbrRes.setSellerEmail(sellerMbr.get(i).getSellerEmail());
				sellerMbrRes.setSellerAddress(sellerMbr.get(i).getSellerAddress());
				sellerMbrRes.setSellerDetailAddress(sellerMbr.get(i).getSellerDetailAddress());
				sellerMbrRes.setBizRegNumber(sellerMbr.get(i).getBizRegNumber());
				sList.add(sellerMbrRes);
			}

		return sList;
	}

}
