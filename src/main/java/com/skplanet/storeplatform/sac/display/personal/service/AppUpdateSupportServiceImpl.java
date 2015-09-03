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

import java.util.*;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.skplanet.plandasj.Plandasj;
import com.skplanet.spring.data.plandasj.PlandasjConnectionFactory;
import com.skplanet.storeplatform.sac.common.util.PartialProcessor;
import com.skplanet.storeplatform.sac.common.util.PartialProcessorHandler;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.personal.vo.UpdatePkgDetail;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.personal.vo.MemberInfo;
import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;

/**
 * <p>
 * AppUpdateSupportServiceImpl
 * </p>
 * Updated on : 2014. 07. 01 Updated by : 정희원, SK 플래닛.
 */
@Service
public class AppUpdateSupportServiceImpl implements AppUpdateSupportService {

    private static final Logger logger = LoggerFactory.getLogger(AppUpdateSupportServiceImpl.class);
//    public static final String PLANDAS_APKPROD_MAPG = "sacdp://apk-prod/";
//    public static final String PLANDAS_APKPROD_SET = "sacdp://apk-prod-set/";
    public static final String PLANDAS_APKPROD_MAPG = "pkg2prod:";
    public static final String PLANDAS_APKPROD_SET = "pkg2prods";

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private ExistenceInternalSacSCI existenceInternalSacSCI;

    @Autowired
    private SearchUserSCI searchUserSCI;

    @Autowired(required = false)
    private PlandasjConnectionFactory connectionFactory;

    private static final int VAR_WINDOW_SIZE = 100;

    public static class PkgNmPidMapg {
        private String apkPkgNm;
        private String prodId;
        private Date lastDeployDt;

        public String getApkPkgNm() {
            return apkPkgNm;
        }

        public void setApkPkgNm(String apkPkgNm) {
            this.apkPkgNm = apkPkgNm;
        }

        public String getProdId() {
            return prodId;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }

        public void setLastDeployDt(Date lastDeployDt) {
            this.lastDeployDt = lastDeployDt;
        }

        public String getSortKey() {
            return apkPkgNm + lastDeployDt.getTime();
        }
    }


    @Override
    public List<SubContentInfo> addSubContentByMapgPkg(List<SubContentInfo> subContentInfos, String deviceModelCd, List<String> pkgList) {
        Set<String> findPkgSet = new HashSet<String>(subContentInfos.size());
        for (SubContentInfo scInfo : subContentInfos)
            findPkgSet.add(scInfo.getApkPkgNm());

        List<String> mapgPkgList = new ArrayList<String>(pkgList.size());
        for (String s : pkgList) {
            if (!findPkgSet.contains(s))
                mapgPkgList.add(s);
        }

        if (!mapgPkgList.isEmpty())
            subContentInfos.addAll(searchSubContentByMapgPkg(deviceModelCd, mapgPkgList));
        return subContentInfos;
    }

