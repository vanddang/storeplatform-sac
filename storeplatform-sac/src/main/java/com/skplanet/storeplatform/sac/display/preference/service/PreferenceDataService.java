/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.preference.service;

import java.util.List;

import com.skplanet.storeplatform.external.client.isf.vo.IsfV2OfferObjectsEcRes;
import com.skplanet.storeplatform.sac.client.display.vo.preference.ListProductReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * <p>
 * PreferenceDataService
 * </p>
 * Updated on : 2014. 11. 28 Updated by : 서대영, SK 플래닛.
 */
public interface PreferenceDataService {

	List<IsfV2OfferObjectsEcRes> getProductList(ListProductReq req);

	List<Product> getProductList(List<IsfV2OfferObjectsEcRes> offerList, SacRequestHeader header, int count);

}
