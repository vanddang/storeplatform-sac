package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.io.Serializable;
import java.util.List;

/**
 * 판매자 회원 탈퇴사유 리스트 response VO
 * 
 * Updated on : 2013-12-20 Updated by : 반범진, 지티소프트.
 */
public class SellerSecedeResonListResponseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String resultCode;

	private String resultMessage;

	private List<SellerSecedeResonVO> sellerSecedeResonList;

	public String getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public List<SellerSecedeResonVO> getSellerSecedeResonList() {
		return this.sellerSecedeResonList;
	}

	public void setSellerSecedeResonList(
			List<SellerSecedeResonVO> sellerSecedeResonList) {
		this.sellerSecedeResonList = sellerSecedeResonList;
	}

}
