package com.skplanet.storeplatform.sac.display.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Component
public class CommonMetaInfoGeneratorImpl implements CommonMetaInfoGenerator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Identifier generateIdentifier(String type, String text) {
		Identifier identifier = new Identifier();
		identifier.setType(type);
		identifier.setText(text);

		return identifier;
	}

	@Override
	public Identifier generateIdentifier(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = null;
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getProdId());
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)
				&& DisplayConstants.DP_SHOPPING_COUPON_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			identifier = this.generateIdentifier(DisplayConstants.DP_CATALOG_IDENTIFIER_CD, metaInfo.getCatalogId());
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
		}
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

	@Override
	public List<Identifier> generateSpecificProductIdentifierList(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = null;
		List<Identifier> identifierList = new ArrayList<Identifier>();

		this.log.debug("##### generateSpecificProductIdentifierList contentsTypeCd : {}", contentsTypeCd);
		// Episode ID 기준검색일 경우
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			this.log.debug("##### Episode & Channel Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId());
			identifierList.add(identifier);
			identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
			identifierList.add(identifier);
		}
		// Catalog ID 기준 검색일 경우
		else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)
				&& DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			this.log.debug("##### Catalog & Episode Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId());
			identifierList.add(identifier);
			identifier = this.generateIdentifier(DisplayConstants.DP_CATALOG_IDENTIFIER_CD, metaInfo.getCatalogId());
			identifierList.add(identifier);
		}
		// Channel ID 기준 검색일 경우
		else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			this.log.debug("##### Channel Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
			identifierList.add(identifier);
		}
		return identifierList;
	}

	@Override
	public Distributor generateDistributor(MetaInfo metaInfo) {
		Distributor distributor = new Distributor();
		distributor.setName(metaInfo.getExpoSellerNm());
		distributor.setTel(metaInfo.getExpoSellerTelNo());
		distributor.setEmail(metaInfo.getExpoSellerEmail());
		return distributor;
	}
}
