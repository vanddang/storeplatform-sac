package com.skplanet.storeplatform.sac.display.common.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.common.vo.BatchStandardDateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 전시 공통 서비스
 *
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional
public class DisplayCommonServiceImpl implements DisplayCommonService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public String getBatchStandardDateString(String tenantId, String listId) {

        return (String)commonDAO.queryForObject("Common.getBatchStandardDate", new BatchStandardDateRequest(tenantId, listId));
    }

}
