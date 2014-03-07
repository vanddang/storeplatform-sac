/**
 * 
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppDetailRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * App 상세 정보 요청(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2014. 3. 6. Updated by : 오승민, 인크로스.
 */
@Service
public class SellerAppDetailServiceImpl implements SellerAppDetailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.openapi.service.SellerAppDetailService#getSellerAppDetail(com.skplanet
	 * .storeplatform.sac.client.display.vo.openapi.SellerAppDetailReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public SellerAppDetailRes getSellerAppDetail(SellerAppDetailReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub
		return null;
	}

}
