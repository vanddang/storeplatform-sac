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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;

// TODO: Auto-generated Javadoc
/**
 * sub 판매자회원 가입 요청 Value Object.
 * 
 * Updated on : 2013. 12. 16. Updated by : wisestone_mikepark
 */
public class CreateSubSellerRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * <pre>
	 * 서브계정 Value Object.
	 * parentSellerKey : 부모 판매자 키
	 * sellerKey : 서브계정 키( 수정시 필요, 등록시 불필요 )
	 * sellerID : 서브계정 ID
	 * subSellerMemo : 서브계정 메모
	 * rightProfileList : 서브계정 권한리스트
	 * sellerEmail : 서브계정 Email
	 * sellerPhoneCountry : 서브계정 국가번호
	 * sellerPhone : 서브계정 무선 전화번호
	 * </pre>
	 */
	private SellerMbr sellerMbr;

	/**
	 * <pre>
	 * 서브계정  비밀번호 정보 Value Object.
	 * memberPW : 서브계정 비밀번호
	 * </pre>
	 */
	private MbrPwd mbrPwd;

	/** 신규 등록여부 Example : Y/N. */
	private String isNew;

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
	 * 서브계정 Value Object를 리턴한다.
	 * 
	 * @return sellerMbr - 서브계정 Value Object
	 */
	public SellerMbr getSellerMbr() {
		return this.sellerMbr;
	}

	/**
	 * 서브계정 Value Object를 설정한다.
	 * 
	 * @param sellerMbr
	 *            서브계정 Value Object
	 */
	public void setSellerMbr(SellerMbr sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

	/**
	 * 서브계정 비밀번호 정보 Value Object를 리턴한다.
	 * 
	 * @return mbrPwd - 서브계정 비밀번호 정보 Value Object
	 */
	public MbrPwd getMbrPwd() {
		return this.mbrPwd;
	}

	/**
	 * 서브계정 비밀번호 정보 Value Object를 설정한다.
	 * 
	 * @param mbrPwd
	 *            서브계정 비밀번호 정보 Value Object
	 */
	public void setMbrPwd(MbrPwd mbrPwd) {
		this.mbrPwd = mbrPwd;
	}

	/**
	 * 신규 등록여부 Y/N Value Object를 리턴한다.
	 * 
	 * @return isNew - 신규 등록여 정보 Value Object
	 */
	public String getIsNew() {
		return this.isNew;
	}

	/**
	 * 신규 등록여부 Y/N Value Object를 설정한다.
	 * 
	 * @param isNew
	 *            서브계정 비밀번호 정보 Value Object
	 */

	public void setIsNew(String isNew) {
		this.isNew = isNew;
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
