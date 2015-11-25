package com.skplanet.storeplatform.sac.client.display.vo.album;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AlbumListByArtistRes {
	List<Product> productList;
	
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
