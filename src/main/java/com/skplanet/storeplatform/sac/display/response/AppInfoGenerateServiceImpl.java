package com.skplanet.storeplatform.sac.display.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
@Transactional
public class AppInfoGenerateServiceImpl implements AppInfoGenerateService {

	@Autowired
	private CommonMetaInfoGenerateService commonService;

	@Override
	public List<Support> generateSupportList(MetaInfo metaInfo) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = this.commonService.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getDrmYn());
		supportList.add(support);
		support = this.commonService.generateSupport(DisplayConstants.DP_IN_APP_SUPPORT_NM,
				metaInfo.getPartParentClsfCd());
		supportList.add(support);
		return supportList;
	}

	@Override
	public App generateApp(MetaInfo metaInfo) {
		App app = new App();
		app.setAid(metaInfo.getAid());
		app.setPackageName(metaInfo.getApkPkgNm());
		app.setVersionCode(metaInfo.getApkVer());
		app.setVersion(metaInfo.getProdVer());
		app.setSize(metaInfo.getFileSize());
		return app;
	}

	@Override
	public List<Menu> generateMenuList(MetaInfo metaInfo) {
		Menu menu = new Menu();
		List<Menu> menuList = new ArrayList<Menu>();

		menu.setId(metaInfo.getMenuId());
		menu.setName(metaInfo.getMenuNm());
		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(metaInfo.getTopMenuId());
		menu.setName(metaInfo.getTopMenuNm());
		menuList.add(menu);
		return menuList;
	}
}
