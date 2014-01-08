package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;

@Service
public class SellerSearchServiceImpl implements SellerSearchService {

	private static final Logger logger = LoggerFactory.getLogger(SellerSearchServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Override
	public DuplicateByIdEmailRes duplicateByIdEmail(DuplicateByIdEmailReq req) {

		/** SC회원 시작 */
		/** 1. ID/Email Req 생성 및 주입 */
		CheckDuplicationSellerRequest checkDuplicationSellerRequest = new CheckDuplicationSellerRequest();

		/** TODO 2. 임시 공통헤더 생성 주입 */
		checkDuplicationSellerRequest.setCommonRequest(this.imsiCommonRequest());

		/**
		 * 검색 조건 타입 <br>
		 * INSD_USERMBR_NO : 내부 사용자 키 <br>
		 * MBR_ID : 사용자 ID <br>
		 * INSD_SELLERMBR_NO : 내부 판매자 키 <br>
		 * SELLERMBR_ID : 판매자 ID <br>
		 * USERMBR_NO : 통합서비스 키 <br>
		 * INSD_DEVICE_ID : 내부 기기 키 <br>
		 * DEVICE_ID : 기기 ID <br>
		 * EMAIL_ADDR : 사용자 이메일 <br>
		 * EMAIL : 판매자 이메일 <br>
		 * TEL_NO : 사용자 연락처 <br>
		 * WILS_TEL_NO : 판매자 연락처
		 */
		KeySearch keySearch = new KeySearch();
		if ("id".equals(req.getKeyType()))
			keySearch.setKeyType("SELLERMBR_ID");
		else if ("email".equals(req.getKeyType()))
			keySearch.setKeyType("EMAIL");
		keySearch.setKeyString(req.getKeyString());
		List<KeySearch> keySearchs = new ArrayList<KeySearch>();
		keySearchs.add(keySearch);

		checkDuplicationSellerRequest.setKeySearchList(keySearchs);

		/** 3. SC회원(ID/Email중복) Call */
		CheckDuplicationSellerResponse checkDuplicationSellerResponse = this.sellerSCI
				.checkDuplicationSeller(checkDuplicationSellerRequest);

		// Response Debug
		logger.info("UpdateStatusSellerResponse Code : {}", checkDuplicationSellerResponse.getCommonResponse()
				.getResultCode());
		logger.info("UpdateStatusSellerResponse Messge : {}", checkDuplicationSellerResponse.getCommonResponse()
				.getResultMessage());

		/** 4. Tenant Response 생성 및 주입 */
		DuplicateByIdEmailRes response = new DuplicateByIdEmailRes(checkDuplicationSellerResponse.getIsRegistered());

		return response;
	}

	@Override
	public DetailInformationRes detailInformation(DetailInformationReq req) {
		SearchSellerResponse result = new SearchSellerResponse();
		// logger.info("----" + req.getKeySearchList().get(0).getKeyString());
		/*
		 * req.getAid(); req.getClass(); req.getSellerKey();
		 */
		SearchSellerRequest request = new SearchSellerRequest();

		request.setCommonRequest(this.imsiCommonRequest());

		KeySearch keySearch = new KeySearch();
		keySearch.setKeyString(req.getSellerKey());
		keySearch.setKeyType("INSD_SELLERMBR_NO");
		List<KeySearch> list = new ArrayList<KeySearch>();
		list.add(keySearch);
		request.setKeySearchList(list);

		result = this.sellerSCI.searchSeller(request);

		DetailInformationRes response = new DetailInformationRes();
		List<ExtraRight> eList = new ArrayList<ExtraRight>();

		for (int i = 0; i < result.getExtraRight().size(); i++) {
			ExtraRight extraRight = new ExtraRight();
			extraRight.setEndDate(result.getExtraRight().get(i).getEndDate());
			extraRight.setRegDate(result.getExtraRight().get(i).getRegDate());
			extraRight.setRegID(result.getExtraRight().get(i).getRegID());
			extraRight.setRightProfileCode(result.getExtraRight().get(i).getRightProfileCode());
			extraRight.setSellerKey(result.getExtraRight().get(i).getSellerKey());
			extraRight.setSellerRate(result.getExtraRight().get(i).getSellerRate());
			extraRight.setStartDate(result.getExtraRight().get(i).getStartDate());
			extraRight.setTenantID(result.getExtraRight().get(i).getTenantID());
			extraRight.setTenantRate(result.getExtraRight().get(i).getTenantRate());
			extraRight.setUpdateDate(result.getExtraRight().get(i).getUpdateDate());
			extraRight.setUpdateID(result.getExtraRight().get(i).getUpdateID());
			eList.add(extraRight);
		}

		response.setExtraRight(eList);
		response.setSellerKey(result.getSellerKey());

		return response;

	}

	/**
	 * <pre>
	 * TODO 임시 SC회원 전달용 공통헤더
	 * </pre>
	 * 
	 * @return
	 */
	private CommonRequest imsiCommonRequest() {
		/** TODO 임시 공통헤더 생성 주입 */
		CommonRequest commonRequest = new CommonRequest();
		// S001(ShopClient), S002(WEB), S003(OpenAPI)
		commonRequest.setSystemID("S001");
		// TODO SC회원 문의?? - Reamine ID생성 규칙과 다름
		// T01(T-Store), T02(A-Store), T03(B-Store)
		// commonRequest.setTenantID("T01");
		commonRequest.setTenantID("S01");
		return commonRequest;
	}
}
