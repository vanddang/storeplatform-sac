/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.LayoutProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.MenuVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.SourceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TitleVO;

/**
 * Interface Message Layout Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(LayoutProto.Layout.class)
public class LayoutVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name; // 베너이름
	private List<BannerVO> bannerList; // 베너리스트
	private TitleVO title; // 베너제목
	private SourceVO source; // 베너 resource 정보
	private MenuVO menu; // 메뉴 정보

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BannerVO> getBannerList() {
		return this.bannerList;
	}

	public void setBannerList(List<BannerVO> bannerList) {
		this.bannerList = bannerList;
	}

	public TitleVO getTitle() {
		return this.title;
	}

	public void setTitle(TitleVO title) {
		this.title = title;
	}

	public SourceVO getSource() {
		return this.source;
	}

	public void setSource(SourceVO source) {
		this.source = source;
	}

	public MenuVO getMenu() {
		return this.menu;
	}

	public void setMenu(MenuVO menu) {
		this.menu = menu;
	}
}
