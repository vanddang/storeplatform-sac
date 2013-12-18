package com.skplanet.storeplatform.sac.common.exception.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.integration.Message;

import com.skplanet.storeplatform.framework.core.exception.ErrorResponse;
import com.skplanet.storeplatform.framework.core.proxy.InvalidatedRequestException;
import com.skplanet.storeplatform.framework.integration.exception.ExceptionMapper;
import com.skplanet.storeplatform.framework.integration.exception.MessageProvider;

/**
 * InvalidatedRequestExceptionMapper.
 * 
 * Updated on : 2013. 11. 15. Updated by : 김상호, 에이엔비.
 */
@MessageProvider
public class InvalidatedRequestExceptionMapper implements ExceptionMapper<InvalidatedRequestException> {

	@Override
	public ErrorResponse process(InvalidatedRequestException exception, Message<?> failedMessage) {

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

		errorResponse.setEntity("잘못된 요청입니다." + exception.getValidatedMessageList());

		return errorResponse;
	}

}
