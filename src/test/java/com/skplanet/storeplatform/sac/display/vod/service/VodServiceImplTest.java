package com.skplanet.storeplatform.sac.display.vod.service;

import java.util.List;

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
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.ProductImage;
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

    @Test
    public void searchVod_dao_selectVodChannel() {
    	VodDetailReq req = new VodDetailReq();
    	req.setChannelId("H090107970");
    	req.setLangCd("ko");
    	req.setTenantId("S01");
    	req.setDeviceModel("SHW-M110S");
    	req.setImgCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
    	req.setOrderedBy("recent");
		VodDetail vodDetail = this.commonDAO.queryForObject("VodDetail.selectVodChannel", req, VodDetail.class);
		this.logger.debug("vodDetail={}", vodDetail);
    }

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


}
