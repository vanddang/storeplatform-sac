package com.skplanet.storeplatform.sac.display.feature.isf.invoker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.skplanet.storeplatform.external.client.isf.sci.ISFSCI;
import com.skplanet.storeplatform.external.client.isf.vo.ISFReq;
import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2EcReq;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2EcRes;
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
	private ISFSCI isfSCI;

	@Override
	public ISFRes invoke(IsfEcReq requestVO) {

		ISFReq isfReq = new ISFReq();
		BeanUtils.copyProperties(requestVO, isfReq);
		return this.isfSCI.get(isfReq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.feature.isf.invoker.IsfEcInvoker#invokeV2(com.skplanet.storeplatform.external
	 * .client.isf.vo.IsfV2EcReq)
	 */
	@Override
	public IsfV2EcRes invokeV2(IsfV2EcReq ecReq) {
		return this.isfSCI.getV2(ecReq);
	}
}
