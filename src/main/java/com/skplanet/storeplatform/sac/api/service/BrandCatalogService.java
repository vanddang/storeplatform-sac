package com.skplanet.storeplatform.sac.api.service;

import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;

/**
 * <pre>
 * 브랜드 카탈로그 서비스 인터페이스
 * </pre>
 * 
 * Created on : 2014-01-03 Created by : 김형식, SK 플래닛. Last Updated on : 2014-01-03 Last Updated by : 김형식, SK 플래닛
 */
public interface BrandCatalogService {

	/**
	 * <pre>
	 * 브랜드 정보(brandId) SEQ 가져오기.
	 * </pre>
	 * 
	 * @return String
	 */
	public String searchCreateBrandId();

	/**
	 * <pre>
	 * 브랜드 정보 입력.
	 * </pre>
	 * 
	 * @param brandInfo
	 *            brandInfo
	 * 
	 */
	public void insertBrandInfo(DpBrandInfo brandInfo);

	/**
	 * <pre>
	 * 브랜드 정보(brandId)가져오기.
	 * </pre>
	 * 
	 * @param brandId
	 *            brandId
	 * @return String
	 */
	public String getCreateBrandId(String brandId);

	/**
	 * <pre>
	 * 브랜드 정보 수정.
	 * </pre>
	 * 
	 * @param brandInfo
	 *            brandInfo
	 * @return
	 */

	public void updateBrandInfo(DpBrandInfo brandInfo);

	/**
	 * <pre>
	 * 상품 이미지 저장.
	 * </pre>
	 * 
	 * @param brandCatalogProdImgInfo
	 *            brandCatalogProdImgInfo
	 * @return
	 */

	public void insertTblDpProdImg(BrandCatalogProdImgInfo brandCatalogProdImgInfo);

	/**
	 * <pre>
	 * 브랜드 정보(catalogId) SEQ 가져오기.
	 * </pre>
	 * 
	 * @return String
	 */
	public String searchCreateCatalogId();

	/**
	 * <pre>
	 * 카탈로그 정보 입력.
	 * </pre>
	 * 
	 * @param catalogInfo
	 *            catalogInfo
	 * @return
	 */
	public void insertCatalogInfo(DpCatalogInfo catalogInfo);

	/**
	 * <pre>
	 * 카탈로그 정보 수정.
	 * </pre>
	 * 
	 * @param catalogInfo
	 *            catalogInfo
	 * @return
	 */
	public void updateCatalogInfo(DpCatalogInfo catalogInfo);

	/**
	 * <pre>
	 * 카탈로그 정보(catalogId)가져오기.
	 * </pre>
	 * 
	 * @param catalogId
	 *            catalogId
	 * @return String
	 */
	public String getCreateCatalogId(String catalogId);

	/**
	 * <pre>
	 * 태그 정보 삭제.
	 * </pre>
	 * 
	 * @param cid
	 *            cid
	 * @return
	 */
	public void deleteTblTagInfo(String cid);

	/**
	 * <pre>
	 * 태그 정보 저장.
	 * </pre>
	 * 
	 * @param tagInfo
	 *            tagInfo
	 * @return
	 */
	public void insertTblTagInfo(DpCatalogTagInfo tagInfo);

	/**
	 * <pre>
	 * 이미지 파일 삭제.
	 * </pre>
	 * 
	 * @param brandID
	 *            brandID
	 * @return
	 */
	public void deleteDpProdImg(String brandID);

}
