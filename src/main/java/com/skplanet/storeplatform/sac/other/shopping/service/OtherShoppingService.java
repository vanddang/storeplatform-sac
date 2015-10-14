package com.skplanet.storeplatform.sac.other.shopping.service;

import com.skplanet.storeplatform.sac.client.other.vo.shopping.AllianceUserCheckReq;
import com.skplanet.storeplatform.sac.client.other.vo.shopping.AllianceUserCheckRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 쇼핑 충전권 연동 Service
 *
 * Updated on : 2015. 10. 07. Updated by : 심대진, 다모아 솔루션.
 */
public interface OtherShoppingService {

    public AllianceUserCheckRes allianceUserCheck(SacRequestHeader sacHeader, AllianceUserCheckReq req);

}