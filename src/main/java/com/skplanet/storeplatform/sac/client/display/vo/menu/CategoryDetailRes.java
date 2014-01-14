/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;

/**
 * 카테고리 조회 Value Object. 최대 중-세 카테고리 정보 표현 Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
public interface CategoryDetailRes extends Serializable {

	public static final long serialVersionUID = 11123123111L;

	public CommonResponse commonResponse = new CommonResponse();

	public Menu category = new Menu();

	public List categoryList = new ArrayList();

	public CommonResponse getCommonResponse();

	public void setCommonResponse(CommonResponse commonResponse);

	public Menu getCategory();

	public void setCategory(Menu category);

	public List getCategoryList();

	public void setCategoryList(List categoryList);
}
