package com.skplanet.storeplatform.sac.display

import com.skplanet.storeplatform.framework.test.RequestBodySetter
import com.skplanet.storeplatform.framework.test.SuccessCallback
import com.skplanet.storeplatform.framework.test.TestCaseTemplate
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext

import static org.hamcrest.CoreMatchers.notNullValue
import static org.junit.Assert.assertThat

/**
 * 앱 상세보기 테스트
 *
 * Updated on : 2014. 01. 06 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(["classpath*:/spring-test/context-test.xml"])
@TransactionConfiguration
@Transactional
class AppDetailTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void get() {
        new TestCaseTemplate(this.mockMvc)
                .url("http://localhost:8080/display/app/v1").httpMethod(HttpMethod.GET)
                .addHeaders("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
                .requestBody(new RequestBodySetter() {
            @Override
            public Object requestBody() {
                return new AppDetailReq(seedApp: "N", episodeId: "100")
            }
        }).success(AppDetailRes.class, new SuccessCallback() {
            @Override
            public void success(Object result, HttpStatus httpStatus, TestCaseTemplate.RunMode runMode) {
                def res = result as AppDetailRes
                assertThat(res, notNullValue());
            }
        }, HttpStatus.OK, HttpStatus.ACCEPTED).run(TestCaseTemplate.RunMode.JSON);
    }

}
