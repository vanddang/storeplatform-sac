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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 음원 상품 전용 정보 Generator
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
public interface MusicInfoGenerator {
	/**
	 * <pre>
	 * 음원 상품 전용 Accrual 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Accrual
	 */
	public Accrual generateAccrual(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 음원 상품 전용 Service 객체 생성.
	 * </pre>
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            type
	 * @return Service
	 */
	public Service generateService(String name, String type);

	/**
	 * <pre>
	 * 음원 상품 전용 Service 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Music
	 */
	public Music generateMusic(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 음원 상품 전용 Service 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Service>
	 */
	public List<Service> generateServiceList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 음원 상품 전용 Contributor 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Contributor
	 */
	public Contributor generateContributor(MetaInfo metaInfo);
}
