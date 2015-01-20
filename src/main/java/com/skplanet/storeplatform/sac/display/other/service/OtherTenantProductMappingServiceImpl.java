/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * OtherTenantProductMappingServiceImpl
 * </p>
 * Updated on : 2015. 01. 19 Updated by : 정희원, SK 플래닛.
 */
@Service
public class OtherTenantProductMappingServiceImpl implements OtherTenantProductMappingService {

    public static final String TENID_TSTORE = "S01";
    public static final String TENID_KT = "S02";
    public static final String TENID_LG = "S03";

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public TenantProductMapping getTenantProductMapping(String prodId) {

        List<SapProductMapping> list = commonDAO.queryForList("OtherTenantProductMapping.getTenantProductMapping", prodId, SapProductMapping.class);
        if(list.size() == 0)
            return null;
        if(list.size() > 1)
            throw new IllegalStateException("조회된 매핑정보가 유효하지 않습니다.");

        SapProductMapping sapMapg = list.get(0);
        if(sapMapg.getProdId().equals(prodId)) {
            return new TenantProductMapping(TENID_TSTORE, sapMapg.getProdId());
        }
        else if(sapMapg.getKtProdId().equals(prodId)) {
            return new TenantProductMapping(TENID_KT, sapMapg.getProdId());
        }
        else if(sapMapg.getLgProdId().equals(prodId)) {
            return new TenantProductMapping(TENID_LG, sapMapg.getProdId());
        }
        else
            throw new IllegalStateException();
    }

    /**
     * getTenantProductMapping VO
     */
    static class SapProductMapping {
        private String prodId;
        private String ktProdId;
        private String lgProdId;

        public String getProdId() {
            return prodId;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }

        public String getKtProdId() {
            return ktProdId;
        }

        public void setKtProdId(String ktProdId) {
            this.ktProdId = ktProdId;
        }

        public String getLgProdId() {
            return lgProdId;
        }

        public void setLgProdId(String lgProdId) {
            this.lgProdId = lgProdId;
        }
    }
}
