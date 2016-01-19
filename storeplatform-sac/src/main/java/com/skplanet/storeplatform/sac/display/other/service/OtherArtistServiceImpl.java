/**
 * 
 */
package com.skplanet.storeplatform.sac.display.other.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.common.header.extractor.HeaderExtractor;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 아티스트별 정보 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
@Service
public class OtherArtistServiceImpl implements OtherArtistService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MusicInfoGenerator musicGenerator;

	@Autowired
	HeaderExtractor header;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.other.service.OtherArtistService#searchArtistDetail(com.skplanet.storeplatform
	 * .sac.client.display.vo.other.OtherArtistReq, com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public OtherArtistRes searchArtistDetail(OtherArtistReq req, SacRequestHeader header) {
		CommonResponse commonResponse = new CommonResponse();
		Contributor contributor = null;
		OtherArtistRes res = new OtherArtistRes();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("artistId", req.getArtistId());
		paramMap.put("tenantId", header.getTenantHeader().getTenantId());

		MetaInfo metaInfo;
		
		if (req.getUserKey() == null) {
			metaInfo = commonDAO.queryForObject("OtherArtist.searchArtistDetail", paramMap, MetaInfo.class);
		} else {
			paramMap.put("userKey", req.getUserKey());
			metaInfo = commonDAO.queryForObject("OtherArtist.searchArtistDetailWithLikeYn", paramMap, MetaInfo.class);
		}

		if (metaInfo == null) {
			commonResponse.setTotalCount(0);
			contributor = new Contributor();

		} else {
			commonResponse.setTotalCount(1);
			contributor = this.musicGenerator.generateArtistDetailContributor(metaInfo);
			contributor.setLikeYn(metaInfo.getLikeYn());
		}
		
		res.setContributor(contributor);
		res.setCommonResponse(commonResponse);

		return res;
	}

}
