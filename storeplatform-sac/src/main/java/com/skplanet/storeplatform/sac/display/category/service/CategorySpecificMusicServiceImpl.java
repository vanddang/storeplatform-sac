/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.BellService;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.vo.CategorySpecificProduct;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 특정 상품 Music 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 이승훈, 엔텔스.
 */
@Service
public class CategorySpecificMusicServiceImpl implements CategorySpecificMusicService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private MusicInfoGenerator musicGenerator;

	@Autowired
    private MemberBenefitService memberBenefitService;
	
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.category.service.CategorySpecificMusicService#getSpecificMusicList
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategorySpecificSacRes getSpecificMusicList(CategorySpecificSacReq req, SacRequestHeader header) {

		CategorySpecificSacRes res = new CategorySpecificSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Product product = null;
		MetaInfo metaInfo = null;
		List<Product> productList = new ArrayList<Product>();
		CategorySpecificProduct categorySpecificProduct = null;

        List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
        if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_MUSIC_PARAMETER_LIMIT) {
            throw new StorePlatformException("SAC_DSP_0004", "list",
                    DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_MUSIC_PARAMETER_LIMIT);
        }
        // 상품 기본 정보 List 조회
        List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
                "CategorySpecificProduct.selectProductInfoList", prodIdList, ProductBasicInfo.class);

        if (productBasicInfoList != null) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("tenantHeader", header.getTenantHeader());
            paramMap.put("deviceHeader", header.getDeviceHeader());
            paramMap.put("lang", "ko");

            for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
                String topMenuId = productBasicInfo.getTopMenuId();
                String svcGrpCd = productBasicInfo.getSvcGrpCd();
                paramMap.put("productBasicInfo", productBasicInfo);

                this.log.debug("##### Top Menu Id : {}", topMenuId);
                this.log.debug("##### Service Group Cd : {}", svcGrpCd);

                // 상품 SVC_GRP_CD 조회
                // DP000203 : 멀티미디어
                // DP000206 : Tstore 쇼핑
                // DP000205 : 소셜쇼핑
                // DP000204 : 폰꾸미기
                // DP000201 : 애플리캐이션

                // 음원 상품의 경우
                if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)
                        || DisplayConstants.DP_DISPLAY_PHONE_TOP_MENU_ID.equals(topMenuId)) {

                    // topMenuId 뮤직으로 설정
                    productBasicInfo.setTopMenuId(DisplayConstants.DP_MUSIC_TOP_MENU_ID);
                    // 배치완료 기준일시 조회
                    productBasicInfo.setMenuId("DP004901");
                    paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

                    // metaInfo = this.metaInfoService.getMusicMetaInfo(paramMap);
                    metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getMusicMetaInfo", paramMap,
                            MetaInfo.class);

                    if (metaInfo != null) {
                        product = this.responseInfoGenerateFacade.generateSpecificMusicProduct(metaInfo);
                        // if (metaInfo.getOutsdContentsId() != null) {
                        paramMap.put("outsdContentsId", metaInfo.getOutsdContentsId());
                        paramMap.put("prodId", metaInfo.getProdId());
                        Music music = new Music();
                        product.setMusic(this.musicGenerator.generateMusic(metaInfo));
                        List<CategorySpecificProduct> metaList = this.commonDAO.queryForList(
                                "CategorySpecificProduct.selectMusicMetaList", paramMap,
                                CategorySpecificProduct.class);
                        if (metaList != null) {

                            List<BellService> bellServiceList = new ArrayList<BellService>();
                            BellService bellService = null;
                            for (int i = 0; i < metaList.size(); i++) {
                                categorySpecificProduct = metaList.get(i);
                                bellService = new BellService();
                                bellService.setName(categorySpecificProduct.getMetaClsfCd());
                                bellService.setType(categorySpecificProduct.getProdId());
                                bellServiceList.add(bellService);
                            }
                            music.setBellServiceList(bellServiceList);
                            // }
                            // Tstore멤버십 적립율 정보
                            List<Point> mileage = commonGenerator.generateMileage(memberBenefitService.getMileageInfo(header.getTenantHeader().getTenantId(), metaInfo.getTopMenuId(), metaInfo.getProdId(), metaInfo.getProdAmt()));
                            product.setPointList(mileage);

                            product.setMusic(music);
                        }
                        productList.add(product);
                    }
                }
            }
        }
        commonResponse.setTotalCount(productList.size());
        res.setCommonResponse(commonResponse);
        res.setProductList(productList);
        return res;
	}

}
