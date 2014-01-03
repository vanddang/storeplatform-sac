package com.skplanet.storeplatform.sac.client.member.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 공통 헤더 정보
 * 
 * Updated on : 2014. 1. 3.
 * Updated by : 심대진, 다모아 솔루션.
 */
public class HeaderVo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * Definitions	: Specify certain media types which are acceptable for the response
	 * Necessity	: Required
	 * Description	: Response Body의 데이터 포멧을 정의한다. (json만 지원)
	 * Example	    : application/json; charset=UTF-8
	 */
	private String accept;
	
	/**
	 * Definitions	: restricts the set of natural languages that are preferred as a response to the request
	 * Necessity	: Required
	 * Description	: Response Body의 데이터에서 사용될 언어를 정의한다.
	 * Example	    : ko-KR,ko
	 *                en-US,en
	 */
	private String acceptLanguage;

	/**
	 * Definitions	: Request Body Content-Length
	 * Necessity	: Required (GET Method : Optional, POST Method : Required)
	 * Description	: HTTP Method가 GET인 경우 : 0
	 *                HTTP Method가 POST인 경우 : Body Content Length
	 * Example	    : 100
	 */
	private String contentLength;

	/**
	 * Definitions	: Request Body Content Type
	 * Necessity	: Required
	 * Description	: Request Body의 데이터 포멧을 정의한다. (json만 지원) 
	 * Example	    : application/json; charset=UTF-8
	 */
	private String contentType;

	/**
	 * Definitions	: 테넌트를 인증하는데 사용되는 키 (테스트키 or 사용키)
	 * Necessity	: Required
	 * Description	: SAC로 부터 발급받은 tenant key
	 * Example	    : 9Coum1NDRACh2v7eoYxfaA
	 */
	private String xSacAuthTenantKey;

	/**
	 * Definitions	: 테넌트를 인증하는데 사용되는 서명 (Signature)
	 * Necessity	: Required
	 * Description	: SAC로 부터 발급받은 tenant secret과 timestamp, nonse 등을 조합하여 단반향 암호화한 서명
	 * Example	    : N1w0EfuCzwfCNjxPdvSHYjUjTtszi47I7rkpbeV0
	 */
	private String xSacAuthSignature;
	
	/**
	 * Definitions	: 테넌트를 인증하는데 사용되는 Timestamp
	 * Necessity	: Conditional (Open API : Required)
	 * Description	: 서명 검증 및 Replay Attack을 막기위해 사용된다
	 * Example	    : 1386239696
	 */
	private String xSacAuthTimestamp;

	/**
	 * Definitions	: 테넌트를 인증하는데 사용되는 난수 (nonce)
	 * Necessity	: Conditional (Open API : Required)
	 * Description	: 서명 검증을 위해 사용된다.
	 * Example	    : 75dzhA
	 */
	private String xSacAuthNonce;

	/**
	 * Definitions	: 인터페이스 아이디
	 * Necessity	: Required
	 * Description	: 인터페이스 구분을 위해 사용된다.
	 * Example	    : I02000001
	 */
	private String xSacInterfaceId;

	/**
	 * Definitions	: 범용 고유 식별자 (GUID)
	 * Necessity	: Required
	 * Description	: 트렌젝션 구분을 위해 사용된다.
	 * Example	    : 550e8400-e29b-41d4-a716-446655440000
	 */
	private String xSacGuid;

	/**
	 * Definitions	: 단말기 정보
	 * Necessity	: Conditional (Mobile APP : Required)
	 * Description	: 통계적 목적 또는 특정 단말기의 제약으로 인해 필요한 HTTP 응답 메시지를 변경할 목적으로 사용된다.
	 *                기존 CSS의 x-planet-device-info와 user-agent를 대신한다.
	 * Example	    : TBD
	 */
	private String xSacDeviceInfo;

	/**
	 * Definitions	: 네트워크 정보
	 * Necessity	: Conditional (Mobile APP : Required)
	 * Description	: 현재 단말기가 접속 중인 Current Network 및 단말기의 Subscribed Carrier 정보 전달을 위해 사용된다.
	 *              : 기존 CSS의 x-planet-network-info를 대신한다.
	 * Example	    : TBD
	 */
	private String xSacNetworkInfo;

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getxSacAuthTenantKey() {
		return xSacAuthTenantKey;
	}

	public void setxSacAuthTenantKey(String xSacAuthTenantKey) {
		this.xSacAuthTenantKey = xSacAuthTenantKey;
	}

	public String getxSacAuthSignature() {
		return xSacAuthSignature;
	}

	public void setxSacAuthSignature(String xSacAuthSignature) {
		this.xSacAuthSignature = xSacAuthSignature;
	}

	public String getxSacAuthTimestamp() {
		return xSacAuthTimestamp;
	}

	public void setxSacAuthTimestamp(String xSacAuthTimestamp) {
		this.xSacAuthTimestamp = xSacAuthTimestamp;
	}

	public String getxSacAuthNonce() {
		return xSacAuthNonce;
	}

	public void setxSacAuthNonce(String xSacAuthNonce) {
		this.xSacAuthNonce = xSacAuthNonce;
	}

	public String getxSacInterfaceId() {
		return xSacInterfaceId;
	}

	public void setxSacInterfaceId(String xSacInterfaceId) {
		this.xSacInterfaceId = xSacInterfaceId;
	}

	public String getxSacGuid() {
		return xSacGuid;
	}

	public void setxSacGuid(String xSacGuid) {
		this.xSacGuid = xSacGuid;
	}

	public String getxSacDeviceInfo() {
		return xSacDeviceInfo;
	}

	public void setxSacDeviceInfo(String xSacDeviceInfo) {
		this.xSacDeviceInfo = xSacDeviceInfo;
	}

	public String getxSacNetworkInfo() {
		return xSacNetworkInfo;
	}

	public void setxSacNetworkInfo(String xSacNetworkInfo) {
		this.xSacNetworkInfo = xSacNetworkInfo;
	}
	
}
