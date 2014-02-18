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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetailComposite;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetailParam;
import com.skplanet.storeplatform.sac.display.music.vo.SubContent;

/**
 * 음악 상세보기
 * <p/>
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
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
		MusicDetail musicDetail = this.commonDAO.queryForObject("MusicDetail.getMusicDetail", param, MusicDetail.class);
		if (musicDetail == null)
			return null;

		List<MenuItem> menuList = this.commonService.getMenuItemList(param.getChannelId(), param.getLangCd());
		List<SubContent> contentList = this.commonDAO.queryForList("MusicDetail.getSubContentList",
				musicDetail.getEpsdId(), SubContent.class);

		detailComposite.setMusicDetail(musicDetail);
		detailComposite.setMenuList(menuList);
		detailComposite.setContentList(contentList);

		return detailComposite;
	}
}
