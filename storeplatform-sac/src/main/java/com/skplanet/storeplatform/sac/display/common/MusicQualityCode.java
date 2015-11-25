package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

public enum MusicQualityCode {

    PD009713(320),
    PD009711(192),
    PD009712(128);

    private int quality;

    MusicQualityCode(int quality) {
        this.quality = quality;
    }

    public String getType() {
        return "audio/mp3-" + quality;
    }

}
