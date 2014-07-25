/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.epub.service;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.display.epub.vo.EpubDetail;

/**
 * EbookEpisodeMapper 인터페이스
 * <pre>
 * Created on 2014. 07. 24. by 서대영, SK 플래닛
 * </pre>
 */
public interface EpubMappingService {

	/**
	 * 미리보기 정보를 맵핑한다.
	 */
	public Preview mapPreview(EpubDetail mapperVO);

}
