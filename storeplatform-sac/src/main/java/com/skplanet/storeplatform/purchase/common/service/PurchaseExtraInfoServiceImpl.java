package com.skplanet.storeplatform.purchase.common.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.common.vo.ExtraInfo;
import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonCode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 구매 추가 정보 테이블(TB_PR_PRCHS_EXTRA_INFO) 저장/조회
 *
 * Updated on : 2016. 1. 15. Updated by : eastaim, SK planet.
 */
@Service
public class PurchaseExtraInfoServiceImpl implements PurchaseExtraInfoService {

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDao;

    /**
     * 데이터 저장
     * @param extraInfo
     */
	@Override
	public void createExtraInfo(ExtraInfo extraInfo) {
        commonDao.insert("ExtraInfo.createExtraInfo", extraInfo);
	}

    /**
     * 데이터 저장
     * @param extraInfo
     */
    @Override
    public void modifyExtraInfo(ExtraInfo extraInfo) {
        commonDao.update("ExtraInfo.modifyExtraInfo", extraInfo);
    }

}
