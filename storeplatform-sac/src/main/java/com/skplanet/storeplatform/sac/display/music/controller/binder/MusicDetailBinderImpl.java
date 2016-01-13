package com.skplanet.storeplatform.sac.display.music.controller.binder;

import com.google.common.collect.Lists;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.MusicQualityCode;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.music.vo.RelatedProduct;
import com.skplanet.storeplatform.sac.display.music.vo.SubContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 음악 상세정보 VO매퍼
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@org.springframework.stereotype.Component
public class MusicDetailBinderImpl implements MusicDetailBinder {
    protected Logger logger = LoggerFactory.getLogger(MusicDetailBinderImpl.class);

    private static Map<String, String> META_CLS_TO_PROD_TYPE;
    static {
        META_CLS_TO_PROD_TYPE = new HashMap<String, String>();
        META_CLS_TO_PROD_TYPE.put("CT30", "colorRing/normal");
        META_CLS_TO_PROD_TYPE.put("CT31", "colorRing/long");
        META_CLS_TO_PROD_TYPE.put("CT32", "liveBell/sq");
        META_CLS_TO_PROD_TYPE.put("CT33", "liveBell/hq");
    }

    private String getMusicQualityType(String dpPgQultCd) {
        String ret = null;
        try {
            ret = MusicQualityCode.valueOf(dpPgQultCd).getType();
        } catch (Exception e) {
            logger.warn("Invalid Quality code.");
            // 실패하는 경우 그냥 무시하고 null이 리턴 되도록 한다.
        }
        return ret;
    }

    @Override
    public void mapMusic(Product product, MusicDetail musicDetail, List<SubContent> contentList, List<RelatedProduct> relatedProductList) {
        Music music = new Music();

        // Music Source List
        music.setSourceList(new ArrayList<Source>());
        for (SubContent sc : contentList) {
            Source src = new Source();
            src.setSize(sc.getFileSize());
            src.setType(getMusicQualityType(sc.getDpPgQultCd()));
            music.getSourceList().add(src);
        }

        music.setServiceList(new ArrayList<Service>(
                Arrays.asList(
                        new Service("mp3", "Y"),
                        new Service("bell", musicDetail.isBellSprtYn() ? "Y" : "N"),
                        new Service("ring", musicDetail.isColorringSprtYn() ? "Y" : "N")
                )
        ));

        music.setRelatedProductList(new ArrayList<Identifier>());
        if(relatedProductList != null && relatedProductList.size() > 0) {
            for (RelatedProduct relatedProduct : relatedProductList) {
                Identifier identifier = new Identifier();
                identifier.setType(META_CLS_TO_PROD_TYPE.get(relatedProduct.getMetaClsfCd()));
                identifier.setText(relatedProduct.getProdId());
                identifier.setPrice(relatedProduct.getProdAmt());   // TODO check
                music.getRelatedProductList().add(identifier);
            }
        }

        music.setAlbumType(musicDetail.getAlbumTypeNm());   // 앨범 유형
        music.setCid(musicDetail.getCid());

        product.setMusic(music);
    }

    @Override
    public void mapThumbnail(Product product, MusicDetail musicDetail) {
        product.setSourceList(new ArrayList<Source>());
        if(!StringUtils.isEmpty(musicDetail.getThmPath())) {
            Source source = new Source();
            source.setUrl(musicDetail.getThmPath());
            source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
            source.setMediaType(DisplayCommonUtil.getMimeType(musicDetail.getThmPath()));
            product.getSourceList().add(source);
        }
    }

    @Override
    public void mapMenu(Product product, MusicDetail musicDetail) {
        product.setMenuList(Lists.newArrayList(
                new Menu(musicDetail.getTopMenuId(), musicDetail.getTopMenuNm(), "topClass"),
                new Menu(musicDetail.getMenuId(), musicDetail.getMenuNm(), null),
                new Menu("CT25", "mp3", "metaClass")
                ));
    }

