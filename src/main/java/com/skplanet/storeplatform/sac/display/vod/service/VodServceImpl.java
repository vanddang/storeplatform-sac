/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.vod.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.vod.controller.VodController;
import com.skplanet.storeplatform.sac.display.vod.vo.VodDetail;

/**
 * VOD Service
 *
 * Updated on : 2014-01-09
 * Updated by : 임근대, SK플래닛.
 */
@Service
@Transactional
public class VodServceImpl implements VodService {

	private static final Logger logger = LoggerFactory.getLogger(VodController.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

    @Autowired
    private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq)
	 */
	@Override
	public VodDetailRes searchVod(VodDetailReq req) {
		//Dummy
		VodDetailRes res = new VodDetailRes();
		Product product = new Product();

		//Channel 정보 조회
		VodDetail appDetail = null;//this.commonDAO.queryForObject("VodDetail.selectVodDetail", req, VodDetail.class);
		logger.debug("appDetail={}", appDetail);

        // Menu
		/*
        List<MenuItem> menuList = this.commonService.getMenuItemList(req.getChannelld(), req.getLangCd());
        product.setMenuList(new ArrayList<Menu>());
        for (MenuItem mi : menuList) {
            Menu menu = new Menu();
            menu.setId(mi.getMenuId());
            menu.setName(mi.getMenuNm());
            if(mi.isInfrMenu())
                menu.setType("topClass");

            product.getMenuList().add(menu);
        }
        */

		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, "H900194984"));
		//title
		product.setTitle(new Title("무한도전"));
		//sourceList
		Source source = new Source();
		source.setMediaType("image/png");
		source.setSize(0);
		source.setType("thumbnail");
		source.setUrl("/SMILE_DATA7/PVODS/201401/11/0000194954/20/0000203872/19/ST1_00000208638_1_0859_694x465.PNG");
		product.setSourceList(new ArrayList<Source>(Arrays.asList(source)));

		product.setProductExplain("국내 최고 리얼 버라이어티 쇼, 무한도전!!");
		product.setProductDetailExplain("국내 최고 리얼 버라이어티 쇼, 무한도전!! 대한민국 평균 이하임을 자처하는 일곱 남자지만, 이들이 모이면 천하무적!");

		Support support = new Support();
		support.setType("drm");
		support.setText("Y");
		Support support1 = new Support();
		support1.setType("hd");
		support1.setText("Y");
		product.setSupportList(new ArrayList<Support>(Arrays.asList(support, support2)));

        Menu menu = new Menu();
        menu.setId("DP000518");
        menu.setType("broadcast");

        Menu menu1 = new Menu();
        menu1.setId("DP18001");
        menu1.setName("broadcast/drama");

        product.setMenuList(new ArrayList<Menu>(Arrays.asList(menu, menu1)));


        Date date = new Date();
        date.setType("date/reg");
        date.setText("20130324T220917+0900");
        Date date1 = new Date();
        date1.setType("date/saleReg");
        date1.setText("20130324T220917+0900");
        product.setDateList(new ArrayList<Date>(Arrays.asList(date, date1)));


        Contributor contributor = new Contributor();
        contributor.setArtist("유재석,박명수,정준하,정형돈,노홍철,하하");
        contributor.setChannel("mbc");
        product.setContributor(contributor);


        Distributor distributor = new Distributor();
        distributor.setIdentifier("mbc_tv");
        distributor.setName("서진우");
        distributor.setTel("0215990011");
        distributor.setEmail("skplanet_vod@tstore.co.kr");
        product.setDistributor(distributor);

        Rights rights = new Rights();
        rights.setGrade("0");
        rights.setAllow("domestic");

        Preview preview = new Preview();

        Source previewSource = new Source();
        previewSource.setMediaType("video/mp4");
        previewSource.setType("video/x-freeview-lq");
        previewSource.setUrl("http://../preview.mp4");
        preview.setSource(previewSource);
        rights.setPreview(preview);

        rights.setPlay(new Play("", new Price(700), new Date("date/publish", "2013")));
        rights.setStore(new Store("", new Price(700), new Date("date/publish", "2013")));
        rights.setDate(new Date("date/publish", "2013"));
        product.setRights(rights);

        Accrual accrual = new Accrual();
        accrual.setVoterCount(48);
        accrual.setDownloadCount(1776);
        accrual.setScore(3.0);
        product.setAccrual(accrual);

        product.setSubProductTotalCount(100);

        //subProjectList
        List<Product> subProjectList = new ArrayList<Product>();
		Product product1 = new Product();
		product1.setIdentifier(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, "0002119673"));

		Support support2 = new Support();
		support2.setType("drm");
		support2.setText("Y");
		Support support3 = new Support();
		support3.setType("hd");
		support3.setText("Y");
		product1.setSupportList(new ArrayList<Support>(Arrays.asList(support2, support3)));

		product1.setTitle(new Title("무한도전"));

        Date date2 = new Date();
        date2.setType("date/reg");
        date2.setText("20130324T220917+0900");
        Date date3 = new Date();
        date3.setType("date/saleReg");
        date3.setText("20130324T220917+0900");
        product1.setDateList(new ArrayList<Date>(Arrays.asList(date2, date3)));

        Rights rights2 = new Rights();
        rights2.setPlay(new Play("", new Price(700), new Date("date/publish", "2013")));
        rights2.setStore(new Store("", new Price(700), new Date("date/publish", "2013")));
        rights2.setDate(new Date("date/publish", "2013"));
        product1.setRights(rights2);

        VideoInfo videoInfo = new VideoInfo();
		videoInfo.setScid("0002663073");
		videoInfo.setType("normal");
		videoInfo.setPixel("576x324");
		videoInfo.setPictureSize("16:9");
		videoInfo.setVersion("1");
		videoInfo.setBtvcid("2222222222");
		videoInfo.setSize("307990233");

        product1.setVod(new Vod(new Time("16", "63"), videoInfo));
        subProjectList.add(product1);
        product.setSubProductList(subProjectList);

        res.setProduct(product);


/*

{
   "product":{
		"identifier":{
			"type":"STRING",
			"text":"STRING"
		},
		"menuList":[
        	{
                  "id":"STRING",
                  "name":"STRING",
                  "type":"STRING"
            }
		],
		"title":{
			"text":"STRING"
		},
      	"subProductList":{
			"product":{
            	"identifier":{
					"type":"STRING",
					"text":"STRING"
				},
				"title":{
					"text":"STRING"
				},
				"rights":{
					"play":{
						"support":"STRING",
						"price":{
							"text":0
						},
						"date":{
							"type":"STRING",
							"text":"STRING"
						}
               		},
					"store":{
                  		"support":"STRING",
                  		"price":{
                     		"text":0
                  		},
                  		"date":{
                     		"type":"STRING",
                     		"text":"STRING"
                  		}
               		}
            	},
				"vod":{
               		"runningTime":{
                  		"text":"STRING"
               		},
               		"videoInfo":{
                  		"scid":"STRING",
                  		"type":"STRING",
                  		"pixel":"STRING",
                  		"pictureSize":"STRING",
                  		"version":"STRING",
                  		"btvcid":"STRING",
                  		"size":"STRING",
                  		"url":"STRING"
               		}
            	},
				"date":{
               		"type":"STRING",
               		"text":"STRING"
            	}
         	}
      	}
   	}
}


*/
		return res;
	}

}
