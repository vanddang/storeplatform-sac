package com.skplanet.storeplatform.sac.display.music.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;

/**
 * 음악 상세보기
 * <p/>
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional
public class MusicDetailServiceImpl implements MusicDetailService {

	protected Logger logger = LoggerFactory.getLogger(MusicDetailServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public MusicDetailRes getMusicDetail(MusicDetailReq req) {
		MusicDetailRes res = new MusicDetailRes();
		Product product = new Product();

		product.setIdentifier(new Identifier("episode", "H001557945"));
		Title title = new Title();
		title.setText("금요일에 만나요 (Feat. 장이정 Of HISTORY)");
		product.setTitle(title);
		Price price = new Price();
		price.setText(660);
		product.setPrice(price);
		Menu menu1 = new Menu();
		menu1.setId("DP000516");
		menu1.setName("music");
		menu1.setType("topClass");
		Menu menu2 = new Menu();
		menu2.setId("DP16002002");
		menu2.setName("k-pop");
		Menu menu3 = new Menu();
		menu3.setId("CT25");
		menu3.setType("metaClass");
		menu3.setName("mp3");

		product.setMenuList(new ArrayList<Menu>(Arrays.asList(menu1, menu2, menu3)));
		Accrual accrual = new Accrual();
		accrual.setVoterCount(48);
		accrual.setDownloadCount(1776);
		accrual.setScore(3.0);
		product.setAccrual(accrual);
		Rights rights = new Rights();
		rights.setGrade("0");
		product.setRights(rights);

		Source source = new Source();
		source.setMediaType("image/png");
		source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PMUSIC/201401/02/0002074441/14/0003894669/14/10_0002074441_200_200_1701_200x200_R180x180.PNG");
		product.setSourceList(new ArrayList<Source>(Arrays.asList(source)));

		Music music = new Music();
		music.setIdentifier(new Identifier("downloadId", "4369827"));
		Source source1 = new Source();
		source1.setSize(5211114);
		source1.setType("audio/mp3-192");
		Source source2 = new Source();
		source2.setSize(3474075);
		source2.setType("audio/mp3-128");
		music.setSourceList(new ArrayList<Source>(Arrays.asList(source1, source2)));
		music.setServiceList(new ArrayList<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service>(
				Arrays.asList(new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service("mp3",
						"support"), new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service(
						"bell", "support"),
						new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service("ring",
								"support"))));
		product.setMusic(music);

		Contributor contributor = new Contributor();
		contributor.setIdentifier(new Identifier("contributorId", "261143"));
		contributor.setName("아이유");
		contributor.setAlbum("Modern Times - Epilogue");
		contributor.setPublisher("로엔트리");
		contributor.setAgency("(주)로엔엔터테인먼트");
		product.setContributor(contributor);

		res.setProduct(product);

		return res;
	}

}
