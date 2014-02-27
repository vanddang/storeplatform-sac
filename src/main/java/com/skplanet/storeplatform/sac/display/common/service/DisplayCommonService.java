package com.skplanet.storeplatform.sac.display.common.service;

import java.util.List;

import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;

/**
 * 전시 공통 서비스
 * 
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
public interface DisplayCommonService {

	/**
	 * 배치 완료 기준일시를 리턴.
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param listId
	 *            목록ID(혹은 배치ID)
	 * @return 기준일시를 yyyyMMddHHmmss 형태의 문자열로 리턴. 만족하는 데이터가 없는 경우 null
	 */
	public String getBatchStandardDateString(String tenantId, String listId);

	/**
	 * 메뉴 목록을 조회한다.
	 * 
	 * @param prodId
	 *            상품ID
	 * @param langCd
	 *            언어코드
	 * @return List<MenuItem>
	 */
	public List<MenuItem> getMenuItemList(String prodId, String langCd);

	/**
	 * 단말 해상도의 코드값을 조회한다.
	 * 
	 * @param resolution
	 *            해상도 값(480*720)
	 * @return String
	 */
	public String getResolutionCode(String resolution);

	/**
	 * DEVICE ID의 유형을 분류한다.
	 * 
	 * @param deviceId
	 *            deviceId
	 * @return String
	 */
	public String getDeviceIdType(String deviceId);

    /**
     * 특정 상품의 구매 내역 유무를 확인한다.
     * @param tenantId 테넌트ID
     * @param userKey 사용자키
     * @param deviceKey 디바이스키
     * @param episodeId 에피소드ID
     * @return 구매 내역이 있는 경우 true
     */
    public boolean checkPurchase(String tenantId, String userKey, String deviceKey, String episodeId);

}
