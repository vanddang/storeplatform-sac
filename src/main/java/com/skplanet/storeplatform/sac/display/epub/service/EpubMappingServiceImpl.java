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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.epub.vo.EpubDetail;

/**
 * EbookEpisodeMapper 클래스
 * <pre>
 * Created on 2014. 07. 24. by 서대영, SK 플래닛 : Mapping 로직을 이 곳에 계속해서 추가했으면 함.
 * </pre>
 */
@Service
public class EpubMappingServiceImpl implements EpubMappingService {

    @Autowired
    private DisplayCommonService commonService;

	@Override
	public Preview mapPreview(EpubDetail mapperVO) {
		Preview preview = new Preview();

		if (StringUtils.isNotBlank(mapperVO.getSamplUrl())) {
			List<Source> sourceList = new ArrayList<Source>();

            Source source = new Source();
			source.setType(DisplayConstants.DP_EPUB_PREVIEW);
			source.setUrl(this.commonService.makeEpubPreviewUrl(mapperVO.getSamplUrl()));
			source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getSamplUrl()));
			sourceList.add(source);

			preview.setSourceList(sourceList);
		}

		return preview;
	}

}
