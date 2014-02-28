/**
 * 
 */
package com.skplanet.storeplatform.sac.display.banner.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 27. Updated by : 이태희, SK 플래닛.
 */
public class test {

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param args
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		int passCnt = 0;
		int aCnt = 3; // a는 2개 담기고
		int bCnt = 2; // b는 3개 담기면된다.

		HashMap map = new HashMap();
		ArrayList list = new ArrayList();
		ArrayList resultList = new ArrayList();

		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X1");
		list.add(map);
		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X1");
		list.add(map);
		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "AAAA");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "BBBB");
		map.put("type", "X2");
		list.add(map);
		map = new HashMap();
		map.put("key", "BBBB");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "BBBB");
		map.put("type", "X2");
		list.add(map);
		map = new HashMap();
		map.put("key", "BBBB");
		map.put("type", "X2");
		list.add(map);
		map = new HashMap();
		map.put("key", "BBBB");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "BBBB");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "BBBB");
		map.put("type", "X");
		list.add(map);
		map = new HashMap();
		map.put("key", "BBBB");
		map.put("type", "X");
		list.add(map);

		boolean aFlag = false;
		boolean bFlag = false;

		for (int i = 0; i < list.size(); i++) {
			HashMap p = (HashMap) list.get(i);
			String key = (String) p.get("key");
			System.out.println(passCnt);
			if ("AAAA".equals(key)) {
				if (aFlag) {
					continue;
				}
				if (passCnt == aCnt) {
					passCnt = 0;
					aFlag = true;
					continue;
				}
			} else if ("BBBB".equals(key)) {
				if (bFlag) {
					continue;
				}
				if (passCnt == bCnt) {
					passCnt = 0;
					bFlag = true;
					continue;
				}
			}

			if ("X".equals(p.get("type"))) {
				++passCnt;
				resultList.add(p);
			}

		}
		System.out.println(resultList.toString());
	}
}
