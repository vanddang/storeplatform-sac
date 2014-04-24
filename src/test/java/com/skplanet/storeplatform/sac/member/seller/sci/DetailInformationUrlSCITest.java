/**
 * 
 */
package com.skplanet.storeplatform.sac.member.seller.sci;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.MockRequestAttributeInitializer;

/**
 * 판매자 기본정보 조회 SCI Controller Test.
 * 
 * Updated on : 2014. 2. 24. Updated by : 한서구, 부르칸.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class DetailInformationUrlSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetailInformationUrlSCITest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	/**
	 * 
	 * <pre>
	 * 초기화.
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

		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 판매자 기본정보 조회(모든키존재).
	 * </pre>
	 */
	@Test
	public void shouldOptianUserCarrer() {
		new TestCaseTemplate(this.mvc).url("/member/seller/sci/detailInformation").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						DetailInformationSacReq req = new DetailInformationSacReq();

						// req.setSellerKey("IF1023229565820110106110241");

						List<SellerMbrSac> sellerMbrSacs = new ArrayList<SellerMbrSac>();
						for (int i = 0; i < 1; i++) {
							SellerMbrSac sellerMbrSac = new SellerMbrSac();
							if (i == 0) {
								sellerMbrSac.setSellerKey("SE201403041615354320000032");
								sellerMbrSacs.add(sellerMbrSac);
							} else if (i == 1) {
								sellerMbrSac.setSellerKey("IF1023498592920130510112417");
								sellerMbrSacs.add(sellerMbrSac);
							} else if (i == 2) {
								sellerMbrSac.setSellerBizNumber("1352668460");
								sellerMbrSacs.add(sellerMbrSac);
							}
						}
						req.setSellerMbrSacList(sellerMbrSacs);
						return req;
					}
				}).success(DetailInformationSacRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						DetailInformationSacRes res = (DetailInformationSacRes) result;
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
