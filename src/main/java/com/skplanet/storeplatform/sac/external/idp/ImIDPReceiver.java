package com.skplanet.storeplatform.sac.external.idp;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.external.idp.vo.ImIDPReceiverM;

public class ImIDPReceiver {

	private static final Logger logger = LoggerFactory.getLogger(ImIDPReceiver.class);

	/**
	 * 
	 * @param reciveData
	 * @return
	 * @throws Exception
	 */
	public ImIDPReceiverM nullValue(ImIDPReceiverM reciveData) throws Exception {

		reciveData.getResponseHeader().setCmd(this.checkNull(reciveData.getResponseHeader().getCmd()));
		reciveData.getResponseHeader().setResult(this.checkNull(reciveData.getResponseHeader().getResult()));
		reciveData.getResponseHeader().setResult_text(this.checkNull(reciveData.getResponseHeader().getResult_text()));
		reciveData.getResponseBody().setTrx_no(this.checkNull(reciveData.getResponseBody().getTrx_no()));
		reciveData.getResponseBody().setIm_int_svc_no(this.checkNull(reciveData.getResponseBody().getIm_int_svc_no()));
		reciveData.getResponseBody().setUser_id(this.checkNull(reciveData.getResponseBody().getUser_id()));
		reciveData.getResponseBody().setUser_key(this.checkNull(reciveData.getResponseBody().getUser_key()));
		reciveData.getResponseBody().setUser_passwd_type(
				this.checkNull(reciveData.getResponseBody().getUser_passwd_type()));
		reciveData.getResponseBody().setUser_passwd_modify_date(
				this.checkNull(reciveData.getResponseBody().getUser_passwd_modify_date()));
		reciveData.getResponseBody().setAuth_type(this.checkNull(reciveData.getResponseBody().getAuth_type()));
		reciveData.getResponseBody().setUser_tn(this.checkNull(reciveData.getResponseBody().getUser_tn()));
		reciveData.getResponseBody().setUser_tn_nation_cd(
				this.checkNull(reciveData.getResponseBody().getUser_tn_nation_cd()));
		reciveData.getResponseBody().setUser_tn_type(this.checkNull(reciveData.getResponseBody().getUser_tn_type()));
		reciveData.getResponseBody().setIs_user_tn_auth(
				this.checkNull(reciveData.getResponseBody().getIs_user_tn_auth()));
		reciveData.getResponseBody()
				.setIs_user_tn_own(this.checkNull(reciveData.getResponseBody().getIs_user_tn_own()));
		reciveData.getResponseBody().setUser_email(this.checkNull(reciveData.getResponseBody().getUser_email()));
		reciveData.getResponseBody().setIs_email_auth(this.checkNull(reciveData.getResponseBody().getIs_email_auth()));
		reciveData.getResponseBody().setUser_type(this.checkNull(reciveData.getResponseBody().getUser_type()));
		reciveData.getResponseBody().setUser_name(this.checkNull(reciveData.getResponseBody().getUser_name()));
		reciveData.getResponseBody().setIs_rname_auth(this.checkNull(reciveData.getResponseBody().getIs_rname_auth()));
		reciveData.getResponseBody().setRname_auth_sst_code(
				this.checkNull(reciveData.getResponseBody().getRname_auth_sst_code()));
		reciveData.getResponseBody().setBiz_number(this.checkNull(reciveData.getResponseBody().getBiz_number()));
		reciveData.getResponseBody().setBiz_name(this.checkNull(reciveData.getResponseBody().getBiz_name()));
		reciveData.getResponseBody().setIs_biz_auth(this.checkNull(reciveData.getResponseBody().getIs_biz_auth()));
		reciveData.getResponseBody().setUser_mdn(this.checkNull(reciveData.getResponseBody().getUser_mdn()));
		reciveData.getResponseBody().setUser_sex(this.checkNull(reciveData.getResponseBody().getUser_sex()));
		reciveData.getResponseBody().setUser_birthday(this.checkNull(reciveData.getResponseBody().getUser_birthday()));
		reciveData.getResponseBody().setUser_calendar(this.checkNull(reciveData.getResponseBody().getUser_calendar()));
		reciveData.getResponseBody().setUser_zipcode(this.checkNull(reciveData.getResponseBody().getUser_zipcode()));
		reciveData.getResponseBody().setUser_address(this.checkNull(reciveData.getResponseBody().getUser_address()));
		reciveData.getResponseBody().setUser_address2(this.checkNull(reciveData.getResponseBody().getUser_address2()));
		reciveData.getResponseBody().setUser_nation_code(
				this.checkNull(reciveData.getResponseBody().getUser_nation_code()));
		reciveData.getResponseBody().setUser_nation_name(
				this.checkNull(reciveData.getResponseBody().getUser_nation_name()));
		reciveData.getResponseBody().setIs_foreign(this.checkNull(reciveData.getResponseBody().getIs_foreign()));
		reciveData.getResponseBody().setIs_im_changed(this.checkNull(reciveData.getResponseBody().getIs_im_changed()));
		reciveData.getResponseBody().setUser_status_code(
				this.checkNull(reciveData.getResponseBody().getUser_status_code()));
		reciveData.getResponseBody().setIs_parent_approve(
				this.checkNull(reciveData.getResponseBody().getIs_parent_approve()));
		reciveData.getResponseBody().setParent_approve_sst_code(
				this.checkNull(reciveData.getResponseBody().getParent_approve_sst_code()));
		reciveData.getResponseBody().setJoin_sst_list(this.checkNull(reciveData.getResponseBody().getJoin_sst_list()));
		reciveData.getResponseBody()
				.setJoin_path_code(this.checkNull(reciveData.getResponseBody().getJoin_path_code()));
		reciveData.getResponseBody().setJoin_sst_code(this.checkNull(reciveData.getResponseBody().getJoin_sst_code()));
		reciveData.getResponseBody().setJoin_date(this.checkNull(reciveData.getResponseBody().getJoin_date()));
		reciveData.getResponseBody().setJoin_time(this.checkNull(reciveData.getResponseBody().getJoin_time()));
		reciveData.getResponseBody().setJoin_ip(this.checkNull(reciveData.getResponseBody().getJoin_ip()));
		reciveData.getResponseBody().setIs_term(this.checkNull(reciveData.getResponseBody().getIs_term()));
		reciveData.getResponseBody()
				.setTerm_reason_cd(this.checkNull(reciveData.getResponseBody().getTerm_reason_cd()));
		reciveData.getResponseBody().setSst_term(this.checkNull(reciveData.getResponseBody().getSst_term()));
		reciveData.getResponseBody().setLogin_status_code(
				this.checkNull(reciveData.getResponseBody().getLogin_status_code()));
		reciveData.getResponseBody().setLogin_limit_sst_code(
				this.checkNull(reciveData.getResponseBody().getLogin_limit_sst_code()));
		reciveData.getResponseBody().setModify_sst_code(
				this.checkNull(reciveData.getResponseBody().getModify_sst_code()));
		reciveData.getResponseBody().setModify_req_date(
				this.checkNull(reciveData.getResponseBody().getModify_req_date()));
		reciveData.getResponseBody().setModify_req_time(
				this.checkNull(reciveData.getResponseBody().getModify_req_time()));
		reciveData.getResponseBody().setDup_sst(this.checkNull(reciveData.getResponseBody().getDup_sst()));
		reciveData.getResponseBody().setAuth_code(this.checkNull(reciveData.getResponseBody().getAuth_code()));
		reciveData.getResponseBody().setUse_sst(this.checkNull(reciveData.getResponseBody().getUse_sst()));
		reciveData.getResponseBody().setUser_auth_key(this.checkNull(reciveData.getResponseBody().getUser_auth_key()));
		reciveData.getResponseBody().setSign_data(this.checkNull(reciveData.getResponseBody().getSign_data()));
		reciveData.getResponseBody().setUser_passwd(this.checkNull(reciveData.getResponseBody().getUser_passwd()));
		reciveData.getResponseBody().setParent_approve_sst_cd(
				this.checkNull(reciveData.getResponseBody().getParent_approve_sst_cd()));
		reciveData.getResponseBody().setSp_list(this.checkNull(reciveData.getResponseBody().getSp_list()));
		reciveData.getResponseBody().setMobile_sign(this.checkNull(reciveData.getResponseBody().getMobile_sign()));
		reciveData.getResponseBody().setSvc_mng_num(this.checkNull(reciveData.getResponseBody().getSvc_mng_num()));
		reciveData.getResponseBody().setModel_id(this.checkNull(reciveData.getResponseBody().getModel_id()));
		reciveData.getResponseBody().setIdp_user_status_cd(
				this.checkNull(reciveData.getResponseBody().getIdp_user_status_cd()));
		reciveData.getResponseBody().setIm_user_status_cd(
				this.checkNull(reciveData.getResponseBody().getIm_user_status_cd()));
		reciveData.getResponseBody().setPhone_auth_code(
				this.checkNull(reciveData.getResponseBody().getPhone_auth_code()));
		return reciveData;
	}

	private String checkNull(String value) {
		if (value == null)
			return "";
		else
			return value;
	}

	/**
	 * The string to tokenize into an array (util package movie...)
	 * 
	 * @param str
	 * @param seperator
	 *            구분자
	 * @return
	 */
	protected String[] tokenize(String str, String seperator) {

		StringTokenizer tokenizer = new StringTokenizer(str, seperator);
		int i = 0;
		String[] tokens = new String[tokenizer.countTokens()];
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().trim();
			if ((token == null) || (token.length() == 0))
				continue;
			tokens[i++] = token;
		}
		return tokens;
	}
}
