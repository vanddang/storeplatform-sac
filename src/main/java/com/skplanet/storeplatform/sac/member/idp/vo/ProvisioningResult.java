/**
 * 
 */
package com.skplanet.storeplatform.sac.member.idp.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ProvisioningResult extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 처리 완료된 command.
	 */
	private String cmd;

	/**
	 * 처리 결과 Code.
	 */
	private String result;

	/**
	 * 처리 결과 Message.
	 */
	private String resultText;

	/**
	 * 분리보관 유무(Y : 분리보관, N : 정상사용, S : 분리보관 예정)
	 */
	private String deactivateStatus;

	/**
	 * @return cmd
	 */
	public String getCmd() {
		return this.cmd;
	}

	/**
	 * @param cmd
	 *            String
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return result
	 */
	public String getResult() {
		return this.result;
	}

	/**
	 * @param result
	 *            String
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return resultText
	 */
	public String getResultText() {
		return this.resultText;
	}

	/**
	 * @param resultText
	 *            String
	 */
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	/**
	 * @return deactivateStatus
	 */
	public String getDeactivateStatus() {
		return this.deactivateStatus;
	}

	/**
	 * @param deactivateStatus
	 *            String
	 */
	public void setDeactivateStatus(String deactivateStatus) {
		this.deactivateStatus = deactivateStatus;
	}

}
