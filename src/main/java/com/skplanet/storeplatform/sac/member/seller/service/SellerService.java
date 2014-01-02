package com.skplanet.storeplatform.sac.member.seller.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListResponseVO;

/**
 * 판매자 회원관련 인터페이스
 * 
 * Updated on : 2013-12-23 Updated by : 반범진, 지티소프트.
 */
public interface SellerService {

	/**
	 * 판매자회원 탈퇴사유 리스트 조회.
	 * 
	 * @param sellerSecedeResonListRequestVO
	 * 
	 * @return 판매자 탈퇴사유 리스트
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	SellerSecedeResonListResponseVO getSellerSecedeResonList(
			SellerSecedeResonListRequestVO sellerSecedeResonListRequestVO)
			throws JsonGenerationException, JsonMappingException, IOException;

}
