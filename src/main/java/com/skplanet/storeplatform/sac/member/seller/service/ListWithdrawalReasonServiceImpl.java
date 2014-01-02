package com.skplanet.storeplatform.sac.member.seller.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListRequestVO;
import com.skplanet.storeplatform.sac.member.seller.dao.ListWithdrawalReasonDAO;

/**
 * NoticeServiceImpl
 * 
 * <PRE>
 * DESC : ListWithdrawalReason 데이터 접근을 위한 서비스 구현 클래스
 * </PRE>
 * 
 * @author 한서구
 * 
 */
@Service("ListWithdrawalReasonService")
public class ListWithdrawalReasonServiceImpl implements ListWithdrawalReasonService {

	@Resource(name = "ListWithdrawalReasonServiceDAO")
	private ListWithdrawalReasonDAO ListWithdrawalReasonServiceDAO;

	@Override
	public List<?> selectNoticePageList(SellerSecedeResonListRequestVO requestVO) throws Exception {
		return this.ListWithdrawalReasonServiceDAO.selectNoticePageList(requestVO);
	}

}
