package com.skplanet.storeplatform.sac.member.seller.sci;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSearchSCIController.class);

	@Autowired
	private SellerSearchService sellerSearchService;

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
	 *            DetailInformationSacReq
	 * @return DetailInformationRes
	 */
	@Override
	@RequestMapping(value = "/detailInformation", method = RequestMethod.POST)
	public @ResponseBody
	DetailInformationSacRes detailInformation(@Validated DetailInformationSacReq request) {

		LOGGER.debug("Internal SCI detailInformation() Request : {}", request);
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		DetailInformationSacRes response = this.sellerSearchService.detailInformationList(requestHeader, request);
		LOGGER.debug("Internal SCI detailInformation() Response : {}", response);
		return response;

	}
}
