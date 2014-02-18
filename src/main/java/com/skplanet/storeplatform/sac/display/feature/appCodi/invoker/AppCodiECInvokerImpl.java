package com.skplanet.storeplatform.sac.display.feature.appCodi.invoker;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.skplanet.storeplatform.external.client.isf.vo.ISFReq;
import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiSacReq;

/**
 * App Codi EC Invoker Interface 구현체
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
@Component
public class AppCodiECInvokerImpl implements AppCodiECInvoker {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Value("#{propertiesForSac['component.external.baseUrl']}")
	private String domainName;

	@Override
	public ISFRes invoke(AppCodiSacReq requestVO) throws StorePlatformException {

		ISFRes response = new ISFRes();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ISFReq> requestEntity = new HttpEntity<ISFReq>(this.makeRequest(requestVO), headers);
		ResponseEntity<ISFRes> res;
		try {
			res = this.restTemplate.exchange(this.domainName + "/isf/appCodi/v1", HttpMethod.POST, requestEntity,
					ISFRes.class);

			if (res.getStatusCode().equals(HttpStatus.OK)) {
				response = res.getBody();
			} else {
				throw new StorePlatformException("SAC_DSP_0008");
			}

			if (this.log.isDebugEnabled()) {
				JAXBContext jc = JAXBContext.newInstance(ISFRes.class);
				Marshaller m1 = jc.createMarshaller();
				m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m1.marshal(response, System.out);
			}
		} catch (StorePlatformException se) {
			throw se;
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0008", e);
		}

		return response;
	}

	private ISFReq makeRequest(AppCodiSacReq requestVO) {

		ISFReq request = new ISFReq();

		if ("long".equals(requestVO.getFilteredBy()))
			request.setId("SVC_CODI_0001");
		else
			request.setId("SVC_MAIN_0003");

		request.setMbn(requestVO.getUserKey());
		request.setMdn(requestVO.getDeviceId());
		request.setType(requestVO.getFilteredBy());

		this.log.debug(request.toString());

		return request;
	}

}
