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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.sci.ShoppingInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.IdentifierSac;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.PriceSac;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.SalesOptionSac;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.SelectOptionSac;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.ShoppingSacInReq;
import com.skplanet.storeplatform.sac.client.internal.display.shopping.vo.ShoppingSacInRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SelectOption;
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
		SalesOptionSac aa = new SalesOptionSac();
		if (sacRes != null) {

			if (sacRes.getProductList().size() > 0) {
				SalesOption vo1 = new SalesOption(); // 쿠폰 정보
				vo1.setBtob("11123423423");
				SalesOptionSac vo = new SalesOptionSac(); // 쿠폰 정보
				// this.voCopy(vo1, vo);

				this.voCopy(sacRes.getProductList().get(0), response);
				this.voCopy(sacRes.getProductList().get(0).getSubProductList().get(0).getPrice(), response.getPrice());
				// System.out.println("+123++++" + sacRes.getProductList().get(0).getSalesOption().getBtob());
				// aa = this.setSaleOption(sacRes.getProductList().get(0).getSalesOption());
				System.out.println("ss");
			}

		}
		return response;

	}

	/**
	 * @param List
	 *            <Identifier> reqVo
	 * @return List<IdentifierSac>
	 */
	private List<IdentifierSac> setIdentifierList(List<Identifier> reqVo) {
		List<IdentifierSac> resVo = new ArrayList<IdentifierSac>();
		this.voCopy(reqVo, resVo);
		System.out.println("sdjfakjadflds++" + resVo.toString());
		return resVo;
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
	private PriceSac setPrice(Price reqVo) {
		PriceSac resVo = new PriceSac();
		this.voCopy(reqVo, resVo);
		return resVo;
	}

	/**
	 * @param List
	 *            <SelectOption> reqVo
	 * @return List<SelectOptionSac>
	 */
	private List<SelectOptionSac> setSelectOptionList(List<SelectOption> reqVo) {
		List<SelectOptionSac> resVo = new ArrayList<SelectOptionSac>();
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
				System.out.println("entry::" + entry);
				System.out.println("resultMap.get(entry)::" + resultMap.get(entry));
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
