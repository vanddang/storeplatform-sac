/**
 * 
 */
package com.skplanet.storeplatform.sac.member.seller.sci.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 판매자 회원 조회 관련 기능 ServiceImpl.
 * 
 * Updated on : 2014. 5. 20. Updated by : 김다슬, 인크로스.
 */
@Service
public class SellerSearchSCIServiceImpl implements SellerSearchSCIService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSearchSCIServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI; // 회원 Component 판매자 기능 Interface.

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트

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
	@Override
	public DetailInformationSacRes detailInformationList(SacRequestHeader header, DetailInformationSacReq req) {

		SearchMbrSellerRequest schReq = new SearchMbrSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		List<KeySearch> sellerMbrSacList = null;
		if (req.getSellerMbrSacList() != null) {
			sellerMbrSacList = new ArrayList<KeySearch>();
			for (int i = 0; i < req.getSellerMbrSacList().size(); i++) {
				KeySearch keySearch = new KeySearch();

				if (StringUtils.isNotBlank(req.getSellerMbrSacList().get(i).getSellerKey())) {
					keySearch.setKeyString(req.getSellerMbrSacList().get(i).getSellerKey());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
					sellerMbrSacList.add(keySearch);
				} else if (StringUtils.isNotBlank(req.getSellerMbrSacList().get(i).getSellerId())) {
					keySearch.setKeyString(req.getSellerMbrSacList().get(i).getSellerId());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_ID);
					sellerMbrSacList.add(keySearch);
				} else if (StringUtils.isNotBlank(req.getSellerMbrSacList().get(i).getSellerBizNumber())) {
					keySearch.setKeyString(req.getSellerMbrSacList().get(i).getSellerBizNumber());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_BIZ_NO); // 사업자 등록번호.
					sellerMbrSacList.add(keySearch);
				}
			}

		}
		schReq.setKeySearchList(sellerMbrSacList);

		SearchMbrSellerResponse schRes = this.sellerSCI.searchMbrSeller(schReq);

		Iterator<String> it = schRes.getSellerMbrListMap().keySet().iterator();

		List<com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac> sellerMbrSacs = null;
		com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac sellerMbrSac = null;
		Map<String, List<com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac>> sellerMbrSacMap = new HashMap<String, List<com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac>>();

		while (it.hasNext()) {
			String key = it.next();
			sellerMbrSacs = new ArrayList<com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac>();
			List<SellerMbr> sellerMbrs = (List<SellerMbr>) schRes.getSellerMbrListMap().get(key);
			for (int i = 0; i < sellerMbrs.size(); i++) {
				sellerMbrSac = new com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac();
				sellerMbrSac.setSellerKey(sellerMbrs.get(i).getSellerKey());
				sellerMbrSac.setSellerId(sellerMbrs.get(i).getSellerID());
				sellerMbrSac.setSellerClass(sellerMbrs.get(i).getSellerClass());
				sellerMbrSac.setCharger(sellerMbrs.get(i).getCharger());
				sellerMbrSac.setSellerCompany(sellerMbrs.get(i).getSellerCompany());
				sellerMbrSac.setSellerNickName(sellerMbrs.get(i).getSellerNickName());
				sellerMbrSac.setSellerBizNumber(sellerMbrs.get(i).getSellerBizNumber());
				sellerMbrSac.setSellerName(sellerMbrs.get(i).getSellerName());
				sellerMbrSac.setRepPhone(sellerMbrs.get(i).getRepPhone());
				sellerMbrSac.setSellerEmail(sellerMbrs.get(i).getSellerEmail());
				sellerMbrSac.setSellerAddress(sellerMbrs.get(i).getSellerAddress());
				sellerMbrSac.setSellerDetailAddress(sellerMbrs.get(i).getSellerDetailAddress());
				sellerMbrSac.setBizRegNumber(sellerMbrs.get(i).getBizRegNumber());
				sellerMbrSacs.add(sellerMbrSac);
			}
			sellerMbrSacMap.put(key, sellerMbrSacs);
		}

		DetailInformationSacRes response = new DetailInformationSacRes();

		response.setSellerMbrListMap(sellerMbrSacMap);
		return response;
	}

}
