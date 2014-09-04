/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacV2Req;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.related.vo.BoughtTogetherProduct;
import org.springframework.stereotype.Service;

/*
 * BoughtTogetherProductTypeService 클래스
 * <pre>
 * Updated on 2014. 08. 07. by 서대영, SK 플래닛
 * </pre>
 */
@Service
public class BoughtTogetherProductTypeServiceImpl implements BoughtTogetherProductTypeService {

	@Override
	public BoughtTogetherProduct fromReq(BoughtTogetherProductSacReq req, SacRequestHeader header) {
		BoughtTogetherProduct vo = new  BoughtTogetherProduct();

		// from query string
		vo.setProductId(req.getProductId());
		vo.setExceptId(req.getExceptId());
		vo.setOffset(req.getOffset() != null ? req.getOffset() : 1);
		vo.setCount(req.getCount() != null ? req.getCount() : 20);

		// from header
		vo.setTenantId(header.getTenantHeader().getTenantId());
		vo.setDeviceModelCd(header.getDeviceHeader().getModel());
		vo.setMmDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// for v2
		if (req instanceof BoughtTogetherProductSacV2Req) {
			BoughtTogetherProductSacV2Req v2Req = (BoughtTogetherProductSacV2Req) req;
			// 주의! menuId 파라미터를 topMenuId 디비칼럼으로 맵핑함
			vo.setTopMenuId(v2Req.getMenuId());
		}

		return vo;
	}

}
