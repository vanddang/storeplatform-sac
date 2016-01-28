package com.skplanet.storeplatform.sac.display.other.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.other.vo.AppApkInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
@Service
public class OtherAppApkServiceImpl implements OtherAppApkService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Resource(name = "propertiesForSac")
    private Properties prop;

    private static final String SIGNED_HASH_PREFIX = "app.apk.signature.hash.";
    private static final String SIGNED_HASH_PRODNAME = "prodname";

    private static Map<String, List<AppApkInfo>> mapAppApkInfo;

    @PostConstruct
    private void putMapAppApkInfo() {
        mapAppApkInfo = new HashMap<String, List<AppApkInfo>>();

        /**
         * properties sample
         * app.apk.signature.hash.prodname=vod,ebook
         * app.apk.signature.hash.vod=vod.pkg.name:vod-signedkeyhash1,vod-signedkeyhash2
         * app.apk.signature.hash.ebook=ebook.pkg.name:ebook-signedkeyhash1
         */
        String prodName = prop.getProperty(SIGNED_HASH_PREFIX + SIGNED_HASH_PRODNAME);
        if (StringUtils.isBlank(prodName)) {
            return;
        }

        String[] arrProdNm = prodName.split(",");
        for (String prodNm : arrProdNm) {
            putAppApkInfoList(prodNm);
        }
    }

    private void putAppApkInfoList(String prodNm) {
        String[] kv = prop.getProperty(SIGNED_HASH_PREFIX + prodNm).split(":");

        if (kv.length != 2) {
            return;
        }

        List<AppApkInfo> appApkInfoList = new ArrayList<AppApkInfo>();

        String[] values = kv[1].split(",");
        for (String value : values) {
            appApkInfoList.add(new AppApkInfo(kv[0], value));
        }

        mapAppApkInfo.put(kv[0], appApkInfoList);
    }

    private List<AppApkInfo> getAppApkInfoByProp(String packageName) {

        return mapAppApkInfo.containsKey(packageName) ? mapAppApkInfo.get(packageName) : new ArrayList<AppApkInfo>();
    }

    private List<AppApkInfo> searchApkSignedKeyHash(String packageName) {
        HashMap<String, Object> req = new HashMap<String, Object>();
        req.put("apkPkgHash", DisplayCryptUtils.createSha1Mac().hashPkgNm(packageName));

        return this.commonDAO.queryForList("OtherAppApk.getApkSignedKeyHash", req, AppApkInfo.class);
    }


    @Override
    public List<AppApkInfo> getApkSignedHash(String packageName) {
        // properties로 관리되는 inHouse앱을 먼저 확인 한다.
        List<AppApkInfo> appApkInfoList = getAppApkInfoByProp(packageName);

        if (appApkInfoList.isEmpty()) {
            // properties에 존재하지 않는 경우 DB에서 확인 한다.
            appApkInfoList = searchApkSignedKeyHash(packageName);
        }

        return appApkInfoList;
    }
}
