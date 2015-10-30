/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
/**
 * 판매자 전환신청 조회 
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

// TODO: Auto-generated Javadoc
/**
 * 판매자 전환신청 요청 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class UpgradeSellerRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** 판매자정보 Value Object. */
	private SellerMbr sellerMbr;

	/** 판매자 전환정보 Value Object. */
	private SellerUpgrade sellerUpgrade;

	/** 판매자 문서정보 Value Object 목록. */
	private List<Document> documentList; // 판매자정보

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/**
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 키
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * 판매자정보 Value Object를 리턴한다.
	 * 
	 * @return sellerMbr - 판매자정보 Value Object
	 */
	public SellerMbr getSellerMbr() {
		return this.sellerMbr;
	}

	/**
	 * 판매자정보 Value Object를 설정한다.
	 * 
	 * @param sellerMbr
	 *            판매자정보 Value Object
	 */
	public void setSellerMbr(SellerMbr sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

	/**
	 * 판매자 문서정보 Value Object 목록을 리턴한다.
	 * 
	 * @return documentList - 판매자 문서정보 Value Object 목록
	 */
	public List<Document> getDocumentList() {
		return this.documentList;
	}

	/**
	 * 판매자 문서정보 Value Object 목록을 설정한다.
	 * 
	 * @param documentList
	 *            판매자 문서정보 Value Object 목록
	 */
	public void setDocumentList(List<Document> documentList) {
		this.documentList = documentList;
	}

	/**
	 * 판매자 전환정보 Value Object를 리턴한다.
	 * 
	 * @return sellerUpgrade - 판매자 전환정보 Value Object
	 */
	public SellerUpgrade getSellerUpgrade() {
		return this.sellerUpgrade;
	}

	/**
	 * 판매자 전환정보 Value Object를 설정한다.
	 * 
	 * @param sellerUpgrade
	 *            판매자 전환정보 Value Object
	 */
	public void setSellerUpgrade(SellerUpgrade sellerUpgrade) {
		this.sellerUpgrade = sellerUpgrade;
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
