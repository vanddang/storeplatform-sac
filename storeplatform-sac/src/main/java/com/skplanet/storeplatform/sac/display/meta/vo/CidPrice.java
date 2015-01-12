/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * CidPrice
 * prodId, rentProdId는 에피소드ID
 * </p>
 * Updated on : 2015. 01. 02 Updated by : 정희원, SK 플래닛.
 */
public class CidPrice extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String prodId;
    private Integer prodAmt;
    private String rentProdId;
    private Integer rentPeriod;
    private String rendPeriodUnitCd;
    private Integer rentProdAmt;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Integer getProdAmt() {
        return prodAmt;
    }

    public void setProdAmt(Integer prodAmt) {
        this.prodAmt = prodAmt;
    }

    public String getRentProdId() {
        return rentProdId;
    }

    public void setRentProdId(String rentProdId) {
        this.rentProdId = rentProdId;
    }

    public Integer getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(Integer rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public String getRendPeriodUnitCd() {
        return rendPeriodUnitCd;
    }

    public void setRendPeriodUnitCd(String rendPeriodUnitCd) {
        this.rendPeriodUnitCd = rendPeriodUnitCd;
    }

    public Integer getRentProdAmt() {
        return rentProdAmt;
    }

    public void setRentProdAmt(Integer rentProdAmt) {
        this.rentProdAmt = rentProdAmt;
    }

    public static class CidPriceRaw {
        private String prodId;
        private Integer usePeriod;
        private String usePeriodUnitCd;
        private Integer prodAmt;

        public String getProdId() {
            return prodId;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }

        public Integer getUsePeriod() {
            return usePeriod;
        }

        public void setUsePeriod(Integer usePeriod) {
            this.usePeriod = usePeriod;
        }

        public String getUsePeriodUnitCd() {
            return usePeriodUnitCd;
        }

        public void setUsePeriodUnitCd(String usePeriodUnitCd) {
            this.usePeriodUnitCd = usePeriodUnitCd;
        }

        public Integer getProdAmt() {
            return prodAmt;
        }

        public void setProdAmt(Integer prodAmt) {
            this.prodAmt = prodAmt;
        }
    }
}
