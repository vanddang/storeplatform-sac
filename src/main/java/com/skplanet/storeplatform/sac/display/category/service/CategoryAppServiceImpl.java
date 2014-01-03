package com.skplanet.storeplatform.sac.display.category.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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

public class CategoryAppServiceImpl implements CategoryAppService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public CategoryAppRes searchCategoryAppList(CategoryAppReq req) {
		CategoryAppRes categoryAppRes = new CategoryAppRes();
		CommonResponse commonResponse = new CommonResponse();

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

				// 상품 정보 (상품ID)
				identifier = new Identifier();
				identifier.setText(categoryAppDTO.getProdId());

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

				// 평점 정보
				accrual = new Accrual();
				accrual.setVoterCount("1820");
				accrual.setDownloadCount("30");
				accrual.setScore(4.5);

				// 이용권한 정보
				rights = new Rights();
				rights.setGrade(categoryAppDTO.getProdGrdCd());

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(categoryAppDTO.getProdNm());

				// 이미지 정보
				source = new Source();
				sourceList = new ArrayList<Source>();
				source.setType("thumbnail");
				source.setUrl(categoryAppDTO.getImgFilePath());
				sourceList.add(source);

				// 상품 정보 (상품설명)
				product = new Product();
				product.setProductExplain(categoryAppDTO.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price = new Price();
				price.setText(Integer.parseInt(categoryAppDTO.getProdAmt()));

				// 상품 타입 (에피소드상품)
				identifier.setType("episode");

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
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setPrice(price);
				productList.add(i, product);

				commonResponse.setTotalCount(categoryAppDTO.getTotalCount());
				categoryAppRes.setProductList(productList);
				categoryAppRes.setCommonResponse(commonResponse);
			}
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			categoryAppRes.setCommonResponse(commonResponse);
		}

		return categoryAppRes;
	}
}
