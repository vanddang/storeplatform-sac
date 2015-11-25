package com.skplanet.storeplatform.sac.client.display.vo.category;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategorySpecificSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;
	private List<?> productList;
    private List<String> dataTypeList;

    public CategorySpecificSacRes() {}

    public CategorySpecificSacRes(CommonResponse commonResponse, List<?> productList, List<String> dataTypeList) {
        this.commonResponse = commonResponse;
        this.productList = productList;
        this.dataTypeList = dataTypeList;
    }

    public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<?> getProductList() {
		return this.productList;
	}

	public void setProductList(List<?> productList) {
		this.productList = productList;
	}

    public List<String> getDataTypeList() {
        return dataTypeList;
    }

    public void setDataTypeList(List<String> dataTypeList) {
        this.dataTypeList = dataTypeList;
    }
}
