package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_DEVICE 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80, I-S Plus.
 */
public class UserMbrDeviceInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantId;

	/** 내부사용자 회원번호. */
	private String insdUserMbrNo;

	/** 내부기기 ID. */
	private String insdDeviceId;

	/** 시작 일시. */
	private Date startDt;

	/** 종료 일시. */
	private Date endDt;

	/** 기기 ID. */
	private String deviceId;

	/** 기기 모델 코드. */
	private String deviceModelCd;

	/** 통신사 코드. */
	private String mnoCd;

	/** 기기 별명. */
	private String deviceAlias;

	/** 대표 기기 여부. */
	private String repDeviceYn;

	/** 인증 여부. */
	private String authYn;

	/** 인증 일시. */
	private Date authDt;

	/** 수정 일시. */
	private Date updDt;

	/** SMS 수신 여부. */
	private String smsRecvYn;

	/** 기기 고유 ID. */
	private String deviceNatvId;

	/** 기기 계정. */
	private String deviceAcct;

	/** 가입 채널 코드. */
	private String entryChnlCd;

	/** 서비스 관리 번호. */
	private String svcMangNo;

	/** 등록 일시. */
	private Date regDt;

	/** 변경 유형 코드. */
	private String chgCaseCd;

	/** 데어터 전송 플래그. */
	private String dataTranFlag;

	/**
	 * <pre>
	 * 테넌트 ID를 리턴한다.
	 * </pre>
	 * 
	 * @return tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * <pre>
	 * 테넌트 ID를 설정한다.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return insdUserMbrNo
	 */
	public String getInsdUserMbrNo() {
		return this.insdUserMbrNo;
	}

	/**
	 * <pre>
	 * 내부기기 ID를 설정한다.
	 * </pre>
	 * 
	 * @param insdUserMbrNo
	 *            내부기기 ID
	 */
	public void setInsdUserMbrNo(String insdUserMbrNo) {
		this.insdUserMbrNo = insdUserMbrNo;
	}

	/**
	 * <pre>
	 * 내부기기 ID르 리턴한다.
	 * </pre>
	 * 
	 * @return insdDeviceId
	 */
	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	/**
	 * <pre>
	 * 내부기기 ID를 설정한다.
	 * </pre>
	 * 
	 * @param insdDeviceId
	 *            내부기기 ID
	 */
	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	/**
	 * <pre>
	 * 시작 일시를 리턴한다.
	 * </pre>
	 * 
	 * @return startDt
	 */
	public Date getStartDt() {
		return this.startDt;
	}

	/**
	 * <pre>
	 * 시작 일시를 설정한다.
	 * </pre>
	 * 
	 * @param startDt
	 *            시작 일시
	 */
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	/**
	 * <pre>
	 * 종료 일시를 리턴한다.
	 * </pre>
	 * 
	 * @return endDt
	 */
	public Date getEndDt() {
		return this.endDt;
	}

	/**
	 * <pre>
	 * 종료 일시를 설정한다.
	 * </pre>
	 * 
	 * @param endDt
	 *            종료 일시
	 */
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	/**
	 * <pre>
	 * 기기 ID를 리턴한다.
	 * </pre>
	 * 
	 * @return deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * <pre>
	 * 기기 ID를 설정한다.
	 * </pre>
	 * 
	 * @param deviceId
	 *            기기 ID
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * <pre>
	 * 기기 모델 코드를 리턴한다.
	 * </pre>
	 * 
	 * @return deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * <pre>
	 * 기기 모델 코드를 설정한다.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            기기 모델 코드
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * <pre>
	 * 통신사 코드를 리턴한다.
	 * </pre>
	 * 
	 * @return mnoCd
	 */
	public String getMnoCd() {
		return this.mnoCd;
	}

	/**
	 * <pre>
	 * 통신사 코드.
	 * </pre>
	 * 
	 * @param mnoCd
	 *            통신사 코드
	 */
	public void setMnoCd(String mnoCd) {
		this.mnoCd = mnoCd;
	}

	/**
	 * <pre>
	 * 기기 별명을 리턴한다.
	 * </pre>
	 * 
	 * @return deviceAlias
	 */
	public String getDeviceAlias() {
		return this.deviceAlias;
	}

	/**
	 * <pre>
	 * 기기 별명을 설정한다.
	 * </pre>
	 * 
	 * @param deviceAlias
	 *            기기 별명
	 */
	public void setDeviceAlias(String deviceAlias) {
		this.deviceAlias = deviceAlias;
	}

	/**
	 * <pre>
	 * 대표 기기 여부를 리턴한다.
	 * </pre>
	 * 
	 * @return repDeviceYn
	 */
	public String getRepDeviceYn() {
		return this.repDeviceYn;
	}

	/**
	 * <pre>
	 * 대표 기기 여부를 설정한다.
	 * </pre>
	 * 
	 * @param repDeviceYn
	 *            대표 기기 여부
	 */
	public void setRepDeviceYn(String repDeviceYn) {
		this.repDeviceYn = repDeviceYn;
	}

	/**
	 * <pre>
	 * 인증 여부를 리턴한다.
	 * </pre>
	 * 
	 * @return authYn
	 */
	public String getAuthYn() {
		return this.authYn;
	}

	/**
	 * <pre>
	 * 인증 여부를 설정한다.
	 * </pre>
	 * 
	 * @param authYn
	 *            인증 여부
	 */
	public void setAuthYn(String authYn) {
		this.authYn = authYn;
	}

	/**
	 * <pre>
	 * 인증 일시를 리턴한다.
	 * </pre>
	 * 
	 * @return authDt
	 */
	public Date getAuthDt() {
		return this.authDt;
	}

	/**
	 * <pre>
	 * 인증 일시를 설정한다.
	 * </pre>
	 * 
	 * @param authDt
	 *            인증 일시
	 */
	public void setAuthDt(Date authDt) {
		this.authDt = authDt;
	}

	/**
	 * <pre>
	 * 수정 일시를 리턴한다.
	 * </pre>
	 * 
	 * @return updDt
	 */
	public Date getUpdDt() {
		return this.updDt;
	}

	/**
	 * <pre>
	 * 수정 일시를 설정한다.
	 * </pre>
	 * 
	 * @param updDt
	 *            수정 일시
	 */
	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}

	/**
	 * <pre>
	 * SMS 수신 여부를 리턴한다.
	 * </pre>
	 * 
	 * @return smsRecvYn
	 */
	public String getSmsRecvYn() {
		return this.smsRecvYn;
	}

	/**
	 * <pre>
	 * SMS 수신 여부를 설정한다.
	 * </pre>
	 * 
	 * @param smsRecvYn
	 *            SMS 수신 여부
	 */
	public void setSmsRecvYn(String smsRecvYn) {
		this.smsRecvYn = smsRecvYn;
	}

	/**
	 * <pre>
	 * 기기 고유 ID를 리턴한다.
	 * </pre>
	 * 
	 * @return deviceNatvId
	 */
	public String getDeviceNatvId() {
		return this.deviceNatvId;
	}

	/**
	 * <pre>
	 * 기기 고유 ID를 설정한다.
	 * </pre>
	 * 
	 * @param deviceNatvId
	 *            기기 고유 ID
	 */
	public void setDeviceNatvId(String deviceNatvId) {
		this.deviceNatvId = deviceNatvId;
	}

	/**
	 * <pre>
	 * 기기 계정을 리턴한다.
	 * </pre>
	 * 
	 * @return deviceAcct
	 */
	public String getDeviceAcct() {
		return this.deviceAcct;
	}

	/**
	 * <pre>
	 * 기기 계정을 설정한다.
	 * </pre>
	 * 
	 * @param deviceAcct
	 *            기기 계정
	 */
	public void setDeviceAcct(String deviceAcct) {
		this.deviceAcct = deviceAcct;
	}

	/**
	 * <pre>
	 * 가입 채널 코드를 리턴한다.
	 * </pre>
	 * 
	 * @return entryChnlCd
	 */
	public String getEntryChnlCd() {
		return this.entryChnlCd;
	}

	/**
	 * <pre>
	 * 가입 채널 코드를 설정한다.
	 * </pre>
	 * 
	 * @param entryChnlCd
	 *            가입 채널 코드
	 */
	public void setEntryChnlCd(String entryChnlCd) {
		this.entryChnlCd = entryChnlCd;
	}

	/**
	 * <pre>
	 * 서비스 관리 번호를 리턴한다.
	 * </pre>
	 * 
	 * @return svcMangNo
	 */
	public String getSvcMangNo() {
		return this.svcMangNo;
	}

	/**
	 * <pre>
	 * 서비스 관리 번호를 설정한다.
	 * </pre>
	 * 
	 * @param svcMangNo
	 *            서비스 관리 번호
	 */
	public void setSvcMangNo(String svcMangNo) {
		this.svcMangNo = svcMangNo;
	}

	/**
	 * <pre>
	 * 등록일시를 리턴한다.
	 * </pre>
	 * 
	 * @return regDt
	 */
	public Date getRegDt() {
		return this.regDt;
	}

	/**
	 * <pre>
	 * 등록일시를 설정한다.
	 * </pre>
	 * 
	 * @param regDt
	 *            등록일시
	 */
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	/**
	 * <pre>
	 * 변경 유형 코드를 리턴한다.
	 * </pre>
	 * 
	 * @return chgCaseCd
	 */
	public String getChgCaseCd() {
		return this.chgCaseCd;
	}

	/**
	 * <pre>
	 * 변경 유형 코드를 설정한다.
	 * </pre>
	 * 
	 * @param chgCaseCd
	 *            변경 유형 코드
	 */
	public void setChgCaseCd(String chgCaseCd) {
		this.chgCaseCd = chgCaseCd;
	}

	/**
	 * <pre>
	 * 데이터 전송 플래그를 리턴한다.
	 * </pre>
	 * 
	 * @return dataTranFlag
	 */
	public String getDataTranFlag() {
		return this.dataTranFlag;
	}

	/**
	 * <pre>
	 * 데이터 전송 플래그를 설정한다.
	 * </pre>
	 * 
	 * @param dataTranFlag
	 *            데이터 전송 플래그
	 */
	public void setDataTranFlag(String dataTranFlag) {
		this.dataTranFlag = dataTranFlag;
	}

}
