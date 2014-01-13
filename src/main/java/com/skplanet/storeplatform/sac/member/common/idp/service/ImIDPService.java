package com.skplanet.storeplatform.sac.member.common.idp.service;

import java.util.Map;

import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;

public interface ImIDPService {

	/** */
	public ImIDPReceiverM authForId(String key, String pwd) throws Exception;

	/** */
	public ImIDPReceiverM getMdnInfoIDP(String mdn) throws Exception;

	/** */
	public ImIDPReceiverM agreeUser(Map<String, Object> param) throws Exception;

	/** */
	public ImIDPReceiverM userInfoIdpSearchServer(String imServiceNo) throws Exception;
}
