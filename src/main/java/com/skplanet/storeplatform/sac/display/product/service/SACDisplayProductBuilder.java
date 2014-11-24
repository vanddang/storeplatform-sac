package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.*;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.product.constant.IFConstants;
import com.skplanet.storeplatform.sac.display.product.exception.IcmsProcessException;
import com.skplanet.storeplatform.sac.display.product.vo.ProductVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * SACDisplayProductBuilder 
 * 
 * CMS 전시 배포 정보 등록 서비스 구현체.
 * 
 * Updated on : 2014. 2. 13. Updated by : 차명호, ANB
 */
@Service
public class SACDisplayProductBuilder implements DisplayProductBuilder {

	private static final Logger log = LoggerFactory.getLogger(SACDisplayProductBuilder.class);
	
	@Autowired
	private DPProductService dpProductService;
	
	@Autowired
	private DPProductCatMapService dpProductCatMapService;
	
	@Autowired
	private DPProductRshpService dpProductRshpService;

	@Autowired
	private DPProductDescService dpProductDescService;

	@Autowired
	private DPAppProductService dpAppProductService;
	
	@Autowired
	private DPTenantProductPriceService dpTenantProductPriceService;
	
	@Autowired
	private DPTenantProductService dpTenantProductService;
	
	@Autowired
	private DPProductImgService dpProductImgService;
	
	@Autowired
	private DPProductUpdService dpProductUpdService;

	@Autowired
	private DPSprtDeviceService dpSprtDeviceService;
	
	@Autowired
	private DPProductSubContsService dpProductSubContsService;
	
	@Autowired
	private DPAppDeltaDeployFileService dPAppDeltaDeployFileService;

	@Autowired
	private DPTagInfoService dpTagInfoService;

	@Autowired
	private DPSeedMappService dpSeedMappService;
	
	@Autowired
	private ProductService prodService;

    @Autowired
    private DpSapProdMapgService dpSapProdMapgService;

    @Autowired
    private DisplayCommonService displayCommonService;

