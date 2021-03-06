/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 
 * 구매요청 요청 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class CreatePurchaseSacReq extends CommonInfo {
	public interface GroupCreatePurchase {
	}

	public interface GroupCreatePurchaseV2 {
	}

	public interface GroupCreateFreePurchase {
	}

	public interface GroupCreateBizPurchase {
	}

	private static final long serialVersionUID = 201401031L;

	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreateBizPurchase.class,
			GroupCreatePurchaseV2.class })
	private String userKey; // 내부 회원 번호
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreateBizPurchase.class,
			GroupCreatePurchaseV2.class })
	private String deviceKey; // 내부 디바이스 ID
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String opmdNo; // OPMD 번호
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class, GroupCreatePurchaseV2.class })
	private String recvUserKey; // (선물 경우 필수) 수신자 내부 회원 번호
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class, GroupCreatePurchaseV2.class })
	private String recvDeviceKey; // (선물 경우 필수) 수신자 내부 디바이스 ID
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreateBizPurchase.class,
			GroupCreatePurchaseV2.class })
	private String prchsReqPathCd; // 구매 요청 경로 코드
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreateBizPurchase.class,
			GroupCreatePurchaseV2.class })
	private String currencyCd; // 통화 코드
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String saleAmtProcType; // 판매금액 처리 타입: OR020501-일반처리, OR020502-서버기준처리, OR020503-요청기준처리
	@NotNull(groups = { GroupCreatePurchase.class, GroupCreatePurchaseV2.class })
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private Double totAmt; // 총 결제 금액
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreateBizPurchase.class,
			GroupCreatePurchaseV2.class })
	private String clientIp; // 클라이언트 IP
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreateBizPurchase.class,
			GroupCreatePurchaseV2.class })
	private String networkTypeCd; // 네트워크 타입 코드
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreateBizPurchase.class,
			GroupCreatePurchaseV2.class })
	private String prchsCaseCd; // 구매 유형 코드
	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreateBizPurchase.class,
			GroupCreatePurchaseV2.class })
	@Size(min = 20, max = 20, groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class,
			GroupCreateBizPurchase.class })
	private String tenantProdGrpCd; // 테넌트 상품 분류 코드
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String deviceModelCd; // 디바이스 모델 코드
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String telecomCd; // 통신사: US001201-SKT, US001202-KT, US001203-LG U+
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String imei; // 단말 식별 번호
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String uacd; // 단말 모델 식별 번호

	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String sid; // SIM Serial Number
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String flag; // SMS 인증 필요 여부

	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String simNo; // TAKTODO:: 사용제외 / 추후 제거
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String simYn; // TAKTODO:: 사용제외 / 추후 제거
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String smsFlag; // TAKTODO:: 사용제외 / 추후 제거

	@Null(groups = { GroupCreateBizPurchase.class })
	private String mediaId; // CPS CPID
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String offeringId; // 오퍼링 ID
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String chargeMemberId; // 쇼핑 충전권-충전자ID
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String chargeMemberNm; // 쇼핑 충전권-충전자명

	@Null(groups = { GroupCreateBizPurchase.class })
	@NotEmpty(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreatePurchaseV2.class })
	@Valid
	private List<CreatePurchaseSacReqProduct> productList; // 구매할 상품 리스트

	@Null(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class, GroupCreatePurchaseV2.class })
	@NotBlank(groups = { GroupCreateBizPurchase.class })
	private String prodId; // biz상품 ID

	@Null(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	@NotEmpty(groups = { GroupCreateBizPurchase.class })
	@Valid
	private List<PurchaseUserInfo> receiverList; // biz상품 수신자 목록

	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String nmDelivery; // 선물수신자명
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String noMdnDelivery; // 선물수신자 MDN

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the opmdNo
	 */
	public String getOpmdNo() {
		return this.opmdNo;
	}

	/**
	 * @param opmdNo
	 *            the opmdNo to set
	 */
	public void setOpmdNo(String opmdNo) {
		this.opmdNo = opmdNo;
	}

	/**
	 * @return the recvUserKey
	 */
	public String getRecvUserKey() {
		return this.recvUserKey;
	}

	/**
	 * @param recvUserKey
	 *            the recvUserKey to set
	 */
	public void setRecvUserKey(String recvUserKey) {
		this.recvUserKey = recvUserKey;
	}

	/**
	 * @return the recvDeviceKey
	 */
	public String getRecvDeviceKey() {
		return this.recvDeviceKey;
	}

	/**
	 * @param recvDeviceKey
	 *            the recvDeviceKey to set
	 */
	public void setRecvDeviceKey(String recvDeviceKey) {
		this.recvDeviceKey = recvDeviceKey;
	}

	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	/**
	 * @param prchsReqPathCd
	 *            the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	/**
	 * @return the currencyCd
	 */
	public String getCurrencyCd() {
		return this.currencyCd;
	}

	/**
	 * @param currencyCd
	 *            the currencyCd to set
	 */
	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	/**
	 * @return the saleAmtProcType
	 */
	public String getSaleAmtProcType() {
		return this.saleAmtProcType;
	}

	/**
	 * @param saleAmtProcType
	 *            the saleAmtProcType to set
	 */
	public void setSaleAmtProcType(String saleAmtProcType) {
		this.saleAmtProcType = saleAmtProcType;
	}

	/**
	 * @return the totAmt
	 */
	public Double getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(Double totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return this.clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the networkTypeCd
	 */
	public String getNetworkTypeCd() {
		return this.networkTypeCd;
	}

	/**
	 * @param networkTypeCd
	 *            the networkTypeCd to set
	 */
	public void setNetworkTypeCd(String networkTypeCd) {
		this.networkTypeCd = networkTypeCd;
	}

	/**
	 * @return the prchsCaseCd
	 */
	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	/**
	 * @param prchsCaseCd
	 *            the prchsCaseCd to set
	 */
	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}

	/**
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the telecomCd
	 */
	public String getTelecomCd() {
		return this.telecomCd;
	}

	/**
	 * @param telecomCd
	 *            the telecomCd to set
	 */
	public void setTelecomCd(String telecomCd) {
		this.telecomCd = telecomCd;
	}

	/**
	 * @return the imei
	 */
	public String getImei() {
		return this.imei;
	}

	/**
	 * @param imei
	 *            the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return the uacd
	 */
	public String getUacd() {
		return this.uacd;
	}

	/**
	 * @param uacd
	 *            the uacd to set
	 */
	public void setUacd(String uacd) {
		this.uacd = uacd;
	}

	/**
	 * @return the sid
	 */
	public String getSid() {
		return this.sid;
	}

	/**
	 * @param sid
	 *            the sid to set
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return this.flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the simNo
	 */
	public String getSimNo() {
		return this.simNo;
	}

	/**
	 * @param simNo
	 *            the simNo to set
	 */
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	/**
	 * @return the simYn
	 */
	public String getSimYn() {
		return this.simYn;
	}

	/**
	 * @param simYn
	 *            the simYn to set
	 */
	public void setSimYn(String simYn) {
		this.simYn = simYn;
	}

	/**
	 * @return the smsFlag
	 */
	public String getSmsFlag() {
		return this.smsFlag;
	}

	/**
	 * @param smsFlag
	 *            the smsFlag to set
	 */
	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}

	/**
	 * @return the offeringId
	 */
	public String getOfferingId() {
		return this.offeringId;
	}

	/**
	 * @param offeringId
	 *            the offeringId to set
	 */
	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	/**
	 * @return the productList
	 */
	public List<CreatePurchaseSacReqProduct> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<CreatePurchaseSacReqProduct> productList) {
		this.productList = productList;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the receiverList
	 */
	public List<PurchaseUserInfo> getReceiverList() {
		return this.receiverList;
	}

	/**
	 * @param receiverList
	 *            the receiverList to set
	 */
	public void setReceiverList(List<PurchaseUserInfo> receiverList) {
		this.receiverList = receiverList;
	}

	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return this.mediaId;
	}

	/**
	 * @param mediaId
	 *            the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * @return the nmDelivery
	 */
	public String getNmDelivery() {
		return this.nmDelivery;
	}

	/**
	 * @param nmDelivery
	 *            the nmDelivery to set
	 */
	public void setNmDelivery(String nmDelivery) {
		this.nmDelivery = nmDelivery;
	}

	/**
	 * @return the noMdnDelivery
	 */
	public String getNoMdnDelivery() {
		return this.noMdnDelivery;
	}

	/**
	 * @param noMdnDelivery
	 *            the noMdnDelivery to set
	 */
	public void setNoMdnDelivery(String noMdnDelivery) {
		this.noMdnDelivery = noMdnDelivery;
	}

	/**
	 * Gets charge member id.
	 *
	 * @return the charge member id
	 */
	public String getChargeMemberId() {
		return chargeMemberId;
	}

	/**
	 * Sets charge member id.
	 *
	 * @param chargeMemberId
	 *            the charge member id
	 */
	public void setChargeMemberId(String chargeMemberId) {
		this.chargeMemberId = chargeMemberId;
	}

	/**
	 * Gets charge member nm.
	 *
	 * @return the charge member nm
	 */
	public String getChargeMemberNm() {
		return chargeMemberNm;
	}

	/**
	 * Sets charge member nm.
	 *
	 * @param chargeMemberNm
	 *            the charge member nm
	 */
	public void setChargeMemberNm(String chargeMemberNm) {
		this.chargeMemberNm = chargeMemberNm;
	}
}
