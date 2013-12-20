/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.menu;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.product.vo.MenuListResProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * Interface Message Menu List Value Object.
 * 
 * Updated on : 2013. 12. 19 Updated by : 윤주영, SK 플래닛.
 */
@ProtobufMapping(MenuListResProto.MenuListResult.class)
public class MenuListResponseVO extends CommonVO implements Serializable {

	private static final long serialVersionUID = 11123123125L;

	private CommonResponse commonRes;
	private List<MenuDetailVO> menuList;

	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	public List<MenuDetailVO> getMenuList() {
		return this.menuList;
	}

	public void setMenuList(List<MenuDetailVO> menuList) {
		this.menuList = menuList;
	}
}
