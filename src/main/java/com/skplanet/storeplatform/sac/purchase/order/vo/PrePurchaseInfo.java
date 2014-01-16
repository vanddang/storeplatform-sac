package com.skplanet.storeplatform.sac.purchase.order.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;

public class PrePurchaseInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private final CreatePurchaseReq createPurchaseReq;

	private String resultType; // 결과 타입: payment-결제Page 요청진행, free-무료구매 완료
	private String paymentPageUrl; // 결제Page_URL
	private String paymentPageParam; // 결제Page_요청_파라미터

	private double realTotAmt; // 최종 결제 총 금액

	private DummyMember purchaseMember;
	private DummyMember recvMember;

	private String deviceModelCd;
	private List<DummyProduct> productList;

	private String tenantId;
	private String systemId;
	private String insdUsermbrNo;
	private String insdDeviceId;
	private String recvTenantId;
	private String recvInsdUsermbrNo;
	private String recvInsdDeviceId;

	private boolean bBlock; // 구매 차단 여부
	private boolean bTestMdn; // 스토어 테스트폰 여부
	private boolean bSktTest; // SKT 시험폰 여부
	private boolean bSktCorp; // SKT 법인폰 여부
	private boolean bSkpCorp; // SKP 법인폰 여부

	// ================================================================================================

	public PrePurchaseInfo(CreatePurchaseReq createPurchaseReq) {
		this.createPurchaseReq = createPurchaseReq;
		this.realTotAmt = createPurchaseReq.getTotAmt();
	}

	// ================================================================================================

	public String getResultType() {
		return this.resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getPaymentPageUrl() {
		return this.paymentPageUrl;
	}

	public void setPaymentPageUrl(String paymentPageUrl) {
		this.paymentPageUrl = paymentPageUrl;
	}

	public String getPaymentPageParam() {
		return this.paymentPageParam;
	}

	public void setPaymentPageParam(String paymentPageParam) {
		this.paymentPageParam = paymentPageParam;
	}

	public double getRealTotAmt() {
		return this.realTotAmt;
	}

	public void setRealTotAmt(double realTotAmt) {
		this.realTotAmt = realTotAmt;
	}

	public DummyMember getPurchaseMember() {
		return this.purchaseMember;
	}

	public void setPurchaseMember(DummyMember purchaseMember) {
		this.purchaseMember = purchaseMember;
	}

	public DummyMember getRecvMember() {
		return this.recvMember;
	}

	public void setRecvMember(DummyMember recvMember) {
		this.recvMember = recvMember;
	}

	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public List<DummyProduct> getProductList() {
		return this.productList;
	}

	public void setProductList(List<DummyProduct> productList) {
		this.productList = productList;
	}

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

	public boolean isbBlock() {
		return this.bBlock;
	}

	public void setbBlock(boolean bBlock) {
		this.bBlock = bBlock;
	}

	public boolean isbTestMdn() {
		return this.bTestMdn;
	}

	public void setbTestMdn(boolean bTestMdn) {
		this.bTestMdn = bTestMdn;
	}

	public boolean isbSktTest() {
		return this.bSktTest;
	}

	public void setbSktTest(boolean bSktTest) {
		this.bSktTest = bSktTest;
	}

	public boolean isbSktCorp() {
		return this.bSktCorp;
	}

	public void setbSktCorp(boolean bSktCorp) {
		this.bSktCorp = bSktCorp;
	}

	public boolean isbSkpCorp() {
		return this.bSkpCorp;
	}

	public void setbSkpCorp(boolean bSkpCorp) {
		this.bSkpCorp = bSkpCorp;
	}

	public CreatePurchaseReq getCreatePurchaseReq() {
		return this.createPurchaseReq;
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

}
