package com.skplanet.storeplatform.sac.system.localsci.controller;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.BannerInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Updated on : 2016-01-06. Updated by : 양해엽, SK Planet.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class VerifyLocalSCITest {
    private static final Logger logger = LoggerFactory.getLogger(VerifyLocalSCITest.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test_find_bean_by_class_type() throws ClassNotFoundException {
        Object bean = this.applicationContext.getBean(BannerInfoSCI.class);

        assertNotNull(bean);
    }

    @Test
    public void test_find_bean_by_fully_qualified_name() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.BannerInfoSCI");
        Object bean = this.applicationContext.getBean(clazz);

        assertNotNull(bean);
    }

    @Test
    public void test_invoke_method_by_bean() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.BannerInfoSCI");
        Object bean = this.applicationContext.getBean(clazz);

        BannerInfoSacReq req = new BannerInfoSacReq();
        req.setTenantId("S01");
        req.setBnrMenuId("DP010929");
        req.setBnrExpoMenuId("DP011100");
        req.setImgSizeCd("DP011033");
        req.setCount(3);
        Object res = ((BannerInfoSCI) bean).getBannerInfoList(req);
        logger.info(ReflectionToStringBuilder.toString(res, ToStringStyle.MULTI_LINE_STYLE));

        assertNotNull(res);
    }

    @Test
    public void test_invoke_method_by_BannerInfoSCI() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        VerifyLocalSCIReq req = makeReq();

        Class<?> clazz = Class.forName(req.getLocalSCIName());
        Object bean = this.applicationContext.getBean(clazz);

        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (StringUtils.equals(req.getInvokeMethod(), method.getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes.length == 1) {

                    ModelMapper modelMapper = new ModelMapper();
                    Object invokeReqParam = modelMapper.map(req.getInvokeParam(), parameterTypes[0]);
                    logger.info(ReflectionToStringBuilder.toString(invokeReqParam, ToStringStyle.MULTI_LINE_STYLE));

                    Object res = method.invoke(bean, invokeReqParam);
                    logger.info(ReflectionToStringBuilder.toString(res, ToStringStyle.MULTI_LINE_STYLE));

                    assertNotNull(res);
                }
            }
        }
    }

    private VerifyLocalSCIReq makeReq() {
        VerifyLocalSCIReq verifyLocalSCIReq = new VerifyLocalSCIReq();
        verifyLocalSCIReq.setLocalSCIName("com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.BannerInfoSCI");
        verifyLocalSCIReq.setInvokeMethod("getBannerInfoList");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", "S01");
        param.put("bnrMenuId", "DP010929");
        param.put("bnrExpoMenuId", "DP011100");
        param.put("imgSizeCd", "DP011033");
        param.put("count", 3);

        List<String> prodIds = new ArrayList<String>();
        prodIds.add("prodId1");
        prodIds.add("prodId2");

        param.put("prodIds", prodIds);

        verifyLocalSCIReq.setInvokeParam(param);

        return verifyLocalSCIReq;
    }

}
