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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTenantProductMappingRes;
import com.skplanet.storeplatform.sac.common.util.SacBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.skplanet.storeplatform.sac.client.display.vo.other.OtherTenantProductMappingRes.TenantProductMapping;

/**
 * <p>
 * OtherTenantProductMappingServiceImpl
 * </p>
 * Updated on : 2015. 01. 19 Updated by : 정희원, SK 플래닛.
 */
@Service
public class OtherTenantProductMappingServiceImpl implements OtherTenantProductMappingService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public OtherTenantProductMappingRes getTenantProductMapping(String prodId) {

        SapProductMapping mapg = commonDAO.queryForObject("OtherTenantProductMapping.getTenantProductMapping", prodId, SapProductMapping.class);
        if(mapg == null)
            throw new StorePlatformException("SAC_DSP_0005", prodId);

        OtherTenantProductMappingRes res = new OtherTenantProductMappingRes();
        res.setTenantProductMappingList(new ArrayList<TenantProductMapping>());

        List<TenantSalesStatus> tenantSalesStatusList = commonDAO.queryForList("OtherTenantProductMapping.getTenantSalesStatus", prodId, TenantSalesStatus.class);
        for (TenantSalesStatus tenantSalesStatus : tenantSalesStatusList) {
            String tProdId = SacBeanUtils.getProperty(mapg, "prodId" + tenantSalesStatus.getTenantId());
            if(tProdId == null)
                continue;

            res.getTenantProductMappingList().add(new TenantProductMapping(tenantSalesStatus.getTenantId(), tenantSalesStatus.getProdStatusCd(), tProdId));
        }

        return res;
    }

    /**
     * getTenantProductMapping VO
     */
    public static class SapProductMapping {
        private String prodIdS01;
        private String prodIdS02;
        private String prodIdS03;

        public String getProdIdS01() {
            return prodIdS01;
        }

        public void setProdIdS01(String prodIdS01) {
            this.prodIdS01 = prodIdS01;
        }

        public String getProdIdS02() {
            return prodIdS02;
        }

        public void setProdIdS02(String prodIdS02) {
            this.prodIdS02 = prodIdS02;
        }

        public String getProdIdS03() {
            return prodIdS03;
        }

        public void setProdIdS03(String prodIdS03) {
            this.prodIdS03 = prodIdS03;
        }
    }

    static class TenantSalesStatus {
        private String tenantId;
        private String prodStatusCd;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getProdStatusCd() {
            return prodStatusCd;
        }

        public void setProdStatusCd(String prodStatusCd) {
            this.prodStatusCd = prodStatusCd;
        }
    }
}
