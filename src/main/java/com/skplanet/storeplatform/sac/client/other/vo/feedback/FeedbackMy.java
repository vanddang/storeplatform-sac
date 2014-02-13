package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * FeedbackMy Value Object
 * 
 * Updated on : 2014. 2. 12. Updated by : 김현일, 인크로스.
 */
@JsonIgnoreProperties({ "selfYn", "saleYn" })
public class FeedbackMy extends Feedback {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
}
