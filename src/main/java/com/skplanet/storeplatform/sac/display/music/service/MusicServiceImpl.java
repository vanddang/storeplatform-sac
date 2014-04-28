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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.display.music.vo.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;

/**
 * 음악 상세보기
 * <p/>
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
public class MusicServiceImpl implements MusicService {

	protected Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);
    private static String[] MUSIC_RELATED_PROD_META_CLS;
    static {
        MUSIC_RELATED_PROD_META_CLS = new String[]{"CT30", "CT31", "CT32", "CT33"};
    }

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	@Override
	public MusicDetailComposite getMusicDetail(MusicDetailParam param) {
		MusicDetailComposite detailComposite = new MusicDetailComposite();

        // 음악 메타데이터 조회
		MusicDetail musicDetail = this.commonDAO.queryForObject("MusicDetail.getMusicDetail", param, MusicDetail.class);
		if (musicDetail == null)
			return null;

        if(musicDetail.getDeviceModelCd() == null)
            throw new StorePlatformException("SAC_DSP_0012", param.getDeviceModelCd());

        if(musicDetail.getMusicSprtYn().equals("N"))
            throw new StorePlatformException("SAC_DSP_0012", param.getDeviceModelCd());

        // 메뉴 목록 조회
		List<MenuItem> menuList = this.commonService.getMenuItemList(param.getChannelId(), param.getLangCd());

        // 서브컨텐트 조회
		List<SubContent> contentList = this.commonDAO.queryForList("MusicDetail.getSubContentList",
				musicDetail.getEpsdId(), SubContent.class);

        // 관련 상품 목록 조회
        Map<String, Object> relProdListReq = new HashMap<String, Object>();
        relProdListReq.put("channelId", param.getChannelId());
        relProdListReq.put("metaCodes", MUSIC_RELATED_PROD_META_CLS);
        List<RelatedProduct> relProdList = this.commonDAO.queryForList("MusicDetail.getRelatedProductList", relProdListReq, RelatedProduct.class);

        
        String topMenuId = menuList.get(0).getTopMenuId();

        //tmembership 할인율
        TmembershipDcInfo tmembershipDcInfo = commonService.getTmembershipDcRateForMenu(param.getTenantId(), topMenuId);
        if(tmembershipDcInfo != null) {
        	List<Point> pointList = null; 
        	
        	if(tmembershipDcInfo.getNormalDcRate() != null) {
        		pointList = new ArrayList<Point>();
		        Point point = new Point();
		        point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
		        point.setType(DisplayConstants.DC_RATE_TYPE_NORMAL);
		        point.setDiscountRate(tmembershipDcInfo.getNormalDcRate());
		        pointList.add(point);
        	}
        	if(tmembershipDcInfo.getFreepassDcRate() != null) {
        		if(pointList == null) pointList = new ArrayList<Point>();
        		Point point = new Point();
        		point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
        		point.setType(DisplayConstants.DC_RATE_TYPE_FREEPASS);
        		point.setDiscountRate(tmembershipDcInfo.getFreepassDcRate());
        		pointList.add(point);
        	}
	        
        	detailComposite.setPointList(pointList);
        }
        
        detailComposite.setMusicDetail(musicDetail);
		detailComposite.setMenuList(menuList);
		detailComposite.setContentList(contentList);
        detailComposite.setRelatedProductList(relProdList);

		return detailComposite;
	}
}
