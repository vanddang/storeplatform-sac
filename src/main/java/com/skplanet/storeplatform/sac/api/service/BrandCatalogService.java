package com.skplanet.storeplatform.sac.api.service;

import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;

public interface BrandCatalogService {
	/**
	 * <pre>
	 * 브랜드 정보 입력
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public void insertBrandInfo(DpBrandInfo brandInfo);

	/**
	 * <pre>
	 * 브랜드 정보(brandId)가져오기
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public String getCreateBrandId(String brandId);

	/**
	 * <pre>
	 * 브랜드 정보 수정
	 * </pre>
	 * 
	 * @
	 * 
	 */

	public void updateBrandInfo(DpBrandInfo brandInfo);

	/**
	 * <pre>
	 * 상품 이미지 저장
	 * </pre>
	 * 
	 * @
	 * 
	 */

	public void insertTblDpProdImg(BrandCatalogProdImgInfo brandCatalogProdImgInfo);

	/**
	 * <pre>
	 * 카탈로그 정보 입력
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public void insertCatalogInfo(DpCatalogInfo catalogInfo);

	/**
	 * <pre>
	 * 카탈로그 정보 수정
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public void updateCatalogInfo(DpCatalogInfo catalogInfo);

	/**
	 * <pre>
	 * 카탈로그 정보(catalogId)가져오기
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public String getCreateCatalogId(String catalogId);

	/**
	 * <pre>
	 * 태그 정보 삭제
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public void deleteTblTagInfo(String catalogID);

	/**
	 * <pre>
	 * 카탈로그 정보 수정
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public void insertTblTagInfo(DpCatalogTagInfo catalogInfo);

	/**
	 * <pre>
	 * 이미지 파일 삭제
	 * </pre>
	 * 
	 * @
	 * 
	 */
	public void deleteDpProdImg(String brandID);

}
