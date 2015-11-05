package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.util.List;
import java.util.Map;

/**
 * PaymentInfo Value Object
 * 
 * 결제 시 필요한 상품 메타 정보 조회 VO
 * 
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 */
public class PaymentInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	// ////////////////////////// 상품 군 조회 변수 ////////////////////////////
	private String topMenuId;
	private String menuId;
	private String svcGrpCd;
	private String inAppYn;
    private Integer ageAllowedFrom;

	// ////////////////////////// APP,멀티미디어 상품 변수 ////////////////////////////
	private String prodId;
	private String prodNm;
	private Double prodAmt;
	private String prodStatusCd;
	private String prodGrdCd;
	private String prodSprtYn;
	private String drmYn;
	private String usePeriodUnitCd;
	private String usePeriod;
	private String aid;
	private String tenantProdGrpCd;
	private String mallCd;
	private String outsdContentsId;
	private String sellerMbrNo;
	private String sellerNm;
	private String sellerEmail;
	private String sellerTelno;
	private String possLendClsfCd;
	private String chapter;
	private String bookClsfCd;
	private String chapterText;
	private String chapterUnit;
	private String chnlProdNm;
    /**
     * 인앱 상품의 경우 부모 상품ID
     */
    private String parentProdId;
    private String parentProdNm;
    private String seriesYn;
    private String metaClsfCd;
    /**
     * S2S 가격정보 조회 URL
     */
    private String searchPriceUrl;

	// ////////////////////////// 쇼핑 상품 변수 ////////////////////////////
	private String couponCode;
	private String itemCode;
	private String prodCaseCd;
	private Double specialSaleAmt;
	private String specialSaleStartDt;
	private String specialSaleEndDt;
	private String specialSaleCouponId;
	private Integer specialSaleMonthLimit;
	private Integer specialSaleDayLimit;
	private Integer specialSaleMonthLimitPerson;
	private Integer specialSaleDayLimitPerson;
	private Integer specialSaleOncePrchsLimit;
	private String specialTypeCd;
	private String orgBrandId;
	private String brandId;
	private String brandNm;
	

	// ////////////////////////// 정액제 상품 변수 ////////////////////////////
	private List<String> availableFixrateProdIdList;
	private List<FreePass> availableFixrateInfoList;

	private String autoPrchsYN;
	private String autoPrchsPeriodUnitCd;
	private Integer autoPrchsPeriodValue;
	private String autoPrchsLastDt;
	private String exclusiveFixrateProdExistYn;
	private String cmpxProdClsfCd;
	private String cmpxProdBookClsfCd;
	private String bnsCashAmt;
	private String packagePrchsYn;
	private String bnsUsePeriodUnitCd;
	private Integer bnsUsePeriod;
	private List<String> exclusiveFixrateProdIdList;

    // ////////////////////////// 프로모션 이벤트 정보 ////////////////////////////
    private Map<String, Integer> mileageRateMap;
    private Integer promId;
    private String acmlMethodCd;
    private String acmlDt;
    private Integer privateAcmlLimit;
    private String promForceCloseCd;
    private String promEndDt;
    private Integer payMethodVdtyDt;

    // ////////////////////////// 이용 기간 설정 구분 ////////////////////////////
    private String usePeriodSetCd;

	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
	 * @return the svcGrpCd
	 */
	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	/**
	 * @param svcGrpCd
	 *            the svcGrpCd to set
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	/**
	 * @return the inAppYn
	 */
	public String getInAppYn() {
		return this.inAppYn;
	}

	/**
	 * @param inAppYn
	 *            the inAppYn to set
	 */
	public void setInAppYn(String inAppYn) {
		this.inAppYn = inAppYn;
	}

    public Integer getAgeAllowedFrom() {
        return ageAllowedFrom;
    }

    public void setAgeAllowedFrom(Integer ageAllowedFrom) {
        this.ageAllowedFrom = ageAllowedFrom;
    }

    /**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the prodNm
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * @param prodNm
	 *            the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * @return the prodAmt
	 */
	public Double getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodStatusCd
	 */
	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	/**
	 * @param prodStatusCd
	 *            the prodStatusCd to set
	 */
	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	/**
	 * @return the prodGrdCd
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * @param prodGrdCd
	 *            the prodGrdCd to set
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * @return the prodSprtYn
	 */
	public String getProdSprtYn() {
		return this.prodSprtYn;
	}

	/**
	 * @param prodSprtYn
	 *            the prodSprtYn to set
	 */
	public void setProdSprtYn(String prodSprtYn) {
		this.prodSprtYn = prodSprtYn;
	}

	/**
	 * @return the drmYn
	 */
	public String getDrmYn() {
		return this.drmYn;
	}

	/**
	 * @param drmYn
	 *            the drmYn to set
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * @param usePeriodUnitCd
	 *            the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * @return the usePeriod
	 */
	public String getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * @return the aid
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

	/**
	 * @return the mallCd
	 */
	public String getMallCd() {
		return this.mallCd;
	}

	/**
	 * @param mallCd
	 *            the mallCd to set
	 */
	public void setMallCd(String mallCd) {
		this.mallCd = mallCd;
	}

	/**
	 * @return the outsdContentsId
	 */
	public String getOutsdContentsId() {
		return this.outsdContentsId;
	}

	/**
	 * @param outsdContentsId
	 *            the outsdContentsId to set
	 */
	public void setOutsdContentsId(String outsdContentsId) {
		this.outsdContentsId = outsdContentsId;
	}

	/**
	 * @return the sellerMbrNo
	 */
	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	/**
	 * @param sellerMbrNo
	 *            the sellerMbrNo to set
	 */
	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	/**
	 * @return the sellerNm
	 */
	public String getSellerNm() {
		return this.sellerNm;
	}

	/**
	 * @param sellerNm
	 *            the sellerNm to set
	 */
	public void setSellerNm(String sellerNm) {
		this.sellerNm = sellerNm;
	}

	/**
	 * @return the sellerEmail
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * @param sellerEmail
	 *            the sellerEmail to set
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	/**
	 * @return the sellerTelno
	 */
	public String getSellerTelno() {
		return this.sellerTelno;
	}

	/**
	 * @param sellerTelno
	 *            the sellerTelno to set
	 */
	public void setSellerTelno(String sellerTelno) {
		this.sellerTelno = sellerTelno;
	}

	/**
	 * @return the possLendClsfCd
	 */
	public String getPossLendClsfCd() {
		return this.possLendClsfCd;
	}

	/**
	 * @param possLendClsfCd
	 *            the possLendClsfCd to set
	 */
	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}

	/**
	 * @return the chapter
	 */
	public String getChapter() {
		return this.chapter;
	}

	/**
	 * @param chapter
	 *            the chapter to set
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	/**
	 * @return the bookClsfCd
	 */
	public String getBookClsfCd() {
		return this.bookClsfCd;
	}

	/**
	 * @param bookClsfCd
	 *            the bookClsfCd to set
	 */
	public void setBookClsfCd(String bookClsfCd) {
		this.bookClsfCd = bookClsfCd;
	}

	/**
	 * @return the chapterText
	 */
	public String getChapterText() {
		return this.chapterText;
	}

	/**
	 * @param chapterText
	 *            the chapterText to set
	 */
	public void setChapterText(String chapterText) {
		this.chapterText = chapterText;
	}

	/**
	 * @return the chapterUnit
	 */
	public String getChapterUnit() {
		return this.chapterUnit;
	}

	/**
	 * @param chapterUnit
	 *            the chapterUnit to set
	 */
	public void setChapterUnit(String chapterUnit) {
		this.chapterUnit = chapterUnit;
	}

	/**
	 * @return the chnlProdNm
	 */
	public String getChnlProdNm() {
		return this.chnlProdNm;
	}

	/**
	 * @param chnlProdNm
	 *            the chnlProdNm to set
	 */
	public void setChnlProdNm(String chnlProdNm) {
		this.chnlProdNm = chnlProdNm;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return this.couponCode;
	}

	/**
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the prodCaseCd
	 */
	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	/**
	 * @param prodCaseCd
	 *            the prodCaseCd to set
	 */
	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	/**
	 * @return the specialSaleAmt
	 */
	public Double getSpecialSaleAmt() {
		return this.specialSaleAmt;
	}

	/**
	 * @param specialSaleAmt
	 *            the specialSaleAmt to set
	 */
	public void setSpecialSaleAmt(Double specialSaleAmt) {
		this.specialSaleAmt = specialSaleAmt;
	}

	/**
	 * @return the specialSaleStartDt
	 */
	public String getSpecialSaleStartDt() {
		return this.specialSaleStartDt;
	}

	/**
	 * @param specialSaleStartDt
	 *            the specialSaleStartDt to set
	 */
	public void setSpecialSaleStartDt(String specialSaleStartDt) {
		this.specialSaleStartDt = specialSaleStartDt;
	}

	/**
	 * @return the specialSaleEndDt
	 */
	public String getSpecialSaleEndDt() {
		return this.specialSaleEndDt;
	}

	/**
	 * @param specialSaleEndDt
	 *            the specialSaleEndDt to set
	 */
	public void setSpecialSaleEndDt(String specialSaleEndDt) {
		this.specialSaleEndDt = specialSaleEndDt;
	}

	/**
	 * @return the specialSaleCouponId
	 */
	public String getSpecialSaleCouponId() {
		return this.specialSaleCouponId;
	}

	/**
	 * @param specialSaleCouponId
	 *            the specialSaleCouponId to set
	 */
	public void setSpecialSaleCouponId(String specialSaleCouponId) {
		this.specialSaleCouponId = specialSaleCouponId;
	}

	/**
	 * @return the specialSaleMonthLimit
	 */
	public Integer getSpecialSaleMonthLimit() {
		return this.specialSaleMonthLimit;
	}

	/**
	 * @param specialSaleMonthLimit
	 *            the specialSaleMonthLimit to set
	 */
	public void setSpecialSaleMonthLimit(Integer specialSaleMonthLimit) {
		this.specialSaleMonthLimit = specialSaleMonthLimit;
	}

	/**
	 * @return the specialSaleDayLimit
	 */
	public Integer getSpecialSaleDayLimit() {
		return this.specialSaleDayLimit;
	}

	/**
	 * @param specialSaleDayLimit
	 *            the specialSaleDayLimit to set
	 */
	public void setSpecialSaleDayLimit(Integer specialSaleDayLimit) {
		this.specialSaleDayLimit = specialSaleDayLimit;
	}

	/**
	 * @return the specialSaleMonthLimitPerson
	 */
	public Integer getSpecialSaleMonthLimitPerson() {
		return this.specialSaleMonthLimitPerson;
	}

	/**
	 * @param specialSaleMonthLimitPerson
	 *            the specialSaleMonthLimitPerson to set
	 */
	public void setSpecialSaleMonthLimitPerson(Integer specialSaleMonthLimitPerson) {
		this.specialSaleMonthLimitPerson = specialSaleMonthLimitPerson;
	}

	/**
	 * @return the specialSaleDayLimitPerson
	 */
	public Integer getSpecialSaleDayLimitPerson() {
		return this.specialSaleDayLimitPerson;
	}

	/**
	 * @param specialSaleDayLimitPerson
	 *            the specialSaleDayLimitPerson to set
	 */
	public void setSpecialSaleDayLimitPerson(Integer specialSaleDayLimitPerson) {
		this.specialSaleDayLimitPerson = specialSaleDayLimitPerson;
	}

	/**
	 * @return the specialSaleOncePrchsLimit
	 */
	public Integer getSpecialSaleOncePrchsLimit() {
		return specialSaleOncePrchsLimit;
	}

	/**
	 * @param specialSaleOncePrchsLimit the specialSaleOncePrchsLimit to set
	 */
	public void setSpecialSaleOncePrchsLimit(Integer specialSaleOncePrchsLimit) {
		this.specialSaleOncePrchsLimit = specialSaleOncePrchsLimit;
	}

	/**
	 * @return the specialTypeCd
	 */
	public String getSpecialTypeCd() {
		return specialTypeCd;
	}

	/**
	 * @param specialTypeCd the specialTypeCd to set
	 */
	public void setSpecialTypeCd(String specialTypeCd) {
		this.specialTypeCd = specialTypeCd;
	}

	/**
	 * @return the orgBrandId
	 */
	public String getOrgBrandId() {
		return orgBrandId;
	}

	/**
	 * @param orgBrandId the orgBrandId to set
	 */
	public void setOrgBrandId(String orgBrandId) {
		this.orgBrandId = orgBrandId;
	}

	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}


	public String getBrandNm() {
		return brandNm;
	}

	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}


	/**
	 * @return the availableFixrateProdIdList
	 */
	public List<String> getAvailableFixrateProdIdList() {
		return this.availableFixrateProdIdList;
	}

	/**
	 * @param availableFixrateProdIdList
	 *            the availableFixrateProdIdList to set
	 */
	public void setAvailableFixrateProdIdList(List<String> availableFixrateProdIdList) {
		this.availableFixrateProdIdList = availableFixrateProdIdList;
	}

	/**
	 * @return the availableFixrateInfoList
	 */
	public List<FreePass> getAvailableFixrateInfoList() {
		return availableFixrateInfoList;
	}

	/**
	 * @param availableFixrateInfoList the availableFixrateInfoList to set
	 */
	public void setAvailableFixrateInfoList(List<FreePass> availableFixrateInfoList) {
		this.availableFixrateInfoList = availableFixrateInfoList;
	}

	/**
	 * @return the autoPrchsYN
	 */
	public String getAutoPrchsYN() {
		return this.autoPrchsYN;
	}

	/**
	 * @param autoPrchsYN
	 *            the autoPrchsYN to set
	 */
	public void setAutoPrchsYN(String autoPrchsYN) {
		this.autoPrchsYN = autoPrchsYN;
	}

	/**
	 * @return the autoPrchsPeriodUnitCd
	 */
	public String getAutoPrchsPeriodUnitCd() {
		return this.autoPrchsPeriodUnitCd;
	}

	/**
	 * @param autoPrchsPeriodUnitCd
	 *            the autoPrchsPeriodUnitCd to set
	 */
	public void setAutoPrchsPeriodUnitCd(String autoPrchsPeriodUnitCd) {
		this.autoPrchsPeriodUnitCd = autoPrchsPeriodUnitCd;
	}

	/**
	 * @return the autoPrchsPeriodValue
	 */
	public Integer getAutoPrchsPeriodValue() {
		return this.autoPrchsPeriodValue;
	}

	/**
	 * @param autoPrchsPeriodValue
	 *            the autoPrchsPeriodValue to set
	 */
	public void setAutoPrchsPeriodValue(Integer autoPrchsPeriodValue) {
		this.autoPrchsPeriodValue = autoPrchsPeriodValue;
	}

	/**
	 * @return the autoPrchsLastDt
	 */
	public String getAutoPrchsLastDt() {
		return this.autoPrchsLastDt;
	}

	/**
	 * @param autoPrchsLastDt
	 *            the autoPrchsLastDt to set
	 */
	public void setAutoPrchsLastDt(String autoPrchsLastDt) {
		this.autoPrchsLastDt = autoPrchsLastDt;
	}

	/**
	 * @return the exclusiveFixrateProdExistYn
	 */
	public String getExclusiveFixrateProdExistYn() {
		return this.exclusiveFixrateProdExistYn;
	}

	/**
	 * @param exclusiveFixrateProdExistYn
	 *            the exclusiveFixrateProdExistYn to set
	 */
	public void setExclusiveFixrateProdExistYn(String exclusiveFixrateProdExistYn) {
		this.exclusiveFixrateProdExistYn = exclusiveFixrateProdExistYn;
	}

	/**
	 * @return the cmpxProdClsfCd
	 */
	public String getCmpxProdClsfCd() {
		return this.cmpxProdClsfCd;
	}

	/**
	 * @param cmpxProdClsfCd
	 *            the cmpxProdClsfCd to set
	 */
	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}

	/**
	 * @return the cmpxProdBookClsfCd
	 */
	public String getCmpxProdBookClsfCd() {
		return cmpxProdBookClsfCd;
	}

	/**
	 * @param cmpxProdBookClsfCd the cmpxProdBookClsfCd to set
	 */
	public void setCmpxProdBookClsfCd(String cmpxProdBookClsfCd) {
		this.cmpxProdBookClsfCd = cmpxProdBookClsfCd;
	}

	/**
	 * @return the bnsCashAmt
	 */
	public String getBnsCashAmt() {
		return this.bnsCashAmt;
	}

	/**
	 * @param bnsCashAmt
	 *            the bnsCashAmt to set
	 */
	public void setBnsCashAmt(String bnsCashAmt) {
		this.bnsCashAmt = bnsCashAmt;
	}

	/**
	 * @return the packagePrchsYn
	 */
	public String getPackagePrchsYn() {
		return packagePrchsYn;
	}

	/**
	 * @param packagePrchsYn the packagePrchsYn to set
	 */
	public void setPackagePrchsYn(String packagePrchsYn) {
		this.packagePrchsYn = packagePrchsYn;
	}

	/**
	 * @return the bnsUsePeriodUnitCd
	 */
	public String getBnsUsePeriodUnitCd() {
		return this.bnsUsePeriodUnitCd;
	}

	/**
	 * @param bnsUsePeriodUnitCd
	 *            the bnsUsePeriodUnitCd to set
	 */
	public void setBnsUsePeriodUnitCd(String bnsUsePeriodUnitCd) {
		this.bnsUsePeriodUnitCd = bnsUsePeriodUnitCd;
	}

	/**
	 * @return the bnsUsePeriod
	 */
	public Integer getBnsUsePeriod() {
		return this.bnsUsePeriod;
	}

	/**
	 * @param bnsUsePeriod
	 *            the bnsUsePeriod to set
	 */
	public void setBnsUsePeriod(Integer bnsUsePeriod) {
		this.bnsUsePeriod = bnsUsePeriod;
	}

	/**
	 * @return the exclusiveFixrateProdIdList
	 */
	public List<String> getExclusiveFixrateProdIdList() {
		return this.exclusiveFixrateProdIdList;
	}

	/**
	 * @param exclusiveFixrateProdIdList
	 *            the exclusiveFixrateProdIdList to set
	 */
	public void setExclusiveFixrateProdIdList(List<String> exclusiveFixrateProdIdList) {
		this.exclusiveFixrateProdIdList = exclusiveFixrateProdIdList;
	}

    public String getParentProdId() {
        return parentProdId;
    }

    public void setParentProdId(String parentProdId) {
        this.parentProdId = parentProdId;
    }

    public String getSeriesYn() {
        return seriesYn;
    }

    public void setSeriesYn(String seriesYn) {
        this.seriesYn = seriesYn;
    }

    public Map<String, Integer> getMileageRateMap() {
        return mileageRateMap;
    }

    public void setMileageRateMap(Map<String, Integer> mileageRateMap) {
        this.mileageRateMap = mileageRateMap;
    }

    public String getMetaClsfCd() {
        return metaClsfCd;
    }

    public void setMetaClsfCd(String metaClsfCd) {
        this.metaClsfCd = metaClsfCd;
    }

    public String getSearchPriceUrl() {
        return searchPriceUrl;
    }

    public void setSearchPriceUrl(String searchPriceUrl) {
        this.searchPriceUrl = searchPriceUrl;
    }

	/**
	 * @return the usePeriodSetCd
	 */
	public String getUsePeriodSetCd() {
		return usePeriodSetCd;
	}

	/**
	 * @param usePeriodSetCd the usePeriodSetCd to set
	 */
	public void setUsePeriodSetCd(String usePeriodSetCd) {
		this.usePeriodSetCd = usePeriodSetCd;
	}

    public String getAcmlMethodCd() {
        return acmlMethodCd;
    }

    public void setAcmlMethodCd(String acmlMethodCd) {
        this.acmlMethodCd = acmlMethodCd;
    }

	public Integer getPromId() {
		return promId;
	}

	public void setPromId(Integer promId) {
		this.promId = promId;
	}

	public String getAcmlDt() {
        return acmlDt;
    }

    public void setAcmlDt(String acmlDt) {
        this.acmlDt = acmlDt;
    }

    public Integer getPrivateAcmlLimit() {
        return privateAcmlLimit;
    }

    public void setPrivateAcmlLimit(Integer privateAcmlLimit) {
        this.privateAcmlLimit = privateAcmlLimit;
    }

    public String getPromForceCloseCd() {
        return promForceCloseCd;
    }

    public void setPromForceCloseCd(String promForceCloseCd) {
        this.promForceCloseCd = promForceCloseCd;
    }

    public String getPromEndDt() {
        return promEndDt;
    }

    public void setPromEndDt(String promEndDt) {
        this.promEndDt = promEndDt;
    }

    public String getParentProdNm() {
        return parentProdNm;
    }

    public void setParentProdNm(String parentProdNm) {
        this.parentProdNm = parentProdNm;
    }

    public Integer getPayMethodVdtyDt() {
        return payMethodVdtyDt;
    }

    public void setPayMethodVdtyDt(Integer payMethodVdtyDt) {
        this.payMethodVdtyDt = payMethodVdtyDt;
    }
}
