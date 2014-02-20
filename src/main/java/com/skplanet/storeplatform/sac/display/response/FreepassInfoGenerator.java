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

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.AutoPay;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 
 * 
 * Updated on : 2014. 2. 11. Updated by : 서영배, GTSOFT.
 */
public interface FreepassInfoGenerator {
	
	/**
	 * <pre>
	 * 정액제 Identifier List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Identifier>
	 */
	public List<Identifier> generateIdentifierList(MetaInfo metaInfo);
	
	/**
	 * <pre>
	 * 정액제 AutoPay 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return AutoPay
	 */
	public AutoPay generateAutoPay(MetaInfo metaInfo);
	
	/**
	 * <pre>
	 * 정액제 Title 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Title
	 */
	public Title generateTitle(MetaInfo metaInfo);
	
	/**
	 * <pre>
	 * 정액제 Source List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Source>
	 */
	public List<Source> generateSourceList(MetaInfo metaInfo);
	
	/**
	 * <pre>
	 * 정액제 Date 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Date
	 */
	public Date generateDate(MetaInfo metaInfo);
	
	/**
	 * <pre>
	 * 정액제 Menu 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List
	 */
	public List<Menu> generateMenuList(MetaInfo metaInfo);

}
