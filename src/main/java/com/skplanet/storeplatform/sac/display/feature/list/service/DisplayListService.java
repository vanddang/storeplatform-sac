package com.skplanet.storeplatform.sac.display.feature.list.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.list.DisplayListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.list.DisplayListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;


public interface DisplayListService {
	DisplayListSacRes searchList(DisplayListSacReq requestVO, SacRequestHeader header);
}
