package com.skplanet.storeplatform.sac.example.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.example.exceptionhandling.sci.ExceptionHandlingSCI;
import com.skplanet.storeplatform.external.client.example.exceptionhandling.vo.UserSearch;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.example.ec.controller.EchoController;

@Controller
@RequestMapping("/example/exceptionhandling")
public class ExceptionHandlingController {
	private static final Logger logger = LoggerFactory.getLogger(EchoController.class);

	@Autowired
	private ExceptionHandlingSCI exceptionHandlingSCI;

	@RequestMapping(value = "/sacSimpleCase", method = RequestMethod.POST)
	public @ResponseBody
	UserSearch sacSimpleCase(@RequestBody UserSearch userSearch) {
		return userSearch;

	}

	@RequestMapping(value = "/sacComplexCase", method = RequestMethod.POST)
	public @ResponseBody
	UserSearch sacComplexCase(@RequestBody UserSearch userSearch) {
		try {
			try {
				try {
					try {
						try {
							throw new NullPointerException("사용자정보가 NULL임.");
						} catch (Exception ex) {
							throw ex;
						}
					} catch (Exception ex) {
						throw new IllegalArgumentException("사용자정보가 정상적으로 입력되지 않았음.", ex);
					}

				} catch (Exception ex) {
					throw new StorePlatformException("SC_MEM_0002", ex);
				}
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_MEM_0002", ex);
			}
		} catch (Exception ex) {
			throw new StorePlatformException("SAC_001", ex, "SAC_MEM_0002");
		}
	}

	@RequestMapping(value = "/sciSimpleCase", method = RequestMethod.POST)
	public @ResponseBody
	UserSearch sciSimpleCase(@RequestBody UserSearch userSearch) {
		return this.exceptionHandlingSCI.simpleCase(userSearch);

	}

	@RequestMapping(value = "/sciComplexCase", method = RequestMethod.POST)
	public @ResponseBody
	UserSearch sciComplexCase(@RequestBody UserSearch userSearch) {
		return this.exceptionHandlingSCI.complexCase(userSearch);
	}
}
