/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.message.repository;

import java.util.Map;

import com.skplanet.storeplatform.external.client.message.vo.AomSendReq;
import com.skplanet.storeplatform.external.client.message.vo.EmailSendReq;
import com.skplanet.storeplatform.external.client.message.vo.MmsSendReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendReq;

public interface MessageRepository {

	public Map<String, String> smsSend(SmsSendReq smsReq);

	public Map<String, String> mmsSend(MmsSendReq mmsReq);

	public Map<String, String> aomSend(AomSendReq aomReq);

	public Map<String, String> emailSend(EmailSendReq emailReq);

}
