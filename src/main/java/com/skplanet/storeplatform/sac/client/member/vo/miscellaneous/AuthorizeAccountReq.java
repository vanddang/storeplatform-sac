package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 결제 계좌 정보 인증
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeAccountReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 예금주명
	 */
	@NotBlank(message = "필수 파라미터 입니다.")
	private String bankAcctName;

	/**
	 * 은행 코드
	 */
	@NotBlank(message = "필수 파라미터 입니다.")
	private String bankCode;

	/**
	 * 계좌 번호
	 */
	@NotBlank(message = "필수 파라미터 입니다.")
	private String bankAccount;

	/**
	 * @return the bankAcctName
	 */
	public String getBankAcctName() {
		return this.bankAcctName;
	}

	/**
	 * @param bankAcctName
	 *            the bankAcctName to set
	 */
	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * @param bankCode
	 *            the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return this.bankAccount;
	}

	/**
	 * @param bankAccount
	 *            the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

}
