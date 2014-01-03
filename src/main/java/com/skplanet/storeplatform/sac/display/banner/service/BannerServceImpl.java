package com.skplanet.storeplatform.sac.display.banner.service;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner;

public class BannerServceImpl implements BannerService {

	@Override
	public BannerRes searchBannerList(BannerReq request) {

		BannerRes response = new BannerRes();
		List<Banner> bannerList = new ArrayList<Banner>();

		// product type일 경우
		Banner prodBanner = new Banner();
		Identifier prodIdentifier = new Identifier();
		Title title = new Title();
		List<Menu> menuList = new ArrayList<Menu>();
		prodIdentifier.setText("2779");
		prodIdentifier.setType("product");
		prodBanner.setSizeType("B");
		title.setText("T쿠폰T데이터쿠폰");

		Menu menuTop = new Menu();
		menuTop.setId("DP000504");
		menuTop.setName("생활/위치");
		menuTop.setType("topClass");
		menuList.add(menuTop);
		Menu menu = new Menu();
		menu.setId("DP04015");
		menu.setName("쇼핑");
		menuList.add(menu);

		List<Source> sourceList = new ArrayList<Source>();
		Source source = new Source();
		source.setUrl("http://wap.tstore.co.kr/images/tstore30/banner/mainTop/mainTop_xhdpi_222_222_20131230092328698.png");
		source.setMediaType("image/png");
		sourceList.add(source);

		prodBanner.setTitle(title);
		prodBanner.setMenuList(menuList);
		prodBanner.setSourceList(sourceList);

		bannerList.add(prodBanner);

		response.setBannerList(bannerList);

		return response;
	}

}
