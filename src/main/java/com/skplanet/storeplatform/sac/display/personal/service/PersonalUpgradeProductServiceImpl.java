/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpgradeProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpgradeProductRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 업데이트 대상 목록 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
@Service
public class PersonalUpgradeProductServiceImpl implements PersonalUpgradeProductService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.personal.service.PersonalUpgradeProductService#searchUpdateProductList
	 * (com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpgradeProductReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public PersonalUpgradeProductRes searchUpdateProductList(PersonalUpgradeProductReq req, SacRequestHeader header) {
		PersonalUpgradeProductRes res = new PersonalUpgradeProductRes();
		CommonResponse commonResopnse = new CommonResponse();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		String memberType = req.getMemberType();
		/**************************************************************
		 * Package 명으로 상품 조회
		 **************************************************************/
		String sArrPkgNm[] = StringUtils.split(req.getPackageInfo(), ",");
		List<String> listPkgNm = new ArrayList<String>();
		for (String s : sArrPkgNm) {
			listPkgNm.add(StringUtils.split(s, "/")[0]);
		}

		// Oracle SQL 리터럴 수행 방지를 위한 예외처리
		int iListPkgSize = listPkgNm.size();
		int iPkgLimited = 0;
		if (iListPkgSize < 300) {
			iPkgLimited = 300;
		} else if (iListPkgSize >= 300 && iListPkgSize < 500) {
			iPkgLimited = 500;
		} else if (iListPkgSize >= 500 && iListPkgSize < 700) {
			iPkgLimited = 700;
		} else if (iListPkgSize >= 700 && iListPkgSize < 1000) {
			iPkgLimited = 1000;
		}
		for (int i = iListPkgSize; i < iPkgLimited; i++) {
			listPkgNm.add("");
		}
		mapReq.put("PKG_LIST", listPkgNm);

		// List<Object> listPkg = queryForList("updateAlarm.getRecentFromPkgNm", mapReq);
		List<Map> listPkg = this.commonDAO.queryForList("PersonalUpgradeProduct.searchRecentFromPkgNm", mapReq,
				Map.class);
		mapReq.remove("PKG_LIST");

		if (listPkg.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0006");
		} else {

			List<Object> listProd = new ArrayList<Object>();
			List<String> listPid = new ArrayList<String>();

			/**************************************************************
			 * Version Code 가 높은 Data 만 추출
			 **************************************************************/
			int iPkgVerCd = 0;
			String sPkgNm = "";
			String sFakeYn = "";
			Map<String, Object> mapPkg = null;

			for (int i = 0; i < listPkg.size(); i++) {
				mapPkg = listPkg.get(i);

				mapPkg.put("APK_VER_CD_TMP", mapPkg.get("APK_VER"));

				sPkgNm = ObjectUtils.toString(mapPkg.get("APK_PKG_NM"));
				sFakeYn = ObjectUtils.toString(mapPkg.get("FAKE_YN"));
				iPkgVerCd = NumberUtils.toInt(ObjectUtils.toString(mapPkg.get("APK_VER")));

				String sArrPkgInfo[] = null;
				int iReqPkgVerCd = 0;

				for (String s : sArrPkgNm) {
					sArrPkgInfo = StringUtils.split(s, "/");
					if (sPkgNm.equals(sArrPkgInfo[0])) {
						iReqPkgVerCd = NumberUtils.toInt(sArrPkgInfo[1]);

						// 단말에서 올라온 VERSION_CODE, installer 셋팅
						mapPkg.put("REQ_APK_VER_CD", sArrPkgInfo[1]);
						mapPkg.put("REQ_INSTALLER", ((sArrPkgInfo.length > 2) ? sArrPkgInfo[2] : ""));

						if (sFakeYn.equals("Y")) {
							// Fake Update 대상일 경우( 단말 Version <= 서버 Version )
							if (iPkgVerCd >= iReqPkgVerCd) {
								// Version 이 동일할 경우 +1
								if (iPkgVerCd == iReqPkgVerCd) {
									mapPkg.put("APK_VER_CD", Integer.toString((iPkgVerCd + 1)));
								}
								listProd.add(mapPkg);
								listPid.add(ObjectUtils.toString(mapPkg.get("PROD_ID")));
							}
						} else {
							// 일반 업데이트 대상 ( 단말 Version < 서버 Version )
							if (iPkgVerCd > iReqPkgVerCd) {
								listProd.add(mapPkg);
								listPid.add(ObjectUtils.toString(mapPkg.get("PROD_ID")));
							}
						}
						break;
					}
				}
			}

			/**************************************************************
			 * 업데이트 목록 가공
			 **************************************************************/
			List<Object> listUpdate = new ArrayList<Object>();
			// ◆ 회원
			// - 구매내역 존재 : 유료상품만 포함
			// - 구매내역 미존재 : 무료상품만 포함
			// ◆ 비회원
			// - 전체상품 포함
			if (!listPid.isEmpty()) {

				if (memberType.equals("updatedList")) {

					// Oracle SQL 리터럴 수행 방지를 위한 예외처리
					int iListPidSize = listPid.size();
					int iPidLimited = 0;
					if (iListPidSize < 300) {
						iPidLimited = 300;
					} else if (iListPidSize >= 300 && iListPidSize < 500) {
						iPidLimited = 500;
					} else if (iListPidSize >= 500 && iListPidSize < 700) {
						iPidLimited = 700;
					} else if (iListPidSize >= 700 && iListPidSize < 1000) {
						iPidLimited = 1000;
					}
					for (int i = iListPidSize; i < iPidLimited; i++) {
						listPid.add("");
					}

					// 회원의 업데이트 목록 가공
					mapReq.put("PID_LIST", listPid);

					// TODO osm1021 기구매 로직으로 변환 필요
					// List<Object> listPrchs = queryForList("updateAlarm.getPrchsInfo", mapReq);
					List<Object> listPrchs = null;
					mapReq.remove("PID_LIST");

					String sProdAmt = "";
					if (!listPrchs.isEmpty()) {

						int iReqPkgVerCd = 0;
						String sPid = "";
						String sInstaller = "";

						for (int i = 0; i < listProd.size(); i++) {

							mapPkg = (Map<String, Object>) listProd.get(i);
							sPid = ObjectUtils.toString(mapPkg.get("PROD_ID"));
							sFakeYn = ObjectUtils.toString(mapPkg.get("FAKE_YN"));
							sProdAmt = ObjectUtils.toString(mapPkg.get("PROD_AMT"));
							iPkgVerCd = NumberUtils.toInt(ObjectUtils.toString(mapPkg.get("APK_VER_CD_TMP")));
							iReqPkgVerCd = NumberUtils.toInt(ObjectUtils.toString(mapPkg.get("REQ_APK_VER_CD")));
							sInstaller = ObjectUtils.toString(mapPkg.get("REQ_INSTALLER"));

							// 구매내역이 존재하는 경우 구매 ID Setting
							boolean isPrchsExists = false;
							Map<String, String> mapPrchs = null;
							for (int j = 0; j < listPrchs.size(); j++) {
								mapPrchs = (Map<String, String>) listPrchs.get(j);
								if (sPid.equals(mapPrchs.get("PROD_ID"))) {
									// 알림설정 미해제인 경우 구매ID Setting
									if (StringUtils.isEmpty(mapPrchs.get("ALARM_OFF_DT"))) {
										// 구매내역 존재 시 업데이트 목록 가공
										// ① 서버 Version > 단말 Version 인 경우
										// ② 서버 Version = 단말 Version 인 경우
										// Fake Update 상품이고 Google Play에서 설치(installer가 'com.android.vending') 했으면 업데이트
										// 대상
										if (iPkgVerCd > iReqPkgVerCd) {
											mapPkg.put("PRCHS_ID", mapPrchs.get("PRCHS_ID"));
										} else if (iPkgVerCd == iReqPkgVerCd) {
											if (sFakeYn.equals("Y") && sInstaller.equals("com.android.vending")) {
												mapPkg.put("PRCHS_ID", mapPrchs.get("PRCHS_ID"));
											} else {
											}
											mapPkg.clear();
										} else {
											mapPkg.clear();
										}
									} else {
										mapPkg.clear();
									}

									isPrchsExists = true;
									break;
								}
							}

							// 구매내역이 존재하지 않는 경우
							if (!isPrchsExists) {
								// 유료상품은 구매내역이 없으면 업데이트 목록에서 제외
								if (!sProdAmt.equals("0")) {
									mapPkg.clear();
								} else {
									// Fake Update 대상일 경우 FUPDATE+상품ID 로 구매ID 생성
									if (sFakeYn.equals("Y")) {
										mapPkg.put("PRCHS_ID", "FUPDATE" + sPid);
									}
								}
							}

							if (!mapPkg.isEmpty()) {
								listUpdate.add(mapPkg);
							}
						}

					} else {

						// 구매내역이 없을 경우 무료상품만 리스트에 포함
						for (int i = 0; i < listProd.size(); i++) {
							mapPkg = (Map<String, Object>) listProd.get(i);
							sProdAmt = ObjectUtils.toString(mapPkg.get("PROD_AMT"));
							if (sProdAmt.equals("0")) {
								sFakeYn = ObjectUtils.toString(mapPkg.get("FAKE_YN"));
								// Fake Update 대상일 경우 FUPDATE+상품ID 로 구매ID 생성
								if (sFakeYn.equals("Y")) {
									mapPkg.put("PRCHS_ID", "FUPDATE" + mapPkg.get("PROD_ID"));
								}
								listUpdate.add(mapPkg);
							}
						}

					}

				} else if (memberType.equals("updatedListForGuest")) {
					// 비회원일 경우 전체 정보 리스트에 포함
					listUpdate.addAll(listProd);
				}

				if (listUpdate.isEmpty()) {
					throw new StorePlatformException("SAC_DSP_0006");
				}

				// TODO osm1021 range는 일단 삭제 한다.

				// range 가 지정된 경우
				// String sStartRow = ObjectUtils.toString(mapReq.get(Consts.HTTP_PARAM_START_ROW));
				// String sEndRow = ObjectUtils.toString(mapReq.get(Consts.HTTP_PARAM_END_ROW));
				// List<Object> listRes = new ArrayList<Object>();
				// if (StringUtils.isNotEmpty(sEndRow)) {
				// int iStartRow = NumberUtils.toInt(sStartRow) - 1;
				// int iEndRow = NumberUtils.toInt(sEndRow);
				// if (iEndRow > iStartRow) {
				// int iUpdateListSize = listUpdate.size();
				// iStartRow = (iStartRow < 0) ? 0 : iStartRow;
				// iEndRow = (iEndRow > iUpdateListSize) ? iUpdateListSize : iEndRow;
				//
				// // range 재설정
				// mapReq.put(Consts.HTTP_PARAM_START_ROW, Integer.toString(iStartRow + 1));
				// mapReq.put(Consts.HTTP_PARAM_END_ROW, Integer.toString(iEndRow));
				//
				// listRes.addAll(listUpdate.subList(iStartRow, iEndRow));
				// } else {
				// listRes.addAll(listUpdate);
				// }
				// } else {
				// listRes.addAll(listUpdate);
				// }

				// TOTAL_COUNT 예외처리

			} else {
				throw new StorePlatformException("SAC_DSP_0006");
			}
		}

		return res;
	}
}
