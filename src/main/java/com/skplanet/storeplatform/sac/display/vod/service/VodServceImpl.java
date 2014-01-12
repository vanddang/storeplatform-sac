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

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;

/**
 * VOD Service
 *
 * Updated on : 2014-01-09
 * Updated by : 임근대, SK플래닛.
 */
@Service
@Transactional
public class VodServceImpl implements VodService {

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq)
	 */
	@Override
	public VodDetailRes searchVod(VodDetailReq req) {
		//Dummy
		VodDetailRes res = new VodDetailRes();

		Product product = new Product();
		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, "H001539415"));

        Menu menu = new Menu();
        menu.setId("DP000518");
        menu.setType("broadcast");

        Menu menu1 = new Menu();
        menu1.setId("DP18001");
        menu1.setName("broadcast/drama");

        product.setMenuList(new ArrayList<Menu>(Arrays.asList(menu, menu1)));

        product.setTitle(new Title("별에서 온 그대 7회"));


        //subProjectList
        List<Product> subProjectList = new ArrayList<Product>();
		Product product1 = new Product();
		product1.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, "H001539415"));
        product1.setTitle(new Title("별에서 온 그대 7회"));
        Rights rights = new Rights();
        rights.setPlay(new Play("", new Price(700), new Date("date/publish", "2013")));
        rights.setStore(new Store("", new Price(700), new Date("date/publish", "2013")));
        rights.setDate(new Date("date/publish", "2013"));
        product1.setRights(rights);

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

		return res;
	}

}
