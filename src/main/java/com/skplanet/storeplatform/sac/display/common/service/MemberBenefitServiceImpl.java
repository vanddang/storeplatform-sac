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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.other.common.constant.OtherConstants;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceDataService;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceTypeService;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

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
    private SacServiceDataService sacServiceDataService;
    
	@Autowired
	private SacServiceTypeService typeSvc;
    
    @Override
    public MileageInfo getMileageInfo(String tenantId, String topMenuId, String chnlId, Integer prodAmt) {
        Map<String, Object> req = new HashMap<String, Object>();
        if(StringUtil.isEmpty(tenantId) || StringUtil.isEmpty(topMenuId)) return null;

        //Tstore멤버십 적립율 정보
        //TODO : 단말 모델 별 서비스 제공여부 추가
        SacService sacService = new SacService();
        sacService.setServiceCd(OtherConstants.SAC_SERVICE_MILEAGE);
        SacService rtnSacService = sacServiceDataService.getServiceActive(sacService);
        
        MileageInfo mileageInfo = null; 
        if(rtnSacService.isActive()) {
	        req.put("tenantId", tenantId);
	        req.put("topMenuId", topMenuId);
	        req.put("chnlId", StringUtil.nvl(chnlId, ""));
	
	        mileageInfo = commonDAO.queryForObject("MemberBenefit.getMileageInfo", req, MileageInfo.class);
	
	        //Tstore멤버십 적립율 정보
	        //예외 상품이 아닌 경우 무료 상품은 적립율을 노출하지 않는다.
	        //무료 상품 && 카테고리 => 마일리지 비노출
	        if (prodAmt == null || prodAmt == 0
	        		&& StringUtils.equals(mileageInfo.getPolicyTargetCd(), DisplayConstants.POLICY_TARGET_CD_CATEGORY)
	        		) {
	        	mileageInfo = null;
	        }
        }
        
        if(mileageInfo != null)
            return mileageInfo;
        else
            return new MileageInfo();
    }
}
