/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.util;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * <p>
 * MetaInfo -> Product 변환 처리 핸들러
 * </p>
 * Updated on : 2014. 04. 08 Updated by : 정희원, SK 플래닛.
 */
public interface MetaMapper {
    Product processRow(MetaInfo meta);
}
