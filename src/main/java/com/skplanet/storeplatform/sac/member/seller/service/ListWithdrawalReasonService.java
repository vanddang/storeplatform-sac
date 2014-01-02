package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.List;

import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListRequestVO;

/**
 * NoticeService
 * 
 * <PRE>
 * DESC : ListWithdrawalReason 데이터 접근을 위한 서비스 인터페이스
 * </PRE>
 * 
 * @author 한서구
 * 
 */
public interface ListWithdrawalReasonService {

	List<?> selectNoticePageList(SellerSecedeResonListRequestVO requestVO) throws Exception;

}
