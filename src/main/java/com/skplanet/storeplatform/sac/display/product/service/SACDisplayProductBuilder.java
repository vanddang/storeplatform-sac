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
		if (null != tenantInfo) {
			log.info("CMS TenantInfo Size = " + tenantInfo.size());
			
			if (0 < tenantInfo.size()) {
				for (DPTenantProductVO vo : tenantInfo) {
					
					// 테넌트 정보 등록
					log.info("Insert CMS Tenant Info");
					dpTenantProductService.insertDPTenant(vo);
				}
			}

		}
		
		
		/*
		 * 테넌트 상품 가격
		 */
		List<DPTenantProductPriceVO> tenantPriceInfo = notification.getDpProductTotal().getDpTenantProductPrice(); 
		if (null != tenantPriceInfo) {
			log.info("CMS TenantPriceInfo Size = " + tenantPriceInfo.size());
			
			if (0 < tenantPriceInfo.size()) {
				for (DPTenantProductPriceVO vo : tenantPriceInfo) {
					
					// 테넌트 상품 가격 등록
					log.info("Insert CMS TenantPrice Info");
					dpTenantProductPriceService.insertDPTenantPrice(vo);
				}
			}

		}
		
		/*
		 * 전시상품 상품상세
		 */
		List<DPProductDescVO> dpProdDesc = notification.getDpProductTotal().getDpProductDesc(); 
		if (null != dpProdDesc) {
			log.info("CMS DpProdDesc Size = " + dpProdDesc.size());
			
			if (0 < dpProdDesc.size()) {
				for (DPProductDescVO vo : dpProdDesc) {
					
					// 전시상품 상품상세 등록
					log.info("Insert CMS DpProdDesc Info");
					dpProductDescService.insertDPProductDesc(vo);
				}
			}

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
        insertInAppInfo(notification, tenantInfo);


		
		/*
		 * 전시상품/부분유료화상품 카테고리정보 
		 */
		List<DPProductCatMapVO> displayCategoryList = notification.getDpProductCatMap();
		if(displayCategoryList != null){
			log.info("CMS displayCategoryList Size = " + displayCategoryList.size());
			if(displayCategoryList.size() >0){
				
				for (DPProductCatMapVO vo : displayCategoryList) {

					// 전시상품/부분유료화상품 카테고리정보  등록
					log.info("Insert CMS DisplayCategory Info");
					dpProductCatMapService.insertDPProductCat(vo);
				}
			}
		}

		
		/*
		 * 전시상품 이미지
		 */
		List<DPProductImgVO> displayProductImageList = notification.getDpProductImgList();
		if(displayProductImageList != null){
			log.info("CMS displayProductImageList Size = " + displayProductImageList.size());
			if(displayProductImageList.size() >0){
				
				for (DPProductImgVO vo : displayProductImageList) {

					// 전시상품 이미지 등록
					log.info("Insert CMS DisplayProductImage Info");
					dpProductImgService.insertDPProductImg(vo);
				}
			}
		}
		
		/*
		 * 전시상품 업데이트이력
		 */
		List<DPProductUpdVO> displayProductUpdateList = notification.getDpProductUpdList();
		if(displayProductUpdateList != null){
			log.info("CMS displayProductUpdateList Size = " + displayProductUpdateList.size());
			if(displayProductUpdateList.size() >0){
				
				for (DPProductUpdVO vo : displayProductUpdateList) {

					// 전시상품 업데이트이력 등록
					log.info("Insert CMS DisplayProductUpdate Info");
					dpProductUpdService.insertDPProductUpd(vo);
				}
			}
		}
		
		/*
		 * 전시상품 단말
		 */
		List<DPSprtDeviceVO> displaySupportPhoneList = notification.getDpSprtDeviceList();
		if(displaySupportPhoneList != null){
			log.info("CMS displaySupportPhoneList Size = " + displaySupportPhoneList.size());
			if(displaySupportPhoneList.size() >0) {

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
        }
		
		/*
		 * 전시상품 배포파일정보
		 */
		List<DPProductSubContsVO> displaySubContentList = notification.getDpProductSubContsList();
		if(displaySubContentList != null){
			log.info("CMS displaySubContentList Size = " + displaySubContentList.size());
			if(displaySubContentList.size() >0){
				
				for (DPProductSubContsVO vo : displaySubContentList) {

					// 전시상품 배포파일정보 등록
					log.info("Insert CMS DisplaySubContent Info - SubContentsId#{}", vo.getSubContentsId());
					dpProductSubContsService.insertDPProductSubconts(vo, DisplayCryptUtils.hashPkgNm(vo.getApkPkgNm()));
				}
			}
		}
		
		/*
		 * 전시상품 Tag정보
		 */
		List<DPTagInfoVO> displayTabInfoList = notification.getDpTagInfoList();
		if(displayTabInfoList != null){
			log.info("CMS displayTabInfoList Size = " + displayTabInfoList.size());
			if(displayTabInfoList.size() >0){
				
				for (DPTagInfoVO vo : displayTabInfoList) {

					// 전시상품 Tag정보 등록
					log.info("Insert CMS DisplayTabInfo Info");
					dpTagInfoService.insertDPTagInfo(vo);
				}
			}
		}
		
		/*
		 * 전시상품 SeedMapp정보
		 */
		List<DPSeedMappVO> displaySeedMappList = notification.getDpSeedMappList();
		if(displayProductImageList != null){
			log.info("CMS displaySeedMappList Size = " + displaySeedMappList.size());
			if(displaySeedMappList.size() >0){
				
				for (DPSeedMappVO vo : displaySeedMappList) {

					// 전시상품 SeedMapp정보
					log.info("Insert CMS DisplaySeedMapp Info - {}/{}", vo.getProdId(), vo.getCaseRefCd());
					dpSeedMappService.insertDPSeedMapp(vo);
				}
			}
		}
		
		
		/*
		 * 델타 File 정보
		 */
		List<DPAppDeltaDeployFileVO> dPAppDeltaDeployFileList = notification.getDpAppDeltaDeployFileList();
		if(dPAppDeltaDeployFileList != null){
			log.info("CMS dPAppDeltaDeployFileList Size = " + dPAppDeltaDeployFileList.size());
			if(dPAppDeltaDeployFileList.size() >0){
				
				for (DPAppDeltaDeployFileVO vo : dPAppDeltaDeployFileList) {

					// 델타 File 정보
					log.info("Insert CMS DpAppDeltaDeployFile Info");
					dPAppDeltaDeployFileService.insertDPAppDeltaDeployFile(vo);
				}
			}
		}

        /**
         * SAP 상품 매핑 정보 - Phase 1에서만 이용함
         */
        log.info("Insert CMS DpSapProdMapg Info");
        DPSapMappingVO dpSapMapping = notification.getDpSapMapping();
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

        List<DPTenantProductVO> tenantInfo = notification.getDpProductTotal().getDpTenantProduct();
        if(CollectionUtils.isNotEmpty(tenantInfo) && tenantInfo.size() == 1) {
            DPTenantProductVO tpS01 = tenantInfo.get(0);
            if(s02) {
                DPTenantProductVO tpS02 = new DPTenantProductVO();
                BeanUtils.copyProperties(tpS01, tpS02);
                tpS02.setTenantId("S02");
                tenantInfo.add(tpS02);
            }
            if(s03) {
                DPTenantProductVO tpS03 = new DPTenantProductVO();
                BeanUtils.copyProperties(tpS01, tpS03);
                tpS03.setTenantId("S03");
                tenantInfo.add(tpS03);
            }
        }

        List<DPTenantProductPriceVO> tenantPriceInfo = notification.getDpProductTotal().getDpTenantProductPrice();
        if(CollectionUtils.isNotEmpty(tenantPriceInfo) && tenantPriceInfo.size() == 1) {
            DPTenantProductPriceVO tppS01 = tenantPriceInfo.get(0);
            if(s02) {
                DPTenantProductPriceVO tppS02 = new DPTenantProductPriceVO();
                BeanUtils.copyProperties(tppS01, tppS02);
                tppS02.setTenantId("S02");
                tenantPriceInfo.add(tppS02);
            }
            if(s03) {
                DPTenantProductPriceVO tppS03 = new DPTenantProductPriceVO();
                BeanUtils.copyProperties(tppS01, tppS03);
                tppS03.setTenantId("S03");
                tenantPriceInfo.add(tppS03);
            }
        }
    }

    private void insertInAppInfo(NotificationRefactoringSac notification, List<DPTenantProductVO> tenantInfo) {
    /*
     * IN-APP 정보
     */
        List<DPProductTotalVO> displayInAppProductList = notification.getDpProductTotalList();
        if( displayInAppProductList != null){

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
                 * IN-APP 테넌트 정보
                 */

                List<DPTenantProductVO> inAppTenantInfo = inAppProduct.getDpTenantProduct();
                if (null != inAppTenantInfo) {
                    log.info("CMS InAppTenantInfo Size = " + tenantInfo.size());

                    if (0 < inAppTenantInfo.size()) {
                        for (DPTenantProductVO vo : inAppTenantInfo) {

                            // IN-APP 테넌트 정보 등록
                            log.info("Insert CMS InAppTenant Info");
                            dpTenantProductService.insertDPTenant(vo);
                        }
                    }

                }


                /*
                 * IN-APP 테넌트 상품 가격
                 */
                List<DPTenantProductPriceVO> inAppTenantPriceInfo = inAppProduct.getDpTenantProductPrice();
                if (null != inAppTenantPriceInfo) {
                    log.info("CMS InAppTenantPriceInfo Size = " + inAppTenantPriceInfo.size());

                    if (0 < inAppTenantPriceInfo.size()) {
                        for (DPTenantProductPriceVO vo : inAppTenantPriceInfo) {

                            // IN-APP 테넌트 상품 가격 등록
                            log.info("Insert CMS InAppTenantPrice Info");
                            dpTenantProductPriceService.insertDPTenantPrice(vo);
                        }
                    }

                }

                /*
                 * IN-APP 전시상품 상품상세
                 */
                List<DPProductDescVO> inAppDpProdDesc = inAppProduct.getDpProductDesc();
                if (null != inAppDpProdDesc) {
                    log.info("CMS InAppDpProdDesc Size = " + inAppDpProdDesc.size());

                    if (0 < inAppDpProdDesc.size()) {
                        for (DPProductDescVO vo : inAppDpProdDesc) {

                            // IN-APP 전시상품 상품상세 등록
                            log.info("Insert CMS InAppDpProdDesc Info");
                            dpProductDescService.insertDPProductDesc(vo);
                        }
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

            }
        }
    }
}
