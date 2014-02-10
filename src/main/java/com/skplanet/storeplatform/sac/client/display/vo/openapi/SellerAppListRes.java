package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Tag;

/**
 * 태그 목록 조회 Response Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class SellerAppListRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * Common Response.
	 */
	private CommonResponse commonResponse;
	/**
	 * Tag List.
	 */
	private List<Tag> tagList;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return the tagList
	 */
	public List<Tag> getTagList() {
		return this.tagList;
	}

	/**
	 * @param tagList
	 *            the tagList to set
	 */
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

}
