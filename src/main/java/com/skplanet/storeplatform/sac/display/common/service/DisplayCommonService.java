package com.skplanet.storeplatform.sac.display.common.service;

import java.util.List;

import com.skplanet.storeplatform.sac.display.common.vo.*;

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
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param userKey
	 *            사용자키
	 * @param deviceKey
	 *            디바이스키
	 * @param episodeId
	 *            에피소드ID
	 * @return 구매 내역이 있는 경우 true
	 */
	public boolean checkPurchase(String tenantId, String userKey, String deviceKey, String episodeId);

	/**
	 * 상품 목록의 구매 내역 유무를 확인한다.
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param userKey
	 *            사용자키
	 * @param deviceKey
	 *            디바이스키
	 * @param episodeIdList
	 *            에피소드ID List
	 * @return 구매 내역 List
	 */
	public com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes checkPurchaseList(String tenantId, String userKey, String deviceKey,
                                                                                                         List<String> episodeIdList);

	/**
	 * 미리보기 URL을 작성한다.
	 * 
	 * @param phyPath
	 *            파일의 물리 경로
	 * @return
	 */
	public String makePreviewUrl(String phyPath);

	/**
	 * 메뉴에 해당하는 T멤버십 할인율 조회한다.
	 * @param tenantId
	 * @param topMenuId
	 * @return
	 */
	public TmembershipDcInfo getTmembershipDcRateForMenu(String tenantId, String topMenuId);

	/**
	 * <pre>
	 * 단말 지원정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 * @return HashMap
	 */
	public SupportDevice getSupportDeviceInfo(String deviceModelCd);
	
	/**
	 * <pre>
	 * 이북/코믹 Chapter Unit
	 * </pre>
	 * 
	 * @param bookClsfCd
	 * @return String
	 * 			chapterUnit
	 */
	public String getEpubChapterUnit(String bookClsfCd);
	
	/**
	 * <pre>
	 * Vod Chapter Unit
	 * </pre>
	 * 
	 * @return String
	 * 			chapterUnit
	 */	
	public String getVodChapterUnit();

    /**
     * 업데이트 히스토리 갯수 조회
     * @param channelId
     * @param offset
     * @param count
     * @return
     */
    public List<UpdateHistory> getUpdateList(String channelId, Integer offset, Integer count);

    /**
     * 업데이트 히스토리의 전체 갯수 조회
     * @param channelId
     * @return
     */
    public int getUpdateCount(String channelId);

    /**
     * prodId로 상품 유형을 조회한다.
     * @param prodId
     * @return
     */
    ProductInfo getProductInfo(String prodId);

    /**
     * 상품 유형 정보를 조회한다.
     * @param svcGrp
     * @param svcTp
     * @param metaClsf
     * @param topMenu
     * @return
     */
    ProductTypeInfo getProductTypeInfo(String svcGrp, String svcTp, String metaClsf, String topMenu);

}
