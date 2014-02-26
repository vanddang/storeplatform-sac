package com.skplanet.storeplatform.sac.member.miscellaneous;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.external.client.idp.sci.ImageSCI;
import com.skplanet.storeplatform.external.client.idp.vo.ImageReq;
import com.skplanet.storeplatform.external.client.idp.vo.ImageRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ImageUrlDataTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageUrlDataTest.class);

	@Autowired
	private ImageSCI imageSCI;

	@Test
	public void testImageUrlData() {

		ImageReq req = new ImageReq();
		req.setProtocol("https");
		req.setUrlPath("watermark/20140121/10182_1390276368209.jpeg");
		ImageRes res = this.imageSCI.convert(req);
		assertThat(res.getImgData(), notNullValue());
		LOGGER.debug("[ImageUrlData] : {}", res.getImgData());
	}
}
