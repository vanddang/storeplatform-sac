/**
 * 
 */
package com.skplanet.storeplatform.sac.display.album.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.album.AlbumDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.album.AlbumListByArtistRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.cache.service.ProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMetaParam;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 14.
 * Updated by : 1002177
 */

@Service
public class AlbumServiceImpl implements AlbumService{

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	@Autowired
	private ProductInfoManager productInfoManager;
	
	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;
	
	@Override
	public AlbumDetailRes getAlbumDetail(String tenantId, String prodId, String langCd, String userKey) {	
		AlbumMetaParam param = new AlbumMetaParam();
		param.setProdId(prodId);
		param.setLangCd(langCd);
		param.setTenantId(tenantId);
		param.setUserKey(userKey);
		
		AlbumMeta albumMeta = productInfoManager.getAlbumMeta(param, false);
		if (albumMeta == null) {
			return null;
		}
		
		AlbumDetailRes res = new AlbumDetailRes();
		res.setProduct(responseInfoGenerateFacade.generateAlbumDetailProduct(albumMeta));
		
		return res;
	}


	@Override
	public AlbumListByArtistRes getAlbumListByArtist(String artistId, String langCd) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("artistId", artistId);
		params.put("langCd", langCd);
		params.put("imgCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		
		List<AlbumMeta> albumMetaList = this.commonDAO.queryForList("AlbumListByArtist.albumList", params, AlbumMeta.class);
		AlbumListByArtistRes res = new AlbumListByArtistRes();
		List<Product> albums = new LinkedList<Product>();
		for (AlbumMeta albumMeta : albumMetaList) {
			albums.add(responseInfoGenerateFacade.generateAlbumProduct(albumMeta));
		}
		res.setProductList(albums);
		return res;
	}

}
