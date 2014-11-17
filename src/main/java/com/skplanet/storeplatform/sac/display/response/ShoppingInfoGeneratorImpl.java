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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 쇼핑 상품 전용 정보 Generator 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Component
public class ShoppingInfoGeneratorImpl implements ShoppingInfoGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator
	 * #generateContributor(com.skplanet.storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		Identifier identifier = new Identifier();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifier.setType(DisplayConstants.DP_BRAND_IDENTIFIER_CD);
		identifier.setText(metaInfo.getBrandId());
		identifierList.add(identifier);
		contributor.setIdentifierList(identifierList);
		contributor.setName(metaInfo.getBrandNm()); // 브랜드명
		return contributor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator
	 * #generateSalesOption(com.skplanet.storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public SalesOption generateSalesOption(MetaInfo metaInfo) {
		SalesOption salesOption = new SalesOption();
		salesOption.setType(metaInfo.getProdCaseCd());
		if (StringUtils.isNotEmpty(metaInfo.getSoldOut())) {
			if (metaInfo.getSoldOut().equals("Y")) {
				salesOption.setStatus(DisplayConstants.DP_SOLDOUT);
			} else {
				salesOption.setStatus(DisplayConstants.DP_CONTINUE);
			}
		}
		return salesOption;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator
	 * #generateRights(com.skplanet.storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Rights generateRights(MetaInfo metaInfo) {
		List<Date> dateList = new ArrayList<Date>();
		Rights rights = new Rights();
		rights.setGrade(metaInfo.getProdGrdCd());
		Date date = new Date(DisplayConstants.DP_DATE_SALE_PERIOD_DURATION, DateUtils.parseDate(metaInfo
				.getApplyStartDt()), DateUtils.parseDate(metaInfo.getApplyEndDt()));
		dateList.add(date);
		rights.setDateList(dateList);
		return rights;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator
	 * #generateAccrual(com.skplanet.storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		accrual.setDownloadCount(metaInfo.getPrchsCnt());
		return accrual;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator
	 * #generatePrice(com.skplanet.storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Price generatePrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setFixedPrice(metaInfo.getProdNetAmt());
		price.setDiscountRate(metaInfo.getDcRate());
		price.setDiscountPrice(Integer.parseInt(metaInfo.getDcAmt()));
		price.setText(metaInfo.getProdAmt());
		return price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator
	 * #generateSpecialSalesSourceList(com.skplanet .storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Source> generateSpecialSalesSourceList(MetaInfo metaInfo) {
		List<Source> sourceList = new ArrayList<Source>();

		Source source = new Source();
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getBannerFilePath()));
		source.setType(DisplayConstants.DP_SOURCE_TYPE_BANNER);
		source.setUrl(metaInfo.getBannerFilePath());
		sourceList.add(source);

		source = new Source();
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getFilePath()));
		source.setType(DisplayConstants.DP_SOURCE_TYPE_PROMOTION);
		source.setUrl(metaInfo.getFilePath());
		sourceList.add(source);

		return sourceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator
	 * #generateSpecialSalesSourceList(com.skplanet .storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Source> generateSpecialSalesSourceListV2(MetaInfo metaInfo) {
		List<Source> sourceList = new ArrayList<Source>();

		Source source = new Source();
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getBannerFilePath()));
		source.setType(DisplayConstants.DP_SOURCE_TYPE_BANNER);
		source.setUrl(metaInfo.getBannerFilePath());
		sourceList.add(source);

		source = new Source();
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getFilePath()));
		source.setType(DisplayConstants.DP_SOURCE_TYPE_PROMOTION);
		source.setUrl(metaInfo.getFilePath());
		sourceList.add(source);

		source = new Source();
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getDetailPromotionPath()));
		source.setType(DisplayConstants.DP_SOURCE_TYPE_DETAIL_PROMOTION);
		source.setUrl(metaInfo.getDetailPromotionPath());
		sourceList.add(source);

		return sourceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator
	 * #generateDate(com.skplanet.storeplatform .sac.display.meta.vo.MetaInfo)
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
	 * @see com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator
	 * #generateDateList(com.skplanet.storeplatform .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Date> generateDateList(MetaInfo metaInfo) {
		List<Date> dateList = new ArrayList<Date>();
		Date date = this.generateDate(metaInfo);
		dateList.add(date);

		// 구매일로부터 기간 제한
		if (StringUtil.nvl(metaInfo.getUsePeriodUnitCd(), "") != "") {
			String usePeriodUnitNm = (metaInfo.getUsePeriodUnitCd() == null) ? "" : (metaInfo.getUsePeriodUnitCd()
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) ? "unlimit" : (metaInfo.getUsePeriodUnitCd()
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_HOUR)) ? "hour" : (metaInfo.getUsePeriodUnitCd()
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_DAY)) ? "day" : (metaInfo.getUsePeriodUnitCd()
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MONTH)) ? "month" : (metaInfo.getUsePeriodUnitCd()
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_YEAR)) ? "year" : (metaInfo.getUsePeriodUnitCd()
					.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_DAY)) ? "limit/day" : (metaInfo
					.getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_MONTH)) ? "limit/month" : (metaInfo
					.getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_YEAR)) ? "limit/year" : (metaInfo
					.getUsePeriodUnitCd().equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_CALENDAR)) ? "calendar" : "";

			if (usePeriodUnitNm != "") {
				if (!usePeriodUnitNm.equals("calendar")) {
					date = new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, usePeriodUnitNm + "/"
							+ metaInfo.getUsePeriod());
				} else {
					Date date1 = new Date();
					date1.setTextFromDate(DateUtils.parseDate(metaInfo.getUsePeriod()));
					date = new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, usePeriodUnitNm + "/" + date1.getText());
				}
				dateList.add(date);
			}
		}

		return dateList;
	}

}
