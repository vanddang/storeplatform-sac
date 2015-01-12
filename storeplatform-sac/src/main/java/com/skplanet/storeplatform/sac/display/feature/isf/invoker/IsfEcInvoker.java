package com.skplanet.storeplatform.sac.display.feature.isf.invoker;

import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2EcReq;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2EcRes;
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.vo.IsfEcReq;

/**
 * ISF EC Invoker Interface
 * 
 * Updated on : 2014. 02. 21. Updated by : 윤주영, SK 플래닛.
 */
public interface IsfEcInvoker {
	public ISFRes invoke(IsfEcReq requestVO);

	/**
	 * <pre>
	 * App Codi(V2) ISF 연동.
	 * </pre>
	 * 
	 * @author 이태희, 부르칸.
	 * @param ecReq
	 *            ecReq
	 * @return IsfV2EcRes
	 */
	public IsfV2EcRes invokeV2(IsfV2EcReq ecReq);
}
