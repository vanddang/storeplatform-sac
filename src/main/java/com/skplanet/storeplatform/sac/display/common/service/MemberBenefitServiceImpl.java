/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.service;

import com.google.common.base.Stopwatch;
import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.GetPromotionEventParam;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.other.common.constant.OtherConstants;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceService;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceTypeService;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * MemberBenefitServiceImpl
 * </p>
 * Updated on : 2014. 07. 25 Updated by : 정희원, SK 플래닛.
 */
@Service
public class MemberBenefitServiceImpl implements MemberBenefitService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private SacServiceService sacServiceDataService;
    
	@Autowired
	private SacServiceTypeService typeSvc;

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

    private static final Logger logger = LoggerFactory.getLogger(MemberBenefitServiceImpl.class);
    
    @Override
    public MileageInfo getMileageInfo(String tenantId, String topMenuId, String chnlId, Integer prodAmt) {
//        Map<String, Object> req = new HashMap<String, Object>();
        if(StringUtil.isEmpty(tenantId) || StringUtil.isEmpty(topMenuId)) return null;

        //Tstore멤버십 적립율 정보
        //TODO : 단말 모델 별 서비스 제공여부 추가
        SacService sacService = new SacService();
        sacService.setServiceCd(OtherConstants.SAC_SERVICE_MILEAGE);
        SacService rtnSacService = sacServiceDataService.getServiceActive(sacService);
        
        MileageInfo mileageInfo = null; 
        if(rtnSacService != null && rtnSacService.isActive()) {
//	        req.put("tenantId", tenantId);
//	        req.put("topMenuId", topMenuId);
//	        req.put("chnlId", StringUtil.nvl(chnlId, ""));

            Stopwatch stopwatch = Stopwatch.createStarted();
            PromotionEvent event = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam(tenantId, topMenuId, chnlId));
            logger.debug("Event fetch from redis - {}", stopwatch.stop());

            if (event != null) {
                mileageInfo = new MileageInfo();
                mileageInfo.setPolicyTargetCd(event.getTargetTp());
                mileageInfo.setRateLv1(event.getRateGrd1());
                mileageInfo.setRateLv2(event.getRateGrd2());
                mileageInfo.setRateLv3(event.getRateGrd3());
            }

//            commonDAO.queryForObject("MemberBenefit.getMileageInfo", req, MileageInfo.class);
//            logger.debug("Event fetch from db - {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS) - elapsedBefore);
//            stopwatch.stop();

            //노출 여부. ResponseInfoGenerateFacadeImpl.appendMileageInfo 에서 처리.
	        /*
	        if(mileageInfo != null) {
		        //Tstore멤버십 적립율 정보
		        //예외 상품이 아닌 경우 무료 상품은 적립율을 노출하지 않는다.
		        //무료 상품 && 카테고리 => 마일리지 비노출
		        if (prodAmt == null || prodAmt == 0
		        		&& StringUtils.equals(mileageInfo.getPolicyTargetCd(), DisplayConstants.POLICY_TARGET_CD_CATEGORY)
		        		) {
		        	mileageInfo = null;
		        }
	        }
	        */
        }
        
        if(mileageInfo != null)
            return mileageInfo;
        else
            return new MileageInfo();
    }

    // TODO Phase 2에서 사용 예정
    @Override
    public MileageInfo getMileageInfo(String tenantId, String menuId, String chnlId) {

        if(StringUtil.isEmpty(tenantId) || StringUtil.isEmpty(menuId))
            return null;

        SacService sacService = new SacService();
        sacService.setServiceCd(OtherConstants.SAC_SERVICE_MILEAGE);
        SacService rtnSacService = sacServiceDataService.getServiceActive(sacService);

        /* TODO
        if(rtnSacService == null || !rtnSacService.isActive())
            return new MileageInfo();
        */

        Stopwatch stopwatch = Stopwatch.createStarted();
        PromotionEvent event = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam(tenantId, menuId, chnlId));
        logger.debug("Event fetch from redis - {}", stopwatch.stop());

        if (event == null)
            return new MileageInfo();

        MileageInfo mileageInfo = new MileageInfo();
        mileageInfo.setPolicyTargetCd(event.getTargetTp());
        mileageInfo.setRateLv1(event.getRateGrd1());
        mileageInfo.setRateLv2(event.getRateGrd2());
        mileageInfo.setRateLv3(event.getRateGrd3());

        return mileageInfo;
    }

    /* (non-Javadoc)
         * @see com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService#checkFreeProduct(com.skplanet.storeplatform.sac.display.common.vo.MileageInfo)
         * 상세 조회 API에서 참조
         */
	@Override
	public MileageInfo checkFreeProduct(MileageInfo mileageInfo, Integer prodAmt) {
		if(mileageInfo != null) {
	        //Tstore멤버십 적립율 정보
	        //예외 상품이 아닌 경우 무료 상품은 적립율을 노출하지 않는다.
	        //무료 상품 && 카테고리 => 마일리지 비노출
	        if (prodAmt == null || prodAmt == 0
	        		&& StringUtils.equals(mileageInfo.getPolicyTargetCd(), DisplayConstants.POLICY_TARGET_CD_CATEGORY)
	        		) {
	        	mileageInfo = null;
	        }
        }
		return mileageInfo;
	}
}
