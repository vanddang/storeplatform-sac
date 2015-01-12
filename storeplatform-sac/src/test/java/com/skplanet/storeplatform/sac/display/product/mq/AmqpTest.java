package com.skplanet.storeplatform.sac.display.product.mq;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-amqp-test.xml" })
public class AmqpTest {

	// @Resource(name = "testRabbitTemplate")
	// private AmqpTemplate template;
	//
	// @Test
	// public void convertAndSend1() throws IOException {
	//
	// final String id = "10005";
	// // final String id = "99999";
	// final String name = "lee";
	// final String description = "abcdefg1234567890";
	//
	// final String queueName = "cmsapp.sac.request.sync.content.test99999";
	// // final String queueName = "cmsapp.sac.request.sync.content.test0001";
	//
	// UserCareerSearch userCareerSearch = new UserCareerSearch();
	// userCareerSearch.setId(id);
	// userCareerSearch.setName(name);
	// userCareerSearch.setDescription(description);
	//
	// MarshallingHelper marshaller = new JacksonMarshallingHelper();
	//
	// byte[] content = marshaller.marshal(userCareerSearch);
	//
	// MessageProperties messageProperties = new MessageProperties();
	//
	// messageProperties.setHeader(IntegrationConstants.MESSAGE_HEADER_NAME_SAC_GUID, "testguid");
	// messageProperties.setHeader(IntegrationConstants.MESSAGE_HEADER_NAME_SAC_SYSTEM_ID, "S01_0001");
	// messageProperties.setContentType(StoreplatformMediaType.MEDIA_TYPE_APP_JSON);
	//
	// Message message = new Message(content, messageProperties);
	//
	// byte[] retContent = (byte[]) this.template.convertSendAndReceive(queueName, message);
	//
	// if (id.equals("99999")) {
	//
	// } else {
	// UserCareerSearch retUserCareerSearch = new UserCareerSearch();
	// if (queueName.equals("cmsapp.sac.request.sync.content.test99999")) {
	// retUserCareerSearch = (UserCareerSearch) marshaller.unmarshal(retContent, UserCareerSearch.class);
	// } else if (queueName.equals("cmsapp.sac.request.sync.content.test0001")) {
	// retUserCareerSearch = (UserCareerSearch) SerializationUtils.deserialize(retContent);
	// }
	//
	// assertEquals(id, retUserCareerSearch.getId());
	// assertEquals(id, retUserCareerSearch.getId());
	// assertEquals(id, retUserCareerSearch.getId());
	// }
	//
	// }
	//
	// @Test
	// public void convertAndSend2() throws IOException {
	// final String id = "10002";
	// final String name = "lee";
	// final String description = "abcdefg1234567890";
	//
	// UserCareerSearch userCareerSearch = new UserCareerSearch();
	// userCareerSearch.setId(id);
	// userCareerSearch.setName(name);
	// userCareerSearch.setDescription(description);
	//
	// MarshallingHelper marshaller = new JacksonMarshallingHelper();
	//
	// byte[] content = marshaller.marshal(userCareerSearch);
	//
	// MessageProperties messageProperties = new MessageProperties();
	// messageProperties.setHeader(IntegrationConstants.MESSAGE_HEADER_NAME_SAC_GUID, "testguid");
	// messageProperties.setHeader(IntegrationConstants.MESSAGE_HEADER_NAME_SAC_SYSTEM_ID, "S01_0001");
	// messageProperties.setContentType(StoreplatformMediaType.MEDIA_TYPE_APP_JSON);
	//
	// Message message = new Message(content, messageProperties);
	//
	// byte[] retContent = (byte[]) this.template.convertSendAndReceive("tenant1.sac.request.sync.content.test0001",
	// message);
	//
	// boolean istrue = (Boolean) SerializationUtils.deserialize(retContent);
	//
	// assertTrue(istrue);
	// }

}
