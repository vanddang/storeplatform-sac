/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.sci;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScV2Req;
import com.skplanet.storeplatform.purchase.history.service.ExistenceScService;

/**
 * 구매 컴포넌트 인터페이스 컨트롤러
 * 
 * Updated on : 2013-12-20 Updated by : 조용진, 엔텔스.
 */
@LocalSCI
public class ExistenceSCIController implements ExistenceSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceScService existenceScService;

	/**
	 * 기구매 체크.
	 * 
	 * @param existenceScReq
	 *            요청정보
	 * @return List<ExistenceScRes>
	 */
	@Override
	public List<ExistenceScRes> searchExistenceList(ExistenceScReq existenceScReq) {
		this.logger.debug("PRCHS,ExistenceSCIController,SC,REQ,{}", existenceScReq);
		// 필수값 체크
		if (existenceScReq.getTenantId() == null || existenceScReq.getTenantId().equals("")) {
			throw new StorePlatformException("SC_PUR_0001", "tenantId");
		}
		if (StringUtils.isBlank(existenceScReq.getPrchsId())) {
			if (StringUtils.isBlank(existenceScReq.getUserKey())) {
				throw new StorePlatformException("SC_PUR_0001", "UserKey");
			}
			// 구매쪽에서 호출시 true값이 넘어 온다.
			if (existenceScReq.isCheckValue()) {
				if (StringUtils.isBlank(existenceScReq.getDeviceKey())) {
					throw new StorePlatformException("SC_PUR_0001", "DeviceKey");
				}
			}
		}

		if (existenceScReq.getProductList() != null) {
			for (ExistenceItemSc existenceItemSac : existenceScReq.getProductList()) {
				if (StringUtils.isBlank(existenceItemSac.getProdId())) {
					throw new StorePlatformException("SC_PUR_0001", "ProdId");
				}
			}
		}

		return this.existenceScService.searchExistenceList(existenceScReq);
	}

	@Override
	public List<ExistenceScRes> searchExistenceListV2(ExistenceScV2Req existenceScV2Req) {
		return this.existenceScService.searchExistenceListV2(existenceScV2Req);
	}

}
