package com.skplanet.storeplatform.sac.member.seller.dao;

import java.util.List;

import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListRequestVO;

/**
 * ListWithdrawalReasonDAO
 * 
 * <PRE>
 * DESC : ListWithdrawalReason 데이터 접근을 위한 인터페이스
 * </PRE>
 * 
 * @author 한서구
 * 
 */
public interface ListWithdrawalReasonDAO {

	List<?> selectNoticePageList(SellerSecedeResonListRequestVO requestVO) throws Exception;

}
