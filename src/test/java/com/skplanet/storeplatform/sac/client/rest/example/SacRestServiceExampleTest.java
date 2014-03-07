package com.skplanet.storeplatform.sac.client.rest.example;

import static org.junit.Assert.assertTrue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientError;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

public class SacRestServiceExampleTest {

	private final SacRestServiceExample example = new SacRestServiceExample();

	@Test
	public void testListPaticpers() {
		ListScorePaticpersSacReq param = new ListScorePaticpersSacReq();
		param.setProdId("0000647637");

		try {
			ListScorePaticpersSacRes data = this.example.listPaticpers(param);
			System.out.println("# Data : \n" + data);
			assertTrue(CollectionUtils.isNotEmpty(data.getAvgScoreList()));
		} catch (SacRestClientException e) {
			SacRestClientError error = e.getError();
			System.out.println("# Error : \n" + error);
			assertTrue(StringUtils.isNotEmpty(error.getCode()));
		}
	}

	@Test
	public void testModifyFeedback() {
		ModifyFeedbackSacReq body = new ModifyFeedbackSacReq();
		body.setProdId("0000059641");
		body.setUserKey("IW1023350238820110701120455");
		body.setUserId("shop_7842");
		body.setAvgScore("2");

		try {
			ModifyFeedbackSacRes data = this.example.modifyFeedback(body);
			System.out.println("# Data : \n" + data);
			assertTrue(StringUtils.isNotEmpty(data.getProdId()));
		} catch (SacRestClientException e) {
			SacRestClientError error = e.getError();
			System.out.println("# Error : \n" + error);
			assertTrue(StringUtils.isNotEmpty(error.getCode()));
		}
	}

}
