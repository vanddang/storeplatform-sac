/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.freepass.service;

import java.util.List;

import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassSeriesReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassSpecificReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.SeriespassListRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePass;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Freepass Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 7. Updated by : 서영배, GTSOFT.
 */
public interface FreepassService {
	/**
	 * <pre>
	 * 자융이용권 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes FreepassListRes
	 */
	FreepassListRes searchFreepassList(FreepassListReq req, SacRequestHeader header);

	/**
	 * <pre>
	 * 자융이용권 상품 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes
	 */
	FreepassDetailRes searchFreepassDetail(FreepassDetailReq req, SacRequestHeader header);

	/**
	 * <pre>
	 * 자융이용권 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes
	 */
	SeriespassListRes searchSeriesPassList(FreepassSeriesReq req, SacRequestHeader header);

	/**
	 * <pre>
	 * 특정 상품에 적용할 자유 이용권 조회.
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes
	 */
	FreepassListRes searchFreepassListByChannel(FreepassSpecificReq req, SacRequestHeader header);

	/**
	 * <pre>
	 * 특정 상품에 적용할 자유 이용권 조회 V2.
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes
	 */
	FreepassListRes searchFreepassListByChannelV2(FreepassSpecificReq req, SacRequestHeader header);

	/**
	 * 이용가능한 정액권목록 구매내역 필요한 정보.
	 * 
	 *
     * @param tenantId
     * @param langCd
     *@param prodId @return List<String>
	 */
	public List<String> getAvailableFixrateProdIdList(String tenantId, String langCd, String prodId);
	
	/**
	 * 이용가능한 정액권목록 구매내역 필요한 정보.
	 * 
	 *
     * @param tenantId
     * @param langCd
     *@param prodId @return List<String>
	 */
	public List<FreePass> getAvailableFixrateInfoList(String tenantId, String langCd, String prodId);

	/**
	 * 구매내역 필요한 정보.
	 * 
	 *
     * @param tenantId
     * @param langCd
     *@param deviceModelCd
     * @param prodId @return List<PaymentInfo>
	 */
    @Deprecated
	public PaymentInfo getFreePassforPayment(String tenantId, String langCd, String deviceModelCd, String prodId);

	/**
	 * <pre>
	 * 자융이용권 목록 조회 (V2 버전).
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes FreepassListRes
	 */
	FreepassListRes searchFreepassListV2(FreepassListReq req, SacRequestHeader header);

	/**
	 * <pre>
	 * 자융이용권 상품 목록 조회 (V2 버전).
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes
	 */
	FreepassDetailRes searchFreepassDetailV2(FreepassDetailReq req, SacRequestHeader header);

	/**
	 * <pre>
	 * 자융이용권 상품 리스트 조회 (V2버전).
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes
	 */
	SeriespassListRes searchSeriesPassListV2(FreepassSeriesReq req, SacRequestHeader header);

	/**
	 * <pre>
	 * 특정 상품에 적용할 자유 이용권 조회 (V3 버전).
	 * </pre>
	 * 
	 * @param req
	 *            FreepassListReq
	 * @param header
	 *            SacRequestHeader
	 * @return FreepassListRes
	 */
	FreepassListRes searchFreepassListByChannelV3(FreepassSpecificReq req, SacRequestHeader header);
}
