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

    	int keyIdx = 33;
    	String token = "d20e061eec4e7083f296efd5a69ff886549e569c66e7a0ec8b49c4ada070f0e3a2f734776a46dc81e2b19f77103035fe7923c6e4e086f5cd076164ea6d2de4828f35e175f5a41e190dc5686286dc3e3bbbd75e19116e75c45203279dd873fc8d704dcc3dc5a17061447e2d88dfffa2b7373437ca09964f281366f0acfd9775aeba0456be468ab48388e5ab1cac3fff30ef9406c314b7c048283fedf687d48f7f825a6f1156249ce67616b1641489fc65b7d456e69aa625dc4fbb5985bfac5b95061b30478c9a7bd4996813639f2e0ac41cd8d3ca46c95bb71c4d70cf9bfc30a2dab5a4c787c8e0f3e3d728f7e02bc896160bb03bc59cbd068c58e1d08aa569cee9dda70880a4ee98c0b521e683978766e8a328a1499911fdbb96b0ff8639c898a31f686e57bb72bf23d4e3c7e7f253d078689d9ba24cc54f1f1907d24f79198ce005aa27e0470b9f6993359a241093d63ad00bfff41234ea511db4d1b62465d17214d48e274573a3d95f8e860fd7aad118aaf0dbd766c924106403158ea8586735be9b3d3043ef07c96549ff0467bf221f135bb55979e005eaadc80b791d14ce0cad2bdf0fdf6fdd047d36b1d96f8b9a819f8ad089a77e4fbaa98074356ff6ce81162e4637f9853f76e7b3e85ec64d605f8ebd5f0ceab5906df122741de5ae1c5aed380bf5d877f071154ac5ebf07065a6cce3f1127f60b3b0a2c36eb2dbe54b8bf23425242562882e4b7751136467c59fdb7c5275e5004aae5819c1853cfa3342cd5d938cef842924f15aaefb53d38a71cdeca501e64dc1e788490e7693898a0e08859af34ff067489b67c438b5c0a4de04463ad100003c26bbabdf8e25368adeeeecbd134dd6e5424bcb7b852b4af61f69a4c28f3fc71fc8ec5bec1b119269a16b300aea9ded4d3513da193588d95f6c7f7dd7bf615c7b13c77079ef412592de6b9b444951a0145f82e3a775f92e575320b3455af09481b653984b30d6";

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
