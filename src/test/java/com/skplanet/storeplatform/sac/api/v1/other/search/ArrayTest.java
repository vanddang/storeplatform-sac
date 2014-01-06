/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.other.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ArrayTest {
	public static void main(String[] args) {

		List<String> aaa = new ArrayList<String>();
		aaa.add("1");

		aaa.get(0);
		aaa.add(0, "100");
		System.out.println(Arrays.toString(aaa.toArray()));

		List<String> pathSegments = aaa;

		if (!pathSegments.isEmpty()) {
			if (StringUtils.equals(pathSegments.get(0), "ㅇㅇ")) {
				pathSegments.add(1, "ㅠㅠ");
			} else {
				pathSegments.add(0, "ㅛㅛ");
			}
		}
		System.out.println(Arrays.toString(pathSegments.toArray()));

		String http = "http://localhost:8010/internal/other/feedback/list/v1";

	}
}
