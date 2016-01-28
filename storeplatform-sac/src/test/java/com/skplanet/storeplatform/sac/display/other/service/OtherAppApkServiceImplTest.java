package com.skplanet.storeplatform.sac.display.other.service;


import com.skplanet.storeplatform.sac.display.other.vo.AppApkInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Updated on : 2016-01-08. Updated by : 양해엽, SK Planet.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-environment.xml" })
public class OtherAppApkServiceImplTest {

    @Resource(name = "propertiesForSac")
    private Properties prop;

    private static final String SIGNED_HASH_PREFIX = "app.apk.signature.hash.";
    private static final String SIGNED_HASH_PRODNAME = "prodname";

    private static Map<String, List<AppApkInfo>> mapApkInfo;

    @Before
    public void setUp() throws Exception {
        mapApkInfo = new HashMap<String, List<AppApkInfo>>();
    }

    @Test
    public void test() {
        assertNotNull(prop);

        /**
         * # app apk signature hash key : 구성관리 앱(inHouse 앱)
         * app.apk.signature.hash.prodname=tstore,arm,vod,ebook,kstore1,kstore2,ustore1,ustore2
         * app.apk.signature.hash.tstore=com.skt.skaf.A000Z00040:be4082453e06da85fcd8ca54610d9afc4a67fafd
         * app.apk.signature.hash.arm=com.skt.skaf.OA00018282:be4082453e06da85fcd8ca54610d9afc4a67fafd
         * app.apk.signature.hash.vod=com.skt.skaf.A000VODBOX:be4082453e06da85fcd8ca54610d9afc4a67fafd
         * app.apk.signature.hash.ebook=com.skt.skaf.OA00050017:79cf0a61fc7d5fed422481d58972115ff9ade51d
         * app.apk.signature.hash.kstore1=com.kt.olleh.storefront:6413dc7abcc8278e5cd8be5023e5c05e54532247
         * app.apk.signature.hash.kstore2=com.kt.olleh.istore:6413dc7abcc8278e5cd8be5023e5c05e54532247
         * app.apk.signature.hash.ustore1=com.lguplus.appstore:094a523a4aa04ff371cd7d858a85b64a8ba1a974
         * app.apk.signature.hash.ustore2=android.lgt.appstore:094a523a4aa04ff371cd7d858a85b64a8ba1a974
         */
        String prodName = prop.getProperty(SIGNED_HASH_PREFIX + SIGNED_HASH_PRODNAME);
        if (StringUtils.isBlank(prodName)) {
            return;
        }

        String[] arrProdNm = prodName.split(",");
        for (String prodNm : arrProdNm) {
            putAppApkInfoList(prodNm);
        }

        List<AppApkInfo> appApkInfos;

        appApkInfos = mapApkInfo.get("com.skt.skaf.A000Z00040");
        assertThat(appApkInfos.size(), is(1));
        assertThat(appApkInfos.get(0).getApkPkgNm(), is("com.skt.skaf.A000Z00040"));
        assertThat(appApkInfos.get(0).getApkSignedKeyHash(), is("be4082453e06da85fcd8ca54610d9afc4a67fafd"));

        appApkInfos = mapApkInfo.get("android.lgt.appstore");
        assertThat(appApkInfos.size(), is(1));
        assertThat(appApkInfos.get(0).getApkPkgNm(), is("android.lgt.appstore"));
        assertThat(appApkInfos.get(0).getApkSignedKeyHash(), is("094a523a4aa04ff371cd7d858a85b64a8ba1a974"));
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

        mapApkInfo.put(kv[0], appApkInfoList);
    }

}