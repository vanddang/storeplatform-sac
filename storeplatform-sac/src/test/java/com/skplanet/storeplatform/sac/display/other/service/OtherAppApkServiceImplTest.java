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

        List<AppApkInfo> appApkInfos;

        appApkInfos = mapApkInfo.get("vod.pkg.name");
        assertThat(appApkInfos.size(), is(2));
        assertThat(appApkInfos.get(0).getApkPkgNm(), is("vod.pkg.name"));
        assertThat(appApkInfos.get(0).getApkSignedKeyHash(), is("vod-signedkeyhash1"));
        assertThat(appApkInfos.get(1).getApkPkgNm(), is("vod.pkg.name"));
        assertThat(appApkInfos.get(1).getApkSignedKeyHash(), is("vod-signedkeyhash2"));

        appApkInfos = mapApkInfo.get("ebook.pkg.name");
        assertThat(appApkInfos.size(), is(1));
        assertThat(appApkInfos.get(0).getApkPkgNm(), is("ebook.pkg.name"));
        assertThat(appApkInfos.get(0).getApkSignedKeyHash(), is("ebook-signedkeyhash1"));
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