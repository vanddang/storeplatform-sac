/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.product.count.service;

import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.product.count.vo.*;

/**
 * 
 * 구매 상품 건수 처리 서비스
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseCountSCServiceImpl implements PurchaseCountSCService {
	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 
	 * <pre>
	 * 상품 건수 저장.
	 * </pre>
	 * 
	 * @param req
	 *            상품 건수 저장 요청 VO
	 * @return 추가한 상품 갯수
	 */
	@Override
	public int insertPurchaseProductCount(InsertPurchaseProductCountScReq req) {
		int insertCount = 0;

		for (PrchsProdCnt countInfo : req.getPrchsProdCntList()) {
			if (StringUtils.equals(countInfo.getStatusCd(), PurchaseCDConstants.PRCHS_STATUS_CANCEL)
					|| StringUtils.equals(countInfo.getStatusCd(), PurchaseCDConstants.PRCHS_STATUS_FAIL)) {
				this.commonDAO.delete("PurchaseProductCount.deletePrchsProdCnt", countInfo); // 구매 데이터 삭제
			}
			insertCount += (Integer) this.commonDAO
					.insert("PurchaseProductCount.insertPurchaseProductCount", countInfo);
		}
		return insertCount;
	}

	@Override
	public UpdatePrchsProdCntProcStatusScRes updatePrchsProdCntProcStatus(
			UpdatePrchsProdCntProcStatusScReq updatePrchsProdCntProcStatusScReq) {

		UpdatePrchsProdCntProcStatusScRes updatePrchsProdCntProcStatusScRes = new UpdatePrchsProdCntProcStatusScRes();
		updatePrchsProdCntProcStatusScRes.setResultCnt(this.commonDAO.update("PurchaseProductCount.updatePrchsProdCnt",
				updatePrchsProdCntProcStatusScReq));

		return updatePrchsProdCntProcStatusScRes;

	}

	@Override
	public GetPrchsProdCntScRes getPrchsProdCnt(GetPrchsProdCntScReq getPrchsProdCntScReq) {

		GetPrchsProdCntScRes getPrchsProdCntScRes = new GetPrchsProdCntScRes();

		this.commonDAO.update("PurchaseProductCount.updatePrchsProdCntByGroupBy", getPrchsProdCntScReq);

		getPrchsProdCntScRes.setPrchsProdCntList(this.commonDAO.queryForList("PurchaseProductCount.getPrchsProdCnt",
				getPrchsProdCntScReq, PrchsProdCnt.class));

		return getPrchsProdCntScRes;
	}
}
