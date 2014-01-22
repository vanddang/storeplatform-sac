package com.skplanet.storeplatform.sac.display.response;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * Response Info Generate Service
 * 
 * Updated on : 2014. 1. 21. Updated by : 홍길동, SK 플래닛.
 */
/**
 * Calss 설명
 * 
 * Updated on : 2014. 1. 21. Updated by : 홍길동, SK 플래닛.
 */
public interface ResponseInfoGenerateFacadeService {

	/**
	 * <pre>
	 * App Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateAppProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Music Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateMusicProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Movie Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateMovieProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 방송 Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateBroadcastProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Ebook Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateEbookProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 *  Comic Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateComicProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Shopping Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateShoppingProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 App Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateSpecificAppProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 Music Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateSpecificMusicProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 Movie Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateSpecificMovieProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 방송 Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateSpecificBroadcastProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 Ebook Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateSpecificEbookProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 Comic Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateSpecificComicProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 Shopping Product 생성
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Product generateSpecificShoppingProduct(MetaInfo metaInfo);
}
