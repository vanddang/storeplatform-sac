/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.sacservice.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

/**
 * SacServiceDataService 클래스
 *
 * Created on 2014. 06. 02. by 서대영, SK플래닛 : DB 셋업이 될 때 까지 임시 메모리 Map으로 구현
 * Updated on 2014. 07. 16. by 서대영, SK플래닛 : 마일리지 지원 여부 추가
 * Updated on 2014. 08. 27. by 서대영, SK플래닛 : 임시 Map에서 DB 조회 방식으로 변경 + 캐시 적용
 */
@Service
public class SacServiceDataServiceImpl implements SacServiceDataService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	@Cacheable(value = "sac:other:sacservice:selectService")
	@Override
	public SacService selectService(String serviceCd) {
		return commonDAO.queryForObject("SacService.selectOne", serviceCd, SacService.class);
	}

	@Cacheable(value = "sac:other:sacservice:selectSimOperatorList")
	@Override
	public List<String> selectSimOperatorList(String serviceCd) {
		List<String> list = commonDAO.queryForList("SacService.selectSimOperatorList", serviceCd, String.class);
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}

	@Cacheable(value = "sac:other:sacservice:selectModelList")
	@Override
	public List<String> selectModelList(String serviceCd) {
		List<String> list = commonDAO.queryForList("SacService.selectModelList", serviceCd, String.class);
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}

    @CacheEvict(value = {
    		"sac:other:sacservice:selectService",
    		"sac:other:sacservice:selectSimOperatorList",
    		"sac:other:sacservice:selectModelList"},
    		allEntries = true)
	@Override
	public void flushCache() {
	}


}
