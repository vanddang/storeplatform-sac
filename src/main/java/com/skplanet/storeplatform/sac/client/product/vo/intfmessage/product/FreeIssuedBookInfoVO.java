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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.FreeIssuedBookInfoProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.IdentifierVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.SourceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TitleVO;

/**
 * Interface Message FreeIssuedBookInfo Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(FreeIssuedBookInfoProto.FreeIssuedBookInfo.class)
public class FreeIssuedBookInfoVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private IdentifierVO identifier; // 무료 발행호 상품 ID
	private String scid; // 무료 발행호 Subn Content ID
	private String version; // 무료 발행호 버전
	/*
	 * 무료 발행호 상태 ( payment : 구매, nonPayment : 비구매, noProduct : 상품 미등록)
	 */
	private String status;
	private TitleVO title; // 무료 발행호 상품명
	private String freeIssuedBookExplain; // 무료 발행호 설명
	private DateVO date; // 예상 정기구독 시작 ~ 종료일
	private SourceVO source; // 무료 발행호 이미지

	public IdentifierVO getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(IdentifierVO identifier) {
		this.identifier = identifier;
	}

	public String getScid() {
		return this.scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TitleVO getTitle() {
		return this.title;
	}

	public void setTitle(TitleVO title) {
		this.title = title;
	}

	public String getFreeIssuedBookExplain() {
		return this.freeIssuedBookExplain;
	}

	public void setFreeIssuedBookExplain(String freeIssuedBookExplain) {
		this.freeIssuedBookExplain = freeIssuedBookExplain;
	}

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
		this.date = date;
	}

	public SourceVO getSource() {
		return this.source;
	}

	public void setSource(SourceVO source) {
		this.source = source;
	}
}
