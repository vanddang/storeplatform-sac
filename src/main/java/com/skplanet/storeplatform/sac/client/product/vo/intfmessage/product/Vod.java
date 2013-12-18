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

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.VodProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;

/**
 * Interface Message Vod Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(VodProto.Vod.class)
public class Vod extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Time runningTime; // Play시간
	private Chapter chapter; // 회차
	private VideoInfo videoInfo; // 화질별 video 정보
	private VodExplain vodExplain; // vod 정보

	public Time getRunningTime() {
		return this.runningTime;
	}

	public void setRunningTime(Time runningTime) {
		this.runningTime = runningTime;
	}

	public Chapter getChapter() {
		return this.chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public VideoInfo getVideoInfo() {
		return this.videoInfo;
	}

	public void setVideoInfo(VideoInfo videoInfo) {
		this.videoInfo = videoInfo;
	}

	public VodExplain getVodExplain() {
		return this.vodExplain;
	}

	public void setVodExplain(VodExplain vodExplain) {
		this.vodExplain = vodExplain;
	}
}