    private List<SubContentInfo> searchSubContentByMapgPkg(final String deviceModelCd, List<String> pkgList) {

        final Plandasj client = connectionFactory.getConnectionPool().getClient();
        final Map<String, String> pidPkgMap = new HashMap<String, String>(pkgList.size());
        List<String> pkgsToFind = new ArrayList<String>(pkgList.size());

        // 패키지명으로 prodId를 조회한다
        // TODO 이 부분은 차후 CachedExtraInfoManager.getProdIdByPkgNm() 로 처리 by joyspring
        for (String apkNm : pkgList) {
            String pid = client.get(PLANDAS_APKPROD_MAPG + apkNm);
            if(pid == null)
                pkgsToFind.add(apkNm);
            else
                pidPkgMap.put(pid, apkNm);
        }

        if (CollectionUtils.isNotEmpty(pkgsToFind)) {
            PartialProcessor.process(pkgsToFind, new PartialProcessorHandler<String>() {
                @Override
                public String processPaddingItem() {
                    return StringUtils.EMPTY;
                }

                @Override
                public void processPartial(List<String> partialList) {
                    Map<String, Object> req = new HashMap<String, Object>();

                    List<String> hashedPkgList = Lists.transform(partialList, new Function<String, String>() {
                        @Override
                        public String apply(String input) {
                            return DisplayCryptUtils.hashPkgNm(input);
                        }
                    });

                    req.put("hashedPkgList", hashedPkgList);
                    List<PkgNmPidMapg> mapgs = commonDAO.queryForList("PersonalUpdateProduct.searchPidByHashedPkg", req, PkgNmPidMapg.class);

                    // MAPG_APK_PKG_NM 기준 정렬
                    Collections.sort(mapgs, new Comparator<PkgNmPidMapg>() {
                        @Override
                        public int compare(PkgNmPidMapg pkgNmPidMapg, PkgNmPidMapg pkgNmPidMapg2) {
                            return pkgNmPidMapg.getSortKey().compareTo(pkgNmPidMapg2.getSortKey());
                        }
                    });

                    PkgNmPidMapg prev = null;
                    for (PkgNmPidMapg m : mapgs) {
                        if (prev == null) {
                            prev = m;
                            continue;
                        }

                        if(!prev.getApkPkgNm().equals(m.getApkPkgNm())) {
                            storeApkPidMapg(client, pidPkgMap, prev.getProdId(), prev.getApkPkgNm());
                        }
                        prev = m;
                    }

                    if(prev != null)
                        storeApkPidMapg(client, pidPkgMap, prev.getProdId(), prev.getApkPkgNm());
                }
            }, VAR_WINDOW_SIZE);
        }

        final List<SubContentInfo> res = new ArrayList<SubContentInfo>(pidPkgMap.size());

        // prodId로 업데이트할 상품을 조회한다.
        PartialProcessor.process(pidPkgMap.keySet(), new PartialProcessorHandler<String>() {
            @Override
            public String processPaddingItem() {
                return StringUtils.EMPTY;
            }

            @Override
            public void processPartial(List<String> partialList) {
                Map<String, Object> req = new HashMap<String, Object>();
                req.put("deviceModelCd", deviceModelCd);
                req.put("prodIds", partialList);

                List<SubContentInfo> updateList = commonDAO.queryForList("PersonalUpdateProduct.searchUpdateList", req, SubContentInfo.class);
                res.addAll(Lists.transform(updateList, new Function<SubContentInfo, SubContentInfo>() {
                    @Override
                    public SubContentInfo apply(SubContentInfo v) {
                        if (!StringUtils.equals(v.getApkPkgNm(), pidPkgMap.get(v.getProdId())))
                            v.setHasDiffPkgYn("Y");
                        v.setApkPkgNm(pidPkgMap.get(v.getProdId()));
                        return v;
                    }
                }));
            }
        });

        return res;
    }

    private void storeApkPidMapg(Plandasj client, Map<String, String> pidPkgMap, String pid, String apkPkgNm) {
        String key = PLANDAS_APKPROD_MAPG + apkPkgNm;
        client.set(key, pid);
        client.pexpire(PLANDAS_APKPROD_MAPG + apkPkgNm, 1000 * 60 * 60 * 1);  // 1시간
//        client.pexpire(PLANDAS_APKPROD_MAPG + apkPkgNm, 1000 * 30); // 30초
        client.sadd(PLANDAS_APKPROD_SET, apkPkgNm);

        pidPkgMap.put(pid, apkPkgNm);
    }



