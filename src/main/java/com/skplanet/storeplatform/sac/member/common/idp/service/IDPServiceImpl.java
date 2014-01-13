package com.skplanet.storeplatform.sac.member.common.idp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.vo.IDPSenderM;

/**
 * IDP - API 항목
 * 
 * Updated on : 2014. 1. 13. Updated by : 김경복, 부르칸.
 */
@Service
public class IDPServiceImpl implements IDPService {

	@Autowired
	private IDPRepository repository;

	/**
	 * <pre>
	 * 아이디 중복 체크
	 * </pre>
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM checkDupID(String id) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_DUPLICATE_ID_CHECK);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_id(id);

		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 모바일 회원 인증
	 * </pre>
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM authForWap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_USER_AUTH);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_AUTH_FOR_WAP);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);

		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 모바일 회원가입
	 * </pre>
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM join4Wap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_JOIN);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_JOIN_FOR_WAP);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setMdn_corp(IDPConstants.IDP_PARAM_KEY_USER_MDN_TYPE_SKT);
		sendData.setUser_mdn(mdn);

		return this.repository.sendIDP(sendData);
	}

	/**
	 * <pre>
	 * 모바일 회원 탈퇴
	 * </pre>
	 * 
	 * @param mdn
	 * @return
	 * @throws Exception
	 */
	@Override
	public IDPReceiverM secedeUser4Wap(String mdn) throws Exception {
		IDPSenderM sendData = new IDPSenderM();

		sendData.setUrl(IDPConstants.IDP_REQ_URL_SECEDE);
		sendData.setCmd(IDPConstants.IDP_REQ_CMD_SECEDE_FOR_WAP);
		sendData.setResp_type(IDPConstants.IDP_PARAM_RESP_TYPE_XML);
		sendData.setResp_flow(IDPConstants.IDP_PARAM_RESP_FLOW_RESPONSE);
		sendData.setUser_mdn(mdn);
		return this.repository.sendIDP(sendData);
	}
}
