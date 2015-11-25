/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * CreateRecommendFeedbackRes Value Object
 * 
 * Updated on : 2014. 1. 23. Updated by : 김현일, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateRecommendFeedbackSacRes extends Feedback {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
