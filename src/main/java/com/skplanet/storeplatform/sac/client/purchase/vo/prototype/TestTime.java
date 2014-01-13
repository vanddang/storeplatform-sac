package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class TestTime extends CommonInfo {
	private static final long serialVersionUID = 201311251L;
	private String type;
	private String description;
	private long time;

	public TestTime() {
	}

	public TestTime(String type, long time, String description) {
		this.type = type;
		this.description = description;
		this.time = time;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
