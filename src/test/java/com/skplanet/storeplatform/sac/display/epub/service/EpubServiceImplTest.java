package com.skplanet.storeplatform.sac.display.epub.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubChannelReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubChannelRes;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.epub.vo.EpubDetail;
import com.skplanet.storeplatform.sac.display.epub.vo.MgzinSubscription;

/**
 * VOD Service
 *
 * Updated on : 2014. 01. 09
 * Updated by : 임근대, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class EpubServiceImplTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EpubService epubService;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Test(timeout=10000)
	public void searchEpubSeries() {
		
		EpubSeriesReq req = new EpubSeriesReq();
		String orderedBy = "nonPayment";
		req.setDeviceModel("SHW-M110S");
		req.setChannelId("H001254069");
		req.setLangCd("ko");
		req.setTenantId("S01");
		req.setOrderedBy(orderedBy);
		req.setOffset(1);
		req.setCount(20);
		req.setDeviceKey("DE201402201711283140002222");
		req.setUserKey("US201402201711282940003170");
		EpubSeriesRes res = epubService.searchEpubSeries(req);
		this.logger.debug("res={}", res);
	}

	
    @Test
    public void searchEpub_dao_selectEpubChannel() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", "S01");
        param.put("channelId", "H900063921");
        param.put("langCd", "ko");
        param.put("deviceModel", "SHV-E110S");
        param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
        
		EpubDetail result = this.commonDAO.queryForObject("EpubDetail.selectEpubChannel", param, EpubDetail.class);
		this.logger.debug("result={}", result);
    }

    @Test
    public void searchEpub_dao_selectEpubSeries_recent() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", "S01");
        param.put("channelId", "H900063921");
        param.put("langCd", "ko");
        param.put("deviceModel", "SHV-E110S");
        param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
        
        //param.put("bookTypeCd", "");
        param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
        param.put("orderedBy", DisplayConstants.DP_ORDEREDBY_TYPE_RECENT);
        param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        param.put("offset", 1);
        param.put("count", 20);
    	
    	EpubDetail result = this.commonDAO.queryForObject("EpubDetail.selectEpubSeries", param, EpubDetail.class);
    	this.logger.debug("result={}", result);
    }
    
    @Test
    public void searchEpub_dao_selectEpubSeries_nonPayment() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("tenantId", "S01");
    	param.put("channelId", "H001431104");
    	param.put("langCd", "ko");
    	param.put("deviceModel", "SHV-E110S");
    	param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
    	param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
    	//param.put("bookTypeCd", "");
    	param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
    	param.put("orderedBy", "nonPayment");
    	param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
    	param.put("offset", 1);
    	param.put("count", 20);
    	
    	List<String> list = new ArrayList<String>();
    	list.add("H000401828");
    	list.add("H000401834");
    	list.add("H000401840");
    	param.put("paymentProdIdList", list);
    	
    	
    	List<EpubDetail> result = this.commonDAO.queryForList("EpubDetail.selectEpubSeries", param, EpubDetail.class);
    	this.logger.debug("result={}", result);
    }
    
    @Test
    public void searchEpub_dao_selectEpubSeries_nonPayment2() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("tenantId", "S01");
    	param.put("channelId", "H001431104");
    	param.put("langCd", "ko");
    	param.put("deviceModel", "SHV-E110S");
    	param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
    	param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
    	//param.put("bookTypeCd", "");
    	param.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
    	param.put("orderedBy", "nonPayment");
    	param.put("imgCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
    	param.put("offset", 1);
    	param.put("count", 20);

    	/*
    	List<String> list = new ArrayList<String>();
    	list.add("H000401828");
    	list.add("H000401834");
    	list.add("H000401840");
    	param.put("paymentProdIdList", list);
    	*/
    	
    	List<EpubDetail> result = this.commonDAO.queryForList("EpubDetail.selectEpubSeries", param, EpubDetail.class);
    	this.logger.debug("result={}", result);
    }

    //@Test
    public void searchEpub_dao_selectEpubSubscription() {
    	EpubChannelReq req = new EpubChannelReq();
    	req.setChannelId("H900063921");
    	req.setLangCd("ko");
    	req.setTenantId("S01");
    	req.setDeviceModel("SHV-E110S");
    	req.setImgCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
    	req.setOrderedBy("recent");
    	MgzinSubscription result = this.commonDAO.queryForObject("EpubDetail.selectEpubSubscription", req, MgzinSubscription.class);
    	this.logger.debug("result={}", result);
    }


    //@Test
    public void searchEpubChannel() {
    	EpubChannelReq req = new EpubChannelReq();
    	req.setChannelId("H000044572");
    	req.setLangCd("ko");
    	req.setTenantId("S01");
    	req.setDeviceModel("SHV-E110S");
    	req.setImgCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
    	req.setOrderedBy("recent");

    	EpubChannelRes result = this.epubService.searchEpubChannel(req);

		this.logger.debug("result={}", result);
    }

}
