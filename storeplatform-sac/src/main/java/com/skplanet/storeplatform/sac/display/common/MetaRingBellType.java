package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * 링벨(폰꾸미기)상품에 대한 메타 타입
 * User: joyspring
 * Date: 2014. 7. 8.
 * Time: PM 5:39
 */
public enum MetaRingBellType {

    RingNormal(DisplayConstants.DP_MUSIC_NORMAL_COLORRING_META_CLASS_CD),
    RingLong(DisplayConstants.DP_MUSIC_LONG_COLORRING_META_CLASS_CD),
    BellSq(DisplayConstants.DP_MUSIC_NORMAL_BELL_META_CLASS_CD),
    BellHq(DisplayConstants.DP_MUSIC_HIGH_QUALITY_BELL_META_CLASS_CD);

    private String code;
    private MetaRingBellType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static MetaRingBellType forCode(String code) {
        if(StringUtils.isEmpty(code))
            throw new IllegalArgumentException();

        MetaRingBellType res = null;
        for (MetaRingBellType v : values()) {
            if(code.equals(v.code)) {
                res = v;
                break;
            }
        }

        if(res == null)
            throw new IllegalArgumentException(code);

        return res;
    }

}
