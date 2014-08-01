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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.ProductSubInfo;
import com.skplanet.storeplatform.sac.display.common.vo.RawSubInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * <p>
 * ProvisioningServiceImpl.
 * 단말기 속성 및 사용 가능 기능 확인
 * </p>
 * Updated on : 2014. 07. 09 Updated by : 정희원, SK 플래닛.
 */
@Service
public class ProvisioningServiceImpl implements ProvisioningService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    private Set<String> AVAILABLE_CHNL_STAT;
    private Set<String> AVAILABLE_EPSD_STAT;

    @PostConstruct
    public void init() {
        AVAILABLE_CHNL_STAT = new HashSet<String>(Arrays.asList("PD000402,PD000403,PD000404".split(",")));
        AVAILABLE_EPSD_STAT = new HashSet<String>(Arrays.asList("PD000403,PD000404,PD000409".split(",")));
    }


    @Override
    public boolean selectDownloadable(String tenantId, String epsdId, String chnlProdStat, String epsdProdStat, boolean isPurchased) {
        // chnlStat in ['PD000402', 'PD000403', 'PD000404']
        // epsdStat in ['PD000403', 'PD000404', 'PD000409']
        if(isPurchased) {
            // 판매상태가 특별한 경우 제외하고는 가능
            return epsdProdStat.equals(DisplayConstants.DP_SALE_STAT_RESTRIC_DN) ||
                    epsdProdStat.equals(DisplayConstants.DP_SALE_STAT_DROP_REQ_DN); // FIXME
        }

        if(!AVAILABLE_CHNL_STAT.contains(chnlProdStat))
            return false;

        if(!AVAILABLE_EPSD_STAT.contains(epsdProdStat))
            return false;

        // 테넌트 가격정보 조회
        Map<String, String> req = new HashMap<String, String>();
        req.put("tenantId", tenantId);
        req.put("epsdId", epsdId);
        Integer prc = commonDAO.queryForInt("DisplayCommon.selectTenantPrice", req);
        return prc != null;
    }

    @Override
    public ProductSubInfo selectSubInfo(String tenantId, String chnlId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("chnlId", chnlId);

        List<RawSubInfo> rawSubInfos = this.commonDAO.queryForList("DisplayCommon.selectRawSubInfo", req, RawSubInfo.class);
        if (CollectionUtils.isEmpty(rawSubInfos)) {
            return null;
        }

        ProductSubInfo info = new ProductSubInfo();
        boolean procCmn = false;
        for (RawSubInfo v : rawSubInfos) {
            // common
            if (!procCmn) {
                info.setChnlProdStatusCd(v.getChnlProdStatusCd());
                info.setEpsdProdStatusCd(v.getEpsdProdStatusCd());
                info.setEpsdPlayTm(v.getEpsdPlayTm());
                procCmn = true;
            }

            // store, play

            // nm,sd,hd
        }

        return info;
    }
}
