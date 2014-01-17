package com.skplanet.storeplatform.sac.display.app.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.AppDebug;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.History;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;

/**
 * 앱 상품 상세조회
 * <p/>
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional
public class AppServiceImpl implements AppService {

	private static Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public AppDetailRes getAppDetail(AppDetailReq request) {
		// Dummy generation
		AppDetailRes res = new AppDetailRes();
		Product product = new Product();

		product.setIdentifier(new Identifier("episode", "0000308800"));

		product.setTitle(new Title("CNN 수퍼스타 20인을 인터뷰하다"));
		Price price = new Price();
		price.setFixedPrice(0);
		product.setPrice(price);
		Menu menu = new Menu();
		menu.setId("DP000508");
		menu.setName("languageEducation");
		menu.setType("topClass");

		Menu menu1 = new Menu();
		menu1.setId("DP08002");
		menu1.setName("liveEnglish");

		product.setMenuList(new ArrayList<Menu>(Arrays.asList(menu, menu1)));
		product.setPacketFee("paid");
		Source source = new Source();
		source.setMediaType("image/png");
		source.setType("screenshot");
		source.setUrl("http://wap.tstore.co.kr/android6/201211/15/IF1423026819420091216095419/0000308800/img/thumbnail/0000308800_219_365_0_01.PNG");

		Source source1 = new Source();
		source1.setMediaType("image/png");
		source1.setType("screenshot");
		source1.setUrl("http://wap.tstore.co.kr/android6/201211/15/IF1423026819420091216095419/0000308800/img/thumbnail/0000308800_219_365_0_02.PNG");

		product.setSourceList(new ArrayList<Source>(Arrays.asList(source, source1)));
		Accrual accrual = new Accrual();
		accrual.setVoterCount(20);
		accrual.setDownloadCount(1000);
		accrual.setScore(4.5);
		product.setAccrual(accrual);

		Rights rights = new Rights();
		rights.setGrade("0");

		product.setRights(rights);

		App app = new App();
		app.setAid("OA00308800");
		app.setSupportedOs("Android 2.2&amp;2.3&amp;3.0&amp;3.1&amp;3.2&amp;4.0&amp;4.1&amp;4.2&amp;4.3&amp;4.4");
		app.setPackageName("kr.co.waterbear.cnn");
		app.setVersionCode("10100014");
		app.setVersion("1.7");

		AppDebug debug = new AppDebug();
		debug.setPackageName("kr.co.waterbear.cnn");
		debug.setVersionCode("10100014");
		app.setAppDebug(debug);
		History history = new History();

		Update update = new Update();
		Date date = new Date();
		update.setDate(date);
		date.setType("date/reg");
		date.setText("20131202T000000+0900");
		update.setUpdateExplain("안녕하세요 와이비엠시사닷컴 입니다. 지원 기기 추가 요청 검증요청 드립니다 ...");

		Update update1 = new Update();
		Date date1 = new Date();
		update1.setDate(date1);
		date1.setType("date/reg");
		date1.setText("20131120T000000+0900");
		update1.setUpdateExplain("해당 어플 추가 지원 기기 문의 드립니다. 기기명 : IM-A860K");
		app.setHistory(history);
		history.setUpdate(new ArrayList<Update>(Arrays.asList(update, update1)));

		product.setApp(app);
		Distributor distributor = new Distributor();
		distributor.setType("corporation");
		distributor.setName("오재환");
		distributor.setNickName("ybmsisa_com");
		distributor.setTel("0220090346");
		distributor.setEmail("sarang486s@ybmsisa.com");
		distributor.setAddress("경기도 성남시 분당구 대왕판교로 670  유스페이스2 A동 9층");
		distributor.setRegNo("강남-1757");
		distributor.setCompany("(주)와이비엠시사닷컴");
		product.setDistributor(distributor);
		product.setPhysicalPath("/android6/201211/15/IF1423026819420091216095419/0000308800/apk/app.apk");

		res.setProduct(product);

		return res;
	}

}
