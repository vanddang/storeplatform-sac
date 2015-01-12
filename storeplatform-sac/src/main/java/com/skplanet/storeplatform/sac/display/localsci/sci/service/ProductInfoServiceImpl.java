package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.MapgProdMeta;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes.SellerMbrInfoSac;
import com.skplanet.storeplatform.sac.display.common.EbookComicType;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.ProductTypeInfo;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

/**
 *
 *
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회 서비스 구현체.
 *
 * Updated on : 2014. 2. 24. Updated by : 오승민, 인크로스
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.skplanet.storeplatform.sac.display.localsci.sci.service.ProductInfoService#getProductList(com.skplanet.
	 * storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq)
	 */
	@Override
	public ProductInfoSacRes getProductList(ProductInfoSacReq req) {
		ProductInfoSacRes res = new ProductInfoSacRes();
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		List<String> prodIdList = req.getList();

		// 상품 기본 정보 List 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("ProductInfo.selectProductInfoList",
				prodIdList, ProductBasicInfo.class);

		this.log.debug("##### parameter cnt : {}", prodIdList.size());

		if (productBasicInfoList != null) {
			// / 단말 지원 정보 조회
			SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelNo());

			this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lang", req.getLang());
			paramMap.put("deviceModelNo", req.getDeviceModelNo());
			paramMap.put("tenantId", req.getTenantId());
			paramMap.put("rshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
			paramMap.put("supportDevice", supportDevice);
			paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				String topMenuId = productBasicInfo.getTopMenuId();
				String svcGrpCd = productBasicInfo.getSvcGrpCd();
                String svcTp = productBasicInfo.getSvcTypeCd();
                String metaClsfCd = productBasicInfo.getMetaClsfCd();

                ProductTypeInfo typeInfo = this.displayCommonService.getProductTypeInfo(svcGrpCd, svcTp, metaClsfCd, topMenuId);

                ProductInfo product = null;
				paramMap.put("productBasicInfo", productBasicInfo);

				this.log.debug("##### Top Menu Id : {}", topMenuId);
				this.log.debug("##### Service Group Cd : {}", svcGrpCd);
				this.log.debug(":: ProductType={}", typeInfo.getProductType().toString());
				this.log.debug(":: MetaClsfCd={}", metaClsfCd);

				if (typeInfo.getProductType() == ProductType.App) {
					paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					paramMap.put("inAppRshpCd", DisplayConstants.DP_PARENT_CHILD_RELATIONSHIP_CD);
					product = this.commonDAO.queryForObject("ProductInfo.getAppMetaInfo", paramMap, ProductInfo.class);
				}
                else if(typeInfo.getProductType() == ProductType.Vod) {
                    paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
                    product = this.commonDAO.queryForObject("ProductInfo.getVODMetaInfo", paramMap,
                            ProductInfo.class);

                    if (product != null) {
                        product.setChapterUnit(this.displayCommonService.getVodChapterUnit());
                    }
                }
                else if(typeInfo.getProductType() == ProductType.EbookComic) {
                    if (typeInfo.getEbookComicType() == EbookComicType.Ebook) {
                        paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
                    } else {
                        paramMap.put("imageCd", DisplayConstants.DP_COMIC_EPISODE_REPRESENT_IMAGE_CD);
                    }
                    product = this.commonDAO.queryForObject("ProductInfo.getEbookComicMetaInfo", paramMap, ProductInfo.class);

                    if (product != null) {
                        product.setChapterUnit(this.displayCommonService.getEpubChapterUnit(product.getBookClsfCd()));
                    }
                }
                else if(typeInfo.getProductType() == ProductType.Music) {
                    paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
                    product = this.commonDAO.queryForObject("ProductInfo.getMusicMetaInfo", paramMap, ProductInfo.class);
                }
                else if(typeInfo.getProductType() == ProductType.RingBell) {
                    paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
                    product = this.commonDAO.queryForObject("ProductInfo.getRingBellMetaInfo", paramMap, ProductInfo.class);
                }
                else if (typeInfo.getProductType() == ProductType.Shopping) { // 쇼핑 상품의 경우
					paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
					paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
					product = this.commonDAO.queryForObject("ProductInfo.getShoppingMetaInfo", paramMap, ProductInfo.class);
				}
                else if (typeInfo.getProductType() == ProductType.Freepass) { // 정액 상품의 경우
					paramMap.put("imageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
					paramMap.put("ebookImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
					product = this.commonDAO.queryForObject("ProductInfo.getFreePassMetaInfo", paramMap,
							ProductInfo.class);
					List<MapgProdMeta> mapgProdIdList = null;
					mapgProdIdList = this.commonDAO.queryForList("ProductInfo.getMapgProdIdList", paramMap,
							MapgProdMeta.class);
					if (product != null) {
						if (mapgProdIdList != null) {
							if (mapgProdIdList.size() > 0) {
								product.setMapgProdIdList(mapgProdIdList);
							} else {
								product.setMapgProdIdList(null);
							}
						}
					}
				}

                if (product != null) {
                    product.setContentsTypeCd(productBasicInfo.getContentsTypeCd());
                    product.setSeriesYn(typeInfo.isSeries() ? "Y" : "N");
                    productList.add(product);
                }
			}
		}

		// 2014.06.12 판매자 정보 목록 조회 추가 (이태희D)
		String sellerMbrNo = null;
		List<String> sellerKeyList = null;
		DetailInformationListForProductSacReq sellerReq = null;
		DetailInformationListForProductSacRes sellerRes = null;

		try {
			sellerKeyList = new ArrayList<String>();
			sellerReq = new DetailInformationListForProductSacReq();

			// 회원 판매자 정보를 위한 판매자키 파라미터 세팅
			for (int i = 0; i < productList.size(); i++) {
				sellerMbrNo = productList.get(i).getSellerMbrNo();

				if (StringUtils.isNotEmpty(sellerMbrNo)) {
					sellerKeyList.add(sellerMbrNo);
				}
			}

			sellerReq.setSellerKeyList(sellerKeyList);

			this.log.debug("##### [SAC DSP LocalSCI] SAC Member Start : sellerSearchSCI.detailInformationListForProduct");
			long start = System.currentTimeMillis();

			// 회원 판매자 정보 조회
			sellerRes = this.sellerSearchSCI.detailInformationListForProduct(sellerReq);

			this.log.debug("##### [SAC DSP LocalSCI] SAC Member End : sellerSearchSCI.detailInformationListForProduct");
			long end = System.currentTimeMillis();
			this.log.debug("##### [SAC DSP LocalSCI] SAC Member sellerSearchSCI.detailInformationListForProduct takes {} ms", (end - start));

			for (int j = 0; j < productList.size(); j++) {
				ProductInfo product = productList.get(j);
				sellerMbrNo = productList.get(j).getSellerMbrNo();

				if (StringUtils.isNotEmpty(sellerMbrNo)) {
					SellerMbrInfoSac SellerMbrInfoSac = sellerRes.getSellerMbrMap().get(sellerMbrNo);

					if (SellerMbrInfoSac != null) {
						product.setSellerMbrInfoSac(SellerMbrInfoSac);

						productList.remove(j);
						productList.add(j, product);
					}
				}
			}
		} catch (Exception e) {
			this.log.error("판매자 정보 목록 조회 연동 중 오류가 발생하였습니다.\n", e);
		}

		res.setProductList(productList);
		return res;
	}
}
