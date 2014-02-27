package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ID 찾기
 * 
 * Updated on : 2014. 2. 10. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchIdSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private String hostName;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	private List<SearchIdSac> searchIdList;

	public List<SearchIdSac> getSearchIdList() {
		return this.searchIdList;
	}

	public void setSearchIdList(List<SearchIdSac> searchIdList) {
		this.searchIdList = searchIdList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
