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

import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.order.vo.AutoPrchsMore;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.MileageSubInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;

/**
 * 
 * 구매정보 생성 서비스
 * 
 * Updated on : 2014. 5. 29. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderMakeDataService {

	/**
	 * <pre>
	 * 구매생성을 위한 데이터 목록 생성.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	public List<PrchsDtlMore> makePrchsDtlMoreList(PurchaseOrderInfo purchaseOrderInfo);

	/**
	 * 
	 * <pre>
	 * 결제내역 생성 목록 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMore
	 *            구매정보
	 * 
	 * @param paymentInfoList
	 *            결제이력 생성 정보
	 * 
	 * @param statusCd
	 *            구매상태코드
	 * 
	 * @return 결제내역 생성 목록
	 */
	public List<Payment> makePaymentList(PrchsDtlMore prchsDtlMore, List<PaymentInfo> paymentInfoList, String statusCd);

	/**
	 * 
	 * <pre>
	 * 상품 건수 저장을 위한 목록 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매 정보 목록
	 * 
	 * @param statusCd
	 *            구매상태코드
	 * 
	 * @return 상품 건수 저장을 위한 목록
	 */
	public List<PrchsProdCnt> makePrchsProdCntList(List<PrchsDtlMore> prchsDtlMoreList, String statusCd);

	/**
	 * 
	 * <pre>
	 * 자동구매 생성을 위한 목록 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMore
	 *            구매생성 정보
	 * 
	 * @param deviceModelCd
	 *            구매 단말 모델 코드
	 * 
	 * @param autoLastPeriod
	 *            자동결제 지속일 수
	 * 
	 * @return 자동구매 생성을 위한 목록
	 */
	public List<AutoPrchsMore> makeAutoPrchsMoreList(PrchsDtlMore prchsDtlMore, String deviceModelCd,
			String autoLastPeriod);

	/**
	 * 
	 * <pre>
	 * 멤버쉽 적립을 위한 목록 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMore
	 *            구매생성 정보
	 * 
	 * @param mileageSubInfo
	 *            멤버쉽 정보
	 * 
	 * @return 멤버쉽 적립을 위한 목록
	 */
	public List<MembershipReserve> makeMembershipReserveList(List<PrchsDtlMore> prchsDtlMoreList,
			MileageSubInfo mileageSubInfo);

	/**
	 * <pre>
	 * 이북/코믹 전권 소장/대여 에피소드 상품 - 구매이력 생성 요청 데이터 목록 생성.
	 * </pre>
	 * 
	 * @param ebookflatInfo
	 *            이북/코믹 전권 소장/대여 구매 정보 VO
	 * 
	 * @param episodeList
	 *            이북/코믹 전권 소장/대여 에피소드 상품 정보 VO
	 * 
	 * @param cmpxProdClsfCd
	 *            정액권 타입 : 전권소장 / 전권대여
	 * 
	 * @return 이북/코믹 전권 소장/대여 에피소드 상품 - 구매이력 생성 요청 데이터 목록
	 */
	public List<PrchsDtlMore> makeEbookComicEpisodeList(PrchsDtlMore ebookflatInfo, List<EpisodeInfoRes> episodeList,
			String cmpxProdClsfCd);

	/**
	 * <pre>
	 * 구매생성을 위한 데이터 목록 생성.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	public void buildReservedData(PurchaseOrderInfo purchaseOrderInfo, List<PrchsDtlMore> prchsDtlMoreList);

	/**
	 * <pre>
	 * 구매예약시 예약컬럼에 저장해뒀던 추가 데이터들.
	 * </pre>
	 * 
	 * @param reservedData
	 *            구매예약시 저장해뒀던 추가 데이터들
	 * 
	 * @return 분리된 파라미터 Map
	 */
	public Map<String, String> parseReservedData(String reservedData);
}
