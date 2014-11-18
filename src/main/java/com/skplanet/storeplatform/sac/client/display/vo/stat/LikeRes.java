package com.skplanet.storeplatform.sac.client.display.vo.stat;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class LikeRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String statsClsf;
	
	private String statsKey;
	
	private int cntLike;
	
	private int cntShar;
	
	private Object item;

	public String getStatsClsf() {
		return statsClsf;
	}

	public void setStatsClsf(String statsClsf) {
		this.statsClsf = statsClsf;
	}

	public String getStatsKey() {
		return statsKey;
	}

	public void setStatsKey(String statsKey) {
		this.statsKey = statsKey;
	}

	public int getCntLike() {
		return cntLike;
	}

	public void setCntLike(int cntLike) {
		this.cntLike = cntLike;
	}

	public int getCntShar() {
		return cntShar;
	}

	public void setCntShar(int cntShar) {
		this.cntShar = cntShar;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}
	
}
