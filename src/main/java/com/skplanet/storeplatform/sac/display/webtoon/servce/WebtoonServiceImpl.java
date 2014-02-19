/**
 * 
 */
package com.skplanet.storeplatform.sac.display.webtoon.servce;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.webtoon.vo.WebtoonDetail;

/**
 * 웹툰 상품 상세 조회
 * 
 * Updated on : 2014. 2. 19. Updated by : 조준일, nTels.
 */
@Service
public class WebtoonServiceImpl implements WebtoonService {

	// private transient Logger logger = LoggerFactory.getLogger(WebtoonServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryVodService#searchVodList(com.skplanet
	 * .storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodReq)
	 */
	@Override
	public WebtoonDetailSacRes searchWebtoonDetail(WebtoonDetailSacReq req, SacRequestHeader header) {

		WebtoonDetailSacRes res = new WebtoonDetailSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String prodId = req.getProdId();

		// prodId 필수 파라미터 체크
		if (StringUtils.isEmpty(prodId)) {
			throw new StorePlatformException("SAC_DSP_0002", "prodId", prodId);
		}

		// 헤더값 세팅
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setImageCd("DP000101");
		req.setLangCd(header.getTenantHeader().getLangCd());

		List<WebtoonDetail> resultList = this.commonDAO.queryForList("Webtoon.searchWebtoonDetail", req,
				WebtoonDetail.class);

		List<Product> listVO = new ArrayList<Product>();

		WebtoonDetail webtoonDetail;
		Product product;
		Identifier identifier;
		Title title;
		Book book;
		Accrual accrual;
		Rights rights;
		Source source;
		Price price;
		Menu menu;
		Contributor contributor;

		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Support> supportList;

		for (int i = 0; resultList != null && i < resultList.size(); i++) {

			webtoonDetail = resultList.get(i);
			product = new Product();
			identifier = new Identifier();
			title = new Title();
			book = new Book();
			accrual = new Accrual();
			rights = new Rights();
			source = new Source();
			price = new Price();
			contributor = new Contributor();

			// 상품ID
			identifier = new Identifier();

			// Response VO를 만들기위한 생성자
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();

			identifier.setType("channel");
			identifier.setText(webtoonDetail.getProdId());
			title.setText(webtoonDetail.getProdNm());

			menu = new Menu();
			menu.setId(webtoonDetail.getTopMenuId());
			menu.setName(webtoonDetail.getTopMenuNm());
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(webtoonDetail.getMenuId());
			menu.setName(webtoonDetail.getMenuNm());
			menuList.add(menu);

			book.setSupportList(supportList);
			product.setBook(book);

			contributor.setName(webtoonDetail.getArtistNm());

			if (!StringUtils.isEmpty(webtoonDetail.getUpdDt())) {
				Date date = new Date();
				date.setType("updDt");
				date.setText(DateUtils.parseDate(webtoonDetail.getUpdDt()));
				contributor.setDate(date);
			}

			accrual.setScore(webtoonDetail.getAvgEvluScore());

			source.setType("thumbNail");
			source.setUrl(webtoonDetail.getFilePath());
			sourceList.add(source);

			product.setIdentifier(identifier);
			product.setTitle(title);
			// support.setText(categoryEpubDTO.getDrmYn() + "|" + categoryEpubDTO.getPartParentClsfCd());
			// supportList.add(support);
			// product.setSupportList(supportList);
			product.setMenuList(menuList);

			product.setAccrual(accrual);
			product.setRights(rights);
			product.setProductExplain(webtoonDetail.getProdDesc());
			product.setProductDetailExplain(webtoonDetail.getProdDtlDesc());
			product.setSourceList(sourceList);
			product.setPrice(price);
			product.setContributor(contributor);

			listVO.add(product);

		}
		res = new WebtoonDetailSacRes();
		commonResponse = new CommonResponse();
		res.setCommonResponse(commonResponse);
		res.setProductList(listVO);

		return res;
	}
}