    @Override
    public List<SubContentInfo> searchSubContentByPkg(final String tenantId, final String deviceModelCd, List<String> pkgList) {

        final List<SubContentInfo> res = new ArrayList<SubContentInfo>(pkgList.size());

        PartialProcessor.process(pkgList, new PartialProcessorHandler<String>() {
            @Override
            public String processPaddingItem() {
                return StringUtils.EMPTY;
            }

            @Override
            public void processPartial(List<String> partialList) {

                List<String> hashedPkgList = Lists.transform(partialList, new Function<String, String>() {
                    @Override
                    public String apply(String input) {
                        return DisplayCryptUtils.hashPkgNm(input);
                    }
                });

                Map<String, Object> req = new HashMap<String, Object>();
				req.put("tenantId", tenantId);
                req.put("parentClsfCd", DisplayConstants.DP_PART_PARENT_CLSF_CD);
                req.put("deviceModelCd", deviceModelCd);
                req.put("hashedPkgList", hashedPkgList);

                res.addAll(commonDAO.queryForList("PersonalUpdateProduct.searchRecentFromPkgNm", req, SubContentInfo.class));
            }
        }, VAR_WINDOW_SIZE);

        // 관리되는 특정 앱 상품을 상위 목록으로 변경한다.(ex, 시럽, OCB)
        Collections.sort(res, new Comparator<SubContentInfo>() {
            @Override
            public int compare(SubContentInfo o1, SubContentInfo o2) {
                return o1.getPriority() > o2.getPriority() ? -1 : o1.getPriority() < o2.getPriority() ? 1 : 0;
            }
        });

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

    @Override
    public MemberInfo getMemberInfo(String deviceId) {
        UserInfoSacReq userInfoSacReq = new UserInfoSacReq();
        userInfoSacReq.setDeviceId(deviceId);

        UserInfoSacRes userInfoSacRes = this.searchUserSCI.searchUserBydeviceId(userInfoSacReq);

        String userMainStatus = userInfoSacRes.getUserMainStatus();
        final String userKey = userInfoSacRes.getUserKey();
        final String deviceKey = userInfoSacRes.getDeviceKey();

        new TLogUtil().set(new TLogUtil.ShuttleSetter() {
            @Override
            public void customize(TLogSentinelShuttle shuttle) {
                shuttle.insd_usermbr_no(userKey).insd_device_id(deviceKey);
            }
        });

        // 회원에 의하면 정상은 UserMainStatus를 참고
        // 정상 회원일이 아닐 경우 -> '업데이트 내역이 없습니다' 에러 발생
        // 탈퇴 회원일 경우 -> 회원에서 '탈퇴한 회원입니다.'에러 발생하여 그대로 throw 함
        if (!DisplayConstants.MEMBER_MAIN_STATUS_NORMAL.equals(userMainStatus)) {
			throw new StorePlatformException("SAC_DSP_0006");
        }

        return new MemberInfo(userKey, deviceKey);
    }

    @Override
    public void logUpdateResult(String type, String deviceId, String userKey, String deviceKey, String network, List<String> updateList) {
        String updateListStr = StringUtils.join(updateList, ",");
        logger.info("UpdateResult: type={}, deviceId={}, userKey={}, deviceKey={}, network={}, updateList=[{}]", type, deviceId, userKey, deviceId, network, updateListStr);
    }

    @Override
    public boolean getDifferentPackageNameYn(String prodId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("prodId", prodId);
        req.put("tenantIds", ServicePropertyManager.getSupportTenantList());
        int v = commonDAO.queryForInt("PersonalUpdateProduct.getPackageNameWarning", req);

        // 매핑된 패키지명의 가짓수가 1개 이상이면 경고
        return v > 1;
    }

	@Override
	public Map<String, UpdatePkgDetail> parsePkgInfoList(List<String> packageInfoList) {
		int reqSize = packageInfoList.size();

		Map<String, UpdatePkgDetail> pkgReqMap = new LinkedHashMap<String, UpdatePkgDetail>(reqSize);

		for (String s : packageInfoList) {
			UpdatePkgDetail dtl = new UpdatePkgDetail(s);
			// parameter가 적어도 packageName/version정보로 와야지만 update 리스트에 추가한다.
			if (dtl.getParsedCnt() >= 2)
				pkgReqMap.put(dtl.getPkgNm(), dtl);
		}

		return pkgReqMap;
	}

	@Override
	public boolean isTargetUpdateProduct(int reqVer, int dbVer, String reqApkSign, String dbApkSign) {

		if (StringUtils.isNotEmpty(reqApkSign) && StringUtils.isNotEmpty(dbApkSign))
			return (dbVer > reqVer) && reqApkSign.equals(dbApkSign);
		else
			return (dbVer > reqVer);
	}
}
