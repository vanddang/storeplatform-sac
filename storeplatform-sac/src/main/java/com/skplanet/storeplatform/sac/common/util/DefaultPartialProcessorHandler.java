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

/**
 * <p>
 * DefaultPartialProcessorHandler
 * </p>
 * Updated on : 2014. 09. 22 Updated by : 정희원, SK 플래닛.
 */
public abstract class DefaultPartialProcessorHandler<T> implements PartialProcessorHandler<T> {

    @Override
    public T processPaddingItem() {
        return null;
    }

}
