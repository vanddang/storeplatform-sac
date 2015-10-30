/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * 판매자 BP정보 Value Object
 * 
 * Updated on : 2014. 03. 28. Updated by : wisestone_jloveonly
 */
public class SellerBp extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** BP_이미지_파일_경로 . */
	private String bpImgPath; // BP_IMG_FILE_PATH

	/** BP_썸네일_이미지_경로 . */
	private String bpTbnailPath; // BP_TBNAIL_IMG_PATH

	/** BP_설명 . */
	private String bpMessage; // BP_DESC

	/** BP_파일_경로 . */
	private String bpFilePath; // BP_FILE_PATH

	/** BP_파일_명 . */
	private String bpFileName; // BP_FILE_NM

	/** BP_이메일_인증_경로 . */
	private String bpEmailAuthPath; // BP_EMAIL_AUTH_PATH

	/** BP_약관_동의_여부 . */
	private String isClause; // BP_CLAUSE_AGREE_YN

	/** FTP_사용_여부 . */
	private String isFtp; // FTP_USE_YN

	/** FTP_계정 . */
	private String ftpAcct; // FTP_ACCT
	/** BP_구분 . */
	private String bpClsf; // BP_CLSF

	/**
	 * BP_이미지_파일_경로 를 리턴한다.
	 * 
	 * @return bpImgPath - BP_이미지_파일_경로
	 */
	public String getBpImgPath() {
		return this.bpImgPath;
	}

	/**
	 * BP_이미지_파일_경로 를 설정한다.
	 * 
	 * @param bpImgPath
	 *            BP_이미지_파일_경로
	 */
	public void setBpImgPath(String bpImgPath) {
		this.bpImgPath = bpImgPath;
	}

	/**
	 * BP_썸네일_이미지_경로 를 리턴한다.
	 * 
	 * @return bpTbnailPath - BP_썸네일_이미지_경로
	 */
	public String getBpTbnailPath() {
		return this.bpTbnailPath;
	}

	/**
	 * BP_썸네일_이미지_경로 를 설정한다.
	 * 
	 * @param bpTbnailPath
	 *            BP_썸네일_이미지_경로
	 */
	public void setBpTbnailPath(String bpTbnailPath) {
		this.bpTbnailPath = bpTbnailPath;
	}

	/**
	 * BP_설명 를 리턴한다.
	 * 
	 * @return bpMessage - BP_설명
	 */
	public String getBpMessage() {
		return this.bpMessage;
	}

	/**
	 * BP_설명 를 설정한다.
	 * 
	 * @param bpMessage
	 *            BP_설명
	 */
	public void setBpMessage(String bpMessage) {
		this.bpMessage = bpMessage;
	}

	/**
	 * BP_파일_경로 를 리턴한다.
	 * 
	 * @return BpFilePath - BP_파일_경로
	 */
	public String getBpFilePath() {
		return this.bpFilePath;
	}

	/**
	 * BP_파일_경로 를 설정한다.
	 * 
	 * @param bpFilePath
	 *            BP_파일_경로
	 */
	public void setBpFilePath(String bpFilePath) {
		this.bpFilePath = bpFilePath;
	}

	/**
	 * BP_파일_명 를 리턴한다.
	 * 
	 * @return bpFileName - BP_파일_명
	 */
	public String getBpFileName() {
		return this.bpFileName;
	}

	/**
	 * BP_파일_명 를 설정한다.
	 * 
	 * @param bpFileName
	 *            BP_파일_명
	 */
	public void setBpFileName(String bpFileName) {
		this.bpFileName = bpFileName;
	}

	/**
	 * BP_이메일_인증_경로 를 리턴한다.
	 * 
	 * @return bpEmailAuthPath - BP_이메일_인증_경로
	 */
	public String getBpEmailAuthPath() {
		return this.bpEmailAuthPath;
	}

	/**
	 * BP_이메일_인증_경로 를 설정한다.
	 * 
	 * @param bpEmailAuthPath
	 *            BP_이메일_인증_경로
	 */
	public void setBpEmailAuthPath(String bpEmailAuthPath) {
		this.bpEmailAuthPath = bpEmailAuthPath;
	}

	/**
	 * BP_약관_동의_여부 를 리턴한다.
	 * 
	 * @return isClause - BP_약관_동의_여부
	 */
	public String getIsClause() {
		return this.isClause;
	}

	/**
	 * BP_약관_동의_여부 를 설정한다.
	 * 
	 * @param isClause
	 *            BP_약관_동의_여부
	 */
	public void setIsClause(String isClause) {
		this.isClause = isClause;
	}

	/**
	 * FTP_사용_여부 를 리턴한다.
	 * 
	 * @return isFtp - FTP_사용_여부
	 */
	public String getIsFtp() {
		return this.isFtp;
	}

	/**
	 * FTP_사용_여부 를 설정한다.
	 * 
	 * @param isFtp
	 *            FTP_사용_여부
	 */
	public void setIsFtp(String isFtp) {
		this.isFtp = isFtp;
	}

	/**
	 * FTP_계정 를 리턴한다.
	 * 
	 * @return ftpAcct - FTP_계정
	 */
	public String getFtpAcct() {
		return this.ftpAcct;
	}

	/**
	 * FTP_계정 를 설정한다.
	 * 
	 * @param ftpAcct
	 *            FTP_계정
	 */
	public void setFtpAcct(String ftpAcct) {
		this.ftpAcct = ftpAcct;
	}

	/**
	 * BP_구분 를 리턴한다.
	 * 
	 * @return bpClsf - BP_구분
	 */
	public String getBpClsf() {
		return this.bpClsf;
	}

	/**
	 * BP_구분 를 설정한다.
	 * 
	 * @param bpClsf
	 *            BP_구분
	 */
	public void setBpClsf(String bpClsf) {
		this.bpClsf = bpClsf;
	}

	/**
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
