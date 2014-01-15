package com.skplanet.storeplatform.sac.member.common.idp.repository;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.vo.IDPSenderM;
import com.skplanet.storeplatform.sac.member.common.idp.vo.ImIDPSenderM;

public interface IDPRepository {

	public IDPReceiverM sendIDP(IDPSenderM sendData) throws Exception;

	public ImIDPReceiverM sendImIDP(ImIDPSenderM sendData) throws Exception;

	public String makePhoneAuthKey(String phoneMeta) throws Exception;

	public String makeSnAuthKey(String mbrNm, String userId) throws Exception;

	// public IDPReceiverM alredyJoinCheckByEmail(String email) throws Exception;
}
