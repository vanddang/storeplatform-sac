package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;

/**
 * Password 보안 질문 확인
 * 
 * Updated on : 2014. 1. 20. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CheckPasswordReminderQuestionReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자회원 ID. */
	private String sellerID; // SELLERMBR_ID 판매자회원 id

	/** 비밀번호 보안질문 Value Object. */
	private List<PwReminder> pWReminderList;

	public String getSellerID() {
		return this.sellerID;
	}

	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}

	public List<PwReminder> getpWReminderList() {
		return this.pWReminderList;
	}

	public void setpWReminderList(List<PwReminder> pWReminderList) {
		this.pWReminderList = pWReminderList;
	}

}
