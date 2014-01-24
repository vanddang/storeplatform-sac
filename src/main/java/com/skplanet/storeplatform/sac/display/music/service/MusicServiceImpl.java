package com.skplanet.storeplatform.sac.display.music.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.music.vo.SubContent;
import com.skplanet.storeplatform.sac.display.music.vo.SubContentReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 음악 상세보기
 * <p/>
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional
public class MusicServiceImpl implements MusicService {

    protected Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private DisplayCommonService commonService;

    @Override
    public MusicDetailRes getMusicDetail(MusicDetailReq req) {
        MusicDetail musicDetail = commonDAO.queryForObject("MusicDetail.getMusicDetail", req, MusicDetail.class);

        MusicDetailRes res = new MusicDetailRes();
        Product product = new Product();

        product.setIdentifier(new Identifier("episode", req.getEpisodeId()));
        Title title = new Title();
        title.setText(musicDetail.getProdNm());
        product.setTitle(title);
        Price price = new Price();
        price.setText(musicDetail.getProdAmt());
        product.setPrice(price);

        // Menu
        List<MenuItem> menuList = commonService.getMenuItemList(req.getEpisodeId(), req.getLangCd());
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

        Accrual accrual = new Accrual();
        accrual.setVoterCount(musicDetail.getPaticpersCnt());
        accrual.setDownloadCount(musicDetail.getDwldCnt());
        accrual.setScore(musicDetail.getAvgEvluScore());
        product.setAccrual(accrual);
        Rights rights = new Rights();
        rights.setGrade(musicDetail.getProdGrd());
        product.setRights(rights);

        // Source List
        Source source = new Source();
        source.setMediaType("image/png");
        source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PMUSIC/201401/02/0002074441/14/0003894669/14/10_0002074441_200_200_1701_200x200_R180x180.PNG");
        product.setSourceList(new ArrayList<Source>(Arrays.asList(source)));

        Music music = new Music();
        music.setIdentifier(new Identifier("downloadId", musicDetail.getOutsdContentsId()));

        // Music Source List
        List<SubContent> contentList = commonDAO.queryForList("MusicDetail.getSubContentList", req.getEpisodeId(), SubContent.class);
        music.setSourceList(new ArrayList<Source>());
        for (SubContent sc : contentList) {
            Source src = new Source();
            src.setSize(sc.getFileSize());
            src.setType(sc.getDpPgQultCd().equals("PD009711") ? "audio/mp3-192" : "audio/mp3-128"); // TODO Util로 만들어야 함.

            music.getSourceList().add(src);
        }

        music.setServiceList(new ArrayList<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service>(
                Arrays.asList(
                        new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service("mp3", "Y"),
                        new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service("bell", musicDetail.isBellSprtYn() ? "Y" : "N"),
                        new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service("ring", musicDetail.isColorringSprtYn() ? "Y" : "N")
                )
        ));

        product.setMusic(music);

        // Contributor
        Contributor contributor = new Contributor();
        contributor.setIdentifier(new Identifier("contributorId", musicDetail.getArtist1Id()));
        contributor.setName(musicDetail.getArtist1Nm());

        contributor.setAlbum(musicDetail.getArtist3Nm());
        contributor.setPublisher(musicDetail.getChnlCompNm());
        contributor.setAgency(musicDetail.getAgencyNm());
        product.setContributor(contributor);

        res.setProduct(product);

        return res;
    }

}
