package com.skplanet.storeplatform.sac.api.v1.member.idp;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningReq;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningRes;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ModifyProfileTest {

	private static final Logger logger = LoggerFactory.getLogger(ModifyProfileTest.class);

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MemberCommonComponent commService;

	@Autowired
	private IDPService idpService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	IDPRepository idpRepository;

	private MockMvc mockMvc;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 신규가입정보를 미동의 사이트에 배포
	 * </pre>
	 */
	@Test
	public void ModifyProfileTest01() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST).requestBody(new RequestBodySetter() {
			@Override
			public Object requestBody() {
				ProvisioningReq req = new ProvisioningReq();
				req.setCmd("modifyProfile");
				HashMap<String, String> resultMap = new HashMap<String, String>();

				resultMap.put("systemID", "S001");
				resultMap.put("tenantID", "S01");

				resultMap.put("sp_id", "90000");
				resultMap.put("target_sst_cd", "10100");
				resultMap.put("im_int_svc_no", " 2222222");
				resultMap.put("user_id", "test");
				resultMap.put("idp_id", "test01");
				resultMap.put("im_mem_type_cd", "100");

				req.setReqParam(resultMap);

				logger.info("request param : {}", req.toString());
				return req;
			}
		}).success(ProvisioningRes.class, new SuccessCallback() {
			@Override
			public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
				ProvisioningRes res = (ProvisioningRes) result;
				// res.get
				// assertThat(res.getSellerKey(), notNullValue());
				logger.info("response param : {}", res.toString());
			}
		}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	@Test
	public void ModifyProfileTest02() {

		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S001");
		tenantHeader.setTenantId("S01");

		SacRequestHeader header = new SacRequestHeader();
		header.setTenantHeader(tenantHeader);

		try {

			String userKey = "US201401161113423010000110";
			String userAuthKey = "114127c7ef42667669819dad5df8d820c";

			/* sc회원 컴포넌트 휴대기기 목록 조회 */
			ListDeviceReq listDeviceReq = new ListDeviceReq();
			listDeviceReq.setIsMainDevice("Y");// 대표기기만 조회(Y), 모든기기 조회(N)
			listDeviceReq.setUserKey(userKey);
			ListDeviceRes listDeviceRes = this.deviceService.listDevice(header, listDeviceReq);

			List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
			String userPhoneStr = null;
			if (deviceInfoList != null) {
				StringBuffer sbUserPhone = new StringBuffer();
				for (DeviceInfo deviceInfo : deviceInfoList) {
					sbUserPhone.append(deviceInfo.getDeviceId());
					sbUserPhone.append(",");
					sbUserPhone.append(deviceInfo.getImMngNum() == null ? "" : deviceInfo.getImMngNum());
					sbUserPhone.append("");
					sbUserPhone.append(",");
					sbUserPhone.append(deviceInfo.getUacd() == null ? "" : deviceInfo.getUacd());
					sbUserPhone.append("");
					sbUserPhone.append(",");
					sbUserPhone.append("KTF");
					sbUserPhone.append("|");
				}
				userPhoneStr = sbUserPhone.toString();

				userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));
				logger.info("::: userPhoneStr : {} ", userPhoneStr);
			}

			/* IDP 휴대기기 정보 등록 요청 */
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("user_auth_key", userAuthKey);
			param.put("user_sex", "1");
			param.put("user_birthday", "19831210");
			param.put("user_zipcode", "123123");
			param.put("user_address", "판교");
			param.put("user_address2", "123-123");
			param.put("user_tel", "021231234");
			param.put("is_foreign", "N");
			param.put("key_type", "2");
			param.put("key", "IW1312839620140116201341");
			if (userPhoneStr != null) {
				param.put("user_phone", userPhoneStr);
				param.put("phone_auth_key", this.idpRepository.makePhoneAuthKey(userPhoneStr));
			}
			IDPReceiverM idpReceiver = this.idpService.modifyProfile(param);
			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[" + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String converterTelecomCode(String code) {
		String value = "";
		if (code.equals(MemberConstants.DEVICE_TELECOM_SKT)) {
			value = "SKT";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_KT)) {
			value = "KTF";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_LGT)) {
			value = "LGT";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_OMD)) {
			value = "OMD";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_NSH)) {
			value = "NSH";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_NON)) {
			value = "NON";
		} else if (code.equals(MemberConstants.DEVICE_TELECOM_IOS)) {
			value = "IOS";
		}
		return value;
	}
}
