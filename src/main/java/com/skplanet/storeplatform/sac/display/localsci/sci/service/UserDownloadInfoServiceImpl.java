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

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.GetUserDownloadInfoParam;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.UserDownloadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * UserDownloadInfoServiceImpl
 * </p>
 * Updated on : 2015. 03. 16 Updated by : 정희원, SK 플래닛.
 */
@Service
public class UserDownloadInfoServiceImpl implements UserDownloadInfoService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public UserDownloadInfo getUserDownloadInfo(GetUserDownloadInfoParam param) {
        Map<String, Object> req = new HashMap<String, Object>();
        if (Strings.isNullOrEmpty(param.getMdn()) || Strings.isNullOrEmpty(param.getAid())) {
            throw new IllegalArgumentException("mdn, aid는 필수값입니다.");
        }

        String key = DisplayCryptUtils.hashMdnAidKey(param.getMdn(), param.getAid());
        if(Strings.isNullOrEmpty(key))
            throw new IllegalStateException();

        req.put("mdnaidKey", key);
        req.put("aid", param.getAid());

        return commonDAO.queryForObject("UserDownloadInfo.getUserDownloadInfo", req, UserDownloadInfo.class);
    }
}
