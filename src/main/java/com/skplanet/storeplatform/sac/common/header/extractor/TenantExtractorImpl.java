/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.header.extractor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;

/**
 * 헤더에서 테넌트 정보를 추출해주는 클래스
 *
 * Updated on : 2014. 4. 7.
 * Updated by : 서대영, SK 플래닛
 */
@Component
public class TenantExtractorImpl implements TenantExtractor {

	@Autowired
	private AclDataAccessService dbService;

	@Value("#{propertiesForSac['skp.common.default.language']}")
    private String DEFAULT_LANG;
    @Value("#{propertiesForSac['skp.common.service.language']}")
    private String SERVICE_LANG;

    private static Pattern PATTERN_ACCLANG_ELEM = Pattern.compile("([a-zA-Z]{1,8}(\\-[a-zA-Z]{1,8})?)(;q=[0-9\\.]+)?");     // HTTP/1.1 Accept-Language Spec.

	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.common.header.extractor.TenantExtractor#extractTenant(org.springframework.web.context.request.NativeWebRequest)
	 */
	@Override
	public TenantHeader extract(NativeWebRequest webRequest) {
		TenantHeader tenant = new TenantHeader();

		/*
		 * 0. 기본값으로 세팅 (나중에 제거되야할 로직)
		 */
		tenant.setTenantId("S01"); // Default Tenant : T Stroe
		tenant.setSystemId("S01-01002"); // Default System : Shop Client 3.0

		/*
		 * 1. Tenant ID 세팅 (x-sac-tenant-id 커스텀 헤더, 없으면 x-sac-auth-key로 DB 조회)
		 */
		String tenantId = webRequest.getHeader(CommonConstants.HEADER_TENANT_ID);
		if (StringUtils.isNotBlank(tenantId)) {
			tenant.setTenantId(tenantId);
		} else {
			String authKey = webRequest.getHeader(CommonConstants.HEADER_AUTH_KEY);
			AuthKey authKeyObj = this.dbService.selectAuthKey(authKey);
			String tenantIdFromDb = authKeyObj.getTenantId();
			tenant.setTenantId(tenantIdFromDb);
		}

		/*
		 * 2. System ID 세팅 (x-sac-tenant-id 커스텀 헤더)
		 */
		String systemId = webRequest.getHeader(CommonConstants.HEADER_SYSTEM_ID);
		tenant.setSystemId(systemId);

		/*
		 * 3. Language ID 세팅 (accept-language 표준 헤더)
		 */
		String acceptLanguage = webRequest.getHeader(CommonConstants.HEADER_ACCEPT_LANGUAGE);
		tenant.setLangCd(this.parseAcceptLanguage(acceptLanguage));

		return tenant;
	}

    /**
     * <p>
     * Accept-Language 값을 파싱한다.
     * language-range부분은 무시하며, 헤더값이 없거나 지원하는 언어가 없는 경우 기본 언어를 리턴한다.
     * </p>
     * @param hdr 헤더값
     * @return 언어코드
     */
    private String parseAcceptLanguage(String hdr) {

        if(StringUtils.isEmpty(hdr))
            return this.DEFAULT_LANG;

        HashSet<String> supportLanges = new HashSet<String>(Arrays.asList(this.SERVICE_LANG.split(",")));
        for(String str : hdr.split(",")) {
            Matcher m = PATTERN_ACCLANG_ELEM.matcher(str.trim());
            if(m.matches()) {
                if(supportLanges.contains(m.group(1)))
                    return m.group(1);
            }
        }
        return this.DEFAULT_LANG;
    }

}
