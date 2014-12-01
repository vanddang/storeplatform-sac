/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preference;

/**
 * <p>
 * Card
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Card extends CommonInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 카드 ID
     */
    private String id;

    /**
     * 카드 타입 (일반리스트, 랭킹리스트, 배너, 스페셜 ...)
     */
    private String typeCd;

    /**
     * 카드 제목
     */
    private String title;

    /**
     * FC1,FC2, FC3 title param
     */
    private Map<String, String> titleParam;

    /**
     * 카드 설명
     */
    private String desc;

    /**
     * Item 전시 레이아웃
     */
    private String layout;

    /**
     * Card 이미지 타입
     */
    private String cardImgType;

	/**
	 * resource 정보.
	 */
	private List<Source> sourceList;

    /**
     * 랜딩 타이틀
     */
    private String lndTitle;

    /**
     * 랜딩 설명
     */
    private String lndDesc;

    /**
     * 랜딩화면 Item 전시 레이아웃
     */
    private String lndLayout;

    /**
     * 메뉴 카테고리
     */
    private String menuId;

    /**
     * 패널에 노출 여부
     */
    private String expoYnInPanel;

    /**
     * 좋아요 여부
     */
    private String likeYn;

    /**
     * 공유 URL
     */
    private String shareUrl;

    /**
     * 기타 속성
     */
    private EtcProp etcProp;

    /**
     * 데이터셋 속성
     */
    private DatasetProp datasetProp;

    /**
     * 사용자 선호도 정보
     */
    @Deprecated
    private Preference preference;

    /**
     * 카드 통계
     */
    private Stats stats;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getTitleParam() {
		return titleParam;
	}

	public void setTitleParam(Map<String, String> titleParam) {
		this.titleParam = titleParam;
	}

	public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

	public String getCardImgType() {
		return cardImgType;
	}

	public void setCardImgType(String cardImgType) {
		this.cardImgType = cardImgType;
	}

	public List<Source> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	public String getLndTitle() {
        return lndTitle;
    }

    public void setLndTitle(String lndTitle) {
        this.lndTitle = lndTitle;
    }

    public String getLndDesc() {
        return lndDesc;
    }

    public void setLndDesc(String lndDesc) {
        this.lndDesc = lndDesc;
    }

    public String getLndLayout() {
        return lndLayout;
    }

    public void setLndLayout(String lndLayout) {
        this.lndLayout = lndLayout;
    }

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getExpoYnInPanel() {
		return expoYnInPanel;
	}

	public void setExpoYnInPanel(String expoYnInPanel) {
		this.expoYnInPanel = expoYnInPanel;
	}

	public String getLikeYn() {
		return likeYn;
	}

	public void setLikeYn(String likeYn) {
		this.likeYn = likeYn;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public EtcProp getEtcProp() {
        return etcProp;
    }

    public void setEtcProp(EtcProp etcProp) {
        this.etcProp = etcProp;
    }

    public DatasetProp getDatasetProp() {
        return datasetProp;
    }

    public void setDatasetProp(DatasetProp datasetProp) {
        this.datasetProp = datasetProp;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }


}
