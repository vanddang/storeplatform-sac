package com.skplanet.storeplatform.sac.purchase.order.service;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.dummypurchase.sci.DummyPurchaseSCI;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.precheck.CheckerManager;
import com.skplanet.storeplatform.sac.purchase.order.precheck.PurchasePreChecker;
import com.skplanet.storeplatform.sac.purchase.order.vo.PrePurchaseInfo;

/**
 * 
 * 구매 서비스 구현
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

	@Autowired
	private DummyPurchaseSCI dummyPurchaseSCI;

	private final CheckerManager checkerManager = new CheckerManager();

	/**
	 * 
	 * <pre>
	 * 구매 전처리.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	@Override
	public void checkPurchase(PrePurchaseInfo purchaseInfo) {
		List<PurchasePreChecker> checkerList = this.checkerManager.getCheckerList(null);

		Long startTime = System.currentTimeMillis();

		for (PurchasePreChecker checker : checkerList) {
			if (checker.isTarget(purchaseInfo) == false) {
				continue;
			}

			if (checker.checkAndSetInfo(purchaseInfo) == false) {
				break;
			}
		}

		logger.debug("PRCHS,DUMMY,CHECK," + (System.currentTimeMillis() - startTime) + "ms");
	}

	/**
	 * 
	 * <pre>
	 * 무료구매 처리.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	@Override
	public void freePurchase(PrePurchaseInfo purchaseInfo) {
		String formattedNow = DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "ddmmss");

		String prchsId = "TAK" + formattedNow + purchaseInfo.getCreatePurchaseReq().getPrchsReqPathCd().substring(8)
				+ (int) (Math.random() * 1000)
				+ purchaseInfo.getCreatePurchaseReq().getProductList().get(0).getProdId().substring(7)
				+ (int) (Math.random() * 1000);

		Prchs prchs = new Prchs();
		prchs.setTenantId(purchaseInfo.getCreatePurchaseReq().getTenantId());
		prchs.setPrchsId(prchsId);
		prchs.setInsdUsermbrNo(purchaseInfo.getCreatePurchaseReq().getInsdUsermbrNo());
		prchs.setInsdDeviceId(purchaseInfo.getCreatePurchaseReq().getInsdDeviceId());
		prchs.setPrchsStatusCd("OR000301");
		prchs.setTotAmt(purchaseInfo.getRealTotAmt());
		prchs.setPrchsReqPathCd(purchaseInfo.getCreatePurchaseReq().getPrchsReqPathCd());
		prchs.setCurrencyCd(purchaseInfo.getCreatePurchaseReq().getCurrencyCd());
		prchs.setClientIp(purchaseInfo.getCreatePurchaseReq().getClientIp());
		prchs.setNetworkTypeCd(purchaseInfo.getCreatePurchaseReq().getNetworkTypeCd());
		prchs.setRegId("testpurchase");
		prchs.setUpdId("testpurchase");

		prchs.setCancelReqPathCd(null);
		prchs.setCancelDt(null);
		prchs.setPrchsCaseCd(null);
		prchs.setResvCol01(null);
		prchs.setResvCol02(null);
		prchs.setResvCol03(null);
		prchs.setResvCol04(null);
		prchs.setResvCol05(null);

		PrchsDtl prchsDtl = new PrchsDtl();
		prchsDtl.setTenantId(prchs.getTenantId());
		prchsDtl.setPrchsId(prchs.getPrchsId());
		prchsDtl.setUseTenantId(prchs.getTenantId());
		prchsDtl.setUseInsdUsermbrNo(prchs.getInsdUsermbrNo());
		prchsDtl.setUseInsdDeviceId(prchs.getInsdDeviceId());
		prchsDtl.setTotAmt(prchs.getTotAmt());
		prchsDtl.setPrchsReqPathCd(prchs.getPrchsReqPathCd());
		prchsDtl.setClientIp(prchs.getClientIp());
		prchsDtl.setStatusCd(prchs.getPrchsStatusCd());
		prchsDtl.setHidingYn("N");
		prchsDtl.setRegId(prchs.getRegId());
		prchsDtl.setUpdId(prchs.getUpdId());

		int i = 0;
		int prchsDtlCnt = 1;
		for (PurchaseProduct product : purchaseInfo.getCreatePurchaseReq().getProductList()) {
			prchsDtl.setProdId(product.getProdId());
			prchsDtl.setProdAmt(product.getProdAmt());
			prchsDtl.setProdQty(product.getProdQty());
			prchsDtl.setProdGrpCd("GRP-1");
			prchsDtl.setRePrchsPmtYn("N");
			prchsDtl.setUseExprDt("99991231235959");
			prchsDtl.setDwldExprDt("99991231235959");

			for (i = 0; i < product.getProdQty(); i++) {
				prchsDtl.setPrchsDtlId(prchsDtlCnt++);

				this.dummyPurchaseSCI.createPrchsDtl(prchsDtl);
			}
		}

		this.dummyPurchaseSCI.createPrchs(prchs);

		// Payment payment = new Payment();
		// this.dummyPurchaseSCI.createPayment(payment);
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 결제Page 준비작업.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 */
	@Override
	public void setPaymentPageInfo(PrePurchaseInfo purchaseInfo) {

	}
}
