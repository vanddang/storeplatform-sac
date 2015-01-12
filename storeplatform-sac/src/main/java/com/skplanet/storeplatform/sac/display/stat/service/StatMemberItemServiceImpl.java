/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.PanelCardInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.CardInfo;
import com.skplanet.storeplatform.sac.display.card.service.CardDetailService;
import com.skplanet.storeplatform.sac.display.card.util.CardDynamicInfoProcessor;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetail;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;
import com.skplanet.storeplatform.sac.display.feature.product.service.ProductListService;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.stat.vo.StatLike;

/**
 * <p>
 * MemberSegmentServiceImpl
 * </p>
 * Updated on : 2014. 11. 05 Updated by : 서대영, SK 플래닛.
 */
@Service
public class StatMemberItemServiceImpl implements StatMemberItemService {

	private static final String CLSF_CARD = "DP01210001";
	private static final String CLSF_PROD = "DP01210002";

	@Autowired
	private CardDetailService cardDetailService;
	
    @Autowired
    private PanelCardInfoManager panelCardInfoManager;

	@Autowired
	private ProductListService productListService;

	@Autowired
	private StatMemberDataService dataServcie;

	@Override
	public Object findItem(StatLike like, SacRequestHeader header, PreferredCategoryInfo preferredCategoryInfo) {
		String statsClsf = like.getStatsClsf();
		String statsKey = like.getStatsKey();
		String userKey = like.getUserKey();

		Object item;
		if (CLSF_CARD.equals(statsClsf)) {
			item = findCard(statsKey, userKey, header, preferredCategoryInfo);
		} else if (CLSF_PROD.equals(statsClsf)) {
			item = findProd(statsKey, header);
		} else {
			item = new Object();
		}

		return item;
	}

	@Override
	public Card findCard(String cardId, String userKey, SacRequestHeader header, PreferredCategoryInfo preferredCategoryInfo) {
		String tenantId = header.getTenantHeader().getTenantId();
		String langCd = header.getTenantHeader().getLangCd();
		
		// 정적 처리 (Cache)
        CardInfo cardInfo = panelCardInfoManager.getCardInfo(tenantId, cardId);
        CardDetail cardDetail = new CardDetail();
        BeanUtils.copyProperties(cardInfo, cardDetail);
        Card card = cardDetailService.makeCard(cardDetail, preferredCategoryInfo, langCd);
        
        // 동적 처리
        CardDynamicInfoProcessor processor = new CardDynamicInfoProcessor(tenantId, cardDetailService);
        processor.addCard(card);
        processor.execute(userKey);
        return card;
	}

	@Override
	public Product findProd(String prodId, SacRequestHeader header) {
		ListProduct listProd;
		/* 쇼핑 상품일 경우, prodId만 있어 메타 조회 가능하나,
		   일반 상품일 경우 TB_DP_PROD 테이블을 조회하여 TopMeuId와 SvcGrpCd를 추가로 획득해야함 */
		if (prodId.startsWith("CL")) {
			listProd = new ListProduct();
			listProd.setProdId(prodId);
		} else {
			listProd = dataServcie.selectProdcut(prodId);
		}
		Product product = productListService.getProduct(header, listProd);
		return product;
	}

}