	@Override
	public void insertProdInfo(NotificationRefactoringSac notification, List<Map<String, Object>> tempList, Set<String> prodExistTenant, String oldSellerMbrNo) {

		DPProductVO dpProd = notification.getDpProductTotal().getDpProduct();
		String prodId = dpProd.getProdId(); // 상품_아이디
		String mbrNo = dpProd.getSellerMbrNo(); // 회원_번호
        String newSellerMbrNo = notification.getDpProductTotal().getDpProduct().getSellerMbrNo();   // prod.sellerMbrNo

        execSapPhase1(notification);

		/*
		 * 전시상품 정보
		 */
		dpProductService.insertDPProduct(notification.getDpProductTotal().getDpProduct());
		
		/*
		 * 상품정보 매핑
		 */
		dpProductRshpService.insertDPProductRshp(notification.getDpProductTotal().getDpProductRshp());
		
		/*
		 * 테넌트 정보
		 */
		List<DPTenantProductVO> tenantInfo = notification.getDpProductTotal().getDpTenantProduct();
        printCountLog("CMS TenantInfo Size = {}", tenantInfo);
        for (DPTenantProductVO vo : tenantInfo) {

            // 테넌트 정보 등록
            log.info("Insert CMS Tenant Info");
            dpTenantProductService.insertDPTenant(vo);
        }
		
		
		/*
		 * 테넌트 상품 가격
		 */
		List<DPTenantProductPriceVO> tenantPriceInfo = notification.getDpProductTotal().getDpTenantProductPrice();
        printCountLog("CMS TenantPriceInfo Size = {}", tenantPriceInfo);
        for (DPTenantProductPriceVO vo : tenantPriceInfo) {

            // 테넌트 상품 가격 등록
            log.info("Insert CMS TenantPrice Info");
            dpTenantProductPriceService.insertDPTenantPrice(vo);
        }
		
		/*
		 * 전시상품 상품상세
		 */
		List<DPProductDescVO> dpProdDesc = notification.getDpProductTotal().getDpProductDesc();
        printCountLog("CMS DpProdDesc Size = {}", dpProdDesc);
        for (DPProductDescVO vo : dpProdDesc) {

            // 전시상품 상품상세 등록
            log.info("Insert CMS DpProdDesc Info");
            dpProductDescService.insertDPProductDesc(vo);
        }

		/*
		 * 전시상품 APP 상품
		 */
		DPAppProductVO dpProdAppInfo = notification.getDpProductTotal().getDpAppProduct(); 
		if (null != dpProdAppInfo.getProdId()) {
			// 전시상품 APP 상품 등록
			log.info("Insert CMS DpProdApp Info");
			dpAppProductService.insertDPAppProduct(notification.getDpProductTotal().getDpAppProduct());
		}

        /*
         * InApp 상품 처리
         */
        insertInAppInfo(notification.getDpProductTotalList());


		/*
		 * 전시상품/부분유료화상품 카테고리정보 
		 */
		List<DPProductCatMapVO> displayCategoryList = notification.getDpProductCatMap();
        printCountLog("CMS displayCategoryList Size = {}", displayCategoryList);
		if(CollectionUtils.isNotEmpty(displayCategoryList)){
            for (DPProductCatMapVO vo : displayCategoryList) {

                // 전시상품/부분유료화상품 카테고리정보  등록
                log.info("Insert CMS DisplayCategory Info");
                dpProductCatMapService.insertDPProductCat(vo);
            }
		}

		
		/*
		 * 전시상품 이미지
		 */
		List<DPProductImgVO> displayProductImageList = notification.getDpProductImgList();
        printCountLog("CMS displayProductImageList Size = {}", displayProductImageList);
        for (DPProductImgVO vo : displayProductImageList) {

            // 전시상품 이미지 등록
            log.info("Insert CMS DisplayProductImage Info");
            dpProductImgService.insertDPProductImg(vo);
        }
		
		/*
		 * 전시상품 업데이트이력
		 */
		List<DPProductUpdVO> displayProductUpdateList = notification.getDpProductUpdList();
        printCountLog("CMS displayProductUpdateList Size = {}", displayProductUpdateList);
		if(CollectionUtils.isNotEmpty(displayProductUpdateList)) {
            for (DPProductUpdVO vo : displayProductUpdateList) {

                // 전시상품 업데이트이력 등록
                log.info("Insert CMS DisplayProductUpdate Info");
                dpProductUpdService.insertDPProductUpd(vo);
            }
		}
		
		/*
		 * 전시상품 단말
		 */
		List<DPSprtDeviceVO> displaySupportPhoneList = notification.getDpSprtDeviceList();
        printCountLog("CMS displaySupportPhoneList Size = {}", displaySupportPhoneList);
		if(CollectionUtils.isNotEmpty(displaySupportPhoneList)) {
            for (DPSprtDeviceVO vo : displaySupportPhoneList) {
                // 전시상품 단말 등록
                log.info("Insert CMS DisplaySupportPhone Info - DeviceModelCd#{}", vo.getDeviceModelCd());
                dpSprtDeviceService.insertDPSprtDevice(vo);
            }

            // 앱 기본 단말(ANY-PHONE-4APP) 추가
            String anySubContentsId = null;
            if(displaySupportPhoneList.size() > 0) {
                anySubContentsId = displaySupportPhoneList.get(0).getSubContentsId();
            }
            else {
                log.warn("ProdId#{}에 대해 anySubContentsId을 지정할 수 없습니다.", prodId);
            }

            if(anySubContentsId != null) {
                log.info("Insert CMS DisplaySupportPhone Info - DeviceModelCd#{}", DisplayConstants.DP_ANY_PHONE_4APP);
                DPSprtDeviceVO anyPhone = new DPSprtDeviceVO();
                anyPhone.setDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4APP);
                anyPhone.setSubContentsId(anySubContentsId);
                anyPhone.setRegId("admin");
                anyPhone.setUpdId("admin");
                anyPhone.setProdId(prodId);
                dpSprtDeviceService.insertDPSprtDevice(anyPhone);
            }
        }
		
