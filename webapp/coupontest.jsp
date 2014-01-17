<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.skplanet.storeplatform.sac.api.util.DateUtil"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
</head>
<body >
	<form name='af' action='http://localhost:8210/inbound/shopping/api/coupon' method=get>
		TX TYPE : <select name='txType' >
					<option value="bd">브랜드상품(bd)</option>
					<option value="ct">카탈로그상품(ct)</option>
					<option value="cp">상품추가/수정(cp)</option>
					<option value="st">상품 상태 변경(st)</option>
					<option value="ls">특가 상품 목록 조회(ls)</option>
					<option value="dt">특가 상품 상세 조회(dt)</option>
				</select>
		CUD TYPE : <input type="text" name='cudType'  size=20 value ="C"><br>
		TX_ID <input type="text" name='txId'  size=22 value ="<%= DateUtil.getToday("yyyyMMddHHmmss") %>00000000"><br>

		<table border=0>
			<tr>
				<td>BrandCode <input type="text" name='brandCode'  size=20 value ="BR1111"></td>
				<td>BrandName <input type="text" name='brandName'  size=20 value ="조은상품"></td>
			</tr>
			<tr>
				<td>brandCategory  <input type="text" name='brandCategory'  size=20 value ="DP28005"></td>
				<td>brandImage <input type="text" name='brandImage'  size=20 value ="http://tstore.co.kr/SMILE_DATA5/COUPON/201307/17/CL00000397/1372868171_2748_5723_180x180.jpg"></td>
			</tr>
		</table>
		<table border=0>
			<tr>
				<td>CatalogId <input type="text" name='catalogCode'  size=20 value ="CT1111"></td>
				<td>CatalogNm  <input type="text" name='catalogName'  size=20 value ="키탈로그상품"></td>
			</tr>
			<tr>
				<td>CatalogCategory  <input type="text" name='catalogCategory'  size=20 value ="DP28012"></td>
				<td>Catalog한줄소개 <input type="text" name='intro_text'  size=50 value ="카탈로그한줄소개"></td>
				<td>CatalogDesc <input type="text" name='catalogDescription'  size=50 value ="카탈로그 상세 설명 블라블라"></td>
			</tr>
			<tr>
				<td>catalogImage1 <input type="text" name='catalogImage1'  size=20 value ="http://tstore.co.kr/SMILE_DATA7/COUPON/201310/31/CL00006231/1383127338_0997_8015_120x120.jpg"></td>
				<td>catalogImage2 <input type="text" name='catalogImage2'  size=20 value ="http://tstore.co.kr/SMILE_DATA5/COUPON/201307/17/CL00000392/1372868178_7171_1446_120x120.jpg"></td>
				<td>태그 <input type="text" name='tag'  size=20 value ="jade_태그1,jade_태그2,jade_태그3"></td>
				변경종류 상태 : <select name='upType' >
					<option value="0">상품상태변경</option>
					<option value="1">단품상태변경</option>
					<option value="2">상품+단품상태 모두</option>
				</select>

			</tr>
            <tr>
                <td>couponCode <input type="text" name='couponCode'  size=20 value ="103826"></td>
                <td>itemCode <input type="text" name='itemCode'  size=20 value ="103827"></td>
                <td>
                    상품 상태 : <select name='coupnStatus' >
                    <option value="3">판매중</option>
                    <option value="4">판매중지</option>
                    <option value="5">판매금지</option>
                </select>                
                </td>
                <td></td>

            </tr>			
		</table>

		<textarea name='rData' cols=130 rows=30>
