package com.skplanet.storeplatform.sac.runtime.extend;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.integration.MessageHeaders;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.integration.IntegrationConstants;
import com.skplanet.storeplatform.framework.integration.serviceactivator.InterfaceIdSearcher;

/**
 * 인터페이스ID를 조회하는 클래스
 * 
 * Updated on : 2013-09-01 Updated by : 최현식, 에이엔비.
 */
@Component
public class SacInterfaceIdSearcher implements InterfaceIdSearcher {

	@Override
	public String search(MessageHeaders headers) {
		// TODO : 이전 설계 방식은 Key를 URL로 정했기 때문에 인터페이스 ID를 가져올 로직이 필요했음.
		// 현재는 Header값에 InterfaceId를 가져오기 때문에 해당 클래스는 의미 없어 보이나
		// 기존 호환성을 위해서 남겨둠.
		// SWA 문의 : 해당 인터페스 구현체는 F/W 에서 @Autowired 사용되고 있음.

		String interfaceId = (String) headers.get(IntegrationConstants.MESSAGE_HEADER_NAME_SAC_INTERFACE_ID);

		// TODO : 인터페이스 ID가 없으면 Framework 에서 에러로 처리하기 때문에.
		// 인터페이스 ID 가없으면 Dummy 값을 셋팅한다. 해당 부분은 ACL 처리가 완료되면 수정한다.
		return ObjectUtils.defaultIfNull(interfaceId, "I00000000");
	}
}
