package com.skplanet.storeplatform.sac.member.seller.service;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SellerSecedeResonListRequestVO;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SellerSecedeResonListResponseVO;
import com.skplanet.storeplatform.sac.client.member.vo.seller.SellerSecedeResonVO;

/**
 * Seller Service 인터페이스 구현체
 *
 * Updated on : 2013-12-23 Updated by : 반범진, 지티소프트.
 */
@Component
public class SellerServiceImpl implements SellerService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UAPSSCI uapsSCI;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public SellerSecedeResonListResponseVO getSellerSecedeResonList(
			SellerSecedeResonListRequestVO requestVO)
			throws JsonGenerationException, JsonMappingException, IOException {

		SellerSecedeResonListResponseVO responseVO = new SellerSecedeResonListResponseVO();

		this.log.info(":::::::::::: langCd : {}", requestVO.getLangCd());

		List<SellerSecedeResonVO> sellerSecedeResonList = (List<SellerSecedeResonVO>) this.commonDAO
				.queryForList("seller.getSellerSecedeResonList", requestVO);

		responseVO.setResultCode("100");
		responseVO.setResultMessage("성공");
		responseVO.setSellerSecedeResonList(sellerSecedeResonList);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper
				.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		String json = objectMapper.writeValueAsString(responseVO);

		this.log.info("json : {}", json);

		return responseVO;

	}

}
