/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductRequest;
import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.product.vo.FeatureVodProductDTO;

/**
 * FeatureVodProductService Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@Service
public class FeatureVodProductServiceImpl implements FeatureVodProductService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.FeatureVodProductService#searchFeatureVodProductList(com.skplanet
	 * .storeplatform.sac.client.product.vo.feature.FeatureVodProductRequestVO)
	 */
	@Override
	public FeatureVodProductResponse searchFeatureVodProductList(FeatureVodProductRequest requestVO) {
		FeatureVodProductResponse responseVO = null;

		List<FeatureVodProductDTO> resultList = this.commonDAO.queryForList(
				"FeatureVodProduct.selectAdminNewProductList", requestVO, FeatureVodProductDTO.class);

		if (resultList != null) {
			FeatureVodProductDTO productDto = new FeatureVodProductDTO();

			// Response VO를 만들기위한 생성자
			Identifier identifier = new Identifier();
			Menu menu = new Menu();
			Contributor contributor = new Contributor();
			Date date = new Date();
			Accrual accrual = new Accrual();
			Rights rights = new Rights();
			Title title = new Title();
			Source source = new Source();
			Price price = new Price();
			Product product = new Product();

			List<Menu> menuList = new ArrayList<Menu>();
			List<Source> sourceList = new ArrayList<Source>();
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < resultList.size(); i++) {
				productDto = resultList.get(i);

				// 상품 정보 (상품ID)
				identifier.setType("channel");
				identifier.setText(productDto.getProdId());

				// 상품 정보 (지원구분)
				product.setSupport(productDto.getHdvYn());

				// 메뉴 정보
				menu.setType("topClass");
				menu.setId(productDto.getUpMenuId());
				menu.setName(productDto.getUpMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(productDto.getMenuId());
				menu.setName(productDto.getMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setType("metaClass");
				menu.setId(productDto.getMetaClsfCd());
				menuList.add(menu);

				// 저자 정보
				contributor.setDirector(productDto.getArtist1Nm());
				contributor.setArtist(productDto.getArtist2Nm());
				date.setType("발매일");
				date.setText(productDto.getIssueDay());
				contributor.setDate(date);

				// 평점 정보
				accrual.setVoterCount(productDto.getPaticpersCnt());
				accrual.setDownloadCount(productDto.getPrchsQty());
				accrual.setScore(Double.parseDouble(productDto.getAvgEvluScore()));

				// 이용권한 정보
				rights.setGrade(productDto.getProdGrdCd());

				// 상품 정보 (상품명)
				title.setText(productDto.getProdNm());

				// 이미지 정보
				source.setUrl(productDto.getImgFilePath());
				sourceList.add(source);

				// 상품 정보 (상품설명)
				product.setProductExplain(productDto.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price.setText(Integer.parseInt(productDto.getProdAmt()));

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setPrice(price);
				productList.add(i, product);

				identifier = new Identifier();
				menu = new Menu();
				contributor = new Contributor();
				date = new Date();
				accrual = new Accrual();
				rights = new Rights();
				title = new Title();
				source = new Source();
				price = new Price();
				product = new Product();

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
			}

			responseVO = new FeatureVodProductResponse();
			responseVO.setProductList(productList);

			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setTotalCount(productDto.getTotalCount());
			responseVO.setCommonResponse(commonResponse);
		}
		return responseVO;
	}
}
