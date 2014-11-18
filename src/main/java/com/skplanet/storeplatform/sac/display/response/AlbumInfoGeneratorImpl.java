package com.skplanet.storeplatform.sac.display.response;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;


/**
 * 앨범 메타 생성 클래스
 * 
 * Updated on : 2014. 10. 23.
 * Updated by : 김희민
 */
@Component
public class AlbumInfoGeneratorImpl implements AlbumInfoGenerator{

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	
	
	@Override
	public List<Identifier> generateIdentifierList(AlbumMeta albumMeta) {
		List<Identifier> identifierList = new ArrayList<Identifier>();
		Identifier identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_ALBUM_IDENTIFIER_CD, albumMeta.getProdId());
		identifierList.add(identifier);
		return identifierList;
	}

	
	@Override
	public Title generateTitle(AlbumMeta albumMeta) {
		Title title = new Title();
		title.setText(albumMeta.getProdNm());
		return title;
	}

	
	@Override
	public List<Menu> generateMenuList(AlbumMeta albumMeta) {
		
		List<Menu> menuList = new ArrayList<Menu>();
		
		Menu menu;
		
		menu = new Menu();
		menu.setId(albumMeta.getMenuId());
		menu.setName(albumMeta.getMenuNm());
		menu.setDesc(albumMeta.getMenuDesc());
		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(albumMeta.getTopMenuId());
		menu.setName(albumMeta.getTopMenuNm());
		menuList.add(menu);
		
		return menuList;
	}

	
	@Override
	public List<Source> generateSourceList(AlbumMeta albumMeta) {
		List<Source> sourceList = new ArrayList<Source>();
		
		Source source = new Source();
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		String url = albumMeta.getImgFilePath() + albumMeta.getImgFileNm();
		source.setMediaType(DisplayCommonUtil.getMimeType(url));
		source.setUrl(url);
		sourceList.add(source);

        return sourceList;
	}

	
	@Override
	public Rights generateRights(AlbumMeta albumMeta) {
		Rights rights = new Rights();
		rights.setGrade(albumMeta.getProdGrdCd());
		return rights;
	}

	
	@Override
	public Contributor generateContributor(AlbumMeta albumMeta) {
		Contributor contributor = new Contributor();
		if (StringUtils.isNotEmpty(albumMeta.getArtist1Id())) {
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_ARTIST_IDENTIFIER_CD
					, albumMeta.getArtist1Id()));
			contributor.setIdentifierList(identifierList);
		}
		if (StringUtils.isNotEmpty(albumMeta.getArtistImgFilePath())) {
			List<Source> sourceList = new ArrayList<Source>();
			sourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_ARTIST_THUMBNAIL
					, albumMeta.getArtistImgFilePath()));
			contributor.setSourceList(sourceList);
		}
		contributor.setName(albumMeta.getArtist1Nm());
		contributor.setPublisher(albumMeta.getChnlCompNm());
		contributor.setAgency(albumMeta.getAgencyNm());
		contributor.setAlbumType(albumMeta.getAlbumTypeNm());
		return contributor;
	}

	@Override
	public List<Date> generateDateList(AlbumMeta albumMeta) {
        List<Date> dateList = new ArrayList<Date>();
        if(StringUtils.isNotEmpty(albumMeta.getRegDt())) {
        	dateList.add(commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, albumMeta.getRegDt()));
        }
        if (StringUtils.isNotEmpty(albumMeta.getIssueDay())) {
            dateList.add(commonGenerator.generateDate(DisplayConstants.DP_DATE_ISSUE, albumMeta.getIssueDay()));
        }
        
        return dateList.isEmpty() ? null : dateList;
	}


}
