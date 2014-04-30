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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.sci.HidingSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingDeviceScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingListSc;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScRes;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseTenantPolicy;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 구매 서비스 인터페이스 구현체
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@Service
public class HidingSacServiceImpl implements HidingSacService {
	private static Logger logger = LoggerFactory.getLogger(HidingSacServiceImpl.class);

	@Autowired
	private HidingSCI hidingSCI;
	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;

	/**
	 * 구매내역 숨김처리.
	 * 
	 * @param hidingReq
	 *            요청정보
	 * @return List<HidingScRes>
	 */
	@Override
	public List<HidingScRes> updateHiding(HidingScReq hidingScReq) {
		// TenantProdGrpCd(Device기반 모든정책 조회 )
		List<PurchaseTenantPolicy> purchaseTenantPolicyList = this.purchaseTenantPolicyService
				.searchPurchaseTenantPolicyList(hidingScReq.getTenantId(), "",
						PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, true);

		List<HidingDeviceScRes> resultList = this.hidingSCI.selectDevice(hidingScReq);
		this.logger.info("리턴 resultList.size: {}", resultList.size());
		for (HidingListSc hidingListSc : hidingScReq.getHidingList()) {
			this.logger.info("리턴 FLAG 11111: {}", hidingScReq.getHidingList().size());
			for (HidingDeviceScRes hidingDeviceScRes : resultList) {
				String flag = "ID";

				if (hidingDeviceScRes != null) {
					if (hidingListSc.getPrchsId().equals(hidingDeviceScRes.getPrchsId())) {
						// TenantProdGrpCd가 null일때는 ID기반으로 체크한다.
						if (StringUtils.isNotBlank(hidingDeviceScRes.getTenantProdGrpCd())) {

							flag = this.checkMdn(hidingScReq, hidingDeviceScRes, purchaseTenantPolicyList, flag);
							this.logger.info("리턴 FLAG : {}", flag);
							hidingListSc.setDeviceType(flag);

						} else {
							hidingListSc.setDeviceType(flag);
						}
					}
				} else {
					// msg:[SAC 구매] 조건에 맞는 데이터가 존재 하지 않습니다.
					throw new StorePlatformException("SAC_PUR_0100");
				}
			}
		}
		return this.hidingSCI.updateHiding(hidingScReq);
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
	private String checkMdn(HidingScReq hidingScReq, HidingDeviceScRes hidingDeviceScRes,
			List<PurchaseTenantPolicy> purchaseTenantPolicyList, String flag) {

		// flag의 값은 ID, MDN, NOT_MDN를 가진다
		// flag : ID => ID기반
		// flag : MDN => MDN기반
		// flag : NOT_MDN => 정책과 조회한 tenantProdGrpCd는 같지만 DeviceKey가 다른 경우는기구매체크에서 제외한다.
		// 정책조회된 것이 없으면 ID기반으로 정의한다.(2014-03-10)
		// 구매아이디로 조회시 devicekey가 null이면 ID기반으로 정의한다.(2014-03-10)

		if (purchaseTenantPolicyList.size() > 0) {
			for (PurchaseTenantPolicy purchaseTenantPolicy : purchaseTenantPolicyList) {

				String tenantProdGrpCd = hidingDeviceScRes.getTenantProdGrpCd();
				// 조회한 tenantProdGrpCd의 시작 코드와 정책코드가 같다면 MDN기반
				if (tenantProdGrpCd.startsWith(purchaseTenantPolicy.getTenantProdGrpCd())) {
					// 정책이 같을 경우에는 조회한 DeviceKey와 입력받은 DeviceKey를 비교하여 flag 셋팅
					if (hidingDeviceScRes.getUseInsdDeviceId().equals(hidingScReq.getDeviceKey())) {
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

		return flag;
	}
}
