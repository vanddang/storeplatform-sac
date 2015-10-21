package com.skplanet.storeplatform.sac.other.shopping.service;

import com.skplanet.storeplatform.external.client.shopping.sci.OtherShoppingSCI;
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
    private OtherShoppingSCI otherShoppingSCI;

    @Override
    public AllianceUserCheckRes allianceUserCheck(SacRequestHeader sacHeader, AllianceUserCheckReq req) {

        /**
         * 쇼핑 CMS EC 연동.
         */
        AllianceUserCheckEcReq ecReq = new AllianceUserCheckEcReq();
        ecReq.setBrandId(req.getBrandId());
        ecReq.setCmsBrandId(req.getCmsBrandId());
        ecReq.setBrandSellerId(req.getBrandSellerId());
        ecReq.setDeviceId(req.getDeviceId());
        ecReq.setChargeId(req.getChargeId());
        LOGGER.info("EC Req = {}", ecReq);
        AllianceUserCheckEcRes ecRes =  otherShoppingSCI.allianceUserCheck(ecReq);
        LOGGER.info("EC Res = {}", ecRes);

        /**
         * Response Setting.
         */
        AllianceUserCheckRes response = new AllianceUserCheckRes();
        response.setBrandId(ecReq.getBrandId());
        response.setAllianceUserYn(ecRes.getAllianceUserYn()); // EC 연동 데이타.
        response.setChargeId(ecRes.getChargeId()); // EC 연동 데이타.
        response.setChargeNm(ecRes.getChargeNm()); // EC 연동 데이타.
        response.setCmsBrandId(ecReq.getCmsBrandId());

        return response;
    }
}
