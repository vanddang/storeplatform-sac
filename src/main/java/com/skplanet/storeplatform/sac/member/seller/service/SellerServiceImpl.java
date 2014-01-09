package com.skplanet.storeplatform.sac.member.seller.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CmsReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CmsRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.common.util.HttpUtil;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 판매자 회원의 등록/수정/탈퇴 기능정의
 * 
 * Updated on : 2014. 1. 7. Updated by : 김경복, 부르칸.
 */
@Service
public class SellerServiceImpl implements SellerService {

	private static final Logger logger = LoggerFactory.getLogger(SellerServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Override
	public CreateRes createSeller() {

		CreateSellerResponse createSellerResponse = this.sellerSCI.createSeller(new CreateSellerRequest());
		logger.debug("code : {}" + createSellerResponse.getCommonResponse().getResultCode());

		return null;
	}

	@Override
	public LockAccountRes lockAccount(LockAccountReq req) {

		/** 1. SC회원 Req 생성 및 주입 */
		UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
		/** TODO 2. 임시 공통헤더 생성 주입 */
		updateStatusSellerRequest.setCommonRequest(this.imsiCommonRequest());
		updateStatusSellerRequest.setSellerID(req.getSellerId());
		/*
		 * US010201(정상) US010202(탈퇴) US010203(가가입) US010204(일시정지) US010205(전환)
		 */
		// TODO 임시 코딩
		updateStatusSellerRequest.setSellerMainStatus(MemberConstants.MAIN_STATUS_WATING);
		/*
		 * US010301(정상) US010302(탈퇴신청) US010303(탈퇴완료) US010304(가입승인만료) US010305(가입승인 대기) US010306(이메일변경승인대기)
		 * US010307(로그인 제한) US010308(직권중지) US010309(7일이용정지) US010310(30일이용정지) US010311(영구이용정지) US010312(전환신청)
		 * US010313(전환재신청) US010314(전환거절)
		 */
		// TODO 임시코딩
		updateStatusSellerRequest.setSellerSubStatus(MemberConstants.SUB_STATUS_LOGIN_PAUSE);

		/** 3. SC회원 Call */
		UpdateStatusSellerResponse updateStatusSellerResponse = this.sellerSCI
				.updateStatusSeller(updateStatusSellerRequest);

		// Response Debug
		logger.info("UpdateStatusSellerResponse Code : {}", updateStatusSellerResponse.getCommonResponse()
				.getResultCode());
		logger.info("UpdateStatusSellerResponse Messge : {}", updateStatusSellerResponse.getCommonResponse()
				.getResultMessage());

		/** 4. Tenant Response 생성 및 주입 */
		LockAccountRes response = new LockAccountRes(updateStatusSellerRequest.getSellerID());
		return response;
	}

	/**
	 * <pre>
	 * TODO 임시 SC회원 전달용 공통헤더
	 * </pre>
	 * 
	 * @return
	 */
	private CommonRequest imsiCommonRequest() {
		/** TODO 임시 공통헤더 생성 주입 */
		CommonRequest commonRequest = new CommonRequest();
		// S001(ShopClient), S002(WEB), S003(OpenAPI)
		commonRequest.setSystemID("S001");
		// TODO SC회원 문의?? - Reamine ID생성 규칙과 다름
		// T01(T-Store), T02(A-Store), T03(B-Store)
		commonRequest.setTenantID("S01");

		return commonRequest;
	}

	/**
	 * <pre>
	 * Object To XML
	 * </pre>
	 * 
	 * @param type
	 * @param obj
	 * @return
	 */
	public static String makeData(String type, Object obj) {
		String resultXml = "";
		try {
			XStream xs = new XStream();
			xs.alias(type, obj.getClass());

			resultXml = xs.toXML(obj);
			resultXml = resultXml.replaceAll("__", "_");

			logger.info("resultXml : " + resultXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultXml;
	}

	/**
	 * <pre>
	 * 통합 CMS 결과 맵핑
	 * XML TO Object
	 * </pre>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static CmsRes resultFromXml(String data) throws Exception {

		CmsRes sr = new CmsRes();
		XStream xs = new XStream(new DomDriver());

		xs.alias("RESULT", CmsRes.class);

		xs.fromXML(data, sr);

		return sr;
	}

	/**
	 * <pre>
	 * TODO 임시 통합 CMS 테스트 데이터
	 * </pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		StringBuffer sb = new StringBuffer();

		String headerXml = "";
		String memberXml = "";
		String memberHpXml = "";

		CmsReq header = new CmsReq();
		header.setWK_CD("UPDATE");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = sf.format(date);
		header.setWK_DTTM(now);
		header.setHP_CNT("2");
		headerXml = SellerServiceImpl.makeData("HEADER", header);

		CmsReq memberReq = new CmsReq();
		memberXml = SellerServiceImpl.makeData("MEMBER", memberReq);

		CmsReq memberHp = new CmsReq();
		memberXml = SellerServiceImpl.makeData("MEMBER_HP", memberHp);

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
		sb.append("<CONTENT>\n");
		sb.append(headerXml + "\n");
		sb.append("<BODY>\n");
		sb.append(memberXml + "\n");
		sb.append(memberHpXml + "\n");
		sb.append("</BODY>\n");
		sb.append("</CONTENT>\n");

		System.out.println(sb.toString());

		// Test CMS
		String xmlReq = "";

		// Header 생성
		Map<String, String> headerMap = new HashedMap();
		headerMap.put("Content-Type", "text/xml; charset=utf-8");
		headerMap.put("Host", "");
		headerMap.put("Content-Length", xmlReq.getBytes().length + "");
		headerMap.put("Connection", "close");

		HttpUtil httpUtil = new HttpUtil();

		// HttpClient Exceute
		String resStr = httpUtil.httpExecutePost("http://220.103.237.139/icms-admin/intf/partner/receiveContent",
				xmlReq, headerMap, "text/xml", "UTF-8");

		// StringBuffer resStr = new StringBuffer();
		// resStr.append("<?xml version='1.0' encoding='euc-kr' ?>");
		// resStr.append("<RESULT>");
		// resStr.append("<RES_DTTM >00</RES_DTTM>");
		// resStr.append("<RES_CD>00</RES_CD>");
		// resStr.append("<RES_MSG>정상</RES_MSG>");
		// resStr.append("</RESULT>");

		try {
			// Response XML To Object
			CmsRes res = SellerServiceImpl.resultFromXml(resStr);
			// Debug
			System.out.println(res.getRES_CD());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
