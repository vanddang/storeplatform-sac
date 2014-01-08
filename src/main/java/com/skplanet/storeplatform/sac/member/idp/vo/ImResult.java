package com.skplanet.storeplatform.sac.member.idp.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ImResult extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cmd;
	private String result;
	private String resultText;
	private String imIntSvcNo;
	private String userId;
	private String isCancelAble;
	private String cancelRetUrl;
	private String termRsnCd;
	private String cancelEtc;

	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return this.cmd;
	}

	/**
	 * @param cmd
	 *            the cmd to set
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return this.result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the resultText
	 */
	public String getResultText() {
		return this.resultText;
	}

	/**
	 * @param resultText
	 *            the resultText to set
	 */
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	/**
	 * @return the imIntSvcNo
	 */
	public String getImIntSvcNo() {
		return this.imIntSvcNo;
	}

	/**
	 * @param imIntSvcNo
	 *            the imIntSvcNo to set
	 */
	public void setImIntSvcNo(String imIntSvcNo) {
		this.imIntSvcNo = imIntSvcNo;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the isCancelAble
	 */
	public String getIsCancelAble() {
		return this.isCancelAble;
	}

	/**
	 * @param isCancelAble
	 *            the isCancelAble to set
	 */
	public void setIsCancelAble(String isCancelAble) {
		this.isCancelAble = isCancelAble;
	}

	/**
	 * @return the cancelRetUrl
	 */
	public String getCancelRetUrl() {
		return this.cancelRetUrl;
	}

	/**
	 * @param cancelRetUrl
	 *            the cancelRetUrl to set
	 */
	public void setCancelRetUrl(String cancelRetUrl) {
		this.cancelRetUrl = cancelRetUrl;
	}

	/**
	 * @return the termRsnCd
	 */
	public String getTermRsnCd() {
		return this.termRsnCd;
	}

	/**
	 * @param termRsnCd
	 *            the termRsnCd to set
	 */
	public void setTermRsnCd(String termRsnCd) {
		this.termRsnCd = termRsnCd;
	}

	/**
	 * @return the cancelEtc
	 */
	public String getCancelEtc() {
		return this.cancelEtc;
	}

	/**
	 * @param cancelEtc
	 *            the cancelEtc to set
	 */
	public void setCancelEtc(String cancelEtc) {
		this.cancelEtc = cancelEtc;
	}

}
