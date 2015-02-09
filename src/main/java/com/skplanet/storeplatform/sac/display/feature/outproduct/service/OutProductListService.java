package com.skplanet.storeplatform.sac.display.feature.outproduct.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.outproduct.list.OutProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.outproduct.list.OutProductListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface OutProductListService {
	public OutProductListSacRes searchOutProductList(OutProductListSacReq requestVO, SacRequestHeader header);
}
