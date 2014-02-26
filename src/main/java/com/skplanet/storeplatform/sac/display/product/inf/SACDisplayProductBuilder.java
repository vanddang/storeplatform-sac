package com.skplanet.storeplatform.sac.display.product.inf;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.icms.refactoring.deploy.DPAppProductVO;
import com.skplanet.icms.refactoring.deploy.DPProductCatMapVO;
import com.skplanet.icms.refactoring.deploy.DPProductDescVO;
import com.skplanet.icms.refactoring.deploy.DPProductImgVO;
import com.skplanet.icms.refactoring.deploy.DPProductRshpVO;
import com.skplanet.icms.refactoring.deploy.DPProductSubContsVO;
import com.skplanet.icms.refactoring.deploy.DPProductTotalVO;
import com.skplanet.icms.refactoring.deploy.DPProductUpdVO;
import com.skplanet.icms.refactoring.deploy.DPProductVO;
import com.skplanet.icms.refactoring.deploy.DPSeedMappVO;
import com.skplanet.icms.refactoring.deploy.DPSprtDeviceVO;
import com.skplanet.icms.refactoring.deploy.DPTagInfoVO;
import com.skplanet.icms.refactoring.deploy.DPTenantProductPriceVO;
import com.skplanet.icms.refactoring.deploy.DPTenantProductVO;
import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.display.product.service.DPAppProductService;
import com.skplanet.storeplatform.sac.display.product.service.DPProductCatMapService;
import com.skplanet.storeplatform.sac.display.product.service.DPProductDescService;
import com.skplanet.storeplatform.sac.display.product.service.DPProductImgService;
import com.skplanet.storeplatform.sac.display.product.service.DPProductRshpService;
import com.skplanet.storeplatform.sac.display.product.service.DPProductService;
import com.skplanet.storeplatform.sac.display.product.service.DPProductSubContsService;
import com.skplanet.storeplatform.sac.display.product.service.DPProductUpdService;
import com.skplanet.storeplatform.sac.display.product.service.DPSeedMappService;
import com.skplanet.storeplatform.sac.display.product.service.DPSprtDeviceService;
import com.skplanet.storeplatform.sac.display.product.service.DPTagInfoService;
import com.skplanet.storeplatform.sac.display.product.service.DPTenantProductPriceService;
import com.skplanet.storeplatform.sac.display.product.service.DPTenantProductService;
import com.skplanet.storeplatform.sac.display.product.service.ProductService;
import com.skplanet.storeplatform.sac.display.product.vo.ProductVo;

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
	private DPTagInfoService dpTagInfoService;

	@Autowired
	private DPSeedMappService dpSeedMappService;
	
	@Autowired
	private ProductService prodService;

	@Override
	public void build(NotificationRefactoringSac notification, List<Map<String, Object>> tempList) throws StorePlatformException {

		DPProductVO dpProd = notification.getDpProductTotal().getDpProduct();
		String prodId = dpProd.getProdId(); // 상품_아이디
		String mbrNo = dpProd.getSellerMbrNo(); // 회원_번호
		
		/*
		 * 전시상품 정보
		 */
		dpProductService.insertDPProduct(notification.getDpProductTotal().getDpProduct());
		
		
		/*
		 * 테넌트 정보
		 */
		List<DPTenantProductVO> tenantInfo = notification.getDpProductTotal().getDpTenantProduct(); 
		if (null != tenantInfo) {
			log.info("CMS TenantInfo Size = " + tenantInfo.size());
			
			if (0 < tenantInfo.size()) {
				for (DPTenantProductVO vo : tenantInfo) {
					
					// 테넌트 정보 등록
					log.info("CMS Tenant Info");
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
					log.info("CMS TenantPrice Info");
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
					log.info("CMS DpProdDesc Info");
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
			log.info("CMS DpProdApp Info");
			dpAppProductService.insertDPAppProduct(notification.getDpProductTotal().getDpAppProduct());

		}
		
		
		
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
					log.info("CMS InAppDpProdInfo Info");
					dpProductService.insertDPProduct(inAppProduct.getDpProduct());
				}			
				
				/*
				 * IN-APP 상품정보 매핑
				 */
				DPProductRshpVO inAppDpProdRshpInfo = inAppProduct.getDpProductRshp(); 
				if (null != inAppDpProdRshpInfo.getProdId()) {
					
					// IN-APP 상품정보 매핑 등록
					log.info("CMS InAppDpProdRshp Info");
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
							log.info("CMS InAppTenant Info");
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
							log.info("CMS InAppTenantPrice Info");
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
							log.info("CMS InAppDpProdDesc Info");
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
					log.info("CMS inAppDpProdApp Info");
					dpAppProductService.insertDPAppProduct(inAppProduct.getDpAppProduct());

				}				

			}
		}

		
		/*
		 * 전시상품/부분유료화상품 카테고리정보 
		 */
		List<DPProductCatMapVO> displayCategoryList = notification.getDpProductCatMap();
		if(displayCategoryList != null){
			log.info("CMS displayCategoryList Size = " + displayCategoryList.size());
			if(displayCategoryList.size() >0){
				
				for (DPProductCatMapVO vo : displayCategoryList) {

					// 전시상품/부분유료화상품 카테고리정보  등록
					log.info("CMS DisplayCategoryList Info");
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
					log.info("CMS DisplayProductImageList Info");
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
					log.info("CMS DisplayProductUpdateList Info");
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
			if(displaySupportPhoneList.size() >0){
				
				for (DPSprtDeviceVO vo : displaySupportPhoneList) {

					// 전시상품 단말 등록
					log.info("CMS DisplaySupportPhoneList Info");
					dpSprtDeviceService.insertDPSprtDevice(vo);
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
					log.info("CMS DisplaySubContentList Info");
					dpProductSubContsService.insertDPProductSubconts(vo);
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
					log.info("CMS DisplayTabInfoList Info");
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
					log.info("CMS DisplaySeedMappList Info");
					dpSeedMappService.insertDPSeedMapp(vo);
				}
			}
		}
		
		
		
		
		if (null != tenantInfo) {
			log.info("CMS tenantInfo Size = " + tenantInfo.size());
			
			if (0 < tenantInfo.size()) {
				for (DPTenantProductVO vo : tenantInfo) {
					String oldProdStatCd = ""; // 기존_판매_상태
					String oldTopMenuId = "";
							
					if (null != tempList) {
						if(tempList.size() > 0){
							for(int count = 0; count < tempList.size() ; count ++){
								
								Map<String, Object> oldProd = tempList.get(count);

								if(null != oldProd.get("TENANTID")){
									if(oldProd.get("TENANTID").equals(vo.getTenantId())){
										oldProdStatCd = (String)oldProd.get("PRODSTATUSCD");
										oldTopMenuId     = (String)oldProd.get("TOPMENUID");
										
										log.info("CMS PROD INFO = " + prodId + " | " + mbrNo);
										ProductVo pv = new ProductVo();
										pv.setMbrNo(mbrNo);
										pv = this.prodService.selectMemberInfo(pv);
										if (null == pv) throw new StorePlatformException(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR, "MBR_NO [ " + mbrNo + " ] 로 등록되어진 회원 정보가 없습니다.");
										
										//ProductVo 값 설정
										pv.setProdId(prodId);
										pv.setRegDt(vo.getRegDt());
										pv.setTenantId(vo.getTenantId());
										pv.setProdStatCd(vo.getProdStatusCd());

										// 신규 상품 등록
										// 트리거 확인 후 작업
										log.info("CMS New Free Data Insert");
										this.prodService.registNewFreeData(pv);
										
										// 판매중인 상품의 카테고리 대분류가 변경될시 운영자추천상품 - SUB 상품 삭제
										log.info("CMS 운영자 추천 상품 삭제 여부 Check | " + oldProdStatCd);
										if (IFConstants.CONTENT_SALE_STAT_ING.equals(oldProdStatCd)) {
											String topCatCd = dpProd.getTopMenuId();
											log.info(oldTopMenuId + " == " + topCatCd);
											if (!oldTopMenuId.equals(topCatCd)) {
												this.prodService.removeAdminRecommand(pv);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			// 바이너리 수정이 있을 경우 화이트 리스트 배포
			log.info("CMS White List Regist");
			this.prodService.registWhiteList(prodId);
			
			//TODO 정산율 배포 : 정산율 프로시져 만들어 지고 나서 확인 후 작업 
//			String result = this.prodService.registProdSettl(pv);
//			log.info("CMS 정산율 = " + result);

		}		
	}
}
