/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.v1.purchase;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.integration.StorePlatformAPIinvokorNew;
import com.skplanet.storeplatform.framework.test.integration.SuccessCallbackForJson;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceItemSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacReq;
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 조용진, 엔텔스.
 */

public class PurchaseExistenceControllerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void before() {
	}

	@Test
	public void existenceControllerTest() throws Exception {

		StorePlatformAPIinvokorNew.create().url("http://localhost:8010/purchase/history/existence/list/v1")
				.method(HttpPost.class).accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						ExistenceSacReq existenceReq = new ExistenceSacReq();
						List<ExistenceItemSac> productList = new ArrayList<ExistenceItemSac>();
						ExistenceItemSac product = new ExistenceItemSac();

						existenceReq.setTenantId("S01");
						existenceReq.setUserKey("IW1023795408420101206143202");
						existenceReq.setDeviceKey("01040449015");
						existenceReq.setPrchsId("");

						product.setProdId("H900000037");
						product.setTenantProdGrpCd("");
						productList.add(product);

						product = new ExistenceItemSac();
						product.setProdId("H000044893");
						product.setTenantProdGrpCd("");
						productList.add(product);

						existenceReq.setProductList(productList);
						return existenceReq;
					}
				}).success(ExistenceListSacRes.class, new SuccessCallbackForJson() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(Object result) throws Exception {
						@SuppressWarnings("unchecked")
						ExistenceListSacRes existenceListRes = (ExistenceListSacRes) result;
						for (int i = 0; i < existenceListRes.getExistenceList().size(); i++) {
							PurchaseExistenceControllerTest.this.logger.debug(
									"@@@@@@@@@@@@ getPrchsId @@@@@@@@@@@@@@@@@@@ : {}", existenceListRes
											.getExistenceList().get(i).getPrchsId());
							PurchaseExistenceControllerTest.this.logger.debug(
									"@@@@@@@@@@@@ getProdId  @@@@@@@@@@@@@@@@@@@ : {}", existenceListRes
											.getExistenceList().get(i).getProdId());
						}
						// ExistenceRes ExistenceRes = (ExistenceRes) result;
						// System.out.println("@@@@@@@@@@@@ getPrchsId @@@@@@@@@@@@@@@@@@@ : " +
						// ExistenceRes.getPrchsId());
						assertThat(existenceListRes, notNullValue());
					}
				}).run();
	}
}
