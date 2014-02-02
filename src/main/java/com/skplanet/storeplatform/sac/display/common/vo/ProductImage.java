package com.skplanet.storeplatform.sac.display.common.vo;

/**
 * Product Image Updated on : 2014. 01. 28 Updated by : 임근대, SK 플래닛.
 */
public class ProductImage {

	/** 상품ID */
	private String prodId;
	/** 이미지 코드 */
	private String imgCd;
	/** 노출 순서 */
	private Integer expoOrd;
	/** 언어 코드 */
	private String langCd;
	/** 파일 경로 */
	private String filePath;
	/** 파일 명 */
	private String fileNm;
	/** 파일 사이즈 */
	private Integer fileSize;

	/**
	 * 상품ID
	 *
	 * @return 상품ID
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * 상품ID
	 *
	 * @param prodId
	 *            상품ID
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * 이미지 코드
	 *
	 * @return 이미지 코드
	 */
	public String getImgCd() {
		return this.imgCd;
	}

	/**
	 * 이미지 코드
	 *
	 * @param imgCd
	 *            이미지 코드
	 */
	public void setImgCd(String imgCd) {
		this.imgCd = imgCd;
	}

	/**
	 * 노출 순서
	 *
	 * @return 노출 순서
	 */
	public Integer getExpoOrd() {
		return this.expoOrd;
	}

	/**
	 * 노출 순서
	 *
	 * @param expoOrd
	 *            노출 순서
	 */
	public void setExpoOrd(Integer expoOrd) {
		this.expoOrd = expoOrd;
	}

	/**
	 * 언어 코드
	 *
	 * @return 언어 코드
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * 언어 코드
	 *
	 * @param langCd
	 *            언어 코드
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * 파일 경로
	 *
	 * @return 파일 경로
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 파일 경로
	 *
	 * @param filePath
	 *            파일 경로
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 파일 명
	 *
	 * @return 파일 명
	 */
	public String getFileNm() {
		return this.fileNm;
	}

	/**
	 * 파일 명
	 *
	 * @param fileNm
	 *            파일 명
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	/**
	 * 파일 사이즈
	 *
	 * @return 파일 사이즈
	 */
	public Integer getFileSize() {
		return this.fileSize;
	}

	/**
	 * 파일 사이즈
	 *
	 * @param fileSize
	 *            파일 사이즈
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "ProductImage [prodId=" + this.prodId + ", imgCd=" + this.imgCd
				+ ", expoOrd=" + this.expoOrd + ", langCd=" + this.langCd
				+ ", filePath=" + this.filePath + ", fileNm=" + this.fileNm
				+ ", fileSize=" + this.fileSize + "]";
	}

}
