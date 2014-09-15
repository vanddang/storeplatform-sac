package com.skplanet.storeplatform.sac.display.common.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.GradeInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceItem;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.display.common.EbookComicType;
import com.skplanet.storeplatform.sac.display.common.MetaRingBellType;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.VodType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.util.*;

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
	private SearchUserSCI searchUserSCI;
	
    @Autowired
    private ExistenceInternalSacSCI existenceInternalSacSCI;

	@Value("#{propertiesForSac['display.previewUrlPrefix']}")
	private String previewPrefix;

	@Value("#{propertiesForSac['display.epub.previewUrlPrefix']}")
	private String previewEpubPrefix;

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
	public String makeEpubPreviewUrl(String phyPath) {
		if (StringUtils.isNotEmpty(phyPath))
			return this.previewEpubPrefix + phyPath;
		else
			return "";
	}

	@Override
	@Cacheable(value = "sac:display:tmembershipdcrate:v2", unless = "#result == null")
	public TmembershipDcInfo getTmembershipDcRateForMenu(String tenantId, String topMenuId) {
        if(topMenuId == null)
            throw new IllegalArgumentException();

		Map<String, String> req = new HashMap<String, String>();
		req.put("tenantId", tenantId);
		req.put("policyId", "policy014"); // policy014 - TMembership 할인정책

        if(!topMenuId.equals(DisplayConstants.REQUEST_TMEMBERSHIP_ALL_MENU))
		    req.put("menuId", topMenuId);

		List<TenantSalePolicy> tenantSalePolicies = this.commonDAO.queryForList(
				"DisplayCommon.getTmembershipDcRateForMenu", req, TenantSalePolicy.class);

        if (topMenuId.equals(DisplayConstants.REQUEST_TMEMBERSHIP_ALL_MENU)) {
            if (CollectionUtils.isNotEmpty(tenantSalePolicies)) {
                TenantSalePolicy maxDcInfo = Collections.max(tenantSalePolicies, new Comparator<TenantSalePolicy>() {
                    @Override
                    public int compare(TenantSalePolicy tenantSalePolicy, TenantSalePolicy tenantSalePolicy2) {
                        if(tenantSalePolicy.getDcRate() < tenantSalePolicy2.getDcRate())
                            return -1;
                        else if(tenantSalePolicy.getDcRate() > tenantSalePolicy2.getDcRate())
                            return 1;
                        else
                            return 0;
                    }
                });
                tenantSalePolicies = Arrays.asList(maxDcInfo);
            }
        }

        TmembershipDcInfo tmembershipDcInfo = new TmembershipDcInfo();
        for (TenantSalePolicy tsp : tenantSalePolicies) {
            if ("OR006311".equals(tsp.getProdTp())) {
                tmembershipDcInfo.setNormalDcRate(tsp.getDcRate());
            } else if ("OR006331".equals(tsp.getProdTp())) {
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
    public ProductInfo getProductInfo(String prodId) {
        ProductInfo info = this.commonDAO.queryForObject("DisplayCommon.selectProductInfo", prodId, ProductInfo.class);

        if(info == null)
            throw new StorePlatformException("SAC_DSP_0005", prodId);

        // ProductType
        String svcGrp = StringUtils.defaultString(info.getSvcGrpCd());
        String svcTp = StringUtils.defaultString(info.getSvcTypeCd());
        String metaClsf = StringUtils.defaultString(info.getMetaClsfCd());
        String topMenu = StringUtils.defaultString(info.getTopMenuId());

        ProductTypeInfo basicInfo = this.getProductTypeInfo(svcGrp, svcTp, metaClsf, topMenu);
        info.setProductType(basicInfo.getProductType());
        info.setSeries(basicInfo.isSeries());
        info.setSubType(basicInfo.getSubType());

        return info;
    }

    @Override
    public ProductTypeInfo getProductTypeInfo(String svcGrp, String svcTp, String metaClsf, String topMenu) {
        if(StringUtils.isEmpty(svcGrp))
            throw new IllegalArgumentException("svcGrp cannot be null.");

        String q = StringUtils.join(new String[]{svcGrp, svcTp, metaClsf}, ".");

        ProductTypeInfo info = new ProductTypeInfo();
        if(q.startsWith("DP000201")) {
            info.setProductType(ProductType.App);
        }
        else if(q.startsWith("DP000203.DP001111")) {
            info.setProductType(ProductType.Music);
        }
        else if(q.startsWith("DP000203.DP001115")) {
            info.setProductType(ProductType.Vod);
            if(DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenu))
                info.setSubType(VodType.Tv);
            else
                info.setSubType(VodType.Movie);
        }
        else if(q.matches("DP000203\\.DP001116.*")) {
            if ("DP13".equals(topMenu) || "DP14".equals(topMenu)) {
                info.setProductType(ProductType.EbookComic);
                info.setSubType("DP13".equals(topMenu) ? EbookComicType.Ebook : EbookComicType.Comic);
            }
            else if ("DP26".equals(topMenu)) {
                info.setProductType(ProductType.Webtoon);
            }
        }
        else if(q.matches("(DP000204|DP000203)\\..*\\.CT(30|31|32|33)")) {
            info.setProductType(ProductType.RingBell);
            info.setSubType(MetaRingBellType.forCode(metaClsf));
        }
        else if(q.startsWith("DP000206")) {
            info.setProductType(ProductType.Shopping);
        }
        else if(q.startsWith("DP000207")) {
            info.setProductType(ProductType.Freepass);
        }
        else
            throw new StorePlatformException("SAC_DSP_0025", svcGrp, svcTp, metaClsf);

        // 시리즈 여부 반영
        info.setSeries(DisplayConstants.SET_SERIES_META.contains(metaClsf));

        return info;
    }
    
    /**
     * UserKey를 이용하여 회원등급 조회.
     * @param userKey
     * @return
     */
	@Override
	public GradeInfoSac getUserGrade(String userKey) {
		if(StringUtils.isEmpty(userKey)) return null;
		SearchUserGradeSacReq gradeReq = new SearchUserGradeSacReq();
		gradeReq.setUserKey(userKey);
		SearchUserGradeSacRes gradeRes = null;
		try {
			gradeRes = searchUserSCI.searchUserGrade(gradeReq);
		} catch(Exception e) {
			//ignore. 오류 무시.
		}
        return gradeRes != null ? gradeRes.getGradeInfoSac() : null;
	}
}
