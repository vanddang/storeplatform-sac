/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.category.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.CategoryEpubReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.CategoryEpubRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.feature.category.vo.CategoryEpubDTO;

/**
 * 
 * 
 * Updated on : 2013. 12. 24. Updated by : 서영배, GTSOFT.
 */
@org.springframework.stereotype.Service
@Transactional
public class FeatureCategoryEpubServiceImpl implements FeatureCategoryEpubService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public CategoryEpubRes searchEpubList(CategoryEpubReq requestVO) {
		// TODO Auto-generated method stub
		//공통 응답 변수 선언
		int totalCount = 0;
		CategoryEpubRes responseVO = null;
		CommonResponse commonResponse = null;

		List<CategoryEpubDTO> resultList = this.commonDAO.queryForList("FeatureCategory.selectCategoryEpubListDummy",requestVO, CategoryEpubDTO.class);
		List<Product> listVO = new ArrayList<Product>();
		
		CategoryEpubDTO categoryEpubDTO;
		Product product;
		Identifier identifier;
		Title title;
		Book book;
		Accrual accrual;
		Rights rights;
		Source source;
		Price price;
		Support support;
		Menu menu;
		Contributor contributor;
		
		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Support> supportList;
		
		for (int i = 0 ; resultList != null && i < resultList.size(); i++ ) {
			
			categoryEpubDTO = resultList.get(i);
			product = new Product();
			identifier = new Identifier();
			title = new Title();
			book = new Book();
			accrual = new Accrual();
			rights = new Rights();
			source = new Source();
			price = new Price();
			support = new Support();
			contributor = new Contributor();
			
			// 상품ID
			identifier = new Identifier();
			
			// Response VO를 만들기위한 생성자
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();
			
			identifier.setType("channel");
			identifier.setText(categoryEpubDTO.getProdId());
			title.setText(categoryEpubDTO.getProdNm());
			
			menu = new Menu();
			menu.setId(categoryEpubDTO.getTopMenuId());
			menu.setName(categoryEpubDTO.getTopMenuNm());
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(categoryEpubDTO.getMenuId());
			menu.setName(categoryEpubDTO.getMenuNm());
			//menu.setType("");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(categoryEpubDTO.getMetaClsfCd());
			menu.setName("ebook/series");
			menu.setType("metaClass");
			menuList.add(menu);

			if ( "CT20".equals(categoryEpubDTO.getMetaClsfCd()) )
					book.setType("serial");
			book.setTotalPages(categoryEpubDTO.getBookPageCnt());
			if ( "CT20".equals(categoryEpubDTO.getMetaClsfCd()) )
				if ( "Y".equals(categoryEpubDTO.getCaptionYn()) )
					book.setStatus("completed");
				else
					book.setStatus("continue");
			log.debug("setStatus");

			if ( Integer.parseInt(categoryEpubDTO.getStrmEpsdCnt()) > 0 )
				support.setText("play");
			support.setText(StringUtil.nvl(support.getText(), "") + "|");
			if ( Integer.parseInt(categoryEpubDTO.getEpsdCnt()) > 0 )
				support.setText(support.getText() + "store");
			log.debug("setText");
			supportList.add(support);
			book.setSupportList(supportList);
			product.setBook(book);
			log.debug("setBook");
			contributor.setName(categoryEpubDTO.getArtist1Nm());
			contributor.setCompany(categoryEpubDTO.getChnlCompNm());
			Date date = new Date();
			date.setText(categoryEpubDTO.getIssueDay());
			contributor.setDate(date);
			log.debug("setCompany");
			

			accrual.setVoterCount(categoryEpubDTO.getPrchsCnt());
			accrual.setDownloadCount(categoryEpubDTO.getDwldCnt());
			accrual.setScore(3.3);
			log.debug("accrual");
			/*
			 * Rights grade
			 */
			rights.setGrade(categoryEpubDTO.getProdGrdCd());
			
			source.setMediaType("image/jpeg");
			source.setSize(categoryEpubDTO.getFileSize());
			source.setType("thumbNail");
			source.setUrl(categoryEpubDTO.getFilePath());
			sourceList.add(source);
			log.debug("sourceList");
			/*
			 * Price text
			 */
			price.setText(Integer.parseInt(categoryEpubDTO.getProdAmt()));
			price.setFixedPrice(categoryEpubDTO.getProdNetAmt());
			log.debug("price");
			//product = new Product();
			product.setIdentifier(identifier);
			product.setTitle(title);
			//support.setText(categoryEpubDTO.getDrmYn() + "|" + categoryEpubDTO.getPartParentClsfCd());
			//supportList.add(support);
			//product.setSupportList(supportList);
			product.setMenuList(menuList);

			product.setAccrual(accrual);
			product.setRights(rights);
			product.setProductExplain(categoryEpubDTO.getProdBaseDesc());
			product.setSourceList(sourceList);
			product.setPrice(price);
			product.setContributor(contributor);

			listVO.add(product);

		}
		responseVO = new CategoryEpubRes();
		responseVO.setFeatureProductList(listVO);
		return responseVO;
	}

}
