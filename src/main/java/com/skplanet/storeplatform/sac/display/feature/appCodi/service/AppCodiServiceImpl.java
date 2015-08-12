/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.appCodi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2EcReq;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2EcRes;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2OfferObjectsEcRes;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2PrefCategoriesEcRes;
import com.skplanet.storeplatform.external.client.isf.vo.MultiValueType;
import com.skplanet.storeplatform.external.client.isf.vo.MultiValuesType;
import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiV2SacReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.AppCodiRes;
import com.skplanet.storeplatform.sac.display.feature.isf.common.constant.IsfConstants;
import com.skplanet.storeplatform.sac.display.feature.isf.common.util.IsfUtils;
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.IsfEcInvoker;
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.vo.IsfEcReq;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * App Codi Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppCodiServiceImpl implements AppCodiService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IsfEcInvoker invoker;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	// private final String APP_CODI_SVC_GRP_NM = "APPCODI";

	/**
	 * 
	 * <pre>
	 * App Codi 상품 리스트 조회.
	 * 회원 번호,전화 번호를 이용하여 ISF와 연동하여 App Codi 상품 리스트를 조회
	 * App Codi 상품의 정보는 채널 정보
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppCodiSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppCodiListSacRes
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public AppCodiListSacRes searchAppCodiList(AppCodiSacReq requestVO, SacRequestHeader requestHeader) {

		boolean isExists = true;

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		Map<String, Object> mapReq = new HashMap<String, Object>();
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);

		String userKey = requestVO.getUserKey();
		String deviceIdType = requestVO.getDeviceIdType();
		String deviceId = requestVO.getDeviceId();

		if (this.log.isDebugEnabled()) {
			this.log.debug("[searchAppCodiList] userKey : {}", userKey);
			this.log.debug("[searchAppCodiList] deviceIdType : {}", deviceIdType);
			this.log.debug("[searchAppCodiList] deviceId : {}", deviceId);
		}

		// 상품 아이디
		String sPid = "";
		// 추천 사유 코드
		String sReasonCode = "";
		// 연관 상품 아이디
		String sRelId = "";

		// 앱코디 상품 리스트 : DB 조회 후 결과를 저장
		List<String> listProdParam = new ArrayList<String>();
		// 연관상품 리스트 : DB 조회 후 결과를 저장
		List<String> listRelProdParam = new ArrayList<String>();

		// 추천 사유 저장 : 상품 아이디 + 추천 사유 코드
		Map<String, String> mapReason = new HashMap<String, String>();
		// 연관 상품 저장 : 상품 아이디 + 연관 상품 아이디
		Map<String, String> mapRelProd = new HashMap<String, String>();
		// ISF에서 내려준 리스트 순서 저장 : 상품 아이디 + 순서
		Map<String, Integer> mapRank = new HashMap<String, Integer>();
		// 순서 체크용 임시 변수 - 나중에 최종 개수로 사용함
		int idx = 0;

		AppCodiListSacRes responseVO = new AppCodiListSacRes();

		List<Product> productList = new ArrayList<Product>();

		CommonResponse commonResponse = new CommonResponse();

		// ISFRes response = new ISFRes();
		ISFRes response = null;
		try {
			// ISF 연동(EC)
			// UserKey와 DeviceId 값에 대항하는 ISF 연동 결과를 내려받는다
			// long 으로 조회시 SVC_CODI_0001 (App Codi Home추천)그 이외에는 SVC_MAIN_0003(메인 4Top Product추천) 연동결과를 내려받는다.
			response = this.invoker.invoke(this.makeRequest(requestVO));

			int multiCount = (response.getProps().getMultiValues() != null) ? response.getProps().getMultiValues().getCount() : 0;
			if (multiCount > 0) {
				MultiValuesType multis = response.getProps().getMultiValues(); // 연동결과가 담겨있는 객체
				for (MultiValueType multi : multis.getMultiValue()) {
					sPid = multi.getId(); // pid(상품 ID : 채널)
					sReasonCode = multi.getReasonCode(); // 추천 사유 코드
					sRelId = multi.getRelId(); // 연관 상품 id

					// PID 리스트 저장
					listProdParam.add(sPid);
					// PID 리스트 순서 저장
					mapRank.put(sPid, idx);
					idx++;
					// PID 별 추천사유 저장
					mapReason.put(sPid, sReasonCode);

					// 연관 상품 정보가 있을 경우에 연관 상품 정보 저장
					if (StringUtils.isNotEmpty(sRelId)) {
						// 연관 상품 PID 저장 : DB 조회용
						listRelProdParam.add(sRelId);
						// 연관 상품 맵핑(추천상품ID, 연관상품ID)
						mapRelProd.put(sPid, sRelId);
					}
				}

				// ISF 내려받은 PID 리스트의 값을 상품 기본정보 조회를 위한 mapReq에 Put
				mapReq.put("pidList", listProdParam);
				mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

				// 단말 지원정보 조회
				SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(deviceHeader.getModel());
				if (supportDevice != null) {
					mapReq.put("ebookSprtYn", supportDevice.getEbookSprtYn());
					mapReq.put("comicSprtYn", supportDevice.getComicSprtYn());
					mapReq.put("musicSprtYn", supportDevice.getMusicSprtYn());
					mapReq.put("videoDrmSprtYn", supportDevice.getVideoDrmSprtYn());
					mapReq.put("sdVideoSprtYn", supportDevice.getSdVideoSprtYn());

					// 상품 기본 정보 List 조회
					List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
							"Isf.AppCodi.getAppCodiProdList", mapReq, ProductBasicInfo.class);

					if (this.log.isDebugEnabled()) {
						this.log.debug("##### parameter cnt : {}", listProdParam.size());
						this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size());
					}
					if (!productBasicInfoList.isEmpty()) {
						commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());

						Product product = null;
						MetaInfo metaInfo = null;

						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("tenantHeader", tenantHeader);
						paramMap.put("deviceHeader", deviceHeader);
						paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING); // 판매중

						// Meta 정보 조회
						for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

							String topMenuId = productBasicInfo.getTopMenuId(); // 탑메뉴
							String svcGrpCd = productBasicInfo.getSvcGrpCd(); // 서비스 그룹 코드
							paramMap.put("productBasicInfo", productBasicInfo); // 상품 기본 정보

							if (this.log.isDebugEnabled()) {
								this.log.debug("##### Top Menu Id : {}", topMenuId);
								this.log.debug("##### Service Group Cd : {}", svcGrpCd);
							}
							// 상품 SVC_GRP_CD 조회
							// DP000203 : 멀티미디어
							// DP000206 : Tstore 쇼핑
							// DP000205 : 소셜쇼핑
							// DP000204 : 폰꾸미기
							// DP000201 : 애플리캐이션
							// APP 상품의 경우
							if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
								paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
								if (this.log.isDebugEnabled()) {
									this.log.debug("##### Search for app  meta info product");
								}
								metaInfo = this.metaInfoService.getAppMetaInfo(paramMap);
								if (metaInfo != null) {
									product = this.responseInfoGenerateFacade.generateAppProduct(metaInfo);
									productList.add(product);
								}

							} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일
																										  // 경우
								// 영화/방송 상품의 경우
								paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
								if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
										|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) { // 영화 / 방송일 경우
									if (this.log.isDebugEnabled()) {
										this.log.debug("##### Search for Vod  meta info product");
									}
									metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);
									if (metaInfo != null) {
										if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
											product = this.responseInfoGenerateFacade.generateMovieProduct(metaInfo);
										} else {
											product = this.responseInfoGenerateFacade
													.generateBroadcastProduct(metaInfo);
										}
										productList.add(product);
									}
								} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
										|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의 경우

									paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

									if (this.log.isDebugEnabled()) {
										this.log.debug("##### Search for EbookComic specific product");
									}
									metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);
									if (metaInfo != null) {
										if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
											product = this.responseInfoGenerateFacade.generateEbookProduct(metaInfo);
										} else {
											product = this.responseInfoGenerateFacade.generateComicProduct(metaInfo);
										}
										productList.add(product);
									}

								} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
								
									paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
									paramMap.put("contentTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

									if (this.log.isDebugEnabled()) {
										this.log.debug("##### Search for music meta info product");
									}
									metaInfo = this.metaInfoService.getMusicMetaInfo(paramMap);
									if (metaInfo != null) {
										product = this.responseInfoGenerateFacade.generateMusicProduct(metaInfo);
										productList.add(product);
									}
								}
							} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
								paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD); // 채널-에피소드  상품 관계 코드
								paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

								if (this.log.isDebugEnabled()) {
									this.log.debug("##### Search for Shopping  meta info product");
								}
								metaInfo = this.metaInfoService.getShoppingMetaInfo(paramMap);
								if (metaInfo != null) {
									product = this.responseInfoGenerateFacade.generateShoppingProduct(metaInfo);
									productList.add(product);
								}
							}
						}
					}
				}

				if (this.log.isDebugEnabled()) {
					this.log.debug("product count : {}", productList.size());
				}

				if (productList.isEmpty()) {
					isExists = false;
				} else {
					// 연관상품 정보 조회
					List<HashMap> listRelProd = null;
					if (!listRelProdParam.isEmpty()) {
						Map<String, Object> mapRel = new HashMap<String, Object>();
						mapRel.put("tenantHeader", tenantHeader);
						mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);
						mapRel.put("pidList", listRelProdParam); // 연관상품의 상품 ID List

						listRelProd = this.commonDAO.queryForList("Isf.AppCodi.getRelProdList", mapRel, HashMap.class);
					}

					boolean isRel = false; // 연관상품 boolean
					String reasonMessage = ""; // 추천사유 Message

					// ISF 내려받은 상품의 숫자만큼 추천 사유가 들어갈 Array 공간을 만들어준다
					List<Product> listAppCodiReason = new ArrayList<Product>(idx);
					for (int i = 0; i < idx; i++) {
						listAppCodiReason.add(null);
					}

					/*
					 * 앱코디 상품 조회 리스트를 돌리면서 추천 사유 메세지 생성. 처리 순서는 처음에 저장한 mapRank의 Index 값을 가져와서 생성하여 처리한다.(pid를 key값으로
					 * Index가 잡혀있음)
					 */
					for (Product product : productList) {
						for (Identifier id : product.getIdentifierList()) {
							// Meta 데이터를 조회한 상품의 ID Type이 채널일때
							if (id.getType().equalsIgnoreCase(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD)) {
								sPid = id.getText();
								if (StringUtil.isNotEmpty(sPid))
									break;
							}
						}

						sRelId = mapRelProd.get(sPid); // 추천 상품 ID에 맵핑되어 있는 연관상품 ID
						sReasonCode = mapReason.get(sPid); // 추천 상품에 맵핑되어 있는 추천 사유 코드

						// 추천 사유코드가 02 또는 03이면 04로 치환
						if ("02".equals(sReasonCode) || "03".equals(sReasonCode)) {
							sReasonCode = "04";
						}

						// ISF에서 내려받은 추천 코드에 해당 하는 Message
						if (sReasonCode != null) {
							reasonMessage = IsfConstants.mapReasonCode.get(sReasonCode);
						}
						if (reasonMessage == null)
							reasonMessage = "";

						// 기존 두자리 사유 코드 처리 정책 (4자리로 바뀌면 의미는 없음)
						// 추천사유코드 : 01이고, 연관 상품이 있으면 상품명의 구매 추천(414 Line에서 처리)
						// 추천사유코드 : 01이고, 연관 상품이 없으면, Power User’s Best 추천(445 Line에서 처리)
						// 추천사유코드 : 02,03 무조건 Power User’s Best 추천

						// 연관상품 여부
						isRel = false;

						/*
						 * 추천 사유 Msessage 중 $문자가 포함되어 있으면 치환 시작 $ 문자는 replace해야 되는 문자
						 */
						if (reasonMessage.indexOf("$") > -1) {

							// 조회된 추천 상품(Isf.AppCodi.getAppCodiProdList) 대분류 카테고리명
							// 추천상품은 연관상품과 별개로 처리함
							String top_cat_nm_pid = "";

							for (Menu menu : product.getMenuList()) {
								// 조회된 추천 상품(Isf.AppCodi.getAppCodiProdList)의 menu type이 topClass 일때 해당 메뉴명을 치환할 변수에 set
								if (!StringUtils.isEmpty(menu.getType())
										&& DisplayConstants.DP_MENU_TOPCLASS_TYPE.equals(menu.getType())) {
									top_cat_nm_pid = menu.getName();
								}
							}

							// 추천사유의 $4 문자를 추천상품의 top_menu 명으로 치환
							reasonMessage = StringUtils.replace(reasonMessage, "$4", top_cat_nm_pid);

							// 연관상품 정보가 있을 경우에 치환처리함
							if (CollectionUtils.isNotEmpty(listRelProd)) {
								for (Map<String, Object> mapRelProdTemp : listRelProd) {
									if (StringUtil.isNotEmpty(sRelId)
											&& sRelId.equals(ObjectUtils.toString(mapRelProdTemp.get("PROD_ID")))) {
										// 연관상품명
										String prod_nm = (String) mapRelProdTemp.get("PROD_NM");
										// 24 byte(한글 12자리)로 상품명을 조절( DA 팀 요청)
										prod_nm = IsfUtils.cutStringLimit(prod_nm, 24);
										// 연관상품 대분류 카테고리명
										String top_cat_nm = (String) mapRelProdTemp.get("TOP_MENU_NM");
										// 연관상품 중분류 카테고리명
										String dp_cat_nm = (String) mapRelProdTemp.get("MENU_NM");

										// 메세지 치환
										reasonMessage = StringUtils.replace(reasonMessage, "$1", prod_nm);
										reasonMessage = StringUtils.replace(reasonMessage, "$2", top_cat_nm);
										reasonMessage = StringUtils.replace(reasonMessage, "$3", dp_cat_nm);

										product.setRecommendedReason(reasonMessage);
										isRel = true;
										break;
									}
								}
							}
						}

						// 연관상품이 없거나 치환할 문자가 없는 경우에는 각각의 사유 메세지 정보를 셋팅
						// 단, 2자리 사유코드 01일 경우($문자가 포함되어 있는 추천사유는 01밖에 없음)에는 04 코드로 사용한다.
						// 01일 경우에는 연관상품이 있어야 하나 없을 경우 대비한 방어로직
						// 기타 4자리 코드인데 연관상품으로 치환되지 않고 사유 메세지가 $문자를 포함되어 있을때 $1,2,3가 그냥 노출되기 때문에 임시로 04번 코드로 치환한다.

						if (!isRel && reasonMessage.indexOf("$") > -1) {
							reasonMessage = IsfConstants.mapReasonCode.get("04");
							product.setRecommendedReason(reasonMessage);
						} else {
							product.setRecommendedReason(reasonMessage);
						}

						if (this.log.isDebugEnabled()) {
							this.log.debug("reasonMessage : " + product.getRecommendedReason());
						}

						// ISF연동 정보시 저장한 순서를 가져와서 최종 List에 해당 Index 순서대로 add 한다.
						int index = mapRank.get(sPid);
						listAppCodiReason.set(index, product);
					}

					// 앱코디 상품리스트 조회 결과(Isf.AppCodi.getAppCodiProdList)가 ISF 에서 넘겨주는 상품 정보보다 적을 수 있어서(판매중지 등의 사유)
					// 중간에 Index가 비는 문제가 발생할 수 있다. 그래서 아래와 같이 null element 삭제 로직 추가한다.
					listAppCodiReason.removeAll(Arrays.asList(new Object[] { null }));

					String sStartRow = "1";
					String sEndRow = "4";

					// Range 가 정의된 경우
					if (!StringUtils.equalsIgnoreCase("short", requestVO.getFilteredBy())) {
						sStartRow = StringUtils.defaultIfEmpty(ObjectUtils.toString(requestVO.getOffset()), "1");
						sEndRow = ObjectUtils.toString(requestVO.getCount() + requestVO.getOffset() - 1);
					}

					List<Product> listRes = new ArrayList<Product>();
					if (StringUtils.isNotEmpty(sEndRow)) {
						int iStartRow = NumberUtils.toInt(sStartRow) - 1;
						int iEndRow = NumberUtils.toInt(sEndRow);
						if (iEndRow > iStartRow) {
							int iAppCodeReasonSize = listAppCodiReason.size();

							iStartRow = (iStartRow < 0) ? 0 : iStartRow;
							iEndRow = (iEndRow > iAppCodeReasonSize) ? iAppCodeReasonSize : iEndRow;

							// range 재설정
							mapReq.put("START_ROW", Integer.toString(iStartRow + 1));
							mapReq.put("END_ROW", Integer.toString(iEndRow));

							listRes.addAll(listAppCodiReason.subList(iStartRow, iEndRow));
						} else {
							listRes.addAll(listAppCodiReason);
						}
					} else {
						listRes.addAll(listAppCodiReason);
					}

					responseVO.setCommonRes(commonResponse);
					responseVO.setProductList(listRes);
				}
			} else {
				// isExists = false;
				ErrorInfo error = new ErrorInfo();
				error.setCode("SAC_DSP_0010"); // ISF 서버로부터 받은 결과값이 없습니다.
				throw new StorePlatformException(error);
			}
		} catch (StorePlatformException se) {
			// isExists = false;
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			// isExists = false;
			ErrorInfo error = new ErrorInfo();
			error.setCode("SAC_DSP_0009"); // 요청하신 자료가 존재하지 않습니다.
			error.setMessage(e.getLocalizedMessage());
			throw new StorePlatformException(error);
		}

		// ISF 연동 실패나 Data 가 없는 경우
		if (!isExists) {
			commonResponse.setTotalCount(0);
			responseVO.setCommonRes(commonResponse);
			responseVO.setProductList(productList);

			/*
			 * ErrorInfo error = new ErrorInfo(); error.setCode("SAC_DSP_0010"); throw new
			 * StorePlatformException(error);
			 *//*
			    * this.totalCount = 0;
			    * 
			    * this.log.info("ISF 연동 실패나 Data 가 없는 경우 - 운영자 추천으로 대체");
			    * 
			    * mapReq = new HashMap<String, Object>(); if (!"long".equalsIgnoreCase(requestVO.getFilteredBy())) {
			    * mapReq.put("START_ROW", "1"); mapReq.put("END_ROW", "4"); } else { mapReq.put("START_ROW",
			    * requestVO.getOffset()); mapReq.put("END_ROW", (requestVO.getOffset() + requestVO.getCount() - 1)); }
			    * 
			    * mapReq.put("tenantHeader", requestHeader.getTenantHeader()); mapReq.put("deviceHeader",
			    * requestHeader.getDeviceHeader()); mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);
			    * 
			    * mapReq.put("listId", IsfConstants.recommandListId); // 운영자 추천
			    * 
			    * 
			    * List<String> imageCodeList = new ArrayList<String>();
			    * imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			    * imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			    * imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			    * imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
			    * imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD); mapReq.put("imageCdList",
			    * imageCodeList);
			    * 
			    * if (this.log.isDebugEnabled()) { this.mapPrint(mapReq); }
			    * 
			    * // 통쿼리 사용제한
			    * 
			    * List<AppCodiRes> appCodiResultList =
			    * this.commonDAO.queryForList("Isf.AppCodi.getAdminRecommandProdList", mapReq, AppCodiRes.class);
			    * productList = this.makeResultList(appCodiResultList);
			    * 
			    * // 상품 기본 정보 List 조회 - 운영자 추천 List<ProductBasicInfo> productBasicInfoList =
			    * this.commonDAO.queryForList( "Isf.AppCodi.getBasicAdminRecommandProdList", mapReq,
			    * ProductBasicInfo.class);
			    * 
			    * if (this.log.isDebugEnabled()) { this.log.debug("##### parameter cnt : {}", listProdParam.size());
			    * this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size()); } if
			    * (!productBasicInfoList.isEmpty()) {
			    * 
			    * Product product = null; MetaInfo metaInfo = null;
			    * 
			    * Map<String, Object> paramMap = new HashMap<String, Object>(); paramMap.put("tenantHeader",
			    * tenantHeader); paramMap.put("deviceHeader", deviceHeader); paramMap.put("prodStatusCd",
			    * DisplayConstants.DP_SALE_STAT_ING); // 판매중
			    * 
			    * // Meta 정보 조회 for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
			    * 
			    * this.totalCount = productBasicInfo.getTotalCount();
			    * 
			    * String topMenuId = productBasicInfo.getTopMenuId(); // 탑메뉴 String svcGrpCd =
			    * productBasicInfo.getSvcGrpCd(); // 서비스 그룹 코드 paramMap.put("productBasicInfo", productBasicInfo);
			    * 
			    * if (this.log.isDebugEnabled()) { this.log.debug("##### Top Menu Id : {}", topMenuId);
			    * this.log.debug("##### Service Group Cd : {}", svcGrpCd); } // 상품 SVC_GRP_CD 조회 // DP000203 : 멀티미디어 //
			    * DP000206 : Tstore 쇼핑 // DP000205 : 소셜쇼핑 // DP000204 : 폰꾸미기 // DP000201 : 애플리캐이션 // APP 상품의 경우 if
			    * (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) { paramMap.put("imageCd",
			    * DisplayConstants.DP_APP_REPRESENT_IMAGE_CD); if (this.log.isDebugEnabled()) {
			    * this.log.debug("##### Search for app  meta info product"); } metaInfo =
			    * this.metaInfoService.getAppMetaInfo(paramMap); if (metaInfo != null) { product =
			    * this.responseInfoGenerateFacade.generateAppProduct(metaInfo); productList.add(product); }
			    * 
			    * } else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우 // 영화/방송
			    * 상품의 경우 paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD); if
			    * (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId) ||
			    * DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) { if (this.log.isDebugEnabled()) {
			    * this.log.debug("##### Search for Vod  meta info product"); } metaInfo =
			    * this.metaInfoService.getVODMetaInfo(paramMap); if (metaInfo != null) { if
			    * (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) { product =
			    * this.responseInfoGenerateFacade.generateMovieProduct(metaInfo); } else { product =
			    * this.responseInfoGenerateFacade.generateBroadcastProduct(metaInfo); } productList.add(product); } }
			    * else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId) ||
			    * DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의 // 경우
			    * 
			    * paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			    * 
			    * if (this.log.isDebugEnabled()) { this.log.debug("##### Search for EbookComic specific product"); }
			    * metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap); if (metaInfo != null) { if
			    * (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) { product =
			    * this.responseInfoGenerateFacade.generateEbookProduct(metaInfo); } else { product =
			    * this.responseInfoGenerateFacade.generateComicProduct(metaInfo); } productList.add(product); }
			    * 
			    * } else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
			    * 
			    * paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD); paramMap.put("contentTypeCd",
			    * DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
			    * 
			    * if (this.log.isDebugEnabled()) { this.log.debug("##### Search for music meta info product"); }
			    * metaInfo = this.metaInfoService.getMusicMetaInfo(paramMap); if (metaInfo != null) { product =
			    * this.responseInfoGenerateFacade.generateMusicProduct(metaInfo); productList.add(product); } } } else
			    * if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
			    * paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
			    * paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
			    * 
			    * if (this.log.isDebugEnabled()) { this.log.debug("##### Search for Shopping  meta info product"); }
			    * metaInfo = this.metaInfoService.getShoppingMetaInfo(paramMap); if (metaInfo != null) { product =
			    * this.responseInfoGenerateFacade.generateShoppingProduct(metaInfo); productList.add(product); } } } }
			    * 
			    * if (this.log.isDebugEnabled()) { this.log.debug("product count : {}", productList.size());
			    * this.log.debug("total count : {}", this.totalCount); }
			    * 
			    * commonResponse.setTotalCount(this.totalCount); responseVO.setCommonRes(commonResponse);
			    * responseVO.setProductList(productList);
			    */
		}

		return responseVO;
	}

	/**
	 * 
	 * <pre>
	 * 개인별 선호 메뉴 및 추천 상품을 조회.
	 * 회원 번호,전화 번호를 이용하여 (DA)서버와 연동하여 개인별 선호 메뉴와 추천 상품 정보를 받는다
	 * 2.8.1 과 달리 상품 정보 조회 상품에 대한 Provisioning는 하지 않는다.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppCodiSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppCodiListSacRes
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AppCodiListSacRes searchAppCodiListV2(AppCodiV2SacReq requestVO, SacRequestHeader requestHeader) {
		AppCodiListSacRes appCodiListSacRes = new AppCodiListSacRes();
		CommonResponse commonResponse = new CommonResponse();

		Layout layout = null;
		List<Product> productList = new ArrayList<Product>();

		IsfV2EcReq ecReq = null;
		IsfV2EcRes ecRes = null;

		String userKey = requestVO.getUserKey();
		String preference = requestVO.getPreference();
		String prodGradeCd = requestVO.getProdGradeCd();
		String topMenuId = requestVO.getTopMenuId();

		try {
			ecReq = new IsfV2EcReq();
			ecReq.setUserKey(userKey);
			ecReq.setPreference(preference);
			// Parameter 중 TopMenuId가 DP00으로 들어오는 경우 빈값(Empty String)으로 Setting(전체 조회를 위해)
			ecReq.setCateId(StringUtils.equals(topMenuId, "DP00") ? "" : topMenuId);

			// Parameter의 상품이용등급이 null 이거나 PD004404 코드가 들어오면 성인등급 추천 값을 Y로 setting
			if (StringUtils.isEmpty(prodGradeCd) || prodGradeCd.indexOf("PD004404") >= 0) {
				ecReq.setAdultYN("Y");
			} else {
				ecReq.setAdultYN("N");
			}

			this.log.info("----------------------------------------------------");
			this.log.info(">>> [appCodiV2EcParam] preference : {}", ecReq.getPreference());
			this.log.info(">>> [appCodiV2EcParam] userKey : {}", ecReq.getUserKey());
			this.log.info(">>> [appCodiV2EcParam] adultYN : {}", ecReq.getAdultYN());
			this.log.info(">>> [appCodiV2EcParam] cateId : {}", ecReq.getCateId());
			this.log.info("----------------------------------------------------");

			// App Codi ISF 연동
			ecRes = this.invoker.invokeV2(ecReq);
		} catch (StorePlatformException se) {
			throw se;
		} catch (Exception e) {
			this.log.error(e.getMessage());
			ErrorInfo error = new ErrorInfo();
			error.setCode("EC_ISF_9999");
			error.setMessage(e.getLocalizedMessage());
			throw new StorePlatformException(error);
		}

		if (ecRes != null) {
			List<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> paramMap = new HashMap<String, Object>();

			// 선호타입이 '101'일 경우, 선호 카테고리 정보 세팅
			if (StringUtils.equals("101", requestVO.getPreference())) {
				for (IsfV2PrefCategoriesEcRes item : ecRes.getData().getPrefCategories()) {
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("menuId", item.getCateId());
					param.put("menuOrd", item.getRank());
					itemList.add(param);
				}

				paramMap.put("menuList", itemList);
				paramMap.put("tenantHeader", requestHeader.getTenantHeader());

				// ISF 응답 결과(선호 카테고리)의 카테고리 정보 조회
				List<HashMap> menuInfoList = this.commonDAO.queryForList("Isf.AppCodi.getCategoryInfoList", paramMap,
						HashMap.class);

				// 조회된 카테고리 정보를 Layout 규격에 set
				if (CollectionUtils.isNotEmpty(menuInfoList)) {
					Menu menu = null;
					List<Menu> menuList = new ArrayList<Menu>();

					for (HashMap map : menuInfoList) {
						menu = new Menu();
						menu.setId((String) map.get("MENU_ID"));
						menu.setName((String) map.get("MENU_NM"));
						menuList.add(menu);
					}

					layout = new Layout();
					layout.setMenuList(menuList);
				}
			}
			itemList.clear();

			for (IsfV2OfferObjectsEcRes item : ecRes.getData().getOfferObjects()) {
				HashMap<String, Object> param = new HashMap<String, Object>();

				param.put("itemId", item.getItemId()); // 추천상품ID
				param.put("itemOrd", item.getRank()); // 추천상품 우선순위
				param.put("recmItemId", StringUtils.stripToEmpty(item.getRelatedItem())); // 추천근거 상품ID
				param.put("recmReasonCd", item.getRecmCd()); // 추천근거 코드

				itemList.add(param);
			}

			paramMap.put("itemList", itemList);
			paramMap.put("tenantHeader", requestHeader.getTenantHeader());

			// ISF 응답 결과인 추천상품 리스트에 대한 상품 기본정보 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
					"Isf.AppCodi.getProdBasicInfoList", paramMap, ProductBasicInfo.class);

			if (CollectionUtils.isNotEmpty(productBasicInfoList)) {
				for (ProductBasicInfo info : productBasicInfoList) {
					// 추천상품 메타정보 조회
					Product product = this.getMetaInfo(info, requestHeader);

					if (product != null) {
						// 추천근거 사유
						product.setRecommendedReason(info.getRecmReason());
						productList.add(product);
					}
				}
			}
		} else {
			throw new StorePlatformException("EC_ISF_9999");
		}

		if (layout != null) {
			appCodiListSacRes.setLayout(layout);
		}

		appCodiListSacRes.setProductList(productList);
		commonResponse.setTotalCount(productList.size());
		appCodiListSacRes.setCommonRes(commonResponse);

		return appCodiListSacRes;
	}

	private Product getMetaInfo(ProductBasicInfo info, SacRequestHeader header) {
		Product product = null;
		MetaInfo metaInfo = null;

		String topMenuId = info.getTopMenuId();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		// 메타정보 조회를 위한 파라미터 세팅
		paramMap.put("productBasicInfo", info);
		paramMap.put("tenantHeader", header.getTenantHeader());
		paramMap.put("deviceHeader", header.getDeviceHeader());
		paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		// APP
		if (DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getAppMetaInfo(paramMap);

			if (metaInfo != null) {
				product = this.responseInfoGenerateFacade.generateAppProductShort(metaInfo);
			}
		}
		// 영화
		else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);

			if (metaInfo != null) {
				metaInfo.setSvcGrpNm(DisplayConstants.APP_CODI_SVC_GRP_NM);
				product = this.responseInfoGenerateFacade.generateMovieProductShort(metaInfo);
			}
		}
		// TV방송
		else if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);

			if (metaInfo != null) {
				metaInfo.setSvcGrpNm(DisplayConstants.APP_CODI_SVC_GRP_NM);
				product = this.responseInfoGenerateFacade.generateBroadcastProductShort(metaInfo);
			}
		}
		// 이북
		else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);

			if (metaInfo != null) {
				metaInfo.setSvcGrpNm(DisplayConstants.APP_CODI_SVC_GRP_NM);
				product = this.responseInfoGenerateFacade.generateEbookProductShort(metaInfo);
			}
		}
		// 코믹
		else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);

			if (metaInfo != null) {
				metaInfo.setSvcGrpNm(DisplayConstants.APP_CODI_SVC_GRP_NM);
				product = this.responseInfoGenerateFacade.generateComicProductShort(metaInfo);
			}
		}
		// 음악
		else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getMusicMetaInfo(paramMap);

			if (metaInfo != null) {
				product = this.responseInfoGenerateFacade.generateMusicProductShort(metaInfo);
			}
		}

		return product;
	}

	private IsfEcReq makeRequest(AppCodiSacReq requestVO) {

		IsfEcReq request = new IsfEcReq();

		if ("long".equals(requestVO.getFilteredBy()))
			request.setId("SVC_CODI_0001"); // App Codi Home추천App&Contents 정보를 조회
		else
			request.setId("SVC_MAIN_0003"); // 모바일 앱의 4Top Product 추천 정보를 조회

		request.setChCode("M"); // 접속 채널 구분 M(Mobile) /W(Web)
		request.setMbn(requestVO.getUserKey());
		request.setMdn(requestVO.getDeviceId());
		request.setType(requestVO.getFilteredBy()); // long / short 실질적으로 ISF에서 사용되지는 않음

		if (this.log.isDebugEnabled()) {
			this.log.debug(request.toString());
		}
		return request;
	}

	@SuppressWarnings({ "unused" })
	private List<Product> makeResultList(List<AppCodiRes> resultList) {

		List<Product> listVO = new ArrayList<Product>();

		for (AppCodiRes mapper : resultList) {

			Product product;
			Identifier identifier;
			Title title;
			App app;
			Music music;
			Accrual accrual;
			Rights rights;
			Source source;
			Support support;
			Price price;
			Menu menu;
			Contributor contributor;

			// Response VO를 만들기위한 생성자
			List<Menu> menuList;
			List<Source> sourceList;
			List<Support> supportList;
			List<Identifier> identifierList;

			product = new Product();
			// identifier = new Identifier();
			title = new Title();
			app = new App();
			music = new Music();
			accrual = new Accrual();
			rights = new Rights();
			support = new Support();
			source = new Source();
			price = new Price();
			contributor = new Contributor();

			// 상품ID
			identifier = new Identifier();

			// Response VO를 만들기위한 생성자
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();
			identifierList = new ArrayList<Identifier>();

			Map<String, String> idReqMap = new HashMap<String, String>();
			idReqMap.put("prodId", mapper.getProdId());
			idReqMap.put("topMenuId", mapper.getTopMenuId());
			idReqMap.put("contentsTypeCd", mapper.getContentsTypeCd());
			idReqMap.put("outsdContentsId", mapper.getOutsdContentsId());

			title.setText(mapper.getProdNm());
			product.setTitle(title);

			menu = new Menu();
			menu.setId(mapper.getTopMenuId());
			menu.setName(mapper.getTopMenuNm());
			menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
			menuList.add(menu);
			menu = new Menu();
			menu.setId(mapper.getMenuId());
			menu.setName(mapper.getMenuNm());
			menuList.add(menu);
			product.setMenuList(menuList);

			accrual.setVoterCount(Integer.valueOf(mapper.getPartCnt()));
			accrual.setDownloadCount(Integer.valueOf(mapper.getDwldCnt()));
			accrual.setScore(Double.valueOf(mapper.getAvgScore()));
			product.setAccrual(accrual);

			/*
			 * Rights grade
			 */
			rights.setGrade(mapper.getProdGrdCd());
			product.setRights(rights);

			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getFilePos()));
			source.setUrl(mapper.getFilePos());
			sourceList.add(source);
			product.setSourceList(sourceList);

			/*
			 * Price text
			 */
			price.setText(Integer.valueOf(mapper.getProdAmt())); // 가격
			price.setFixedPrice(Integer.valueOf(mapper.getProdNetAmt())); // 고정가
			product.setPrice(price);

			// 상품 SVC_GRP_CD 조회
			// DP000203 : 멀티미디어
			// DP000206 : Tstore 쇼핑
			// DP000205 : 소셜쇼핑
			// DP000204 : 폰꾸미기
			// DP000201 : 애플리캐이션
			if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 앱 타입일 경우

				identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(mapper.getProdId());
				identifierList.add(identifier);
				product.setIdentifierList(identifierList);

				app.setAid(mapper.getAid());
				app.setPackageName(mapper.getApkPkg());
				app.setVersionCode(mapper.getApkVerCd());
				app.setVersion(mapper.getProdVer()); // 확인 필요
				product.setApp(app);

				// support list
				support.setText(DisplayConstants.DP_DRM_SUPPORT_NM);
				support.setText(mapper.getDrm());
				supportList.add(support);

				support.setText(DisplayConstants.DP_IN_APP_SUPPORT_NM);
				support.setText(mapper.getInAppBilling());
				supportList.add(support);

				product.setSupportList(supportList);

			} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 멀티미디어 타입일 경우

				identifierList = IsfUtils.generateIdentifierList(idReqMap);
				product.setIdentifierList(identifierList);

				// support list
				if (!StringUtils.isEmpty(mapper.getHdvYn())) {
					support.setText(DisplayConstants.DP_VOD_HD_SUPPORT_NM);
					support.setText(mapper.getHdvYn());
					supportList.add(support);
				}

				if (!StringUtils.isEmpty(mapper.getDolbySprt_yn())) {
					support.setText(DisplayConstants.DP_VOD_DOLBY_SUPPORT_NM);
					support.setText(mapper.getDolbySprt_yn());
					supportList.add(support);
				}

				if (!supportList.isEmpty()) {
					product.setSupportList(supportList);
				}

				if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(mapper.getTopMenuId())
						|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // 영화/방송

					contributor.setDirector(mapper.getVodArtistNm2()); // 제작자
					contributor.setArtist(mapper.getVodArtistNm()); // 출연자
					contributor.setCompany(mapper.getVodChnlCompNm());
					Date date = new Date();
					date.setTextFromDate(DateUtils.parseDate(mapper.getVodSaleDt()));
					contributor.setDate(date);
					product.setContributor(contributor);

				} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapper.getTopMenuId())
						|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // Ebook / Comic

					contributor.setName(mapper.getBookArtistNm()); // 제목
					contributor.setPainter(mapper.getBookArtistNm2()); //
					contributor.setPublisher(mapper.getBookChnlCompNm()); // 출판사
					Date date = new Date();
					date.setTextFromDate(DateUtils.parseDate(mapper.getBookSaleDt())); // 출판년도
					contributor.setDate(date);
					product.setContributor(contributor);

				} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // 음원 상품의 경우
					// music service list set
					List<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service> serviceList = new ArrayList<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service>();

					com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();
					service.setName(DisplayConstants.DP_MUSIC_SERVICE_MP3);
					service.setType(mapper.getMp3Sprt());
					serviceList.add(service);

					service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();
					service.setName(DisplayConstants.DP_MUSIC_SERVICE_BELL);
					service.setType(mapper.getBellSprt());
					serviceList.add(service);

					service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();
					service.setName(DisplayConstants.DP_MUSIC_SERVICE_RING);
					service.setType(mapper.getRingSprt());
					serviceList.add(service);
					music.setServiceList(serviceList);
					product.setMusic(music);

					contributor.setName(mapper.getMusicArtistNm()); // 가수
					contributor.setAlbum(mapper.getMusicArtistNm3()); // 앨범명
					contributor.setPublisher(mapper.getMusicChnlCompNm()); // 발행인
					contributor.setAgency(mapper.getMusicAgencyNm()); // 에이전시
					product.setContributor(contributor);
				}
			} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 쇼핑 상품의 경우

				identifierList = IsfUtils.generateIdentifierList(idReqMap);
				product.setIdentifierList(identifierList);

			}

			product.setProductExplain(mapper.getProdDesc());

			listVO.add(product);

		} // end of while

		return listVO;
	}

}
