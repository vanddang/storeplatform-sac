/**
 * 
 */
package com.skplanet.storeplatform.sac.display.feature.isf.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 3. 14. Updated by : 윤주영, SK 플래닛.
 */
public class IsfConstants {

	public static final Map<String, String> mapReasonCode = new HashMap<String, String>();

	public static final String recommandListId = "ADM000000012";

	static {
		mapReasonCode.put("01", "$1 구매");
		mapReasonCode.put("02", "시리즈 연속성 추천");
		mapReasonCode.put("03", "Beginner’s Best 추천");
		mapReasonCode.put("04", "Power User’s Best 추천");

		// $1 : 연관상품 상품명
		// $2 : 연관상품 대분류 카테고리명
		// $3 : 연관상품 소분류 카테고리명
		// $4 : 대상상품 대분류 카테고리명 -> 추천 코드 1191 / 9299 에 해당됨
		mapReasonCode.put("1091", "$1 구매자의 인기 앱");
		mapReasonCode.put("1191", "취향이 비슷한 구매자의 인기 앱");
		mapReasonCode.put("1291", "$1 구매자의 인기 앱");
		mapReasonCode.put("1391", "취향이 비슷한 구매자의 인기 앱");

		mapReasonCode.put("2091", "$1 구매자의 인기 앱");
		mapReasonCode.put("2013", "$1 구매자의 인기 이북");
		mapReasonCode.put("2014", "$1 구매자의 인기 만화");
		mapReasonCode.put("2016", "$1 구매자의 인기 음악");
		mapReasonCode.put("2017", "$1 구매자의 인기 영화");
		mapReasonCode.put("2018", "$1 구매자의 인기 방송");
		mapReasonCode.put("2191", "취향이 비슷한 구매자의 인기 앱");

		mapReasonCode.put("9001", "요즘 가장 인기 있는 게임 앱");
		mapReasonCode.put("9003", "요즘 가장 인기 있는 FUN 앱");
		mapReasonCode.put("9004", "요즘 가장 인기 있는 생활·위치 앱");
		mapReasonCode.put("9008", "요즘 가장 인기 있는 어학·교육 앱");

		mapReasonCode.put("3014", "따끈따끈한 $1의 신간");
		mapReasonCode.put("3018", "따끈따끈한 $1의 신작");
		mapReasonCode.put("4013", "$1 작가의 또 다른 이북");
		mapReasonCode.put("4014", "$1 작가의 또 다른 만화");

		mapReasonCode.put("8016", "당신의 성향에 맞는 영화");

		mapReasonCode.put("9414", "요즘 가장 인기 있는 $3 만화");
		mapReasonCode.put("9113", "요즘 가장 인기 있는 이북");
		mapReasonCode.put("9114", "요즘 가장 인기 있는 만화");
		mapReasonCode.put("9116", "요즘 가장 인기 있는 음악");
		mapReasonCode.put("9117", "요즘 가장 인기 있는 영화");
		mapReasonCode.put("9118", "요즘 가장 인기 있는 방송");
		mapReasonCode.put("9199", "요즘 가장 인기 있는 컨텐츠");

		mapReasonCode.put("9201", "우리 또래의 인기 게임 앱");
		mapReasonCode.put("9203", "우리 또래의 인기 FUN 앱");
		mapReasonCode.put("9204", "우리 또래의 인기 생활·위치 앱");
		mapReasonCode.put("9208", "우리 또래의 인기 어학·교육 앱");

		mapReasonCode.put("9213", "우리 또래의 인기 이북");
		mapReasonCode.put("9214", "우리 또래의 인기 만화");
		mapReasonCode.put("9216", "우리 또래의 인기 음악");
		mapReasonCode.put("9217", "우리 또래의 인기 영화");
		mapReasonCode.put("9218", "우리 또래의 인기 방송");
		mapReasonCode.put("9299", "우리 또래의 인기 $4");
		mapReasonCode.put("9399", "신규 사용자의 인기 컨텐츠");
	}
}
