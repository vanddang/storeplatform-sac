package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.vo.SaveAndSync;

/**
 * Save & Sync 관련 인터페이스
 * 
 * Updated on : 2014. 3. 7. Updated by : 심대진, 다모아 솔루션.
 */
public interface SaveAndSyncService {

	/**
	 * <pre>
	 * 변동성 대상 체크.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param deviceId
	 *            기기 ID
	 * @return SaveAndSync
	 */
	public SaveAndSync checkSaveAndSync(SacRequestHeader sacHeader, String deviceId);

}
