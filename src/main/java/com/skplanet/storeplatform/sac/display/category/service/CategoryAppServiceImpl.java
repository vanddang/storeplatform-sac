package com.skplanet.storeplatform.sac.display.category.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.category.vo.CategoryAppDTO;
import com.skplanet.storeplatform.sac.product.service.ProductCommonServiceImpl;

@Service
@Transactional
public class CategoryAppServiceImpl implements CategoryAppService {
	private transient Logger logger = LoggerFactory.getLogger(ProductCommonServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public CategoryAppRes searchCategoryAppList(CategoryAppReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchCategoryAppList Service started!!");
		this.logger.debug("----------------------------------------------------------------");

		CategoryAppRes categoryAppRes = new CategoryAppRes();
		CommonResponse commonResponse = new CommonResponse();

		// 일반 카테고리 앱 상품 조회
		List<CategoryAppDTO> appList = this.commonDAO.queryForList("Category.selectCategoryAppList", req,
				CategoryAppDTO.class);

		if (appList != null) {
			CategoryAppDTO categoryAppDTO = null;

			Product product = null;
			Identifier identifier = null;
			Menu menu = null;
			Accrual accrual = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Price price = null;
			App app = null;
			Support support = null;

			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Product> productList = new ArrayList<Product>();
			List<Support> supportList = new ArrayList<Support>();

			for (int i = 0; i < appList.size(); i++) {
				categoryAppDTO = appList.get(i);

				product = new Product();

				// 상품 정보 (상품ID)
				identifier = new Identifier();
				identifier.setType("episode");
				identifier.setText(categoryAppDTO.getProdId());
				product.setIdentifier(identifier);

				// 메뉴 정보
				menu = new Menu();
				menuList = new ArrayList<Menu>();
				menu.setType("topClass");
				menu.setId(categoryAppDTO.getUpMenuId());
				menu.setName(categoryAppDTO.getUpMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(categoryAppDTO.getMenuId());
				menu.setName(categoryAppDTO.getMenuNm());
				menuList.add(menu);
				product.setMenuList(menuList);

				// 평점 정보
				accrual = new Accrual();
				accrual.setVoterCount("1820");
				accrual.setDownloadCount("30");
				accrual.setScore(4.5);
				product.setAccrual(accrual);

				// 이용권한 정보
				rights = new Rights();
				rights.setGrade(categoryAppDTO.getProdGrdCd());
				product.setRights(rights);

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(categoryAppDTO.getProdNm());
				product.setTitle(title);

				// 이미지 정보
				source = new Source();
				sourceList = new ArrayList<Source>();
				source.setType("thumbnail");
				source.setUrl(categoryAppDTO.getImgPath());
				sourceList.add(source);
				product.setSourceList(sourceList);

				// 상품 정보 (상품설명)
				product.setProductExplain(categoryAppDTO.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price = new Price();
				price.setText(Integer.parseInt(categoryAppDTO.getProdAmt()));
				product.setPrice(price);

				// 상품 지원 구분 정보
				support = new Support();
				supportList = new ArrayList<Support>();
				support.setType("drm");
				support.setText(categoryAppDTO.getDrmYn());
				supportList.add(support);

				support = new Support();
				support.setType("inApp");
				support.setText(categoryAppDTO.getPartParentClsfCd());
				supportList.add(support);
				product.setSupportList(supportList);

				// 어플리케이션 정보
				app = new App();
				app.setAid(categoryAppDTO.getAid());
				app.setPackageName(categoryAppDTO.getApkPkgNm());
				app.setVersionCode(categoryAppDTO.getApkVer());
				app.setVersion(categoryAppDTO.getProdVer());
				product.setApp(app);

				// 데이터 매핑
				productList.add(i, product);
			}

			commonResponse.setTotalCount(categoryAppDTO.getTotalCount());
			categoryAppRes.setProductList(productList);
			categoryAppRes.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			categoryAppRes.setCommonResponse(commonResponse);
		}

		return categoryAppRes;
	}
}