    @Override
    public void mapBasicInfo(Product product, MusicDetail musicDetail, List<Point> pointList) {

        product.setIdentifierList(newIdentifierList(musicDetail));
        product.setProductExplain(musicDetail.getProdBaseDesc());
        product.setProductDetailExplain(musicDetail.getProdDtlDesc());
        product.setTitle(newTitle(musicDetail));
        product.setPrice(newPrice(musicDetail));
        product.setAccrual(newAccrual(musicDetail));
        product.setRights(newRights(musicDetail));
        product.setSvcGrpCd(musicDetail.getSvcGrpCd());
        product.setSalesStatus(musicDetail.getProdStatusCd());
        product.setContributor(newContributor(musicDetail));
        product.setDateList(newDateList(musicDetail));
        product.setLikeYn(musicDetail.getLikeYn());
        product.setDistributor(newDistributor(musicDetail));
        if(musicDetail.getDeviceModelCd() == null || "N".equals(musicDetail.getMusicSprtYn())) {
            product.setIsDeviceSupported("N");
        }
        else {
            product.setIsDeviceSupported("Y");
        }

        // tmembership 할인율
        if(pointList != null) {
        	product.setPointList(pointList);
        }
    }
    
    private Contributor newContributor(MusicDetail musicDetail) {
    	Contributor contributor = new Contributor();
        contributor.setIdentifierList(new ArrayList<Identifier>());
        contributor.getIdentifierList().add(new Identifier("artist", musicDetail.getArtist1Id()));
        contributor.setName(musicDetail.getArtist1Nm());

        contributor.setAlbum(musicDetail.getArtist3Nm());
        contributor.setPublisher(musicDetail.getChnlCompNm());
        contributor.setAgency(musicDetail.getAgencyNm());
        
        contributor.setSourceList(new ArrayList<Source>());
        if(!StringUtils.isEmpty(musicDetail.getArtistThmPath())) {
            Source source = new Source();
            source.setUrl(musicDetail.getArtistThmPath());
            source.setType(DisplayConstants.DP_SOURCE_TYPE_ARTIST_THUMBNAIL);
            source.setMediaType(DisplayCommonUtil.getMimeType(musicDetail.getArtistThmPath()));
            contributor.getSourceList().add(source);
        }
        if(!StringUtils.isEmpty(musicDetail.getAlbumThmPath())) {
            Source source = new Source();
            source.setUrl(musicDetail.getAlbumThmPath());
            source.setType(DisplayConstants.DP_SOURCE_TYPE_ALBUM_THUMBNAIL);
            source.setMediaType(DisplayCommonUtil.getMimeType(musicDetail.getAlbumThmPath()));
            contributor.getSourceList().add(source);
        }
        return contributor;
    }

    private Distributor newDistributor(MusicDetail musicDetail) {
        Distributor distributor = new Distributor();
        distributor.setSellerKey(musicDetail.getSellerMbrNo());
        return distributor;
    }

    private List<Date> newDateList(MusicDetail musicDetail) {
        List<Date> dateList = new ArrayList<Date>();
        if(StringUtils.isNotEmpty(musicDetail.getIssueDay())) {
            dateList.add(new Date(DisplayConstants.DP_DATE_ISSUE,
                    DateUtils.parseDate(StringUtils.rightPad(musicDetail.getIssueDay(), 14, "0"))));
        }
        return dateList;
    }

    private Rights newRights(MusicDetail musicDetail) {
        Rights rights = new Rights();
        rights.setGrade(musicDetail.getProdGrdCd());
        return rights;
    }

    private Accrual newAccrual(MusicDetail musicDetail) {
        Accrual accrual = new Accrual();
        accrual.setVoterCount(musicDetail.getPaticpersCnt());
        accrual.setDownloadCount(musicDetail.getDwldCnt());
        accrual.setScore(musicDetail.getAvgEvluScore());
        return accrual;
    }

    private Price newPrice(MusicDetail musicDetail) {
        Price price = new Price();
        price.setText(musicDetail.getProdAmt());
        return price;
    }

    private Title newTitle(MusicDetail musicDetail) {
        Title title = new Title();
        title.setText(musicDetail.getProdNm());
        return title;
    }

    private List<Identifier> newIdentifierList(MusicDetail musicDetail) {
        List<Identifier> identifiers = new ArrayList<Identifier>();
        identifiers.add(new Identifier("channel", musicDetail.getChnlId()));
        identifiers.add(new Identifier("episode", musicDetail.getEpsdId()));
        identifiers.add(new Identifier("song", musicDetail.getOutsdContentsId()));
        identifiers.add(new Identifier("album", musicDetail.getAlbumId()));
        return identifiers;
    }
}
