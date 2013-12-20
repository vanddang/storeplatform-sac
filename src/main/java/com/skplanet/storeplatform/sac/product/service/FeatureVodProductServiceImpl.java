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
import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductRequestVO;
import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductResponseVO;
import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductVO;
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
import com.skplanet.storeplatform.sac.product.vo.FeatureVodProductMapperVO;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
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
	public FeatureVodProductResponseVO searchFeatureVodProductList(FeatureVodProductRequestVO requestVO) {
		FeatureVodProductResponseVO responseVO = null;

		List<FeatureVodProductMapperVO> resultList = this.commonDAO.queryForList(
				"FeatureVodProduct.selectAdminNewProductList", requestVO, FeatureVodProductMapperVO.class);

		if (resultList != null) {
			FeatureVodProductMapperVO mapperVO = new FeatureVodProductMapperVO();

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

			FeatureVodProductVO featureVodProductVO = new FeatureVodProductVO();
			List<FeatureVodProductVO> listVO = new ArrayList<FeatureVodProductVO>();

			for (int i = 0; i < resultList.size(); i++) {
				mapperVO = resultList.get(i);

				// 상품 정보 (상품ID)
				identifier.setType("channel");
				identifier.setText(mapperVO.getProdId());

				// 상품 정보 (지원구분)
				product.setSupport(mapperVO.getHdvYn());

				// 메뉴 정보
				menu.setType("topClass");
				menu.setId(mapperVO.getUpMenuId());
				menu.setName(mapperVO.getUpMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(mapperVO.getMenuId());
				menu.setName(mapperVO.getMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setType("metaClass");
				menu.setId(mapperVO.getMetaClsfCd());
				menuList.add(menu);

				// 저자 정보
				contributor.setDirector(mapperVO.getArtist1Nm());
				contributor.setArtist(mapperVO.getArtist2Nm());
				date.setType("발매일");
				date.setText(mapperVO.getIssueDay());
				contributor.setDate(date);

				// 평점 정보
				accrual.setVoterCount(mapperVO.getPaticpersCnt());
				accrual.setDownloadCount(mapperVO.getPrchsQty());
				accrual.setScore(Double.parseDouble(mapperVO.getAvgEvluScore()));

				// 이용권한 정보
				rights.setGrade(mapperVO.getProdGrdCd());

				// 상품 정보 (상품명)
				title.setText(mapperVO.getProdNm());

				// 이미지 정보
				source.setUrl(mapperVO.getImgFilePath());
				sourceList.add(source);

				// 상품 정보 (상품설명)
				product.setProductExplain(mapperVO.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price.setText(Integer.parseInt(mapperVO.getProdAmt()));

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setPrice(price);

				featureVodProductVO.setProduct(product);
				listVO.add(i, featureVodProductVO);

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

				featureVodProductVO = new FeatureVodProductVO();
			}

			responseVO = new FeatureVodProductResponseVO();
			responseVO.setFeatureVodProductList(listVO);
		}
		return responseVO;
	}
}
