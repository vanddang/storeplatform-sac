package com.skplanet.storeplatform.sac.system.localsci.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Updated on : 2016-01-06. Updated by : 양해엽, SK Planet.
 */
@Controller
@RequestMapping(value = "/system/localsci")
public class VerifyLocalSCIController {

    private static final Logger logger = LoggerFactory.getLogger(VerifyLocalSCIController.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * localsci 검증용 controller.
     * localsci을 직접 조회 해 볼 수 없어서, 이슈대응에 어려움을 해결 하기 위해 작성.
     * 실제 서비스에서는 사용하지 않음.
     */
    @RequestMapping(value = "/verify/v1", method = RequestMethod.POST)
    @ResponseBody
    public Object getLocalSci(@Validated @RequestBody VerifyLocalSCIReq req)
            throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        logger.info(ReflectionToStringBuilder.toString(req, ToStringStyle.MULTI_LINE_STYLE));

        if (req.getInvokeParam() == null) {
            throw new StorePlatformException(getErrorInfo("invokeParam value must be set"));
        }

        Class<?> clazz = Class.forName(req.getLocalSCIName());;

        Object bean = this.applicationContext.getBean(clazz);

        Method method = getMethod(clazz.getMethods(), req.getInvokeMethod());

        ModelMapper modelMapper = new ModelMapper();
        Object invokeReqParam = modelMapper.map(req.getInvokeParam(), getParameterType(method));

        return method.invoke(bean, invokeReqParam);
    }

    private Method getMethod(Method[] methods, String invokeMethod) {

        for (Method method : methods) {

            if (StringUtils.equals(invokeMethod, method.getName())) {
                return method;
            }
        }

        throw new StorePlatformException(getErrorInfo("not found invokeMethod : " + invokeMethod));
    }

    private Class<?> getParameterType(Method method) {

        Class<?>[] parameterTypes = method.getParameterTypes();

        if (parameterTypes.length == 1) {
            return parameterTypes[0];
        }

        throw new StorePlatformException(getErrorInfo("parameter type of invoke method must be 1"));
    }

    private ErrorInfo getErrorInfo(String message) {
        ErrorInfo error = new ErrorInfo();
        error.setCode("SAC_DSP_9999");
        error.setMessage(message);

        return error;
    }

}
