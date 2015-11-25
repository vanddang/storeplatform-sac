/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 특정 상품 music 조회 Service
 * 
 * Updated on : 2014. 2. 4. Updated by : 이승훈, 엔텔스.
 */
public interface CategorySpecificMusicService {
	/**
	 * <pre>
	 * 특정 상품 music 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return CategorySpecificSacRes
	 */
	CategorySpecificSacRes getSpecificMusicList(CategorySpecificSacReq req, SacRequestHeader header);

    /**
     * Music에 관련 링벨 상품을 매핑한다.
     * @param tenantId
     * @param chnlId
     * @param music 매핑 대상
     */
    void mapgRingbell(String tenantId, String chnlId, Music music);
}
