package com.skplanet.storeplatform.sac.member.seller.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.member.vo.seller.CmsReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CmsRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * IMSI
 * 
 * Updated on : 2014. 1. 6. Updated by : 김경복, 부르칸.
 */
@Service
public class SellerJoinServiceImpl implements SellerJoinService {

	private static final Logger logger = LoggerFactory.getLogger(SellerJoinServiceImpl.class);

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

	public static CmsRes resultFromXml(String data) throws Exception {

		CmsRes sr = new CmsRes();
		XStream xs = new XStream(new DomDriver());

		xs.alias("RESULT", CmsRes.class);

		xs.fromXML(data, sr);

		return sr;
	}

	/**
	 * <pre>
	 * 통합 CMS 테스트 데이터
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
		headerXml = SellerJoinServiceImpl.makeData("HEADER", header);

		CmsReq memberReq = new CmsReq();
		memberXml = SellerJoinServiceImpl.makeData("MEMBER", memberReq);

		CmsReq memberHp = new CmsReq();
		memberXml = SellerJoinServiceImpl.makeData("MEMBER_HP", memberHp);

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
		sb.append("<CONTENT>\n");
		sb.append(headerXml + "\n");
		sb.append("<BODY>\n");
		sb.append(memberXml + "\n");
		sb.append(memberHpXml + "\n");
		sb.append("</BODY>\n");
		sb.append("</CONTENT>\n");

		System.out.println(sb.toString());

		StringBuffer resStr = new StringBuffer();
		resStr.append("<?xml version='1.0' encoding='euc-kr' ?>");
		resStr.append("<RESULT>");
		resStr.append("<RES_DTTM >00</RES_DTTM>");
		resStr.append("<RES_CD>00</RES_CD>");
		resStr.append("<RES_MSG>정상</RES_MSG>");
		resStr.append("</RESULT>");
		try {
			CmsRes res = SellerJoinServiceImpl.resultFromXml(resStr.toString());
			System.out.println(res.getRES_CD());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
