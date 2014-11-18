/**
 * 
 */
package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.AlbumProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.AlbumProductSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 앨범별 음악 리스트
 * 
 * Updated on : 2014. 10. 23.
 * Updated by : 1002177
 */
public interface AlbumProductService {
	public AlbumProductSacRes searchAlbumProductList(String tenantId, String langCd, String deviceModelCd, String prodId);
}
