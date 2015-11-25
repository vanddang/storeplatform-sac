/**
 * 
 */
package com.skplanet.storeplatform.sac.display.webtoon.servce;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.webtoon.vo.WebtoonDetail;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.webtoon.servce.WebtoonService#searchWebtoonDetail(com.skplanet
	 * .storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacReq)
	 */
	@Override
	public WebtoonDetailSacRes searchWebtoonDetail(WebtoonDetailSacReq req, SacRequestHeader header) {

        String tenantId = header.getTenantHeader().getTenantId();
        WebtoonDetailSacRes res = new WebtoonDetailSacRes();
        CommonResponse commonResponse = new CommonResponse();

        // 헤더값 세팅
        req.setDeviceModelCd(header.getDeviceHeader().getModel());
        req.setTenantId(header.getTenantHeader().getTenantId());
        req.setImageCd(DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
        req.setLangCd(header.getTenantHeader().getLangCd());

        List<WebtoonDetail> resultList = this.commonDAO.queryForList("Webtoon.searchWebtoonDetail", req,
                WebtoonDetail.class);

        List<Product> listVO = new ArrayList<Product>();

        Product product;
        Identifier identifier;
        Distributor distributor;
        Title title;
        Book book;
        Accrual accrual;
        Source source;
        Menu menu;
        Date date;
        Contributor contributor;

        // Response VO를 만들기위한 생성자
        List<Menu> menuList;
        List<Source> sourceList;
        List<Identifier> identifierList = new ArrayList<Identifier>();

        String prePartId = "";
        String nextPartId = "";

        if (CollectionUtils.isEmpty(resultList))
            throw new StorePlatformException("SAC_DSP_0009");

        for (WebtoonDetail webtoonDetail : resultList) {
            product = new Product();

            String chapter = webtoonDetail.getChapter();
            req.setChapter(chapter);

            if (!StringUtils.isEmpty(chapter)) {
                Map<String, Object> qreq = new HashMap<String, Object>();
                qreq.put("tenantId", tenantId);
                qreq.put("prodId", req.getProdId());
                qreq.put("chapter", req.getChapter());

                // 이전회 상품ID
                qreq.put("orderedBy", "pre");
                prePartId = (String) this.commonDAO.queryForObject("Webtoon.getWebtoonPreNext", qreq);
                // 다음회 상품ID
                qreq.put("orderedBy", "next");
                nextPartId = (String) this.commonDAO.queryForObject("Webtoon.getWebtoonPreNext", qreq);
            }

            // 서비스그룹코드
            product.setSvcGrpCd(webtoonDetail.getSvcGrpCd());

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
            identifierList.add(new Identifier("pre", prePartId));
            // 다음회 상품ID
            identifierList.add(new Identifier("next", nextPartId));

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
            menu.setId(webtoonDetail.getMenuId());
            menu.setName(webtoonDetail.getMenuNm());
            menu.setDesc(webtoonDetail.getMenuDesc());
            menuList.add(menu);
            menu = new Menu();
            menu.setId(webtoonDetail.getTopMenuId());
            menu.setName(webtoonDetail.getTopMenuNm());
            menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
            menuList.add(menu);
            menu = new Menu();
            menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
            menu.setId(webtoonDetail.getMetaClsfCd());
            menuList.add(menu);
            product.setMenuList(menuList);

            // source 정보
            source = new Source();
            sourceList = new ArrayList<Source>();
            source.setType("mainThumbNail");
            source.setUrl(webtoonDetail.getMainFilePath());
            sourceList.add(source);
            source = new Source();
            String filePath = webtoonDetail.getFilePath();
            filePath = filePath.substring(0, filePath.lastIndexOf("/")); // 이미지 경로
            source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
            source.setUrl(filePath);
            sourceList.add(source);
            product.setSourceList(sourceList);

            // 페이지 수
            book = new Book();
            book.setTotalPages(webtoonDetail.getBookPageCnt());
            product.setBook(book);

            // contributor 작가명
            contributor = new Contributor();
            contributor.setName(webtoonDetail.getArtist1Nm());
            product.setContributor(contributor);

            // 업데이트 일자
            date = new Date();
            date.setType("updDt");
            date.setTextFromDate(DateUtils.parseDate(webtoonDetail.getUpdDt()));
            product.setDate(date);

            // 평점, 참여자수
            accrual = new Accrual();
            accrual.setScore(webtoonDetail.getAvgEvluScore());
            accrual.setVoterCount(webtoonDetail.getPaticpersCnt());
            product.setAccrual(accrual);

            // 상품설명
            product.setProductExplain(webtoonDetail.getProdBaseDesc());
            product.setProductDetailExplain(webtoonDetail.getProdDtlDesc());

            // 상품 판매상태
            product.setSalesStatus(webtoonDetail.getProdStatusCd());

            listVO.add(product);

            commonResponse.setTotalCount(webtoonDetail.getTotalCount());
        }

        res.setCommonResponse(commonResponse);
        res.setProductList(listVO);

        return res;
    }

}
