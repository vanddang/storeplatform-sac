/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.validation;

import java.util.Map;

public interface RequestValidateService {

	void validateInterface(Map<String, Object> headerMap);

	void validateTimestamp(Map<String, Object> headerMap);

}
