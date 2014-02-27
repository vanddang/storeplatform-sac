package com.skplanet.storeplatform.sac.display.music.controller.binder;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.music.vo.SubContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 음악 상세정보 VO매퍼
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@org.springframework.stereotype.Component
public class MusicDetailBinderImpl implements MusicDetailBinder {

    @Override
    public void mapMusic(Product product, MusicDetail musicDetail, List<SubContent> contentList) {
        Music music = new Music();

        // Music Source List
        music.setSourceList(new ArrayList<Source>());
        for (SubContent sc : contentList) {
            Source src = new Source();
            src.setSize(sc.getFileSize());
            if("PD009711".equals(sc.getDpPgQultCd()))
                src.setType(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_192);
            else if("PD009712".equals(sc.getDpPgQultCd()))
                src.setType(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_128);

            music.getSourceList().add(src);
        }

        music.setServiceList(new ArrayList<Service>(
                Arrays.asList(
                        new Service("mp3", "Y"),
                        new Service("bell", musicDetail.isBellSprtYn() ? "Y" : "N"),
                        new Service("ring", musicDetail.isColorringSprtYn() ? "Y" : "N")
                )
        ));

        product.setMusic(music);
    }

    @Override
    public void mapThumbnail(Product product, MusicDetail musicDetail) {
        product.setSourceList(new ArrayList<Source>());
        if(!StringUtils.isEmpty(musicDetail.getThmPath())) {
            Source source = new Source();
            source.setUrl(musicDetail.getThmPath());
            source.setMediaType(DisplayCommonUtil.getMimeType(musicDetail.getThmPath()));
            product.getSourceList().add(source);
        }
    }

    @Override
    public void mapMenu(Product product, List<MenuItem> menuList) {
        product.setMenuList(new ArrayList<Menu>());
        product.getMenuList().add(new Menu("DP000516", "music", "topClass"));
        for (MenuItem mi : menuList) {

            if (!mi.isInfrMenu()) {
                Menu menu = new Menu();
                menu.setId(mi.getMenuId());
                menu.setName(mi.getMenuNm());

                product.getMenuList().add(menu);
            }
        }

        product.getMenuList().add(new Menu("CT25", "mp3", "metaClass"));
    }

    @Override
    public void mapBasicInfo(Product product, MusicDetail musicDetail) {

        product.setIdentifierList(new ArrayList<Identifier>());
        product.getIdentifierList().add(new Identifier("channel", musicDetail.getChnlId()));
        product.getIdentifierList().add(new Identifier("episode", musicDetail.getEpsdId()));
        product.getIdentifierList().add(new Identifier("song", musicDetail.getOutsdContentsId()));

        Title title = new Title();
        title.setText(musicDetail.getProdNm());
        product.setTitle(title);
        Price price = new Price();
        price.setText(musicDetail.getProdAmt());
        product.setPrice(price);
        Accrual accrual = new Accrual();
        accrual.setVoterCount(musicDetail.getPaticpersCnt());
        accrual.setDownloadCount(musicDetail.getDwldCnt());
        accrual.setScore(musicDetail.getAvgEvluScore());
        product.setAccrual(accrual);
        Rights rights = new Rights();
        rights.setGrade(musicDetail.getProdGrd());
        product.setRights(rights);

        if(!DisplayConstants.DP_SALE_STAT_ING.equals(musicDetail.getProdStatusCd()))
            product.setSalesStatus("restricted");

        // Contributor
        Contributor contributor = new Contributor();
        contributor.setIdentifier(new Identifier("artist", musicDetail.getArtist1Id()));
        contributor.setName(musicDetail.getArtist1Nm());

        contributor.setAlbum(musicDetail.getArtist3Nm());
        contributor.setPublisher(musicDetail.getChnlCompNm());
        contributor.setAgency(musicDetail.getAgencyNm());
        product.setContributor(contributor);
    }
}
