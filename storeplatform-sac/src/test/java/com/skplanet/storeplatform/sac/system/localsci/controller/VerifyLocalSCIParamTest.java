package com.skplanet.storeplatform.sac.system.localsci.controller;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.system.localsci.controller.VerifyLocalSCIReq;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Updated on : 2016-01-06. Updated by : 양해엽, SK Planet.
 */
@ActiveProfiles(value = "local")
public class VerifyLocalSCIParamTest {

    @Test
    public void test_model_mapper() throws IOException {
        VerifyLocalSCIReq verifyLocalSCIReq = makeReq();

        ModelMapper modelMapper = new ModelMapper();
        BannerInfoSacReq bannerInfoSacReq = modelMapper.map(verifyLocalSCIReq.getInvokeParam(), BannerInfoSacReq.class);

        Map<String, Object> param = (Map<String, Object>) verifyLocalSCIReq.getInvokeParam();
        assertEquals(param.get("tenantId"), bannerInfoSacReq.getTenantId());
        assertEquals(param.get("bnrMenuId"), bannerInfoSacReq.getBnrMenuId());
        assertEquals(param.get("bnrExpoMenuId"), bannerInfoSacReq.getBnrExpoMenuId());
        assertEquals(param.get("imgSizeCd"), bannerInfoSacReq.getImgSizeCd());
        assertEquals(param.get("count"), bannerInfoSacReq.getCount());
    }

    private VerifyLocalSCIReq makeReq() {
        VerifyLocalSCIReq verifyLocalSCIReq = new VerifyLocalSCIReq();
        verifyLocalSCIReq.setLocalSCIName("com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.BannerInfoSCI");
        verifyLocalSCIReq.setInvokeMethod("getBannerInfoList");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", "S01");
        param.put("bnrMenuId", "DP010929");
        param.put("bnrExpoMenuId", "DP011100");
        param.put("imgSizeCd", "DP011033");
        param.put("count", 3);

        List<String> prodIds = new ArrayList<String>();
        prodIds.add("prodId1");
        prodIds.add("prodId2");

        param.put("prodIds", prodIds);

        verifyLocalSCIReq.setInvokeParam(param);

        return verifyLocalSCIReq;
    }

}
