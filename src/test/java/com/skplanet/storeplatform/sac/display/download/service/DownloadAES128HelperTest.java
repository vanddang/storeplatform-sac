/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 08. 04 Updated by : 양해엽, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DownloadAES128HelperTest {

    private static final Logger logger = LoggerFactory.getLogger(DownloadAES128HelperTest.class);

    @Autowired
    private DownloadAES128Helper downloadAES128Helper;


    @Before
    public void init() {

    }

    @Test
    public void testDecryption() {

    	int keyIdx = 59;
    	String token = "9cac97f011cf3eb0ebd7fd059a71fc2e73297f2fb0b7c270f80a496bb0564483cac10017a04e962b52bf8b1f3286d19e0b2f7146e47a5f15ee93bf13b62948f72d56dd422fe34458d5a7b05e0c1157fb19ae46853c6a18ead41380d627b2217c9f985824e33d89bdfe58d7aba82674aad68f02fea06787e68fa6667d311002d2e37f425a08cdb25db0bbcddce4263213011789f0a705ad637418238617120b2bb3013fb7cc417e9f5c44d49d3023a4071fbbb6684897ffec9abe2a53efd541483639ea29650f77895f37eb2a2187fc1afc456e83fad105fd90a326dcd1f6c1c2592812696ed9271466774dffb49e22329a7780f812082f9d2a47ad122fd39b1c27acb64e4f6aa93a07483035d84ee99355298f387392e44f751b224403098a9c5dea3fc7e317d4fb7cd614c486626379ecdb10cb63526e03684af5fa32232bf00f2f7afd7d96a5407e33d2985e8bde2ce54df0b4cae4ddb70be4e1b5be2089b8341df69449fb2ffff7ae6887861a84b8ccbb41c41e113abe8dd71b6b8fe044dc690b96f7d3efcd35f1bfcf059ada612badfdc228dceff3d2d1463380ce1a161e811251746464ade3f2100b97ef1517d5c57a43da1fedf7620505fccba3e43fd4fb87c962cb1813fe50560860cc3ac0fa63e0dacef3d27d6e7397d4857e87ab313602510559994bc3b0fb55bde12c6bd14b928fab53b9059632e1b9c415799e074e6d36704feaa47c90b39cd6f3f7b38055887461b5e39e534d72fda4397c1de0444956a407b64b00d141692f7b8d53bee8804d90948c7f4e71ed90c6eca3be0dbb031e8497723082f05e759de6c03bd2b18b284c41c00b2997406ba11067d0b26fa9f1d12362270f5d2be478bc76c76579752363c80fcebd9303c660cd2abb7ab8f726790fa994890d194c09a890ecc83b19bb390e8d225f7f5d1ab706854067731e224722947c5530d8c6faa50dcfaf2ac07b7d0c633046e5d71d1109799fe0f1483452e7d0";

    	byte [] data = this.downloadAES128Helper.convertBytes(token);

    	byte [] decryption = this.downloadAES128Helper.decryption(keyIdx, data);

    	try {

			String result = new String(decryption, "UTF-8");

			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(result, Object.class);

			String indented = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(json);

			System.out.println(result);
			System.out.println(indented);

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
