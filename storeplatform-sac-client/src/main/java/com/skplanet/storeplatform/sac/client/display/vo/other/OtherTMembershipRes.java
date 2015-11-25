package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;

/**
 * T Membership 할인율 조회 Request Value Object.
 * 
 * Updated on : 2014. 3. 31. Updated by : 윤주영, SK 플래닛.
 */
public class OtherTMembershipRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5172135327550786693L;

	/**
	 * Common Response.
	 */
	private CommonResponse commonResponse;

	private Point point;

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
	 * @return the point
	 */
	public Point getPoint() {
		return this.point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	public void setPoint(Point point) {
		this.point = point;
	}

}
