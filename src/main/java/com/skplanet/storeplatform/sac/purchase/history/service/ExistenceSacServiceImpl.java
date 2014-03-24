/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseTenantPolicy;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 기구매 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
public class ExistenceSacServiceImpl implements ExistenceSacService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSCI existenceSCI;

	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;

	/**
	 * 기구매 체크 SAC Service.
	 * 
	 * @param existenceScReq
	 *            요청
	 * @param inputValue
	 *            내외부 사용구분 내부 true 요청정보
	 * @return List<ExistenceScRes>
	 */
	@Override
	public List<ExistenceScRes> searchExistenceList(ExistenceScReq existenceScReq) {

		// 기구매내역 조회함
		List<ExistenceScRes> resultList = this.existenceSCI.searchExistenceList(existenceScReq);
		// 구매상태가 구매완료건만을 넣기 위한 리스트
		List<ExistenceScRes> existenceListScRes = new ArrayList<ExistenceScRes>();
		// 내부구매처리시 기구매 체크는 inputValue = true

		// TenantProdGrpCd(Device기반 모든정책 조회 )
		List<PurchaseTenantPolicy> purchaseTenantPolicyList = this.purchaseTenantPolicyService
				.searchPurchaseTenantPolicyList(existenceScReq.getTenantId(), "",
						PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, true);

		for (ExistenceScRes existenceScRes : resultList) {
			String flag = "";
			this.logger.debug("existenceScRes.getStatusCd() : {}", existenceScRes.getStatusCd());

			if (existenceScRes.getStatusCd() != null
					&& existenceScRes.getStatusCd().equals(PurchaseConstants.PRCHS_STATUS_COMPT)) {

				// TenantProdGrpCd가 null일때는 ID기반으로 체크한다.
				if (existenceScRes.getTenantProdGrpCd() != null) {
					flag = this.checkMdn(existenceScReq, existenceScRes, purchaseTenantPolicyList, flag);
					this.logger.debug("리턴 FLAG : {}", flag);
					// flag가 ID 이거나 MDN일 경우에만 기구매셋팅
					if (flag.equals("ID") || flag.equals("MDN")) {
						existenceListScRes.add(existenceScRes);
					}
				} else {
					// TenantProdGrpCd가 null일때는 ID기반으로 체크한다.
					existenceListScRes.add(existenceScRes);
				}
			}
		}

		return existenceListScRes;
	}

	/**
	 * <pre>
	 * 기구매상품이 ID기반인지 MDN기반 인지 체크한다.
	 * </pre>
	 * 
	 * @param existenceScReq
	 *            구매요청값
	 * @param existenceScRes
	 *            구매응답값
	 * @param purchaseTenantPolicyList
	 *            정책리스트
	 * @param flag
	 *            정책구분
	 * @return String
	 */
	private String checkMdn(ExistenceScReq existenceScReq, ExistenceScRes existenceScRes,
			List<PurchaseTenantPolicy> purchaseTenantPolicyList, String flag) {

		// flag의 값은 ID, MDN, NOT_MDN를 가진다
		// flag : ID => ID기반
		// flag : MDN => MDN기반
		// flag : NOT_MDN => 정책과 조회한 tenantProdGrpCd는 같지만 DeviceKey가 다른 경우는기구매체크에서 제외한다.
		// 정책조회된 것이 없으면 ID기반으로 정의한다.(2014-03-10)
		// 구매아이디로 조회시 devicekey가 null이면 ID기반으로 정의한다.(2014-03-10)
		if (existenceScReq.getPrchsId() != null) {
			flag = "ID";
		} else {
			if (purchaseTenantPolicyList.size() > 0) {
				for (PurchaseTenantPolicy purchaseTenantPolicy : purchaseTenantPolicyList) {

					String tenantProdGrpCd = existenceScRes.getTenantProdGrpCd();
					// 조회한 tenantProdGrpCd의 시작 코드와 정책코드가 같다면 MDN기반
					if (tenantProdGrpCd.startsWith(purchaseTenantPolicy.getTenantProdGrpCd())) {
						// 정책이 같을 경우에는 조회한 DeviceKey와 입력받은 DeviceKey를 비교하여 flag 셋팅
						if (existenceScRes.getUseInsdDeviceId().equals(existenceScReq.getDeviceKey())) {
							// MDN기반이면 리턴을 한다.
							return flag = "MDN";
						} else {
							flag = "NOT_MDN";
						}
					} else {
						// 조회한 tenantProdGrpCd의 시작 코드와 정책코드가 다르면 ID기반
						if (!flag.equals("NOT_MDN")) {
							flag = "ID";
						}
					}
				}
			} else {
				// 정책 조회시 정책이 없는경우
				flag = "ID";
			}
		}
		return flag;
	}
}
