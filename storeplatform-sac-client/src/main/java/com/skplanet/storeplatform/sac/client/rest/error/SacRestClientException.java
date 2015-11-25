package com.skplanet.storeplatform.sac.client.rest.error;

import org.apache.commons.lang3.StringUtils;

public class SacRestClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private SacRestClientError error;

	public SacRestClientException(SacRestClientError errorInfo) {
		super(errorInfo.getCode());
		this.error = errorInfo;
	}

	public SacRestClientException(SacRestClientError errorInfo, Throwable cause) {
		super(errorInfo.getCode(), cause);
		this.error = errorInfo;

	}

	public SacRestClientException(String code, Object... args) {
		this(code, null, args);

	}

	public SacRestClientException(String code, Throwable cause, Object... args) {
		super(code, cause);
		this.error = new SacRestClientError();
		this.error.setCode(code);
		this.error.setArgs(args);
	}

	public SacRestClientError getError() {
		return this.error;
	}

	public void setError(SacRestClientError errorInfo) {
		this.error = errorInfo;
	}

	public String getCode() {
		if (this.error == null || StringUtils.isEmpty(this.error.getCode())) {
			return null;
		}
		return this.error.getCode();
	}


}