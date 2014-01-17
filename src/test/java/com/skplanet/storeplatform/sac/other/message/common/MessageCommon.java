/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.message.common;

import com.skplanet.storeplatform.external.client.message.vo.EmailSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;

/**
 * 
 * Message Common
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
public class MessageCommon {

	/**
	 * 
	 * <pre>
	 * SmsSendReq VO 생성.
	 * </pre>
	 * 
	 * @return SmsSendReq
	 */
	public static SmsSendReq getSmsSendReq() {
		SmsSendReq smsSendReq = new SmsSendReq();
		smsSendReq.setSrcId("OR00401");
		smsSendReq.setDeviceTelecom("SKT");
		smsSendReq.setTeleSvcId("0");
		smsSendReq.setSendMdn("0101231234");
		smsSendReq.setRecvMdn("0101231234");
		smsSendReq.setMsg("test message");
		smsSendReq.setCallbackUrl("test");
		smsSendReq.setResvDtTm("20140115094931");
		smsSendReq.setInterfaceId("9");
		smsSendReq.setCampaignId("111111111");
		smsSendReq.setSendOrder("9");
		return smsSendReq;
	}

	/**
	 * 
	 * <pre>
	 * EmailSendReq VO 생성.
	 * </pre>
	 * 
	 * @return EmailSendReq
	 */
	public static EmailSendReq getEmailSendReq() {
		EmailSendReq emailSendReq = new EmailSendReq();
		emailSendReq.setTemplateId(0);
		emailSendReq.setRecvId("tester");
		emailSendReq.setRecvNm("tester");
		emailSendReq.setRecvMail("nahyiak@me.com");
		String[] sParam = new String[14];
		sParam[0] = "";
		sParam[1] = "";
		sParam[2] = "";
		sParam[3] = "";
		sParam[4] = "";
		sParam[5] = "";
		sParam[6] = "";
		sParam[7] = "";
		sParam[8] = "";
		sParam[9] = "기타";
		sParam[10] = "";
		sParam[11] = "";
		sParam[12] = "";
		sParam[13] = "";
		emailSendReq.setsParam(sParam);
		emailSendReq.setSendNm("T STORE");
		emailSendReq.setSendMail("nahiyak@nate.com");
		emailSendReq.setSendId("tester");
		emailSendReq.setTitle("[T store] 고객문의 접수안내");
		emailSendReq.setTemplateId(4503);
		emailSendReq.setDocCd("100");
		emailSendReq.setContents("test message");
		return emailSendReq;
	}
}
