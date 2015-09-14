/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import java.util.Date;

/**
 * <p>
 * GetPromotionEventParam
 * - nowDt를 입력하지 않으면 메소드에서 직접 DB에서 시간을 조회합니다.
 * </p>
 * Updated on : 2015. 07. 15 Updated by : 정희원, SK 플래닛.
 */
public class GetPromotionEventParam {

    private String tenantId;
    private String menuId;
    private String chnlId;
    private Date nowDt;
    private boolean useDb = false;

    public GetPromotionEventParam() {}

    public GetPromotionEventParam(String tenantId, String menuId, String chnlId) {
        this.tenantId = tenantId;
        this.menuId = menuId;
        this.chnlId = chnlId;
    }

    public GetPromotionEventParam(String tenantId, String menuId, String chnlId, boolean useDb) {
        this.tenantId = tenantId;
        this.menuId = menuId;
        this.chnlId = chnlId;
        this.useDb = useDb;
    }

    public GetPromotionEventParam(String tenantId, String menuId, String chnlId, Date nowDt) {
        this.tenantId = tenantId;
        this.menuId = menuId;
        this.chnlId = chnlId;
        this.nowDt = nowDt;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getChnlId() {
        return chnlId;
    }

    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    public Date getNowDt() {
        return nowDt;
    }

    public void setNowDt(Date nowDt) {
        this.nowDt = nowDt;
    }

    public boolean isUseDb() {
        return useDb;
    }

    public void setUseDb(boolean useDb) {
        this.useDb = useDb;
    }
}
