/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.AutoPay;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 
 * 
 * Updated on : 2014. 2. 11. Updated by : 서영배, GTSOFT.
 */
@Component
public class FreepassInfoGeneratorImpl implements FreepassInfoGenerator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifierList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Identifier> generateIdentifierList(MetaInfo metaInfo) {
		Identifier identifier = new Identifier();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		
		identifier.setType(DisplayConstants.DP_FREEPASS_IDENTIFIER_CD);
		identifier.setText(metaInfo.getProdId());
		identifierList.add(identifier);

		return identifierList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifierList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public AutoPay generateAutoPay(MetaInfo metaInfo) {
		AutoPay autoPay = new AutoPay();
		
		if ("Y".equals(metaInfo.getAutoApprYn()))
			autoPay.setType(DisplayConstants.DP_AUTOPAY_AUTO);
		else
			autoPay.setType(DisplayConstants.DP_AUTOPAY_NORMAL);

		return autoPay;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifierList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Title generateTitle(MetaInfo metaInfo) {
		Title title = new Title();
		
		title.setText(metaInfo.getProdNm());
		title.setAlias(metaInfo.getProdAlias());

		return title;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSourceList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Source> generateSourceList(MetaInfo metaInfo) {
		List<Source> sourceList = new ArrayList<Source>();

		Source source = new Source();
		source.setType(DisplayConstants.DP_SOURCE_TYPE_BANNER);
		source.setUrl(metaInfo.getBannerFilePath());
		sourceList.add(source);
		
		source = new Source();
		source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
		source.setUrl(metaInfo.getThumbnailFilePath());
		sourceList.add(source);
		
		return sourceList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator#generateRights(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Date generateDate(MetaInfo metaInfo) {

		Date date = new Date(DisplayConstants.DP_DATE_USAGE_PERIOD, 
				DateUtils.parseDate(metaInfo.getApplyStartDt()), DateUtils.parseDate(metaInfo.getApplyEndDt()));

		return date;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.AppInfoGenerator#generateMenuList(com.skplanet.storeplatform.
	 * sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Menu> generateMenuList(MetaInfo metaInfo) {
		Menu menu = new Menu();
		List<Menu> menuList = new ArrayList<Menu>();

		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(metaInfo.getTopMenuId());
		menu.setName(metaInfo.getTopMenuNm());
		menuList.add(menu);
		return menuList;
	}

}
