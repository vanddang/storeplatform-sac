/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.vo;

import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;

/**
 * <p>
 * PaymentInfoContext
 * </p>
 * Updated on : 2015. 07. 14 Updated by : 정희원, SK 플래닛.
 */
public class PaymentInfoContext {

    private String tenantId;
    private String langCd;
    private String deviceModelCd;
    private String ebookSprtYn = "Y";
    private String comicSprtYn = "Y";
    private String musicSprtYn = "Y";
    private String videoDrmSprtYn = "Y";
    private String sdVideoSprtYn = "Y";
    private String sclShpgSprtYn = "Y";
    private String vodFixisttSprtYn = "Y";


    public PaymentInfoContext(String tenantId, String langCd, String deviceModelCd, SupportDevice sprtDev) {
        this.tenantId = tenantId;
        this.langCd = langCd;
        this.deviceModelCd = deviceModelCd;

        if (sprtDev != null) {
            this.ebookSprtYn = sprtDev.getEbookSprtYn();
            this.comicSprtYn = sprtDev.getComicSprtYn();
            this.musicSprtYn = sprtDev.getMusicSprtYn();
            this.videoDrmSprtYn = sprtDev.getVideoDrmSprtYn();
            this.sdVideoSprtYn = sprtDev.getSdVideoSprtYn();
            this.sclShpgSprtYn = sprtDev.getSclShpgSprtYn();
            this.vodFixisttSprtYn = sprtDev.getVodFixisttSprtYn();
        }
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getLangCd() {
        return langCd;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public String getEbookSprtYn() {
        return ebookSprtYn;
    }

    public String getComicSprtYn() {
        return comicSprtYn;
    }

    public String getMusicSprtYn() {
        return musicSprtYn;
    }

    public String getVideoDrmSprtYn() {
        return videoDrmSprtYn;
    }

    public String getSdVideoSprtYn() {
        return sdVideoSprtYn;
    }

    public String getSclShpgSprtYn() {
        return sclShpgSprtYn;
    }

    public String getVodFixisttSprtYn() {
        return vodFixisttSprtYn;
    }
}
