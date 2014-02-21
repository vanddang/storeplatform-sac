/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 전처리 브랜드 카탈로그 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class BrandCatalogProdImgInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String prodId; // 브랜드,카탈로그id
	private String imgCls; // 이미지구분
	private final int fileSize = 0; // 파일size
	private String insBy; // 등록자
	private String insDts; // 등록일시
	private String updBy; // 수정자
	private String updDts; // 수정일시
	private String filePos;
	private String fileNm;
	private String langCd;
	private int seq = 1; // TBL_DP_PROD_IMG.DP_ORDER

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
	 * @return the imgCls
	 */
	public String getImgCls() {
		return this.imgCls;
	}

	/**
	 * @param imgCls
	 *            the imgCls to set
	 */
	public void setImgCls(String imgCls) {
		this.imgCls = imgCls;
	}

	/**
	 * @return the insBy
	 */
	public String getInsBy() {
		return this.insBy;
	}

	/**
	 * @param insBy
	 *            the insBy to set
	 */
	public void setInsBy(String insBy) {
		this.insBy = insBy;
	}

	/**
	 * @return the insDts
	 */
	public String getInsDts() {
		return this.insDts;
	}

	/**
	 * @param insDts
	 *            the insDts to set
	 */
	public void setInsDts(String insDts) {
		this.insDts = insDts;
	}

	/**
	 * @return the updBy
	 */
	public String getUpdBy() {
		return this.updBy;
	}

	/**
	 * @param updBy
	 *            the updBy to set
	 */
	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	/**
	 * @return the updDts
	 */
	public String getUpdDts() {
		return this.updDts;
	}

	/**
	 * @param updDts
	 *            the updDts to set
	 */
	public void setUpdDts(String updDts) {
		this.updDts = updDts;
	}

	/**
	 * @return the filePos
	 */
	public String getFilePos() {
		return this.filePos;
	}

	/**
	 * @param filePos
	 *            the filePos to set
	 */
	public void setFilePos(String filePos) {
		this.filePos = filePos;
	}

	/**
	 * @return the fileNm
	 */
	public String getFileNm() {
		return this.fileNm;
	}

	/**
	 * @param fileNm
	 *            the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the fileSize
	 */
	public int getFileSize() {
		return this.fileSize;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return this.seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

}
