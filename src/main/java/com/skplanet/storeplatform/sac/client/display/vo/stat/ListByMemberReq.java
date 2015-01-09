package com.skplanet.storeplatform.sac.client.display.vo.stat;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.display.vo.card.PreferredCategoryReq;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListByMemberReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

	@NotBlank
	private String userKey;
	
	private int startKey = 999999;
	
	private int count = 20;
	
	private List<PreferredCategoryReq> preferredCategoryList;

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public int getStartKey() {
		return startKey;
	}

	public void setStartKey(int startKey) {
		this.startKey = startKey;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public List<PreferredCategoryReq> getPreferredCategoryList() {
		return preferredCategoryList;
	}

	public void setPreferredCategoryList(List<PreferredCategoryReq> preferredCategoryList) {
		this.preferredCategoryList = preferredCategoryList;
	}

}
