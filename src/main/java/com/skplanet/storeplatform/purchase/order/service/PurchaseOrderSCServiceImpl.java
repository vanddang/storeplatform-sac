/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.order.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.DateUtils;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.SapNoti;
import com.skplanet.storeplatform.purchase.client.common.vo.UniqueTid;
import com.skplanet.storeplatform.purchase.client.order.vo.*;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScReq;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import com.skplanet.storeplatform.purchase.history.service.ExistenceScService;
import com.skplanet.storeplatform.purchase.membership.service.MembershipReserveSCService;
import com.skplanet.storeplatform.purchase.product.count.service.PurchaseCountSCService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * 구매SC - 구매 서비스 구현체
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderSCServiceImpl implements PurchaseOrderSCService {

	@Autowired
	private PurchaseOrderSearchSCService orderSearchScService;
	@Autowired
	private PurchaseCountSCService purchaseCountScService;
	@Autowired
	private MembershipReserveSCService membershipReserveScService;
	@Autowired
	private ExistenceScService existenceScService;

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	// ======================================================================================================

	/**
	 * 
	 * <pre>
	 * 구매 예약.
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매예약 정보 목록
	 * @return 예약된 갯수
	 */
	@Override
	public int executeReserve(List<PrchsDtlMore> prchsDtlMoreList) {

		// --------------------------------------------------------------
		// TB_구매상세 INSERT

		return this.insertPrchsDtl(prchsDtlMoreList);
	}

	/**
	 * 
	 * <pre>
	 * 무료 구매.
	 * </pre>
	 * 
	 * @param makeFreePurchaseScReq
	 *            무료구매 요청 VO
	 * @return 무료구매 처리된 갯수
	 */
	@Override
	public int executeFreePurchase(MakeFreePurchaseScReq makeFreePurchaseScReq) {

		// --------------------------------------------------------------
		// TB_건수집계 INSERT (중복구매 방지 역할 포함)

		if (CollectionUtils.isNotEmpty(makeFreePurchaseScReq.getPrchsProdCntList())) {
			this.purchaseCountScService.insertPurchaseProductCount(new InsertPurchaseProductCountScReq(
					makeFreePurchaseScReq.getPrchsProdCntList()));
		}

		// --------------------------------------------------------------
		// TB_결제 INSERT

		if (CollectionUtils.isNotEmpty(makeFreePurchaseScReq.getPaymentList())) {
			this.insertPayment(makeFreePurchaseScReq.getPaymentList());
		}

		// --------------------------------------------------------------
		// TB_구매 INSERT

		PrchsDtlMore prchsDtlMore = makeFreePurchaseScReq.getPrchsDtlMoreList().get(0);

		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseCDConstants.PRCHS_CASE_GIFT_CD)) {
			prchsDtlMore.setInsdUsermbrNo(prchsDtlMore.getSendInsdUsermbrNo());
			prchsDtlMore.setInsdDeviceId(prchsDtlMore.getSendInsdDeviceId());
		} else {
			prchsDtlMore.setInsdUsermbrNo(prchsDtlMore.getUseInsdUsermbrNo());
			prchsDtlMore.setInsdDeviceId(prchsDtlMore.getUseInsdDeviceId());
		}

		this.insertPrchs(prchsDtlMore);

		// --------------------------------------------------------------
		// TB_구매상세 INSERT (구매완료)

		int cnt = this.insertPrchsDtl(makeFreePurchaseScReq.getPrchsDtlMoreList());

		// --------------------------------------------------------------
		// TB_구매상세 INSERT - 정액제 하위 에피소드 상품 목록 일괄 구매이력 생성
		if (CollectionUtils.isNotEmpty(makeFreePurchaseScReq.getPackageEpisodeList())) {
			// 정액제 구매이력 생성 정보 조회
			List<PrchsDtlMore> prchsDtlMoreList = this.orderSearchScService.searchPurchaseDtlListByStatus(
					prchsDtlMore.getTenantId(), prchsDtlMore.getPrchsId(), prchsDtlMore.getUseTenantId(),
					prchsDtlMore.getUseInsdUsermbrNo(), PurchaseCDConstants.PRCHS_STATUS_COMPT);

			// 하위 에피소드 구매이력 생성
			this.executeAddPackageEpisodeList(makeFreePurchaseScReq.getPackageEpisodeList(), prchsDtlMoreList.get(0)
					.getUseStartDt(), prchsDtlMoreList.get(0).getUseExprDt(), prchsDtlMoreList.get(0).getDwldStartDt(),
					prchsDtlMoreList.get(0).getDwldExprDt());
		}

		return cnt;
	}

	/**
	 * 
	 * <pre>
	 * 구매 확정.
	 * </pre>
	 * 
	 * @param confirmPurchaseScReq
	 *            구매확정 요청 VO
	 * @return 구매확정된 갯수
	 */
	@Override
	public int executeConfirmPurchase(ConfirmPurchaseScReq confirmPurchaseScReq) {
		// --------------------------------------------------------------
		// TB_구매상세 SELECT: 예약된 구매정보 조회

		List<PrchsDtlMore> prchsDtlMoreList = this.orderSearchScService.searchPurchaseDtlListByStatus(
				confirmPurchaseScReq.getTenantId(), confirmPurchaseScReq.getPrchsId(),
				confirmPurchaseScReq.getUseTenantId(), confirmPurchaseScReq.getUseInsdUsermbrNo(),
				PurchaseCDConstants.PRCHS_STATUS_RESERVATION);

		if (prchsDtlMoreList.size() < 1) {
			throw new StorePlatformException("SC_PUR_7101");
		}

		for (PrchsDtlMore prchsDtlMore : prchsDtlMoreList)
			prchsDtlMore.setPrchsDt(confirmPurchaseScReq.getPrchsDt());

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);
		prchsDtlMore.setStatusCd(PurchaseCDConstants.PRCHS_STATUS_COMPT); // 구매완료 상태로 세팅
		prchsDtlMore.setSystemId(confirmPurchaseScReq.getSystemId());
		prchsDtlMore.setMediId(confirmPurchaseScReq.getMediaId());

		// --------------------------------------------------------------
		// TB_건수집계 INSERT (중복구매 방지 역할 포함)

		if (CollectionUtils.isNotEmpty(confirmPurchaseScReq.getPrchsProdCntList())) {
			this.purchaseCountScService.insertPurchaseProductCount(new InsertPurchaseProductCountScReq(
					confirmPurchaseScReq.getPrchsProdCntList()));
		}

		// --------------------------------------------------------------
		// TB_결제 INSERT

		this.insertPayment(confirmPurchaseScReq.getPaymentList());

		// --------------------------------------------------------------
		// TB_구매 INSERT (tb_pr_prchs)

		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseCDConstants.PRCHS_CASE_GIFT_CD)) {
			prchsDtlMore.setInsdUsermbrNo(prchsDtlMore.getSendInsdUsermbrNo());
			prchsDtlMore.setInsdDeviceId(prchsDtlMore.getSendInsdDeviceId());
		} else {
			prchsDtlMore.setInsdUsermbrNo(prchsDtlMore.getUseInsdUsermbrNo());
			prchsDtlMore.setInsdDeviceId(prchsDtlMore.getUseInsdDeviceId());
		}
		prchsDtlMore.setNetworkTypeCd(confirmPurchaseScReq.getNetworkTypeCd());

		this.insertPrchs(prchsDtlMore);

		// --------------------------------------------------------------
		// TB_자동구매 INSERT

		if (CollectionUtils.isNotEmpty(confirmPurchaseScReq.getAutoPrchsMoreList())) {
			this.insertAutoPrchs(confirmPurchaseScReq.getAutoPrchsMoreList());
		}

		// --------------------------------------------------------------
		// TB_구매상세 UPDATE - 상태변경(구매완료), 기간변경

		// 캐쉬 경우, 충전 ID 저장 : 예비컬럼3 (게임 캐시, 북스 캐시)
		if (StringUtils.contains(prchsDtlMore.getTenantProdGrpCd(),
				PurchaseCDConstants.TENANT_PRODUCT_GROUP_SUFFIX_CASH)) {
			prchsDtlMore.setResvCol03(confirmPurchaseScReq.getCashInfo());
		}

		int count = this.updatePrchsDtlByStatus(prchsDtlMore, PurchaseCDConstants.PRCHS_STATUS_RESERVATION,
				PurchaseCDConstants.PRCHS_STATUS_COMPT, confirmPurchaseScReq.getOfferingId(),
				confirmPurchaseScReq.getShoppingCouponList(), confirmPurchaseScReq.getUseTenantId(),
				confirmPurchaseScReq.getUseInsdUsermbrNo());
		if (count < 1) {
			throw new StorePlatformException("SC_PUR_7101");
		}

		// --------------------------------------------------------------
		// TB_구매상세 INSERT - 정액제 하위 에피소드 상품 목록 일괄 구매이력 생성
		if (CollectionUtils.isNotEmpty(confirmPurchaseScReq.getPackageEpisodeList())) {
			this.executeAddPackageEpisodeList(confirmPurchaseScReq.getPackageEpisodeList(),
					prchsDtlMore.getUseStartDt(), prchsDtlMore.getUseExprDt(), prchsDtlMore.getDwldStartDt(),
					prchsDtlMore.getDwldExprDt());
		}

		// --------------------------------------------------------------
		// TB_멤버쉽 INSERT
		if (CollectionUtils.isNotEmpty(confirmPurchaseScReq.getMembershipReserveList())) {
			this.membershipReserveScService
					.insertMembershipReserveList(confirmPurchaseScReq.getMembershipReserveList());
		}

		return count;
	}

	/**
	 * 
	 * <pre>
	 * 구매/결제 통합 구매이력 생성.
	 * </pre>
	 * 
	 * @param createCompletePurchaseScReq
	 *            구매/결제 통합 구매이력 생성 요청 VO
	 * @return 구매/결제 통합 구매이력 생성 응답 VO
	 */
	@Override
	public int executeCompletePurchase(CreateCompletePurchaseScReq createCompletePurchaseScReq) {
		// --------------------------------------------------------------
		// TB_PR_UNIQUE_TID INSERT (중복구매 방지 역할 포함)

		this.insertUniqueTid(createCompletePurchaseScReq.getUniqueTid());

		// --------------------------------------------------------------
		// TB_건수집계 INSERT

		if (CollectionUtils.isNotEmpty(createCompletePurchaseScReq.getPrchsProdCntList())) {
			this.purchaseCountScService.insertPurchaseProductCount(new InsertPurchaseProductCountScReq(
					createCompletePurchaseScReq.getPrchsProdCntList()));
		}

		// --------------------------------------------------------------
		// TB_결제 INSERT

		this.insertPayment(createCompletePurchaseScReq.getPaymentList());

		// --------------------------------------------------------------
		// TB_구매 INSERT

		PrchsDtlMore prchsDtlMore = createCompletePurchaseScReq.getPrchsDtlMoreList().get(0);
		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseCDConstants.PRCHS_CASE_GIFT_CD)) {
			prchsDtlMore.setInsdUsermbrNo(prchsDtlMore.getSendInsdUsermbrNo());
			prchsDtlMore.setInsdDeviceId(prchsDtlMore.getSendInsdDeviceId());
		} else {
			prchsDtlMore.setInsdUsermbrNo(prchsDtlMore.getUseInsdUsermbrNo());
			prchsDtlMore.setInsdDeviceId(prchsDtlMore.getUseInsdDeviceId());
		}

		this.insertPrchs(prchsDtlMore);

		// --------------------------------------------------------------
		// TB_구매상세 INSERT (구매완료)

		int count = this.insertPrchsDtl(createCompletePurchaseScReq.getPrchsDtlMoreList());

		// --------------------------------------------------------------
		// TB_멤버쉽 INSERT
		if (CollectionUtils.isNotEmpty(createCompletePurchaseScReq.getMembershipReserveList())) {
			this.membershipReserveScService.insertMembershipReserveList(createCompletePurchaseScReq
					.getMembershipReserveList());
		}

		return count;
	}

	// ======================================================================================================

	/**
	 * 
	 * <pre>
	 * 구매 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMore
	 *            구매 정보
	 * 
	 * @return 생성한 갯수
	 */
	@Override
	public int insertPrchs(PrchsDtlMore prchsDtlMore) {
		PrchsDtlMore prchs = new PrchsDtlMore();
		prchs.setTenantId(prchsDtlMore.getTenantId());
		prchs.setPrchsId(prchsDtlMore.getPrchsId());
		prchs.setInsdUsermbrNo(prchsDtlMore.getInsdUsermbrNo());
		prchs.setInsdDeviceId(prchsDtlMore.getInsdDeviceId());
		prchs.setPrchsDt(prchsDtlMore.getPrchsDt());
		prchs.setStatusCd(prchsDtlMore.getStatusCd());
		prchs.setTotAmt(prchsDtlMore.getTotAmt());
		prchs.setPrchsReqPathCd(prchsDtlMore.getPrchsReqPathCd());
		prchs.setCurrencyCd(prchsDtlMore.getCurrencyCd());
		prchs.setCancelReqPathCd(prchsDtlMore.getCancelReqPathCd());
		prchs.setCancelDt(prchsDtlMore.getCancelDt());
		prchs.setClientIp(prchsDtlMore.getClientIp());
		prchs.setPrchsCaseCd(prchsDtlMore.getPrchsCaseCd());
		prchs.setNetworkTypeCd(prchsDtlMore.getNetworkTypeCd());
		prchs.setMediId(prchsDtlMore.getMediId());
		prchs.setRegId(prchsDtlMore.getRegId());
		prchs.setUpdId(prchsDtlMore.getUpdId());
		// prchs.setResvCol01(null);
		// prchs.setResvCol02(null);
		// prchs.setResvCol03(null);
		// prchs.setResvCol04(null);
		// prchs.setResvCol05(null);

		// 정액권을 이용한 구매 시, 구매 예약 필드1에 결제수단코드 저장
		if (StringUtils.isNotBlank(prchsDtlMore.getUseFixrateProdId())) {
			String useFixrateProdClsfCd = prchsDtlMore.getUseFixrateProdClsfCd();

			if (StringUtils.startsWith(useFixrateProdClsfCd, PurchaseCDConstants.FIXRATE_PROD_TYPE_FIXRATE)) {
				prchs.setResvCol01(PurchaseCDConstants.PAYMENT_METHOD_FIXRATE);

			} else if (StringUtils.startsWith(useFixrateProdClsfCd, PurchaseCDConstants.FIXRATE_PROD_TYPE_SERIESPASS)) {
				prchs.setResvCol01(PurchaseCDConstants.PAYMENT_METHOD_SERIESPASS);

			}
		}

		return (Integer) this.commonDAO.insert("PurchaseOrder.insertPrchs", prchs);
	}

	/**
	 * 
	 * <pre>
	 * 구매상세 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매상세 정보 리스트
	 * 
	 * @return 생성한 갯수
	 */
	@Override
	public int insertPrchsDtl(List<PrchsDtlMore> prchsDtlMoreList) {
		int insertCnt = 0;

		for (PrchsDtlMore prchsDtlMore : prchsDtlMoreList) {

			if (CollectionUtils.isNotEmpty(prchsDtlMore.getReceiverList())) { // Biz 쿠폰
				int dtlCnt = 1;
				for (PurchaseUserInfo receiver : prchsDtlMore.getReceiverList()) {
					prchsDtlMore.setPrchsDtlId(dtlCnt++);
					prchsDtlMore.setUseInsdUsermbrNo(receiver.getUserKey());
					prchsDtlMore.setUseInsdDeviceId(receiver.getDeviceKey());
					insertCnt += (Integer) this.commonDAO.insert("PurchaseOrder.insertPrchsDtl", prchsDtlMore);
				}

			} else {
				insertCnt += (Integer) this.commonDAO.insert("PurchaseOrder.insertPrchsDtl", prchsDtlMore);
			}
		}

		return insertCnt;
	}

	/**
	 * 
	 * <pre>
	 * 결제 생성.
	 * </pre>
	 * 
	 * @param paymentList
	 *            결제 정보 리스트
	 * 
	 * @return 생성된 건수
	 */
	@Override
	public int insertPayment(List<Payment> paymentList) {
		int cnt = 1, insertCnt = 0;

		for (Payment payment : paymentList) {
			payment.setPaymentDtlId(cnt++);
			insertCnt += (Integer) this.commonDAO.insert("PurchaseOrder.insertPayment", payment);
		}

		return insertCnt;
	}

	/**
	 * 
	 * <pre>
	 * 신규 자동구매 생성.
	 * </pre>
	 * 
	 * @param autoPrchsMoreList
	 *            자동구매 생성 목록
	 * 
	 * @return 자동구매 생성 건수
	 */
	@Override
	public int insertAutoPrchs(List<AutoPrchsMore> autoPrchsMoreList) {
		int insertCnt = 0;

		for (AutoPrchsMore autoPrchsMore : autoPrchsMoreList) {
			insertCnt += (Integer) this.commonDAO.insert("PurchaseOrder.insertAutoPrchs", autoPrchsMore);
		}

		return insertCnt;
	}

	@Override
	public String selectCurrentDate() {
		return (String) this.commonDAO.queryForObject("PurchaseOrder.selectSysdate", null);
	}

	/**
	 * 
	 * <pre>
	 * 결제TID Unique 정보 생성.
	 * </pre>
	 * 
	 * @param uniqueTid
	 *            결제TID Unique 정보
	 * 
	 * @return 결제TID Unique 정보 생성 건수
	 */
	@Override
	public int insertUniqueTid(UniqueTid uniqueTid) {
		return (Integer) this.commonDAO.insert("PurchaseOrder.insertUniqueTid", uniqueTid);
	}

	/**
	 * <pre>
	 * 구매상세 변경.
	 * </pre>
	 * 
	 * @param prchsDtlMoreParam
	 *            변경할 구매상세 정보
	 * @param oldStatusCd
	 *            현재 구매상태코드
	 * @param newStatusCd
	 *            변경할 구매상태코드
	 * @param offeringId
	 *            오퍼링 ID
	 * @param shoppingCouponList
	 *            쇼핑발급 목록
	 * @param useTenantId
	 *            구매DB 파티션 처리용 - 이용자 테넌트ID
	 * @param useInsdUsermbrNo
	 *            구매DB 파티션 처리용 - 이용자 회원 내부관리번호
	 * @return 변경된 건수
	 */
	@Override
	public int updatePrchsDtlByStatus(PrchsDtlMore prchsDtlMoreParam, String oldStatusCd, String newStatusCd,
			String offeringId, List<ShoppingCouponPublishInfo> shoppingCouponList, String useTenantId,
			String useInsdUsermbrNo) {

		PrchsDtlMore prchsDtlMore = new PrchsDtlMore();
		prchsDtlMore.setTenantId(prchsDtlMoreParam.getTenantId());
		prchsDtlMore.setUseTenantId(useTenantId);
		prchsDtlMore.setUseInsdUsermbrNo(useInsdUsermbrNo);
		prchsDtlMore.setPrchsId(prchsDtlMoreParam.getPrchsId());
		prchsDtlMore.setSystemId(prchsDtlMoreParam.getSystemId());
		prchsDtlMore.setOldStatusCd(oldStatusCd);
		prchsDtlMore.setStatusCd(newStatusCd);
		prchsDtlMore.setResvCol01(offeringId); // 오퍼링ID
		prchsDtlMore.setResvCol03(prchsDtlMoreParam.getResvCol03()); // 게임캐쉬 충전ID 정보
		prchsDtlMore.setPrchsDt(prchsDtlMoreParam.getPrchsDt());

		if (CollectionUtils.isEmpty(shoppingCouponList)) {

			return this.commonDAO.update("PurchaseOrder.updatePrchsDtlByStatus", prchsDtlMore);

		} else {
			int updCnt = 0;

			for (ShoppingCouponPublishInfo couponInfo : shoppingCouponList) {
				prchsDtlMore.setPrchsDtlId(couponInfo.getPrchsDtlId());

				prchsDtlMore.setUseStartDt(couponInfo.getAvailStartDt());
				prchsDtlMore.setUseExprDt(couponInfo.getAvailEndDt());
				prchsDtlMore.setDwldStartDt(couponInfo.getAvailStartDt());
				prchsDtlMore.setDwldExprDt(couponInfo.getAvailEndDt());

				prchsDtlMore.setCpnPublishCd(couponInfo.getPublishCode());
				prchsDtlMore.setCpnDlvUrl(couponInfo.getShippingUrl());
				prchsDtlMore.setCpnAddInfo(couponInfo.getAddInfo());
				prchsDtlMore.setCpnBizOrderNo(couponInfo.getBizOrderNo());
				prchsDtlMore.setCpnBizProdSeq(couponInfo.getBizSeq());
				prchsDtlMore.setPrchsDt(prchsDtlMoreParam.getPrchsDt());

				updCnt += this.commonDAO.update("PurchaseOrder.updatePrchsDtlByStatus", prchsDtlMore);
			}

			return updCnt;
		}
	}

	/**
	 * 
	 * <pre>
	 * SAP 결제완료Noti 생성.
	 * </pre>
	 * 
	 * @param sapNotiList
	 *            SAP 결제완료Noti 생성 목록
	 * @return SAP 결제완료Noti 생성 건수
	 */
	@Override
	public int insertSapNoti(List<SapNoti> sapNotiList) {
		int insertCnt = 0;

		for (SapNoti sapNoti : sapNotiList) {
			insertCnt += (Integer) this.commonDAO.insert("PurchaseOrder.insertSapNoti", sapNoti);
		}

		return insertCnt;
	}

	/**
	 * 
	 * <pre>
	 * 이북/코믹 전권 소장/대여 에피소드 상품 별 : 기구매 및 유효기간 체크 - 유효기간 짧은 기구매 건 종료일시 업데이트 - 신규 구매이력 생성.
	 * </pre>
	 * 
	 * @param episodeList
	 *            에피소드 상품 목록
	 * @param useStartDt
	 *            이용 시작일시
	 * @param useExprDt
	 *            이용 종료일시
	 * @param dwldStartDt
	 *            다운로드 시작일시
	 * @param dwldExprDt
	 *            다운로드 종료일시
	 * @return 신규 구매이력 생성한 에피소드 상품 건수
	 */
	public int executeAddPackageEpisodeList(List<PrchsDtlMore> episodeList, String useStartDt, String useExprDt,
			String dwldStartDt, String dwldExprDt) {

		List<PrchsDtlMore> insertList = new ArrayList<PrchsDtlMore>();

		String exprDt = null;

		for (PrchsDtlMore episode : episodeList) {
			// T프리미엄 경우, 정액제 구매종료 일시가 현 단계에서 생성 - SAC에서 미리 체크 불가
			// 다운로드 만료일시의 최대값은 정액권의 이용종료일시
			// 2015.05: 이용 만료일시도 동일 처리
			exprDt = this.calculateUseDate(useStartDt, episode.getUsePeriodUnitCd(), episode.getUsePeriod(), false);
			if (exprDt.compareTo(useExprDt) > 0) {
				episode.setUseExprDt(useExprDt);
				episode.setDwldExprDt(useExprDt);
			}

			// 기구매 조회
			if (CollectionUtils.isEmpty(this.commonDAO.queryForList("PurchaseOrder.selectExistenceEpisode", episode,
					PrchsDtlMore.class))) {
				episode.setUseStartDt(useStartDt);
				episode.setDwldStartDt(dwldStartDt);

				insertList.add(episode); // 신규 구매 목록에 추가
			}
		}

		// 순번 재세팅 및 cid null세팅
		int i = 2;
		for (PrchsDtlMore insertInfo : insertList) {
			insertInfo.setPrchsDtlId(i++);
			insertInfo.setCid(null);
		}

		// 신규 구매이력 생성
		return this.insertPrchsDtl(insertList);
	}

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
	private String calculateUseDate(String startDt, String periodUnitCd, String periodVal, boolean bAutoPrchs) {
		if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED)) { // 무제한
			return PurchaseCDConstants.UNLIMITED_DATE;
		} else if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_SELECT)) { // 기간선택
			return periodVal;
		} else if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_CURR_DATE)) { // 당일
			return startDt.substring(0, 8) + "235959";
		} else if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_CURR_YEAR)) { // 당년
			return startDt.substring(0, 4) + "1231235959";
		} else {
			Date checkDate = null;
			try {
				checkDate = DateUtils.parseDate(startDt, "yyyyMMddHHmmss");
			} catch (ParseException e) {
				throw new StorePlatformException("SC_PUR_7216", startDt);
			}

			if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_DATE)) { // 일
				checkDate = DateUtils.addDays(checkDate, Integer.parseInt(periodVal));
			} else if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_HOUR)) { // 시간
				checkDate = DateUtils.addHours(checkDate, Integer.parseInt(periodVal));
			} else if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_MONTH)) { // 월
				checkDate = DateUtils.addMonths(checkDate, Integer.parseInt(periodVal));
			} else if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_YEAR)) { // 년
				checkDate = DateUtils.addYears(checkDate, Integer.parseInt(periodVal));
			} else if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_CURR_MONTH)) { // 당월
				checkDate = DateUtils.ceiling(checkDate, Calendar.MONTH);
			} else if (StringUtils.equals(periodUnitCd, PurchaseCDConstants.PRODUCT_USE_PERIOD_UNIT_MINUTE)) { // 분
				checkDate = DateUtils.addMinutes(checkDate, Integer.parseInt(periodVal));
			} else {
				throw new StorePlatformException("SC_PUR_7215", periodUnitCd);
			}

			if (bAutoPrchs) {
				checkDate = DateUtils.addHours(DateUtils.truncate(checkDate, Calendar.DATE), 10);
			}

			return DateFormatUtils.format(checkDate, "yyyyMMddHHmmss");
		}
	}
}
