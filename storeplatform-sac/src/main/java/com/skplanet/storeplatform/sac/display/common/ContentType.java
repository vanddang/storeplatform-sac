package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * ContentType
 * User: joyspring
 * Date: 2014. 4. 15.
 * Time: PM 7:10
 */
public enum ContentType {
    Channel(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD),
    Episode(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD),
    Album(DisplayConstants.DP_ALBUM_CONTENT_TYPE_CD)
    ;

    private String code;
    private ContentType(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
    public static ContentType forCode(String code) {
        if(code.equals(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD))
            return Channel;
        else if(code.equals(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD))
            return Episode;
        else
            return null;
    }
}
