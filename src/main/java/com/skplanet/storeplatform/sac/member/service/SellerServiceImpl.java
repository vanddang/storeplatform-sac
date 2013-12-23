package com.skplanet.storeplatform.sac.member.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListResponseVO;
import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonVO;

/**
 * Seller Service 인터페이스 구현체
 * 
 * Updated on : 2013-12-23 Updated by : 반범진, 지티소프트.
 */
@Component
public class SellerServiceImpl implements SellerService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public SellerSecedeResonListResponseVO getSellerSecedeResonList(
			SellerSecedeResonListRequestVO requestVO) {

		SellerSecedeResonListResponseVO responseVO = new SellerSecedeResonListResponseVO();

		this.log.info(":::::::::::: langCd : " + requestVO.getLangCd());
		requestVO.setLangCd("en");

		List<SellerSecedeResonVO> sellerSecedeResonList = (List<SellerSecedeResonVO>) this.commonDAO
				.queryForList("seller.getSellerSecedeResonList", requestVO);

		responseVO.setResultCode("100");
		responseVO.setResultMessage("성공");
		responseVO.setSellerSecedeResonList(sellerSecedeResonList);

		return responseVO;

	}

}
