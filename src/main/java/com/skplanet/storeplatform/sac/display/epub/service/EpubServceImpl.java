/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.epub.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;

/**
 * EPUB Service
 * 
 * Updated on : 2014-01-09 Updated by : 임근대, SK플래닛.
 */
@Service
@Transactional
public class EpubServceImpl implements EpubService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client
	 * .display.vo.vod.VodDetailReq)
	 */
	@Override
	public EpubDetailRes searchEpub(EpubDetailReq req) {
		EpubDetailRes res = new EpubDetailRes();

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

		// subProjectList
		List<Product> subProjectList = new ArrayList<Product>();
		Product product1 = new Product();
		product1.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, "H001539415"));
		product1.setTitle(new Title("별에서 온 그대 7회"));
		Rights rights = new Rights();
		Support support = new Support();
		rights.setPlay(new Play(support, new Price(700), new Date("date/publish", "2013")));
		rights.setStore(new Store(support, new Price(700), new Date("date/publish", "2013")));
		rights.setDate(new Date("date/publish", "2013"));
		product1.setRights(rights);

		Book book = new Book();
		book.setStatus("continue");
		book.setTotalPages("13");
		book.setType("serial");

		List<Support> supportList = new ArrayList<Support>();
		support.setType("store");
		supportList.add(support);
		book.setSupportList(supportList);
		product1.setBook(new Book());
		subProjectList.add(product1);
		product.setSubProductList(subProjectList);

		res.setProduct(product);
		/*
		 * 
		 * { "product":{ "identifier":{ "type":"STRING", "text":"STRING" }, "menuList":[ { "id":"STRING",
		 * "name":"STRING", "type":"STRING" } ], "title":{ "text":"STRING" }, "subProduct":{ "product":{ "identifier":{
		 * "type":"STRING", "text":"STRING" }, "title":{ "text":"STRING" }, "rights":{ "play":{ "support":"STRING",
		 * "price":{ "text":0 }, "date":{ "type":"STRING", "text":"STRING" } }, "store":{ "support":"STRING", "price":{
		 * "text":0 }, "date":{ "type":"STRING", "text":"STRING" } } }, "book":{ "type":"STRING",
		 * "updateCycle":"STRING", "totalPages":"STRING", "bookVersion":"STRING", "scid":"STRING", "size":0,
		 * "url":"STRING", "chapter":{ "unit":"STRING" } } } } } }
		 */

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.epub.service.EpubService#searchEpubSeries(com.skplanet.storeplatform.sac
	 * .client.display.vo.epub.EpubSeriesReq)
	 */
	@Override
	public EpubSeriesRes searchEpubSeries(EpubSeriesReq req) {
		EpubSeriesRes res = new EpubSeriesRes();

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

		// subProjectList
		List<Product> subProjectList = new ArrayList<Product>();
		Product product1 = new Product();
		product1.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, "H001539415"));
		product1.setTitle(new Title("별에서 온 그대 7회"));
		Rights rights = new Rights();
		Support support = new Support();
		rights.setPlay(new Play(support, new Price(700), new Date("date/publish", "2013")));
		rights.setStore(new Store(support, new Price(700), new Date("date/publish", "2013")));
		rights.setDate(new Date("date/publish", "2013"));
		product1.setRights(rights);

		Book book = new Book();
		book.setStatus("continue");
		book.setTotalPages("13");
		book.setType("serial");

		List<Support> supportList = new ArrayList<Support>();
		support.setType("store");
		supportList.add(support);
		book.setSupportList(supportList);
		product1.setBook(new Book());
		subProjectList.add(product1);
		product.setSubProductList(subProjectList);

		res.setProduct(product);
		/*
		 * 
		 * { "product":{ "identifier":{ "type":"STRING", "text":"STRING" }, "menuList":[ { "id":"STRING",
		 * "name":"STRING", "type":"STRING" } ], "title":{ "text":"STRING" }, "subProduct":{ "product":{ "identifier":{
		 * "type":"STRING", "text":"STRING" }, "title":{ "text":"STRING" }, "rights":{ "play":{ "support":"STRING",
		 * "price":{ "text":0 }, "date":{ "type":"STRING", "text":"STRING" } }, "store":{ "support":"STRING", "price":{
		 * "text":0 }, "date":{ "type":"STRING", "text":"STRING" } } }, "book":{ "type":"STRING",
		 * "updateCycle":"STRING", "totalPages":"STRING", "bookVersion":"STRING", "scid":"STRING", "size":0,
		 * "url":"STRING", "chapter":{ "unit":"STRING" } } } } } }
		 */

		return res;
	}

}
