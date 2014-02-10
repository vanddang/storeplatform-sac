package com.skplanet.storeplatform.sac.client.display.vo.freepass;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

public class SeriespassListRes {

	private static final long serialVersionUID = -7112036496004829217L;

	private CommonResponse commonResponse;
	private List<Product> productList;
	public CommonResponse getCommonResponse() {
		return commonResponse;
	}
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
