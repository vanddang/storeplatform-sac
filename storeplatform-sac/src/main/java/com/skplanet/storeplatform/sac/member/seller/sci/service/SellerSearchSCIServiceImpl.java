/**
 * 
 */
package com.skplanet.storeplatform.sac.member.seller.sci.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchProviderRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchProviderResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes.SellerMbrInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes.SellerMbrInfoSac.ProviderMbrAppSac;
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
 * Updated on : 2015. 12. 10. Updated by : 최진호, 보고지티.
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
			List<SellerMbrSac> sellerMbrSacs = null;
			SellerMbrSac sellerMbrSac = null;
			Map<String, List<SellerMbrSac>> sellerMbrSacMap = new HashMap<String, List<SellerMbrSac>>();

			for (Entry<String, List<SellerMbr>> entry : sellerMbrMapSc.entrySet()) {
				sellerMbrSacs = new ArrayList<SellerMbrSac>();
				for (SellerMbr sellerMbr : entry.getValue()) {
					sellerMbrSac = new SellerMbrSac();
					// 제공자 정보 조회 여부를 판단하기 위한 req 매핑
					for (SellerMbrSac sellerMbrSacReq : req.getSellerMbrSacList()) {
						// req의 sellerKey, sellerId, sellerBizNumber로 확인
						if (StringUtil.contains(sellerMbr.getSellerKey(), sellerMbrSacReq.getSellerKey())
								|| StringUtil.contains(sellerMbr.getSellerID(), sellerMbrSacReq.getSellerId())
								|| StringUtil.contains(sellerMbr.getSellerBizNumber(),
										sellerMbrSacReq.getSellerBizNumber())) {
							// 매핑된 req의 categoryCd 값이 있다면
							if (StringUtil.isNotBlank(sellerMbrSacReq.getCategoryCd())) {

								// SC 제공자 정보 조회
								SearchProviderResponse schProvRes = new SearchProviderResponse();

								SearchProviderRequest schProvReq = new SearchProviderRequest();
								schProvReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
								schProvReq.setSellerKey(sellerMbr.getSellerKey());
								schProvReq.setCategoryCd(sellerMbrSacReq.getCategoryCd());

								schProvRes = this.sellerSCI.searchProviderInfo(schProvReq);

								// 제공자 정보가 있으면 기존 판매자 정보를 제공자 정보로, 제공자 정보를 판매자 정보로 바꾼다.
								if (schProvRes != null && StringUtil.isNotBlank(schProvRes.getSellerKey())) {
									// 판매 제공자 유무 값 셋팅
									sellerMbrSac.setSellerClass("US010103");
									sellerMbrSac.setSellerCompany(schProvRes.getSellerCompany());
									sellerMbrSac.setSellerName(schProvRes.getSellerName());
									sellerMbrSac.setRepPhone(schProvRes.getSellerPhone());
									sellerMbrSac.setSellerEmail(schProvRes.getSellerEmail());
									sellerMbrSac.setSellerAddress(schProvRes.getSellerAddress());
									sellerMbrSac.setBizRegNumber(schProvRes.getBizRegNumber());
									sellerMbrSac.setProviderYn("Y");
									sellerMbrSac.setProviderKey(sellerMbr.getSellerKey());
									sellerMbrSac.setProviderId(sellerMbr.getSellerID());
									sellerMbrSac.setProviderClass(sellerMbr.getSellerClass());
									sellerMbrSac.setProviderCharger(StringUtils.replace(sellerMbr.getCharger(), "|",
											" "));
									sellerMbrSac.setProviderCompany(sellerMbr.getSellerCompany());
									sellerMbrSac.setProviderNickName(sellerMbr.getSellerNickName());
									sellerMbrSac.setProviderBizRegNumber(sellerMbr.getBizRegNumber());
									sellerMbrSac.setProviderName(StringUtils.replace(sellerMbr.getSellerName(), "|",
											" "));
									sellerMbrSac.setProviderRepPhone(sellerMbr.getRepPhone());
									sellerMbrSac.setProviderEmail(sellerMbr.getSellerEmail());
									sellerMbrSac.setProviderAddress(sellerMbr.getSellerAddress());
									sellerMbrSac.setProviderDetailAddress(sellerMbr.getSellerDetailAddress());
									sellerMbrSac.setProviderBizNumber(sellerMbr.getBizRegNumber());
								} else {
									// 매핑된 req의 categoryCd값이 있지만 조회된 결과가 없으므로 기존 판매자 셋팅
									sellerMbrSac.setSellerKey(sellerMbr.getSellerKey());
									sellerMbrSac.setSellerId(sellerMbr.getSellerID());
									sellerMbrSac.setSellerClass(sellerMbr.getSellerClass());
									sellerMbrSac.setCharger(StringUtils.replace(sellerMbr.getCharger(), "|", " "));
									sellerMbrSac.setSellerCompany(sellerMbr.getSellerCompany());
									sellerMbrSac.setSellerNickName(sellerMbr.getSellerNickName());
									sellerMbrSac.setSellerBizNumber(sellerMbr.getSellerBizNumber());
									sellerMbrSac
											.setSellerName(StringUtils.replace(sellerMbr.getSellerName(), "|", " "));
									sellerMbrSac.setRepPhone(sellerMbr.getRepPhone());
									sellerMbrSac.setSellerEmail(sellerMbr.getSellerEmail());
									sellerMbrSac.setSellerAddress(sellerMbr.getSellerAddress());
									sellerMbrSac.setSellerDetailAddress(sellerMbr.getSellerDetailAddress());
									sellerMbrSac.setBizRegNumber(sellerMbr.getBizRegNumber());
									sellerMbrSac.setProviderYn("N");
								}
							} else {
								// 매핑된 req의 categoryCd값이 없으므로 기존 판매자 셋팅
								sellerMbrSac.setSellerKey(sellerMbr.getSellerKey());
								sellerMbrSac.setSellerId(sellerMbr.getSellerID());
								sellerMbrSac.setSellerClass(sellerMbr.getSellerClass());
								sellerMbrSac.setCharger(StringUtils.replace(sellerMbr.getCharger(), "|", " "));
								sellerMbrSac.setSellerCompany(sellerMbr.getSellerCompany());
								sellerMbrSac.setSellerNickName(sellerMbr.getSellerNickName());
								sellerMbrSac.setSellerBizNumber(sellerMbr.getSellerBizNumber());
								sellerMbrSac.setSellerName(StringUtils.replace(sellerMbr.getSellerName(), "|", " "));
								sellerMbrSac.setRepPhone(sellerMbr.getRepPhone());
								sellerMbrSac.setSellerEmail(sellerMbr.getSellerEmail());
								sellerMbrSac.setSellerAddress(sellerMbr.getSellerAddress());
								sellerMbrSac.setSellerDetailAddress(sellerMbr.getSellerDetailAddress());
								sellerMbrSac.setBizRegNumber(sellerMbr.getBizRegNumber());
								sellerMbrSac.setProviderYn("N");
							}
							break;
						}
					}
					sellerMbrSacs.add(sellerMbrSac);
				}
				sellerMbrSacMap.put(entry.getKey(), sellerMbrSacs);
			}

			// Iterator<String> it = sellerMbrMapSc.keySet().iterator();
			// while (it.hasNext()) {
			// String key = it.next(); // 요청된 키값
			// sellerMbrSacs = new ArrayList<SellerMbrSac>();
			// for (SellerMbr sellerMbr : sellerMbrMapSc.get(key)) {
			// sellerMbrSac = new SellerMbrSac();
			// sellerMbrSac.setSellerKey(sellerMbr.getSellerKey());
			// sellerMbrSac.setSellerId(sellerMbr.getSellerID());
			// sellerMbrSac.setSellerClass(sellerMbr.getSellerClass());
			// sellerMbrSac.setCharger(sellerMbr.getCharger());
			// sellerMbrSac.setSellerCompany(sellerMbr.getSellerCompany());
			// sellerMbrSac.setSellerNickName(sellerMbr.getSellerNickName());
			// sellerMbrSac.setSellerBizNumber(sellerMbr.getSellerBizNumber());
			// sellerMbrSac.setSellerName(sellerMbr.getSellerName());
			// sellerMbrSac.setRepPhone(sellerMbr.getRepPhone());
			// sellerMbrSac.setSellerEmail(sellerMbr.getSellerEmail());
			// sellerMbrSac.setSellerAddress(sellerMbr.getSellerAddress());
			// sellerMbrSac.setSellerDetailAddress(sellerMbr.getSellerDetailAddress());
			// sellerMbrSac.setBizRegNumber(sellerMbr.getBizRegNumber());
			// sellerMbrSacs.add(sellerMbrSac);
			// }
			// sellerMbrSacMap.put(key, sellerMbrSacs);
			// }
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
		if (req.getSellerMbrSacList() != null) {
			sellerKeyList = new ArrayList<KeySearch>();
			for (int i = 0; i < req.getSellerMbrSacList().size(); i++) {
				KeySearch keySearch = new KeySearch();
				keySearch.setKeyString(req.getSellerMbrSacList().get(i).getSellerKey());
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
				String bizNumber = null;

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
						bizNumber = StringUtils.defaultString(StringUtils.isNotBlank(sellerMbrs.get(0)
								.getSellerBizNumber()) ? sellerMbrs.get(0).getSellerBizNumber() : "");
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
								StringUtils.isNotBlank(sellerMbrs.get(0).getSellerName()) ? StringUtils.replace(
										sellerMbrs.get(0).getSellerName(), "|", " ") : sellerMbrs.get(0)
										.getSellerCompany(), "");
					}
					// 개인 사업자, 법인 사업자 ( 상호명, 이메일 )
					if (StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_PRIVATE_BUSINESS, sellerMbrs
							.get(0).getSellerClass())
							|| StringUtils.equals(MemberConstants.SellerConstants.SELLER_TYPE_LEGAL_BUSINESS,
									sellerMbrs.get(0).getSellerClass())) {

						// first:sellerName, second:sellerCompany, default:""
						nameLower = StringUtils.defaultString(
								StringUtils.isNotBlank(sellerMbrs.get(0).getSellerName()) ? StringUtils.replace(
										sellerMbrs.get(0).getSellerName(), "|", " ") : sellerMbrs.get(0)
										.getSellerCompany(), "");

						// first:sellerNickName, second:sellerCompany, third:sellerName, default:""
						compNmLower = StringUtils.isNotBlank(sellerMbrs.get(0).getSellerNickName()) ? sellerMbrs.get(0)
								.getSellerNickName() : StringUtils.defaultString(
								StringUtils.isNotBlank(sellerMbrs.get(0).getSellerCompany()) ? sellerMbrs.get(0)
										.getSellerCompany() : StringUtils.replace(sellerMbrs.get(0).getSellerName(),
										"|", " "), "");
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

				// 판매자, 제공자 Values object
				SellerMbrAppSac sellerMbrSac = null;
				ProviderMbrAppSac providerMbrSac = null;
				// 판매자, 제공자 정보 목록
				List<SellerMbrAppSac> sellerResList = new ArrayList<SellerMbrAppSac>();
				List<ProviderMbrAppSac> providerResList = new ArrayList<ProviderMbrAppSac>();

				// 상품 상세의 판매자 기본 정보 셋팅
				sellerInfo = new SellerMbrInfoSac();
				sellerInfo.setSellerId(sellerMbrs.get(0).getSellerID());
				sellerInfo.setIsDomestic(sellerMbrs.get(0).getIsDomestic());
				sellerInfo.setSellerClass(sellerMbrs.get(0).getSellerClass());
				sellerInfo.setProviderYn("N");

				for (SellerMbrSac sellerMbrSacReq : req.getSellerMbrSacList()) {
					// 현재의 sellerKey에 매칭되는 req를 확인하여 처리
					if (sellerMbrSacReq.getSellerKey().equals(sellerKey)) {
						// 해당 req에 categoryCd값이 있다면 제공자 정보 조회후 셋팅 없으면 판매자정보만 셋팅
						if (StringUtil.isNotBlank(sellerMbrSacReq.getCategoryCd())) {
							// SC 제공자 정보 조회
							SearchProviderResponse schProvRes = new SearchProviderResponse();
							SearchProviderRequest schProvReq = new SearchProviderRequest();
							schProvReq.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
							schProvReq.setSellerKey(sellerKey);
							schProvReq.setCategoryCd(sellerMbrSacReq.getCategoryCd());

							schProvRes = this.sellerSCI.searchProviderInfo(schProvReq);

							// 제공자 정보가 있으면 기존 판매자 정보를 제공자 정보로, 제공자 정보를 판매자 정보로 바꾼다.
							if (schProvRes != null && StringUtil.isNotBlank(schProvRes.getSellerKey())) {
								// 판매 제공자 유무 값 셋팅
								sellerInfo.setProviderYn("Y");

								// 기존 판매자 정보를 제공자 정보로 셋팅
								// 1. 상단 셋팅
								providerMbrSac = new ProviderMbrAppSac();
								providerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
								providerMbrSac.setSellerName(nameTop);
								providerResList.add(0, providerMbrSac);
								// 2. 하단 셋팅
								providerMbrSac = new ProviderMbrAppSac();
								providerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);
								providerMbrSac.setSellerName(nameLower);
								providerMbrSac.setSellerCompany(compNmLower);
								providerMbrSac.setSellerEmail(emailLower);
								providerMbrSac.setBizRegNumber(bizNoLower);
								providerMbrSac.setSellerAddress(addrLower);
								providerMbrSac.setSellerPhone(phoneLower);
								providerResList.add(1, providerMbrSac);
								// 3. 기존 판매자 정보를 제공자 정보로 셋팅
								sellerInfo.setProviderMbrList(providerResList);
								sellerInfo.setProviderId(sellerMbrs.get(0).getSellerID());
								sellerInfo.setProviderClass(sellerMbrs.get(0).getSellerClass());
								sellerInfo.setProviderIsDomestic(sellerMbrs.get(0).getIsDomestic());

								// 조회된 제공자 정보를 판매자 정보로 셋팅
								// 1. 상단 셋팅
								sellerMbrSac = new SellerMbrAppSac();
								sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
								sellerMbrSac.setSellerName(schProvRes.getSellerCompany());
								sellerResList.add(0, sellerMbrSac);
								// 2. 하단 셋팅
								sellerMbrSac = new SellerMbrAppSac();
								sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);
								sellerMbrSac.setSellerName(schProvRes.getSellerName());
								sellerMbrSac.setSellerCompany(schProvRes.getSellerCompany());
								sellerMbrSac.setSellerEmail(schProvRes.getSellerEmail());
								sellerMbrSac.setBizRegNumber(schProvRes.getBizRegNumber());
								sellerMbrSac.setSellerAddress(schProvRes.getSellerAddress());
								sellerMbrSac.setSellerPhone(schProvRes.getSellerPhone());
								sellerResList.add(1, sellerMbrSac);
								// 3. 조회된 제공자 정보를 판매자 정보로 셋팅. 단, 판매자id, 판매자구분코드, 판매자 내국인여부는 고정
								sellerInfo.setSellerMbrList(sellerResList);
								sellerInfo.setSellerId("");
								sellerInfo.setSellerClass("US010103");
								sellerInfo.setIsDomestic("Y");

								sellerInfoSacMap.put(sellerKey, sellerInfo);
							} else {
								// 1. 상단 셋팅
								sellerMbrSac = new SellerMbrAppSac();
								sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
								sellerMbrSac.setSellerName(nameTop);
								sellerResList.add(0, sellerMbrSac);
								// 2. 하단 셋팅
								sellerMbrSac = new SellerMbrAppSac();
								sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);
								sellerMbrSac.setSellerName(nameLower);
								sellerMbrSac.setSellerCompany(compNmLower);
								sellerMbrSac.setSellerEmail(emailLower);
								sellerMbrSac.setBizRegNumber(bizNoLower);
								sellerMbrSac.setSellerAddress(addrLower);
								sellerMbrSac.setSellerPhone(phoneLower);
								sellerResList.add(1, sellerMbrSac);
								// 3. 판매자 정보 셋팅
								sellerInfo.setSellerMbrList(sellerResList);
								// 4. 제공자 정보 null 셋팅
								sellerInfo.setProviderMbrList(providerResList);

								sellerInfo.setProviderYn("N");

								sellerInfoSacMap.put(sellerKey, sellerInfo);
							}
						} else {
							// 1. 상단 셋팅
							sellerMbrSac = new SellerMbrAppSac();
							sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_TOP);
							sellerMbrSac.setSellerName(nameTop);
							sellerResList.add(0, sellerMbrSac);
							// 2. 하단 셋팅
							sellerMbrSac = new SellerMbrAppSac();
							sellerMbrSac.setAppStat(MemberConstants.SellerConstants.SELLER_APP_DISPLAY_LOWER);
							sellerMbrSac.setSellerName(nameLower);
							sellerMbrSac.setSellerCompany(compNmLower);
							sellerMbrSac.setSellerEmail(emailLower);
							sellerMbrSac.setBizRegNumber(bizNoLower);
							sellerMbrSac.setSellerAddress(addrLower);
							sellerMbrSac.setSellerPhone(phoneLower);
							sellerResList.add(1, sellerMbrSac);
							// 3. 판매자 정보 셋팅
							sellerInfo.setSellerMbrList(sellerResList);
							// 4. 제공자 정보 null 셋팅
							sellerInfo.setProviderMbrList(providerResList);
							sellerInfo.setProviderYn("N");

							sellerInfoSacMap.put(sellerKey, sellerInfo);
						}
						break;
					}
				}
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
