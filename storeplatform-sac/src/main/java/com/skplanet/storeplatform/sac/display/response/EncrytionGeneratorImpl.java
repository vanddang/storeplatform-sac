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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(EncrytionGeneratorImpl.class);
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
            
            /*
			 * 4.x I/F 일때
			 * update by 2015.08.18 이석희 I-S PLUS
			 */

			if (supportFhdVideo) {
				/*
				 * TB_CM_DEVICE.HDV_SPRT_YN ='N'이면 A,B 화질만 보이게 변경 Update By 2015.09.10 이석희 I-S PLUS
				 */
				if(!"N".equals(metaInfo.getHdvSprtYn())){
					if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())) {
						EncryptionSubContents hdSc = this.getEncryptionHdSubContentsV2(metaInfo);
						subContentsList.add(hdSc);
					}
	
					if (StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
						EncryptionSubContents hihdSc = this.getEncryptionHiHdSubContents(metaInfo);
						subContentsList.add(hihdSc);
					}
				}
			} else {
				/*
				 * 고화질 정보(3.x I/F 일때) HD 화질 정보와 HIHD 화질정보 동시에 존재 할때에는 HIHD 화질이 우선적으로 내려가도록  update by 2015.08.18 이석희 I-S PLUS
				 * TB_CM_DEVICE.HDV_SPRT_YN ='N'이면 A,B 화질만 보이게 변경 Update By 2015.09.10 이석희 I-S PLUS
				 */
				if(!"N".equals(metaInfo.getHdvSprtYn())){
					if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())|| StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
						EncryptionSubContents hdSc = this.getEncryptionHdSubContents(metaInfo);
						subContentsList.add(hdSc);
					}
				}
			}			
			
			// HD2 (D화질) 정보 우선, 없으면 HD 정보를 내려줌
//			if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())|| StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
//				EncryptionSubContents hdSc = this.getEncryptionHdSubContents(metaInfo);
//				subContentsList.add(hdSc);
//			}
			// FHD( FHD 화질이 내려가지 않도록 주석처리) update by 2015.08.18 이석희 I-S PLUS
