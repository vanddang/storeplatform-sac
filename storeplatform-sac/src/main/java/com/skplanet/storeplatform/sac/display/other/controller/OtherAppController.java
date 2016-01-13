package com.skplanet.storeplatform.sac.display.other.controller;

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherAppApkSignedHashReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherAppApkSignedHashRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.AppApk;
import com.skplanet.storeplatform.sac.display.other.service.OtherAppApkService;
import com.skplanet.storeplatform.sac.display.other.vo.AppApkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
@Controller
@RequestMapping("/display/other/app")
public class OtherAppController {

    @Autowired
    private OtherAppApkService otherAppApkService;

    /**
     * 패키지명으로 APK Signature Hash 조회
     */
    @RequestMapping(value = "/apkSignedHash/get/v1", method = RequestMethod.GET)
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
}
