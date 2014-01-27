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
 * 전처리 브랜드 카탈로그 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */
public class BrandCatalogProdImgInfo extends CommonInfo {

	private String prodId; // 브랜드,카탈로그id
	private String imgCls; // 이미지구분
	private int fileSize = 0; // 파일size
	private String insBy; // 등록자
	private String insDts; // 등록일시
	private String updBy; // 수정자
	private String updDts; // 수정일시
	private String filePos;
	private String fileNm;
	private String langCd;
	private int seq = 1; // TBL_DP_PROD_IMG.DP_ORDER

	public String getImgCls() {
		return this.imgCls;
	}

	public void setImgCls(String imgCls) {
		this.imgCls = imgCls;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getInsBy() {
		return this.insBy;
	}

	public void setInsBy(String insBy) {
		this.insBy = insBy;
	}

	public String getInsDts() {
		return this.insDts;
	}

	public void setInsDts(String insDts) {
		this.insDts = insDts;
	}

	public String getUpdBy() {
		return this.updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public String getUpdDts() {
		return this.updDts;
	}

	public void setUpdDts(String updDts) {
		this.updDts = updDts;
	}

	public String getFilePos() {
		return this.filePos;
	}

	public void setFilePos(String filePos) {
		this.filePos = filePos;
	}

	public String getFileNm() {
		return this.fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public int getSeq() {
		return this.seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}
