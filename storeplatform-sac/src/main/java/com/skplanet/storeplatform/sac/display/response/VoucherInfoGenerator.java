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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Cash;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.FreepassAttr;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 
 * 
 * Updated on : 2015. 05. 08. Updated by : 이태균, IS-PLUS.
 */
public interface VoucherInfoGenerator {

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
	 * 정액제 Date 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List
	 */
	public List<Date> generateDateList(MetaInfo metaInfo);

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

	/**
	 * <pre>
	 * 정액제 Cash 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List
	 */
	public List<Cash> generateCashList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 이용권 생성정보 Date 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return FreepassAttr
	 */
	public FreepassAttr generateFreepassAttrList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 이용권 Rigjts 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Rights generateRights(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 지원 Support 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public List<Support> generateSupportList(MetaInfo metaInfo);
}
