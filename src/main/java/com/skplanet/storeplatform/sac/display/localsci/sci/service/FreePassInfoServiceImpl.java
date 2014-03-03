package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfoSacReq;

/**
 * 
 * 정액제 상품 DRM 정보 조회.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
@Service
public class FreePassInfoServiceImpl implements FreePassInfoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 정액제 상품 DRM 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return FreepassDrmInfo 정액제 상품 DRM 정보
	 */
	@Override
	public FreePassInfo searchFreePassDrmInfo(FreePassInfoSacReq req) {
		FreePassInfo freepassDrmInfo = this.commonDAO.queryForObject("FreePassInfo.searchFreePassDrmInfo", req,
				FreePassInfo.class);
		return freepassDrmInfo;
	}

}
