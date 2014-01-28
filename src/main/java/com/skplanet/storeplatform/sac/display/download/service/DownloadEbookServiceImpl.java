/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * DownloadEbook Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
@Transactional
public class DownloadEbookServiceImpl implements DownloadEbookService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	@Override
	public DownloadEbookRes getDownloadEbookInfo(SacRequestHeader requestHeader, DownloadEbookReq downloadEbookReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVodList Service started!!");
		this.logger.debug("----------------------------------------------------------------");

		DownloadEbookRes ebookRes = null;

		String idType = downloadEbookReq.getIdType();
		String productId = downloadEbookReq.getProductId();
		String deviceKey = downloadEbookReq.getDeviceKey();
		String userKey = downloadEbookReq.getUserKey();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(idType) || StringUtils.isEmpty(productId) || StringUtils.isEmpty(deviceKey)
				|| StringUtils.isEmpty(userKey)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("필수 파라미터 부족");
			this.logger.debug("----------------------------------------------------------------");

			throw new StorePlatformException("ERROR_0001", "1", "2", "3");
		}
		// ID유형 유효값 체크
		if (!"channel".equals(idType) && !"episode".equals(idType)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("유효하지않은 ID유형");
			this.logger.debug("----------------------------------------------------------------");

			throw new StorePlatformException("ERROR_0001", "1", "2", "3");
		}

		// 헤더정보 세팅
		downloadEbookReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		downloadEbookReq.setLangCd(requestHeader.getTenantHeader().getLangCd());
		downloadEbookReq.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		downloadEbookReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

		MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadEbookInfo",
				downloadEbookReq);

		this.logger.info("----> " + metaInfo);

		return null;
	}
}
