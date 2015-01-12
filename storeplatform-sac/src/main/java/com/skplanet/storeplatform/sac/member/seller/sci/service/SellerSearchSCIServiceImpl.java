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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes.SellerMbrInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes.SellerMbrInfoSac.SellerMbrAppSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
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

		SearchMbrSellerRequest schReq = null;
		CommonRequest commonRequest = this.commonComponent.getSCCommonRequest(header);
		DetailInformationSacRes response = new DetailInformationSacRes();

		if (req.getSellerMbrSacList() != null) {
			List<KeySearch> sellerKeys = new ArrayList<KeySearch>();
			List<KeySearch> sellerIds = new ArrayList<KeySearch>();
			List<KeySearch> sellerBizNos = new ArrayList<KeySearch>();

			// 요청 Key별 KeyList Setting.
			for (SellerMbrSac sellerMbrSac : req.getSellerMbrSacList()) {
				if (StringUtils.isNotBlank(sellerMbrSac.getSellerKey())) {
					KeySearch keySearch = new KeySearch();
					keySearch.setKeyString(sellerMbrSac.getSellerKey());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
					sellerKeys.add(keySearch);
				} else if (StringUtils.isNotBlank(sellerMbrSac.getSellerId())) {
					KeySearch keySearch = new KeySearch();
					keySearch.setKeyString(sellerMbrSac.getSellerId());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_ID);
					sellerIds.add(keySearch);
				} else if (StringUtils.isNotBlank(sellerMbrSac.getSellerBizNumber())) {
					KeySearch keySearch = new KeySearch();
					keySearch.setKeyString(sellerMbrSac.getSellerBizNumber());
					keySearch.setKeyType(MemberConstants.KEY_TYPE_SELLERMBR_BIZ_NO);
					sellerBizNos.add(keySearch);
				}

			} // End For Loop.

			// KeyList별 SC 호출해서 ScMap에 PutAll.
			Map<String, List<SellerMbr>> sellerMbrMapSc = new HashMap<String, List<SellerMbr>>();
			// SC Temp Map
			Map<String, List<SellerMbr>> resultMap = null;
			int keyCnt = sellerKeys.size() + sellerIds.size() + sellerBizNos.size();
			int errCnt = 0;
			if (!sellerKeys.isEmpty()) {
				schReq = new SearchMbrSellerRequest();
				schReq.setCommonRequest(commonRequest);
				schReq.setKeySearchList(sellerKeys);
				resultMap = this.isException(schReq, keyCnt, errCnt);
				if (resultMap != null) {
					sellerMbrMapSc.putAll(resultMap);
				} else {
					errCnt++;
				}
			}
			if (!sellerIds.isEmpty()) {
				schReq = new SearchMbrSellerRequest();
				schReq.setCommonRequest(commonRequest);
				schReq.setKeySearchList(sellerIds);
				resultMap = this.isException(schReq, keyCnt, errCnt);
				if (resultMap != null) {
					sellerMbrMapSc.putAll(resultMap);
				} else {
					errCnt++;
				}
			}
			if (!sellerBizNos.isEmpty()) {
				schReq = new SearchMbrSellerRequest();
				schReq.setCommonRequest(commonRequest);
				schReq.setKeySearchList(sellerBizNos);
				resultMap = this.isException(schReq, keyCnt, errCnt);
				if (resultMap != null) {
					sellerMbrMapSc.putAll(resultMap);
				} else {
					errCnt++;
				}
			}
			// List<SellerMbr> list = sellerMbrMapSc
			LOGGER.info("요청한 Key값들 중 일부 또는 전체에 대한 결과 존재.");
			// SC 호출 후 PutAll한 Map을 SacMap에 Setting.
			Iterator<String> it = sellerMbrMapSc.keySet().iterator();

			List<SellerMbrSac> sellerMbrSacs = null;
			SellerMbrSac sellerMbrSac = null;
			Map<String, List<SellerMbrSac>> sellerMbrSacMap = new HashMap<String, List<SellerMbrSac>>();

			while (it.hasNext()) {
				String key = it.next(); // 요청된 키값
				sellerMbrSacs = new ArrayList<SellerMbrSac>();
				for (SellerMbr sellerMbr : sellerMbrMapSc.get(key)) {
					sellerMbrSac = new SellerMbrSac();
					sellerMbrSac.setSellerKey(sellerMbr.getSellerKey());
					sellerMbrSac.setSellerId(sellerMbr.getSellerID());
					sellerMbrSac.setSellerClass(sellerMbr.getSellerClass());
					sellerMbrSac.setCharger(sellerMbr.getCharger());
					sellerMbrSac.setSellerCompany(sellerMbr.getSellerCompany());
					sellerMbrSac.setSellerNickName(sellerMbr.getSellerNickName());
					sellerMbrSac.setSellerBizNumber(sellerMbr.getSellerBizNumber());
					sellerMbrSac.setSellerName(sellerMbr.getSellerName());
					sellerMbrSac.setRepPhone(sellerMbr.getRepPhone());
					sellerMbrSac.setSellerEmail(sellerMbr.getSellerEmail());
					sellerMbrSac.setSellerAddress(sellerMbr.getSellerAddress());
					sellerMbrSac.setSellerDetailAddress(sellerMbr.getSellerDetailAddress());
					sellerMbrSac.setBizRegNumber(sellerMbr.getBizRegNumber());
					sellerMbrSacs.add(sellerMbrSac);
				}
				sellerMbrSacMap.put(key, sellerMbrSacs);
			}
			response.setSellerMbrListMap(sellerMbrSacMap);
		} // End if (req.getSellerMbrSacList() != null).

		return response;
	}

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
	@Override
	public DetailInformationListForProductSacRes detailInformationListForProduct(SacRequestHeader header,
			DetailInformationListForProductSacReq req) {

		SearchMbrSellerRequest schReq = new SearchMbrSellerRequest();
		schReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		DetailInformationListForProductSacRes response = new DetailInformationListForProductSacRes();

		List<KeySearch> sellerKeyList = null;
		if (req.getSellerKeyList() != null) {
			sellerKeyList = new ArrayList<KeySearch>();
			for (int i = 0; i < req.getSellerKeyList().size(); i++) {
				KeySearch keySearch = new KeySearch();
				keySearch.setKeyString(req.getSellerKeyList().get(i));
				keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
				sellerKeyList.add(keySearch);
			}
			schReq.setKeySearchList(sellerKeyList);

			// SC 판매자 정보 조회 호출
			SearchMbrSellerResponse schRes = this.sellerSCI.searchMbrSeller(schReq);

			Iterator<String> it = schRes.getSellerMbrListMap().keySet().iterator();

			// SAC [RESPONSE] - Object
			SellerMbrInfoSac sellerInfo = null;
			Map<String, SellerMbrInfoSac> sellerInfoSacMap = new HashMap<String, SellerMbrInfoSac>();
			while (it.hasNext()) {
				// 판매자 회원 내부 키값
				String sellerKey = it.next();
				// SC [RESPONSE] - Object
				List<SellerMbr> sellerMbrs = (List<SellerMbr>) schRes.getSellerMbrListMap().get(sellerKey);

				// 상단
				String nameTop = null;
				// 하단
				String nameLower = null;
				String compNmLower = null;
				String emailLower = null;
				String bizNoLower = null;
				String addrLower = null;
				String phoneLower = null;

				// Top + Lower
				// 내국인
				if (StringUtils.equals(MemberConstants.USE_Y, sellerMbrs.get(0).getIsDomestic())) {
					// 개인
					if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON, sellerMbrs
							.get(0).getSellerClass())) {

						// first:sellerNickName, second:charger, default:""
						nameTop = StringUtils.defaultString(StringUtils.isNotBlank(sellerMbrs.get(0)
								.getSellerNickName()) ? sellerMbrs.get(0).getSellerNickName() : sellerMbrs.get(0)
								.getCharger(), "");

						// first:sellerName, second:charger, default:""
						nameLower = StringUtils.defaultString(
								StringUtils.isNotBlank(sellerMbrs.get(0).getSellerName()) ? sellerMbrs.get(0)
										.getSellerName() : sellerMbrs.get(0).getCharger(), "");
					}
					// 개인 사업자, 법인 사업자
					if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS, sellerMbrs
							.get(0).getSellerClass())
							|| StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS,
									sellerMbrs.get(0).getSellerClass())) {

						// first:sellerNickName, second:sellerCompany, default:""
						nameTop = compNmLower = StringUtils.defaultString(StringUtils.isNotBlank(sellerMbrs.get(0)
								.getSellerNickName()) ? sellerMbrs.get(0).getSellerNickName() : sellerMbrs.get(0)
								.getSellerCompany(), "");

						nameLower = StringUtils.defaultString(sellerMbrs.get(0).getCeoName(), "");
						bizNoLower = StringUtils.defaultString(sellerMbrs.get(0).getBizRegNumber(), "");
						// first:repPhone, second:cordedTelephone, default:""
						phoneLower = StringUtils.defaultString(
								StringUtils.isNotBlank(sellerMbrs.get(0).getRepPhone()) ? sellerMbrs.get(0)
										.getRepPhone() : sellerMbrs.get(0).getCordedTelephone(), "");
						addrLower = StringUtils.isNotBlank(sellerMbrs.get(0).getSellerAddress()) ? sellerMbrs.get(0)
								.getSellerAddress() : "";
						addrLower += StringUtils.isNotBlank(sellerMbrs.get(0).getSellerDetailAddress()) ? " "
								+ sellerMbrs.get(0).getSellerDetailAddress() : "";
					}

				} else { // 외국인
					// first:sellerNickName, second:sellerCompany, default:""
					nameTop = StringUtils.defaultString(
							StringUtils.isNotBlank(sellerMbrs.get(0).getSellerNickName()) ? sellerMbrs.get(0)
									.getSellerNickName() : sellerMbrs.get(0).getSellerCompany(), "");

					// 개인 ( 판매자명, 이메일 )
					if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_PERSON, sellerMbrs
							.get(0).getSellerClass())) {
						// first:sellerName, second:sellerCompany, default:""
						nameLower = StringUtils.defaultString(
								StringUtils.isNotBlank(sellerMbrs.get(0).getSellerName()) ? sellerMbrs.get(0)
										.getSellerName() : sellerMbrs.get(0).getSellerCompany(), "");
					}
					// 개인 사업자, 법인 사업자 ( 상호명, 이메일 )
					if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS, sellerMbrs
							.get(0).getSellerClass())
							|| StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS,
									sellerMbrs.get(0).getSellerClass())) {

						// first:sellerNickName, second:sellerCompany, third:sellerName, default:""
						compNmLower = StringUtils.isNotBlank(sellerMbrs.get(0).getSellerNickName()) ? sellerMbrs.get(0)
								.getSellerNickName() : StringUtils.defaultString(StringUtils.isNotBlank(sellerMbrs.get(
								0).getSellerCompany()) ? sellerMbrs.get(0).getSellerCompany() : sellerMbrs.get(0)
								.getSellerName(), "");
					}
				}

				// first:repEmail, second:customerEmail, third:sellerEmail, default:""
				if (StringUtils.isNotBlank(sellerMbrs.get(0).getRepEmail())) {
					emailLower = sellerMbrs.get(0).getRepEmail();
				} else if (StringUtils.isNotBlank(sellerMbrs.get(0).getCustomerEmail())) {
					emailLower = sellerMbrs.get(0).getCustomerEmail();
				} else {
					emailLower = StringUtils.defaultString(sellerMbrs.get(0).getSellerEmail(), "");
				}

				SellerMbrAppSac sellerMbrSac = new SellerMbrAppSac();
				List<SellerMbrAppSac> resList = new ArrayList<SellerMbrAppSac>();

				// 상단 셋팅
				sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
				sellerMbrSac.setSellerName(nameTop);
				resList.add(0, sellerMbrSac);

				sellerMbrSac = new SellerMbrAppSac();
				// 하단 셋팅
				sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);
				resList.add(1, sellerMbrSac);
				sellerMbrSac.setSellerName(nameLower);
				sellerMbrSac.setSellerCompany(compNmLower);
				sellerMbrSac.setSellerEmail(emailLower);
				sellerMbrSac.setBizRegNumber(bizNoLower);
				sellerMbrSac.setSellerAddress(addrLower);
				sellerMbrSac.setSellerPhone(phoneLower);

				sellerInfo = new SellerMbrInfoSac();
				sellerInfo.setSellerId(sellerMbrs.get(0).getSellerID());
				sellerInfo.setIsDomestic(sellerMbrs.get(0).getIsDomestic());
				sellerInfo.setSellerClass(sellerMbrs.get(0).getSellerClass());
				sellerInfo.setSellerMbrList(resList);
				sellerInfoSacMap.put(sellerKey, sellerInfo);
			}
			response.setSellerMbrMap(sellerInfoSacMap);

		}
		return response;
	}

	/**
	 * <pre>
	 * SC Exception 발생 여부 확인.
	 * </pre>
	 * 
	 * @param schReq
	 * @return Map<String, List<SellerMbrSac>>
	 */
	public Map<String, List<SellerMbr>> isException(SearchMbrSellerRequest schReq, int keyCnt, int errCnt) {
		Map<String, List<SellerMbr>> resultMap = null;
		try {
			resultMap = this.sellerSCI.searchMbrSeller(schReq).getSellerMbrListMap();
		} catch (StorePlatformException e) {
			if (!e.getErrorInfo().getCode().equals("SC_MEM_9982")) { // 검색결과 없음이 아닌경우 Exception.
				throw e;
			}
			if (++errCnt == keyCnt) {
				throw e;
			}
		}
		return resultMap;
	}
}
