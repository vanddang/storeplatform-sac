/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.display.vo.banner;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner;

import java.util.List;

/**
 * Updated on : 2015-06-30. Updated by : 양해엽, SK Planet.
 */
public class BannerListSacRes extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String startKey;
    private String hasNext; // Y/N
	private Integer count;

    private List<Banner> bannerList;

    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(String startKey) {
        this.startKey = startKey;
    }

    public String getHasNext() {
        return hasNext;
    }

    public void setHasNext(String hasNext) {
        this.hasNext = hasNext;
    }

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }
}
