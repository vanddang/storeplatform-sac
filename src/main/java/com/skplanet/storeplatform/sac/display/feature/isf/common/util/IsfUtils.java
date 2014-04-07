/**
 * 
 */
package com.skplanet.storeplatform.sac.display.feature.isf.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 3. 14. Updated by : 윤주영, SK 플래닛.
 */
public class IsfUtils {

	public static void mapPrint(Map<String, Object> mapReq, Logger log) {
		// Get Map in Set interface to get key and value
		Set<Entry<String, Object>> s = mapReq.entrySet();
		// Move next key and value of Map by iterator
		Iterator<Entry<String, Object>> it = s.iterator();
		while (it.hasNext()) {
			// key=value separator this by Map.Entry to get key and value
			Entry<String, Object> m = it.next();

			// getKey is used to get key of Map
			String key = m.getKey();

			// getValue is used to get value of key in Map
			Object value = m.getValue();

			log.debug(key + ":[" + value.toString() + "]");
		}
	}

	public static String cutStringLimit(String str, int limit) {
		if (str == null || str.getBytes().length <= limit)
			return str;

		int len = str.length();
		int cnt = 0, index = 0;

		while (index < len && cnt < limit) {
			if (str.charAt(index++) < 256)
				// 1바이트 문자라면...
				cnt++; // 길이 1 증가
			else {
				cnt += 2; // 길이 2 증가
			}
		}

		if (index < len && limit >= cnt)
			str = str.substring(0, index);
		else if (index < len && limit < cnt)
			str = str.substring(0, index - 1);

		if (len > index) {
			return str + "...";
		} else {
			return str;
		}
	}

	public static List<Identifier> generateIdentifierList(Map<String, String> param) {
		Identifier identifier = null;
		List<Identifier> identifierList = new ArrayList<Identifier>();

		String contentsTypeCd = param.get("contentsTypeCd");
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Episode ID 기준검색일 경우 (PD002502)
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);

			if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(param.get("topMenuId"))) {
				if (param.get("catalogId") != null) {
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
					identifier.setText(param.get("catalogId"));
					identifierList.add(identifier);
				} else {
					if (param.get("prodId") != null) {
						identifier = new Identifier();
						identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
						identifier.setText(param.get("prodId"));
						identifierList.add(identifier);
					}
				}
			} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(param.get("topMenuId"))) {
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_SONG_IDENTIFIER_CD);
				identifier.setText(param.get("outsdContentsId"));
				identifierList.add(identifier);
			}
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd) // Catalog ID 기준 검색일 경우
				&& DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(param.get("topMenuId"))) {
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(param.get("catalogId"));
			identifierList.add(identifier);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Channel ID 기준 검색일 경우
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			identifier.setText(param.get("prodId"));
			identifierList.add(identifier);
		}
		return identifierList;
	}
}
