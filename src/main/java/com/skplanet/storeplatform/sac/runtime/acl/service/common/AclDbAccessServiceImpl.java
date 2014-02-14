package com.skplanet.storeplatform.sac.runtime.acl.service.common;

import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

public class AclDbAccessServiceImpl implements AclDbAccessService {

	@Override
	public Interface selectInterfaceById(String interfaceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tenant selectTenantByAuthKey(String authKey) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public String selectInterfaceStatus(String tenantId, String interfaceId) {
        // TODO Implementation.
        return null;
    }
}
