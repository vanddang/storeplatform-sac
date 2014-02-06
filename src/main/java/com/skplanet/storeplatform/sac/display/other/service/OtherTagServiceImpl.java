/**
 * 
 */
package com.skplanet.storeplatform.sac.display.other.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTagReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTagRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Tag;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 태그 목록 조회 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
@Service
public class OtherTagServiceImpl implements OtherTagService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 태그 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return OtherTagRes
	 */
	@Override
	public OtherTagRes searchTagList(OtherTagReq req, SacRequestHeader header) {
		CommonResponse commonResponse = new CommonResponse();
		OtherTagRes res = new OtherTagRes();
		List<Tag> tagList = new ArrayList<Tag>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sellerTagCd", DisplayConstants.DP_SELLER_TAG_CD);
		paramMap.put("keywordTagCd", DisplayConstants.DP_KEYWORD_TAG_CD);
		paramMap.put("shoppingCouponTagCd", DisplayConstants.DP_SHOPPING_COUPON_CD);
		paramMap.put("prodId", req.getProductId());

		List<MetaInfo> metaInfoList = this.commonDAO.queryForList("OtherTag.searchTagList", paramMap, MetaInfo.class);
		for (MetaInfo metaInfo : metaInfoList) {
			Tag tag = new Tag();
			tag.setTagCd(metaInfo.getTagCd());
			tag.setTagTypeCd(metaInfo.getTagTypeCd());
			tag.setText(metaInfo.getTagNm());
			tagList.add(tag);
		}
		commonResponse.setTotalCount(tagList.size());
		res.setCommonResponse(commonResponse);
		res.setTagList(tagList);
		return res;
	}

}
