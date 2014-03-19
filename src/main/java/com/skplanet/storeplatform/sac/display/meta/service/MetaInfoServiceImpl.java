package com.skplanet.storeplatform.sac.display.meta.service;

import java.lang.reflect.Method;
import java.util.Map;

import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.cache.service.ProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContent;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Meta 정보 조회 Service 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Service
public class MetaInfoServiceImpl implements MetaInfoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

    @Autowired
    private ProductInfoManager productInfoManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getAppMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getAppMetaInfo(Map<String, Object> paramMap) {
        Boolean useCache = (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute("useCache", RequestAttributes.SCOPE_REQUEST);
        if(useCache) {
            AppMetaInfoParam param = new AppMetaInfoParam();
            ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");
            TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");
            DeviceHeader deviceHeader = (DeviceHeader) paramMap.get("deviceHeader");

            param.setChannelId(basicInfo.getProdId());
            param.setLangCd(tenantHeader.getLangCd());
            param.setTenantId(tenantHeader.getTenantId());

            AppMetaInfo app = productInfoManager.getAppMetaInfo(param);
            SubContent subContent = productInfoManager.getSubContent(basicInfo.getProdId(), deviceHeader.getModel());
            MenuInfo menuInfo = null;
            if(basicInfo.getMenuId() != null)
                menuInfo = productInfoManager.getMenuInfo(tenantHeader.getLangCd(), basicInfo.getMenuId(), basicInfo.getProdId());

            MetaInfo me = new MetaInfo();
            setProperties(menuInfo, me); // MenuInfo
            setProperties(subContent, me); // SubContent
            setProperties(app, me); // App

            if(app.getPartParentClsfCd() != null) {
                me.setPartParentClsfCd("PD012301".equals(app.getPartParentClsfCd()) ? "Y" : "N");
            }
            me.setSubContentsId(null);

            return me;
        }
        else
            return this.commonDAO.queryForObject("MetaInfo.getAppMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getMusicMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getMusicMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getMusicMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getVODMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getVODMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getVODMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getEbookComicMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getEbookComicMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getEbookComicMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getWebtoonMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getWebtoonMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getWebtoonMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getShoppingMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getShoppingMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getShoppingMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getFreepassMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getFreepassMetaInfo(Map<String, Object> paramMap) {
		return this.commonDAO.queryForObject("MetaInfo.getFreepassMetaInfo", paramMap, MetaInfo.class);
	}

    private static <T> void setProperties(T prop, MetaInfo meta) {
        if(prop == null || meta == null)
            return;

        for(Method mtd : prop.getClass().getDeclaredMethods()) {
            if(mtd.getName().startsWith("get")) {
                String fld = mtd.getName().substring(3);
                try {
                    Method setMtd = MetaInfo.class.getDeclaredMethod("set"+fld, mtd.getReturnType());
                    setMtd.invoke(meta, mtd.invoke(prop));
                }
                catch(NoSuchMethodException nsme) {

                }
                catch(Exception e) {
                    throw new RuntimeException();
                }
            }
        }
    }

}
