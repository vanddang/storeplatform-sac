/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Pay Planet 결제 Page 요청 파라미터 VO
 * 
 * Updated on : 2014. 1. 24. Updated by : 이승택, nTels.
 */
public class PaymentPageParam extends CommonInfo {
	public static final String PP_RETURN_FORMAT_JSON = "1";

	private static final long serialVersionUID = 201401241L;

	private String version;
	private String token; // MD5( AuthKey+OrderID+MctTrDate+AmtPurchase+MID )
	private String eData;

	private String authKey; // 가맹점 인증키

	private String mid; // 가맹점ID
	private String orderId; // 가맹점 주문번호
	private String mctTrDate; // 결제요청일시
	private String amtPurchase; // 결제요청금액
	private String pid; // 가맹점 상품ID
	private String pName; // 가맹점 상품명
	private String pDescription; // 가맹점 상품설명
	private String pType; // 가맹점 상품유형
	private String aid; // 가맹점AppID
	private String returnFormat; // 결제결과 수신포맷 (0: HttpServletResponse, 1: JSON)
	private String flgMchtAuth; // 가맹점 인증의뢰여부
	private String returnPath; // 결제화면전환Path
	private String resultPath; // 결제결과통보Path
	private String mctSpareParam; // 가맹점용 파라미터
	private String mdn; // 단말번호
	private String nmDevice; // 단말명
	private String imei; // 단말식별번호
	private String uacd; // 단말모델식별번호
	private String typeNetwork; // 결제요청Network (1: 3G(LTE) , 2:Wifi)
	private String carrier; // 통신사 (1: SKT , 2: LGU, 3: KT)
	private String noSim; // SIM Serial Number
	private String flgSim; // SIM 조회가능여부

	// ==========================================================================================

	/**
	 * 
	 * <pre>
	 * Token 데이터 구성: AuthKey+OrderID+MctTrDate+AmtPurchase+MID.
	 * </pre>
	 * 
	 * @return Token 데이터
	 */
	public String makeTokenFormat() {
		StringBuffer sb = new StringBuffer(128);

		sb.append(this.authKey).append(this.orderId).append(this.mctTrDate).append(this.amtPurchase).append(this.mid);

		return sb.toString();
	}

	/**
	 * 
	 * <pre>
	 * 암호화 할 데이터 구성.
	 * </pre>
	 * 
	 * @return 암호화 할 데이터 문자열
	 */
	public String makeEncDataFormat() {
		StringBuffer sb = new StringBuffer(1024);

		sb.append("mid:").append(this.mid).append(";orderId:").append(this.orderId).append(";mctTrDate:")
				.append(this.mctTrDate).append(";amtPurchase:").append(this.amtPurchase).append(";pid:")
				.append(this.pid).append(";pName:").append(this.pName).append(";pDescription:")
				.append(this.pDescription).append(";pType:").append(this.pType).append(";aid:").append(this.aid)
				.append(";returnFormat:").append(this.returnFormat).append(";flgMchtAuth:").append(this.flgMchtAuth)
				.append(";returnPath:").append(this.returnPath).append(";resultPath:").append(this.resultPath)
				.append(";mctSpareParam:").append(this.mctSpareParam).append(";mdn:").append(this.mdn)
				.append(";nmDevice:").append(this.nmDevice).append(";imei:").append(this.imei).append(";uacd:")
				.append(this.uacd).append(";typeNetwork:").append(this.typeNetwork).append(";carrier:")
				.append(this.carrier).append(";noSim:").append(this.noSim).append(";flgSim:").append(this.flgSim);

		return sb.toString();
	}

	// ==========================================================================================

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the eData
	 */
	public String geteData() {
		return this.eData;
	}

	/**
	 * @param eData
	 *            the eData to set
	 */
	public void seteData(String eData) {
		this.eData = eData;
	}

	/**
	 * @return the authKey
	 */
	public String getAuthKey() {
		return this.authKey;
	}