		/*
		 * 전시상품 배포파일정보
		 */
		List<DPProductSubContsVO> displaySubContentList = notification.getDpProductSubContsList();
        printCountLog("CMS displaySubContentList Size = {}", displaySubContentList);
        if(CollectionUtils.isNotEmpty(displaySubContentList)) {
            for (DPProductSubContsVO vo : displaySubContentList) {

                // 전시상품 배포파일정보 등록
                log.info("Insert CMS DisplaySubContent Info - SubContentsId#{}", vo.getSubContentsId());
                dpProductSubContsService.insertDPProductSubconts(vo, DisplayCryptUtils.hashPkgNm(vo.getApkPkgNm()));
            }
        }

		
		/*
		 * 전시상품 Tag정보
		 */
		List<DPTagInfoVO> displayTabInfoList = notification.getDpTagInfoList();
        printCountLog("CMS displayTabInfoList Size = {}", displayTabInfoList);
        for (DPTagInfoVO vo : displayTabInfoList) {

            // 전시상품 Tag정보 등록
            log.info("Insert CMS DisplayTabInfo Info");
            dpTagInfoService.insertDPTagInfo(vo);
        }

		/*
		 * 전시상품 SeedMapp정보
		 */
		List<DPSeedMappVO> displaySeedMappList = notification.getDpSeedMappList();
        printCountLog("CMS displaySeedMappList Size = {}", displaySeedMappList);
        if(CollectionUtils.isNotEmpty(displaySeedMappList)) {
            for (DPSeedMappVO vo : displaySeedMappList) {

                // 전시상품 SeedMapp정보
                log.info("Insert CMS DisplaySeedMapp Info - {}/{}", vo.getProdId(), vo.getCaseRefCd());
                dpSeedMappService.insertDPSeedMapp(vo);
            }
        }

		/*
		 * 델타 File 정보
		 */
		List<DPAppDeltaDeployFileVO> dpAppDeltaDeployFileList = notification.getDpAppDeltaDeployFileList();
        printCountLog("CMS dPAppDeltaDeployFileList Size = {}", dpAppDeltaDeployFileList);
		if(CollectionUtils.isNotEmpty(dpAppDeltaDeployFileList)) {
            for (DPAppDeltaDeployFileVO vo : dpAppDeltaDeployFileList) {

                // 델타 File 정보
                log.info("Insert CMS DpAppDeltaDeployFile Info");
                dPAppDeltaDeployFileService.insertDPAppDeltaDeployFile(vo);
            }
		}

        /**
         * SAP 상품 매핑 정보 - Phase 1에서만 이용함
         */
        log.info("Insert CMS DpSapProdMapg Info");
        DPSapMappingVO dpSapMapping = notification.getDpSapMapping();
        if(dpSapMapping != null)
            dpSapProdMapgService.insertDPSapProdMapg(dpSapMapping);


