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

		List<KeySearch> sellerMbrSacList = null;
		if (req.getSellerMbrSacList() != null) {
			sellerMbrSacList = new ArrayList<KeySearch>();
			for (int i = 0; i < req.getSellerMbrSacList().size(); i++) {
				KeySearch sellerMbrSac = new KeySearch();

				if (!StringUtil.nvl(req.getSellerMbrSacList().get(i).getSellerKey(), "").equals("")) {
					sellerMbrSac.setKeyString(req.getSellerMbrSacList().get(i).getSellerKey());
					sellerMbrSac.setKeyType("INSD_SELLERMBR_NO");
					sellerMbrSacList.add(sellerMbrSac);
				} else if (!StringUtil.nvl(req.getSellerMbrSacList().get(i).getSellerId(), "").equals("")) {
					sellerMbrSac.setKeyString(req.getSellerMbrSacList().get(i).getSellerId());
					sellerMbrSac.setKeyType("SELLERMBR_ID");
					sellerMbrSacList.add(sellerMbrSac);
				} else if (!StringUtil.nvl(req.getSellerMbrSacList().get(i).getSellerBizNumber(), "").equals("")) {
					sellerMbrSac.setKeyString(req.getSellerMbrSacList().get(i).getSellerBizNumber());
					sellerMbrSac.setKeyType("BIZ_REG_NO");
					sellerMbrSacList.add(sellerMbrSac);
				}
			}

		}
		schReq.setKeySearchList(sellerMbrSacList);

		SearchMbrSellerResponse schRes = this.sellerSCI.searchMbrSeller(schReq);

		DetailInformationSacRes response = new DetailInformationSacRes();

		response.setSellerMbrListMap(schRes.getSellerMbrListMap());

		return response;
	}

}
