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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
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
	 * @see com.skplanet.storeplatform.sac.display.webtoon.servce.WebtoonService#searchWebtoonDetail(com.skplanet
	 * .storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacReq)
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
		req.setImageCd(DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
		req.setLangCd(header.getTenantHeader().getLangCd());

		List<WebtoonDetail> resultList = this.commonDAO.queryForList("Webtoon.searchWebtoonDetail", req,
				WebtoonDetail.class);

		List<Product> listVO = new ArrayList<Product>();

		WebtoonDetail webtoonDetail = new WebtoonDetail();
		Product product;
		Identifier identifier;
		Distributor distributor;
		Title title;
		Book book;
		Accrual accrual;
		Source source;
		Menu menu;
		Contributor contributor;

		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Identifier> identifierList = new ArrayList<Identifier>();

		String prePartId = "";
		String nextPartId = "";

		for (int i = 0; resultList != null && i < resultList.size(); i++) {
			webtoonDetail = resultList.get(i);

			String chapter = resultList.get(i).getChapter();
			req.setChapter(chapter);

			if (!StringUtils.isEmpty(chapter)) {
				// 이전회 상품ID
				req.setOrderedBy("pre");
				prePartId = (String) this.commonDAO.queryForObject("Webtoon.getWebtoonPreNext", req);
				// 다음회 상품ID
				req.setOrderedBy("next");
				nextPartId = (String) this.commonDAO.queryForObject("Webtoon.getWebtoonPreNext", req);
			}

			webtoonDetail = resultList.get(i);
			product = new Product();

			// 채널상품ID
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			identifier.setText(webtoonDetail.getProdId());
			identifierList.add(identifier);
			// 에피소드 상품ID
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(webtoonDetail.getPartProdId());
			identifierList.add(identifier);
			// 이전회 상품ID
			identifier = new Identifier();
			identifier.setType("pre");
			identifier.setText(prePartId);
			identifierList.add(identifier);
			// 다음회 상품ID
			identifier = new Identifier();
			identifier.setType("next");
			identifier.setText(nextPartId);
			identifierList.add(identifier);

			product.setIdentifierList(identifierList);

			// 회원번호
			distributor = new Distributor();
			distributor.setSellerKey(webtoonDetail.getSellerMbrNo());
			product.setDistributor(distributor);

			// 상품 정보 (상품명)
			title = new Title();
			title.setName(webtoonDetail.getChnlProdNm()); // 채널명
			title.setText(webtoonDetail.getProdNm()); // 에피소드명
			title.setPostfix(webtoonDetail.getChapter()); // 회차
			product.setTitle(title);

			// 메뉴 정보
			menu = new Menu();
			menuList = new ArrayList<Menu>();
			menu.setId(webtoonDetail.getTopMenuId());
			menu.setName(webtoonDetail.getTopMenuNm());
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(webtoonDetail.getMenuId());
			menu.setName(webtoonDetail.getMenuNm());
			menuList.add(menu);
			product.setMenuList(menuList);

			// source 정보
			source = new Source();
			sourceList = new ArrayList<Source>();
			source.setType("mainThumbNail");
			source.setUrl(webtoonDetail.getMainFilePath());
			sourceList.add(source);
			source = new Source();
			source.setType("thumbNail");
			source.setUrl(webtoonDetail.getFilePath());
			product.setSourceList(sourceList);

			// 페이지 수
			book = new Book();
			book.setTotalPages(webtoonDetail.getBookPageCnt());
			product.setBook(book);

			// contributor (업데이트 일자, 작가명)
			contributor = new Contributor();
			if (!StringUtils.isEmpty(webtoonDetail.getUpdDt())) {
				Date date = new Date();
				date.setType("updDt");
				date.setText(DateUtils.parseDate(webtoonDetail.getUpdDt()));
				contributor.setDate(date);
			}
			// 작가명
			contributor.setName(webtoonDetail.getArtist1Nm());
			product.setContributor(contributor);

			// 평점, 참여자수
			accrual = new Accrual();
			accrual.setScore(webtoonDetail.getAvgEvluScore());
			accrual.setVoterCount(webtoonDetail.getPaticpersCnt());
			product.setAccrual(accrual);

			// 상품설명
			product.setProductExplain(webtoonDetail.getProdBaseDesc());
			product.setProductDetailExplain(webtoonDetail.getProdDtlDesc());

			listVO.add(product);

		}

		commonResponse.setTotalCount(webtoonDetail.getTotalCount());
		res.setCommonResponse(commonResponse);
		res.setProductList(listVO);

		return res;
	}

}
