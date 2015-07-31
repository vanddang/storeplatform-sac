package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * 상품 유형
 * User: joyspring
 * Date: 2014. 4. 29.
 * Time: AM 10:24
 */
public enum ProductType {

    App("app", "DP013301"),
    InApp("iap", "DP013302"),
    Music("music", "DP013303"),
    Vod("vod", null),
    VodTv("tv", "DP013304"),
    VodMovie("movie", "DP013305"),
    EbookComic("ebookcomic", null),
    Ebook("ebook", "DP013306"),
    Comic("comic", "DP013307"),
    Webtoon("webtoon", "DP013308"),
    Shopping("shopping", "DP013309"),
    Voucher("voucher", "DP013310"),
    @Deprecated Freepass("freepass", "DP013310"),
    Album("album", null),
    RingBell("ringbell", null);

    private String name;
    private String code;

    ProductType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return code;
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
