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

import com.skplanet.storeplatform.sac.display.personal.vo.MemberInfo;
import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;
import com.skplanet.storeplatform.sac.display.personal.vo.UpdatePkgDetail;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * AppUpdateInfoManager
 * </p>
 * Updated on : 2014. 07. 01 Updated by : 정희원, SK 플래닛.
 */
public interface AppUpdateSupportService {

    /**
     * 패키지명으로 서브컨텐트 정보를 조회한다.
     * @param deviceModelCd
     * @param pkgList @return
     */
    List<SubContentInfo> searchSubContentByPkg(String deviceModelCd, List<String> pkgList);

    /**
     * 타사 매핑 패키지명으로 서브컨텐트 정보를 조회한다.
     * @param deviceModelCd
     * @param pkgList
     * @return
     */
    List<SubContentInfo> addSubContentByMapgPkg(List<SubContentInfo> subContentInfos, String deviceModelCd, List<String> pkgList);

    /**
     * 기구매 내역을 조회한다.
     * @param tenantId
     * @param userKey
     * @param deviceKey
     * @param prodIdList
     * @return
     */
    Set<String> getPurchaseSet(String tenantId, String userKey, String deviceKey, List<String> prodIdList);

    /**
     * deviceId로 회원 정보를 조회한다.
     * @param deviceId
     * @return
     */
    MemberInfo getMemberInfo(String deviceId);

    /**
     * 업데이트 결과를 로그에 남긴다.
     * @param type
     * @param deviceId
     * @param userKey
     * @param deviceKey
     * @param network
     * @param updateList
     */
    void logUpdateResult(String type, String deviceId, String userKey, String deviceKey, String network, List<String> updateList);

    /**
     * 상품에 테넌트별로 패키지명이 다르게 매핑되었는지 조회한다.
     * @param prodId 상품ID
     * @return true 다른 매핑이 조회됨.
     */
    boolean getDifferentPackageNameYn(String prodId);

	/**
	 * packageInfo string 값을 파싱하여 Map으로 리턴한다.
	 * @param packageInfoList
	 * @return
	 */
	Map<String, UpdatePkgDetail> parsePkgInfoList(List<String> packageInfoList);

	/**
	 * dbVer이 reqVer보다 높고,
	 * ApkSign 값이 존재 한다면, 일치하는 경우 true을 리턴한다.
	 * @param reqVer
	 * @param dbVer
	 * @param reqApkSign
	 * @param dbApkSign
	 * @return
	 */
	boolean isTargetUpdateProduct(int reqVer, int dbVer, String reqApkSign, String dbApkSign);
}
