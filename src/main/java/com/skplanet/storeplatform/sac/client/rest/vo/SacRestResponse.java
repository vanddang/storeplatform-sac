/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.rest.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * SacRestClient를 호출할 때 사용되는 응답 객체
 *
 * Created on 2014. 6. 30. by 서대영, SK플래닛.
 */
public class SacRestResponse<T> extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int status;
	private String result;
	private String contentType;
	private long contentLength;

	private String guid;

	private String bodyAsText;
	private T body;

	public int getStatus() {
		return this.status;
	}
	public void setStatus(int i) {
		this.status = i;
	}
	public String getGuid() {
		return this.guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getContentType() {
		return this.contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getContentLength() {
		return this.contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	public String getBodyAsText() {
		return this.bodyAsText;
	}
	public void setBodyAsText(String body) {
		this.bodyAsText = body;
	}
	public String getResult() {
		return this.result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}

}
