/**
 * 
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.stat.StatActionRes;
import com.skplanet.storeplatform.sac.client.display.vo.stat.StatDetailRes;
import com.skplanet.storeplatform.sac.display.stat.vo.StatDetail;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 15.
 * Updated by : 1002177
 */
@Service
public class StatServiceImpl implements StatService{
	private final String ACTION_TYPE_LIKE = "DP01220001";
	private final String ACTION_TYPE_SHAR = "DP01220002";
	private final String KEY_PREFIX_CARD = "CRD";
	private final String STAT_CLSF_CARD = "DP01210001";
	private final String STAT_CLSF_PROD = "DP01210002";
	
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public StatDetailRes getStatDetail(String tenantId, String key) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("key", key);
		params.put("clsf", statClsfOf(key));
		params.put("tenantId", tenantId);
		
		StatDetail statDetail = this.commonDAO.queryForObject("Stat.detail", params, StatDetail.class);
		
		if (statDetail == null) {
			return null;
		}
		
		StatDetailRes res = new StatDetailRes();
		res.setClsf(statDetail.getClsf());
		res.setCntBrws(statDetail.getCntBrws());
		res.setCntLike(statDetail.getCntLike());
		res.setCntShar(statDetail.getCntShar());
		res.setKey(statDetail.getKey());
		
		return res;
	}


	@Override
	public StatActionRes createStat(String tenantId, String userKey, String key,
			String regCaseCd) {
		
		insertLikeIfItIsLikeAction(tenantId, userKey, key, regCaseCd);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tenantId", tenantId);
		params.put("key", key);
		params.put("clsf", statClsfOf(key));
		params.put("regCaseCd", regCaseCd);

		this.commonDAO.update("Stat.countUp", params);
		StatActionRes res = new StatActionRes();
		res.setAction("CREATE");
		return res;
	}


	@Override
	public StatActionRes removeStat(String tenantId, String userKey, String key,
			String regCaseCd) {

		deleteLikeIfItIsLikeAction(tenantId, userKey, key, regCaseCd);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tenantId", tenantId);
		params.put("key", key);
		params.put("clsf", statClsfOf(key));
		params.put("regCaseCd", regCaseCd);

		this.commonDAO.update("Stat.countDown", params);

		StatActionRes res = new StatActionRes();
		res.setAction("REMOVE");
		return res;
	}
	
	private void insertLikeIfItIsLikeAction(String tenantId, String userKey, String key, String regCaseCd) {
		if (!ACTION_TYPE_LIKE.equals(regCaseCd))
			return;
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tenantId", tenantId);
		params.put("userKey", userKey);
		params.put("key", key);
		params.put("clsf", statClsfOf(key));
		
		try {
			this.commonDAO.insert("StatLike.insert", params);
		}catch (DuplicateKeyException e) {
			throw new StorePlatformException("SAC_DSP_0027");
		}
		
	}
	
	private void deleteLikeIfItIsLikeAction(String tenantId, String userKey, String key, String regCaseCd) {
		if (!ACTION_TYPE_LIKE.equals(regCaseCd))
			return;
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tenantId", tenantId);
		params.put("userKey", userKey);
		params.put("key", key);
		params.put("clsf", statClsfOf(key));
		
		int count = this.commonDAO.delete("StatLike.delete", params);
		if (0 == count) {
			throw new StorePlatformException("SAC_DSP_0028");
		}
		
	}
	
	private String statClsfOf(String key) {
		return key.startsWith(KEY_PREFIX_CARD) ? "DP01210001" : "DP01210002";
	}

}
