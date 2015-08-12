package com.skplanet.storeplatform.sac.other.csp.service;

import com.skplanet.storeplatform.sac.client.other.vo.csp.CspTingPointReq;
import com.skplanet.storeplatform.sac.client.other.vo.csp.CspTingPointRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CSP 연동 Service
 *
 * Updated on : 2015. 8. 12. Updated by : 심대진, 다모아 솔루션.
 */
public interface CspService {

    public CspTingPointRes getTingPoint(SacRequestHeader sacHeader, CspTingPointReq req);

}
