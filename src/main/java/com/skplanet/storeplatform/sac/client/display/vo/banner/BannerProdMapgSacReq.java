/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.display.vo.banner;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * 배너 상품 매핑 Reqeust Value Object
 * <pre>
 *
 * Updated on : 2015-04-13. Updated By : 양해엽, SK Planet.
 */
public class BannerProdMapgSacReq extends CommonInfo {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String episodeId;

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }
}