//			if (supportFhdVideo	&& StringUtils.isNotEmpty(metaInfo.getFhdSubContsId())) {
//				EncryptionSubContents fhdSc = this.getEncryptionFhdSubContents(metaInfo);
//				subContentsList.add(fhdSc);
//			}
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
        String dlStrmCd = getDlStrmCd(metaInfo);
        usagePolicy.setDlStrmCd(dlStrmCd);

		String availablePlayer = StringUtils.lowerCase(metaInfo.getAvailablePlayer());			// 실제 DB값
		String availablePlayerReq = StringUtils.lowerCase(metaInfo.getAvailablePlayerReq()); 	// Request 값
		if(StringUtils.isNotEmpty(metaInfo.getAvailablePlayer())) {
			if (availablePlayer.contains(availablePlayerReq)) {
				usagePolicy.setPlayer(metaInfo.getAvailablePlayerReq());
			}
		}

		/**
		 * vod,이북 컨텐츠 DRM Key setting.
		 * 기존 MDN을 DRM키로 사용하였으나 MDN이 없는 Wi-Fi 전용 단말을 위해 DRM키 정책을 만든다.
		 */
		usagePolicy.setDrmKey(metaInfo.getDrmKey());

        data.setUsagePolicy(usagePolicy);

        // 기기 정보
        deviceKey.setKey(metaInfo.getDeviceKey());
        deviceKey.setType(metaInfo.getDeviceType());
        deviceKey.setSubKey(metaInfo.getDeviceSubKey());
        data.setDeviceKey(deviceKey);

        // 구매내역 숨김 여부
        if ( StringUtils.isNotEmpty(metaInfo.getPurchaseHide()) ) {
            status.setPurchaseHide(metaInfo.getPurchaseHide());
        }
        // 업데이트 알람 수신 여부
        status.setUpdateAlarm(metaInfo.getUpdateAlarm());
        data.setStatus(status);

        // extra : 값의 형식은 "key=value;key2=value2;"로 구성된다. 추후 정의하여 사용.
        data.setExtra(this.makeExtra(metaInfo));

        contents.setData(data);
        return contents;
    }
    
	@Override
	public EncryptionContents generateEncryptionContentsV2(MetaInfo metaInfo,
			boolean supportFhdVideo, boolean unlimitedDrmExpireDt) {
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
				EncryptionSubContents nmSc = this.getEncryptionNmSubContents(metaInfo);
				subContentsList.add(nmSc);
			}
			if (StringUtils.isNotEmpty(metaInfo.getSdSubContsId())) {
				EncryptionSubContents sdSc = this.getEncryptionSdSubContents(metaInfo);
				subContentsList.add(sdSc);
			}
			
			/*
			 * 4.x I/F 일때
			 * update by 2015.08.18 이석희 I-S PLUS
			 */

			if (supportFhdVideo) {
				if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())) {
					EncryptionSubContents hdSc = this.getEncryptionHdSubContentsV2(metaInfo);
					subContentsList.add(hdSc);
				}

				if (StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
					EncryptionSubContents hihdSc = this.getEncryptionHiHdSubContents(metaInfo);
					subContentsList.add(hihdSc);
				}

			} else {
				/*
				 * 고화질 정보(3.x I/F 일때) HD 화질 정보와 HIHD 화질정보 동시에 존재 할때에는 HIHD 화질이 우선적으로 내려가도록
				 * update by 2015.08.18 이석희 I-S PLUS
				 */
				if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())|| StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
					EncryptionSubContents hdSc = this.getEncryptionHdSubContents(metaInfo);
					subContentsList.add(hdSc);
				}
			}			
			
			// HD2 (D화질) 정보 우선, 없으면 HD 정보를 내려줌
//			if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())|| StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
//				EncryptionSubContents hdSc = this.getEncryptionHdSubContents(metaInfo);
//				subContentsList.add(hdSc);
//			}
			// FHD( FHD 화질이 내려가지 않도록 주석처리) update by 2015.08.18 이석희 I-S PLUS
