/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.common.sci;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.common.sci.PurchaseCommonSCI;
import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonCode;
import com.skplanet.storeplatform.purchase.common.service.PurchaseCommonService;

/**
 * 
 * 구매 Common 컨트롤러
 * 
 * Updated on : 2015. 3. 2. Updated by : 이승택, nTels.
 */
@LocalSCI
public class PurchaseCommonSCIController implements PurchaseCommonSCI {

	@Autowired
	private PurchaseCommonService purchaseCommonService;

	/**
	 * 
	 * <pre>
	 * 해당 공통 코드에 공통코드 조회.
	 * </pre>
	 * 
	 * @param cdIdList
	 *            조회할 공통 코드 목록
	 * @param langCd
	 *            언어 코드
	 * @return
	 */
	@Override
	public Map<String, PurchaseCommonCode> searchCommonCodeMap(List<String> cdIdList, String langCd) {
		return this.purchaseCommonService.searchCommonCodeMap(cdIdList, langCd);
	}

	/**
	 * 
	 * <pre>
	 * 공통 코드 그룹에 속하는 공통코드 조회.
	 * </pre>
	 * 
	 * @param grpCdId
	 *            공통 코드 그룹ID
	 * @param langCd
	 *            언어 코드
	 * @return
	 */
	@Override
	public Map<String, PurchaseCommonCode> searchCommonCodeMapByGrpCdId(String grpCdId, String langCd) {
		return this.purchaseCommonService.searchCommonCodeMapByGrpCdId(grpCdId, langCd);
	}

}
