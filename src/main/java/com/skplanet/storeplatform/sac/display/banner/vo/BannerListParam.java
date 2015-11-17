/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.banner.vo;

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerListSacReq;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Updated on : 2015-06-30. Updated by : 양해엽, SK Planet.
 */
public class BannerListParam {

    private static final int DEFAULT_BNR_COUNT = 20;
    private static final int MAX_BNR_COUNT = 100;
    private static final int MIN_EXPO_ORD = -99999999;

	private static final Set<String> bannerDeviceProvision =
			new HashSet<String>(Arrays.asList("DP010920", "DP010921", "DP010928", "DP010932", "DP010933", "DP010934"));

    private String tenantId;
    private String bnrMenuId;
    private String bnrExpoMenuId;
    private String imgSizeCd;
    private String deviceModelCd;
    private String anyDeviceModelCd;
    private String langCd;
    private Integer lastExpoOrd;
    private Integer lastExpoOrdSub;
    private Integer count;
    private Integer maxBnrCount;
	private String useGrdCd;
	private boolean isDvcProvision;

    public BannerListParam(BannerListSacReq req, String tenantId, String deviceModelCd, String langCd) {
        this.tenantId = tenantId;
        this.deviceModelCd = deviceModelCd;
        this.langCd = langCd;

        this.bnrMenuId = req.getBnrMenuId();
        this.bnrExpoMenuId = req.getBnrExpoMenuId();
        this.imgSizeCd = req.getImgSizeCd();

        this.anyDeviceModelCd = DisplayConstants.DP_ANY_PHONE_4MM;
        this.maxBnrCount = MAX_BNR_COUNT;

		this.useGrdCd = req.getUseGrdCd();

		this.isDvcProvision = bannerDeviceProvision.contains(req.getBnrMenuId());

        setupOrders(req);
        setCount(req);
    }

    private void setupOrders(BannerListSacReq req) {
        String startKey = req.getStartKey();

        if(startKey == null || startKey.isEmpty()) {
            lastExpoOrd    = MIN_EXPO_ORD;
            lastExpoOrdSub = MIN_EXPO_ORD;
        } else {
            String[] keys = startKey.split("/");
            lastExpoOrd    = Integer.valueOf(keys[0]);
            lastExpoOrdSub = Integer.valueOf(keys[1]);
        }
    }

    private void setCount(BannerListSacReq req) {
        count = req.getCount() == null ? DEFAULT_BNR_COUNT : req.getCount();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBnrMenuId() {
        return bnrMenuId;
    }

    public void setBnrMenuId(String bnrMenuId) {
        this.bnrMenuId = bnrMenuId;
    }

    public String getBnrExpoMenuId() {
        return bnrExpoMenuId;
    }

    public void setBnrExpoMenuId(String bnrExpoMenuId) {
        this.bnrExpoMenuId = bnrExpoMenuId;
    }

    public String getImgSizeCd() {
        return imgSizeCd;
    }

    public void setImgSizeCd(String imgSizeCd) {
        this.imgSizeCd = imgSizeCd;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }

    public String getAnyDeviceModelCd() {
        return anyDeviceModelCd;
    }

    public void setAnyDeviceModelCd(String anyDeviceModelCd) {
        this.anyDeviceModelCd = anyDeviceModelCd;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public Integer getLastExpoOrd() {
        return lastExpoOrd;
    }

    public void setLastExpoOrd(Integer lastExpoOrd) {
        this.lastExpoOrd = lastExpoOrd;
    }

    public Integer getLastExpoOrdSub() {
        return lastExpoOrdSub;
    }

    public void setLastExpoOrdSub(Integer lastExpoOrdSub) {
        this.lastExpoOrdSub = lastExpoOrdSub;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMaxBnrCount() {
        return maxBnrCount;
    }

    public void setMaxBnrCount(Integer maxBnrCount) {
        this.maxBnrCount = maxBnrCount;
    }

	public String getUseGrdCd() {
		return useGrdCd;
	}

	public void setUseGrdCd(String useGrdCd) {
		this.useGrdCd = useGrdCd;
	}

	public boolean isDvcProvision() {
		return isDvcProvision;
	}
}
