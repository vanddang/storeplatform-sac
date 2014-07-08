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

import java.util.*;

import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
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
	 * @param req
	 * @param header
	 *            header
	 * @return OtherServiceGroupSacRes
	 */
	@Override
	public OtherServiceGroupSacRes searchServiceGroupList(OtherServiceGroupSacReq req, SacRequestHeader header) {

		OtherServiceGroupSacRes appRes = new OtherServiceGroupSacRes();
		CommonResponse commonRes = new CommonResponse();

		// prodId 리스트로 변경
		List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
		if (prodIdList.size() > 50) {
			// TODO 에러 처리 추가 필요
			this.logger.error("## prod id over 50 : {}" + prodIdList.size());
		}
		req.setProdIdList(prodIdList);

		// 기타 카테고리 상품서비스군 상품 조회
		List<OtherServiceGroup> appList = this.commonDAO.queryForList("OtherServiceGroup.selectOtherServiceGroupList",
				req, OtherServiceGroup.class);
		if (!appList.isEmpty()) {
			OtherServiceGroup otherServiceGroup = null;

			// Identifier 설정
			List<Identifier> identifierList;
			Identifier identifier = null;
			Product product = null;
			// right 설정
			Rights rights = null;

			List<Menu> menuList;
			List<Product> productList = new ArrayList<Product>();
			Menu menu;

			for (int i = 0; i < appList.size(); i++) {
				otherServiceGroup = appList.get(i);

				product = new Product(); // 결과물
				identifier = new Identifier();
				identifierList = new ArrayList<Identifier>();

				// Response VO를 만들기위한 생성자
				menuList = new ArrayList<Menu>(); // 메뉴 리스트

				if ("PD002501".equals(otherServiceGroup.getContentsTypeCd()))
					identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				else if ("PD002502".equals(otherServiceGroup.getContentsTypeCd()))
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(otherServiceGroup.getProdId());
				identifierList.add(identifier);
				product.setIdentifierList(identifierList);

                // chnlId로 요청시 epsdId목록을 응답에 추가
                if(otherServiceGroup.getContentsTypeCd().equals(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD)) {
                    List<String> partProdIds = selectPartProdId(otherServiceGroup.getProdId());
                    for (String v : partProdIds) {
                        identifierList.add(new Identifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, v));
                    }
                }

				// 메뉴 정보
				menu = new Menu(); // TOP메뉴
				menu.setId(otherServiceGroup.getTopMenuId());
				menu.setName(otherServiceGroup.getTopMenuNm());
				menu.setType("topClass");
				menuList.add(menu);
				// menu = new Menu(); // 메뉴
				// menu.setId(otherServiceGroup.getMenuId());
				// menu.setName(otherServiceGroup.getMenuNm());
				// menuList.add(menu);
				menu = new Menu(); // SVC_GRP_CD
				menu.setId(otherServiceGroup.getSvcGrpCd());
				menu.setType("svcGrpCd");
				menuList.add(menu);
				menu = new Menu(); // META_CLSF_CD
				menu.setId(otherServiceGroup.getMetaClsfCd());
				menu.setType("metaClass");
				menuList.add(menu);

				product.setMenuList(menuList);

				// right 설정
				rights = new Rights();
				rights.setGrade(otherServiceGroup.getProdGrdCd());

				product.setRights(rights);

				// 상품 유/무료 구분
				product.setProdChrgYn(otherServiceGroup.getProdChrgYn());

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

    private List<String> selectPartProdId(String chnlId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("rshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
        req.put("chnlId", chnlId);
        return commonDAO.queryForList("OtherServiceGroup.selectPartProdId", req, String.class);
    }
}
