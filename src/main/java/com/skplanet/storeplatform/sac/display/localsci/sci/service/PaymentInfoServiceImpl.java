package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.*;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.GetProductBaseInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.ProductExtraInfoService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.MappedProduct;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.PaymentInfoContext;
import com.skplanet.storeplatform.sac.display.promotion.PromotionEventDataService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.EXINFO_S2S_INFO;
import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS;

/**
 * 결제 시 필요한 상품 메타 정보 조회 서비스 구현체.
 *
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 * Updated on : 2015. 7. 13. Updated by : 정희원, SKP
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private DisplayCommonService displayCommonService;

    @Autowired
    private ProductExtraInfoService productExtraInfoService;

    @Autowired
    private PromotionEventDataService promotionEventDataService;

    @Autowired
    private CachedExtraInfoManager extraInfoManager;

    /**
     * <pre>
     * 결제시 상품 메타 정보 조회.
     * </pre>
     *
     * @param req 파라미터
     * @return 상품 메타 정보 리스트
     */
    @Override
    public PaymentInfoSacRes searchPaymentInfo(PaymentInfoSacReq req) {

        List<String> prodIdList = req.getProdIdList();
        String tenantId = req.getTenantId(),
                userKey = req.getUserKey();

        this.log.debug("##### prodIdList size : {}", prodIdList.size());

        // 파라미터 존재 여부 체크
        parameterCheck(req);

        SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelCd());
        PaymentInfoContext ctx = new PaymentInfoContext(req.getTenantId(), req.getLangCd(), req.getDeviceModelCd(), supportDevice);

        List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>(prodIdList.size());

        for (String prodId : prodIdList) {

            // 상품 기본정보 조회
            ProductBaseInfo baseInfo = extraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(prodId));

            // TODO 에피소드 유형의 요청만 가능. 확정후 적용
            if(baseInfo.getContentsTypeCd().equals(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD))
                log.warn("{} is not episode type.", prodId);
