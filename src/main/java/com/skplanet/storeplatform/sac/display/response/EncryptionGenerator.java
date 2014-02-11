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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 상품 정보 조회 (for download) 전용 Generator
 * 
 * Updated on : 2014. 02. 11. Updated by : 이태희, 부르칸.
 */
public interface EncryptionGenerator {
	/**
	 * <pre>
	 * 상품 정보 조회 (for download) 전용 Encryption Contents 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Menu>
	 */
	public EncryptionContents generateEncryptionContents(MetaInfo metaInfo);
}
