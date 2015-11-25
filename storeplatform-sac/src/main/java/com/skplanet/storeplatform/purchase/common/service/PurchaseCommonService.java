package com.skplanet.storeplatform.purchase.common.service;

import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonCode;

public interface PurchaseCommonService {

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
	public Map<String, PurchaseCommonCode> searchCommonCodeMap(List<String> cdIdList, String langCd);

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
	public Map<String, PurchaseCommonCode> searchCommonCodeMapByGrpCdId(String grpCdId, String langCd);
}
