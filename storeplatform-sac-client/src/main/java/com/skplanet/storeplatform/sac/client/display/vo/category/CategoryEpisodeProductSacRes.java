/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.display.vo.category;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EpisodeProduct;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * Updated on : 2015-07-30. Updated by : 양해엽, SK Planet.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryEpisodeProductSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;
	private List<EpisodeProduct> episodeProductList;

	public CategoryEpisodeProductSacRes() {}

	public CategoryEpisodeProductSacRes(CommonResponse commonResponse, List<EpisodeProduct> episodeProductList) {
		this.commonResponse = commonResponse;
		this.episodeProductList = episodeProductList;
	}

	public CommonResponse getCommonResponse() {
		return commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<EpisodeProduct> getEpisodeProductList() {
		return episodeProductList;
	}

	public void setEpisodeProductList(List<EpisodeProduct> episodeProductList) {
		this.episodeProductList = episodeProductList;
	}
}
