/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.appguide.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.external.client.isf.vo.MultiValueType;
import com.skplanet.storeplatform.external.client.isf.vo.MultiValuesType;
import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideIsfSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.appguide.vo.Appguide;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.feature.isf.common.constant.IsfConstants;
import com.skplanet.storeplatform.sac.display.feature.isf.common.util.IsfUtils;
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.IsfEcInvoker;
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.vo.IsfEcReq;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * App guide Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 02. 26. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppguideIsfServiceImpl implements AppguideIsfService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int totalCount = 0;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private IsfEcInvoker invoker;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchTopCategoryList(MenuReq
	 * requestVO)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AppguideSacRes searchIsfRecommendList(AppguideIsfSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		boolean isExists = true;

		String className = this.getClass().getName();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		Map<String, Object> mapReq = new HashMap<String, Object>();
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		String userKey = requestVO.getUserKey();
		String deviceIdType = requestVO.getDeviceIdType();
		String deviceId = requestVO.getDeviceId();

		if (this.log.isDebugEnabled()) {
			this.log.debug("[{}] userKey : {}", className, userKey);
			this.log.debug("[{}] deviceIdType : {}", className, deviceIdType);
			this.log.debug("[{}] deviceId : {}", className, deviceId);
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

		AppguideSacRes responseVO = new AppguideSacRes();

		List<Product> productList = new ArrayList<Product>();

		CommonResponse commonResponse = new CommonResponse();

		ISFRes response = new ISFRes();
		try {
			// ISF 연동
			response = this.invoker.invoke(this.makeRequest(requestVO));

			int multiCount = (response.getProps().getMultiValues() != null) ? response.getProps().getMultiValues()
					.getCount() : 0;
			this.log.debug("multiCount : {}", multiCount);

			if (multiCount > 0) {
				MultiValuesType multis = response.getProps().getMultiValues();
				for (MultiValueType multi : multis.getMultiValue()) {
					sPid = multi.getId(); // pid
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
						// PID - 연관상품 ID 매핑 관리
						mapRelProd.put(sPid, sRelId);
					}
				}
				this.log.debug("idx : {}", idx);
				this.log.debug("size : {}", listProdParam.size());

				// 앱코디 상품 리스트 조회
				mapReq.put("pidList", listProdParam);

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
							"Isf.Appguide.getAppguideProdList", mapReq, ProductBasicInfo.class);

					MetaInfo metaInfo = null;

					if (this.log.isDebugEnabled()) {
						this.log.debug("##### parameter cnt : {}", listProdParam.size());
						this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size());
						// productBasicInfoList.clear();
					}
					if (!productBasicInfoList.isEmpty()) {

						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("tenantHeader", tenantHeader);
						paramMap.put("deviceHeader", deviceHeader);
						paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING); // 판매중

						// Meta 정보 조회
						for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

							this.totalCount = productBasicInfo.getTotalCount();

							String topMenuId = productBasicInfo.getTopMenuId(); // 탑메뉴
							String svcGrpCd = productBasicInfo.getSvcGrpCd(); // 서비스 그룹 코드
							paramMap.put("productBasicInfo", productBasicInfo);

							Product product = null;

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
										|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
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
										|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic
																									  // 상품의
																									  // 경우

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
							} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의
																											   // 경우
								paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
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
					this.log.debug("total count : {}", this.totalCount);
					// productList.clear();
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
						mapRel.put("pidList", listRelProdParam);

						listRelProd = this.commonDAO.queryForList("Isf.Appguide.getRelProdList", mapRel, HashMap.class);
					}

					// 추천사유코드 Mapping
					boolean isRel = false;
					String reasonMessage = "";

					/*
					 * 앱코디 상품 조회 리스트를 돌리면서 추천 사유 메세지 만드는 작업을 한다. 순서는 처음에 저장한 mapRank 를 참조로 Index 생성하여 처리한다.
					 */
					List<Product> listAppCodiReason = new ArrayList<Product>(idx);
					for (int i = 0; i < idx; i++) {
						listAppCodiReason.add(null);
					}

					for (Product product : productList) {
						for (Identifier id : product.getIdentifierList()) {
							if (id.getType().equalsIgnoreCase(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD)) {
								sPid = id.getText();
								if (StringUtil.isNotEmpty(sPid))
									break;
							}
						}
						sRelId = mapRelProd.get(sPid);
						sReasonCode = mapReason.get(sPid);
						if ("02".equals(sReasonCode) || "03".equals(sReasonCode)) {
							sReasonCode = "04";
						}
						if (sReasonCode != null) {
							reasonMessage = IsfConstants.mapReasonCode.get(sReasonCode);
						}
						if (reasonMessage == null)
							reasonMessage = "";

						// 기존 두자리 사유 코드 처리 정책 (4자리로 바뀌면 의미는 없음)
						// 추천사유코드 : 01이고, 연관 상품이 있으면 구매 추천
						// 추천사유코드 : 01이고, 연관 상품이 없으면, Power User’s Best 추천
						// 추천사유코드 : 02이면, 무조건 Power User’s Best 추천
						// 추천사유코드 : 03이면 무조건 Beginner’s Best 추천
						// 연관상품 여부
						isRel = false;
						/* replace 해야하는 사유가 아니면 skip */
						if (reasonMessage.indexOf("$") > -1) {

							// 대상상품 대분류 카테고리명
							// 대상상품은 연관상품과 별개로 처리함
							String top_cat_nm_pid = "";
							for (Menu menu : product.getMenuList()) {
								if (!StringUtils.isEmpty(menu.getType())
										&& DisplayConstants.DP_MENU_TOPCLASS_TYPE.equals(menu.getType())) {
									top_cat_nm_pid = menu.getName();
								}
							}
							reasonMessage = StringUtils.replace(reasonMessage, "$4", top_cat_nm_pid);

							// 연관상품 정보가 있을 경우에 치환처리함
							if (!listRelProd.isEmpty()) {
								for (Map<String, Object> mapRelProdTemp : listRelProd) {
									if (StringUtil.isNotEmpty(sRelId)
											&& sRelId.equals(ObjectUtils.toString(mapRelProdTemp.get("PROD_ID")))) {
										// 연관상품명
										String prod_nm = (String) mapRelProdTemp.get("PROD_NM");
										// DA 팀에서 24 byte(한글 12자리)로 상품명을 조절해 달라고 요청함
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

						// 연관상품이 없을 경우에는 각각의 사유 메세지 정보를 셋팅
						// 단, 2자리 사유코드 01일 경우에는 04 코드로 사용한다.
						// 01일 경우에는 연관상품이 있어야 하나 없을 경우에...
						// 기타 4자리 코드인데 연관상품으로 치환되지 않았을 경우에
						// $1,2,3가 그냥 노출되기 때문에 임시로 04번 코드로 치환한다.
						if (!isRel && reasonMessage.indexOf("$") > -1) {
							reasonMessage = IsfConstants.mapReasonCode.get("04");
							product.setRecommendedReason(reasonMessage);
						} else {
							product.setRecommendedReason(reasonMessage);
						}

						if (this.log.isDebugEnabled()) {
							this.log.debug("reasonMessage : " + product.getRecommendedReason());
						}

						// ISF연동 정보시 저장한 순서를 가져와서 최종 List에 해당 Index로 add 한다.
						int index = mapRank.get(sPid);
						listAppCodiReason.set(index, product);
					}

					// 앱코디 상품리스트 조회 결과가 ISF 에서 넘겨주는 상품 정보보다 적을 수 있어서(판매중지 등의 사유)
					// 중간에 Index가 비는 문제가 발생할 수 있다. 그래서 아래와 같이 null element 삭제 로직 추가한다.
					listAppCodiReason.removeAll(Arrays.asList(new Object[] { null }));

					// Range 가 정의된 경우
					String sStartRow = StringUtils.defaultIfEmpty(ObjectUtils.toString(requestVO.getOffset()), "1");
					String sEndRow = ObjectUtils.toString(requestVO.getCount() + requestVO.getOffset() - 1);

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

					commonResponse.setTotalCount(this.totalCount);
					responseVO.setCommonResponse(commonResponse);
					responseVO.setProductList(listRes);
				}
			} else {
				isExists = false;
				/*
				 * ErrorInfo error = new ErrorInfo(); error.setCode("SAC_DSP_0010"); throw new
				 * StorePlatformException(error);
				 */
			}
		} catch (StorePlatformException se) {
			// isExists = false;
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			// isExists = false;
			ErrorInfo error = new ErrorInfo();
			error.setCode("SAC_DSP_0009");
			error.setMessage(e.getLocalizedMessage());
			throw new StorePlatformException(error);
		}

		// ISF 연동 실패나 Data 가 없는 경우( 운영자 추천으로 대체 )
		if (!isExists) {
			commonResponse.setTotalCount(this.totalCount);
			responseVO.setCommonResponse(commonResponse);
			responseVO.setProductList(productList);
			/*
			 * ErrorInfo error = new ErrorInfo(); error.setCode("SAC_DSP_0009"); throw new
			 * StorePlatformException(error);
			 */
			/*
			 * this.totalCount = 0;
			 * 
			 * this.log.info("ISF 연동 실패나 Data 가 없는 경우 - 운영자 추천으로 대체");
			 * 
			 * mapReq = new HashMap<String, Object>(); mapReq.put("START_ROW", requestVO.getOffset());
			 * mapReq.put("END_ROW", (requestVO.getOffset() + requestVO.getCount() - 1));
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
			 * 
			 * List<Appguide> appguideResultList = this.commonDAO.queryForList("Isf.Appguide.getAdminRecommandProdList",
			 * mapReq, Appguide.class);
			 * 
			 * productList = this.makeResultList(appguideResultList);
			 * 
			 * 
			 * // 상품 기본 정보 List 조회 - 운영자 추천 List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
			 * "Isf.AppCodi.getBasicAdminRecommandProdList", mapReq, ProductBasicInfo.class);
			 * 
			 * if (this.log.isDebugEnabled()) { this.log.debug("##### parameter cnt : {}", listProdParam.size());
			 * this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size()); } if
			 * (!productBasicInfoList.isEmpty()) {
			 * 
			 * Product product = null; MetaInfo metaInfo = null;
			 * 
			 * Map<String, Object> paramMap = new HashMap<String, Object>(); paramMap.put("tenantHeader", tenantHeader);
			 * paramMap.put("deviceHeader", deviceHeader); paramMap.put("prodStatusCd",
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
			 * this.responseInfoGenerateFacade.generateBroadcastProduct(metaInfo); } productList.add(product); } } else
			 * if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId) ||
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
			 * if (this.log.isDebugEnabled()) { this.log.debug("##### Search for music meta info product"); } metaInfo =
			 * this.metaInfoService.getMusicMetaInfo(paramMap); if (metaInfo != null) { product =
			 * this.responseInfoGenerateFacade.generateMusicProduct(metaInfo); productList.add(product); } } } else if
			 * (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
			 * paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD); paramMap.put("imageCd",
			 * DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
			 * 
			 * if (this.log.isDebugEnabled()) { this.log.debug("##### Search for Shopping  meta info product"); }
			 * metaInfo = this.metaInfoService.getShoppingMetaInfo(paramMap); if (metaInfo != null) { product =
			 * this.responseInfoGenerateFacade.generateShoppingProduct(metaInfo); productList.add(product); } } } }
			 * 
			 * if (this.log.isDebugEnabled()) { this.log.debug("product count : {}", productList.size());
			 * this.log.debug("total count : {}", this.totalCount); } commonResponse.setTotalCount(this.totalCount);
			 * responseVO.setCommonResponse(commonResponse); responseVO.setProductList(productList);
			 */
		}

		return responseVO;
	}

	private IsfEcReq makeRequest(AppguideIsfSacReq requestVO) {

		IsfEcReq request = new IsfEcReq();

		request.setId("SVC_RECM_0001");
		request.setMbn(requestVO.getUserKey());
		request.setMdn(requestVO.getDeviceId());
		request.setChCode("M");
		// request.setType("appguide");

		if (this.log.isDebugEnabled()) {
			this.log.debug(request.toString());
		}
		return request;
	}

	@SuppressWarnings("unused")
	private List<Product> makeResultList(List<Appguide> resultList) {

		List<Product> listVO = new ArrayList<Product>();

		for (Appguide mapper : resultList) {

			this.totalCount = mapper.getTotalCount();

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
			identifier = new Identifier();
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
					date.setText(DateUtils.parseDate(mapper.getVodSaleDt()));
					contributor.setDate(date);
					product.setContributor(contributor);

				} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapper.getTopMenuId())
						|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // Ebook / Comic

					contributor.setName(mapper.getBookArtistNm()); // 제목
					contributor.setPainter(mapper.getBookArtistNm2()); //
					contributor.setPublisher(mapper.getBookChnlCompNm()); // 출판사
					Date date = new Date();
					date.setText(DateUtils.parseDate(mapper.getBookSaleDt())); // 출판년도
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
			product.setRecommendedReason(IsfConstants.mapReasonCode.get("04"));

			listVO.add(product);

		} // end of for

		return listVO;
	}

}
