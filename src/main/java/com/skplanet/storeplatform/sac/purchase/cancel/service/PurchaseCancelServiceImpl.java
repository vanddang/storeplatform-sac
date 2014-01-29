/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.purchase.cancel.repository.PurchaseCancelRepository;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelParamDetail;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelResultDetail;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseDetail;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonParam;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 구매 취소 Service Implements.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Service
@Transactional
public class PurchaseCancelServiceImpl implements PurchaseCancelService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseCancelRepository purchaseCancelRepository;

	@Override
	public PurchaseCancelResult cancelPurchaseList(PurchaseCancelParam purchaseCancelParam) {

		PurchaseCancelResult purchaseCancelResult = new PurchaseCancelResult();
		List<PurchaseCancelResultDetail> prchsCancelResultList = new ArrayList<PurchaseCancelResultDetail>();
		int totCnt = 0;
		int successCnt = 0;
		int failCnt = 0;

		for (PurchaseCancelParamDetail purchaseCancelParamDetail : purchaseCancelParam.getPrchsCancelParamDetailList()) {
			totCnt++;
			PurchaseCancelResultDetail purchaseCancelResultDetail;

			try {

				purchaseCancelResultDetail = this.cancelPurchaseItem(purchaseCancelParam, purchaseCancelParamDetail);

			} catch (Exception e) {
				purchaseCancelResultDetail = new PurchaseCancelResultDetail();
				purchaseCancelResultDetail.setPrchsId(purchaseCancelParamDetail.getPrchsId());
				purchaseCancelResultDetail.setPrchsCancelResultCd("실패");
				purchaseCancelResultDetail.setPrchsCancelResultMsg("실패");
			}
			// TODO : 성공 코드값 및 메시지 정의 필요
			if ("성공".equals(StringUtil.setTrim(purchaseCancelResultDetail.getPrchsCancelResultCd()))) {
				successCnt++;
			} else {
				failCnt++;
			}
			prchsCancelResultList.add(purchaseCancelResultDetail);
		}

		purchaseCancelResult.setTotCnt(totCnt);
		purchaseCancelResult.setSuccessCnt(successCnt);
		purchaseCancelResult.setFailCnt(failCnt);
		purchaseCancelResult.setPrchsCancelResultList(prchsCancelResultList);

		return purchaseCancelResult;

	}

	@Override
	public PurchaseCancelResultDetail cancelPurchaseItem(PurchaseCommonParam purchaseCommonParam,
			PurchaseCancelParamDetail purchaseCancelParamDetail) {

		this.logger.debug("구매 취소 tr 시작");
		PurchaseCancelResultDetail purchaseCancelResultDetail = new PurchaseCancelResultDetail();

		// DB 조회
		purchaseCancelParamDetail = this.purchaseCancelRepository.getPurchaseDtlHistoryList(purchaseCommonParam,
				purchaseCancelParamDetail);

		List<PurchaseDetail> purchaseDetailList = purchaseCancelParamDetail.getPurchaseDetailList();

		// 회원과 구매내역의 회원과 일치 하던 안하던 상관없이 그냥 구매ID단위로 무조껀 취소? ADMIN일경우는 그게 맞는데.. 개인회원이면.. 어케할려고..
		// 조회 된 구매가 정상인지 확인.
		for (PurchaseDetail purchaseDetail : purchaseDetailList) {
			if (!PurchaseConstants.PRCHS_STATUS_COMPT.equals(StringUtil.setTrim(purchaseDetail.getStatusCd()))) {
				// 구매 완료가 아니면 구매 취소 처리 불가!
				throw new StorePlatformException("구매 취소 처리 불가!");
			}

			// TODO : TSTORE CASH 잔량 확인해야 하나? 아니면 PP에서 확인해야하나? 확인필요.

			if (PurchaseConstants.PRCHS_PROD_TYPE_FIX.equals(StringUtil.setTrim(purchaseDetail.getPrchsProdType()))) {
				// 정액권 상품이면 이용내역 없는지 확인.
				// TODO : 정액권 상품 컬럼 생기면 확인.
				;
			}
		}

		// TODO : TEST MDN 확인. 요건 어케 할지 생각 좀 해봐야 함..

		// TODO : payment 테이블 가져와야 함? 결제 취소 후 머 벨리데이션 해야하나? payPlanet 연동 규격서 확인 후 작업..
		// TODO : payPlanet에 취소 요청

		// TODO : 쇼핑 쿠폰이면 처리 .. 쇼핑 쿠폰 처리 위치가 변경 될 수 있음.. 구 쿠폰의경우 PP 후에 처리 되야 할꺼고..
		// TODO : 신 쿠폰의 경우 PP에서 한도체크나 다날체크를 하면 쇼핑쿠폰처리가 뒤에 오고..
		// TODO : PP에서 한도체크나 다날체크 안하고 SAC에서 해야하면 쇼핑쿠폰처리가 앞에 오고..? 쇼핑쿠폰은 실물쿠폰인데 결제취소만 되고 쿠폰은 취소 안되어도 컴플레인이 없나?
		/*
		 * 티스토어 쇼핑 구매취소불가. CASE 4 : 결제 방식이 다날결제 이면서 결제월과 취소월이 틀릴 경우 티스토어 쇼핑 구매취소불가. CASE 5 : 결제 방식이 SK 후불 이면서 한도상품 가입자의
		 * 경우. 티스토어 쇼핑 구매취소불가. CASE 6 : CMS 쿠폰사용조회 오류가 발생할 경우 티스토어 쇼핑 구매취소불가. CASE 7 : CMS 쿠폰사용조회 후 사용된 쿠폰이 존재할 경우.
		 */

		// TODO : 정액권 상품이면 자동결제 해지예약도 한다. 해지는? 없다. 이거 모 호출해야 할지 모름..
		// TR 시작
		// DB 업데이트
		purchaseCancelParamDetail = this.purchaseCancelRepository.cancelPurchase(purchaseCommonParam,
				purchaseCancelParamDetail);

		if (purchaseCancelParamDetail.getPrchsCancelCnt() != 1 || purchaseCancelParamDetail.getPrchsDtlCancelCnt() < 1) {
			throw new StorePlatformException("구매 취소 처리 실패!");
		}

		// TR 끝
		// RO 삭제 - 코드 DP0002의DP000201면 APP
		// // 구매 상품이 APP이고 appid가 null이 아닌경우

		// 상품 구매 건수 차감 호출.

		purchaseCancelResultDetail.setPrchsId(purchaseCancelParamDetail.getPrchsId());
		purchaseCancelResultDetail.setPrchsCancelResultCd("성공");
		purchaseCancelResultDetail.setPrchsCancelResultMsg("성공");

		// ro 삭제 진행 삭제 실패해도 구매취소는 성공!
		try {
			this.removeRO(purchaseCommonParam, purchaseCancelParamDetail);
		} catch (Exception e) {
			this.logger.debug("ro 삭제 실패! : {} ", e.getMessage());
		}

		/*
		 * 구매 상태가 정상인지
		 * 
		 * 이용내역? 이용이력? 이런게 있는지? 확인?
		 * 
		 * 선물 한 건지 받은건지 ?
		 * 
		 * 이미 이용중인지 확인? 이미 만료 된 건이 있는지 확인? use_expr_dt
		 * 
		 * 오퍼링 상품은 어케 처리?
		 * 
		 * 정액권 구매건인지
		 * 
		 * 정액권 구매건일 경우 상품 산게 있는지 체크
		 * 
		 * 정액권으로 산 상품이랑 일반 결제랑 함께 있을 수 있는지?
		 * 
		 * T Store Cash 취소 일 경우 취소할 캐쉬량이 남이 있는지 확인 필요(예약)
		 * 
		 * 쇼핑쿠폰 인지 확인 - 쇼셜쇼핑 TSTORE 쇼핑?
		 * 
		 * 쿠폰처리 확인?
		 * 
		 * 부분유료화 상품 취소 가능?
		 * 
		 * 
		 * 
		 * ro 삭제해야하는 건 있는지 -> RO삭제 실패해도 구매 취소 성공?
		 * 
		 * 이용내역 취소는 머지?
		 */

		this.logger.debug("구매 취소 tr 종료");
		return purchaseCancelResultDetail;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param purchaseCommonParam
	 *            purchaseCommonParam
	 * @param purchaseCancelParamDetail
	 *            purchaseCancelParamDetail
	 */
	private void removeRO(PurchaseCommonParam purchaseCommonParam, PurchaseCancelParamDetail purchaseCancelParamDetail) {

		// aom message 발송.

		// arm 라이센스 취소 호출.
		this.purchaseCancelRepository.removeLicense(purchaseCommonParam, purchaseCancelParamDetail);

	}
}
