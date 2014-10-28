package com.skplanet.storeplatform.sac.display.download.service;

import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * for D/L 관련 공통 Service Biz 추가.
 *
 * Updated on : 2014. 10. 28.
 * Updated by : 양해엽, SK Planet
 */
public interface DownloadCommonService {

	void validateVisitPathNm(MetaInfo metaInfo, final String visitPathNm, final String prodId);

}
