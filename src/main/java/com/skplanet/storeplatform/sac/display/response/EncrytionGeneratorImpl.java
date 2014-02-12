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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionData;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionDeviceKey;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionSubContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionUsagePolicy;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * App 상품 전용 정보 Generator 구현체.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
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
		List<EncryptionSubContents> subContentsList = new ArrayList<EncryptionSubContents>();

		// 요청 만료 정보
		contents.setExpired(metaInfo.getExpiredDate());

		// 상품 및 구매 정보
		data.setTitle(metaInfo.getProdNm());
		data.setTopCatCd(metaInfo.getTopMenuId());
		data.setCatCd(metaInfo.getMenuId());
		data.setPacketFee(metaInfo.getProdClsfCd());
		data.setProductFee(metaInfo.getProdChrg());
		data.setProductId(metaInfo.getPurchaseProdId());
		data.setPurchaseId(metaInfo.getPurchaseId());
		data.setPurchaseDate(metaInfo.getPurchaseDt());

		// 서브 컨텐츠 정보
		if (StringUtils.isNotEmpty(metaInfo.getNmBtvCid())) {
			subContents = new EncryptionSubContents();
			subContents.setScid(metaInfo.getNmBtvCid());
			subContents.setPath(metaInfo.getNmFilePath());
			subContentsList.add(subContents);
		}
		if (StringUtils.isNotEmpty(metaInfo.getSdBtvCid())) {
			subContents = new EncryptionSubContents();
			subContents.setScid(metaInfo.getSdBtvCid());
			subContents.setPath(metaInfo.getSdFilePath());
			subContentsList.add(subContents);
		}
		if (StringUtils.isNotEmpty(metaInfo.getHdBtvCid())) {
			subContents = new EncryptionSubContents();
			subContents.setScid(metaInfo.getHdBtvCid());
			subContents.setPath(metaInfo.getHdFilePath());
			subContentsList.add(subContents);
		}
		if (StringUtils.isNotEmpty(metaInfo.getSubContentsId())) {
			subContents = new EncryptionSubContents();
			subContents.setScid(metaInfo.getSubContentsId());
			subContents.setPath(metaInfo.getFilePath());
			subContentsList.add(subContents);
		}
		data.setSubContents(subContentsList);

		// 사용 정책
		usagePolicy.setApplyDrm(metaInfo.getDrmYn());
		usagePolicy.setExpirationDate(metaInfo.getDwldExprDt()); // 확인필요
		usagePolicy.setBpCode(metaInfo.getBpJoinFileType());
		usagePolicy.setCertKey(metaInfo.getBpJoinFileNo());
		data.setUsagePolicy(usagePolicy);

		// 기기 정보
		deviceKey.setKey(metaInfo.getDeviceKey());
		deviceKey.setType(metaInfo.getDeviceType());
		deviceKey.setSubKey(metaInfo.getDeviceSubKey());
		data.setDeviceKey(deviceKey);

		contents.setData(data);
		return contents;
	}
}
