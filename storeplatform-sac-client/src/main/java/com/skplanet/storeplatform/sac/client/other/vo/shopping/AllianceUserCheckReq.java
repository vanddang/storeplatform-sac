package com.skplanet.storeplatform.sac.client.other.vo.shopping;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 제휴사 회원 유효성 체크 Value Object
 *
 * Updated on : 2015. 10. 07. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AllianceUserCheckReq {

    private static final long serialVersionUID = 1L;

    /**
     * 쇼핑 브랜드 ID.
     */
    @NotBlank
    private String brandId;
    /**
     * 쇼핑 CMD 브랜드ID.
     */
    @NotBlank
    private String cmsBrandId;
    /**
     * 브랜드 판매사ID.
     */
    @NotBlank
    private String brandSellerId;
    /**
     * 구매자 MDN.
     */
    @NotBlank
    @Pattern(regexp = "(01[0|1|6|7|8|9])(\\d{3,4})(\\d{4})")
    private String deviceId;
    /**
     * 제휴사 회원 ID.
     */
    @NotBlank
    private String chargeId;

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

    public String getBrandSellerId() {
        return brandSellerId;
    }

    public void setBrandSellerId(String brandSellerId) {
        this.brandSellerId = brandSellerId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }
}
