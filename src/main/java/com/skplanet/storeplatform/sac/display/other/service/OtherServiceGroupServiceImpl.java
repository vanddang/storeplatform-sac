/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.other.vo.OtherServiceGroup;

/**
 * 
 * 
 * Updated on : 2014. 1. 28. Updated by : 이승훈, 엔텔스.
 */
@Service
public class OtherServiceGroupServiceImpl implements OtherServiceGroupService {
	private transient Logger logger = LoggerFactory.getLogger(OtherServiceGroupServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 기타 카테고리 상품서비스군 상품 조회.
	 * </pre>
	 * 
	 * @param OtherServiceGroupSacReq
	 * @return OtherServiceGroupSacRes
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 */
	@Override
	public OtherServiceGroupSacRes searchServiceGroupList(OtherServiceGroupSacReq req, SacRequestHeader header) {

		OtherServiceGroupSacRes appRes = new OtherServiceGroupSacRes();
		CommonResponse commonRes = new CommonResponse();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(req.getList())) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("필수 파라미터 부족");
			this.logger.debug("----------------------------------------------------------------");

			appRes.setCommonResponse(commonRes);
			return appRes;
		}

		// prodId 리스트로 변경
		List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
		if (prodIdList.size() > 50) {
			// TODO osm1021 에러 처리 추가 필요
			this.logger.error("## prod id over 50 : {}" + prodIdList.size());
		}
		req.setProdIdList(prodIdList);

		// 기타 카테고리 상품서비스군 상품 조회
		List<OtherServiceGroup> appList = this.commonDAO.queryForList("OtherServiceGroup.selectOtherServiceGroupList",
				req, OtherServiceGroup.class);
		if (!appList.isEmpty()) {
			OtherServiceGroup otherServiceGroup = null;

			Identifier identifier = null;
			Product product = null;

			List<Menu> menuList;
			List<Product> productList = new ArrayList<Product>();
			Menu menu;

			for (int i = 0; i < appList.size(); i++) {
				otherServiceGroup = appList.get(i);

				product = new Product(); // 결과물
				identifier = new Identifier();

				// Response VO를 만들기위한 생성자
				menuList = new ArrayList<Menu>(); // 메뉴 리스트

				if ("PD002501".equals(otherServiceGroup.getContentsTypeCd()))
					identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				else if ("PD002502".equals(otherServiceGroup.getContentsTypeCd()))
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(otherServiceGroup.getProdId());
				product.setIdentifier(identifier);

				// 메뉴 정보
				menu = new Menu(); // 메뉴
				menu.setId(otherServiceGroup.getTopMenuId());
				menu.setName(otherServiceGroup.getTopMenuNm());
				menu.setType("topClass");
				menuList.add(menu);
				product.setMenuList(menuList);

				// 데이터 매핑
				productList.add(i, product);
			}

			commonRes.setTotalCount(otherServiceGroup.getTotalCount());

			appRes.setCommonResponse(commonRes);
			appRes.setProductList(productList);

		} else {
			// 조회 결과 없음
			appRes.setCommonResponse(commonRes);
		}

		return appRes;
	}
}
