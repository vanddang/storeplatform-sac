/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonCode;

/**
 * 공통코드 처리 서비스
 * 
 * Updated on : 2014. 12. 3. Updated by : 이승택, nTels.
 */
@Service
public class CommonCodeServiceImpl implements CommonCodeService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

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
		Map<String, Object> qryParamMap = new HashMap<String, Object>();
		qryParamMap.put("cdIdList", cdIdList);
		qryParamMap.put("langCd", langCd);

		List<PurchaseCommonCode> commonCodeList = this.commonDao.queryForList("PurchaseSacCommon.searchCommonCodeList",
				qryParamMap, PurchaseCommonCode.class);
		if (CollectionUtils.isEmpty(commonCodeList)) {
			return new HashMap<String, PurchaseCommonCode>();
		}

		Map<String, PurchaseCommonCode> commonCodeMap = new HashMap<String, PurchaseCommonCode>();
		for (PurchaseCommonCode commonCode : commonCodeList) {
			commonCodeMap.put(commonCode.getCdId(), commonCode);
		}

		return commonCodeMap;
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
		Map<String, Object> qryParamMap = new HashMap<String, Object>();
		qryParamMap.put("grpCdId", grpCdId);
		qryParamMap.put("langCd", langCd);

		List<PurchaseCommonCode> commonCodeList = this.commonDao.queryForList(
				"PurchaseSacCommon.searchCommonCodeListByGrpCdId", qryParamMap, PurchaseCommonCode.class);
		if (CollectionUtils.isEmpty(commonCodeList)) {
			return new HashMap<String, PurchaseCommonCode>();
		}

		Map<String, PurchaseCommonCode> commonCodeMap = new HashMap<String, PurchaseCommonCode>();
		for (PurchaseCommonCode commonCode : commonCodeList) {
			commonCodeMap.put(commonCode.getCdId(), commonCode);
		}

		return commonCodeMap;
	}

}
