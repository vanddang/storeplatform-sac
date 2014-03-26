package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * App 상세 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationForProductRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 구분 코드 . */
	private String sellerClass;
	/** 내국인 여부 . */
	private String isDomestic;
	/** 판매자 정보 Value List. */
	private List<SellerMbrAppSac> sellerMbrList;

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

	@JsonSerialize(include = Inclusion.NON_NULL)
	public static class SellerMbrAppSac {
		/** 상하구분 . */
		public String appStat;
		/** 회사명 . */
		public String sellerCompany;
		/** 판매자명 . */
		public String sellerName;
		/** 판매자 Email . */
		public String sellerEmail;
		/** 통신판매업 신고번호 . */
		public String bizRegNumbe;
		/** 주소 . */
		public String sellerAddress;
		/** 연락처 . */
		public String sellerPhone;

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
		 * @return the bizRegNumbe
		 */
		public String getBizRegNumbe() {
			return this.bizRegNumbe;
		}

		/**
		 * @param bizRegNumbe
		 *            the bizRegNumbe to set
		 */
		public void setBizRegNumbe(String bizRegNumbe) {
			this.bizRegNumbe = bizRegNumbe;
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

	}
}
