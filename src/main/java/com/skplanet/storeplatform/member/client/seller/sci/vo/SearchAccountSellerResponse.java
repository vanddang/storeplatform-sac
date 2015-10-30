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
 * 판매자 정산정보 조회 
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 판매자회원 정산정보 조회 value object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class SearchAccountSellerResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 판매자 키. */
	private String sellerKey;

	/** 판매자정보 Value Object. */
	private SellerMbr sellerMbr;

	/**
	 * BP Value Object를 리턴한다.
	 * 
	 * @return sellerBp - BP Value Object
	 */
	public SellerBp getSellerBp() {
		return this.sellerBp;
	}

	/**
	 * BP를 설정한다.
	 * 
	 * @param sellerBp
	 *            BP
	 */
	public void setSellerBp(SellerBp sellerBp) {
		this.sellerBp = sellerBp;
	}

	/** 판매자 Bp정보 Value Object. */
	private SellerBp sellerBp;

	/** 판매자 정산정보 Value Object. */
	private SellerAccount sellerAccount;

	/** 판매자 문서정보 Value Object 목록. */
	private List<Document> document; // TB_US_SELLERMBR_DOC 서류 테이블 에서 추출

	/** 판매자 부가정보 Value Object 목록. */
	private List<ExtraRight> extraRight; // 멀티미지어 권한 (정산률) TB_US_SELLERMBR_MULTIMDA_AUTH

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
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
	 * 판매자 정산정보 Value Object를 리턴한다.
	 * 
	 * @return sellerAccount - 판매자 정산정보 Value Object
	 */
	public SellerAccount getSellerAccount() {
		return this.sellerAccount;
	}

	/**
	 * 판매자 정산정보 Value Object를 설정한다.
	 * 
	 * @param sellerAccount
	 *            판매자 정산정보 Value Object
	 */
	public void setSellerAccount(SellerAccount sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	/**
	 * 판매자 부가정보 Value Object 목록을 리턴한다.
	 * 
	 * @return extraRight - 판매자 부가정보 Value Object 목록
	 */
	public List<ExtraRight> getExtraRight() {
		return this.extraRight;
	}

	/**
	 * 판매자 부가정보 Value Object 목록을 설정한다.
	 * 
	 * @param extraRight
	 *            판매자 부가정보 Value Object 목록
	 */
	public void setExtraRight(List<ExtraRight> extraRight) {
		this.extraRight = extraRight;
	}

	/**
	 * 판매자 문서정보 Value Object 목록을 리턴한다.
	 * 
	 * @return document - 판매자 문서정보 Value Object 목록
	 */
	public List<Document> getDocument() {
		return this.document;
	}

	/**
	 * 판매자 문서정보 Value Object 목록을 설정한다.
	 * 
	 * @param document
	 *            판매자 문서정보 Value Object 목록
	 */
	public void setDocument(List<Document> document) {
		this.document = document;
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
