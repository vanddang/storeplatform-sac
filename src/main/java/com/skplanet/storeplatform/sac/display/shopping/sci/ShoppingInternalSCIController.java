/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.shopping.sci;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.sci.ShoppingInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.PriceSac;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.SalesOptionSac;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.ShoppingSacInReq;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.ShoppingSacInRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.display.shopping.service.ShoppingInternalService;

/**
 * 쇼핑 판매건수 Controller
 * 
 * Updated on : 2014. 02. 13. Updated by : 김형식
 */
@LocalSCI
public class ShoppingInternalSCIController implements ShoppingInternalSCI {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShoppingInternalService service;

	/**
	 * 쇼핑 판매건수 기능을 제공한다.
	 * 
	 * @param request
	 *            쇼핑 판매건수
	 * @return ShoppingSacInRes
	 */
	@Override
	public ShoppingSacInRes searchShoppingList(ShoppingSacInReq request) {
		this.log.debug(">>>> >>> ShoppingSacInReq: {}", request);

		ShoppingReq sacReq = new ShoppingReq();
		ShoppingRes sacRes = new ShoppingRes();

		sacReq.setTenantId(request.getTenantId());
		sacReq.setDeviceModelCd(request.getDeviceModelCd());
		sacReq.setLangCd(request.getLangCd());
		sacReq.setImageCd(request.getImageCd());
		sacReq.setProdId(request.getChannelId());
		sacReq.setSpecialProdId(request.getEpisodeId());

		sacRes = this.service.searchShoppingInternal(sacReq);
		ShoppingSacInRes response = new ShoppingSacInRes();
		if (sacRes != null) {
			if (sacRes.getProductList().size() > 1) {
				response.setSalesOption(this.setSaleOption(sacRes.getProductList().get(0).getSalesOption()));
				response.setPrice(this.setPricet(sacRes.getProductList().get(0).getPrice()));
			}
		}
		return response;

	}

	/**
	 * @param salesOption
	 * @return SalesOptionSac
	 */
	private SalesOptionSac setSaleOption(SalesOption reqVo) {
		SalesOptionSac resVo = new SalesOptionSac();
		this.voCopy(reqVo, resVo);
		return resVo;
	}

	/**
	 * @param Price
	 * @return PriceSac
	 */
	private PriceSac setPricet(Price reqVo) {
		PriceSac resVo = new PriceSac();
		this.voCopy(reqVo, resVo);
		return resVo;
	}

	/**
	 * <pre>
	 * VO 복사
	 * </pre>
	 * 
	 * @param Object
	 *            obj1
	 * @param Object
	 *            obj2
	 * @return
	 */
	private void voCopy(Object obj1, Object obj2) {

		Map resultMap = new HashMap();
		try {
			Field[] fields = obj1.getClass().getDeclaredFields();
			for (int i = 0; i <= fields.length - 1; i++) {
				fields[i].setAccessible(true);
				resultMap.put(fields[i].getName(), fields[i].get(obj1));
			}
			Iterator<String> keys = resultMap.keySet().iterator();
			while (keys.hasNext()) {
				String entry = keys.next();
				this.invoke(obj2, "set" + entry.substring(0, 1).toUpperCase() + entry.substring(1),
						new Object[] { resultMap.get(entry) });
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 특정 클래스의 내용을 invoke
	 * 
	 * @param obj
	 *            Method Invoke할 오브젝트
	 * @param methodName
	 *            Method Name
	 * @param objList
	 *            Parameter Object List
	 * @return boolean
	 */
	private boolean invoke(Object obj, String methodName, Object[] objList) {
		Method[] methods = obj.getClass().getMethods();
		boolean result = false;

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				try {
					if (methods[i].getReturnType().getName().equals("void")) {
						result = true;
						methods[i].invoke(obj, objList);
					}
				} catch (IllegalAccessException lae) {
					this.log.info("LAE : " + lae.getMessage());
				} catch (InvocationTargetException ite) {
					this.log.info("ITE : " + ite.getMessage());
				}
			}
		}
		return result;
	}

}
