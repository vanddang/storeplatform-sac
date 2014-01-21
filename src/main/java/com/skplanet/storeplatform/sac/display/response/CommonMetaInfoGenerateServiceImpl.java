package com.skplanet.storeplatform.sac.display.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
@Transactional
public class CommonMetaInfoGenerateServiceImpl implements CommonMetaInfoGenerateService {
	@Override
	public Identifier generateIdentifier(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = new Identifier();
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)
				&& DisplayConstants.DP_SHOPPING_COUPON_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			identifier.setType(DisplayConstants.DP_CATALOG_IDENTIFIER_CD);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
		}
		identifier.setText(metaInfo.getProdId());
		return identifier;
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

		menu = new Menu();
		menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
		menu.setId(metaInfo.getMetaClsfCd());
		menuList.add(menu);
		return menuList;
	}

	@Override
	public Source generateSource(MetaInfo metaInfo) {
		Source source = new Source();
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getFilePath()));
		source.setUrl(metaInfo.getFilePath());
		return source;
	}

	@Override
	public List<Source> generateSourceList(MetaInfo metaInfo) {
		List<Source> sourceList = new ArrayList<Source>();
		Source source = this.generateSource(metaInfo);
		sourceList.add(source);
		return sourceList;
	}

	@Override
	public Rights generateRights(MetaInfo metaInfo) {
		Rights rights = new Rights();
		rights.setGrade(metaInfo.getProdGrdCd());
		return rights;
	}

	@Override
	public Support generateSupport(String type, String text) {
		Support support = new Support();
		support.setType(type);
		support.setText(text);
		return support;
	}

	@Override
	public Map<String, Object> generateSupportList(MetaInfo metaInfo) {
		return null;
	}

	@Override
	public Price generatePrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setText(metaInfo.getProdAmt());
		return price;
	}

	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		accrual.setVoterCount(metaInfo.getPaticpersCnt());
		accrual.setDownloadCount(metaInfo.getPrchsCnt());
		accrual.setScore(metaInfo.getAvgEvluScore());

		return accrual;
	}

	@Override
	public Title generateTitle(MetaInfo metaInfo) {
		Title title = new Title();
		title.setText(metaInfo.getProdNm());
		return title;
	}
}
