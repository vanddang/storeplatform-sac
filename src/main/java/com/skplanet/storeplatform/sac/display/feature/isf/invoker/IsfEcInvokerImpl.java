package com.skplanet.storeplatform.sac.display.feature.isf.invoker;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.vo.IsfEcReq;

/**
 * ISF EC Invoker Interface 구현체
 * 
 * Updated on : 2014. 02. 21. Updated by : 윤주영, SK 플래닛.
 */
@Component
public class IsfEcInvokerImpl implements IsfEcInvoker {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Value("#{propertiesForSac['component.external.baseUrl']}")
	private String domainName;

	@Override
	public ISFRes invoke(IsfEcReq requestVO) throws StorePlatformException {
		if (this.log.isDebugEnabled()) {
			this.log.debug(this.getClass().getName() + ".invoke start...... !!");
		}
		ISFReq isfReq = new ISFReq();
		ISFRes isfRes = new ISFRes();

		BeanUtils.copyProperties(requestVO, isfReq);
		if (this.log.isDebugEnabled()) {
			this.log.debug("isfReq {}", isfReq);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ISFReq> requestEntity = new HttpEntity<ISFReq>(isfReq, headers);
		ResponseEntity<ISFRes> res;
		try {
			res = this.restTemplate.exchange(this.domainName + "/isf/get/v1", HttpMethod.POST, requestEntity,
					ISFRes.class);

			if (res.getStatusCode().equals(HttpStatus.OK)) {
				isfRes = res.getBody();
			} else {
				throw new StorePlatformException("SAC_DSP_0010");
			}

			if (this.log.isDebugEnabled()) {
				JAXBContext jc = JAXBContext.newInstance(ISFRes.class);
				Marshaller m1 = jc.createMarshaller();
				m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m1.marshal(isfRes, System.out);
			}
		} catch (StorePlatformException se) {
			throw se;
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0010", e);
		}
		if (this.log.isDebugEnabled()) {
			this.log.debug(this.getClass().getName() + ".invoke end...... !!");
		}
		return isfRes;
	}

}
