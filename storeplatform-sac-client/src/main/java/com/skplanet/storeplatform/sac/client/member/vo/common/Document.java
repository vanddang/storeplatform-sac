package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 정산 정보 조회 문서정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Document extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 전환 신청 키.
	 */
	private String accountChangeKey;

	/**
	 * 문서 코드.
	 */
	private String documentCode;

	/**
	 * 문서명.
	 */
	private String documentName;

	/**
	 * 문서 경로.
	 */
	private String documentPath;

	/**
	 * 문서 사이즈.
	 */
	private String documentSize;

	/**
	 * 문서 사용여부 (Y/N).
	 */
	private String isUsed;

	/**
	 * 등록일시.
	 */
	private String regDate;

	/**
	 * 등록 ID.
	 */
	private String regID;

	/**
	 * 판매자 Key.
	 */
	private String sellerKey;

	/**
	 * 수정일시.
	 */
	private String updateDate;

	/**
	 * 수정 ID.
	 */
	private String updateID;

	/**
	 * @return the accountChangeKey
	 */
	public String getAccountChangeKey() {
		return this.accountChangeKey;
	}

	/**
	 * @param accountChangeKey
	 *            the accountChangeKey to set
	 */
	public void setAccountChangeKey(String accountChangeKey) {
		this.accountChangeKey = accountChangeKey;
	}

	/**
	 * @return the documentCode
	 */
	public String getDocumentCode() {
		return this.documentCode;
	}

	/**
	 * @param documentCode
	 *            the documentCode to set
	 */
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	/**
	 * @return the documentName
	 */
	public String getDocumentName() {
		return this.documentName;
	}

	/**
	 * @param documentName
	 *            the documentName to set
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * @return the documentPath
	 */
	public String getDocumentPath() {
		return this.documentPath;
	}

	/**
	 * @param documentPath
	 *            the documentPath to set
	 */
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	/**
	 * @return the documentSize
	 */
	public String getDocumentSize() {
		return this.documentSize;
	}

	/**
	 * @param documentSize
	 *            the documentSize to set
	 */
	public void setDocumentSize(String documentSize) {
		this.documentSize = documentSize;
	}

	/**
	 * @return the isUsed
	 */
	public String getIsUsed() {
		return this.isUsed;
	}

	/**
	 * @param isUsed
	 *            the isUsed to set
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * @param regDate
	 *            the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regID
	 */
	public String getRegID() {
		return this.regID;
	}

	/**
	 * @param regID
	 *            the regID to set
	 */
	public void setRegID(String regID) {
		this.regID = regID;
	}

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateID
	 */
	public String getUpdateID() {
		return this.updateID;
	}

	/**
	 * @param updateID
	 *            the updateID to set
	 */
	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}

}
