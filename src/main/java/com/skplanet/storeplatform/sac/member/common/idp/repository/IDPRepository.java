package com.skplanet.storeplatform.sac.member.common.idp.repository;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.vo.IDPSenderM;
import com.skplanet.storeplatform.sac.member.common.idp.vo.ImIDPSenderM;

public interface IDPRepository {

	public IDPReceiverM sendIDP(IDPSenderM sendData);

	public ImIDPReceiverM sendImIDP(ImIDPSenderM sendData);

	public String makePhoneAuthKey(String phoneMeta);

	public String makeSnAuthKey(String mbrNm, String userId);

	// public IDPReceiverM alredyJoinCheckByEmail(String email);
}
