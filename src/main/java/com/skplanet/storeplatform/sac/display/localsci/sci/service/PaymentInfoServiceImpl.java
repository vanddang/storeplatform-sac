package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.freepass.service.FreepassService;
import com.skplanet.storeplatform.sac.display.shopping.service.ShoppingService;

/**
 * 
 * 
 * 결제 시 필요한 상품 메타 정보 조회 서비스 구현체.
 * 
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ShoppingService shoppingService;

	@Autowired
	private FreepassService freepassService;

	@Autowired
	private DisplayCommonService displayCommonService;

	/**
	 * <pre>
	 * 결제 시 필요한 상품 메타 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PaymentInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public PaymentInfoSacRes searchPaymentInfo(PaymentInfoSacReq req) {
		PaymentInfoSacRes res = new PaymentInfoSacRes();
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
		List<String> prodIdList = req.getProdIdList();
		String tenantId = req.getTenantId();
		String langCd = req.getLangCd();
		String deviceModelCd = req.getDeviceModelCd();

		this.log.debug("##### prodIdList size : {}", prodIdList.size());

		// 파라미터 존재 여부 체크
		if (prodIdList.size() == 0) {
			this.log.info(
					"searchPaymentInfo[prodIdList is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}",
					prodIdList, tenantId, langCd, deviceModelCd);
			throw new StorePlatformException("SAC_DSP_0002", "prodIdList", prodIdList.toString());
		}
		if (StringUtils.isEmpty(tenantId)) {
			this.log.info(
					"searchPaymentInfo[tenantId is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}",
					prodIdList, tenantId, langCd, deviceModelCd);
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", tenantId);
		}
		if (StringUtils.isEmpty(langCd)) {
			this.log.info(
					"searchPaymentInfo[langCd is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}",
					prodIdList, tenantId, langCd, deviceModelCd);
			throw new StorePlatformException("SAC_DSP_0002", "langCd", langCd);
		}
		if (StringUtils.isEmpty(deviceModelCd)) {
			this.log.info(
					"searchPaymentInfo[deviceModelCd is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}",
					prodIdList, tenantId, langCd, deviceModelCd);
			req.setDeviceModelCd("NULL");
		}

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelCd());
		if (supportDevice == null) {
			req.setEbookSprtYn("Y");
			req.setComicSprtYn("Y");
			req.setMusicSprtYn("Y");
			req.setVideoDrmSprtYn("Y");
			req.setSdVideoSprtYn("Y");
		} else {
			req.setEbookSprtYn(supportDevice.getEbookSprtYn());
			req.setComicSprtYn(supportDevice.getComicSprtYn());
			req.setMusicSprtYn(supportDevice.getMusicSprtYn());
			req.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn());
			req.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn());
		}
		req.setDpAnyPhone4mm(DisplayConstants.DP_ANY_PHONE_4MM);

		// 파라미터 유효 값 체크
		if (prodIdList.size() > DisplayConstants.DP_PAYMENT_INFO_PARAMETER_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "prodIdList",
					DisplayConstants.DP_PAYMENT_INFO_PARAMETER_LIMIT);
		}

		// 상품 군 조회
		PaymentInfo paymentProdType = this.commonDAO.queryForObject("PaymentInfo.searchProdType", prodIdList.get(0),
				PaymentInfo.class);

		if (paymentProdType == null) {
			this.log.info(
					"searchPaymentInfo[paymentProdType is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}, prodId : {}",
					prodIdList, tenantId, langCd, deviceModelCd, prodIdList.get(0));
			throw new StorePlatformException("SAC_DSP_0005", "[상품 군 조회]" + prodIdList.get(0));
		} else {
			this.log.debug("##### searchProdType result : {}, {}, {}", paymentProdType.getTopMenuId(),
					paymentProdType.getSvcGrpCd(), paymentProdType.getInAppYn());

			if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(paymentProdType.getSvcGrpCd())) { // 쇼핑 상품
				paymentInfoList = this.shoppingService.getShoppingforPayment(req);
			} else if (DisplayConstants.DP_TSTORE_FREEPASS_PROD_SVC_GRP_CD.equals(paymentProdType.getSvcGrpCd())) { // 정액권_상품
				paymentInfoList = this.freepassService.getFreePassforPayment(req);
			} else {
				for (int i = 0; i < prodIdList.size(); i++) {
					req.setProdId(prodIdList.get(i)); // prodIdList 에 있는 상품ID 1개씩 setting
					PaymentInfo paymentInfo = this.commonDAO.queryForObject("PaymentInfo.searchPaymentInfo", req,
							PaymentInfo.class);

					if (paymentInfo == null) {
						this.log.info(
								"searchPaymentInfo[paymentInfo is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}, prodId : {}",
								prodIdList, tenantId, langCd, deviceModelCd, prodIdList.get(i));
						throw new StorePlatformException("SAC_DSP_0005", "[일반상품 조회]" + prodIdList.get(i));
					} else {
						paymentInfo.setTopMenuId(paymentProdType.getTopMenuId());
						paymentInfo.setSvcGrpCd(paymentProdType.getSvcGrpCd());
						paymentInfo.setInAppYn(paymentProdType.getInAppYn()); // In-App 여부

						// 이용가능한 정액권목록 제공
						paymentInfo.setAvailableFixrateProdIdList(this.freepassService
								.getAvailableFixrateProdIdList(req));
						paymentInfoList.add(paymentInfo);
					}
				}
			}
		}

		res.setPaymentInfoList(paymentInfoList);
		return res;
	}
}
