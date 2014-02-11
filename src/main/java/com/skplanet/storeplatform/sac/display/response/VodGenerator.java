/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.response;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * VOD 상품 전용 정보 Generator
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
public interface VodGenerator {
	/**
	 * <pre>
	 * 방송 상품 전용 Contributor 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Contributor
	 */
	public Contributor generateBroadcastContributor(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 영화 상품 전용 Contributor 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Contributor
	 */
	public Contributor generateMovieContributor(MetaInfo metaInfo);

	/**
	 * <pre>
	 * VOD 상품 전용 Vod 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Vod
	 */
	public Vod generateVod(MetaInfo metaInfo);

	/**
	 * <pre>
	 * VOD 상품 전용 Support List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Support>
	 */
	public List<Support> generateSupportList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * VOD 상품 전용 Rights 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Rights
	 */
	public Rights generateRights(MetaInfo metaInfo);

	/**
	 * <pre>
	 * VOD 상품 전용 videoInfo 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Vod
	 */
	public List<VideoInfo> generateVideoInfoList(MetaInfo metaInfo);

}
