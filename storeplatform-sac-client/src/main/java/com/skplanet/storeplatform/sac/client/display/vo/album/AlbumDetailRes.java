package com.skplanet.storeplatform.sac.client.display.vo.album;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AlbumDetailRes {
	
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
