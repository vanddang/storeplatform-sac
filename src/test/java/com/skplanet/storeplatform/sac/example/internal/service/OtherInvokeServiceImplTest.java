package com.skplanet.storeplatform.sac.example.internal.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.client.internal.other.feedback.vo.UserReflectSacReq;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class OtherInvokeServiceImplTest {

	@Autowired
	private OtherInvokeService service;

	@Test
	public void testReflectOne() {
		UserReflectSacReq req = new UserReflectSacReq();
		// req.setTenantId("S01");
		req.setOldUserId("USER_ID#1");
		req.setNewUserId("USER_ID#1-1");
		req.setOldUserKey("USER_KEY#1");
		req.setNewUserKey("USER_KEY#1-1");

		this.service.reflectOne(req);
	}

	@Test
	public void testReflectList() {
		List<UserReflectSacReq> reqList = new ArrayList<UserReflectSacReq>();

		for (int i = 1; i <= 5; i++) {
			UserReflectSacReq req = new UserReflectSacReq();
			req.setTenantId("S01");
			req.setOldUserId("USER_ID#" + i);
			req.setNewUserId("USER_ID#" + i + "-1");
			req.setOldUserKey("USER_KEY#" + i);
			req.setNewUserKey("USER_KEY#" + i + "-1");
			reqList.add(req);
		}

		this.service.reflectList(reqList);
	}

}
