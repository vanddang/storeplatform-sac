package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * 
 * FeedbackMy Value Object
 * 
 * Updated on : 2014. 2. 12. Updated by : 김현일, 인크로스.
 */
@JsonPropertyOrder({ "totalCount" })
@JsonIgnoreProperties({ "selfYn", "saleYn" })
public class FeedbackMy extends Feedback {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용후기 전체 갯수.
	 */
	private String totalCount;

	/**
	 * @return String
	 */
	public String getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            totalCount
	 */
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
