package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface RelatedProductService {
    public RelatedProductSacRes searchRelatedProductList(RelatedProductSacReq requestVO, SacRequestHeader requestHeader);
}
