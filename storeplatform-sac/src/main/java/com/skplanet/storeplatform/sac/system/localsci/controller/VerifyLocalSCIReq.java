package com.skplanet.storeplatform.sac.system.localsci.controller;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Updated on : 2016-01-06. Updated by : 양해엽, SK Planet.
 */
public class VerifyLocalSCIReq {

    private static final long serialVersionUID = -1L;

    @NotBlank
    private String localSCIName;

    @NotBlank
    private String invokeMethod;

    private Object invokeParam;

    public String getLocalSCIName() {
        return localSCIName;
    }

    public void setLocalSCIName(String localSCIName) {
        this.localSCIName = localSCIName;
    }

    public String getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(String invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    public Object getInvokeParam() {
        return invokeParam;
    }

    public void setInvokeParam(Object invokeParam) {
        this.invokeParam = invokeParam;
    }
}
