package com.skplanet.storeplatform.sac.display.feature.outproduct.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.outproduct.list.OutProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.outproduct.list.OutProductListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.OutProduct;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListCriteria;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListFromDB;
import com.skplanet.storeplatform.sac.display.feature.outproduct.vo.OutProductDbResultMap;
import com.skplanet.storeplatform.sac.display.feature.outproduct.vo.OutProductListCriteria;

@Service
public class OutProductListServiceImpl implements OutProductListService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;


	@Override
	public OutProductListSacRes searchOutProductList(OutProductListSacReq requestVO, SacRequestHeader header) {
		String tenantId = header.getTenantHeader().getTenantId();
		String listId = requestVO.getListId();
		String stdDt = getBatchStdDateStringFromDB(tenantId, listId);
		Integer lastRank = requestVO.getStartKey();
		Integer countFromReq = requestVO.getCount();

		List<OutProductDbResultMap> prodListsFromDB = getProductListFromDb(tenantId, listId, stdDt, lastRank, countFromReq);
		OutProductListSacRes res = makeResponseFrom(prodListsFromDB);
		List<DisplayListFromDB> dplistFromDB = getDisplayListFromDb(tenantId, listId);
		setRemainsIntoResponse(listId, countFromReq, prodListsFromDB, res, dplistFromDB);
		return res;
	}

	private void setRemainsIntoResponse(String listId, Integer countFromReq, List<OutProductDbResultMap> prodListsFromDB, OutProductListSacRes res,
			List<DisplayListFromDB> dplistFromDB) {
		if(dplistFromDB!=null && !dplistFromDB.isEmpty()){
    		setListNm(res, dplistFromDB);
    		setHasNextAndRemoveLastProduct(countFromReq, prodListsFromDB, res);
    		setStartKey(prodListsFromDB, res);
    		res.setCount(res.getProductList().size());
		} else {
			res.setCount(0);
			res.setProductList(new ArrayList<OutProduct>());
		}
		res.setListId(listId);
	}

	private List<OutProductDbResultMap> getProductListFromDb(String tenantId, String listId, String stdDt, Integer lastRank, Integer countFromReq) {
		OutProductListCriteria criteria = makeDbCriteria(tenantId, listId, stdDt, lastRank, countFromReq);
		List<OutProductDbResultMap> prodListsFromDB = commonDAO.queryForList( "OutProductList.selectOutProdList", criteria, OutProductDbResultMap.class);
		return prodListsFromDB;
	}

	private List<DisplayListFromDB> getDisplayListFromDb(String tenantId, String listId) {
		DisplayListCriteria listCriteria = new DisplayListCriteria(tenantId, listId, "N", 1);
		List<DisplayListFromDB> dplistFromDB = commonDAO.queryForList( "DisplayList.selectDisplayList", listCriteria, DisplayListFromDB.class);
		return dplistFromDB;
	}

	private void setStartKey(List<OutProductDbResultMap> prodListsFromDB, OutProductListSacRes res) {
		if(prodListsFromDB==null||prodListsFromDB.isEmpty())
			return;
		res.setStartKey(prodListsFromDB.get(prodListsFromDB.size()-1).getRank().toString());
	}

	private void setListNm(OutProductListSacRes res, List<DisplayListFromDB> dplistFromDB) {
		res.setListNm(dplistFromDB.get(0).getListNm());
	}

	private void setHasNextAndRemoveLastProduct(Integer countFromReq, List<OutProductDbResultMap> prodListsFromDB, OutProductListSacRes res) {
		if(prodListsFromDB!=null && countFromReq<prodListsFromDB.size()) {
			res.setHasNext("Y");
			res.getProductList().remove(res.getProductList().size()-1);
		} else {
			res.setHasNext("N");
		}
	}

	private OutProductListSacRes makeResponseFrom(List<OutProductDbResultMap> listsFromDB) {
		OutProductListSacRes res = new OutProductListSacRes();
		List<OutProduct> list = new ArrayList<OutProduct>();
		for(OutProductDbResultMap rm: listsFromDB) {
			OutProduct p = makeResponse(rm);
			list.add(p);
		}
		res.setProductList(list);
		
		return res;
	}

	private OutProductListCriteria makeDbCriteria(String tenantId, String listId, String stdDt, Integer lastRank, Integer count) {
		OutProductListCriteria criteria = new OutProductListCriteria();
		criteria.setTenantId(tenantId);
		criteria.setListId(listId);
		criteria.setLastRank(lastRank);
		criteria.setStdDt(stdDt);
		criteria.setCount(count);
		return criteria;
	}

	private OutProduct makeResponse(OutProductDbResultMap rm) {
		OutProduct p = new OutProduct();

		p.setDetailUrl(rm.getDetailUrl());
		p.setRank(rm.getRank());
		p.setTitle(rm.getTitle());
		p.setThumbnailUrl(rm.getThumbnailUrl());
		p.setThumbnailPath(rm.getThumbnailPath());
		p.setScore(rm.getScore());
		p.setVoterCount(rm.getVoterCount());
		p.setAccrualCount(rm.getAccrualCount());
		p.setRankChange(rm.getRankChange());
		p.setCategory(rm.getCategory());
		p.setCategorySub(rm.getCategorySub());
		p.setRightGrade(rm.getRightGrade());
		p.setPrice(rm.getPrice());
		p.setPriceRent(rm.getPriceRent());
		p.setPriceFixed(rm.getPriceFixed());
		p.setDiscountRate(rm.getDiscountRate());
		p.setSupport(rm.getSupport());
		p.setChapter(rm.getChapter());
		p.setSubCount(rm.getSubCount());
		p.setDescription(rm.getDescription());
		p.setRunningTime(rm.getRunningTime());
		p.setContributorName(rm.getContributorName());
		p.setContributorDirector(rm.getContributorDirector());
		p.setContributorArtist(rm.getContributorArtist());
		p.setContributorAuthor(rm.getContributorAuthor());
		p.setContributorPainter(rm.getContributorPainter());
		p.setContributorChannel(rm.getContributorChannel());
		p.setContributorParter(rm.getContributorParter());
		p.setAlbumName(rm.getAlbumName());
		p.setSaleDateInfo(rm.getSaleDateInfo());
		p.setDateRelease(rm.getDateRelease());
		p.setBookType(rm.getBookType());
		p.setSerialDate(rm.getSerialDate());
		p.setSerialWeek(rm.getSerialWeek());
		p.setStatus(rm.getStatus());
		p.setDelivery(rm.getDelivery());
		p.setOrigin(rm.getOrigin());
		p.setPreviewUrl(rm.getPreviewUrl());
		p.setRecommendReason(rm.getRecommendReason());
		p.setFreeDefined01(rm.getFreeDefined01());
		p.setFreeDefined02(rm.getFreeDefined02());
		p.setFreeDefined03(rm.getFreeDefined03());
		p.setFreeDefined04(rm.getFreeDefined04());
		p.setFreeDefined05(rm.getFreeDefined05());
		p.setFreeDefined06(rm.getFreeDefined06());
		p.setFreeDefined07(rm.getFreeDefined07());
		p.setFreeDefined08(rm.getFreeDefined08());
		p.setFreeDefined09(rm.getFreeDefined09());
		p.setFreeDefined10(rm.getFreeDefined10());
		return p;
	}

	private String getBatchStdDateStringFromDB(String tenantId, String listId) {
		String stdDt = displayCommonService.getBatchStandardDateString(tenantId, listId);
		if (StringUtils.isEmpty(stdDt))
			throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
		return stdDt;
	}
}