	/**
	 * @param authKey
	 *            the authKey to set
	 */
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	/**
	 * @return the mid
	 */
	public String getMid() {
		return this.mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return this.orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the mctTrDate
	 */
	public String getMctTrDate() {
		return this.mctTrDate;
	}

	/**
	 * @param mctTrDate
	 *            the mctTrDate to set
	 */
	public void setMctTrDate(String mctTrDate) {
		this.mctTrDate = mctTrDate;
	}

	/**
	 * @return the amtPurchase
	 */
	public String getAmtPurchase() {
		return this.amtPurchase;
	}

	/**
	 * @param amtPurchase
	 *            the amtPurchase to set
	 */
	public void setAmtPurchase(String amtPurchase) {
		this.amtPurchase = amtPurchase;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return this.pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the pName
	 */
	public String getpName() {
		return this.pName;
	}

	/**
	 * @param pName
	 *            the pName to set
	 */
	public void setpName(String pName) {
		this.pName = pName;
	}

	/**
	 * @return the pDescription
	 */
	public String getpDescription() {
		return this.pDescription;
	}

	/**
	 * @param pDescription
	 *            the pDescription to set
	 */
	public void setpDescription(String pDescription) {
		this.pDescription = pDescription;
	}

	/**
	 * @return the pType
	 */
	public String getpType() {
		return this.pType;
	}

	/**
	 * @param pType
	 *            the pType to set
	 */
	public void setpType(String pType) {
		this.pType = pType;
	}

	/**
	 * @return the aid
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * @return the returnFormat
	 */
	public String getReturnFormat() {
		return this.returnFormat;
	}

	/**
	 * @param returnFormat
	 *            the returnFormat to set
	 */
	public void setReturnFormat(String returnFormat) {
		this.returnFormat = returnFormat;
	}

	/**
	 * @return the flgMchtAuth
	 */
	public String getFlgMchtAuth() {
		return this.flgMchtAuth;
	}

	/**
	 * @param flgMchtAuth
	 *            the flgMchtAuth to set
	 */
	public void setFlgMchtAuth(String flgMchtAuth) {
		this.flgMchtAuth = flgMchtAuth;
	}

	/**
	 * @return the returnPath
	 */
	public String getReturnPath() {
		return this.returnPath;
	}

	/**
	 * @param returnPath
	 *            the returnPath to set
	 */
	public void setReturnPath(String returnPath) {
		this.returnPath = returnPath;
	}

	/**
	 * @return the resultPath
	 */
	public String getResultPath() {
		return this.resultPath;
	}

	/**
	 * @param resultPath
	 *            the resultPath to set
	 */
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	/**
	 * @return the mctSpareParam
	 */
	public String getMctSpareParam() {
		return this.mctSpareParam;
	}

	/**
	 * @param mctSpareParam
	 *            the mctSpareParam to set
	 */
	public void setMctSpareParam(String mctSpareParam) {
		this.mctSpareParam = mctSpareParam;
	}

	/**
	 * @return the mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return the nmDevice
	 */
	public String getNmDevice() {
		return this.nmDevice;
	}

	/**
	 * @param nmDevice
	 *            the nmDevice to set
	 */
	public void setNmDevice(String nmDevice) {
		this.nmDevice = nmDevice;
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
	 * @return the typeNetwork
	 */
	public String getTypeNetwork() {
		return this.typeNetwork;
	}

	/**
	 * @param typeNetwork
	 *            the typeNetwork to set
	 */
	public void setTypeNetwork(String typeNetwork) {
		this.typeNetwork = typeNetwork;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return this.carrier;
	}

	/**
	 * @param carrier
	 *            the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return the noSim
	 */
	public String getNoSim() {
		return this.noSim;
	}

	/**
	 * @param noSim
	 *            the noSim to set
	 */
	public void setNoSim(String noSim) {
		this.noSim = noSim;
	}

	/**
	 * @return the flgSim
	 */
	public String getFlgSim() {
		return this.flgSim;
	}

	/**
	 * @param flgSim
	 *            the flgSim to set
	 */
	public void setFlgSim(String flgSim) {
		this.flgSim = flgSim;
	}

}
