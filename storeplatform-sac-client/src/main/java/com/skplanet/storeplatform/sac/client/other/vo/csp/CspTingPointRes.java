package com.skplanet.storeplatform.sac.client.other.vo.csp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * CSP Ting Point 연동 Value Object
 *
 * Updated on : 2015. 8. 12. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CspTingPointRes {

    private static final long serialVersionUID = 1L;

    /**
     * 잔여 데이타량.
     */
    private String chargeBalance;
    /**
     * 단위. (원, 초, 건, KB)
     */
    private String chargeUnit;

    public String getChargeBalance() {
        return chargeBalance;
    }

    public void setChargeBalance(String chargeBalance) {
        this.chargeBalance = chargeBalance;
    }

    public String getChargeUnit() {
        return chargeUnit;
    }

    public void setChargeUnit(String chargeUnit) {
        this.chargeUnit = chargeUnit;
    }
}
