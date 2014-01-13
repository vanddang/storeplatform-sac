package com.skplanet.storeplatform.sac.member.seller.service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailAccountInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DetailInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.DuplicateByIdEmailRes;

public interface SellerSearchService {

	/** 판매자 회원 ID/Email 중복조회 */
	public DuplicateByIdEmailRes duplicateByIdEmail(DuplicateByIdEmailReq req);

	/** 판매자 회원 기본정보조회 */
	public DetailInformationRes detailInformation(DetailInformationReq req);

	/** 판매자 회원 정산정보조회 */
	public DetailAccountInformationRes detailAccountInformation(DetailAccountInformationReq req);

}
