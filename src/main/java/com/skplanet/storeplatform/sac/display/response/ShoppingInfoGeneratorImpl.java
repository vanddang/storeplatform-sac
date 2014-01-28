/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.response;

import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 쇼핑 상품 전용 정보 Generator 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Component
public class ShoppingInfoGeneratorImpl implements ShoppingInfoGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator#generateContributor(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		Identifier identifier = new Identifier();
		identifier.setType(DisplayConstants.DP_BRAND_IDENTIFIER_CD);
		identifier.setText(metaInfo.getBrandId());
		contributor.setIdentifier(identifier);
		contributor.setName(metaInfo.getBrandName()); // 브랜드명
		return contributor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator#generateSalesOption(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public SalesOption generateSalesOption(MetaInfo metaInfo) {
		SalesOption salesOption = new SalesOption();
		salesOption.setType(metaInfo.getProdCaseCd());
		return salesOption;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator#generateRights(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Rights generateRights(MetaInfo metaInfo) {
		Rights rights = new Rights();
		Date date = new Date();
		rights.setGrade(metaInfo.getProdGrdCd());
		date.setType(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_NM);
		date.setText(metaInfo.getApplyStartDt() + "/" + metaInfo.getApplyEndDt());
		rights.setDate(date);
		return rights;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator#generateAccrual(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		accrual.setDownloadCount(metaInfo.getPrchsCnt());
		return accrual;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator#generatePrice(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Price generatePrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setFixedPrice(metaInfo.getProdNetAmt());
		price.setDiscountRate(metaInfo.getDcRate());
		price.setText(metaInfo.getProdAmt());
		return price;
	}

}
