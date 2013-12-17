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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.RightsProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;

/**
 * Interface Message Rights Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(RightsProto.Rights.class)
public class RightsVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String allow; // 이용권한 (freepass : 자유이용권 지원, domestic : 국내만 지원, feedback : 사용후기 가능)
	private String deny; // 이용금지 권한 (multiAccess 여러 개의 단말에서 이용 금지, access : 이 단말에서 사용 금지)
	private String grade; // 이용등급 (0 : 전체이용가, 1 : 12세 이용가, 2 : 15세 이용가, 4 : 청소년사용불가)
	private PreviewVO preview; // 미리보기 정보를 정의
	private PlayVO play; // 스트리밍을 지원할 경우 정의한다.
	private StoreVO store; // 다운로드를 지원할 경우 정의한다.
	private DateVO date; // 사용가능 시작/만료 일자
	private String additionalUsagePeriod; // 전시를 목적으로 이용 기간을 표기할 경우 사용한다.
	private String durationUsagePeriodView; // 전시를 목적으로 추가 이용 기간을 표기할 경우 사용한다.
	private String subscription; // 정기구독정보

	public String getAllow() {
		return this.allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public String getDeny() {
		return this.deny;
	}

	public void setDeny(String deny) {
		this.deny = deny;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public PreviewVO getPreview() {
		return this.preview;
	}

	public void setPreview(PreviewVO preview) {
		this.preview = preview;
	}

	public PlayVO getPlay() {
		return this.play;
	}

	public void setPlay(PlayVO play) {
		this.play = play;
	}

	public StoreVO getStore() {
		return this.store;
	}

	public void setStore(StoreVO store) {
		this.store = store;
	}

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
		this.date = date;
	}

	public String getAdditionalUsagePeriod() {
		return this.additionalUsagePeriod;
	}

	public void setAdditionalUsagePeriod(String additionalUsagePeriod) {
		this.additionalUsagePeriod = additionalUsagePeriod;
	}

	public String getDurationUsagePeriodView() {
		return this.durationUsagePeriodView;
	}

	public void setDurationUsagePeriodView(String durationUsagePeriodView) {
		this.durationUsagePeriodView = durationUsagePeriodView;
	}

	public String getSubscription() {
		return this.subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}
}
