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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
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
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 
 * 
 * Updated on : 2015. 05. 08. Updated by : 이태균, IS-PLUS.
 */
@Component
public class VoucherInfoGeneratorImpl implements VoucherInfoGenerator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifierList(com.skplanet.
	 * storeplatform .sac.display.meta.vo.MetaInfo)
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
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifierList(com.skplanet.
	 * storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public AutoPay generateAutoPay(MetaInfo metaInfo) {
		AutoPay autoPay = new AutoPay();

		// 자동결제
		if ("Y".equals(metaInfo.getAutoApprYn()))
			autoPay.setType(DisplayConstants.DP_AUTOPAY_AUTO);
		else
			// 일반결제
			autoPay.setType(DisplayConstants.DP_AUTOPAY_NORMAL);

		return autoPay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifierList(com.skplanet.
	 * storeplatform .sac.display.meta.vo.MetaInfo)
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
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getBannerFilePath()));
		source.setType(DisplayConstants.DP_SOURCE_TYPE_BANNER);
		source.setUrl(metaInfo.getBannerFilePath());
		sourceList.add(source);

		source = new Source();
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getThumbnailFilePath()));
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

		Date date = new Date(DisplayConstants.DP_DATE_SALE_PERIOD_DURATION, DateUtils.parseDate(metaInfo
				.getApplyStartDt()), DateUtils.parseDate(metaInfo.getApplyEndDt()));

		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator#generateDateList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Date> generateDateList(MetaInfo metaInfo) {

		List<Date> dateList = new ArrayList<Date>();
		Date date = this.generateDate(metaInfo);
		dateList.add(date);

		// 사용기간 Date 변환.
		date = this.generateUsePeriod(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, metaInfo.getUsePeriodUnitCd(),
				metaInfo.getUsePeriod());
		if (date != null)
			dateList.add(date);

		// 구매일로부터 기간 제한
		/*
		 * if (StringUtil.nvl(metaInfo.getUsePeriodUnitCd(), "") != "") { String usePeriodUnitNm =
		 * (metaInfo.getUsePeriodUnitCd() == null) ? "" : (metaInfo.getUsePeriodUnitCd()
		 * .equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) ? "unlimit" : (metaInfo.getUsePeriodUnitCd()
		 * .equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_HOUR)) ? "hour" : (metaInfo.getUsePeriodUnitCd()
		 * .equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_DAY)) ? "day" : (metaInfo.getUsePeriodUnitCd()
		 * .equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MONTH)) ? "month" : (metaInfo.getUsePeriodUnitCd()
		 * .equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_YEAR)) ? "year" : (metaInfo.getUsePeriodUnitCd()
		 * .equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_DAY)) ? "limit/day" : (metaInfo
		 * .getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_MONTH)) ? "limit/month" : (metaInfo
		 * .getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_YEAR)) ? "limit/year" : (metaInfo
		 * .getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_CALENDAR)) ? "calendar" : "";
		 * 
		 * if (usePeriodUnitNm != "") { date = new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, usePeriodUnitNm + "/"
		 * + metaInfo.getUsePeriod()); dateList.add(date); } }
		 */

		return dateList;
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
		Menu menu = null;
		List<Menu> menuList = new ArrayList<Menu>();

		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(metaInfo.getTopMenuId());
		menu.setName(metaInfo.getTopMenuNm());
		menuList.add(menu);
		return menuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.FreepassInfoGenerator#generateCashList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Cash> generateCashList(MetaInfo metaInfo) {
		Cash cash = null;
		List<Cash> cashList = new ArrayList<Cash>();

		if (metaInfo.getCashAmt() != null) {
			cash = new Cash();
			cash.setName(DisplayConstants.DP_FREEPASS_CASH);
			cash.setText(metaInfo.getCashAmt());
			cashList.add(cash);

			cash = new Cash();
			cash.setName(DisplayConstants.DP_FREEPASS_BONUS);
			cash.setText(metaInfo.getBnsCashAmt());
			cash.setCashRate(metaInfo.getBnsCashRatio());
			Date date = null;

			// 사용기간 Date 변환
			date = this.generateUsePeriod(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, metaInfo.getBnsUsePeriodUnitCd(),
					metaInfo.getBnsUsePeriod());

			// 구매일로부터 기간 제한
			/*
			 * if (StringUtil.nvl(metaInfo.getBnsUsePeriodUnitCd(), "") != "") { String bnsUsePeriodUnitNm =
			 * (metaInfo.getBnsUsePeriodUnitCd() == null) ? "" : (metaInfo
			 * .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) ? "unlimit" : (metaInfo
			 * .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_HOUR)) ? "hour" : (metaInfo
			 * .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_DAY)) ? "day" : (metaInfo
			 * .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MONTH)) ? "month" : (metaInfo
			 * .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_YEAR)) ? "year" : (metaInfo
			 * .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_DAY)) ? "limit/day" :
			 * (metaInfo .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_MONTH)) ?
			 * "limit/month" : (metaInfo
			 * .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_YEAR)) ? "limit/year" :
			 * (metaInfo .getBnsUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_CALENDAR)) ? "calendar"
			 * : "";
			 * 
			 * if (bnsUsePeriodUnitNm != "") { date = new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD,
			 * bnsUsePeriodUnitNm + "/" + metaInfo.getBnsUsePeriod());
			 * 
			 * } }
			 */
			cash.setDate(date);

			cashList.add(cash);
		}

		return cashList;
	}

	/**
	 * <pre>
	 * 사용기간 Date 변환.
	 * </pre>
	 * 
	 * @param dateType
	 *            dateType
	 * @param usePeriodUnitCd
	 *            usePeriodUnitCd
	 * @param usePeriod
	 *            usePeriod
	 * @return Date
	 */
	public Date generateUsePeriod(String dateType, String usePeriodUnitCd, String usePeriod) {
		Date date = null;
		// 구매일로부터 기간 제한
		if (StringUtil.isNotEmpty(usePeriodUnitCd)) {
			String usePeriodUnitNm = (usePeriodUnitCd == null) ? "" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) ? "unlimit" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_HOUR)) ? "hour" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_DAY)) ? "day" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MONTH)) ? "month" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_YEAR)) ? "year" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_DAY)) ? "limit/day" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_MONTH)) ? "limit/month" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_YEAR)) ? "limit/year" : (usePeriodUnitCd
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_CALENDAR)) ? "calendar" : "";

			if (StringUtil.isNotEmpty(usePeriodUnitNm)) {
				if (!usePeriodUnitNm.equals("unlimit")) {
					date = new Date(dateType, usePeriodUnitNm + "/" + usePeriod);
				} else {
					date = new Date(dateType, usePeriodUnitNm);
				}

			}

		}

		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.VoucherInfoGenerator#generateFreepassAttrList(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public FreepassAttr generateFreepassAttrList(MetaInfo metaInfo) {
		FreepassAttr freepassAttr = new FreepassAttr();

		freepassAttr.setCouponGroup(metaInfo.getCmpxProdGrpCd());
		freepassAttr.setPossLend(metaInfo.getPossLendClsfCd());
		freepassAttr.setSerialBook(metaInfo.getSeriesBookClsfCd());
		freepassAttr.setDrmAttrCd(metaInfo.getDrmAttrCd());
		freepassAttr.setDlStrmAttrCd(metaInfo.getDlStrmAttrCd());
		freepassAttr.setUsePeriodAttrCd(metaInfo.getUsePeriodAttrCd());

		return freepassAttr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.VoucherInfoGenerator#generateRights(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Rights generateRights(MetaInfo metaInfo) {
		Rights rights = new Rights();
		rights.setGrade(metaInfo.getProdGrdCd());
		rights.setPlus19Yn(metaInfo.getPlus19Yn());
		return rights;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.VoucherInfoGenerator#generateSupportList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Support> generateSupportList(MetaInfo metaInfo) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = null;
		support = new Support();

		/* Play */
		support.setType("play");

		// 대여, 소장/대여 경우
		if (metaInfo.getPossLendClsfCd().equals("DP010602") || metaInfo.getPossLendClsfCd().equals("DP010603")) {
			support.setText("Y");
		} else {
			support.setText("N");
		}
		supportList.add(support);

		/* Store */
		support = new Support();
		support.setType("store");

		// 소장, 소장/대여 경우
		if (metaInfo.getPossLendClsfCd().equals("DP010601") || metaInfo.getPossLendClsfCd().equals("DP010603")) {
			support.setText("Y");
		} else {
			support.setText("N");
		}

		supportList.add(support);

		support = new Support();
		support.setType("drm");
		if (StringUtil.isNotEmpty(metaInfo.getDrmYn())) {
			support.setText(metaInfo.getDrmYn());
		}
		supportList.add(support);

		support = new Support();
		support.setType("dlStrmCd");
		if (StringUtil.isNotEmpty(metaInfo.getDwldStrmClsfCd())) {
			// 다운로드
			if (metaInfo.getDwldStrmClsfCd().equals("DP010501")) {
				support.setText("dl");
				// 스트리밍
			} else if (metaInfo.getDwldStrmClsfCd().equals("DP010502")) {
				support.setText("strm");
				// 다운로드&스트리밍
			} else if (metaInfo.getDwldStrmClsfCd().equals("DP010503")) {
				support.setText("both");
			}
		}

		supportList.add(support);

		return supportList;
	}
}
