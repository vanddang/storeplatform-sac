/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceItem;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * AppUpdateSupportServiceImpl
 * </p>
 * Updated on : 2014. 07. 01 Updated by : 정희원, SK 플래닛.
 */
@Service
public class AppUpdateSupportServiceImpl implements AppUpdateSupportService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private ExistenceInternalSacSCI existenceInternalSacSCI;

    private static final int VAR_WINDOW_SIZE = 200;

    @Override
    public List<SubContentInfo> searchSubContentByPkg(String deviceModelCd, List<String> pkgList, boolean isHashed) {

        Map<String, Object> req = new HashMap<String, Object>();
        req.put("parentClsfCd", DisplayConstants.DP_PART_PARENT_CLSF_CD);
        req.put("deviceModelCd", deviceModelCd);

        List<SubContentInfo> res = new ArrayList<SubContentInfo>(pkgList.size());

        int size = pkgList.size();
        int loopCnt = size / VAR_WINDOW_SIZE + (size % VAR_WINDOW_SIZE == 0 ? 0 : 1);
        for(int lp = 0; lp < loopCnt; ++lp) {

            int toIdx;
            int paddingCnt = 0;
            List<String> reqProdId;
            toIdx = (lp + 1) * VAR_WINDOW_SIZE;
            if(toIdx > size) {
                paddingCnt = toIdx - size;
                toIdx = size;
            }
            reqProdId = new ArrayList<String>(pkgList.subList(lp * VAR_WINDOW_SIZE, toIdx));
            if(paddingCnt > 0) {
                for(int j=0; j<paddingCnt; ++j)
                   reqProdId.add("");
            }

            if(isHashed)
                req.put("hashedPkgList", reqProdId);
            else
                req.put("pkgList", reqProdId);

            res.addAll(commonDAO.queryForList("PersonalUpdateProduct.searchRecentFromPkgNm", req, SubContentInfo.class));
        }

        return res;
    }

    @Override
    public Set<String> getPurchaseSet(String tenantId, String userKey, String deviceKey, List<String> prodIdList) {
        Set<String> res = new HashSet<String>();

        try {
            ExistenceReq existenceReq = new ExistenceReq();
            List<ExistenceItem> existenceItemList = new ArrayList<ExistenceItem>();
            for (String prodId : prodIdList) {
                ExistenceItem existenceItem = new ExistenceItem();
                existenceItem.setProdId(prodId);
                existenceItemList.add(existenceItem);
            }
            existenceReq.setTenantId(tenantId);
            existenceReq.setUserKey(userKey);
            existenceReq.setDeviceKey(deviceKey);
            existenceReq.setExistenceItem(existenceItemList);

            ExistenceListRes existenceListRes = this.existenceInternalSacSCI.searchExistenceList(existenceReq);

            for(ExistenceRes er : existenceListRes.getExistenceListRes()) {
                res.add(er.getProdId());
            }
        } catch (Exception e) {
            // Exception 무시
        }

        return res;
    }
}
