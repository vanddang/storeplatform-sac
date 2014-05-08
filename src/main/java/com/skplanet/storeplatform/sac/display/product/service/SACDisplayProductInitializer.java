package com.skplanet.storeplatform.sac.display.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.icms.refactoring.deploy.DPProductCatMapVO;
import com.skplanet.icms.refactoring.deploy.DPProductTotalVO;
import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;
import com.skplanet.storeplatform.sac.display.product.constant.IFConstants;

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

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	private DPAppDeltaDeployFileService dPAppDeltaDeployFileService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.icms.deploy.job.initializer.DisplayProductInitializer#clear(java.lang.String)
	 */
	@Override
	public void deleteProdInfo(NotificationRefactoringSac notification) {
		this.log.info("CMS executeProcess 3");
		String pid = notification.getDpProductTotal().getDpProduct().getProdId();
		List<DPProductTotalVO> inAppList = notification.getDpProductTotalList();
		List<DPProductCatMapVO> inAppCatList = notification.getDpProductCatMap();

		/*
		 * 전시상품 상품상세
		 */
		this.dpProductDescService.deleteDPProductDesc(pid);
		this.log.info("CMS executeProcess 4");
		/*
		 * 전시상품 APP 상품
		 */
		this.dpAppProductService.deleteDPAppProduct(pid);
		this.log.info("CMS executeProcess 5");
		/*
		 * 전시상품 배포파일정보
		 */
		this.dpProductSubContsService.deleteDPProductSubconts(null, pid);
		this.log.info("CMS executeProcess 6");
		/*
		 * 전시상품 이미지
		 */
		this.dpProductImgService.deleteDPProductImg(pid, null, null);
		this.log.info("CMS executeProcess 7");
		/*
		 * 전시상품 단말
		 */
		this.dpSprtDeviceService.deleteDPSprtDevice(pid, null);
		this.log.info("CMS executeProcess 8");
		/*
		 * 전시상품 업데이트이력
		 */
		this.dpProductUpdService.deleteDPProductUpd(null, pid);
		this.log.info("CMS executeProcess 9");

		/*
		 * 상품정보 매핑
		 */
		this.dpProductRshpService.deleteDPProductRshp(pid);
		this.log.info("CMS executeProcess 10");
		/*
		 * 테넌트 상품 가격
		 */
		this.dpTenantProductPriceService.deleteDPTenantPrice(pid);
		this.log.info("CMS executeProcess 11");
		/*
		 * 테넌트 정보
		 */
		this.dpTenantProductService.deleteDPTenant(pid);
		this.log.info("CMS executeProcess 12");

		/*
		 * 전시상품 Tag정보
		 */
		this.dpTagInfoService.deleteDPTagInfo(pid, null);
		this.log.info("CMS executeProcess 13");
		/*
		 * 전시상품 SeedMapp정보
		 */
		this.dPAppDeltaDeployFileService.deleteDPAppDeltaDeployFile(pid);
		this.log.info("CMS executeProcess 14");

		/*
		 * 전시상품 카테고리정보 :: pid(모상품pid) epsdProdId(모상품pid/부분유료화상품pid)
		 */
		List<DPProductCatMapVO> displayProductCategoryList = this.dpProductCatMapService.getDPProductCatList(pid, null,
				IFConstants.USE_Y, null);
		this.log.info("CMS executeProcess 15");
		for (DPProductCatMapVO productCategory : displayProductCategoryList) {
			this.dpProductCatMapService
					.deleteDPProductCat(productCategory.getProdId(), productCategory.getCategoryNo());
		}
		this.log.info("CMS executeProcess 16");

		if (null != inAppList) {

			/*
			 * 부분유료화상품 카테고리정보 :: pid(모상품pid) epsdProdId(모상품pid/부분유료화상품pid)
			 */
			this.log.info("CMS executeProcess 17");
			for (DPProductCatMapVO inAppCat : inAppCatList) {
				this.dpProductCatMapService.deleteDPProductCat(inAppCat.getProdId(), inAppCat.getCategoryNo());
			}
			this.log.info("CMS executeProcess 18");
			/*
			 * 부분유료화상품
			 */
			for (DPProductTotalVO inApp : inAppList) {
				/*
				 * 전시상품 상품상세
				 */
				this.dpProductDescService.deleteDPProductDesc(inApp.getDpProduct().getProdId());
				/*
				 * 전시상품 APP 상품
				 */
				this.dpAppProductService.deleteDPAppProduct(inApp.getDpProduct().getProdId());
				/*
				 * 상품정보 매핑
				 */
				this.dpProductRshpService.deleteDPProductRshp(inApp.getDpProduct().getProdId());
				/*
				 * 테넌트 상품 가격
				 */
				this.dpTenantProductPriceService.deleteDPTenantPrice(inApp.getDpProduct().getProdId());
				/*
				 * 테넌트 정보
				 */
				this.dpTenantProductService.deleteDPTenant(inApp.getDpProduct().getProdId());
				/*
				 * 전시상품 정보
				 */
				this.dpProductService.deleteDPPartProduct(inApp.getDpProduct().getProdId());
			}

		}
		this.log.info("CMS executeProcess 19");

		/*
		 * 전시상품 정보
		 */
		this.dpProductService.deleteDPProduct(pid);
		this.log.info("CMS executeProcess 20");

	}

}
