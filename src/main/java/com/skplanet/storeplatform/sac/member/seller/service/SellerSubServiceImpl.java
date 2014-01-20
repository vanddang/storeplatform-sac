package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateSubsellerRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.RemoveSubsellerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

@Service
@Transactional
public class SellerSubServiceImpl implements SellerSubService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSubServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

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

		CreateSubSellerResponse schRes = new CreateSubSellerResponse();
		CreateSubSellerRequest schReq = new CreateSubSellerRequest();

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			schReq.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			schReq.setCommonRequest(commonRequest);
		}
		schReq.setSellerKey(req.getSellerKey());
		schReq.setSubSellerID(req.getSubSellerID());
		schReq.setSubSellerPW(req.getSubSellerPW());
		schReq.setSubSellerMemo(req.getSubSellerMemo());
		schReq.setSubSellerEmail(req.getSubSellerEmail());

		schRes = this.sellerSCI.createSubSeller(schReq);

		CreateSubsellerRes response = new CreateSubsellerRes();

		response.setSubSellerKey(schRes.getSubSellerKey());

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

		RemoveSubSellerResponse schRes = new RemoveSubSellerResponse();
		RemoveSubSellerRequest schReq = new RemoveSubSellerRequest();

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			schReq.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			schReq.setCommonRequest(commonRequest);
		}

		schReq.setSellerKey(req.getSellerKey());
		// 최종 vo 에 값 셋팅
		List<String> removeKeyList;
		removeKeyList = new ArrayList<String>();
		removeKeyList.add("US201312311522096210000038");
		schReq.setSubSeller(removeKeyList);

		schRes = this.sellerSCI.removeSubSeller(schReq);

		RemoveSubsellerRes response = new RemoveSubsellerRes();

		response.setRemoveCnt(schRes.getDeletedNumber());

		return response;
	}

	/**
	 * <pre>
	 * TODO 임시 SC회원 전달용 공통헤더.
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
		// T01(T-Store), T02(A-Store), T03(B-Store) - ['S01' 데이터로 마이그레이션 작업 할 예정]
		commonRequest.setTenantID("S01");
		return commonRequest;
	}

}
