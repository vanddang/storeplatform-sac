package com.skplanet.storeplatform.sac.client.purchase.vo.purchase;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class PurchaseProduct extends CommonInfo {
	private static final long serialVersionUID = 201401031L;

	private String prodId; // 상품 ID
	private Double prodAmt; // 상품 가격
	private Integer prodQty; // 상품 수량
	private String tenantProdGrpCd; // 테넌트 상품그룹 코드

	public PurchaseProduct() {
	}

	public PurchaseProduct(String prodId, Double prodAmt, Integer prodQty, String tenantProdGrpCd) {
		this.prodId = prodId;
		this.prodAmt = prodAmt;
		this.prodQty = prodQty;
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public Double getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	public Integer getProdQty() {
		return this.prodQty;
	}

	public void setProdQty(Integer prodQty) {
		this.prodQty = prodQty;
	}

	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}
}
