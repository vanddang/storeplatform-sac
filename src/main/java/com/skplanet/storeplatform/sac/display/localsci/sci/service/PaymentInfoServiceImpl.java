package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.VodType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.ProductInfo;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.freepass.service.FreepassService;
import com.skplanet.storeplatform.sac.display.shopping.service.ShoppingService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.SET_SERIES_META;

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

    @Autowired
    private MemberBenefitService benefitService;

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
        List<PaymentInfo> paymentInfoList;
        List<String> prodIdList = req.getProdIdList();
        String tenantId = req.getTenantId();
        String langCd = req.getLangCd();
        String deviceModelCd = req.getDeviceModelCd();

        this.log.debug("##### prodIdList size : {}", prodIdList.size());

        // 파라미터 존재 여부 체크
        if (CollectionUtils.isEmpty(prodIdList)) {
            this.log.debug(
                    "searchPaymentInfo[prodIdList is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}",
                    prodIdList, tenantId, langCd, deviceModelCd);
            throw new StorePlatformException("SAC_DSP_0002", "prodIdList", prodIdList.toString());
        }
        if (prodIdList.size() > DisplayConstants.DP_PAYMENT_INFO_PARAMETER_LIMIT) {
            throw new StorePlatformException("SAC_DSP_0004", "prodIdList",
                    DisplayConstants.DP_PAYMENT_INFO_PARAMETER_LIMIT);
        }
        if (StringUtils.isEmpty(tenantId)) {
            this.log.debug(
                    "searchPaymentInfo[tenantId is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}",
                    prodIdList, tenantId, langCd, deviceModelCd);
            throw new StorePlatformException("SAC_DSP_0002", "tenantId", tenantId);
        }
        if (StringUtils.isEmpty(langCd)) {
            this.log.debug(
                    "searchPaymentInfo[langCd is null] = prodIdList : {}, tenantId : {}, langCd : {}, deviceModelCd : {}",
                    prodIdList, tenantId, langCd, deviceModelCd);
            throw new StorePlatformException("SAC_DSP_0002", "langCd", langCd);
        }
        if (StringUtils.isEmpty(deviceModelCd)) {
            this.log.debug(
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


        // 상품 군 조회 (VOD는 VOD끼리만, 이북/코믹은 이북/코믹끼리만 요청이 오므로, 1번만 조회)
        ProductInfo prodInfo = displayCommonService.getProductInfo(prodIdList.get(0));

        if (prodInfo.getProductType() == ProductType.Shopping) { // 쇼핑 상품
            paymentInfoList = this.shoppingService.getShoppingforPayment(req);
        } else if (prodInfo.getProductType() == ProductType.Freepass) { // 정액권_상품
            paymentInfoList = this.freepassService.getFreePassforPayment(req);
        } else {
            paymentInfoList = getAppNMmProductInfo(req, prodIdList, prodInfo);
        }

        for (PaymentInfo paymentInfo : paymentInfoList) {
            Map<String, Integer> milMap = new HashMap<String, Integer>();
            MileageInfo mileageInfo;
            
            Integer prodAmt = paymentInfo.getProdAmt() != null ? paymentInfo.getProdAmt().intValue() : null;
            
            if("Y".equals(paymentInfo.getInAppYn())) {
                mileageInfo = benefitService.getMileageInfo(tenantId, paymentInfo.getTopMenuId(), paymentInfo.getParentProdId(), prodAmt);
            }
            else
                mileageInfo = benefitService.getMileageInfo(tenantId, paymentInfo.getTopMenuId(), paymentInfo.getProdId(), prodAmt);

            // Tstore 멤버십 - 방어코드 추가
            if(mileageInfo == null) mileageInfo = new MileageInfo();	 
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV1, mileageInfo.getRateLv1());
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV2, mileageInfo.getRateLv2());
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV3, mileageInfo.getRateLv3());
            paymentInfo.setMileageRateMap(milMap);
        }

        res.setPaymentInfoList(paymentInfoList);
        return res;
    }

    private List<PaymentInfo> getAppNMmProductInfo(PaymentInfoSacReq req, List<String> prodIds, ProductInfo prodInfo) {
        List<PaymentInfo> res = new ArrayList<PaymentInfo>();

        for (String prodId : prodIds) {
            req.setProdId(prodId);

            PaymentInfo paymentInfo = this.commonDAO.queryForObject("PaymentInfo.searchPaymentInfo", req,
                    PaymentInfo.class);

            if (paymentInfo == null) {
                throw new StorePlatformException("SAC_DSP_0005", "[일반상품 조회]" + prodId);
            }

            paymentInfo.setTopMenuId(prodInfo.getTopMenuId());
            paymentInfo.setSvcGrpCd(prodInfo.getSvcGrpCd());
            paymentInfo.setInAppYn(prodInfo.isIap() ? "Y" : "N"); // In-App 여부

            // Chapter 및 채널명 셋팅
            if (prodInfo.getProductType() == ProductType.Vod && prodInfo.getVodType() == VodType.Tv) { // TV
                if (StringUtils.isNotEmpty(paymentInfo.getChapter())) {
                    paymentInfo.setChapterText(paymentInfo.getChapter());
                    paymentInfo.setChapterUnit(this.displayCommonService.getVodChapterUnit());
                    paymentInfo.setProdNm(paymentInfo.getChnlProdNm());
                }
            } else if (prodInfo.getProductType() == ProductType.EbookComic) { // 이북,코믹
                if (StringUtils.isNotEmpty(paymentInfo.getChapter())
                        && StringUtils.isNotEmpty(paymentInfo.getBookClsfCd())) {
                    paymentInfo.setChapterText(paymentInfo.getChapter());
                    paymentInfo.setChapterUnit(this.displayCommonService.getEpubChapterUnit(paymentInfo.getBookClsfCd()));
                    paymentInfo.setProdNm(paymentInfo.getChnlProdNm());
                }
            }

            // 시리즈 여부 세팅
            paymentInfo.setSeriesYn(SET_SERIES_META.contains(paymentInfo.getMetaClsfCd()) ? "Y" : "N");

            if (paymentInfo.getSeriesYn().equals("Y")) {
                String prodNm = paymentInfo.getChnlProdNm() + " " + paymentInfo.getChapterText() + paymentInfo.getChapterUnit();
                paymentInfo.setProdNm(prodNm);
            }

            // 이용가능한 정액권목록 제공
            paymentInfo.setAvailableFixrateProdIdList(this.freepassService.getAvailableFixrateProdIdList(req));
            res.add(paymentInfo);
        }

        return res;
    }
}
