package com.skplanet.storeplatform.sac.other.shopping.service;

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.AllianceUserCheckEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.AllianceUserCheckEcRes;
import com.skplanet.storeplatform.sac.client.other.vo.shopping.AllianceUserCheckReq;
import com.skplanet.storeplatform.sac.client.other.vo.shopping.AllianceUserCheckRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 쇼핑 충전권 연동 ServiceImpl
 *
 * Updated on : 2015. 10. 07. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class OtherShoppingServiceImpl implements OtherShoppingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtherShoppingServiceImpl.class);

    @Autowired
    private ShoppingSCI shoppingSCI;

    @Override
    public AllianceUserCheckRes allianceUserCheck(SacRequestHeader sacHeader, AllianceUserCheckReq req) {

        AllianceUserCheckRes response = new AllianceUserCheckRes();

        LOGGER.info("!!!!!!!!!!!!!쇼핑 충전권 연동한다....");

        AllianceUserCheckEcReq ecReq = new AllianceUserCheckEcReq();
        LOGGER.info("EC Req = {}", ecReq);
        ecReq.setBrandId(req.getBrandId());
        ecReq.setCmsBrandId(req.getCmsBrandId());
        ecReq.setBrandSellerId(req.getBrandSellerId());
        ecReq.setDeviceId(req.getDeviceId());
        ecReq.setChargeId(req.getChargeId());
        AllianceUserCheckEcRes ecRes =  shoppingSCI.allianceUserCheck(ecReq);
        LOGGER.info("EC Res = {}", ecRes);

        return response;
    }
}
