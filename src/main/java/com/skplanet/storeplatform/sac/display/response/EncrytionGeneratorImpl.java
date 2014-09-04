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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * for download 전용 상품 암호화 정보 Generator 구현체.
 *
 * Updated on : 2014. 03. 26. Updated by : 이태희
 */
@Component
public class EncrytionGeneratorImpl implements EncryptionGenerator {
	@Override
	public EncryptionContents generateEncryptionContents(MetaInfo metaInfo) {
		EncryptionContents contents = new EncryptionContents();
		EncryptionData data = new EncryptionData();
		EncryptionSubContents subContents = null;
		EncryptionUsagePolicy usagePolicy = new EncryptionUsagePolicy();
		EncryptionDeviceKey deviceKey = new EncryptionDeviceKey();
		Date date = new Date();
		EncryptionStatus status = new EncryptionStatus();

		List<EncryptionSubContents> subContentsList = new ArrayList<EncryptionSubContents>();

		// 요청 만료 정보
		date.setTextUtc(DateUtils.parseDate(metaInfo.getExpiredDate()));
		contents.setExpired(date.getText());

		// 상품 및 구매 정보
		data.setTitle(metaInfo.getProdNm());
		data.setTopCatCd(metaInfo.getTopMenuId());
		data.setCatCd(metaInfo.getMenuId());
		data.setPacketFee(this.isEmpty(metaInfo.getProdClsfCd()));
		data.setProductFee(metaInfo.getProdChrg());
		data.setProductId(metaInfo.getPurchaseProdId());
		data.setPurchaseId(metaInfo.getPurchaseId());
		data.setUserKey(metaInfo.getUserKey());
		data.setPurchasePrice(metaInfo.getPurchasePrice());

		date = new Date();
		date.setTextUtc(DateUtils.parseDate(metaInfo.getPurchaseDt()));
		data.setPurchaseDate(date.getText());

		if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(metaInfo.getTopMenuId())
				|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {

            subContents = new EncryptionSubContents();
            subContents.setType("");
            subContents.setDeltaPath("");
            subContents.setDeltaSize(0L);

			// 서브 컨텐츠 정보
			if (StringUtils.isNotEmpty(metaInfo.getNmSubContsId())) {
				subContents.setSize(Long.parseLong(metaInfo.getNmFileSize()));
				subContents.setScid(metaInfo.getNmSubContsId());
				subContents.setPath(metaInfo.getNmFilePath());
                subContentsList.add(subContents);
			}
			if (StringUtils.isNotEmpty(metaInfo.getSdSubContsId())) {
				subContents.setSize(Long.parseLong(metaInfo.getSdFileSize()));
				subContents.setScid(metaInfo.getSdSubContsId());
				subContents.setPath(metaInfo.getSdFilePath());
                subContentsList.add(subContents);
			}
			if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())) {
				subContents.setSize(Long.parseLong(metaInfo.getHdFileSize()));
				subContents.setScid(metaInfo.getHdSubContsId());
				subContents.setPath(metaInfo.getHdFilePath());
                subContentsList.add(subContents);
			}
		} else {
			subContents = new EncryptionSubContents();
			subContents.setType("");
			subContents.setDeltaPath("");
			subContents.setSize(metaInfo.getFileSize());
			subContents.setDeltaSize(0L);
			subContents.setScid(metaInfo.getSubContentsId());
			subContents.setPath(metaInfo.getFilePath());
			subContentsList.add(subContents);

		}
		data.setSubContents(subContentsList);

		// 사용 정책
		usagePolicy.setApplyDrm(metaInfo.getDrmYn());
		if (StringUtils.isNotEmpty(metaInfo.getUseExprDt())) {
			date = new Date();
			date.setTextUtc(DateUtils.parseDate(metaInfo.getUseExprDt()));
			usagePolicy.setExpirationDate(date.getText());
		} else {
			usagePolicy.setExpirationDate("");
		}
		data.setUsagePolicy(usagePolicy);

		// 기기 정보
		deviceKey.setKey(metaInfo.getDeviceKey());
		deviceKey.setType(metaInfo.getDeviceType());
		deviceKey.setSubKey(metaInfo.getDeviceSubKey());
		data.setDeviceKey(deviceKey);

		// 구매내역 숨김 유무 / 업데이트 알람 유무
		if (StringUtils.isNotEmpty(metaInfo.getPurchaseHide())
				&& StringUtils.isNotEmpty(metaInfo.getUpdateAlarm())) {

			status.setPurchaseHide(metaInfo.getPurchaseHide());
			status.setUpdateAlarm(metaInfo.getUpdateAlarm());

			data.setStatus(status);
		}

		// extra : 값의 형식은 "key=value;key2=value2;"로 구성된다. 추후 정의하여 사용.
		data.setExtra(this.makeExtra(metaInfo));

		contents.setData(data);
		return contents;
	}

	private String isEmpty(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		return str;
	}

	private String makeExtra(MetaInfo metaInfo) {

		StringBuffer extra = new StringBuffer("");

		if (StringUtils.isNotBlank(metaInfo.getParentBunchId())) {
			extra.append("parentBunchId=").append(metaInfo.getParentBunchId()).append(";");
		}

		return extra.toString();
	}
}
