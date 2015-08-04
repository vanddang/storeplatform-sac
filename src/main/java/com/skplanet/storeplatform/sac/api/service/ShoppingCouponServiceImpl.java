package com.skplanet.storeplatform.sac.api.service;

import static com.skplanet.storeplatform.sac.display.common.ProductType.Shopping;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.ImageUtil;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;
import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictHelperComponent;
import com.skplanet.storeplatform.sac.mq.client.search.constant.SearchConstant;
import com.skplanet.storeplatform.sac.mq.client.search.util.SearchQueueUtils;
import com.skplanet.storeplatform.sac.mq.client.search.vo.SearchInterfaceQueue;

/**
 * <pre>
 * 쿠폰아이템 서비스 인터페이스 imple.
 * </pre>
 * 
 * Created on : 2014-01-03 Created by : 김형식, SK 플래닛. Last Updated on : 2014-01-03 Last Updated by : 김형식, SK 플래닛
 */
@Service
public class ShoppingCouponServiceImpl implements ShoppingCouponService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BrandCatalogService brandCatalogService;

	@Autowired
	private CacheEvictHelperComponent cacheEvictHelperComponent;

	public final BrandCatalogProdImgInfo brandCatalogProdImgInfo;

	@Value("#{propertiesForSac['shopping.repositoryPath']}")
	private String repositoryPath;

	@Autowired
	@Resource(name = "sacSearchAmqpTemplate")
	private AmqpTemplate sacSearchAmqpTemplate; // 검색 서버 MQ 연동.

	/**
	 * <pre>
	 * 브랜드 카탈로그 이미지 정보.
	 * </pre>
	 * 
	 */
	public ShoppingCouponServiceImpl() {
		this.brandCatalogProdImgInfo = new BrandCatalogProdImgInfo();
	}

	@Override
	public boolean insertBrandInfo(DpBrandInfo dpBrandInfo) {
		String brandImgPath = null;
		String decodeBrandImgPath = null;
		String message = "";
		try {
			brandImgPath = dpBrandInfo.getBrandImgPath();
			decodeBrandImgPath = URLDecoder.decode(brandImgPath, "utf-8");
			dpBrandInfo.setBrandImgPath(decodeBrandImgPath);

			// 2014.04.02 이현남 매니저 요청 사항 C -> U로 수정 해달라고 요청옴
			this.brandCatalogchangeCud(dpBrandInfo, null);

			// CUD_TYPE에 따라 INSERT/UPDATE
			if ("C".equalsIgnoreCase(dpBrandInfo.getCudType())) {
				// 브랜드ID 생성
				String brandId = this.brandCatalogService.searchCreateBrandId();

				if (StringUtils.isBlank(brandId)) {
					message = "[COUPON_CONTENT_ID]를 생성하지 못했습니다.";
					return false;
				}
				dpBrandInfo.setCreateBrandId(brandId);
				
				this.brandCatalogService.insertBrandInfo(dpBrandInfo);
			} else if ("U".equalsIgnoreCase(dpBrandInfo.getCudType())) {

				String brandID = this.getCreateBrandId(dpBrandInfo.getBrandId());
				dpBrandInfo.setCreateBrandId(brandID);

				this.brandCatalogService.updateBrandInfo(dpBrandInfo);
				//
				// // 수정일시 TB_DP_PROD_IMG 테이블 삭제
				this.brandCatalogService.deleteDpProdImg(brandID);
			}

			// 이미지 가져옴
			this.inputBrandFile(dpBrandInfo, null);

			// 이미지 리사이즈 처리
			this.brandImgResize(dpBrandInfo);

			// cash flush
			this.cacheEvictShoppingMeta(dpBrandInfo, null);

		} catch (CouponException e) {
			throw new CouponException(e.getErrCode(), message, null);
		} catch (UnsupportedEncodingException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

		return true;
	}

	/**
	 * 브랜드,카탈로그 파일 이미지 처리.
	 * 
	 * @param dpBrandInfo
	 *            dpBrandInfo
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return boolean
	 */
	public boolean inputBrandFile(DpBrandInfo dpBrandInfo, DpCatalogInfo dpCatalogInfo) {

		// log.info("<inputBrandFile> inputBrandFile...");

		byte[] bytes = null;
		String uploadPath = null;
		ArrayList<byte[]> isList = null;
		ArrayList<String> fileNameList = null;
		String downloadUrl = "";
		String orgDownloadFileName = "";
		String message = "";
		try {

			// 브랜드 이미지 처리
			if (dpBrandInfo != null) {

				this.log.info("filepath : " + dpBrandInfo.getBrandImgPath());

				// 파일저장 경로
				uploadPath = // "/data7/COUPON"
				this.repositoryPath + File.separator + DateUtil.getToday("yyyyMM") + File.separator
						+ DateUtil.getToday("dd") + File.separator + DateUtil.getTime() + File.separator
						+ dpBrandInfo.getCreateBrandId();

				this.log.info("uploadPath : " + uploadPath);
				fileNameList = (ArrayList<String>) dpBrandInfo.getFileNameList();
				isList = (ArrayList<byte[]>) dpBrandInfo.getIsList();
				dpBrandInfo.setBrandImgPath(uploadPath);
			}

			// 카탈로그 이미지 처리
			if (dpCatalogInfo != null) {
				this.log.info("dpCatalogInfo.getCreateCatalogId() : " + dpCatalogInfo.getCreateCatalogId());

				// 파일저장 경로
				uploadPath = // "/data7/COUPON"
				this.repositoryPath + File.separator + DateUtil.getToday("yyyyMM") + File.separator
						+ DateUtil.getToday("dd") + File.separator + DateUtil.getTime() + File.separator
						+ dpCatalogInfo.getCreateCatalogId();
				fileNameList = (ArrayList<String>) dpCatalogInfo.getFileNameList();
				isList = (ArrayList<byte[]>) dpCatalogInfo.getIsList();
				this.log.info("uploadPath : " + uploadPath);

			}

			File downloadDir = new File(uploadPath);
			// 디렉토리가 존재하지 않으면 만든다.
			if (!downloadDir.exists()) {
				downloadDir.mkdirs();
				this.log.info("폴더 생성 성공");
			}

			this.log.info("brandImgPath(downloadPath) = " + uploadPath);

			for (int i = 0; i < isList.size(); i++) {
				downloadUrl = fileNameList.get(i);

				if (!"".equals(downloadUrl)) {

					// 다운로드 파일명 추출
					orgDownloadFileName = downloadUrl;
					this.log.info("downloadFileName : " + orgDownloadFileName);

					// 파일명.확장자 형식 체크
					int fileOk = orgDownloadFileName.indexOf(".");

					this.log.info("fileOk : " + fileOk);

					if (fileOk != -1) {

						this.log.info("File DownLoad Start!");

						String downloadFilePath = uploadPath + File.separator + orgDownloadFileName;
						this.log.info("downloadFilePath : " + downloadFilePath);

						try {

							this.log.info("File DownLoad Start!!");
							bytes = isList.get(i);
							IOUtils.write(bytes, new FileOutputStream(downloadFilePath));
							this.log.info("File DownLoad OK!!");
							// Catalog 이미지가 두개라 처리.
							if (i == 0) {
								if (dpCatalogInfo == null) {
									dpBrandInfo.setBrandImgPath(downloadFilePath);
									this.log.info("dpBrandInfo.getBrandImgPath() : " + dpBrandInfo.getBrandImgPath());
								}
								if (dpBrandInfo == null) {
									dpCatalogInfo.setTopImgPath(downloadFilePath);
									this.log.info("dpCatalogInfo.setTopImgPath() : " + dpCatalogInfo.getTopImgPath());
								}

							}
							if (i == 1) {
								if (dpBrandInfo == null) {
									// downloadFilePath2 = uploadPath + File.separator + renFileName;
									dpCatalogInfo.setDtlImgPath(downloadFilePath);
									this.log.info("dpCatalogInfo.getDtlImgPath() : " + dpCatalogInfo.getDtlImgPath());
								}

							}

						} catch (CouponException e) {
							// this.log.info("파일 다운로드 중 오류 발생!!");
							// this.message = "파일 다운로드 중 오류 발생!!";
							throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR,
									"파일 다운로드 중 오류 발생!!", null);

						}

					} else {
						this.log.info("filePath url에 파일정보가 없습니다!");
						message = "filePath url에 파일정보가 없습니다!";
						return false;
					}
				}
			}
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR, message, null);
		} catch (Exception e) {
			// 정보변경 실패 알림 페이지
			e.printStackTrace();
			this.log.error("브랜드 or 카탈로그 파일 정보 가져오기 실패", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR, message, null);
		}
		return true;

	}

	/**
	 * 카탈로그 정보를 추가한다.
	 * 
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return boolean
	 */
	@Override
	public boolean insertCatalogInfo(DpCatalogInfo dpCatalogInfo) {

		String topImgPath = null;
		String dtlImgPath = null;
		String decodeTopImgPath = null;
		String decodeTopDtlImgPath = null;
		String message = "";
		try {

			this.log.info("dpCatalogInfo.getCatalogId() = " + dpCatalogInfo.getCatalogId());

			// 2014.04.02 이현남 매니저 요청 사항 C -> U로 수정 해달라고 요청옴
			this.brandCatalogchangeCud(null, dpCatalogInfo);

			topImgPath = dpCatalogInfo.getTopImgPath();
			dtlImgPath = dpCatalogInfo.getDtlImgPath();

			decodeTopImgPath = URLDecoder.decode(topImgPath, "utf-8");
			decodeTopDtlImgPath = URLDecoder.decode(dtlImgPath, "utf-8");

			dpCatalogInfo.setTopImgPath(decodeTopImgPath);
			dpCatalogInfo.setDtlImgPath(decodeTopDtlImgPath);

			// CUD_TYPE에 따라 INSERT/UPDATE
			if ("C".equalsIgnoreCase(dpCatalogInfo.getCudType())) {

				// 카탈로그 ID 생성
				String catalogID = this.brandCatalogService.searchCreateCatalogId();

				if (StringUtils.isBlank(catalogID)) {
					message = "[catalogId]를 생성하지 못했습니다.";
					return false;
				}

				dpCatalogInfo.setCreateCatalogId(catalogID);
				String brandId = this.getCreateBrandId(dpCatalogInfo.getBrandId());
				if (StringUtils.isBlank(brandId)) {
					message = "[brandId]를 가져오지 못했습니다.";
					return false;
				}
				this.log.info("brandId = " + brandId);
				dpCatalogInfo.setCreateBrandId(brandId);

				this.brandCatalogService.insertCatalogInfo(dpCatalogInfo);
			} else if ("U".equalsIgnoreCase(dpCatalogInfo.getCudType())) {
				this.log.info("***** BrandCatalogService.updateBrandInfo *****");

				String catalogID = this.getCreateCatalogId(dpCatalogInfo.getCatalogId());
				dpCatalogInfo.setCreateCatalogId(catalogID);

				String brandId = this.getCreateBrandId(dpCatalogInfo.getBrandId());
				dpCatalogInfo.setCreateBrandId(brandId);

				this.brandCatalogService.updateCatalogInfo(dpCatalogInfo);

				// 수정일시 TB_DP_PROD_IMG 테이블 삭제
				this.brandCatalogService.deleteDpProdImg(catalogID);

				this.brandCatalogService.deleteTblTagInfo(catalogID);

			}

			// 이미지 가져옴
			this.log.info("============이미지 NAS에 처리 시작====================");
			this.inputBrandFile(null, dpCatalogInfo);
			this.log.info("============이미지 NAS에 처리 끝====================");

			// 이미지 리사이즈 처리
			this.log.info("============이미지 리사이즈 처리 시작====================");
			this.catalogImgResize(dpCatalogInfo);
			this.log.info("============이미지 리사이즈 처리 끝====================");
			// 카탈로그 태그정보 처리
			this.catalogTagList(dpCatalogInfo);

			// cash flush
			this.cacheEvictShoppingMeta(null, dpCatalogInfo);

			// MQ 연동 서비스
			this.getConnectMqForSearchServer(dpCatalogInfo);

		} catch (CouponException e) {
			throw new CouponException(e.getErrCode(), message, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 브랜드 이미지파일 처리.
	 * 
	 * @param dpBrandInfo
	 *            dpBrandInfo
	 * @return boolean
	 */
	public boolean brandImgResize(DpBrandInfo dpBrandInfo) {
		this.log.info("■■■■■BrandImgResize■■■■■ 시작 ");

		// 파일명 끝에 추가할 명칭
		String[] drivedFileNameForDrived = { "_177x177" };

		// TB_DP_PROD_IMG.IMG_CLS 설정
		String[] imgClsCode = { CouponConstants.BRAND_IMG_177_177 };



		String targetFileName = null;
		String brandImgPath = null;
		String uploadDir = null;
		String srcFileName = null;
		String tmpFileName = null;
		String fileExt = null;

		// TB_DP_PROD_IMG 테이블에 DP_ORDER컬럼에 값 셋팅
		int seq = 1;
		try {
			brandImgPath = dpBrandInfo.getBrandImgPath();
			uploadDir = brandImgPath.substring(0, brandImgPath.lastIndexOf(File.separator) + 1);
			srcFileName = brandImgPath.substring(brandImgPath.lastIndexOf(File.separator)).replace(File.separator, "");
			tmpFileName = srcFileName.substring(0, srcFileName.lastIndexOf(".")); // 파일명
			fileExt = srcFileName.substring(srcFileName.lastIndexOf(".") + 1); // 확장자
			for (int i = 0; i < drivedFileNameForDrived.length; i++) {

				targetFileName = tmpFileName + "." + fileExt; // fileExt 대신 타입으로 확장자
				String outFile = uploadDir + targetFileName;

				File outputfile = new File(outFile);
				long fileSize = outputfile.length();

				this.brandCatalogProdImgInfo.setProdId(dpBrandInfo.getCreateBrandId());
				this.brandCatalogProdImgInfo.setImgCls(imgClsCode[i]);
				this.brandCatalogProdImgInfo.setFileSize(fileSize);
				this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
				this.brandCatalogProdImgInfo.setFilePos(uploadDir);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName);
				this.brandCatalogProdImgInfo.setSeq(seq);
				seq++;
				// TB_DP_PROD_IMG 테이블에 INSERT
				this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);
			}

		} catch (CouponException e) {
			this.log.error("<brandImgResize> brandResize : ", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "브랜드 이미지 Resize 실패", null);

		}
		return true;
	}

	/**
	 * 카탈로그 이미지파일 처리.
	 * 
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return boolean
	 */
	public boolean catalogImgResize(DpCatalogInfo dpCatalogInfo) {

		ImageUtil imgUtil = new ImageUtil();
		// 파일명 끝에 추가할 명칭

		// TB_DP_PROD_IMG.IMG_CLS 설정
		String[] imgClsCodeTop = { CouponConstants.CATALOG_TOP_IMG_182_182 };
		String[] imgClsCodeDtl = { CouponConstants.CATALOG_DTL_IMG_684_X };

		String targetFileName = null;
		String brandImgPath = null;
		String uploadDir = null;
		String srcFileName = null;
		String tmpFileName = null;
		String fileExt = null;
		String targetFileName1 = null;
		String brandImgPath1 = null;
		String uploadDir1 = null;
		String srcFileName1 = null;
		String tmpFileName1 = null;
		String fileExt1 = null;

		try {

			// 카탈로그 대표이미지 변수
			brandImgPath = dpCatalogInfo.getTopImgPath(); // 이미지경로 + 이미지NAME
			uploadDir = brandImgPath.substring(0, brandImgPath.lastIndexOf(File.separator) + 1); // 이미지 경로
			srcFileName = brandImgPath.substring(brandImgPath.lastIndexOf(File.separator)).replace(File.separator, ""); // 이미지NAME
			tmpFileName = srcFileName.substring(0, srcFileName.lastIndexOf(".")); // 파일명
			fileExt = srcFileName.substring(srcFileName.lastIndexOf(".") + 1); // 확장자
			// 카탈로그 상세이미지 변수
			brandImgPath1 = dpCatalogInfo.getDtlImgPath(); // 이미지경로 + 이미지NAME
			uploadDir1 = brandImgPath1.substring(0, brandImgPath1.lastIndexOf(File.separator) + 1); // 이미지 경로
			srcFileName1 = brandImgPath1.substring(brandImgPath1.lastIndexOf(File.separator)).replace(File.separator,
					""); // 이미지NAME
			tmpFileName1 = srcFileName1.substring(0, srcFileName1.lastIndexOf(".")); // 파일명
			fileExt1 = srcFileName1.substring(srcFileName1.lastIndexOf(".") + 1); // 확장자

			Image img = new ImageIcon(uploadDir1 + srcFileName1).getImage(); // 이미지 사이즈를 구하기 위해 이미지를 가져옴.

			int oWidth = img.getWidth(null); // 이미지 가로사이즈
			int oHeight = img.getHeight(null); // 이미지 세로사이즈
			int nHeightSize = oHeight;
			this.log.info("■■■■■oWidth■■■■■ ::" + oWidth);
			this.log.info("■■■■■oHeight■■■■■ :: " + oHeight);
			
			// 파일 생성 크기
			int[][] imageSizeForDrivedDtl = { { oWidth, nHeightSize } };

			// 카탈로그 대표이미지 리사이즈
			for (int i = 0; i < 1; i++) {

				targetFileName = tmpFileName + "." + fileExt;
				int seq = 1;

				String outFile = uploadDir + targetFileName;

				File outputfile = new File(outFile);
				long fileSize = outputfile.length();

				this.brandCatalogProdImgInfo.setProdId(dpCatalogInfo.getCreateCatalogId());
				this.brandCatalogProdImgInfo.setImgCls(imgClsCodeTop[i]);
				this.brandCatalogProdImgInfo.setFileSize(fileSize);
				this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
				this.brandCatalogProdImgInfo.setFilePos(uploadDir);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName);
				this.brandCatalogProdImgInfo.setSeq(seq);

				// TB_DP_PROD_IMG 테이블에 INSERT
				this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);
			}

			// 카탈로그 상세이미지 리사이즈
			for (int i = 0; i < 1; i++) {

				targetFileName1 = tmpFileName1 + "." + fileExt;
				int width = imageSizeForDrivedDtl[i][0];
				int height = imageSizeForDrivedDtl[i][1];
				int seq = 1;

				String cutOutFile = uploadDir1 + targetFileName1;

				this.log.info("■■■■■CatalogImg■■■■■ : " + targetFileName1 + "을 생성 하였습니다.");

				File outputfile = new File(cutOutFile);
				long fileSize = outputfile.length();

				this.brandCatalogProdImgInfo.setProdId(dpCatalogInfo.getCreateCatalogId());
				this.brandCatalogProdImgInfo.setImgCls(CouponConstants.CATALOG_DTL_IMG_684_XY);
				this.brandCatalogProdImgInfo.setFileSize(fileSize);
				this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
				this.brandCatalogProdImgInfo.setFilePos(uploadDir1);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName1);
				this.brandCatalogProdImgInfo.setSeq(seq);

				this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);

				this.brandCatalogProdImgInfo.setImgCls(imgClsCodeDtl[i]);

				// 이미지 세로길이가 1170보다 클경우
				if (nHeightSize > 1170) {
					this.log.info("■■■■■이미지 세로길이가 1170보다 클경우■■■■■");
					int cY = 0; // 이미지 컷처리 시작

					nHeightSize = 1170;
					this.log.info("■■■■■nHeightSize■■■■■" + nHeightSize);
					// 이미지를 1170사이즈까지 잘라서 저장
					for (seq = 1; nHeightSize < height; seq++) {

						String resizetargetFileName1 = tmpFileName1 + "_"+oWidth+"xy" + seq + "." + fileExt1;

						if (!imgUtil.cutImage2(uploadDir1 + targetFileName1, uploadDir1 + resizetargetFileName1, 0, cY,
								width, 1170)) {
							throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ",
									null);
						}
						this.brandCatalogProdImgInfo.setFileNm(resizetargetFileName1);
						this.log.info("■■■■■catalogImg■■■■■ : " + resizetargetFileName1 + "을 생성 하였습니다.");

						cY = nHeightSize;
						nHeightSize = nHeightSize + 1170;
						File cutOutputfile = new File(uploadDir1 + resizetargetFileName1);
						long cutFileSize = cutOutputfile.length();

						this.brandCatalogProdImgInfo.setFileSize(cutFileSize);
						this.brandCatalogProdImgInfo.setSeq(seq);
						this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
						this.brandCatalogProdImgInfo.setFileNm(resizetargetFileName1);

						// TB_DP_PROD_IMG 테이블에 INSERT
						this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);

						// 이미지 마지막 1170보다 작은 이미지 생성및 DB처리
						if (height <= nHeightSize) {
							this.log.info("■■■■■이미지 마지막 1170보다 작은 이미지 생성■■■■■");
							seq = seq + 1;

							resizetargetFileName1 = tmpFileName1 + "_"+oWidth+"xy" + seq + "." + fileExt1;
							this.log.info("■■■■■resizetargetFileName1■■■■■ :" + resizetargetFileName1 + "을 생성 하였습니다.");

							if (!imgUtil.cutImage2(uploadDir1 + targetFileName1, uploadDir1 + resizetargetFileName1, 0,
									cY, width, height - cY)) {
								throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR,
										"이미지 생성 오류!!", null);
							}
							File lastCutOutputfile = new File(uploadDir1 + resizetargetFileName1);
							long lastCutFileSize = lastCutOutputfile.length();

							this.brandCatalogProdImgInfo.setFileSize(lastCutFileSize);
							this.brandCatalogProdImgInfo.setSeq(seq);
							this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
							this.brandCatalogProdImgInfo.setFileNm(resizetargetFileName1);

							// TB_DP_PROD_IMG 테이블에 INSERT/UPDATE
							this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);

						}
					}
				} else {
					this.log.info("■■■■■이미지 세로길이가 1170보다 작을경우■■■■■");

					// 이미지 세로의 길이가 1170보다 작을경우 INSERT
					this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);

				}
			}

		} catch (CouponException e) {
			this.log.info("■■■■■이미지 생성 오류■■■■■ ");
			this.log.error("catalogImgResize> catalogResize : ", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "카탈로그 이미지 Resize 실패", null);
		}

		return true;
	}

	/**
	 * TB_DP_PROD_TAG 정보를 설정.
	 * 
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return boolean
	 */
	public boolean catalogTagList(DpCatalogInfo dpCatalogInfo) {

		try {
			ArrayList<DpCatalogTagInfo> tagList = new ArrayList<DpCatalogTagInfo>(); // TB_DP_PROD_TAG 정보
			if (StringUtils.isNotBlank(dpCatalogInfo.getCatalogTag())) {
				String[] tags = dpCatalogInfo.getCatalogTag().split(",");

				for (String tagNm : tags) {
					DpCatalogTagInfo tagInfo = new DpCatalogTagInfo();

					tagInfo.setCid(dpCatalogInfo.getCreateCatalogId());
					tagInfo.setTagTypeCd(CouponConstants.TAG_TYPE_FOR_COUPON_TAG);
					tagInfo.setTagCd("");
					tagInfo.setTagNm(tagNm);
					tagInfo.setRegId("admin");
					tagInfo.setUpdId("admin");
					tagList.add(tagInfo);
				}
			}

			for (int i = 0; i < tagList.size(); i++) {
				this.brandCatalogService.insertTblTagInfo(tagList.get(i));
			}

		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, "태그 정보 저장 실패", null);
		}
		return true;
	}

	/**
	 * 해당 쿼리에 해당하는 BRAND_ID를 리턴한다.
	 * 
	 * @param brandId
	 *            brandId
	 * @return String
	 */
	public String getCreateBrandId(String brandId) {
		this.log.info("<<<BrandCatalogService>>> getBrandId...");
		return this.brandCatalogService.getCreateBrandId(brandId);
	}

	/**
	 * 해당 쿼리에 해당하는 CATALOG_ID를 리턴한다.
	 * 
	 * @param catalogId
	 *            ++9+
	 * @return String
	 */
	public String getCreateCatalogId(String catalogId) {
		this.log.info("<<<BrandCatalogService>>> getCreateCatalogId...");
		return this.brandCatalogService.getCreateCatalogId(catalogId);
	}

	/**
	 * 2014.04.02 이현남 매니저 요청 사항 C -> U로 수정 해달라고 요청옴.
	 * 
	 * @param dpBrandInfo
	 *            dpBrandInfo
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return
	 */
	private void brandCatalogchangeCud(DpBrandInfo dpBrandInfo, DpCatalogInfo dpCatalogInfo) {
		// 브랜드 C->U 변경
		if (dpBrandInfo != null) {
			if ("C".equalsIgnoreCase(dpBrandInfo.getCudType())) {
				if (this.brandCatalogService.getBrandCatalogChangeCudType(dpBrandInfo.getBrandId(), null) > 0) {
					dpBrandInfo.setCudType("U");
				}
			}
		}

		// 카탈로그 C->U 변경
		if (dpCatalogInfo != null) {
			if ("C".equalsIgnoreCase(dpCatalogInfo.getCudType())) {
				if (this.brandCatalogService.getBrandCatalogChangeCudType(null, dpCatalogInfo.getCatalogId()) > 0) {
					dpCatalogInfo.setCudType("U");
				}
			}
		}
	}

	/**
	 * cash flush .
	 * 
	 * @param dpBrandInfo
	 *            dpBrandInfo
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return
	 */
	private void cacheEvictShoppingMeta(DpBrandInfo dpBrandInfo, DpCatalogInfo dpCatalogInfo) {
		// 브랜드 cash flush
		if (dpBrandInfo != null) {
			if ("U".equalsIgnoreCase(dpBrandInfo.getCudType())) {
				this.log.info("Brand Update cash Brand Id : " + dpBrandInfo.getCreateBrandId());
				this.cacheEvictHelperComponent.evictProductMetaByBrand(dpBrandInfo.getCreateBrandId());
			}
		}

		// 카탈로그 cash flush
		if (dpCatalogInfo != null) {
			if ("U".equalsIgnoreCase(dpCatalogInfo.getCudType())) {
				this.log.info("Catalog Update cash Catalog Id : " + dpCatalogInfo.getCreateCatalogId());
				this.cacheEvictHelperComponent.evictProductMeta(Shopping, dpCatalogInfo.getCreateCatalogId());
			}
		}
	}

	

	/**
	 * getConnectMqForSearchServer MQ 연동
	 * 
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 */

	private void getConnectMqForSearchServer(DpCatalogInfo dpCatalogInfo) {

		this.log.info("■■■■■ 카탈로그 검색서버를 위한 MQ 연동 start ■■■■■");

		// 수정일때 그냥 무조건 MQ 연동
		if ("U".equalsIgnoreCase(dpCatalogInfo.getCudType())) {
			SearchInterfaceQueue queueMsg = SearchQueueUtils.makeMsg(
					"U"
					, CouponConstants.TOP_MENU_ID_CUPON_CONTENT
					, SearchConstant.UPD_ID_SAC_SHOPPING.toString()
					, SearchConstant.CONTENT_TYPE_CATALOG.toString()
					, dpCatalogInfo.getCreateCatalogId()
					);

			this.sacSearchAmqpTemplate.convertAndSend(queueMsg);
			this.log.info("=================================================");
			this.log.info("==MQ 연동 성공 :: queueMsg ::================" + queueMsg.toString());
			this.log.info("==MQ 연동 성공 :: catalogId ::================" + dpCatalogInfo.getCreateCatalogId());
			this.log.info("=================================================");

		}

		this.log.info("■■■■■ 카탈로그 검색서버를 위한 MQ 연동 end ■■■■■");

	}
}