//			if (supportFhdVideo	&& StringUtils.isNotEmpty(metaInfo.getFhdSubContsId())) {
//				EncryptionSubContents fhdSc = this.getEncryptionFhdSubContents(metaInfo);
//				subContentsList.add(fhdSc);
//			}
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
		// 소장 상품이고, 구매내역의 DRM이 Y 이고, 정액권으로 구매한 상품일때 무제한으로 내려준다.
		if (unlimitedDrmExpireDt) {
			date = new Date();
			date.setTextUtc(DateUtils.parseDate("99991231235959"));
			usagePolicy.setExpirationDate(date.getText());
		} else {
			if (StringUtils.isNotEmpty(metaInfo.getUseExprDt())) {
				date = new Date();
				date.setTextUtc(DateUtils.parseDate(metaInfo.getUseExprDt()));
				usagePolicy.setExpirationDate(date.getText());
			} else {
				usagePolicy.setExpirationDate("");
			}
		}
		
		log.info("----------------------------------------------------------------");
		log.info("[DownloadVod EncrytionGeneratorImpl] PurchaseProdId : {} ", data.getProductId());
		log.info("[DownloadVod EncrytionGeneratorImpl] unlimitedDrmExpireDt : {} ", unlimitedDrmExpireDt);
		log.info("[DownloadVod EncrytionGeneratorImpl] exprirationDate : {} ", usagePolicy.getExpirationDate());
		log.info("----------------------------------------------------------------");

		String dlStrmCd = this.getDlStrmCd(metaInfo);
		usagePolicy.setDlStrmCd(dlStrmCd);
		data.setUsagePolicy(usagePolicy);

		// 기기 정보
		deviceKey.setKey(metaInfo.getDeviceKey());
		deviceKey.setType(metaInfo.getDeviceType());
		deviceKey.setSubKey(metaInfo.getDeviceSubKey());
		data.setDeviceKey(deviceKey);

		// 구매내역 숨김 여부
		if (StringUtils.isNotEmpty(metaInfo.getPurchaseHide())) {
			status.setPurchaseHide(metaInfo.getPurchaseHide());
		}
		// 업데이트 알람 수신 여부
		status.setUpdateAlarm(metaInfo.getUpdateAlarm());
		data.setStatus(status);

		// extra : 값의 형식은 "key=value;key2=value2;"로 구성된다. 추후 정의하여 사용.
		data.setExtra(this.makeExtra(metaInfo));

		contents.setData(data);
		return contents;
	}

	private String getDlStrmCd(MetaInfo metaInfo) {
		String dlStrmCdStore=metaInfo.getStoreDlStrmCd();
        String dlStrmCdPlay=metaInfo.getPlayDlStrmCd();
        String dlStrmCd;
        if(StringUtils.isNotEmpty(dlStrmCdStore))
        	dlStrmCd = dlStrmCdStore;
        else if(StringUtils.isNotEmpty(dlStrmCdPlay))
        	dlStrmCd = dlStrmCdPlay;
        else
        	dlStrmCd = ""; // Default 값으로 null이 아닌 ""이 들어가야 한다.
		return dlStrmCd;
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

    /*
     * 4.X I/F 호출시 HD 화질 설정 
     */
    private EncryptionSubContents getEncryptionHdSubContentsV2(MetaInfo metaInfo) {
        EncryptionSubContents hdSc = new EncryptionSubContents();
        if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())) {
	    	hdSc.setType("");
	        hdSc.setDeltaPath("");
	        hdSc.setDeltaSize(0L);
	        hdSc.setSize(Long.parseLong(metaInfo.getHdFileSize()));
	        hdSc.setScid(metaInfo.getHdSubContsId());
	        hdSc.setPath(metaInfo.getHiFilePath());
        }
        
        return hdSc;
    }

    /*
     * 4.X I/F 호출시 HIHD 화질 설정 
     */
    private EncryptionSubContents getEncryptionHiHdSubContents(MetaInfo metaInfo) {
        EncryptionSubContents hihdSc = new EncryptionSubContents();
        if (StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
        	hihdSc.setType("");
        	hihdSc.setDeltaPath("");
        	hihdSc.setDeltaSize(0L);
        	hihdSc.setSize(Long.parseLong(metaInfo.getHihdFileSize()));
        	hihdSc.setScid(metaInfo.getHihdSubContsId());
        	hihdSc.setPath(metaInfo.getHihdFilePath());
        }
        
        return hihdSc;
    }    
    
    /*
     * 3.X I/F 호출시 C(HD), D(HIHD)화질이 동시에 있으면 D화질(HIHD) 우선적으로 내려가도록 설정 
     */
    private EncryptionSubContents getEncryptionHdSubContents(MetaInfo metaInfo) {
        EncryptionSubContents hdSc = new EncryptionSubContents();

        if (StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
        	hdSc.setType("");
            hdSc.setDeltaPath("");
            hdSc.setDeltaSize(0L);
            hdSc.setSize(Long.parseLong(metaInfo.getHihdFileSize()));
            hdSc.setScid(metaInfo.getHihdSubContsId());
            hdSc.setPath(metaInfo.getHihdFilePath());
        } else {
        	hdSc.setType("");
            hdSc.setDeltaPath("");
            hdSc.setDeltaSize(0L);
            hdSc.setSize(Long.parseLong(metaInfo.getHdFileSize()));
            hdSc.setScid(metaInfo.getHdSubContsId());
            hdSc.setPath(metaInfo.getHiFilePath());
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
