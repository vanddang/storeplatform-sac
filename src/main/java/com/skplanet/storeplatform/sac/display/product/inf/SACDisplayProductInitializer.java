package com.skplanet.storeplatform.sac.display.product.inf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.icms.refactoring.deploy.DPProductCatMapVO;
import com.skplanet.icms.refactoring.deploy.DPProductTotalVO;
import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;
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

/**
 * 
 * SACDisplayProductInitializer 
 * 
 * CMS 전시 배포 기존 정보 초기화 서비스 구현체.
 * 
 * Updated on : 2014. 2. 13. Updated by : 차명호, ANB
 */

@Service
public class SACDisplayProductInitializer implements DisplayProductInitializer {

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

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.job.initializer.DisplayProductInitializer#clear(java.lang.String)
	 */
	public void clear(NotificationRefactoringSac notification) {
		
		String pid= notification.getDpProductTotal().getDpProduct().getProdId();
		List<DPProductTotalVO> inAppList =notification.getDpProductTotalList();
		List<DPProductCatMapVO> inAppCatList =notification.getDpProductCatMap();
		
		/*
		 * 전시상품 상품상세
		 */
		dpProductDescService.deleteDPProductDesc(pid);
		
		/*
		 * 전시상품 APP 상품
		 */
		dpAppProductService.deleteDPAppProduct(pid);
		/*
		 * 전시상품 배포파일정보
		 */
		dpProductSubContsService.deleteDPProductSubconts(null, pid);
		
		/*
		 * 전시상품 이미지
		 */
		dpProductImgService.deleteDPProductImg(pid, null, null);
		
		/*
		 * 전시상품 단말
		 */
		dpSprtDeviceService.deleteDPSprtDevice(pid, null);

		/*
		 * 전시상품 업데이트이력
		 */
		dpProductUpdService.deleteDPProductUpd(null, pid);

		
		/*
		 * 상품정보 매핑
		 */
		dpProductRshpService.deleteDPProductRshp(pid);
		
		/*
		 * 테넌트 상품 가격
		 */
		dpTenantProductPriceService.deleteDPTenantPrice(pid);
		
		/*
		 * 테넌트 정보
		 */
		dpTenantProductService.deleteDPTenant(pid);
		
		
		/*
		 * 전시상품 Tag정보
		 */
		dpTagInfoService.deleteDPTagInfo(pid, null);
		
		/*
		 * 전시상품 SeedMapp정보
		 */
		dpSeedMappService.deleteDPSeedMapp(pid, null);
		

		/*
		 * 전시상품 카테고리정보 :: pid(모상품pid)  epsdProdId(모상품pid/부분유료화상품pid)
		 */
		List<DPProductCatMapVO> displayProductCategoryList = dpProductCatMapService.getDPProductCatList(pid, null, IFConstants.USE_Y, null);
		for (DPProductCatMapVO productCategory : displayProductCategoryList) {
			dpProductCatMapService.deleteDPProductCat(productCategory.getProdId(), productCategory.getCategoryNo());
		}
		
		if(null != inAppList){
			
			/*
			 * 부분유료화상품 카테고리정보 :: pid(모상품pid)  epsdProdId(모상품pid/부분유료화상품pid)
			 */
			for (DPProductCatMapVO inAppCat : inAppCatList) {
				dpProductCatMapService.deleteDPProductCat(inAppCat.getProdId(), inAppCat.getCategoryNo());
			}
			
			/*
			 * 부분유료화상품
			 */
			for (DPProductTotalVO inApp : inAppList) {
				/*
				 * 전시상품 상품상세
				 */
				dpProductDescService.deleteDPProductDesc(inApp.getDpProduct().getProdId());
				
				/*
				 * 전시상품 APP 상품
				 */
				dpAppProductService.deleteDPAppProduct(inApp.getDpProduct().getProdId());
				
				/*
				 * 상품정보 매핑
				 */
				dpProductRshpService.deleteDPProductRshp(inApp.getDpProduct().getProdId());
				
				/*
				 * 테넌트 상품 가격
				 */
				dpTenantProductPriceService.deleteDPTenantPrice(inApp.getDpProduct().getProdId());
				
				/*
				 * 테넌트 정보
				 */
				dpTenantProductService.deleteDPTenant(inApp.getDpProduct().getProdId());
				/*
				 * 전시상품 정보
				 */
				dpProductService.deleteDPPartProduct(inApp.getDpProduct().getProdId());
				
			}
			
		}
		
		/*
		 * 전시상품 정보
		 */
		dpProductService.deleteDPProduct(pid);
		
	}

}
