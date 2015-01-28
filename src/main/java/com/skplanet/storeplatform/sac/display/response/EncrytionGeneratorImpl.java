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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionData;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionDeviceKey;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionStatus;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionSubContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionUsagePolicy;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * for download 전용 상품 암호화 정보 Generator 구현체.
 *
 * Updated on : 2014. 03. 26. Updated by : 이태희
 */
@Component
public class EncrytionGeneratorImpl implements EncryptionGenerator {

    @Override
		public EncryptionContents generateEncryptionContents(MetaInfo metaInfo, boolean supportFhdVideo) {
        EncryptionContents contents = new EncryptionContents();
        EncryptionData data = new EncryptionData();
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
        data.setPacketFee(StringUtils.defaultString(metaInfo.getProdClsfCd()));
        data.setProductFee(metaInfo.getProdChrg());
        data.setProductId(metaInfo.getPurchaseProdId());
        data.setPurchaseId(metaInfo.getPurchaseId());
        data.setUserKey(metaInfo.getUserKey());
        data.setPurchasePrice(metaInfo.getPurchasePrice());
        data.setSystemId(metaInfo.getSystemId());
        data.setTenantId(metaInfo.getTenantId());

        date = new Date();
        date.setTextUtc(DateUtils.parseDate(metaInfo.getPurchaseDt()));
        data.setPurchaseDate(date.getText());

        if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(metaInfo.getTopMenuId())
                || DisplayConstants.DP_TV_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {

            // 서브 컨텐츠 정보
            if (StringUtils.isNotEmpty(metaInfo.getNmSubContsId())) {
                EncryptionSubContents nmSc = getEncryptionNmSubContents(metaInfo);
                subContentsList.add(nmSc);
            }
            if (StringUtils.isNotEmpty(metaInfo.getSdSubContsId())) {
                EncryptionSubContents sdSc = getEncryptionSdSubContents(metaInfo);
                subContentsList.add(sdSc);
            }
            //HD2 (D화질) 정보 우선, 없으면 HD 정보를 내려줌
            if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId()) || StringUtils.isNotEmpty(metaInfo.getHd2SubContsId())) {
                EncryptionSubContents hdSc = getEncryptionHdSubContents(metaInfo);
                subContentsList.add(hdSc);
            }
            //FHD
            if (supportFhdVideo && StringUtils.isNotEmpty(metaInfo.getFhdSubContsId())) {
                EncryptionSubContents fhdSc = getEncryptionFhdSubContents(metaInfo);
                subContentsList.add(fhdSc);
            }
        } else {
            EncryptionSubContents sc = new EncryptionSubContents();
            if (StringUtils.isNotBlank(metaInfo.getDeltaType())) {
                sc.setType(metaInfo.getDeltaType());
                sc.setDeltaPath(metaInfo.getDeltaFilePath());
                sc.setDeltaSize(metaInfo.getDeltaFileSize());
            } else {
                sc.setType("");
                sc.setDeltaPath("");
                sc.setDeltaSize(0L);
            }
            sc.setSize(metaInfo.getFileSize());
            sc.setScid(metaInfo.getSubContentsId());
            sc.setPath(metaInfo.getFilePath());
            subContentsList.add(sc);

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

    private EncryptionSubContents getEncryptionSdSubContents(MetaInfo metaInfo) {
        EncryptionSubContents sdSc = new EncryptionSubContents();
        sdSc.setType("");
        sdSc.setDeltaPath("");
        sdSc.setDeltaSize(0L);
        sdSc.setSize(Long.parseLong(metaInfo.getSdFileSize()));
        sdSc.setScid(metaInfo.getSdSubContsId());
        sdSc.setPath(metaInfo.getSdFilePath());
        return sdSc;
    }

    private EncryptionSubContents getEncryptionFhdSubContents(MetaInfo metaInfo) {
        EncryptionSubContents hdSc = new EncryptionSubContents();
        hdSc.setType("");
        hdSc.setDeltaPath("");
        hdSc.setDeltaSize(0L);
        hdSc.setSize(Long.parseLong(metaInfo.getFhdFileSize()));
        hdSc.setScid(metaInfo.getFhdSubContsId());
        hdSc.setPath(metaInfo.getFhdFilePath());
        return hdSc;
    }

    private EncryptionSubContents getEncryptionNmSubContents(MetaInfo metaInfo) {
        EncryptionSubContents nmSc = new EncryptionSubContents();
        nmSc.setType("");
        nmSc.setDeltaPath("");
        nmSc.setDeltaSize(0L);
        nmSc.setSize(Long.parseLong(metaInfo.getNmFileSize()));
        nmSc.setScid(metaInfo.getNmSubContsId());
        nmSc.setPath(metaInfo.getNmFilePath());
        return nmSc;
    }

    private EncryptionSubContents getEncryptionHdSubContents(MetaInfo metaInfo) {
        EncryptionSubContents hdSc = new EncryptionSubContents();
        
        if (StringUtils.isNotEmpty(metaInfo.getHd2SubContsId())) {
        	hdSc.setType("");
            hdSc.setDeltaPath("");
            hdSc.setDeltaSize(0L);
            hdSc.setSize(Long.parseLong(metaInfo.getHd2FileSize()));
            hdSc.setScid(metaInfo.getHd2SubContsId());
            hdSc.setPath(metaInfo.getHd2FilePath());	
        } else {
        	hdSc.setType("");
            hdSc.setDeltaPath("");
            hdSc.setDeltaSize(0L);
            hdSc.setSize(Long.parseLong(metaInfo.getHdFileSize()));
            hdSc.setScid(metaInfo.getHdSubContsId());
            hdSc.setPath(metaInfo.getHdFilePath());
        }
        
        return hdSc;
    }

    private String makeExtra(MetaInfo metaInfo) {

        StringBuilder extra = new StringBuilder();

		if (StringUtils.isNotBlank(metaInfo.getSystemId())) {
			extra.append("systemId=").append(metaInfo.getSystemId()).append(";");
		}

		if (StringUtils.isNotBlank(metaInfo.getParentBunchId())) {
			extra.append("parentBunchId=").append(metaInfo.getParentBunchId()).append(";");
		}
		
		if (StringUtils.isNotBlank(metaInfo.getVisitPathNm())) {
			extra.append("visitPathNm=").append(metaInfo.getVisitPathNm()).append(";");
		}

		if (StringUtils.isNotBlank(metaInfo.getDwldTypeCd())) {
			extra.append("dwldTypeCd=").append(metaInfo.getDwldTypeCd()).append(";");
		}


		return extra.toString();
	}
}
