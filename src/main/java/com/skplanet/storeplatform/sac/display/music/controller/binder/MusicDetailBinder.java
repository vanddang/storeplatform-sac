/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.music.controller.binder;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.music.vo.SubContent;

import java.util.List;

/**
 * MusicDetailBinder
 * Updated on : 2014. 01. 06 Updated by : 정희원, SK 플래닛.
 */
public interface MusicDetailBinder {
    void mapMusic(Product product, MusicDetail musicDetail, List<SubContent> contentList);

    void mapThumbnail(Product product, MusicDetail musicDetail);

    void mapMenu(Product product, List<MenuItem> menuList);

    void mapBasicInfo(Product product, MusicDetail musicDetail);
}
