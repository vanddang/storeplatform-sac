package com.skplanet.storeplatform.sac.external.idp;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.external.idp.vo.IDPReceiverM;

public class IDPReceiver {

	private static final Logger logger = LoggerFactory.getLogger(IDPReceiver.class);

	/**
	 * 
	 * @param reciveData
	 * @return
	 * @throws Exception
	 */
	public IDPReceiverM nullValue(IDPReceiverM reciveData) throws Exception {

		reciveData.getResponseHeader().setCmd(this.checkNull(reciveData.getResponseHeader().getCmd()));
		reciveData.getResponseHeader().setResult(this.checkNull(reciveData.getResponseHeader().getResult()));
		reciveData.getResponseHeader().setResult_text(this.checkNull(reciveData.getResponseHeader().getResult_text()));

		if (null != reciveData.getResponseBody()) {
			reciveData.getResponseBody().setJoin_type(this.checkNull(reciveData.getResponseBody().getJoin_type()));
			reciveData.getResponseBody().setMobile_sign(this.checkNull(reciveData.getResponseBody().getMobile_sign()));
			reciveData.getResponseBody().setSign_data(this.checkNull(reciveData.getResponseBody().getSign_data()));
			reciveData.getResponseBody().setImage_url(this.checkNull(reciveData.getResponseBody().getImage_url()));
			reciveData.getResponseBody().setImage_sign(this.checkNull(reciveData.getResponseBody().getImage_sign()));
			reciveData.getResponseBody().setEmail_auth_link(
					this.checkNull(reciveData.getResponseBody().getEmail_auth_link()));
			reciveData.getResponseBody().setUser_key(this.checkNull(reciveData.getResponseBody().getUser_key()));
			reciveData.getResponseBody().setUser_id(this.checkNull(reciveData.getResponseBody().getUser_id()));
			reciveData.getResponseBody().setUser_auth_key(
					this.checkNull(reciveData.getResponseBody().getUser_auth_key()));
			reciveData.getResponseBody().setUser_mdn(this.checkNull(reciveData.getResponseBody().getUser_mdn()));
			reciveData.getResponseBody().setUser_email(this.checkNull(reciveData.getResponseBody().getUser_email()));
			reciveData.getResponseBody().setUser_job_code(
					this.checkNull(reciveData.getResponseBody().getUser_job_code()));
			reciveData.getResponseBody()
					.setUser_zipcode(this.checkNull(reciveData.getResponseBody().getUser_zipcode()));
			reciveData.getResponseBody()
					.setUser_address(this.checkNull(reciveData.getResponseBody().getUser_address()));
			reciveData.getResponseBody().setUser_sex(this.checkNull(reciveData.getResponseBody().getUser_sex()));
			reciveData.getResponseBody().setUser_birthday(
					this.checkNull(reciveData.getResponseBody().getUser_birthday()));
			reciveData.getResponseBody().setUser_social_number(
					this.checkNull(reciveData.getResponseBody().getUser_social_number()));
			reciveData.getResponseBody().setUser_name(this.checkNull(reciveData.getResponseBody().getUser_name()));
			reciveData.getResponseBody().setUser_phone(this.checkNull(reciveData.getResponseBody().getUser_phone()));
			reciveData.getResponseBody().setId_list(this.checkNull(reciveData.getResponseBody().getId_list()));
			reciveData.getResponseBody().setTemp_passwd(this.checkNull(reciveData.getResponseBody().getTemp_passwd()));
			reciveData.getResponseBody().setUser_calendar(
					this.checkNull(reciveData.getResponseBody().getUser_calendar()));
			reciveData.getResponseBody().setUser_tel(this.checkNull(reciveData.getResponseBody().getUser_tel()));
			reciveData.getResponseBody().setUser_type(this.checkNull(reciveData.getResponseBody().getUser_type()));
			reciveData.getResponseBody().setIs_foreign(this.checkNull(reciveData.getResponseBody().getIs_foreign()));
			reciveData.getResponseBody().setUser_nation(this.checkNull(reciveData.getResponseBody().getUser_nation()));
			reciveData.getResponseBody().setIs_rname_auth(
					this.checkNull(reciveData.getResponseBody().getIs_rname_auth()));
			reciveData.getResponseBody().setSvc_mng_num(this.checkNull(reciveData.getResponseBody().getSvc_mng_num()));
			reciveData.getResponseBody().setStatus(this.checkNull(reciveData.getResponseBody().getStatus()));
			reciveData.getResponseBody().setUser_calendar(
					this.checkNull(reciveData.getResponseBody().getUser_calendar()));
			reciveData.getResponseBody().setId_type(this.checkNull(reciveData.getResponseBody().getId_type()));
			reciveData.getResponseBody().setSp_token(this.checkNull(reciveData.getResponseBody().getSp_token()));
		}
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
	public String[] tokenize(String str, String seperator) {

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
