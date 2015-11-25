package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ListProductTitle {
	private String prefix; // 제목 앞에 붙는 추가 노출 문구
	private String postfix; // 제목 뒤에 붙는 추가 노출 문구. 예) 열혈강호 59권
	private String title; // 제목

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPostfix() {
		return this.postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
