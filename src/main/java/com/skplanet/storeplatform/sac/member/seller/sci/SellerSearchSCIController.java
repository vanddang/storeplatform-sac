package com.skplanet.storeplatform.sac.member.seller.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.seller.sci.service.SellerSearchSCIService;

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
	private SellerSearchSCIService sellerSearchService;

	/**
	 * <pre>
	 * 판매자회원 기본정보조회.
	 * </pre>
	 * 
	 * @param request
	 *            DetailInformationSacReq
	 * @return DetailInformationRes
	 */
	@Override
	@RequestMapping(value = "/detailInformation", method = RequestMethod.POST)
	@ResponseBody
	public DetailInformationSacRes detailInformation(@Validated DetailInformationSacReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		DetailInformationSacRes response = this.sellerSearchService.detailInformationList(requestHeader, request);
		LOGGER.info("Response : seller count : {}", response.getSellerMbrListMap().size());
		return response;

	}
}
