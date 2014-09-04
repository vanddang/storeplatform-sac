/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.related.vo.BoughtTogetherProduct;

import java.util.List;

/**
 * BoughtTogetherProductDataService 인터페이스
 * <pre>
 * Created on 2014. 02. 18. by 서대영, SK 플래닛
 * </pre>
 */
public interface BoughtTogetherProductDataService {

	List<ProductBasicInfo> selectList(BoughtTogetherProduct product);

}
