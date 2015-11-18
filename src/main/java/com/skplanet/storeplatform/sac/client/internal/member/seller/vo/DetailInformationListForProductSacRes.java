/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.seller.vo;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST]상품상세의 판매자 정보 목록 조회.
 * 
 * Updated on : 2015. 11. 17. Updated by : 최진호, 보고지티.
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationListForProductSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 판매자 정보 목록.
	 */
	@NotBlank
	private Map<String, SellerMbrInfoSac> sellerMbrMap;

	/**
	 * @return the sellerMbrMap
	 */
	public Map<String, SellerMbrInfoSac> getSellerMbrMap() {
		return this.sellerMbrMap;
	}

	/**
	 * @param sellerMbrMap
	 *            the sellerMbrMap to set
	 */
	public void setSellerMbrMap(Map<String, SellerMbrInfoSac> sellerMbrMap) {
		this.sellerMbrMap = sellerMbrMap;
	}

	/**
	 * 상품상세의 판매자 정보.
	 */
	@JsonSerialize(include = Inclusion.NON_NULL)
	public static class SellerMbrInfoSac extends CommonInfo {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/** 판매자 ID. */
		private String sellerId;
		/** 판매자 구분 코드 . */
		private String sellerClass;
		/** 내국인 여부 . */
		private String isDomestic;
		/** 판매자 정보 Value List. */
		private List<SellerMbrAppSac> sellerMbrList;
		/** 판매 제공자 여부. */
		private String providerYn;
		/** 제공자 정보 Value List. */
		private List<ProviderMbrAppSac> providerMbrList;

		/**
		 * @return the sellerId
		 */
		public String getSellerId() {
			return this.sellerId;
		}

		/**
		 * @param sellerId
		 *            the sellerId to set
		 */
		public void setSellerId(String sellerId) {
			this.sellerId = sellerId;
		}

		/**
		 * @return the sellerClass
		 */
		public String getSellerClass() {
			return this.sellerClass;
		}

		/**
		 * @param sellerClass
		 *            the sellerClass to set
		 */
		public void setSellerClass(String sellerClass) {
			this.sellerClass = sellerClass;
		}

		/**
		 * @return the isDomestic
		 */
		public String getIsDomestic() {
			return this.isDomestic;
		}

		/**
		 * @param isDomestic
		 *            the isDomestic to set
		 */
		public void setIsDomestic(String isDomestic) {
			this.isDomestic = isDomestic;
		}

		/**
		 * @return the sellerMbrList
		 */
		public List<SellerMbrAppSac> getSellerMbrList() {
			return this.sellerMbrList;
		}

		/**
		 * @param sellerMbrList
		 *            the sellerMbrList to set
		 */
		public void setSellerMbrList(List<SellerMbrAppSac> sellerMbrList) {
			this.sellerMbrList = sellerMbrList;
		}

		/**
		 * @return the providerYn
		 */
		public String getProviderYn() {
			return this.providerYn;
		}

		/**
		 * @param providerYn
		 *            the providerYn to set
		 */
		public void setProviderYn(String providerYn) {
			this.providerYn = providerYn;
		}

		/**
		 * @return the providerMbrList
		 */
		public List<ProviderMbrAppSac> getProviderMbrList() {
			return this.providerMbrList;
		}

		/**
		 * @param providerMbrList
		 *            the providerMbrList to set
		 */
		public void setProviderMbrList(List<ProviderMbrAppSac> providerMbrList) {
			this.providerMbrList = providerMbrList;
		}

		/**
		 * 상하구분에 따른 판매자 정보.
		 */
		@JsonSerialize(include = Inclusion.NON_NULL)
		public static class SellerMbrAppSac extends CommonInfo {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			/** 상하구분 . */
			public String appStat;
			/** 회사명 . */
			public String sellerCompany;
			/** 판매자명 . */
			public String sellerName;
			/** 판매자 Email . */
			public String sellerEmail;
			/** 통신판매업 신고번호 . */
			public String bizRegNumber;
			/** 주소 . */
			public String sellerAddress;
			/** 연락처 . */
			public String sellerPhone;
			/** 사업자 등록번호. */
			public String sellerBizNumber;

			/**
			 * @return the appStat
			 */
			public String getAppStat() {
				return this.appStat;
			}

			/**
			 * @param appStat
			 *            the appStat to set
			 */
			public void setAppStat(String appStat) {
				this.appStat = appStat;
			}

			/**
			 * @return the sellerCompany
			 */
			public String getSellerCompany() {
				return this.sellerCompany;
			}

			/**
			 * @param sellerCompany
			 *            the sellerCompany to set
			 */
			public void setSellerCompany(String sellerCompany) {
				this.sellerCompany = sellerCompany;
			}

			/**
			 * @return the sellerName
			 */
			public String getSellerName() {
				return this.sellerName;
			}

			/**
			 * @param sellerName
			 *            the sellerName to set
			 */
			public void setSellerName(String sellerName) {
				this.sellerName = sellerName;
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
			 * @return the bizRegNumber
			 */
			public String getBizRegNumber() {
				return this.bizRegNumber;
			}

			/**
			 * @param bizRegNumber
			 *            the bizRegNumber to set
			 */
			public void setBizRegNumber(String bizRegNumber) {
				this.bizRegNumber = bizRegNumber;
			}

			/**
			 * @return the sellerAddress
			 */
			public String getSellerAddress() {
				return this.sellerAddress;
			}

			/**
			 * @param sellerAddress
			 *            the sellerAddress to set
			 */
			public void setSellerAddress(String sellerAddress) {
				this.sellerAddress = sellerAddress;
			}

			/**
			 * @return the sellerPhone
			 */
			public String getSellerPhone() {
				return this.sellerPhone;
			}

			/**
			 * @param sellerPhone
			 *            the sellerPhone to set
			 */
			public void setSellerPhone(String sellerPhone) {
				this.sellerPhone = sellerPhone;
			}

			/**
			 * @return the sellerBizNumber
			 */
			public String getSellerBizNumber() {
				return this.sellerBizNumber;
			}

			/**
			 * @param sellerBizNumber
			 *            the sellerBizNumber to set
			 */
			public void setSellerBizNumber(String sellerBizNumber) {
				this.sellerBizNumber = sellerBizNumber;
			}

		}

		/**
		 * 상하구분에 따른 제공자 정보.
		 */
		@JsonSerialize(include = Inclusion.NON_NULL)
		public static class ProviderMbrAppSac extends CommonInfo {
			/** */
			private static final long serialVersionUID = 1L;
			/** 상하구분 . */
			public String appStat;
			/** 회사명 . */
			public String sellerCompany;
			/** 판매자명 . */
			public String sellerName;
			/** 판매자 Email . */
			public String sellerEmail;
			/** 통신판매업 신고번호 . */
			public String bizRegNumber;
			/** 주소 . */
			public String sellerAddress;
			/** 연락처 . */
			public String sellerPhone;
			/** 사업자 등록번호. */
			public String sellerBizNumber;

			/**
			 * @return the appStat
			 */
			public String getAppStat() {
				return this.appStat;
			}

			/**
			 * @param appStat
			 *            the appStat to set
			 */
			public void setAppStat(String appStat) {
				this.appStat = appStat;
			}

			/**
			 * @return the sellerCompany
			 */
			public String getSellerCompany() {
				return this.sellerCompany;
			}

			/**
			 * @param sellerCompany
			 *            the sellerCompany to set
			 */
			public void setSellerCompany(String sellerCompany) {
				this.sellerCompany = sellerCompany;
			}

			/**
			 * @return the sellerName
			 */
			public String getSellerName() {
				return this.sellerName;
			}

			/**
			 * @param sellerName
			 *            the sellerName to set
			 */
			public void setSellerName(String sellerName) {
				this.sellerName = sellerName;
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
			 * @return the bizRegNumber
			 */
			public String getBizRegNumber() {
				return this.bizRegNumber;
			}

			/**
			 * @param bizRegNumber
			 *            the bizRegNumber to set
			 */
			public void setBizRegNumber(String bizRegNumber) {
				this.bizRegNumber = bizRegNumber;
			}

			/**
			 * @return the sellerAddress
			 */
			public String getSellerAddress() {
				return this.sellerAddress;
			}

			/**
			 * @param sellerAddress
			 *            the sellerAddress to set
			 */
			public void setSellerAddress(String sellerAddress) {
				this.sellerAddress = sellerAddress;
			}

			/**
			 * @return the sellerPhone
			 */
			public String getSellerPhone() {
				return this.sellerPhone;
			}

			/**
			 * @param sellerPhone
			 *            the sellerPhone to set
			 */
			public void setSellerPhone(String sellerPhone) {
				this.sellerPhone = sellerPhone;
			}

			/**
			 * @return the sellerBizNumber
			 */
			public String getSellerBizNumber() {
				return this.sellerBizNumber;
			}

			/**
			 * @param sellerBizNumber
			 *            the sellerBizNumber to set
			 */
			public void setSellerBizNumber(String sellerBizNumber) {
				this.sellerBizNumber = sellerBizNumber;
			}

		}

	}
}
