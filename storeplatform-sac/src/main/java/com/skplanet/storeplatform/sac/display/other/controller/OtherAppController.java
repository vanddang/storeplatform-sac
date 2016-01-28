package com.skplanet.storeplatform.sac.display.other.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.other.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.AppApk;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.other.service.*;
import com.skplanet.storeplatform.sac.display.other.vo.AppApkInfo;
import com.skplanet.storeplatform.sac.display.other.vo.GetVersionInfoByPkgParam;
import com.skplanet.storeplatform.sac.display.other.vo.VersionInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
@Controller
@RequestMapping("/display/other")
public class OtherAppController {

    @Autowired
    private OtherAppApkService otherAppApkService;

    @Autowired
    private OtherPackageListService otherPackageListService;

    @Autowired
    private OtherAIDListService otherAIDListService;

    @Autowired
    private OtherTenantProductMappingService otherTenantProductMappingService;

    @Autowired
    private OtherAppVersionService appVersionService;

    /**
     * 패키지명으로 APK Signature Hash 조회
     */
    @RequestMapping(value = "/app/apkSignedHash/get/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherAppApkSignedHashRes getAppApkSignedHash(@Validated OtherAppApkSignedHashReq req) {
        List<AppApkInfo> appApkInfoList = otherAppApkService.getApkSignedHash(req.getPackageName());

        return new OtherAppApkSignedHashRes(makeAppApkList(appApkInfoList));

    }

    private List<AppApk> makeAppApkList(List<AppApkInfo> appApkInfoList) {
        ArrayList<AppApk> appApkList = new ArrayList<AppApk>();

        for (AppApkInfo appApkInfo : appApkInfoList) {
            appApkList.add(new AppApk(appApkInfo.getApkPkgNm(), appApkInfo.getApkSignedKeyHash()));
        }

        return appApkList;
    }

    /**
     * 상품 ID 조회 (by 패키지명)
     */
    @RequestMapping(value = "/package/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherPackageListRes searchProductListByPackageNm(@Validated OtherPackageListReq req, SacRequestHeader header) {
        List<String> packageInfoList = Arrays.asList(StringUtils.split(req.getPackageInfo(), "+"));
        if (packageInfoList.size() > DisplayConstants.DP_UPDATE_PARAM_LIMIT) {
            throw new StorePlatformException("SAC_DSP_0004", "packageInfo", DisplayConstants.DP_UPDATE_PARAM_LIMIT);
        }
        return this.otherPackageListService.searchProductListByPackageNm(req, header, packageInfoList);
    }

    /**
     * 상품 ID 조회 (by AID)
     */
    @RequestMapping(value = "/aid/list/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherAIDListRes searchProductListByAID(@Validated OtherAIDListReq req, SacRequestHeader header) {
        List<String> aIdList = Arrays.asList(StringUtils.split(req.getAidList(), "+"));
        if (aIdList.size() > DisplayConstants.DP_UPDATE_PARAM_LIMIT) {
            throw new StorePlatformException("SAC_DSP_0004", "list", DisplayConstants.DP_UPDATE_PARAM_LIMIT);
        }
        return this.otherAIDListService.searchProductListByAID(req, header, aIdList);
    }

    /**
     * 테넌트 상품 매핑 조회
     */
    @RequestMapping(value = "/tenantProdMapg/get/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherTenantProductMappingRes getTenantProductMapping(@Validated OtherTenantProductMappingReq req) {
        return otherTenantProductMappingService.getTenantProductMapping(req.getProdId());
    }

    /**
     * 사용자의 Tenant 정보 조회
     */
    @RequestMapping(value = "/userTenant/get/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherUserTenantRes getUserTenant(@Validated OtherUserTenantReq req) {
        return otherTenantProductMappingService.getUserTenant(req);
    }

    /**
     * 패키지명으로 앱 버전 조회
     */
    @RequestMapping(value = "/appVersion/get/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherAppVersionRes getAppVersion(@Valid OtherAppVersionReq req) {
        VersionInfo versionInfo = appVersionService.getVersionInfoByPkg(new GetVersionInfoByPkgParam(req.getPkgNm(), req.getDeviceModelCd(), req.getOsVer()));
        if(versionInfo == null)
            throw new StorePlatformException("SAC_DSP_0009");

        return new OtherAppVersionRes(versionInfo.getProdId(), versionInfo.getVersionCode(), versionInfo.getVersion());
    }

    /**
     * 타사 상품ID로 앱 버전 조회
     *
     * 타사 embedded된 VAS 앱이 OneStore 샵클을 통해 업데이트 유무를 확인 하기 위해 사용.
     */
    @RequestMapping(value = "/app/mapgVersion/get/v1", method = RequestMethod.GET)
    @ResponseBody
    public OtherAppMapgVersionRes getAppMapgVersion(@Valid OtherAppMapgVersionReq req) {

        if(StringUtils.equals(req.getDeviceModelCd(), DisplayConstants.DP_ANY_PHONE_4APP)) {
            throw new StorePlatformException("SAC_DSP_0031");
        }

        String osVer = DisplayCommonUtil.extractOsVer(req.getOsVer());
        if(osVer.equals(DisplayCommonUtil.WRONG_OS_VER)) {
            throw new StorePlatformException("SAC_DSP_0030");
        }

        VersionInfo verInfo = appVersionService.getMapgVersionInfo(req.getMapgProdId(), req.getDeviceModelCd(), osVer);
        if(verInfo == null)
            throw new StorePlatformException("SAC_DSP_0009");

        return new OtherAppMapgVersionRes(verInfo.getProdId(), verInfo.getPackageName(), verInfo.getVersionCode(), verInfo.getVersion());
    }
}
