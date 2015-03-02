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

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * PartialProcessor. 다수 데이터 처리시 특정 갯수 단위로 처리하는 기능을 제공한다.
 * </p>
 * Updated on : 2014. 09. 18 Updated by : 정희원, SK 플래닛.
 */
public class PartialProcessor {

    public static final int DEFAULT_PARTIAL_WINDOW_SIZE = 1000;

    public static <T> void process(Collection<T> list, PartialProcessorHandler<T> handler, int windowSize) {
        if(handler == null)
            throw new IllegalArgumentException("PartialProcessorHandler cannot be null.");

        if(CollectionUtils.isEmpty(list))
            return;

        boolean doPadding = handler.processPaddingItem() != null;
        int size = list.size();
        List<T> data = new ArrayList<T>(list);
        int loopCnt = size / windowSize + (size % windowSize == 0 ? 0 : 1);

        for(int lp = 0; lp < loopCnt; ++lp) {

            int toIdx;
            int paddingCnt = 0;
            List<T> partialList;
            toIdx = (lp + 1) * windowSize;
            if(toIdx > size) {
                paddingCnt = toIdx - size;
                toIdx = size;
            }

            partialList = new ArrayList<T>(data.subList(lp * windowSize, toIdx));

            // padding 처리
            if(doPadding && paddingCnt > 0) {
                for(int j=0; j<paddingCnt; ++j) {
                    partialList.add(handler.processPaddingItem());
                }
            }

            // ===== 처리 부분
            handler.processPartial(partialList);
        }
    }

    public static <T> void process(Collection<T> list, PartialProcessorHandler<T> handler) {
        process(list, handler, DEFAULT_PARTIAL_WINDOW_SIZE);
    }

}
