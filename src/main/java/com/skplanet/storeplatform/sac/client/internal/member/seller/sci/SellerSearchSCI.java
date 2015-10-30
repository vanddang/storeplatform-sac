package com.skplanet.storeplatform.sac.client.internal.member.seller.sci;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes;

/**
 * 판매자 회원 조회관련 기능 항목들
 * 
 * Updated on : 2014. 2. 12. Updated by : 한서구, 부르칸.
 */
@SCI
@RequestMapping(value = "/member/seller/sci")
public interface SellerSearchSCI {

	/**
	 * <pre>
	 * 판매자 회원 기본정보조회.
	 * </pre>
	 * 
	 * @param req
	 *            DetailInformationSacReq
	 * @return DetailInformationSacRes
	 */
	@ResponseBody
	@RequestMapping(value = "/detailInformation", method = RequestMethod.POST)
	public DetailInformationSacRes detailInformation(@Validated DetailInformationSacReq req);

	/**
	 * <pre>
	 * 상품상세의 판매자 정보 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            detailInformationListForProductSacReq
	 * @return detailInformationListForProductSacRes
	 */
	@ResponseBody
	@RequestMapping(value = "/detailInformationListForProduct", method = RequestMethod.POST)
	public DetailInformationListForProductSacRes detailInformationListForProduct(
			@Validated DetailInformationListForProductSacReq req);

}
