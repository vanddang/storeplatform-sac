package com.skplanet.storeplatform.sac.example.mq.service;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StopWatch;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AmqpServiceImplTest {

	@Resource(name = "memberAddDeviceAmqpTemplate")
	private AmqpTemplate memberAddDeviceAmqpTemplate;

	class Worker implements Runnable {
		@Override
		public void run() {
			TestMessage message = new TestMessage("param1", "param2", "param3", "param4");
			AmqpServiceImplTest.this.memberAddDeviceAmqpTemplate.convertAndSend(message);
			try {
			Thread.sleep(1000);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		}
	}

	// @Test
	public void convertAndSend() {
		StopWatch stopwatch = new StopWatch("MQ Send Test");
		stopwatch.start();

		int count = 100;
		ExecutorService executor = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
		Runnable worker = new Worker();
		executor.execute(new Thread() {

		});
		}
		executor.shutdown();

		stopwatch.stop();
		;
		System.out.println(stopwatch.shortSummary());
		// StopWatch 'MQ Send Test': running time (millis) = 105
	}

	// @Test
	public void receive() {
		Message message = null;
		int count = 0;
		while ((message = this.memberAddDeviceAmqpTemplate.receive("sac.tenant.member.add-device.async")) != null) {
		System.out.println((++count) + "message = " + message);
		}
	}

	class TestMessage implements Serializable {

		String param1;
		String param2;
		String param3;
		String param4;

		TestMessage(String param1, String param2, String param3, String param4) {
			this.param1 = param1;
			this.param2 = param2;
			this.param3 = param3;
			this.param4 = param4;
		}

		public String getParam1() {
			return this.param1;
		}

		public void setParam1(String param1) {
			this.param1 = param1;
		}

		public String getParam2() {
			return this.param2;
		}

		public void setParam2(String param2) {
			this.param2 = param2;
		}

		public String getParam3() {
			return this.param3;
		}

		public void setParam3(String param3) {
			this.param3 = param3;
		}

		public String getParam4() {
			return this.param4;
		}

		public void setParam4(String param4) {
			this.param4 = this.param4;
		}
	}
}
