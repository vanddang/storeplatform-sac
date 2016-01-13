package com.skplanet.storeplatform.sac.common.header.extractor;

import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Header Extractor used in application scope
 *
 * @author 정화수 (SK Planet)
 * @since 2016-01-07
 */
@Component
public class HeaderExtractor {

    /**
     * Get Request Header
     * @return Request Header
     */
    public SacRequestHeader getRequestHeader() {
        return (SacRequestHeader) RequestContextHolder.getRequestAttributes().getAttribute( SacRequestHeader.class.getName(), RequestAttributes.SCOPE_REQUEST );
    }

    /**
     * Get Tenant Header
     * @return get Tenant Header
     */
    public TenantHeader getTenantHeader() {
        return getRequestHeader().getTenantHeader();
    }

    /**
     * Get Device Header
     * @return get Device Header
     */
    public DeviceHeader getDeviceHeader() {
        return getRequestHeader().getDeviceHeader();
    }

    /**
     * Get Network Header
     * @return Network Header
     */
    public NetworkHeader getNetworkHeader() {
        return getRequestHeader().getNetworkHeader();
    }

    /**
     * Get UserKey
     * @return User Key
     */
    public String getUserKey() {
        return getRequestHeader().getUserKey();
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
