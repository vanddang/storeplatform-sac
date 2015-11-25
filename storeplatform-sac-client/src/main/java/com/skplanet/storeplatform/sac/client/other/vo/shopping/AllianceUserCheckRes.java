package com.skplanet.storeplatform.sac.client.other.vo.shopping;

/**
 * 제휴사 회원 유효성 체크 Value Object
 *
 * Updated on : 2015. 10. 07. Updated by : 심대진, 다모아 솔루션.
 */
public class AllianceUserCheckRes {

    private static final long serialVersionUID = 1L;

    /**
     * 쇼핑 브랜드ID.
     */
    private String brandId;
    /**
     * 쇼핑 CMD 브랜드ID.
     */
    private String cmsBrandId;
    /**
     * 제휴사 회원 ID.
     */
    private String chargeId;
    /**
     * 제휴사 회원명.
     */
    private String chargeNm;
    /**
     * 제휴사 회원 여부.
     * - Y : 제휴사 회원
     * - N : 제휴사 회원 아님
     */
    private String allianceUserYn;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCmsBrandId() {
        return cmsBrandId;
    }

    public void setCmsBrandId(String cmsBrandId) {
        this.cmsBrandId = cmsBrandId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getChargeNm() {
        return chargeNm;
    }

    public void setChargeNm(String chargeNm) {
        this.chargeNm = chargeNm;
    }

    public String getAllianceUserYn() {
        return allianceUserYn;
    }

    public void setAllianceUserYn(String allianceUserYn) {
        this.allianceUserYn = allianceUserYn;
    }
}
