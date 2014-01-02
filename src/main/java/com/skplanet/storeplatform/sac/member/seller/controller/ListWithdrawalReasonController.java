package com.skplanet.storeplatform.sac.member.seller.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListResponseVO;
import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonVO;
import com.skplanet.storeplatform.sac.member.seller.service.ListWithdrawalReasonService;

/**
 * ListWithdrawalReasonController
 * 
 * <PRE>
 * DESC : ListWithdrawalReason 페이지 출력 및 등록을 위한 컨트롤러
 * </PRE>
 * 
 * @author 한서구
 * 
 */
public class ListWithdrawalReasonController extends MultiActionController {

	/**
	 * DB 데이터 접근을 위한 Service
	 */
	@Resource(name = "ListWithdrawalReasonService")
	private ListWithdrawalReasonService noticeService;

	/**
	 * ListWithdrawalReason 목록 조회 후 JSP 페이지를 출력합니다.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seller/secedeResonList.do")
	public SellerSecedeResonListResponseVO secedeResonList(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("SellerSecedeResonListRequestVO") SellerSecedeResonListRequestVO requestVO)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		SellerSecedeResonListResponseVO responseVO = new SellerSecedeResonListResponseVO();

		List<SellerSecedeResonVO> sellerSecedeResonList = (List<SellerSecedeResonVO>) this.noticeService
				.selectNoticePageList(requestVO);

		responseVO.setResultCode("100");
		responseVO.setResultMessage("성공");
		responseVO.setSellerSecedeResonList(sellerSecedeResonList);

		return responseVO;
	}

}
