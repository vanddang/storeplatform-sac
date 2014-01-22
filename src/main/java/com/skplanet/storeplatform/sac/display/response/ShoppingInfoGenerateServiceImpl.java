package com.skplanet.storeplatform.sac.display.response;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
@Transactional
public class ShoppingInfoGenerateServiceImpl implements ShoppingInfoGenerateService {

	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		Identifier identifier = new Identifier();
		identifier.setType(DisplayConstants.DP_BRAND_IDENTIFIER_CD);
		identifier.setText(metaInfo.getBrandId());
		contributor.setIdentifier(identifier);
		contributor.setName(metaInfo.getBrandName()); // 브랜드명
		return contributor;
	}

	@Override
	public SalesOption generateSalesOption(MetaInfo metaInfo) {
		SalesOption salesOption = new SalesOption();
		salesOption.setType(metaInfo.getProdCaseCd());
		return salesOption;
	}

	@Override
	public Rights generateRights(MetaInfo metaInfo) {
		Rights rights = new Rights();
		Date date = new Date();
		rights.setGrade(metaInfo.getProdGrdCd());
		date.setType(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_NM);
		date.setText(metaInfo.getApplyStartDt() + "/" + metaInfo.getApplyEndDt());
		rights.setDate(date);
		return rights;
	}

	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		accrual.setDownloadCount(metaInfo.getPrchsCnt());
		return accrual;
	}

	@Override
	public Price generatePrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setFixedPrice(metaInfo.getProdNetAmt());
		price.setDiscountRate(metaInfo.getDcRate());
		price.setText(metaInfo.getProdAmt());
		return price;
	}

}
