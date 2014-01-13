/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Request parameter Exception Handling
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
@SuppressWarnings("unused")
public class ParameterExceptionHandling {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterExceptionHandling.class);

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param ex
	 *            MethodArgumentNotValidException
	 * @return Map<String, String>
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		// if (true) {
		// throw new RuntimeException("");
		// }

		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> errors = bindingResult.getFieldErrors();
		StringBuffer customMessage = new StringBuffer();
		for (FieldError error : errors) {
			customMessage.append("[파라미터오류] " + error.getField() + "-" + error.getDefaultMessage());
		}

		Map<String, String> errResult = new HashMap<String, String>();
		errResult.put("result", customMessage.toString());

		return errResult;
	}

	// @ExceptionHandler(IOException.class)
	// @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	// public Map<String, String> handleIOException(MethodArgumentNotValidException ex) {
	// Map<String, String> errResult = new HashMap<String, String>();
	// return errResult;
	// }
}
