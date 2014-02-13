package com.skplanet.storeplatform.sac.member.seller.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.seller.service.SellerSearchService;

/**
 * 판매자 회원 조회 관련 기능
 * 
 * Updated on : 2014. 1. 8. Updated by : 한서구, 부르칸.
 */
@LocalSCI
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
	public DetailInformationSacRes detailInformation(DetailInformationSacReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID("S01");
		commonRequest.setTenantID("S01-01002");

		SearchSellerRequest schReq = new SearchSellerRequest();
		schReq.setCommonRequest(commonRequest);

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

		SearchSellerResponse schRes = this.sellerSCI.searchSeller(schReq);

		DetailInformationSacRes response = new DetailInformationSacRes();
		response.setSellerMbr(this.sellerMbr(schRes.getSellerMbr()));// 판매자 정보

		return response;
	}

	/**
	 * <pre>
	 * TODO 판매자 정보.
	 * </pre>
	 * 
	 * @return
	 */
	private SellerMbrSac sellerMbr(com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr sellerMbr) {
		// 판매자 정보
		SellerMbrSac sellerMbrRes = new SellerMbrSac();
		if (sellerMbr != null) {
			sellerMbrRes.setSellerKey(sellerMbr.getSellerKey());
			sellerMbrRes.setSellerId(sellerMbr.getSellerID());
			sellerMbrRes.setSellerClass(sellerMbr.getSellerClass());
			sellerMbrRes.setCharger(sellerMbr.getCharger());
			sellerMbrRes.setSellerCompany(sellerMbr.getSellerCompany());
			sellerMbrRes.setSellerNickName(sellerMbr.getSellerNickName());
			sellerMbrRes.setSellerBizNumber(sellerMbr.getSellerBizNumber());
		}
		return sellerMbrRes;
	}

}
