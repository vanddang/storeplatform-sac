/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.common.vo.MenuProto;

/**
 * Interface Message Menu Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(MenuProto.Menu.class)
public class MenuVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 전시 메뉴ID type에 따른 ID
	private String name; // 전시 메뉴ID에 따른 설명
	private String type; // 전시 메뉴 타입
	private String count; // 전시 메뉴 상품수
	private SourceVO source; // 전시 메뉴의 graphic resource가 있을 경우 정의

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCount() {
		return this.count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public SourceVO getSource() {
		return this.source;
	}

	public void setSource(SourceVO source) {
		this.source = source;
	}
}
