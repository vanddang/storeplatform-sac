package com.skplanet.storeplatform.sac.display.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceItem;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.display.common.ContentType;
import com.skplanet.storeplatform.sac.display.common.MetaRingBellType;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.vo.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * 전시 공통 서비스
 * 
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
public class DisplayCommonServiceImpl implements DisplayCommonService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

//	@Autowired
//	private ExistenceSCI existenceSCI;

    @Autowired
    private ExistenceInternalSacSCI existenceInternalSacSCI;

	@Value("#{propertiesForSac['display.previewUrlPrefix']}")
	private String previewPrefix;

	/** The message source accessor. */
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	@Override
	public String getBatchStandardDateString(String tenantId, String listId) {

		return (String) this.commonDAO.queryForObject("DisplayCommon.getBatchStandardDate",
				new BatchStandardDateRequest(tenantId, listId));
	}

	@Override
	public List<MenuItem> getMenuItemList(String prodId, String langCd) {

		return this.commonDAO.queryForList("DisplayCommon.getMenuItemList", new MenuItemReq(prodId, langCd),
				MenuItem.class);
	}

	@Override
	public String getResolutionCode(String resolution) {

		String[] resolutionTemp = resolution.trim().split("\\*");

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("pixel1", resolutionTemp[0]);
		paramMap.put("pixel2", resolutionTemp[1]);

		return (String) this.commonDAO.queryForObject("DisplayCommon.getResolutionCode", paramMap);
	}

	@Override
	public String getDeviceIdType(String deviceId) {

		if (!StringUtils.isEmpty(deviceId)) {

			String repDeviceId = deviceId.replaceAll("-", "");
			String subStrDeviceId = repDeviceId.substring(0, 2);
			Boolean isNum = deviceId.matches("^[0-9]+$");
			String deviceType = "";

			if (repDeviceId.length() < 12 && (subStrDeviceId.equals("01") || subStrDeviceId.equals("98"))) {
				deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN;
			} else if (!isNum && deviceId.length() == 12) {
				deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_MAC;
			} else {
				deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_UUID;
			}

			return deviceType;
		} else {
			return "";
		}

	}

	@Override
	public boolean checkPurchase(String tenantId, String userKey, String deviceKey, String episodeId) {

        ExistenceReq existenceReq = new ExistenceReq();
        existenceReq.setTenantId(tenantId);
        existenceReq.setUserKey(userKey);
        existenceReq.setDeviceKey(deviceKey);
        existenceReq.setExistenceItem(new ArrayList<ExistenceItem>());

        ExistenceItem existenceItem = new ExistenceItem();
        existenceItem.setProdId(episodeId);
        existenceReq.getExistenceItem().add(existenceItem);

        this.log.info("##### [SAC DSP LocalSCI] SAC Purchase Start : existenceSCI.searchExistenceList");
		long start = System.currentTimeMillis();
        ExistenceListRes res = this.existenceInternalSacSCI.searchExistenceList(existenceReq);
		this.log.info("##### [SAC DSP LocalSCI] SAC Purchase End : existenceSCI.searchExistenceList");
		long end = System.currentTimeMillis();
		this.log.info("##### [SAC DSP LocalSCI] SAC Purchase existenceSCI.searchExistenceList takes {} ms",
				(end - start));
        return res.getExistenceListRes() != null && res.getExistenceListRes().size() > 0;
	}

	@Override
	public ExistenceListRes checkPurchaseList(String tenantId, String userKey, String deviceKey,
                                              List<String> episodeIdList) {
		ExistenceScReq existenceScReq = new ExistenceScReq();
		existenceScReq.setTenantId(tenantId);
		existenceScReq.setUserKey(userKey);
		existenceScReq.setDeviceKey(deviceKey);

		List<ExistenceItemSc> itemScList = new ArrayList<ExistenceItemSc>();
		for (String episodeId : episodeIdList) {
			ExistenceItemSc itemSc = new ExistenceItemSc();
			itemSc.setProdId(episodeId);
			itemScList.add(itemSc);
		}
		existenceScReq.setProductList(itemScList);

        ExistenceReq existenceReq = new ExistenceReq();
        existenceReq.setTenantId(tenantId);
        existenceReq.setUserKey(userKey);
        existenceReq.setDeviceKey(deviceKey);
        existenceReq.setExistenceItem(new ArrayList<ExistenceItem>());

        for (String episodeId : episodeIdList) {
            ExistenceItem existenceItem = new ExistenceItem();
            existenceItem.setProdId(episodeId);
            existenceReq.getExistenceItem().add(existenceItem);
        }

		this.log.info("##### [SAC DSP LocalSCI] SAC Purchase Start : existenceSCI.searchExistenceList");
		long start = System.currentTimeMillis();
		ExistenceListRes res = this.existenceInternalSacSCI.searchExistenceList(existenceReq);
		this.log.info("##### [SAC DSP LocalSCI] SAC Purchase End : existenceSCI.searchExistenceList");
		long end = System.currentTimeMillis();
		this.log.info("##### [SAC DSP LocalSCI] SAC Purchase existenceSCI.searchExistenceList takes {} ms",
				(end - start));

		return res;
	}

	@Override
	public String makePreviewUrl(String phyPath) {
		if (StringUtils.isNotEmpty(phyPath))
			return this.previewPrefix + phyPath;
		else
			return "";
	}

	@Override
	@Cacheable(value = "sac:display:tmembershipdcrate:v2", unless = "#result == null")
	public TmembershipDcInfo getTmembershipDcRateForMenu(String tenantId, String topMenuId) {
		Map<String, String> req = new HashMap<String, String>();
		req.put("tenantId", tenantId);
		req.put("policyId", "policy014"); // policy014 - TMembership 할인정책
		req.put("menuId", topMenuId);

		List<TenantSalePolicy> tenantSalePolicies = this.commonDAO.queryForList(
				"DisplayCommon.getTmembershipDcRateForMenu", req, TenantSalePolicy.class);
		TmembershipDcInfo tmembershipDcInfo = new TmembershipDcInfo();
		for (TenantSalePolicy tsp : tenantSalePolicies) {
			if (tsp.getProdTp().equals("OR006311")) {
				tmembershipDcInfo.setNormalDcRate(tsp.getDcRate());
			} else if (tsp.getProdTp().equals("OR006331")) {
				tmembershipDcInfo.setFreepassDcRate(tsp.getDcRate());
			}
		}

		return tmembershipDcInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService#getSupportDeviceInfo(java.lang.String)
	 */
	@Override
	public SupportDevice getSupportDeviceInfo(String deviceModelCd) {
		if (StringUtils.isEmpty(deviceModelCd)) {
			return null;
		}

		return (SupportDevice) this.commonDAO.queryForObject("DisplayCommon.getSupportDeviceInfo", deviceModelCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService#getSupportDeviceInfo(java.lang.String)
	 */
	@Override
	public String getEpubChapterUnit(String bookClsfCd) {
		String chapterUnit = null;
		if (StringUtils.equals(bookClsfCd, DisplayConstants.DP_BOOK_BOOK)) {
			chapterUnit = this.messageSourceAccessor.getMessage("display.chapter.unit.book");
		} else if (StringUtils.equals(bookClsfCd, DisplayConstants.DP_BOOK_SERIAL)) {
			chapterUnit = this.messageSourceAccessor.getMessage("display.chapter.unit.serial");
		} else if (StringUtils.equals(bookClsfCd, DisplayConstants.DP_BOOK_MAGAZINE)) {
			chapterUnit = this.messageSourceAccessor.getMessage("display.chapter.unit.magazine");
		}
		return chapterUnit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService#getSupportDeviceInfo(java.lang.String)
	 */
	@Override
	public String getVodChapterUnit() {
		return this.messageSourceAccessor.getMessage("display.chapter.unit.vod");
	}

    @Override
    public List<UpdateHistory> getUpdateList(String channelId, Integer offset, Integer count) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("channelId", channelId);
        if(offset != null && count != null) {
            req.put("rowStart", offset);
            req.put("rowEnd", offset + count - 1);
        }
        return this.commonDAO.queryForList("DisplayCommon.getUpdateList", req, UpdateHistory.class);
    }

    @Override
    public int getUpdateCount(String channelId) {
        return this.commonDAO.queryForObject("DisplayCommon.getUpdateCount", channelId, Integer.class);
    }

    @Override
    public ProductInfo selectProductInfo(String prodId) {
        ProductInfo info = this.commonDAO.queryForObject("DisplayCommon.selectProductInfo", prodId, ProductInfo.class);

        if(info == null)
            throw new StorePlatformException("SAC_DSP_0005", prodId);

        // ContentType
        info.setContentType(ContentType.forCode(info.getContentsTypeCd()));

        // ProductType
        String svcGrp = StringUtils.defaultString(info.getSvcGrpCd());
        String svcTp = StringUtils.defaultString(info.getSvcTypeCd());
        String metaClsf = StringUtils.defaultString(info.getMetaClsfCd());
        String q = StringUtils.join(new String[]{svcGrp, svcTp, metaClsf}, ".");

        if(q.startsWith("DP000201")) {
            info.setProductType(ProductType.App);
        }
        else if(q.startsWith("DP000203.DP001111")) {
            info.setProductType(ProductType.Music);
        }
        else if(q.startsWith("DP000203.DP001115")) {
            info.setProductType(ProductType.Vod);
        }
        else if(q.matches("DP000203\\.DP001116\\.CT(19|20)")) {
            info.setProductType(ProductType.Webtoon);
        }
        else if(q.matches("DP000203\\.DP001116\\.CT(21|22|24|26)")) {
            info.setProductType(ProductType.EbookComic);
        }
        else if(q.startsWith("DP000204") || q.matches("DP000203\\..*\\.CT(30|31|32|33)")) {
            info.setProductType(ProductType.RingBell);
            info.setMetaType(MetaRingBellType.forCode(info.getMetaClsfCd()));
        }
        else if(q.startsWith("DP000206")) {
            info.setProductType(ProductType.Shopping);
        }
        else if(q.startsWith("DP000207")) {
            info.setProductType(ProductType.Freepass);
        }
        else
            throw new IllegalStateException("[" + q + "] 에 해당하는 상품을 찾을 수 없습니다.");

        return info;
    }
}
