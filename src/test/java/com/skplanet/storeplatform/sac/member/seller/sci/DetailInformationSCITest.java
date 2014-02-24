package com.skplanet.storeplatform.sac.member.seller.sci;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
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
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.MockRequestAttributeInitializer;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class DetailInformationSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetailInformationSCITest.class);

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		TenantHeader tenant = new TenantHeader();
		tenant.setTenantId("S01");
		tenant.setSystemId("S001");
		sacRequestHeader.setTenantHeader(tenant);

		MockRequestAttributeInitializer.init(SacRequestHeader.class.getName(), sacRequestHeader);
	}

	/**
	 * <pre>
	 * 판매자 기본정보 조회(모든키존재).
	 * </pre>
	 */

	@Test
	public void shouldOptianUserCarrer() {
		DetailInformationSacReq req = new DetailInformationSacReq();

		// req.setSellerKey("IF1023229565820110106110241");

		List<SellerMbrSac> sellerMbrSacs = new ArrayList<SellerMbrSac>();
		for (int i = 0; i < 3; i++) {
			SellerMbrSac sellerMbrSac = new SellerMbrSac();
			if (i == 0) {
				sellerMbrSac.setSellerBizNumber("2432412");
				sellerMbrSacs.add(sellerMbrSac);
			} else if (i == 1) {
				sellerMbrSac.setSellerKey("IF1023498592920130510112417");
				sellerMbrSacs.add(sellerMbrSac);
			} else if (i == 2) {
				sellerMbrSac.setSellerKey("IF1023479054520130204101236");
				sellerMbrSacs.add(sellerMbrSac);
			}
		}
		req.setSellerMbrSacList(sellerMbrSacs);

		DetailInformationSacRes res = this.sellerSearchSCI.detailInformation(req);

		assertThat(res.getSellerMbrListMap(), notNullValue());
		LOGGER.debug("response param : {}", res.toString());
	}

}
