/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.music.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.music.vo.*;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 음악 상세보기
 * <p/>
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional
public class MusicServiceImpl implements MusicService {

    protected Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private DisplayCommonService commonService;

    @Override
    public MusicDetailComposite getMusicDetail(MusicDetailParam param) {
        MusicDetailComposite detailComposite = new MusicDetailComposite();
        MusicDetail musicDetail = commonDAO.queryForObject("MusicDetail.getMusicDetail", param, MusicDetail.class);
        if(musicDetail == null)
            throw new StorePlatformException("SAC_DSP_9999");

        List<MenuItem> menuList = commonService.getMenuItemList(param.getChannelId(), param.getLangCd());
        List<SubContent> contentList = commonDAO.queryForList("MusicDetail.getSubContentList", musicDetail.getEpsdId(), SubContent.class);

        detailComposite.setMusicDetail(musicDetail);
        detailComposite.setMenuList(menuList);
        detailComposite.setContentList(contentList);

        return detailComposite;
    }
}
