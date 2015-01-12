package com.skplanet.storeplatform.sac.display.product.vo;

public class ProductVo {
	
	/** 상품_아이디 */
	private String prodId;
	/** 서브_상품_아이디 */
	private String subContsId;
	/** 테넌트_아이디 */
	private String tenantId;	
	/** 상품_상태_코드 */
	private String prodStatCd;
	/** 회원_아이디 */
	private String mbrId;
	/** 회원_번호 */
	private String mbrNo;
	/** 배포_여부 */
	private String deployYn;
	/** 생성 일자 */
	private String regDt;
	
	
	
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getSubContsId() {
		return subContsId;
	}
	public void setSubContsId(String subContsId) {
		this.subContsId = subContsId;
	}
	public String getProdStatCd() {
		return prodStatCd;
	}
	public void setProdStatCd(String prodStatCd) {
		this.prodStatCd = prodStatCd;
	}
	public String getMbrId() {
		return mbrId;
	}
	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}
	public String getMbrNo() {
		return mbrNo;
	}
	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}
	public String getDeployYn() {
		return deployYn;
	}
	public void setDeployYn(String deployYn) {
		this.deployYn = deployYn;
	}
	
}