/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.PreviewProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.SourceVO;

/**
 * Interface Message Preview Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(PreviewProto.Preview.class)
public class PreviewVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private SourceVO source;

	public SourceVO getSource() {
		return this.source;
	}

	public void setSource(SourceVO source) {
		this.source = source;
	}

}
