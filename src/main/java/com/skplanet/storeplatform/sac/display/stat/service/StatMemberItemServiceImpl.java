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

import com.skplanet.storeplatform.sac.client.display.vo.card.CardDetailSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.card.service.CardDetailService;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetail;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetailParam;
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
	private ProductListService productListService;
	
	@Autowired
	private StatMemberDataService dataServcie;
	
	@Override
	public Object findItem(StatLike like, SacRequestHeader header) {
		String statsClsf = like.getStatsClsf();
		
		Object item;
		if (CLSF_CARD.equals(statsClsf)) {
			item = findCard(like, header);
		} else if (CLSF_PROD.equals(statsClsf)) {
			item = findProd(like, header);
		} else {
			item = new Object();
		}
		
		return item;
	}
	
	private CardDetailSacRes findCard(StatLike like, SacRequestHeader header) {
		String tenantId = header.getTenantHeader().getTenantId();
		String cardId = like.getStatsKey();
		
		CardDetailParam param = new CardDetailParam();
		param.setTenantId(tenantId);
		param.setCardId(cardId);
		
		CardDetail cardDetail = cardDetailService.searchCardDetail(param);
		CardDetailSacRes res = new CardDetailSacRes();
		Card card = cardDetailService.makeCard(cardDetail);

		BeanUtils.copyProperties(card, res);
		return res;
	}
	
	private Object findProd(StatLike like, SacRequestHeader header) {
		String prodId= like.getStatsKey();
		
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
