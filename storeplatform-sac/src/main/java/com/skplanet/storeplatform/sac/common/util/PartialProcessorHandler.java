/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.util;

import java.util.List;

/**
 * <p>
 * PartialProcessorHandler
 * </p>
 * Updated on : 2014. 09. 18 Updated by : 정희원, SK 플래닛.
 */
public interface PartialProcessorHandler<T> {

    /**
     * 패딩이 필요한 경우 패딩값을 응답해준다.
     * @return 패딩값. null을 응답하는 경우 패딩 처리가 되지 않는다.
     */
    T processPaddingItem();

    /**
     * 부분 처리용 함수를 구현한다.
     * @param partialList 처리해야 할 부분 목록 데이터
     */
    void processPartial(List<T> partialList);

}
