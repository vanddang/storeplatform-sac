/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.display.localsci.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 16 Updated by : 정희원, SK 플래닛.
 */
@SCI
public interface IapProductInfoSCI {

    public IapProductInfoRes getIapProductInfo(@Validated IapProductInfoReq req);

}
