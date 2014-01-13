package com.skplanet.storeplatform.sac.display.banner.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.BannerExplain;

@Service
@Transactional
public class BannerServceImpl implements BannerService {

	@Override
	public BannerRes searchBannerList(BannerReq req) {

		BannerRes res = new BannerRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Banner> bannerList = new ArrayList<Banner>();

		// ======================== product type일 경우 ========================
		Banner banner = new Banner();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		Identifier identifier = new Identifier();
		Title title = new Title();
		List<Menu> menuList = new ArrayList<Menu>();
		Menu menuTop = new Menu();
		List<Source> sourceList = new ArrayList<Source>();
		Source source = new Source();

		// Identifier 설정
		identifier.setText("2779");
		identifier.setType("product");
		identifierList.add(identifier);

		// sizeType 설정
		banner.setSizeType("B");

		// Title 설정
		title.setText("T쿠폰T데이터쿠폰");

		// Menu 설정
		menuTop.setId("DP000504");
		menuTop.setName("생활/위치");
		menuTop.setType("topClass");
		menuList.add(menuTop);
		Menu menu = new Menu();
		menu.setId("DP04015");
		menu.setName("쇼핑");
		menuList.add(menu);

		// Source 설정
		source.setUrl("http://wap.tstore.co.kr/images/tstore30/banner/mainTop/mainTop_xhdpi_222_222_20131230092328698.png");
		source.setMediaType("image/png");
		sourceList.add(source);

		banner.setTitle(title);
		banner.setMenuList(menuList);
		banner.setSourceList(sourceList);
		banner.setIdentifier(identifierList);
		bannerList.add(banner);

		// ======================== 외부 url일 경우 ========================
		// sizeType 설정
		banner = new Banner();
		identifierList = new ArrayList<Identifier>();
		identifier = new Identifier();
		title = new Title();
		Url url = new Url();

		banner.setSizeType("B");

		// Identifier 설정
		identifier.setText("37274");
		identifier.setType("external");
		identifierList.add(identifier);
		banner.setIdentifier(identifierList);

		// URL 설정
		url.setText("http://is.gd/643Al3?ua=Android%2F4.0.4+%28SHV-E160S%3Bresolution%3D800*1280%3Bdpi%3D320%29+Tstore-Svc%2F1.0+%28com.skt.skaf.A000Z00040%2F9999%29&amp;id=2830%2B20140103T084313Z&amp;type=banner");
		banner.setUrl(url);

		// Source 설정
		sourceList = new ArrayList<Source>();
		source = new Source();
		source.setUrl("http://wap.tstore.co.kr/images/tstore30/banner/mainTop/mainTop_xhdpi_222_222_20131230092328698.png");
		source.setMediaType("image/png");
		sourceList.add(source);
		banner.setSourceList(sourceList);

		bannerList.add(banner);

		// ======================== 메뉴 연결일 경우 ========================
		// sizeType 설정
		banner = new Banner();
		identifierList = new ArrayList<Identifier>();
		identifier = new Identifier();
		title = new Title();
		url = new Url();

		banner.setSizeType("B");

		// Title 설정
		title.setText("메뉴");

		// Identifier 설정
		identifier.setText("37274");
		identifier.setType("menu");
		identifierList.add(identifier);
		banner.setIdentifier(identifierList);

		// URL 설정
		url.setText("http://is.gd/643Al3?ua=Android%2F4.0.4+%28SHV-E160S%3Bresolution%3D800*1280%3Bdpi%3D320%29+Tstore-Svc%2F1.0+%28com.skt.skaf.A000Z00040%2F9999%29&amp;id=2830%2B20140103T084313Z&amp;type=banner");
		banner.setUrl(url);

		// Source 설정
		sourceList = new ArrayList<Source>();
		source = new Source();
		source.setUrl("http://wap.tstore.co.kr/images/tstore30/banner/mainTop/mainTop_xhdpi_222_222_20131230092328698.png");
		source.setMediaType("image/png");
		sourceList.add(source);
		banner.setSourceList(sourceList);

		bannerList.add(banner);

		// ========================url type일 경우========================
		banner = new Banner();
		identifierList = new ArrayList<Identifier>();
		identifier = new Identifier();
		title = new Title();
		url = new Url();

		// Identifier 설정
		identifier.setText("37274");
		identifier.setType("url");
		identifierList.add(identifier);

		// Title 설정
		title.setText("게임 보너스 클럽");

		// URL 설정
		url.setText("http://is.gd/643Al3?ua=Android%2F4.0.4+%28SHV-E160S%3Bresolution%3D800*1280%3Bdpi%3D320%29+Tstore-Svc%2F1.0+%28com.skt.skaf.A000Z00040%2F9999%29&amp;id=2830%2B20140103T084313Z&amp;type=banner");

		// Source 설정
		sourceList = new ArrayList<Source>();
		source = new Source();
		source.setUrl("http://wap.tstore.co.kr/images/tstore30/banner/mainTop/mainTop_xhdpi_222_222_20131230092328698.png");
		source.setMediaType("image/png");
		sourceList.add(source);

		banner.setTitle(title);
		banner.setUrl(url);
		banner.setIdentifier(identifierList);
		banner.setSourceList(sourceList);
		bannerList.add(banner);

		// themeZone type일 경우
		banner = new Banner();
		identifierList = new ArrayList<Identifier>();
		identifier = new Identifier();
		title = new Title();

		// Base 설정
		banner.setBase("/category/themeZone");

		// Identifier 설정
		identifier.setText("2849");
		identifier.setType("themeZone");
		identifierList.add(identifier);

		// sizeType 설정
		banner.setSizeType("B");

		// Title 설정
		title.setText("최고의 로맨스소설 50프로 할인 이벤트");

		// BannerExplain 설정
		BannerExplain bannerExplain = new BannerExplain();
		bannerExplain.setId("R00001359");

		// Source 설정
		sourceList = new ArrayList<Source>();
		source = new Source();
		source.setUrl("http://wap.tstore.co.kr/images/tstore30/banner/mainTop/mainTop_xhdpi_222_222_20140106094451812.png");
		source.setMediaType("image/png");
		sourceList.add(source);

		banner.setTitle(title);
		banner.setUrl(url);
		banner.setIdentifier(identifierList);
		banner.setBannerExplain(bannerExplain);
		banner.setSourceList(sourceList);
		bannerList.add(banner);

		commonResponse.setTotalCount(bannerList.size());

		res.setBannerList(bannerList);
		res.setCommonResponse(commonResponse);
		return res;
	}

}
