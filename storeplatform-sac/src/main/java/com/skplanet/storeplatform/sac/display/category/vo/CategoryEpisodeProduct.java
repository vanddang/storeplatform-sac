/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.category.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Updated on : 2015-08-03. Updated by : 양해엽, SK Planet.
 */
public class CategoryEpisodeProduct extends CommonInfo {

	private String channelId;
	private String episodeId;
	private String topMenuId;
	private String metaClsfCd;
	private String svcGrpCd;
	private String prodGrdCd;
	private String possLendClsfCd;
	private String prodStatusCd;
	private String songId;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	public String getTopMenuId() {
		return topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getMetaClsfCd() {
		return metaClsfCd;
	}

	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	public String getSvcGrpCd() {
		return svcGrpCd;
	}

	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	public String getProdGrdCd() {
		return prodGrdCd;
	}

	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	public String getPossLendClsfCd() {
		return possLendClsfCd;
	}

	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}

	public String getProdStatusCd() {
		return prodStatusCd;
	}

	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	public String getSongId() {
		return songId;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}
}
