package com.skplanet.storeplatform.sac.example.mq.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StopWatch;

import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceAmqpSacReq;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class RemoveDeviceAmqpTest {

	// @Resource(name = "memberDelDeviceAmqpTemplate")
	// private AmqpTemplate memberDelDeviceAmqpTemplate;

	class Worker implements Runnable {
		@Override
		public void run() {
			RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();
			mqInfo.setWorkDt("20140310130101");
			mqInfo.setUserKey("US201403071349411360004171");
			mqInfo.setDeviceKey("01091074897");
			mqInfo.setDeviceId("01091074897");
			mqInfo.setChgCaseCd("US012012"); // 번호이동
			mqInfo.setSvcMangNo("7032891742");

			// RemoveDeviceAmqpTest.this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);

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
	 * Amqp 단말 삭제 정보 입력.
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
	@Test
	public void receive() {
		Message message = null;
		int count = 0;
		// while ((message = this.memberDelDeviceAmqpTemplate.receive("sac.tenant.member.del-device.async")) != null) {
		// System.out.println((++count) + "message = " + message);
		// }
	}
}
