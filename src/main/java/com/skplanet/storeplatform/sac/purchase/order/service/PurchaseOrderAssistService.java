/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

/**
 * 
 * 구매 보조 서비스
 * 
 * Updated on : 2014. 6. 19. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderAssistService {

	/**
	 * <pre>
	 * 새로운 구매ID 생성.
	 * </pre>
	 * 
	 * @return 새로 생성된 구매ID
	 */
	public String makePrchsId(String sequence, String date);

	/**
	 * 
	 * <pre>
	 * 기준일로부터 이용 일자 계산.
	 * </pre>
	 * 
	 * @param startDt
	 *            기준일(시작일)
	 * @param periodUnitCd
	 *            이용기간 단위 코드
	 * @param periodVal
	 *            이용기간 값
	 * 
	 * @return 계산된 이용 일자
	 */
	public String calculateUseDate(String startDt, String periodUnitCd, String periodVal);

	/**
	 * 
	 * <pre>
	 * 기준일로부터 이용 일자 계산.
	 * </pre>
	 * 
	 * @param startDt
	 *            기준일(시작일)
	 * @param periodUnitCd
	 *            이용기간 단위 코드
	 * @param periodVal
	 *            이용기간 값
	 * @param bAutoPrchs
	 *            자동결제상품 여부
	 * 
	 * @return 계산된 이용 일자
	 */
	public String calculateUseDate(String startDt, String periodUnitCd, String periodVal, boolean bAutoPrchs);

	/**
	 * 
	 * <pre>
	 * DB PK 오류 여부 체크.
	 * </pre>
	 * 
	 * @param e
	 *            발생한 Exception 개체
	 * 
	 * @return DB PK 오류 여부
	 */
	public boolean isDuplicateKeyException(Exception e);
}
