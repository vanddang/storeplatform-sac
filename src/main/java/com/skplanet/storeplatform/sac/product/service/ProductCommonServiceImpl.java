/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.ErrorMessageBuilder;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.common.ProductCommonRequest;
import com.skplanet.storeplatform.sac.client.product.vo.common.ProductCommonResponse;
import com.skplanet.storeplatform.sac.product.vo.ProductCommonDTO;

@Service
@Transactional
public class ProductCommonServiceImpl implements ProductCommonService {
	private transient Logger logger = LoggerFactory.getLogger(ProductCommonServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.product.service.ProductCommonService#searchMenuInfo(java.lang.Object)
	 */
	@Override
	public ProductCommonResponse searchMenuInfo(Object ob) {
		ProductCommonResponse response = new ProductCommonResponse();
		response.setErrorCode("01");

		try {
			String menuId = null;
			String tenantId = null;
			String systemId = null;
			String methodName = null;
			Method methods[] = ob.getClass().getMethods();

			for (int i = 0; i < methods.length; i++) {
				methodName = methods[i].getName();

				if (methods[i].getName().startsWith("get")) {
					if ("getMenuId".equals(methodName)) {
						menuId = (String) methods[i].invoke(ob);
					} else if ("getTenantId".equals(methodName)) {
						tenantId = (String) methods[i].invoke(ob);
					} else if ("getSystemId".equals(methodName)) {
						systemId = (String) methods[i].invoke(ob);
					}
				}

				if (StringUtils.isNotEmpty(menuId) && StringUtils.isNotEmpty(tenantId)
						&& StringUtils.isNotEmpty(systemId)) {
					break;
				}
			}

			this.logger.debug("--------------------------------------------------------------------------");
			this.logger.debug("### menuId : {}", menuId);
			this.logger.debug("### tenantId : {}", tenantId);
			this.logger.debug("### systemId : {}", systemId);
			this.logger.debug("--------------------------------------------------------------------------");

			// Request parameter
			ProductCommonRequest params = new ProductCommonRequest();
			params.setMenuId(menuId);
			params.setTenantId(tenantId);
			params.setSystemId(systemId);

			// 메뉴 조회
			ProductCommonDTO dto = this.commonDAO.queryForObject("ProductCommon.selectMenuInfo", params,
					ProductCommonDTO.class);

			if (dto != null) {
				response.setMenuId(dto.getMenuId());
				response.setMenuNm(dto.getMenuNm());
				response.setUpMenuId(dto.getUpMenuId());
				response.setUpMenuNm(dto.getUpMenuNm());
				response.setErrorCode("00");
			}
		} catch (Exception e) {
			throw new StorePlatformException(ErrorMessageBuilder.create().defaultMessage(e.getMessage()).build(), e);
		}

		return response;
	}
}