        if (CollectionUtils.isNotEmpty(tenantInfo)) {
			log.info("CMS tenantInfo Size = " + tenantInfo.size());

            for (DPTenantProductVO vo : tenantInfo) {

                String tenantId = vo.getTenantId();
                if(!"S01".equals(tenantId))
                    continue;

                log.info("CMS PROD INFO = " + prodId + " | " + mbrNo);

                if(!prodExistTenant.contains(tenantId) || !StringUtils.equals(oldSellerMbrNo, newSellerMbrNo)) {
                    // 정산율 등록
                    ProductVo pv = this.prodService.selectMemberInfo(mbrNo);
                    if (pv == null)
                        throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR, "MBR_NO [ " + mbrNo + " ] 로 등록되어진 회원 정보가 없습니다.");

                    pv.setProdId(prodId);
                    pv.setRegDt(vo.getRegDt());
                    pv.setTenantId(vo.getTenantId());
                    pv.setProdStatCd(vo.getProdStatusCd());
                    String result = this.prodService.registProdSettl(pv);
                    log.info("CMS 정산율 = " + result);
                }

                if (!prodExistTenant.contains(tenantId)) {

                    // newFree 데이터 처리
                    String stdDt = displayCommonService.getBatchStandardDateString(tenantId, DisplayConstants.DP_LIST_NEWFREE);
                    this.prodService.insertNewFreeData(tenantId, prodId, stdDt);
                    log.info("CMS New Free Data Insert");
                }

                if (tempList != null) {
                    for (Map<String, Object> oldProd : tempList) {
                        if (StringUtils.equals((String)oldProd.get("TENANTID"), tenantId)) {
                            String oldProdStatCd = (String) oldProd.get("PRODSTATUSCD");
                            String oldTopMenuId = (String) oldProd.get("TOPMENUID");

                            // 판매중인 상품의 카테고리 대분류가 변경될시 운영자추천상품 - SUB 상품 삭제
                            log.info("CMS 운영자 추천 상품 삭제 여부 Check | " + oldProdStatCd);
                            if (IFConstants.CONTENT_SALE_STAT_ING.equals(oldProdStatCd)) {
                                String topCatCd = dpProd.getTopMenuId();
                                log.info(oldTopMenuId + " == " + topCatCd);
                                if (!oldTopMenuId.equals(topCatCd)) {
                                    this.prodService.removeAdminRecommand(tenantId, prodId);
                                }
                            }
                        }
                    }
                }
            }

