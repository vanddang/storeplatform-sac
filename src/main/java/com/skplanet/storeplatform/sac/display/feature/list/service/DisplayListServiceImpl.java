package com.skplanet.storeplatform.sac.display.feature.list.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.list.DisplayListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.list.DisplayListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.DisplayList;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListCriteria;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListFromDB;

@org.springframework.stereotype.Service
public class DisplayListServiceImpl implements DisplayListService{
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public DisplayListSacRes searchList(DisplayListSacReq requestVO, SacRequestHeader header) {
		DisplayListSacRes response = new DisplayListSacRes();
		DisplayListCriteria lpCriteria = new DisplayListCriteria(requestVO, header.getTenantHeader().getTenantId());
		
		List<DisplayListFromDB> listsFromDB = this.commonDAO.queryForList( "DisplayList.selectDisplayList", lpCriteria, DisplayListFromDB.class);
		
		setStartKeyIntoResponse(response, listsFromDB);
		setDisplayListIntoResponse(header, response, lpCriteria, listsFromDB);
		setHasNext(requestVO, response, listsFromDB);
		response.setCount(response.getLists().size());
		return response;
	}

	private void setHasNext(DisplayListSacReq requestVO, DisplayListSacRes response, List<DisplayListFromDB> listsFromDB) {
		if(hasNext(requestVO, listsFromDB)){
			response.setHasNext("Y");
			removeRedundantLastProduct(response);
		} else
			response.setHasNext("N");
	}

	private boolean hasNext(DisplayListSacReq requestVO, List<DisplayListFromDB> listsFromDB) {
		return listsFromDB.size()>requestVO.getCount();
	}

	private void removeRedundantLastProduct(DisplayListSacRes response) {
		List<DisplayList> list = response.getLists();
		list.remove(list.size()-1);
	}


	private void setDisplayListIntoResponse(SacRequestHeader header, DisplayListSacRes response,
											DisplayListCriteria lpCriteria, List<DisplayListFromDB> listsFromDB) {
		List<DisplayList> displayLists = new ArrayList<DisplayList>();
		for(DisplayListFromDB displayList: listsFromDB){
			DisplayList dl = getDisplayList(header, displayList);
			if(dl!=null)
				displayLists.add(dl);
		}
		
		response.setLists(displayLists);
	}

	private DisplayList getDisplayList(SacRequestHeader header, DisplayListFromDB listParam) {
		DisplayList dl = new DisplayList();
		
		setupSourceList(listParam, dl);
		dl.setListId(listParam.getListId());
		dl.setTitle(listParam.getListNm());
		dl.setEtcProp(listParam.getEtcProp());
		return dl;
	}

	private void setupSourceList(DisplayListFromDB listParam, DisplayList dl) {
		String path = listParam.getImgPath();
		if (StringUtils.isNotEmpty(path)) {
			List<Source> sl = new ArrayList<Source>();
			Source source = new Source();
			source = new Source();
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setMediaType(DisplayCommonUtil.getMimeType(path));
			source.setUrl(listParam.getImgPath());
			sl.add(source);
			dl.setSourceList(sl);
		}
	}
	
	private void setStartKeyIntoResponse(DisplayListSacRes response, List<DisplayListFromDB> list) {
		if(list.size()==0)
			return;
		String startKey = list.get(list.size()-1).getExpoOrd() + "/";
		startKey       += list.get(list.size()-1).getExpoOrdSub();
		response.setStartKey(startKey);
	}

	
}
