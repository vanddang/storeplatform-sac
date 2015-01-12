package com.skplanet.storeplatform.sac.display.download.service;

import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
public class DownloadCommonServiceImpl implements DownloadCommonService {

	@Override
	public void validateVisitPathNm(MetaInfo metaInfo, String visitPathNm, String prodId) {

		if (StringUtils.isBlank(visitPathNm)) return;

		String[] visitPathNmArr = visitPathNm .split("\\.");

		if ( visitPathNmArr.length != 2 ) return;

		if ( StringUtils.equals(visitPathNmArr[1], prodId) )
			metaInfo.setVisitPathNm(visitPathNmArr[0]);

	}

}
