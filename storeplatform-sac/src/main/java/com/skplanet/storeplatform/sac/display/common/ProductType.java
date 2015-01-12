package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * 상품 유형
 * User: joyspring
 * Date: 2014. 4. 29.
 * Time: AM 10:24
 */
public enum ProductType {
    App("app"),
    Music("music"),
    Album("album"),
    Shopping("shopping"),
    Freepass("freepass"),
    Vod("vod"),
    EbookComic("ebookcomic"),
    Webtoon("webtoon"),
    RingBell("ringbell");

    private String name;
    private ProductType(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public static ProductType forName(String name) {
        ProductType v = null;
        if(StringUtils.isEmpty(name))
            throw new IllegalArgumentException("name cannot be null.");

        String name2 = name.toLowerCase();

        for (ProductType i : values()) {
            if (i.getName().equals(name2)) {
                v = i;
                break;
            }
        }

        if(v == null)
            throw new IllegalArgumentException();

        return v;
    }
}