<rData>
	<couponCode><%= DateUtil.getToday("HHmmss") %></couponCode>
	<couponName><%= DateUtil.getToday("yyyyMMddHH") %>샘플쿠폰</couponName>
	<issueSDate>20130305000000</issueSDate>
	<issueEDate>20140325235959</issueEDate>
	<validSDate>20130326000000</validSDate>
	<validEDate>20140326235959</validEDate>
	<validUntil>10</validUntil>
	<Tag>샘플태그1,샘플태그2</Tag>
	<description><![CDATA[배송상품용 설명, 상품권은 빈값으로 옴]]></description >
	<direction><![CDATA[사용장소에 대한 설명...  이렇게 저렇게 해서 찾아가면 된다...  등등등...  ...  끝]]></direction>
	<useCondition><![CDATA[1인당 1매만 가능  기타 할인, 이벤트 제외]]></useCondition>
	<addtionalInfo><![CDATA[예약 필수]]></addtionalInfo>
	<refundCondition><![CDATA[구매후 1주일 이내 취소 가능]]></refundCondition>
	<storeSaleType>1</storeSaleType>
	<storeb2bFlag>N</storeb2bFlag>
	<storeCatalogCode>CT1111</storeCatalogCode>
	<accountingRate>95</accountingRate>
	<taxType>01</taxType>
	<bpId>testbp2</bpId>
	<coupnStatus>3</coupnStatus>
	<items>
		<item>
			<itemCode><%= DateUtil.getToday("HHmmss") %>1</itemCode>
			<storeLicenseCode>5555</storeLicenseCode>
			<itemName><%= DateUtil.getToday("yyyyMMddHH") %>단품1</itemName>
			<orgPrice>1000</orgPrice>
			<salePrice>900</salePrice>
			<itemPrice>900</itemPrice>
			<dcRate>10</dcRate>
			<maxCount>10000</maxCount>
			<maxCountMonthly>1000</maxCountMonthly>
			<maxCountDaily>100</maxCountDaily>
			<maxCountMonthlyUser>0</maxCountMonthlyUser>
			<maxCountDailyUser>0</maxCountDailyUser>
			<buyMaxLimit>0</buyMaxLimit>
			<itemValue1>단품속성1</itemValue1>
			<itemValue2>단품속성11</itemValue2>
			<bpManageId>4444</bpManageId>
			<ShippingUrl>http://www.shopingcoupon.co.kr</ShippingUrl>
			<itemStatus>3</itemStatus>
			<cudType>C</cudType>
		</item>
		<item>
			<itemCode><%= DateUtil.getToday("HHmmss") %>2</itemCode>
			<storeLicenseCode>5556</storeLicenseCode>
			<itemName><%= DateUtil.getToday("yyyyMMddHH") %>단품2</itemName>
			<orgPrice>2000</orgPrice>
			<salePrice>1000</salePrice>
			<itemPrice>1000</itemPrice>
			<dcRate>50</dcRate>
			<maxCount>10005</maxCount>
			<maxCountMonthly>1005</maxCountMonthly>
			<maxCountDaily>105</maxCountDaily>
			<maxCountMonthlyUser>5</maxCountMonthlyUser>
			<maxCountDailyUser>5</maxCountDailyUser>
			<buyMaxLimit>1</buyMaxLimit>
			<itemValue1>단품속성2</itemValue1>
			<itemValue2>단품속성22</itemValue2>
			<bpManageId>4445</bpManageId>
			<ShippingUrl>http://www.shopingcoupon.co.kr</ShippingUrl>
			<itemStatus>3</itemStatus>
			<cudType>C</cudType>
		</item>
		<item>
			<itemCode><%= DateUtil.getToday("HHmmss") %>3</itemCode>
			<storeLicenseCode>5557</storeLicenseCode>
			<itemName><%= DateUtil.getToday("yyyyMMddHH") %>단품3</itemName>
			<orgPrice>1600</orgPrice>
			<salePrice>1280</salePrice>
			<itemPrice>1280</itemPrice>
			<dcRate>20</dcRate>
			<maxCount>10006</maxCount>
			<maxCountMonthly>1006</maxCountMonthly>
			<maxCountDaily>106</maxCountDaily>
			<maxCountMonthlyUser>6</maxCountMonthlyUser>
			<maxCountDailyUser>5</maxCountDailyUser>
			<buyMaxLimit>2</buyMaxLimit>
			<itemValue1>단품속성3</itemValue1>
			<itemValue2>단품속성32</itemValue2>
			<bpManageId>4446</bpManageId>
			<ShippingUrl>http://www.shopingcoupon.co.kr</ShippingUrl>
			<itemStatus>3</itemStatus>
			<cudType>C</cudType>
		</item>
	</items>
</rData>
		</textarea>
		<input type=submit>
	</form>
</body>
</html>


