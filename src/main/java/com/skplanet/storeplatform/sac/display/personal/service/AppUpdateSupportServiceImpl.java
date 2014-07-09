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

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceItem;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
import com.skplanet.storeplatform.sac.display.personal.vo.MemberInfo;
import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger logger = LoggerFactory.getLogger(AppUpdateSupportServiceImpl.class);

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private ExistenceInternalSacSCI existenceInternalSacSCI;

    @Autowired
    private SearchUserSCI searchUserSCI;

    private static final int VAR_WINDOW_SIZE = 100;

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

            ExistenceListRes existenceListRes = existenceInternalSacSCI.searchExistenceList(existenceReq);

            for(ExistenceRes er : existenceListRes.getExistenceListRes()) {
                res.add(er.getProdId());
            }
        } catch (Exception e) {
            // Exception 무시
        }

        return res;
    }

    @Override
    public MemberInfo getMemberInfo(String deviceId) {
        logger.debug("##### check user status");
        logger.debug("##### deviceId :: {} " + deviceId);
        UserInfoSacReq userInfoSacReq = new UserInfoSacReq();
        userInfoSacReq.setDeviceId(deviceId);
        logger.debug("##### [SAC DSP LocalSCI] SAC Member Start : searchUserSCI.searchUserBydeviceId");
        long start = System.currentTimeMillis();
        UserInfoSacRes userInfoSacRes = searchUserSCI.searchUserBydeviceId(userInfoSacReq);
        logger.debug("##### [SAC DSP LocalSCI] SAC Member End : searchUserSCI.searchUserBydeviceId");
        long end = System.currentTimeMillis();
        logger.debug("##### [SAC DSP LocalSCI] SAC Member searchUserSCI.searchUserBydeviceId takes {} ms",
                (end - start));

        String userMainStatus = userInfoSacRes.getUserMainStatus();
        final String userKey = userInfoSacRes.getUserKey();
        final String deviceKey = userInfoSacRes.getDeviceKey();
        logger.debug("##### userKey :: {} " + userKey);
        logger.debug("##### deviceKey :: {} " + deviceKey);

        new TLogUtil().set(new TLogUtil.ShuttleSetter() {
            @Override
            public void customize(TLogSentinelShuttle shuttle) {
                shuttle.insd_usermbr_no(userKey).insd_device_id(deviceKey);
            }
        });

        // 회원에 의하면 정상은 UserMainStatus를 참고
        // 정상 회원일이 아닐 경우 -> '업데이트 내역이 없습니다' 에러 발생
        // 탈퇴 회원일 경우 -> 회원에서 '탈퇴한 회원입니다.'에러 발생하여 그대로 throw 함
        if (DisplayConstants.MEMBER_MAIN_STATUS_NORMAL.equals(userMainStatus)) {
            logger.debug("##### This user is normal user!!!!");
        } else {
            logger.debug("##### This user is unnormal user!!!!");
            throw new StorePlatformException("SAC_DSP_0006");
        }

        return new MemberInfo(userKey, deviceKey);
    }

    @Override
    public void logUpdateResult(String type, String deviceId, String userKey, String deviceKey, String network, List<String> updateList) {
        String updateListStr = StringUtils.join(updateList, ",");
        logger.info("UpdateResult: type={}, deviceId={}, userKey={}, deviceKey={}, network={}, updateList=[{}]", type, deviceId, userKey, deviceId, network, updateListStr);
    }
}
