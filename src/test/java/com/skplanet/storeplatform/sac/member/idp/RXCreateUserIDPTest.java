package com.skplanet.storeplatform.sac.member.idp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.junit.After;
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

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningReq;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class RXCreateUserIDPTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RXCreateUserIDPTest.class);

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private UserSCI userSCI;

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
	 * 테스트로 신규가입한 회원을 탈퇴.
	 * </pre>
	 */
	@After
	public void after() {

		// 14세미만 가입삭제
		System.out.println("test after");
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("MBR_ID");

		// this.userSCI.searchUser(searchUserRequest);

	}

	/*
	 * @Test public void rxTest() { System.out.println("test1"); }
	 * 
	 * @Test public void rxTest1() { System.out.println("test2"); }
	 */

	/**
	 * <pre>
	 * 통합회원 전환생성정보를 사이트에 배포 (신규가입 14세미만 부모약관동의).
	 * </pre>
	 */
	@Test
	public void rXCreateUserIDPNewTypeOne() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXCreateUserIDP");
						HashMap<String, String> resultMap = new HashMap<String, String>();

						resultMap.put("tenantID", "S01");
						resultMap.put("systemID", "S001");

						/* 신규 가입 테스트 데이터 시작 */

						/* 부모약관동의 Y 시작 */
						resultMap.put("user_di", "yyyyyy");
						resultMap.put("is_parent_approve", "Y");
						resultMap.put("parent_rname_auth_type", "1");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("parent_approve_sst_code", "90300");
						resultMap.put("biz_name", "");
						resultMap.put("rname_auth_sst_code", "90300");
						resultMap.put("user_name", "홍길동");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("rname_auth_mns_code", "");
						resultMap.put("via_sst_code", "90300");
						resultMap.put("is_user_tn_auth", "Y");
						resultMap.put("user_tn_type", "M");
						resultMap.put("user_city_name", "Pittsburgh");
						resultMap.put("is_im_changed", "N");
						resultMap.put("is_email_auth", "Y");
						resultMap.put("user_ci", "xxxxxx");
						resultMap.put("parent_birthday", "1911");
						resultMap
								.put("parent_rname_auth_key",
										"1/1 BV9Y4RRruwJ24F74nz1lES2wgD2EWsAzE3TBd/3571E1EvHiT3W2lt0d5DsO67//bRgO70yUNUKsO8N/aQ==");
						resultMap.put("im_int_svc_no", "100000001371");
						resultMap.put("user_birthday", "19700315");
						resultMap.put("is_mkt_email_recv", "N");
						resultMap.put("is_biz_auth", "N");
						resultMap.put("auth_type", "2");
						resultMap.put("old_id", "");
						resultMap.put("is_user_tn_own", "Y");
						resultMap.put("user_id", "idptest10_0001");
						resultMap.put("user_nation_code", "US");
						resultMap.put("browser_code", "E");
						resultMap.put("parent_approve_date", "20130901");
						resultMap.put("join_sst_code", "20100");
						resultMap.put("user_area_code", "PA");
						resultMap.put("join_ip", "4.2.161.48");
						resultMap.put("ocb_join_code", "N");
						resultMap.put("parent_type", "0");
						resultMap.put("rname_auth_mbr_code", "");
						resultMap.put("user_nation_name", "Unated+States");
						resultMap.put("join_time", "142653");
						resultMap.put("modify_sst_code", "90300");
						resultMap.put("user_area_name", "Pennsylvania");
						resultMap.put("os_code", "A");
						resultMap.put("user_key", "IM110000000137120131007130602");
						resultMap.put("modify_req_time", "092511");
						resultMap.put("user_tn", "01012345678");
						resultMap.put("join_date", "20120306");
						resultMap.put("parent_email", "testtest@test.com");
						resultMap.put("im_mem_type_cd", "100");
						resultMap.put("user_sex", "M");
						resultMap.put("join_path_code", "W");
						resultMap.put("modify_req_date", "20120801");
						resultMap.put("rname_auth_date", "20120801130011");
						resultMap.put("join_sst_list",
								"20100,A^B^C,20120306,142653|41100,11^22,20120306,142653|90300,null,20120306,142653");
						resultMap.put("trx_no", "900002012103113302500002");
						resultMap.put("biz_number", "");
						resultMap.put("user_tn_nation_cd", "82");
						resultMap.put("is_rname_auth", "Y");
						resultMap.put("user_type", "1");
						resultMap.put("user_email", "heeya1215@naver.com");
						resultMap.put("parent_name", "홍삼원");
						resultMap.put("user_status_code", "10");
						/* 부모약관동의 Y log.20131007 끝 */

						/* 신규 가입 테스트 데이터 끝 */

						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						assertThat(res.getImResult(), notNullValue());
						assertThat(res.getImResult().getCmd(), is("RXCreateUserIDP"));
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * <pre>
	 * 통합회원 전환생성정보를 사이트에 배포 (신규가입 14세이상).
	 * </pre>
	 */
	@Test
	public void rXCreateUserIDPNewTypeTwo() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXCreateUserIDP");
						HashMap<String, String> resultMap = new HashMap<String, String>();

						resultMap.put("tenantID", "S01");
						resultMap.put("systemID", "S001");

						/* 신규 가입 테스트 데이터 시작 */

						/* 부모약관 동의 N */
						resultMap.put("user_di", "MC0GCCqGSIb3DQIJAyEAQRV+Hdxtbe2NeY/OV0qwsaIdaxB/dOP1g8hTcrGP0r4=");
						resultMap.put("is_parent_approve", "N");
						resultMap.put("parent_rname_auth_type", "");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("parent_approve_sst_code", "");
						resultMap.put("biz_name", "");
						resultMap.put("rname_auth_sst_code", "41100");
						resultMap.put("user_name", "최승화");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("rname_auth_mns_code", "2");
						resultMap.put("via_sst_code", "90300");
						resultMap.put("is_user_tn_auth", "Y");
						resultMap.put("user_tn_type", "M");
						resultMap.put("user_city_name", "");
						resultMap.put("is_im_changed", "N");
						resultMap.put("is_email_auth", "N");
						resultMap
								.put("user_ci",
										"TNX17GUW0azebKLD53w5wXr+eG538o+pT9OmxTm5fKblau9Nt3yDy766yuWW3+X0hDilAGbYY6tD1rd5Yl1Teg==");
						resultMap.put("parent_birthday", "");
						resultMap.put("parent_rname_auth_key", "");
						resultMap.put("im_int_svc_no", "200000545652");
						resultMap.put("user_birthday", "19820106");
						resultMap.put("is_mkt_email_recv", "");
						resultMap.put("is_biz_auth", "N");
						resultMap.put("auth_type", "4");
						resultMap.put("old_id", "");
						resultMap.put("is_user_tn_own", "Y");
						resultMap.put("user_id", "stg_csh4314");
						resultMap.put("user_nation_code", "KR");
						resultMap.put("browser_code", "");
						resultMap.put("parent_approve_date", "");
						resultMap.put("join_sst_code", "41100");
						resultMap.put("user_area_code", "");
						resultMap.put("join_ip", "121.165.99.42");
						resultMap.put("ocb_join_code", "Y");
						resultMap.put("parent_type", "");
						resultMap.put("rname_auth_mbr_code", "10");
						resultMap.put("user_nation_name", "Korea");
						resultMap.put("join_time", "095129");
						resultMap.put("modify_sst_code", "90300");
						resultMap.put("user_area_name", "");
						resultMap.put("os_code", "");
						resultMap.put("user_key", "IM120000054565220131105095128");
						resultMap.put("modify_req_time", "095129");
						resultMap.put("user_tn", "01050236209");
						resultMap.put("join_date", "20131105");
						resultMap.put("parent_email", "");
						resultMap.put("im_mem_type_cd", "100");
						resultMap.put("user_sex", "M");
						resultMap.put("join_path_code", "W");
						resultMap.put("modify_req_date", "20131105");
						resultMap.put("rname_auth_date", "20131105094942");
						resultMap
								.put("join_sst_list",
										"41100,TAC001^TAC002^TAC003^TAC004^TAC005^TAC006,20131105,095129|42100,ER^PK,20131105,095129|90300,null,20131105,095129");
						resultMap.put("trx_no", "900002013110509512939001");
						resultMap.put("biz_number", "");
						resultMap.put("user_tn_nation_cd", "82");
						resultMap.put("is_rname_auth", "Y");
						resultMap.put("user_type", "1");
						resultMap.put("user_email", "aragon122@gmail.com");
						resultMap.put("parent_name", "");
						resultMap.put("user_status_code", "10");

						/* 신규 가입 테스트 데이터 끝 */

						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						assertThat(res.getImResult(), notNullValue());
						assertThat(res.getImResult().getCmd(), is("RXCreateUserIDP"));
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 
	 * <pre>
	 * 통합회원 전환생성정보를 사이트에 배포 (전환가입).
	 * </pre>
	 */
	@Test
	public void rXCreateUserIDPChangeTypeOne() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXCreateUserIDP");
						HashMap<String, String> resultMap = new HashMap<String, String>();

						resultMap.put("tenantID", "S01");
						resultMap.put("systemID", "S001");

						resultMap.put("user_di", "B0001BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB14TSC0060");
						resultMap.put("is_parent_approve", "Y");
						resultMap.put("parent_rname_auth_type", "3");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("parent_approve_sst_code", "90300");
						resultMap.put("biz_name", "");
						resultMap.put("rname_auth_sst_code", "41100");
						resultMap.put("user_name", "이일이용");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("rname_auth_mns_code", "2");
						resultMap.put("via_sst_code", "90300");
						resultMap.put("is_user_tn_auth", "N");
						resultMap.put("user_tn_type", "M");
						resultMap.put("user_city_name", "");
						resultMap.put("is_im_changed", "Y");
						resultMap.put("is_email_auth", "N");
						resultMap
								.put("user_ci",
										"A0001AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA14TSC0047");
						resultMap.put("parent_birthday", "19780305");
						resultMap
								.put("parent_rname_auth_key",
										"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1U0009");
						resultMap.put("im_int_svc_no", "200000006122");
						resultMap.put("user_birthday", "20100417");
						resultMap.put("is_mkt_email_recv", "");
						resultMap.put("is_biz_auth", "N");
						resultMap.put("auth_type", "");
						resultMap.put("old_id", "omptest0622");
						resultMap.put("is_user_tn_own", "N");
						resultMap.put("user_id", "omptest0622");
						resultMap.put("user_nation_code", "");
						resultMap.put("browser_code", "");
						resultMap.put("parent_approve_date", "20131011");
						resultMap.put("join_sst_code", "41100");
						resultMap.put("user_area_code", "");
						resultMap.put("join_ip", "");
						resultMap.put("ocb_join_code", "Y");
						resultMap.put("parent_type", "0");
						resultMap.put("rname_auth_mbr_code", "");
						resultMap.put("user_nation_name", "");
						resultMap.put("join_time", "145849");
						resultMap.put("modify_sst_code", "90300");
						resultMap.put("user_area_name", "");
						resultMap.put("os_code", "");
						resultMap.put("user_key", "IM120000054522120131028101538");
						resultMap.put("modify_req_time", "145849");
						resultMap.put("user_tn", "01020000048");
						resultMap.put("join_date", "20131011");
						resultMap.put("parent_email", "");
						resultMap.put("im_mem_type_cd", "100");
						resultMap.put("user_sex", "M");
						resultMap.put("join_path_code", "W");
						resultMap.put("modify_req_date", "20131011");
						resultMap.put("rname_auth_date", "20131011145817");
						resultMap
								.put("join_sst_list",
										"41100,null,20131011,145849,omptest0622|42100,ER^PK,20131011,145849|90300,null,20131011,145849");
						resultMap.put("trx_no", "900002013101114584949976");
						resultMap.put("biz_number", "");
						resultMap.put("user_tn_nation_cd", "82");
						resultMap.put("is_rname_auth", "Y");
						resultMap.put("user_type", "1");
						resultMap.put("user_email", "s_1settestid185@gmail.com");
						resultMap.put("parent_name", "부모님");
						resultMap.put("user_status_code", "10");
						resultMap.put("user_tn_nation_cd", "82");

						/* 전환가입 테스트 데이터 끝 */

						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						assertThat(res.getImResult(), notNullValue());
						assertThat(res.getImResult().getCmd(), is("RXCreateUserIDP"));
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

	/**
	 * 
	 * <pre>
	 * 통합회원 전환생성정보를 사이트에 배포 (변경전환,변경가입).
	 * </pre>
	 */
	@Test
	public void rXCreateUserIDPChangeTypeTwo() {

		new TestCaseTemplate(this.mockMvc).url("/member/idp/provisioning/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ProvisioningReq req = new ProvisioningReq();
						req.setCmd("RXCreateUserIDP");
						HashMap<String, String> resultMap = new HashMap<String, String>();

						resultMap.put("tenantID", "S01");
						resultMap.put("systemID", "S001");

						resultMap.put("user_di", "B3BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBC1174");
						resultMap.put("is_parent_approve", "N");
						resultMap.put("parent_rname_auth_type", "");
						resultMap.put("sp_id", "OMP10000");
						resultMap.put("parent_approve_sst_code", "");
						resultMap.put("biz_name", "");
						resultMap.put("rname_auth_sst_code", "41100");
						resultMap.put("user_name", "유숙영");
						resultMap.put("target_sst_cd", "41100");
						resultMap.put("rname_auth_mns_code", "9");
						resultMap.put("via_sst_code", "90300");
						resultMap.put("is_user_tn_auth", "N");
						resultMap.put("user_tn_type", "T");
						resultMap.put("user_city_name", "");
						resultMap.put("is_im_changed", "C");
						resultMap.put("is_email_auth", "Y");
						resultMap
								.put("user_ci",
										"A3AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAC1161");
						resultMap.put("parent_birthday", "");
						resultMap.put("parent_rname_auth_key", "");
						resultMap.put("im_int_svc_no", "980000005612");
						resultMap.put("user_birthday", "19780429");
						resultMap.put("is_mkt_email_recv", "");
						resultMap.put("is_biz_auth", "N");
						resultMap.put("auth_type", "2");
						resultMap.put("old_id", "");
						resultMap.put("is_user_tn_own", "N");
						resultMap.put("user_id", "Tochun02Change");
						resultMap.put("user_nation_code", "KR");
						resultMap.put("browser_code", "");
						resultMap.put("parent_approve_date", "");
						resultMap.put("join_sst_code", "41100");
						resultMap.put("user_area_code", "");
						resultMap.put("join_ip", "");
						resultMap.put("ocb_join_code", "N");
						resultMap.put("parent_type", "");
						resultMap.put("rname_auth_mbr_code", "");
						resultMap.put("user_nation_name", "Korea");
						resultMap.put("join_time", "164207");
						resultMap.put("modify_sst_code", "90300");
						resultMap.put("user_area_name", "");
						resultMap.put("os_code", "");
						resultMap.put("user_key", "IF102815905620090809145354");
						resultMap.put("modify_req_time", "164207");
						resultMap.put("user_tn", "01010000061");
						resultMap.put("join_date", "20131007");
						resultMap.put("parent_email", "");
						resultMap.put("im_mem_type_cd", "100");
						resultMap.put("user_sex", "M");
						resultMap.put("join_path_code", "W");
						resultMap.put("modify_req_date", "20131007");
						resultMap.put("rname_auth_date", "20131007164207");
						resultMap
								.put("join_sst_list", "41100,null,20131007,164207,Tochun02|90300,null,20131007,164207");
						resultMap.put("trx_no", "900002013100716420801023");
						resultMap.put("biz_number", "");
						resultMap.put("user_tn_nation_cd", "82");
						resultMap.put("is_rname_auth", "Y");
						resultMap.put("user_type", "2");
						resultMap.put("user_email", "tochun7@nate.com");
						resultMap.put("parent_name", "");
						resultMap.put("user_status_code", "10");

						/* 전환가입 테스트 데이터 끝 */

						req.setReqParam(resultMap);

						LOGGER.info("request param : {}", req.toString());
						return req;
					}
				}).success(ProvisioningRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						ProvisioningRes res = (ProvisioningRes) result;
						assertThat(res.getImResult(), notNullValue());
						assertThat(res.getImResult().getCmd(), is("RXCreateUserIDP"));
						LOGGER.info("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}

}
