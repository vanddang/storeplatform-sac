/**
 * 
 */
package com.skplanet.storeplatform.sac.member.seller.sci.service;

import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 판매자 회원 조회 관련 기능 Service.
 * 
 * Updated on : 2014. 5. 20. Updated by : 김다슬, 인크로스.
 */
public interface SellerSearchSCIService {

	/**
	 * <pre>
	 * 판매자 회원 정보 조회 - 내부메서드.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            DetailInformationSacReq
	 * @return DetailInformationSacRes
	 */
	public DetailInformationSacRes detailInformationList(SacRequestHeader header, DetailInformationSacReq req);

	/**
	 * <pre>
	 * 상품상세의 판매자 정보 목록 조회 - 내부메서드.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            detailInformationListForProductSacReq
	 * @return detailInformationListForProductSacRes
	 */
	public DetailInformationListForProductSacRes detailInformationListForProduct(SacRequestHeader header,
			DetailInformationListForProductSacReq req);
}
