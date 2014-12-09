package com.skplanet.storeplatform.sac.client.display.vo.category;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryShoppingSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private Product product;

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}


}
