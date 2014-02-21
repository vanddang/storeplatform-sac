package com.skplanet.storeplatform.sac.display.epub.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubChannelReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubChannelRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.epub.vo.EpubDetail;
import com.skplanet.storeplatform.sac.display.epub.vo.MgzinSubscription;
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

    @Test
    public void searchEpub_dao_selectEpubChannel() {
    	EpubChannelReq req = new EpubChannelReq();
    	req.setChannelId("H900063921");
        req.setDeviceKey("DE201402201711283140002222");
        req.setUserKey("US201402201711282940003170");
    	req.setLangCd("ko");
    	req.setTenantId("S01");
    	req.setDeviceModel("SHV-E110S");
    	req.setImgCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
    	req.setOrderedBy("recent");
		EpubDetail result = this.commonDAO.queryForObject("EpubDetail.selectEpubChannel", req, EpubDetail.class);
		this.logger.debug("result={}", result);
    }

    @Test
    public void searchEpub_dao_selectEpubSeries() {
    	EpubChannelReq req = new EpubChannelReq();
    	req.setChannelId("H900009069");
    	req.setLangCd("ko");
    	req.setTenantId("S01");
    	req.setDeviceModel("IM-S330");
    	req.setImgCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
    	req.setOrderedBy("recent");
    	EpubDetail result = this.commonDAO.queryForObject("EpubDetail.selectEpubChannel", req, EpubDetail.class);
    	this.logger.debug("result={}", result);
    }

    @Test
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


    @Test
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
