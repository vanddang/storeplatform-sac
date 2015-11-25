package com.skplanet.storeplatform.sac.client.rest.apache;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.skplanet.storeplatform.sac.client.rest.vo.SacRestRequest;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestScheme;

public class UrlFormatterTest {

	@Test
	public void test() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("prodId", "0000059641");
		param.put("userId", "shop_7842");

		SacRestRequest request = new SacRestRequest();
		request.setScheme(SacRestScheme.http);
		request.setHost("devspweb1.sungsu.skplanet.com");
		request.setPath("/sp_sac/other/feedback/listScorePaticpers/v1");
		request.setParam(param);

		String url = UrlFormatter.formatUrl(request);
		System.out.println(url);
		assertEquals("http://devspweb1.sungsu.skplanet.com/sp_sac/other/feedback/listScorePaticpers/v1?userId=shop_7842&prodId=0000059641", url);
	}

}