//                throw new StorePlatformException("SAC_DSP_0033", prodId);

            ProductType prodTp = baseInfo.getProductType();
            PaymentInfo paymentInfo;

            switch (prodTp) {
                case Shopping:
                    paymentInfo = getShoppingPaymentMeta(ctx, prodId);
                    break;
                case Voucher:
                    paymentInfo = getVoucherPaymentMeta(ctx, prodId);
                    break;
                default:
                    paymentInfo = getAppAndMultimediaPaymentMeta(ctx, prodId, baseInfo);
            }

            if(paymentInfo == null)
                continue;

            // 멤버십 프로모션 정보 매핑
            mapgPromotion(tenantId, userKey, baseInfo, paymentInfo);

            // 허용 연령 정보
            paymentInfo.setAgeAllowedFrom(displayCommonService.getAllowedAge(paymentInfo.getTopMenuId(), paymentInfo.getProdGrdCd()));

            // FIXME 이용 기간 설정 구분
            paymentInfo.setUsePeriodSetCd(displayCommonService.getUsePeriodSetCd(paymentInfo.getTopMenuId(), paymentInfo.getProdId(), paymentInfo.getDrmYn(),paymentInfo.getSvcGrpCd()));

            paymentInfoList.add(paymentInfo);
        }

        return new PaymentInfoSacRes(paymentInfoList);
    }

    /**
     * 쇼핑 상품의 결제 처리용 메타데이터 조회
     * @param ctx
     * @param prodId
     * @return
     */
    private PaymentInfo getShoppingPaymentMeta(PaymentInfoContext ctx, String prodId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
        paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
        paramMap.put("deviceModelCd", ctx.getDeviceModelCd());   // TODO why?
        paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);
        paramMap.put("sclShpgSprtYn", ctx.getSclShpgSprtYn());
        paramMap.put("langCd", ctx.getLangCd());
        paramMap.put("tenantId", ctx.getTenantId());
        paramMap.put("prodId", prodId);

        return this.commonDAO.queryForObject("PaymentInfo.getShoppingMetaInfo", paramMap, PaymentInfo.class);
    }
    /**
     * 정액권의 결제 처리용 메타데이터 조회
     * @param ctx
     * @param prodId
     * @return
     */
    private PaymentInfo getVoucherPaymentMeta(PaymentInfoContext ctx, String prodId) {

        List<String> exclusiveFixrateProdIdList = new ArrayList<String>();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("langCd", ctx.getLangCd());
        paramMap.put("tenantId", ctx.getTenantId());
        paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
        paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
        paramMap.put("deviceModelCd", ctx.getDeviceModelCd());
        paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);
        paramMap.put("vodFixisttSprtYn", ctx.getVodFixisttSprtYn());
        paramMap.put("ebookSprtYn", ctx.getEbookSprtYn());
        paramMap.put("comicSprtYn", ctx.getComicSprtYn());
        paramMap.put("prodId", prodId);

        PaymentInfo paymentInfo = this.commonDAO.queryForObject("PaymentInfo.getVoucherMetaInfo", paramMap, PaymentInfo.class);

        if(paymentInfo == null)
            throw new StorePlatformException("SAC_DSP_0009", "[정액권 상품 조회]" + prodId);

        List<ExclusiveFreePass> exclusiveTypeInfoList = this.commonDAO.queryForList("PaymentInfo.getExclusiveTypeInfoList", prodId,
                ExclusiveFreePass.class);

        for (ExclusiveFreePass info : exclusiveTypeInfoList) {

            if ("PD013403".equals(info.getDupPrchsLimtTypeCd())) {
                paramMap.put("dupPrchsLimtProdId", info.getDupPrchsLimtProdId());
                exclusiveFixrateProdIdList.addAll(this.commonDAO.queryForList(
                        "PaymentInfo.getExclusiveGroupProdIdList", paramMap, String.class));
            } else {
                exclusiveFixrateProdIdList.add(info.getDupPrchsLimtProdId());
            }
        }

        paymentInfo.setExclusiveFixrateProdIdList(exclusiveFixrateProdIdList);

        return paymentInfo;
    }

    /**
     * 앱, 멀티미디어 상품의 결제 처리용 메타데이터 조회
     * @param ctx
     * @param prodId
     * @param baseInfo
     * @return
     */
    private PaymentInfo getAppAndMultimediaPaymentMeta(PaymentInfoContext ctx, String prodId, ProductBaseInfo baseInfo) {

        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("deviceModelCd", StringUtils.defaultString(ctx.getDeviceModelCd(), "NULL"));
        reqMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);
        reqMap.put("ebookSprtYn", ctx.getEbookSprtYn());
        reqMap.put("comicSprtYn", ctx.getComicSprtYn());
        reqMap.put("musicSprtYn", ctx.getMusicSprtYn());
        reqMap.put("videoDrmSprtYn", ctx.getVideoDrmSprtYn());
        reqMap.put("sdVideoSprtYn", ctx.getSdVideoSprtYn());
        reqMap.put("tenantId", ctx.getTenantId());
        reqMap.put("langCd", ctx.getLangCd());
        reqMap.put("prodId", prodId);

        PaymentInfo paymentInfo = this.commonDAO.queryForObject("PaymentInfo.searchPaymentInfo", reqMap, PaymentInfo.class);

        if (paymentInfo == null) {
            throw new StorePlatformException("SAC_DSP_0005", "[일반상품 조회]" + prodId);
        }

        paymentInfo.setTopMenuId(baseInfo.getTopMenuId());
        paymentInfo.setSvcGrpCd(baseInfo.getSvcGrpCd());
        paymentInfo.setInAppYn(baseInfo.isIapProduct() ? "Y" : "N"); // In-App 여부
        paymentInfo.setSeriesYn(baseInfo.isSeries() ? "Y" : "N"); // 시리즈 여부 세팅

        // 상품 유형별 추가 작업
        ProductType prodTp = baseInfo.getProductType();
        switch (prodTp) {
            case VodTv:
                if (StringUtils.isNotEmpty(paymentInfo.getChapter())) {
                    paymentInfo.setChapterText(paymentInfo.getChapter());
                    paymentInfo.setChapterUnit(this.displayCommonService.getVodChapterUnit());
                    paymentInfo.setProdNm(paymentInfo.getChnlProdNm());
                }
                break;
            case Ebook:
            case Comic:
                if (StringUtils.isNotEmpty(paymentInfo.getChapter())
                        && StringUtils.isNotEmpty(paymentInfo.getBookClsfCd())) {
                    paymentInfo.setChapterText(paymentInfo.getChapter());
                    paymentInfo.setChapterUnit(this.displayCommonService.getEpubChapterUnit(paymentInfo.getBookClsfCd()));
                    paymentInfo.setProdNm(paymentInfo.getChnlProdNm());
                }
                break;
            case InApp:
                bindS2SInfo(prodId, paymentInfo);
                paymentInfo.setUsePeriod("0");
                paymentInfo.setUsePeriodUnitCd(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE);
                break;
            case App:
                // 앱상품의 경우 사용정책 값을 무제한으로 지정
                paymentInfo.setUsePeriod("0");
                paymentInfo.setUsePeriodUnitCd(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE);
                break;
        }

        if (baseInfo.isSeries()) {
            StringBuilder sb = new StringBuilder(paymentInfo.getChnlProdNm());
            if (!Strings.isNullOrEmpty(paymentInfo.getChapterText())) {
                sb.append(" ")
                        .append(paymentInfo.getChapterText())
                        .append(paymentInfo.getChapterUnit());
            }
            paymentInfo.setProdNm(sb.toString());
        }

        // 이용가능한 정액권목록 제공
        List<FreePass> availableFixrateInfoList = getAvailableFixrateInfoList(ctx, prodId ,paymentInfo.getPossLendClsfCd(),paymentInfo.getBookClsfCd());
        Collection<String> availableFixrateProdList = Collections2.transform(availableFixrateInfoList, new Function<FreePass, String>() {
            @Override
            public String apply(FreePass input) {
                return input.getProdId();
            }
        });
        paymentInfo.setAvailableFixrateProdIdList(new ArrayList<String>(availableFixrateProdList));
        paymentInfo.setAvailableFixrateInfoList(availableFixrateInfoList);

        return paymentInfo;
    }

    private List<FreePass> getAvailableFixrateInfoList(PaymentInfoContext ctx, String prodId ,String possLendClsfCd, String bookClsfCd ) {

        List<FreePass> availableFixrateInfoList = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("langCd", ctx.getLangCd());
        paramMap.put("tenantId", ctx.getTenantId());
        paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
        paramMap.put("prodId", prodId);
        paramMap.put("possLendClsfCd", possLendClsfCd);
        paramMap.put("bookClsfCd", bookClsfCd);
        availableFixrateInfoList = this.commonDAO.queryForList("PaymentInfo.getAvailableFixrateInfoList", paramMap,
                FreePass.class);

        return availableFixrateInfoList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 파라메터 유효성 검증
     * @param req
     */
    private void parameterCheck(PaymentInfoSacReq req) {
        List<String> prodIdList = req.getProdIdList();
        String tenantId = req.getTenantId();
        String langCd = req.getLangCd();

        if (CollectionUtils.isEmpty(prodIdList)) {
            throw new StorePlatformException("SAC_DSP_0002", "prodIdList", prodIdList.toString());
        }
        if (prodIdList.size() > DisplayConstants.DP_PAYMENT_INFO_PARAMETER_LIMIT) {
            throw new StorePlatformException("SAC_DSP_0004", "prodIdList",
                    DisplayConstants.DP_PAYMENT_INFO_PARAMETER_LIMIT);
        }
        if (StringUtils.isEmpty(tenantId)) {
            throw new StorePlatformException("SAC_DSP_0002", "tenantId", tenantId);
        }
        if (StringUtils.isEmpty(langCd)) {
            throw new StorePlatformException("SAC_DSP_0002", "langCd", langCd);
        }
    }

    private void mapgPromotion(String tenantId, String userKey, ProductBaseInfo baseInfo, PaymentInfo paymentInfo) {

        // IAP상품은 부모 상품으로, 그 외의 상품은 chnlId로 조회
        String chnlId = "", menuId = "";

        // 이용권 상품이면서 "시리즈 전회차 상품"인 경우 매핑된 채널ID의 상품 정보를 조회하여 프로모션 정보를 조회한다.
        if(FIXRATE_PROD_TYPE_VOD_SERIESPASS.equals(paymentInfo.getCmpxProdClsfCd())) {
            MappedProduct mappedProduct = commonDAO.queryForObject("PaymentInfo.getMappedProdByVoucher", paymentInfo.getProdId(), MappedProduct.class);
            if(mappedProduct != null) {
                chnlId = mappedProduct.getProdId();
                menuId = mappedProduct.getMenuId();
            }
        }
        else {
            chnlId = baseInfo.isIapProduct() ? paymentInfo.getParentProdId() : baseInfo.getChnlId();
            menuId = StringUtils.defaultString(paymentInfo.getMenuId(), paymentInfo.getTopMenuId());
        }

        RawPromotionEvent event = promotionEventDataService.getLivePromotionEventForUser(tenantId, chnlId, menuId, userKey);

        Map<String, Integer> milMap = Maps.newHashMap();
        if (event != null) {
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV1, event.getRateGrd1());
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV2, event.getRateGrd2());
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV3, event.getRateGrd3());

            paymentInfo.setPromId(event.getPromId());
            paymentInfo.setAcmlDt(event.getAcmlDt());
            paymentInfo.setAcmlMethodCd(event.getAcmlMethodCd());
            paymentInfo.setPrivateAcmlLimit(event.getAcmlLimt());
            paymentInfo.setPromForceCloseCd(event.getPromForceCloseCd());
            if(Strings.isNullOrEmpty(event.getPromForceCloseCd()))
                paymentInfo.setPromEndDt(DateUtils.format(event.getEndDt()));
            else
                paymentInfo.setPromEndDt(DateUtils.format(event.getPromForceCloseDt()));
        }
        else {
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV1, 0);
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV2, 0);
            milMap.put(DisplayConstants.POINT_TP_MILEAGE_LV3, 0);
        }

        paymentInfo.setMileageRateMap(milMap);
    }

    private void bindS2SInfo(String prodId, PaymentInfo paymentInfo) {
        Map infoMap = productExtraInfoService.getInfoAsJSON(prodId, EXINFO_S2S_INFO);
        if(infoMap == null)
            return;

        if (infoMap.containsKey("_info"))
            paymentInfo.setSearchPriceUrl("" + infoMap.get("_info"));
        else {
            if(infoMap.containsKey("searchPriceUrl"))
                paymentInfo.setSearchPriceUrl("" + infoMap.get("searchPriceUrl"));
        }
    }

}
