package com.skplanet.storeplatform.sac.client.display.vo.freepass;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 
 * FreepassDetailReq Value Object
 * 
 * 정액제 req VO
 * 
 * Updated on : 2014. 2. 13. Updated by : 서영배, GTSOFT
 */
public class SeriespassListRes {

	private static final long serialVersionUID = -7112036496004829217L;

	private CommonResponse commonResponse;
	private List<Product> productList;
	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return commonResponse;
	}
	/**
	 * @param commonResponse the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
	/**
	 * @return the productList
	 */
	public List<Product> getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
