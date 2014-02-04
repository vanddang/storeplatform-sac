package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.Map;

import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * Meta 정보 조회 Service.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
public interface MetaInfoService {

	/**
	 * <pre>
	 * App Meta 정보 조회.
	 * </pre>
	 * 
	 * @param paramMap
	 *            paramMap
	 * @return MetaInfo
	 */
	public MetaInfo getAppMetaInfo(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 음원 Meta 정보 조회.
	 * </pre>
	 * 
	 * @param paramMap
	 *            paramMap
	 * @return MetaInfo
	 */
	public MetaInfo getMusicMetaInfo(Map<String, Object> paramMap);

	// TODO osm1021 review cache 문제가 없다면 VO를 파라미터로 추가 (일단 추가)
	/**
	 * <pre>
	 * VOD Meta 정보 조회.
	 * </pre>
	 * 
	 * @param paramMap
	 *            paramMap
	 * @return MetaInfo
	 */
	public MetaInfo getVODMetaInfo(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * Ebook Meta 정보 조회.
	 * </pre>
	 * 
	 * @param paramMap
	 *            paramMap
	 * @return MetaInfo
	 */
	public MetaInfo getEbookComicMetaInfo(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * Webtoon Meta 정보 조회.
	 * </pre>
	 * 
	 * @param paramMap
	 *            paramMap
	 * @return MetaInfo
	 */
	public MetaInfo getWebtoonMetaInfo(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 쇼핑 Meta 정보 조회.
	 * </pre>
	 * 
	 * @param paramMap
	 *            paramMap
	 * @return MetaInfo
	 */
	public MetaInfo getShoppingMetaInfo(Map<String, Object> paramMap);
}
