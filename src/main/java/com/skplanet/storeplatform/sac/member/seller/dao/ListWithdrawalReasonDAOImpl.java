package com.skplanet.storeplatform.sac.member.seller.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListRequestVO;

/**
 * NoticeDAOImpl
 * 
 * <PRE>
 * DESC : ListWithdrawalReason 데이터 접근 로직 구현 클래스
 * - ListWithdrawalReason 페이지의 DB CRUD 작업을 담당하는 DAO 구현 클래스로 BaseDAO 를 상속받아 사용한다.
 * </PRE>
 * 
 * @author 한서구
 * 
 */
@Service("ListWithdrawalReasonDAO")
public class ListWithdrawalReasonDAOImpl implements ListWithdrawalReasonDAO {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public List<?> selectNoticePageList(SellerSecedeResonListRequestVO requestVO) throws Exception {
		return this.commonDAO.queryForList("seller.getSellerSecedeResonList", requestVO);
	}

}
