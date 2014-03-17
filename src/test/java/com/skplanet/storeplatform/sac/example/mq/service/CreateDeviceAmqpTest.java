package com.skplanet.storeplatform.sac.example.mq.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StopWatch;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceAmqpSacReq;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateDeviceAmqpTest {

	@Resource(name = "memberAddDeviceAmqpTemplate")
	private AmqpTemplate memberAddDeviceAmqpTemplate;

	class Worker implements Runnable {
		@Override
		public void run() {
			CreateDeviceAmqpSacReq mqInfo = new CreateDeviceAmqpSacReq();

			mqInfo.setWorkDt("20140310160101");
			mqInfo.setUserKey("US201403071349411360004171");
			mqInfo.setOldUserKey("US201403071349411360004171");
			mqInfo.setDeviceKey("01035870955");
			mqInfo.setOldDeviceKey("01035870955");
			mqInfo.setDeviceId("01035870955");
			mqInfo.setMnoCd("US001201"); // SKT

			CreateDeviceAmqpTest.this.memberAddDeviceAmqpTemplate.convertAndSend(mqInfo);

			System.out.println("[" + this.toString() + "]convertAndSend");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * Amqp 단말추가정보 입력.
	 * </pre>
	 */
	@Test
	public void convertAndSend() {
		StopWatch stopwatch = new StopWatch("MQ Send Test");
		stopwatch.start();

		int count = 1;
		ExecutorService executor = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			Runnable worker = new Worker();
			executor.execute(worker);
		}
		executor.shutdown();

		stopwatch.stop();
		System.out.println(stopwatch.shortSummary());

	}

	/**
	 * 
	 * <pre>
	 * Amqp 정보확인
	 * </pre>
	 */
	// @Test
	public void receive() {
		Message message = null;
		int count = 0;
		try {
			while ((message = this.memberAddDeviceAmqpTemplate.receive("sac.tenant.member-add-device.async")) != null) {
				System.out.println((++count) + "message = " + message);
				byte[] jsonData = message.getBody();
				CreateDeviceAmqpSacReq obj = new ObjectMapper().readValue(jsonData, CreateDeviceAmqpSacReq.class);
				System.out.println(obj.toString());
			}
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0099", e);
		}
	}
}
