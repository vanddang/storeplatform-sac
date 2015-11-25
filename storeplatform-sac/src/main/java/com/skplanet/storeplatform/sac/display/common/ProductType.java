package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.framework.core.util.StringUtils;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.*;
/**
 * 상품 유형
 * User: joyspring
 * Date: 2014. 4. 29.
 * Time: AM 10:24
 */
public enum ProductType {

    App("app", "DP013301", DP_APP_REPRESENT_IMAGE_CD),
    InApp("iap", "DP013302", DP_APP_REPRESENT_IMAGE_CD),
    Music("music", "DP013303", DP_MUSIC_REPRESENT_IMAGE_CD),
    RingBell("ringbell", "DP013303", DP_MUSIC_REPRESENT_IMAGE_CD),
    Album("album", null, null),
    @Deprecated Vod("vod", null, DP_VOD_REPRESENT_IMAGE_CD),
    VodTv("tv", "DP013304", DP_VOD_REPRESENT_IMAGE_CD),
    VodMovie("movie", "DP013305", DP_VOD_REPRESENT_IMAGE_CD),
    @Deprecated EbookComic("ebookcomic", null, null),
    Ebook("ebook", "DP013306", DP_EBOOK_COMIC_REPRESENT_IMAGE_CD),
    Comic("comic", "DP013307", DP_COMIC_EPISODE_REPRESENT_IMAGE_CD),
    Webtoon("webtoon", "DP013308", DP_WEBTOON_REPRESENT_IMAGE_CD),
    Shopping("shopping", "DP013309", DP_SHOPPING_REPRESENT_IMAGE_CD),
    Voucher("voucher", "DP013310", DP_FREEPASS_THUMBNAIL_IMAGE_CD),

    @Deprecated Freepass("freepass", "DP013310", DP_FREEPASS_THUMBNAIL_IMAGE_CD);

    private String name;
    private String code;
    private String imageCd;

    ProductType(String name, String code, String imageCd) {
        this.name = name;
        this.code = code;
        this.imageCd = imageCd;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return code;
    }

    public String getImageCd() {
        return imageCd;
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
