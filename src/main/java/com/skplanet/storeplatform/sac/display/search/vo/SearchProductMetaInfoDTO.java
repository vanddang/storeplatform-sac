package com.skplanet.storeplatform.sac.display.search.vo;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;

public class SearchProductMetaInfoDTO {
	private Identifier identifier;
	private Title title;
	private Price price;
	private List<Menu> menuList;
	private List<Source> sourceList;
	private List<Support> supportList;

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public List<Menu> getMenuList() {
		return this.menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public List<Source> getSourceList() {
		return this.sourceList;
	}

	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	public List<Support> getSupportList() {
		return this.supportList;
	}

	public void setSupportList(List<Support> supportList) {
		this.supportList = supportList;
	}

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	private class Identifier {
		private String type;
		private String text; // 상품ID

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getText() {
			return this.text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	private class Menu {
		private String id; // 전시 메뉴ID type에 따른 ID
		private String name; // 전시 메뉴ID에 따른 이름
		private String type; // 전시 메뉴 타입
		private String count; // 전시 메뉴 상품수
		private Source source; // 전시 메뉴의 graphic resource가 있을 경우 정의

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getCount() {
			return this.count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public Source getSource() {
			return this.source;
		}

		public void setSource(Source source) {
			this.source = source;
		}
	}

	private class Source {
		private String mediaType;
		private String size; // 파일 사이즈
		private String type;
		private String url; // Resource URL

		public String getMediaType() {
			return this.mediaType;
		}

		public void setMediaType(String mediaType) {
			this.mediaType = mediaType;
		}

		public String getSize() {
			return this.size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUrl() {
			return this.url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	private class Support {
		private String type;
		private String text;

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getText() {
			return this.text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	private class Title {
		private String text; // 제목

		public String getText() {
			return this.text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	private class Accrual {
		private String voterCount;
		private String downloadCount;
		private String score;

		public String getVoterCount() {
			return this.voterCount;
		}

		public void setVoterCount(String voterCount) {
			this.voterCount = voterCount;
		}

		public String getDownloadCount() {
			return this.downloadCount;
		}

		public void setDownloadCount(String downloadCount) {
			this.downloadCount = downloadCount;
		}

		public String getScore() {
			return this.score;
		}

		public void setScore(String score) {
			this.score = score;
		}
	}

	private class Rights {
		private String grade;

		public String getGrade() {
			return this.grade;
		}

		public void setGrade(String grade) {
			this.grade = grade;
		}
	}

	private class Vod {
		private VideoInfo videoInfo;

		public VideoInfo getVideoInfo() {
			return this.videoInfo;
		}

		public void setVideoInfo(VideoInfo videoInfo) {
			this.videoInfo = videoInfo;
		}

		private class VideoInfo {
			private String type;

			public String getType() {
				return this.type;
			}

			public void setType(String type) {
				this.type = type;
			}
		}
	}

	private class Date {
		private String type;
		private String text;

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getText() {
			return this.text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

}
