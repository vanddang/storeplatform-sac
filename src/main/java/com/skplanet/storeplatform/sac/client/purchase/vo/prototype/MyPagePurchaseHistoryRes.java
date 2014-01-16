package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class MyPagePurchaseHistoryRes extends CommonInfo {
	private static final long serialVersionUID = 201311131L;

	private List<MyPagePurchaseProduct> history;
	private List<TestTime> testTimeList = new ArrayList<TestTime>();

	public List<MyPagePurchaseProduct> getHistory() {
		return this.history;
	}

	public void setHistory(List<MyPagePurchaseProduct> history) {
		this.history = history;
	}

	public List<TestTime> getTestTimeList() {
		return this.testTimeList;
	}

	public void setTestTimeList(List<TestTime> testTimeList) {
		this.testTimeList = testTimeList;
	}
}
