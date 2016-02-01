package com.skplanet.storeplatform.sac.display.cache;

/**
 * Updated on : 2016-01-19. Updated by : 양해엽, SK Planet.
 */
public enum RedisKeys {
    PKG_TO_PROD("pkgToProd:v1"),
    PKGS_IN_PROD("pkgInProd:v1"),
    SPRT_DEV("sprtDev:v1"),
    ;

    private String prefix;

    RedisKeys(String prefix) {
        this.prefix = prefix;
    }

    public String getKey(String key) {
        return prefix + ":" + key;
    }
}
