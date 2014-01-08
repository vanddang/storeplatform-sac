package com.skplanet.storeplatform.sac.member.seller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 1. 7. Updated by : 김경복, 부르칸.
 */
@Service
public class SellerServiceImpl implements SellerService {

	private static final Logger logger = LoggerFactory.getLogger(SellerServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Override
	public CreateRes createSeller() {

		CreateSellerResponse createSellerResponse = this.sellerSCI.createSeller(new CreateSellerRequest());
		logger.debug("code , {}" + createSellerResponse.getCommonResponse().getResultCode());

		return null;
	}

	@Override
	public LockAccountRes updateStatusSeller(LockAccountReq request) {

		UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
		updateStatusSellerRequest.setSellerID(request.getSellerId());
		// 일시정지
		updateStatusSellerRequest.setSellerMainStatus("US010204");
		// 일시정지
		updateStatusSellerRequest.setSellerSubStatus("US010307");

		UpdateStatusSellerResponse updateStatusSellerResponse = this.sellerSCI
				.updateStatusSeller(updateStatusSellerRequest);

		// Debug
		logger.info("Response CODE : {}", updateStatusSellerResponse.getCommonResponse().getResultCode());
		logger.info("Response MSG : {}", updateStatusSellerResponse.getCommonResponse().getResultMessage());

		LockAccountRes response = new LockAccountRes();
		response.setSellerKey(updateStatusSellerRequest.getSellerID());
		return response;
	}
}
