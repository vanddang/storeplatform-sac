/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.header.extractor;

import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Header Extractor used in application scope
 *
 * Updated on : 2016. 01. 07 Updated by : 정화수, SK 플래닛.
 */
@Component
public class HeaderExtractor {

    private static final String REQUEST_HEADER_KEY = SacRequestHeader.class.getName();

    /**
     * Set header for UnitTest
     *
     * @param header SAC header information
     */
    public void setHeader( SacRequestHeader header ) {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute( REQUEST_HEADER_KEY, header );

        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );

    }

    /**
     * Get Request Header
     * @return Request Header
     */
    public SacRequestHeader getHeader() {
        return (SacRequestHeader) RequestContextHolder.getRequestAttributes().getAttribute( REQUEST_HEADER_KEY, RequestAttributes.SCOPE_REQUEST );
    }

    /**
     * Get Tenant Header
     * @return get Tenant Header
     */
    public TenantHeader getTenantHeader() {
        return getHeader().getTenantHeader();
    }

    /**
     * Get Device Header
     * @return get Device Header
     */
    public DeviceHeader getDeviceHeader() {
        return getHeader().getDeviceHeader();
    }

    /**
     * Get Network Header
     * @return Network Header
     */
    public NetworkHeader getNetworkHeader() {
        return getHeader().getNetworkHeader();
    }

    /**
     * Get UserKey
     * @return User Key
     */
    public String getUserKey() {
        return getHeader().getUserKey();
    }

    /**
     * Get Tenant Id
     * @return Tenant Id
     */
    public String getTenantId() {
        return getTenantHeader().getTenantId();
    }

    /**
     * Get System Id
     * @return System Id (ShopClient 3.0, ShopClient 4.0, EbookClient, etc...)
     */
    public String getSystemId() {
        return getTenantHeader().getSystemId();
    }

    /**
     * Get Language Code
     * @return Language Code
     */
    public String getLangCd() {
        return getTenantHeader().getLangCd();
    }

    /**
     * Get Device Model
     * @return Device Model (LG-F350S as "G pro 2", SM-G900S as "Galaxy S5", etc...)
     */
    public String getDeviceModel() {
        return getDeviceHeader().getModel();
    }


}
