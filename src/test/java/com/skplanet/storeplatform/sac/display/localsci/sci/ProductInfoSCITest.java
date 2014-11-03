/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * ProductInfoSCITest
 * </p>
 * Updated on : 2014. 07. 24 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ProductInfoSCITest {

    private static final Logger log = LoggerFactory.getLogger(ProductInfoSCITest.class);

    @Autowired
    private ProductInfoSCI productInfoSCI;

    // 시리즈 상품 메타코드: CT14,CT15,CT16,CT20,CT21,CT22

    @Test
    public void integratedTestProd() {
        // Episode Only
        List<TestProd> prods = new ArrayList<TestProd>();
        prods.add(new TestProd("0000657169", ProductType.App));
        prods.add(new TestProd("0000651962", ProductType.App)); // SeedMapg 존재하는 앱 상품
        prods.add(new TestProd("0900000141", ProductType.App));
        prods.add(new TestProd("H000400088", ProductType.Music));
        prods.add(new TestProd("H000043059", ProductType.Vod));
        prods.add(new TestProd("H000043062", ProductType.Vod, "Y"));
        prods.add(new TestProd("H001727890", ProductType.EbookComic, "Y"));
        prods.add(new TestProd("H002928525", ProductType.EbookComic));
        prods.add(new TestProd("H002643572", ProductType.RingBell));
        prods.add(new TestProd("H001929394", ProductType.RingBell));
        prods.add(new TestProd("S900027675", ProductType.Shopping));
//        prods.add(new TestProd("H002725517", ProductType.RingBell));    // QA Only
        // TODO 링벨의 메타 클래스 응답 체크

        ProductInfoSacReq req = new ProductInfoSacReq();
        req.setDeviceModelNo("ANY-PHONE-4APP");
        req.setTenantId("S01");

        for(TestProd prod : prods) {
            req.setList(Arrays.asList(prod.prodId));
            ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
            List<ProductInfo> productList = res.getProductList();

            if(productList.size() == 0)
                throw new RuntimeException("응답 데이터가 없습니다. #"+prod.prodId);

            for (ProductInfo productInfo : productList) {
                log.info(ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));

                if(!prod.seriesYn.equals(productInfo.getSeriesYn()))
                    throw new RuntimeException("시리즈 여부가 일치하지 않습니다.");
            }
        }
    }

    class TestProd {
        String prodId;
        ProductType type;
        String seriesYn;

        TestProd(String prodId, ProductType type, String seriesYn) {
            this.prodId = prodId;
            this.type = type;
            this.seriesYn = seriesYn;
        }

        TestProd(String prodId, ProductType type) {
            this.prodId = prodId;
            this.type = type;
            this.seriesYn = "N";
        }
    }
}
