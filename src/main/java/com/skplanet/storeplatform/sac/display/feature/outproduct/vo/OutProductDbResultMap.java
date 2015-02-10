package com.skplanet.storeplatform.sac.display.feature.outproduct.vo;

public class OutProductDbResultMap {
	private String tenantId;		// TENANT_ID	VARCHAR2(3 BYTE)
	private String listId;			// LIST_ID	VARCHAR2(20 BYTE)
	private String stdDt;			// STD_DT	DATE
	private String detailUrl;		// DETAIL_URL	VARCHAR2(300 BYTE)
	private Integer rank;			// RANK	NUMBER
	private String title;			// TITLE	VARCHAR2(1000 BYTE)
	private String thumbnailUrl;	// THUMBNAIL_URL	VARCHAR2(300 BYTE)
	private String thumbnailPath;	// THUMBNAIL_PATH	VARCHAR2(300 BYTE)
	private Double score;			// SCORE	NUMBER
	private Integer voterCount;		// VOTER_COUNT	NUMBER
	private Integer accrualCount;	// ACCRUAL_COUNT	NUMBER
	private Integer rankChange;		// RANK_CHANGE	NUMBER
	private String category;		// CATEGORY	VARCHAR2(600 BYTE)
	private String categorySub;		// CATEGORY_SUB	VARCHAR2(600 BYTE)
	private String rightGrade;		// RIGHT_GRADE	VARCHAR2(60 BYTE)
	private Integer price;			// PRICE	NUMBER
	private Integer priceRent;		// PRICE_RENT	NUMBER
	private Integer priceFixed;		// PRICE_FIXED	NUMBER
	private Double discountRate;	// DISCOUNT_RATE	NUMBER
	private String support;			// SUPPORT	VARCHAR2(600 BYTE)
	private Integer chapter;			// CHAPTER	NUMBER
	private Integer subCount;		// SUB_COUNT	NUMBER
	private String description;		// DESCRIPTION	VARCHAR2(4000 BYTE)
	private String runningTime;		// RUNNING_TIME	DATE
	private String contributorName;			// CONTRIBUTOR_NAME	VARCHAR2(600 BYTE)
	private String contributorDirector;		// CONTRIBUTOR_DIRECTOR	VARCHAR2(600 BYTE)
	private String contributorArtist;		// CONTRIBUTOR_ARTIST	VARCHAR2(600 BYTE)
	private String contributorAuthor;		// CONTRIBUTOR_AUTHOR	VARCHAR2(600 BYTE)
	private String contributorPainter;		// CONTRIBUTOR_PAINTER	VARCHAR2(600 BYTE)
	private String contributorChannel;		// CONTRIBUTOR_CHANNEL	VARCHAR2(600 BYTE)
	private String contributorParter;		// CONTRIBUTOR_PARTER	VARCHAR2(600 BYTE)
	private String albumName;			// ALBUM_NAME	VARCHAR2(1000 BYTE)
	private String saleDateInfo;		// SALE_DATE_INFO	DATE
	private String dateRelease;			// DATE_RELEASE	DATE
	private String bookType;			// BOOK_TYPE	CHAR(1 BYTE)
	private String serialDate;			// SERIAL_DATE	DATE
	private String serialWeek;			// SERIAL_WEEK	VARCHAR2(60 BYTE)
	private String status;				// STATUS	CHAR(1 BYTE)
	private String delivery;			// DELIVERY	CHAR(1 BYTE)
	private String origin;				// ORIGIN	VARCHAR2(600 BYTE)
	private String previewUrl;			// PREVIEW_URL	VARCHAR2(300 BYTE)
	private String recommendReason;		// RECOMMEND_REASON	VARCHAR2(4000 BYTE)
	private String freeDefined01;		// FREE_DEFINED_01	VARCHAR2(600 BYTE)
	private String freeDefined02;		// FREE_DEFINED_02	VARCHAR2(600 BYTE)
	private String freeDefined03;		// FREE_DEFINED_03	VARCHAR2(600 BYTE)
	private String freeDefined04;		// FREE_DEFINED_04	VARCHAR2(600 BYTE)
	private String freeDefined05;		// FREE_DEFINED_05	VARCHAR2(600 BYTE)
	private String freeDefined06;		// FREE_DEFINED_06	VARCHAR2(600 BYTE)
	private String freeDefined07;		// FREE_DEFINED_07	VARCHAR2(600 BYTE)
	private String freeDefined08;		// FREE_DEFINED_08	VARCHAR2(600 BYTE)
	private String freeDefined09;		// FREE_DEFINED_09	VARCHAR2(600 BYTE)
	private String freeDefined10;		// FREE_DEFINED_10	VARCHAR2(600 BYTE)
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getStdDt() {
		return stdDt;
	}
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Integer getVoterCount() {
		return voterCount;
	}
	public void setVoterCount(Integer voterCount) {
		this.voterCount = voterCount;
	}
	public Integer getAccrualCount() {
		return accrualCount;
	}
	public void setAccrualCount(Integer accrualCount) {
		this.accrualCount = accrualCount;
	}
	public Integer getRankChange() {
		return rankChange;
	}
	public void setRankChange(Integer rankChange) {
		this.rankChange = rankChange;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategorySub() {
		return categorySub;
	}
	public void setCategorySub(String categorySub) {
		this.categorySub = categorySub;
	}
	public String getRightGrade() {
		return rightGrade;
	}
	public void setRightGrade(String rightGrade) {
		this.rightGrade = rightGrade;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getPriceRent() {
		return priceRent;
	}
	public void setPriceRent(Integer priceRent) {
		this.priceRent = priceRent;
	}
	public Integer getPriceFixed() {
		return priceFixed;
	}
	public void setPriceFixed(Integer priceFixed) {
		this.priceFixed = priceFixed;
	}
	public Double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
	public Integer getChapter() {
		return chapter;
	}
	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}
	public Integer getSubCount() {
		return subCount;
	}
	public void setSubCount(Integer subCount) {
		this.subCount = subCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}
	public String getContributorName() {
		return contributorName;
	}
	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}
	public String getContributorDirector() {
		return contributorDirector;
	}
	public void setContributorDirector(String contributorDirector) {
		this.contributorDirector = contributorDirector;
	}
	public String getContributorArtist() {
		return contributorArtist;
	}
	public void setContributorArtist(String contributorArtist) {
		this.contributorArtist = contributorArtist;
	}
	public String getContributorAuthor() {
		return contributorAuthor;
	}
	public void setContributorAuthor(String contributorAuthor) {
		this.contributorAuthor = contributorAuthor;
	}
	public String getContributorPainter() {
		return contributorPainter;
	}
	public void setContributorPainter(String contributorPainter) {
		this.contributorPainter = contributorPainter;
	}
	public String getContributorChannel() {
		return contributorChannel;
	}
	public void setContributorChannel(String contributorChannel) {
		this.contributorChannel = contributorChannel;
	}
	public String getContributorParter() {
		return contributorParter;
	}
	public void setContributorParter(String contributorParter) {
		this.contributorParter = contributorParter;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getSaleDateInfo() {
		return saleDateInfo;
	}
	public void setSaleDateInfo(String saleDateInfo) {
		this.saleDateInfo = saleDateInfo;
	}
	public String getDateRelease() {
		return dateRelease;
	}
	public void setDateRelease(String dateRelease) {
		this.dateRelease = dateRelease;
	}
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	public String getSerialDate() {
		return serialDate;
	}
	public void setSerialDate(String serialDate) {
		this.serialDate = serialDate;
	}
	public String getSerialWeek() {
		return serialWeek;
	}
	public void setSerialWeek(String serialWeek) {
		this.serialWeek = serialWeek;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getPreviewUrl() {
		return previewUrl;
	}
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	public String getRecommendReason() {
		return recommendReason;
	}
	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}
	public String getFreeDefined01() {
		return freeDefined01;
	}
	public void setFreeDefined01(String freeDefined01) {
		this.freeDefined01 = freeDefined01;
	}
	public String getFreeDefined02() {
		return freeDefined02;
	}
	public void setFreeDefined02(String freeDefined02) {
		this.freeDefined02 = freeDefined02;
	}
	public String getFreeDefined03() {
		return freeDefined03;
	}
	public void setFreeDefined03(String freeDefined03) {
		this.freeDefined03 = freeDefined03;
	}
	public String getFreeDefined04() {
		return freeDefined04;
	}
	public void setFreeDefined04(String freeDefined04) {
		this.freeDefined04 = freeDefined04;
	}
	public String getFreeDefined05() {
		return freeDefined05;
	}
	public void setFreeDefined05(String freeDefined05) {
		this.freeDefined05 = freeDefined05;
	}
	public String getFreeDefined06() {
		return freeDefined06;
	}
	public void setFreeDefined06(String freeDefined06) {
		this.freeDefined06 = freeDefined06;
	}
	public String getFreeDefined07() {
		return freeDefined07;
	}
	public void setFreeDefined07(String freeDefined07) {
		this.freeDefined07 = freeDefined07;
	}
	public String getFreeDefined08() {
		return freeDefined08;
	}
	public void setFreeDefined08(String freeDefined08) {
		this.freeDefined08 = freeDefined08;
	}
	public String getFreeDefined09() {
		return freeDefined09;
	}
	public void setFreeDefined09(String freeDefined09) {
		this.freeDefined09 = freeDefined09;
	}
	public String getFreeDefined10() {
		return freeDefined10;
	}
	public void setFreeDefined10(String freeDefined10) {
		this.freeDefined10 = freeDefined10;
	}
}