            // 바이너리 수정이 있을 경우 화이트 리스트 배포
			log.info("CMS White List Regist");
			this.prodService.insertWhiteList(prodId);

		}		
	}

    private void execSapPhase1(NotificationRefactoringSac notification) {
        boolean s02 = notification.getDpSapMapping() != null && "Y".equals(notification.getDpSapMapping().getKtCheckYn());
        boolean s03 = notification.getDpSapMapping() != null && "Y".equals(notification.getDpSapMapping().getLgCheckYn());

        ////////// 모상품 처리
        List<DPTenantProductVO> tenantInfo = notification.getDpProductTotal().getDpTenantProduct();
        List<DPTenantProductPriceVO> tenantPriceInfo = notification.getDpProductTotal().getDpTenantProductPrice();
        if(s02) {
            duplicateTenantProd(tenantInfo, "S02");
            duplicateTenantPriceProd(tenantPriceInfo, "S02");
        }

        if(s03) {
            duplicateTenantProd(tenantInfo, "S03");
            duplicateTenantPriceProd(tenantPriceInfo, "S03");
        }


        ////////// 인앱상품 처리
        List<DPProductTotalVO> iapProductList = notification.getDpProductTotalList();
        if(CollectionUtils.isNotEmpty(iapProductList)) {
            for (DPProductTotalVO iapProd : iapProductList) {

                List<DPTenantProductVO> iapTProd = iapProd.getDpTenantProduct();
                List<DPTenantProductPriceVO> iapTPP = iapProd.getDpTenantProductPrice();

                if(s02) {
                    duplicateTenantProd(iapTProd, "S02");
                    duplicateTenantPriceProd(iapTPP, "S02");
                }
                if(s03) {
                    duplicateTenantProd(iapTProd, "S03");
                    duplicateTenantPriceProd(iapTPP, "S03");
                }
            }
        }
    }

    private void duplicateTenantProd(List<DPTenantProductVO> tpList, String tenantId) {
        if(CollectionUtils.isEmpty(tpList) || tenantId == null)
            return;

        DPTenantProductVO tpS01 = tpList.get(0);
        DPTenantProductVO dup = new DPTenantProductVO();
        BeanUtils.copyProperties(tpS01, dup);
        dup.setTenantId(tenantId);
        tpList.add(dup);
    }

    private void duplicateTenantPriceProd(List<DPTenantProductPriceVO> tppList, String tenantId) {
        if(CollectionUtils.isEmpty(tppList) || tenantId == null)
            return;

        DPTenantProductPriceVO tppS01 = tppList.get(0);
        DPTenantProductPriceVO dup = new DPTenantProductPriceVO();
        BeanUtils.copyProperties(tppS01, dup);
        dup.setTenantId(tenantId);
        tppList.add(dup);
    }

    private void insertInAppInfo(List<DPProductTotalVO> displayInAppProductList) {

        for (DPProductTotalVO inAppProduct : displayInAppProductList) {
            /*
             * IN-APP 전시상품 정보
             */
            DPProductVO inAppDpProdInfo = inAppProduct.getDpProduct();
            if (null != inAppDpProdInfo.getProdId()) {

                // IN-APP 전시상품 정보 등록
                log.info("Insert CMS InAppDpProdInfo Info");
                dpProductService.insertDPProduct(inAppProduct.getDpProduct());
            }

            /*
             * IN-APP 상품정보 매핑
             */
            DPProductRshpVO inAppDpProdRshpInfo = inAppProduct.getDpProductRshp();
            if (null != inAppDpProdRshpInfo.getProdId()) {

                // IN-APP 상품정보 매핑 등록
                log.info("Insert CMS InAppDpProdRshp Info");
                dpProductRshpService.insertDPProductRshp(inAppProduct.getDpProductRshp());
            }

            /*
             * IN-APP 전시상품 상품상세
             */
            List<DPProductDescVO> inAppDpProdDesc = inAppProduct.getDpProductDesc();
            if(CollectionUtils.isNotEmpty(inAppDpProdDesc)) {
                for (DPProductDescVO vo : inAppDpProdDesc) {

                    // IN-APP 전시상품 상품상세 등록
                    log.info("Insert CMS InAppDpProdDesc Info");
                    dpProductDescService.insertDPProductDesc(vo);
                }
            }

            /*
             * IN-APP 전시상품 APP 상품
             */
            DPAppProductVO inAppDpProdAppInfo = inAppProduct.getDpAppProduct();
            if (null != inAppDpProdAppInfo.getProdId()) {

                // IN-APP 전시상품 APP 상품 등록
                log.info("Insert CMS inAppDpProdApp Info #{}", inAppDpProdInfo.getProdId());
                dpAppProductService.insertDPAppProduct(inAppProduct.getDpAppProduct());
            }

            /*
             * IN-APP 테넌트 정보
             */
            List<DPTenantProductVO> inAppTenantInfo = inAppProduct.getDpTenantProduct();
            if(CollectionUtils.isNotEmpty(inAppTenantInfo)) {
                for (DPTenantProductVO vo : inAppTenantInfo) {

                    // IN-APP 테넌트 정보 등록
                    log.info("Insert CMS InAppTenant Info");
                    dpTenantProductService.insertDPTenant(vo);
                }
            }

            /*
             * IN-APP 테넌트 상품 가격
             */
            List<DPTenantProductPriceVO> inAppTenantPriceInfo = inAppProduct.getDpTenantProductPrice();
            if(CollectionUtils.isNotEmpty(inAppTenantPriceInfo)) {
                for (DPTenantProductPriceVO vo : inAppTenantPriceInfo) {

                    // IN-APP 테넌트 상품 가격 등록
                    log.info("Insert CMS InAppTenantPrice Info");
                    dpTenantProductPriceService.insertDPTenantPrice(vo);
                }
            }
        }
    }

    private void printCountLog(String msg, List list) {
        log.info(msg, list != null ? list.size() : 0);
    }
}