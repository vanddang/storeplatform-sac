/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.DPSapMappingVO;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <p>
 * DpSapProdMapgImpl
 * </p>
 * Updated on : 2014. 11. 04 Updated by : 정희원, SK 플래닛.
 */
@Service
public class DpSapProdMapgServiceImpl implements DpSapProdMapgService {

    @Autowired
    @Qualifier("cmsApp")
    private CommonDAO commonDAO;


    @Override
    public void insertDPSapProdMapg(DPSapMappingVO vo) {
        commonDAO.insert("DPSapProdMapg.insertDPSapProdMapg", vo);
    }

    @Override
    public void deleteDPSapProdMapg(String prodId) {
        commonDAO.delete("DPSapProdMapg.deleteDPSapProdMapg", prodId);
    }
}
