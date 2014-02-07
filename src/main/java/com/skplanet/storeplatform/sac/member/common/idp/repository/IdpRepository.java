package com.skplanet.storeplatform.sac.member.common.idp.repository;

import com.skplanet.storeplatform.external.client.idp.vo.IdpReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIdpReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.vo.IdpSenderM;
import com.skplanet.storeplatform.sac.member.common.idp.vo.ImIdpSenderM;

public interface IdpRepository {

	public IdpReceiverM sendIDP(IdpSenderM sendData, String httpMethod);

	public ImIdpReceiverM sendImIDP(ImIdpSenderM sendData, String httpMethod);

	public String makePhoneAuthKey(String phoneMeta);

	public String makeSnAuthKey(String mbrNm, String userId);

	// public IDPReceiverM alredyJoinCheckByEmail(String email);
}
