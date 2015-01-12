/**
 * 
 */
package com.skplanet.storeplatform.sac.display.album.service;


import com.skplanet.storeplatform.sac.client.display.vo.album.AlbumDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.album.AlbumListByArtistRes;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 14.
 * Updated by : 1002177
 */
public interface AlbumService {
	public AlbumDetailRes getAlbumDetail(String tenantId, String prodId, String langCd, String userKey);
	public AlbumListByArtistRes getAlbumListByArtist(String artistId, String langCd);
}
