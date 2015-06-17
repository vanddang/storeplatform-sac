package com.skplanet.storeplatform.sac.client.display.vo.related;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RelatedProductSacReq {
    @NotNull @NotBlank
    private String productId;

    @NotNull
    @Pattern(regexp = "DP01310001")
    private String relatedType;

    private String prodGradeCd;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType;
    }

    public String getProdGradeCd() {
        return prodGradeCd;
    }

    public void setProdGradeCd(String prodGradeCd) {
        this.prodGradeCd = prodGradeCd;
    }
}
