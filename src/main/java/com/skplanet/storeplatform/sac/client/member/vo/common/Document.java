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

	private String accountChangeKey;
	private String documentCode;
	private String documentName;
	private String documentPath;
	private String documentSize;
	private String isUsed;
	private String regDate;
	private String regID;
	private String sellerKey;
	private String updateDate;
	private String updateID;

	public String getAccountChangeKey() {
		return this.accountChangeKey;
	}

	public void setAccountChangeKey(String accountChangeKey) {
		this.accountChangeKey = accountChangeKey;
	}

	public String getDocumentCode() {
		return this.documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getDocumentName() {
		return this.documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentPath() {
		return this.documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getDocumentSize() {
		return this.documentSize;
	}

	public void setDocumentSize(String documentSize) {
		this.documentSize = documentSize;
	}

	public String getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegID() {
		return this.regID;
	}

	public void setRegID(String regID) {
		this.regID = regID;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateID() {
		return this.updateID;
	}

	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}

}
