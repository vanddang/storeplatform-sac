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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TimeVO;

/**
 * Interface Message Vod Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(VodProto.Vod.class)
public class VodVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private TimeVO runningTime; // Play시간
	private ChapterVO chapter; // 회차
	private VideoInfoVO videoInfo; // 화질별 video 정보
	private VodExplainVO vodExplain; // vod 정보

	public TimeVO getRunningTime() {
		return this.runningTime;
	}

	public void setRunningTime(TimeVO runningTime) {
		this.runningTime = runningTime;
	}

	public ChapterVO getChapter() {
		return this.chapter;
	}

	public void setChapter(ChapterVO chapter) {
		this.chapter = chapter;
	}

	public VideoInfoVO getVideoInfo() {
		return this.videoInfo;
	}

	public void setVideoInfo(VideoInfoVO videoInfo) {
		this.videoInfo = videoInfo;
	}

	public VodExplainVO getVodExplain() {
		return this.vodExplain;
	}

	public void setVodExplain(VodExplainVO vodExplain) {
		this.vodExplain = vodExplain;
	}
}
