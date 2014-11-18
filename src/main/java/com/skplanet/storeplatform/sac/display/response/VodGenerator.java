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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

import java.util.List;

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
     *
	 * @return Vod
	 */
	public Vod generateVod(MetaInfo metaInfo);

    /**
     * VOD 상품 전용 Vod 객체 생성.
     * FHD 지원 여부 파라미터 추가
     * @param metaInfo
     * @param supportFhdVideo FHD 지원 여부
     * @return
     */
	public Vod generateVod(MetaInfo metaInfo, boolean supportFhdVideo);

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


    /**
     * 일반 화질 VideoInfo 객체 생성
     * @param metaInfo
     * @return
     */
    public VideoInfo getNmVideoInfo(MetaInfo metaInfo);
    /**
     * SD 화질 VideoInfo 객체 생성
     * @param metaInfo
     * @return
     */
    public VideoInfo getSdVideoInfo(MetaInfo metaInfo);
    /**
     * HD 화질 VideoInfo 객체 생성
     * @param metaInfo
     * @return
     */
    public VideoInfo getHdVideoInfo(MetaInfo metaInfo);
    /**
     * FHD 화질 VideoInfo 객체 생성
     * @param metaInfo
     * @return
     */
    public VideoInfo getFhdVideoInfo(MetaInfo metaInfo);

    /**
     * VOD 가격
     * @param metaInfo
     * @return
     */
	public Price generateVodPrice(MetaInfo metaInfo);
}
