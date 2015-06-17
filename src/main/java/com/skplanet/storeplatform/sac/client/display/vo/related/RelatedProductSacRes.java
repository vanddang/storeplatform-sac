package com.skplanet.storeplatform.sac.client.display.vo.related;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RelatedProductSacRes {
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
