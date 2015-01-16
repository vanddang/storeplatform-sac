/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.Banner;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacRes;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.BannerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BannerInfoServiceImpl
 *
 * Created by 1002184 on 2015-01-14.
 */
@Service
public class BannerInfoServiceImpl implements BannerInfoService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    /**
     * 요청한 배너의 디폴트 갯수
     */
    private final int BANNER_DEFAULT_COUNT = 10;

    @Override
    public BannerInfoSacRes getBannerInfoList(BannerInfoSacReq req) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tenantId", req.getTenantId());
        paramMap.put("bnrMenuId", req.getBnrMenuId());
        paramMap.put("bnrExpoMenuId", req.getBnrExpoMenuId());
        paramMap.put("imgSizeCd", req.getImgSizeCd());
        paramMap.put("count", req.getCount() != null ? req.getCount() : BANNER_DEFAULT_COUNT);

        List<BannerInfo> bannerInfoList = this.commonDAO.queryForList("BannerInfo.getBannerInfoList", paramMap, BannerInfo.class);
        if (bannerInfoList == null || bannerInfoList.isEmpty()) {
            throw new StorePlatformException("SAC_DSP_0009");
        }

        BannerInfoSacRes res = new BannerInfoSacRes();
        res.setBannerList(makeBannerList(bannerInfoList));

        return res;
    }

    private List<Banner> makeBannerList(final List<BannerInfo> bannerInfoList) {

        if (bannerInfoList == null) { return null; }

        List<Banner> bannerList = new ArrayList<Banner>();
        for(BannerInfo bannerInfo : bannerInfoList) {
            bannerList.add(makeBanner(bannerInfo));
        }

        return bannerList;
    }

    private Banner makeBanner(final BannerInfo bannerInfo) {
        Banner banner = new Banner();

        banner.setBnrId(bannerInfo.getBnrId());
        banner.setBnrInfoTypeCd(bannerInfo.getBnrInfoTypeCd());
        banner.setImgSizeCd(bannerInfo.getImgSizeCd());
        banner.setTitle(bannerInfo.getBnrNm());
        banner.setImagePath(bannerInfo.getImgPath());
        banner.setLinkUrl(bannerInfo.getBnrInfo());
        banner.setBackColorCd(bannerInfo.getBgColorCd());

        return banner;
    }
}
