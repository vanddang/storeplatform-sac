package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역숨김처리 컴포넌트 요청.
 * 
 * Updated on : 2014. 12. 20. Updated by : 조용진, 엔텔스.
 */
public class HidingListSac extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String prchsId; // 구매아이디
	@Min(1)
	@NotNull
	private Integer prchsDtlId; // 상품 아이디
	// @NotNull
	// @NotEmpty
	private String sendYn; // 미보유상품 숨김처리 Y, 보유상품 숨김처리 N
	@NotNull
	@NotEmpty
	private String hidingYn; // 숨김여부

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the prchsDtlId
	 */
	public Integer getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(Integer prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

	/**
	 * @return the sendYn
	 */
	public String getSendYn() {
		return this.sendYn;
	}

	/**
	 * @param sendYn
	 *            the sendYn to set
	 */
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}

	/**
	 * @return the hidingYn
	 */
	public String getHidingYn() {
		return this.hidingYn;
	}

	/**
	 * @param hidingYn
	 *            the hidingYn to set
	 */
	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}

}
