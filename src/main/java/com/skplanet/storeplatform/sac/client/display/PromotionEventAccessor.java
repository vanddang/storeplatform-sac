/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;

import java.util.List;

/**
 * <p>
 * PromotionEventAccessor
 * AOP로 이벤트 프로모션을 처리할 수 있으려면 이 인터페이스가 구현되어야 한다.
 * </p>
 * Updated on : 2015. 08. 20 Updated by : 정희원, SK 플래닛.
 */
public interface PromotionEventAccessor {

    List<Menu> getMenuList();

    List<Identifier> getIdentifierList();

    List<Point> getPointList();

    void setPointList(List<Point> pointList);
}
