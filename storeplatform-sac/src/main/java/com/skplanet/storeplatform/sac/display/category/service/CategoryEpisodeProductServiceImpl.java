/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEpisodeProductSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.MusicDetailSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EpisodeProduct;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.GetProductBaseInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import com.skplanet.storeplatform.sac.display.category.vo.CategoryEpisodeProduct;
import com.skplanet.storeplatform.sac.display.category.vo.SearchEpisodeListParam;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.*;

/**
 * Updated on : 2015-07-30. Updated by : 양해엽, SK Planet.
 */
@Service
public class CategoryEpisodeProductServiceImpl implements CategoryEpisodeProductService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CachedExtraInfoManager extraInfoManager;


	@Override
	public CategoryEpisodeProductSacRes searchEpisodeProductList(SearchEpisodeListParam param) {

		ProductBaseInfo baseInfo = extraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(param.getChannelId()));

		if (!isValidRequestId(baseInfo)) {
			throw new StorePlatformException("SAC_DSP_0005", param.getChannelId());
		}

		List<EpisodeProduct> episodeProductList = new ArrayList<EpisodeProduct>();
		List<CategoryEpisodeProduct> categoryEpisodeProductList = getCategoryEpisodeProductList(param);

		for (CategoryEpisodeProduct categoryEpisodeProduct : categoryEpisodeProductList) {
			episodeProductList.add(makeResEpisodeProduct(categoryEpisodeProduct));
		}

		return new CategoryEpisodeProductSacRes(new CommonResponse(episodeProductList.size()), episodeProductList);
	}

	private boolean isValidRequestId(ProductBaseInfo baseInfo) {

		if (baseInfo == null) return false;

		boolean isValid = false;
		ProductType type = baseInfo.getProductType();
		switch (type) {
			case App:
				if (StringUtils.equals(DP_EPISODE_CONTENT_TYPE_CD, baseInfo.getContentsTypeCd()))
					isValid = true;
				break;
			default:
				if (StringUtils.equals(DP_CHANNEL_CONTENT_TYPE_CD, baseInfo.getContentsTypeCd()))
					isValid = true;
				break;
		}

		return isValid;
	}

	private EpisodeProduct makeResEpisodeProduct(CategoryEpisodeProduct cep) {
		EpisodeProduct episodeProduct = new EpisodeProduct();

		episodeProduct.setChannelId(cep.getChannelId());
		episodeProduct.setEpisodeId(cep.getEpisodeId());
		episodeProduct.setProdStatusCd(cep.getProdStatusCd());
		episodeProduct.setPossLendClsfCd(cep.getPossLendClsfCd());
		episodeProduct.setProdGrdCd(cep.getProdGrdCd());
		episodeProduct.setTopMenuId(cep.getTopMenuId());
		episodeProduct.setMetaClsfCd(cep.getMetaClsfCd());
		episodeProduct.setSvcGrpCd(cep.getSvcGrpCd());
		episodeProduct.setSongId(cep.getSongId());

		return episodeProduct;
	}

	private List<CategoryEpisodeProduct> getCategoryEpisodeProductList(SearchEpisodeListParam param) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("tenantId", param.getTenantId());
		reqMap.put("channelId", param.getChannelId());

		return this.commonDAO.queryForList("CategoryEpisodeProduct.getEpisodeProdList", reqMap, CategoryEpisodeProduct.class);
	}
}
