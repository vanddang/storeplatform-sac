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

    	int keyIdx = 18;
    	String token = "b84011f249a572444809868915514e00c28fcd0751d028d04f962bdc32cd3cb1ab86fe68b1276ba183d85a44dcb13d259533a6a72989330683c867126cbba91d13ea9199fba727f546184f30fbccf7d8031d320afa7b7bf3981d3bf2556655e9611cbd9121c9c39b9a1f0d2d536f9a672ad500b2cc9a74912d68a9f16953f2654cb06a4a7faa4836052ee19c0abd9cfb5f7400b5cd8c3cd4fc51cac2676809bc8122a83dbb6acb20c011f138e76c9b87f77d6efc641845756d073c1b79748c3b27819d34e0351b4fbd4779b09ca9a4814e7be92f7e88f00ac0321ef1e7b2d9553a0226299c75b111708ffdd4790aa207f0ae03238c48fb825e9aca29d5764fc59926464f31f5310d7a935222373a031ad4692a6bd62425ccd0309967daa71fdb14df63be4e16f57ad9d7e39b769fe2e6333f52d16ee63d5ee8d7967263ab6a72fa974d37085132f91e82916d4912ad0d65b8400190977bcdd986ee746ce17d5788580c64c438f5d975892623861577fa5acc0103762219b047c7bd16047847d716c3d0c9082cb50920d9dd4a641d87fd4ea7f765b21a5501f8e66c32a9db335f5521f36b677970960fda42f424f13156269b5552bb06da940966f1dbe4a007b620c0c55591233311d3a87612a96751e5961de58e8ad205b5246243453978fd013517dbb9d8e99ba4edee74e5897e1a5efefb0f044936718510645bb9a7def8bffc877b8b1302beee2544b8805e63b20d6a7719f70c54fe1f2692b76de60a0d9c51ce907f6e75b70692ed702733637f7e52f51875141aef2776a8f15171158ac32f2c34dd6f0e487f531d51e7d17fea5fe5715f6dc997fb534480ab8320c51490a666144a87e65ffc4190d3de2f198f9330dd3add81e19d1363195f83cb94b0fea463818504b20a8b2f503847c2db7d7b14a90537b614c99f5b3c2b2e8a138ba9978594306d371b017e1036f4e5e7259243c7067eed8336a02a7b46e10bce3f7ea532ea4814fb2b1e6462f0ec25224d65c7";

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
