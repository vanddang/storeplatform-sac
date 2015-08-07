package com.skplanet.storeplatform.sac.display.openapi.vo;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.MusicDetailSendSmsSacRes;

public class MusicDetailSendSmsRes {
    public static enum ResultCode {
        SENT(),
        ERROR();
    }

    private ResultCode resultCode;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
