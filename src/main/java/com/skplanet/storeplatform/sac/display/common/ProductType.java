package com.skplanet.storeplatform.sac.display.common;

/**
 * 상품 유형
 * User: joyspring
 * Date: 2014. 4. 29.
 * Time: AM 10:24
 */
public enum ProductType {
    App("app"),
    Music("music"),
    Shopping("shopping"),
    Freepass("freepass"),
    Vod("vod"),
    EbookComic("ebookcomic"),
    Webtoon("webtoon");

    private String name;
    private ProductType(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public static ProductType forName(String name) {
        ProductType v = null;
        if(name == null)
            throw new IllegalStateException("name cannot be null.");
        String name2 = name.toLowerCase();

        for (ProductType i : values()) {
            if (i.getName().equals(name2)) {
                v = i;
                break;
            }
        }

        return v;

    }
}
