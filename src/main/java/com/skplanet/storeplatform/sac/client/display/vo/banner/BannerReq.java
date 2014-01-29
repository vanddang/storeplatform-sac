package com.skplanet.storeplatform.sac.client.display.vo.banner;

public class BannerReq {
	private String dummy;
	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템 ID
	private String bnrGrpId; // 배너 그룹 ID
	private String bnrTopCd; // 탑 코드
	private String imgCaseCd; // 이미지_유형_코드
	private String rsltnSize; // 해상도
	private String prodClsf; // 상품_구분(전체:ALL, App:APP, VOD:VOD, 이북:EBOOK, 코믹:COMMIC, 코믹(채널):CO_CHANEL,
							 // 코믹(시리즈):CO_SERISE, 잡지:MAGAZINE, 뮤직:MUSIC)
	private String deviceModelCd; // 기기_모델_코드
	private String prodId; // 상품_ID
	private String resvExpoYn; // 예비_노출_여부
	private String langCd; // 언어 코드
	private int offset;
	private int count;

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getBnrGrpId() {
		return this.bnrGrpId;
	}

	public void setBnrGrpId(String bnrGrpId) {
		this.bnrGrpId = bnrGrpId;
	}

	public String getBnrTopCd() {
		return this.bnrTopCd;
	}

	public void setBnrTopCd(String bnrTopCd) {
		this.bnrTopCd = bnrTopCd;
	}

	public String getImgCaseCd() {
		return this.imgCaseCd;
	}

	public void setImgCaseCd(String imgCaseCd) {
		this.imgCaseCd = imgCaseCd;
	}

	public String getRsltnSize() {
		return this.rsltnSize;
	}

	public void setRsltnSize(String rsltnSize) {
		this.rsltnSize = rsltnSize;
	}

	public String getProdClsf() {
		return this.prodClsf;
	}

	public void setProdClsf(String prodClsf) {
		this.prodClsf = prodClsf;
	}

	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	public String getResvExpoYn() {
		return this.resvExpoYn;
	}

	public void setResvExpoYn(String resvExpoYn) {
		this.resvExpoYn = resvExpoYn;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

}
