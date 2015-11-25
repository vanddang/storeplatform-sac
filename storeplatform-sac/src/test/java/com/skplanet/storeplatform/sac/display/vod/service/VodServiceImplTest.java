package com.skplanet.storeplatform.sac.display.vod.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.vod.vo.VodDetail;

/**
 * VOD Service
 *
 * Updated on : 2014. 01. 09
 * Updated by : 임근대, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-test.xml"})
@TransactionConfiguration
@Transactional
public class VodServiceImplTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VodService vodService;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	@Test(timeout=3000)
	public void searchVod() {
		
		VodDetailReq req = new VodDetailReq();
		
		String orderedBy = "nonPayment";
		req.setDeviceModel("SHW-M110S");
		req.setChannelId("H900537521");
		req.setLangCd("ko");
		req.setTenantId("S01");
		req.setOrderedBy(orderedBy);
		req.setOffset(1);
		req.setCount(20);
		req.setDeviceKey("DE201402201711283140002222");
		req.setUserKey("US201402201711282940003170");
		VodDetailRes res = vodService.searchVod(req, false);
		this.logger.debug("res={}", res);
	}

    @Test(timeout=3000)
    public void searchVod_dao_selectVodChannel() {
    	String orderedBy = "nonPayment";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("imgCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
        param.put("deviceModel", "SHW-M110S");
        param.put("channelId", "H900537521");
        param.put("langCd", "ko");
        param.put("tenantId", "S01");
        param.put("orderedBy", orderedBy);
        param.put("offset", 1);
        param.put("count", 20);
		VodDetail vodDetail = this.commonDAO.queryForObject("VodDetail.selectVodChannel", param, VodDetail.class);
		this.logger.debug("vodDetail={}", vodDetail);
    }
    
    @Test(timeout=3000)
    public void searchVod_dao_selectVodSeries_nonPayment() {
    	String orderedBy = "nonPayment";
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("imgCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
    	param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
    	param.put("deviceModel", "SHW-M110S");
    	param.put("channelId", "H900537521");
    	param.put("langCd", "ko");
    	param.put("tenantId", "S01");
    	param.put("orderedBy", orderedBy);
    	param.put("offset", 1);
    	param.put("count", 20);
    	
    	List<String> paymentProdIdList = new ArrayList<String>();
    	paymentProdIdList.add("H900544460");
    	paymentProdIdList.add("H900558298");
    	param.put("paymentProdIdList", paymentProdIdList);
    	
    	List<VodDetail> vodDetail = this.commonDAO.queryForList("VodDetail.selectVodSeries", param, VodDetail.class);
    	this.logger.debug("vodDetail={}", vodDetail);
    }
	/*
    @Test
    public void searchVod_dao_screenshotList() {
    	ProductImage param = new ProductImage();
    	param.setProdId("H090107970");
    	param.setLangCd("ko");
    	List<ProductImage> screenshotList = this.commonDAO.queryForList("VodDetail.selectSourceList", param, ProductImage.class);
    	this.logger.debug("screenshotList={}", screenshotList);
    }

    @Test
    public void searchVod() {
    	VodDetailReq req = new VodDetailReq();
    	req.setChannelId("H090107970");
    	req.setTenantId("S01");
    	req.setLangCd("ko");
    	req.setDeviceModel("SHW-M110S");
    	req.setImgCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
    	req.setOrderedBy("recent");
        VodDetailRes res = this.vodService.searchVod(req);
        this.logger.debug("VodDetailRes={}", res);
    }
	 */

    @Test
    public void searchVod_dao_selectProdRshp() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("channelId", "H900537521");
        List<String> episodeIdList = this.commonDAO.queryForList("VodDetail.selectProdRshp", param, String.class);
        this.logger.debug("episodeIdList={}", episodeIdList);
    }
}
