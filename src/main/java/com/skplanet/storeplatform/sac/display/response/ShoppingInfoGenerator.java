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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 쇼핑 상품 전용 정보 Generator
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
public interface ShoppingInfoGenerator {
	/**
	 * <pre>
	 * 쇼핑 상품 전용 Contributor 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Contributor
	 */
	public Contributor generateContributor(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 쇼핑 상품 전용 SalesOption 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return SalesOption
	 */
	public SalesOption generateSalesOption(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 쇼핑 상품 전용 Rights 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Rights
	 */
	public Rights generateRights(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 쇼핑 상품 전용 Accrual 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Accrual
	 */
	public Accrual generateAccrual(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 쇼핑 상품 전용 Price 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Price
	 */
	public Price generatePrice(MetaInfo metaInfo);

}
