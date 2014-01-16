package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CreatePurchaseReq extends CommonInfo {
	private static final long serialVersionUID = 201401031L;

	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템 ID
	private String insdUsermbrNo; // 내부 회원 번호
	private String insdDeviceId; // 내부 디바이스 ID
	private String prchsReqPathCd; // 구매 요청 경로 코드
	private String mid; // 가맹점 ID
	private String authKey; // 가맹점 인증키
	private String resultUrl; // 결과처리 URL
	private String currencyCd; // 통화 코드
	private Double totAmt; // 총 결제 금액
	private String clientIp; // 클라이언트 IP
	private String networkTypeCd; // 네트워크 타입 코드
	private String prchsCaseCd; // 구매 유형 코드
	private String recvTenantId; // 수신자 테넌트 ID
	private String recvInsdUsermbrNo; // 수신자 내부 회원 번호
	private String recvInsdDeviceId; // 수신자 내부 디바이스 ID
	private List<PurchaseProduct> productList; // 구매할 상품 리스트

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

	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAuthKey() {
		return this.authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getResultUrl() {
		return this.resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}

	public String getCurrencyCd() {
		return this.currencyCd;
	}

	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	public Double getTotAmt() {
		return this.totAmt;
	}

	public void setTotAmt(Double totAmt) {
		this.totAmt = totAmt;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getNetworkTypeCd() {
		return this.networkTypeCd;
	}

	public void setNetworkTypeCd(String networkTypeCd) {
		this.networkTypeCd = networkTypeCd;
	}

	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}

	public String getRecvTenantId() {
		return this.recvTenantId;
	}

	public void setRecvTenantId(String recvTenantId) {
		this.recvTenantId = recvTenantId;
	}

	public String getRecvInsdUsermbrNo() {
		return this.recvInsdUsermbrNo;
	}

	public void setRecvInsdUsermbrNo(String recvInsdUsermbrNo) {
		this.recvInsdUsermbrNo = recvInsdUsermbrNo;
	}

	public String getRecvInsdDeviceId() {
		return this.recvInsdDeviceId;
	}

	public void setRecvInsdDeviceId(String recvInsdDeviceId) {
		this.recvInsdDeviceId = recvInsdDeviceId;
	}

	public List<PurchaseProduct> getProductList() {
		return this.productList;
	}

	public void setProductList(List<PurchaseProduct> productList) {
		this.productList = productList;
	}
}
