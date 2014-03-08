package com.skplanet.storeplatform.sac.example.mq.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StopWatch;

import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceAmqpSacReq;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ModifyDeviceAmqpTest {

	@Resource(name = "memberModDeviceAmqpTemplate")
	private AmqpTemplate memberModDeviceAmqpTemplate;

	class Worker implements Runnable {
		@Override
		public void run() {
			ModifyDeviceAmqpSacReq mqInfo = new ModifyDeviceAmqpSacReq();
			mqInfo.setMbrNo("IM190000008392320140308115548");
			mqInfo.setMnoCd("US001201"); // SKT
			mqInfo.setDeviceKey("01035870955");
			mqInfo.setDeviceId("01035870955");
			ModifyDeviceAmqpTest.this.memberModDeviceAmqpTemplate.convertAndSend(mqInfo);

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
	 * Amqp 단말 수정 정보 입력.
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
		while ((message = this.memberModDeviceAmqpTemplate.receive("sac.tenant.member.mod-device.async")) != null) {
			System.out.println((++count) + "message = " + message);
		}
	}
}
