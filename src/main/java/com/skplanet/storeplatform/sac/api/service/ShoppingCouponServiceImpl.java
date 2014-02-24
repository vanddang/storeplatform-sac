package com.skplanet.storeplatform.sac.api.service;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.ImageUtil;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;

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

	public final BrandCatalogProdImgInfo brandCatalogProdImgInfo;

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

			// CUD_TYPE에 따라 INSERT/UPDATE
			if ("C".equalsIgnoreCase(dpBrandInfo.getCudType())) {
				// 브랜드ID 생성
				String brandId = this.brandCatalogService.searchCreateBrandId();

				if (StringUtils.isBlank(brandId)) {
					message = "[COUPON_CONTENT_ID]를 생성하지 못했습니다.";
					return false;
				}
				dpBrandInfo.setCreateBrandId(brandId);
				// 신규등록일시 TBL_DP_TSP_BRAND_INFO테이블 INSERT
				this.brandCatalogService.insertBrandInfo(dpBrandInfo);
			} else if ("U".equalsIgnoreCase(dpBrandInfo.getCudType())) {

				String brandID = this.getCreateBrandId(dpBrandInfo.getBrandId());
				dpBrandInfo.setCreateBrandId(brandID);

				// // 수정일시 TBL_DP_TSP_BRAND_INFO테이블 UPDATE
				this.brandCatalogService.updateBrandInfo(dpBrandInfo);
				//
				// // 수정일시 TBL_DP_PROD_IMG 테이블 삭제
				this.brandCatalogService.deleteDpProdImg(brandID);
			}

			// 이미지 가져옴
			this.inputBrandFile(dpBrandInfo, null);

			// 이미지 리사이즈 처리
			this.brandImgResize(dpBrandInfo);

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

		String repositoryPath = null;

		OutputStream os = null;
		URLConnection urlcon = null;
		InputStream is = null;
		String uploadPath = null;

		String downloadUrl = "";
		String orgDownloadFileName = "";
		String renFileName = "";
		// String downloadFilePath2 = "";
		ArrayList<String> fileList = new ArrayList<String>();
		File destFile = null;
		File copyFile = null;
		String message = "";
		try {

			// repositoryPath = getLabelForQuery(new String[] { CouponConstants.PCD_FOR_REPOSITORY_PATH,
			// CouponConstants.TYPE_FOR_REPOSITORY_PATH });

			repositoryPath = "/data1/SMILE_DATA";
			// log.info("repositoryPath : " + repositoryPath);

			// 브랜드 이미지 처리
			if (dpCatalogInfo == null) {

				this.log.info("filepath : " + dpBrandInfo.getBrandImgPath());

				// 다운로드할 파일 리스트
				fileList.add(dpBrandInfo.getBrandImgPath());

				// 파일저장 경로
				uploadPath = // "/data5/SMILE_DATA5/COUPON"
				repositoryPath + File.separator + CouponConstants.BRAND_CATALOG_PATH + File.separator
						+ DateUtil.getToday("yyyyMM") + File.separator + DateUtil.getToday("dd") + File.separator
						+ dpBrandInfo.getCreateBrandId();

				this.log.info("uploadPath : " + uploadPath);
				dpBrandInfo.setBrandImgPath(uploadPath);
			}

			// 카탈로그 이미지 처리
			if (dpBrandInfo == null) {
				this.log.info("dpCatalogInfo.getCreateCatalogId() : " + dpCatalogInfo.getCreateCatalogId());
				// 다운로드할 파일 리스트
				fileList.add(dpCatalogInfo.getTopImgPath());
				fileList.add(dpCatalogInfo.getDtlImgPath());

				// 파일저장 경로
				uploadPath = // "/data5/SMILE_DATA5/COUPON"
				repositoryPath + File.separator + CouponConstants.BRAND_CATALOG_PATH + File.separator
						+ DateUtil.getToday("yyyyMM") + File.separator + DateUtil.getToday("dd") + File.separator
						+ dpCatalogInfo.getCreateCatalogId();

				this.log.info("uploadPath : " + uploadPath);

			}

			File downloadDir = new File(uploadPath);
			// 디렉토리가 존재하지 않으면 만든다.
			if (!downloadDir.exists()) {
				downloadDir.mkdirs();
			}

			this.log.info("brandImgPath(downloadPath) = " + uploadPath);

			for (int i = 0; i < fileList.size(); i++) {
				downloadUrl = fileList.get(i);

				if (!"".equals(downloadUrl)) {

					// 다운로드 파일명 추출
					orgDownloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf("/")).replace("/", "");
					this.log.info("downloadFileName : " + orgDownloadFileName);

					// 파일명.확장자 형식 체크
					int fileOk = orgDownloadFileName.indexOf(".");

					this.log.info("fileOk : " + fileOk);

					if (fileOk != -1) {

						this.log.info("File DownLoad Start!!");

						String downloadFilePath = uploadPath + File.separator + orgDownloadFileName;
						this.log.info("downloadFilePath : " + downloadFilePath);

						try {

							this.log.info("File URLConnection Start!!");

							URL url = new URL(downloadUrl);
							try {

								urlcon = url.openConnection();
								this.log.info("File URLConnection OK!!");

							} catch (Exception e) {

								this.log.info("다운로드 URL에 접속 1차 실패!!");

								try {

									urlcon = url.openConnection();
									this.log.info("File URLConnection OK!!");

								} catch (Exception e1) {

									this.log.info("다운로드 URL에 접속 2차 실패!!");
									throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR,
											"다운로드 URL에 접속 2차 실패!!", null);
								}
							}

							try {

								this.log.info("File DownLoad Start!!");

								copyFile = new File(downloadFilePath);

								if (copyFile.exists()) {
									renFileName = orgDownloadFileName.substring(0, fileOk) + DateUtil.getTime() + "("
											+ i + ")"
											+ orgDownloadFileName.substring(orgDownloadFileName.lastIndexOf("."));

									this.log.info("DateUtil.getTime()" + DateUtil.getTime());
									this.log.info("renFileName : " + renFileName);
									destFile = new File(uploadPath, renFileName);
									FileUtils.copyFile(copyFile, destFile);
									downloadFilePath = uploadPath + File.separator + renFileName;

									os = new BufferedOutputStream(new FileOutputStream(downloadFilePath));
									is = urlcon.getInputStream();
									int readFileValue;

									while ((readFileValue = is.read()) != -1) {
										os.write(readFileValue);
									}

									is.close();
									os.close();

									this.log.info("File DownLoad OK!!");

								} else {

									os = new BufferedOutputStream(new FileOutputStream(downloadFilePath));
									is = urlcon.getInputStream();
									int readFileValue;

									while ((readFileValue = is.read()) != -1) {
										os.write(readFileValue);
									}

									is.close();
									os.close();

									this.log.info("File DownLoad OK!!");
								}

								// Catalog 이미지가 두개라 처리.
								if (i == 0) {
									if (dpCatalogInfo == null) {
										dpBrandInfo.setBrandImgPath(downloadFilePath);
										this.log.info("dpBrandInfo.getBrandImgPath() : "
												+ dpBrandInfo.getBrandImgPath());
									}
									if (dpBrandInfo == null) {
										dpCatalogInfo.setTopImgPath(downloadFilePath);
										this.log.info("dpCatalogInfo.setTopImgPath() : "
												+ dpCatalogInfo.getTopImgPath());
									}

								}
								if (i == 1) {
									if (dpBrandInfo == null) {
										// downloadFilePath2 = uploadPath + File.separator + renFileName;
										dpCatalogInfo.setDtlImgPath(downloadFilePath);
										this.log.info("dpCatalogInfo.getDtlImgPath() : "
												+ dpCatalogInfo.getDtlImgPath());
									}

								}

							} catch (CouponException e) {
								// this.log.info("파일 다운로드 중 오류 발생!!");
								// this.message = "파일 다운로드 중 오류 발생!!";
								throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR,
										"파일 다운로드 중 오류 발생!!", null);

							}

						} catch (CouponException e) {
							// this.log.error(e);
							throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR, message, null);

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
			this.log.error("브랜드,카탈로그 파일 정보 가져오기 실패", e);
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
					message = "[COUPON_catalogId]를 생성하지 못했습니다.";
					return false;
				}

				dpCatalogInfo.setCreateCatalogId(catalogID);
				String brandId = this.getCreateBrandId(dpCatalogInfo.getBrandId());
				if (StringUtils.isBlank(brandId)) {
					message = "[COUPON_brandId]를 가져오지 못했습니다.";
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

				// 수정일시 TBL_DP_PROD_IMG 테이블 삭제
				this.brandCatalogService.deleteDpProdImg(catalogID);

				// 수정일시 TBL_TAG_INFO 데이터 삭제
				this.brandCatalogService.deleteTblTagInfo(catalogID);

			}

			// 이미지 가져옴
			this.log.info("이미지 처리 시작");
			this.inputBrandFile(null, dpCatalogInfo);

			// 이미지 리사이즈 처리
			this.catalogImgResize(dpCatalogInfo);

			// 카탈로그 태그정보 처리
			this.catalogTagList(dpCatalogInfo);

		} catch (CouponException e) {
			throw new CouponException(e.getErrCode(), message, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 브랜드 이미지파일 리사이즈 처리.
	 * 
	 * @param dpBrandInfo
	 *            dpBrandInfo
	 * @return boolean
	 */
	public boolean brandImgResize(DpBrandInfo dpBrandInfo) {
		// 파일명 끝에 추가할 명칭
		String[] drivedFileNameForDrived = { "_260x170", "_177x177", "_114x114", "_29x29", "_56x56", "_27x27", "_25x25" };

		// TBL_DP_PROD_IMG.IMG_CLS 설정
		String[] imgClsCode = { CouponConstants.BRAND_IMG_260x170, CouponConstants.BRAND_IMG_177x177,
				CouponConstants.BRAND_IMG_114x114, CouponConstants.BRAND_IMG_29x29, CouponConstants.BRAND_IMG_56x56,
				CouponConstants.BRAND_IMG_27x27, CouponConstants.BRAND_IMG_25x25 };

		// 파일 생성 크기
		int[][] imageSizeForDrived = { { 260, 170 }, { 177, 177 }, { 114, 114 }, { 29, 29 }, { 56, 56 }, { 27, 27 },
				{ 25, 25 } };
		// String IMAGE_TYPE = "PNG";

		String targetFileName = null;
		String brandImgPath = null;
		String uploadDir = null;
		String srcFileName = null;
		String tmpFileName = null;
		String fileExt = null;

		// TBL_DP_PROD_IMG 테이블에 DP_ORDER컬럼에 값 셋팅
		int seq = 1;

		try {
			brandImgPath = dpBrandInfo.getBrandImgPath();
			uploadDir = brandImgPath.substring(0, brandImgPath.lastIndexOf(File.separator) + 1);
			srcFileName = brandImgPath.substring(brandImgPath.lastIndexOf(File.separator)).replace(File.separator, "");
			tmpFileName = srcFileName.substring(0, srcFileName.lastIndexOf(".")); // 파일명
			fileExt = srcFileName.substring(srcFileName.lastIndexOf(".") + 1); // 확장자
			for (int i = 0; i < drivedFileNameForDrived.length; i++) {

				targetFileName = tmpFileName + drivedFileNameForDrived[i] + "." + fileExt; // fileExt 대신 타입으로 확장자
																						   // PNG

				int width = imageSizeForDrived[i][0];
				int height = imageSizeForDrived[i][1];

				// 이미지 리사이즈 처리
				File srcFile = new File(uploadDir + srcFileName);
				String outFile = uploadDir + targetFileName;

				// 이미지 리사이즈
				if (!ImageUtil.setImgScale(srcFile, outFile, width, height, uploadDir)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ", null);
				}
				// log.info("■■■■■BrandImgResize■■■■■ : " + targetFileName + "을 생성 하였습니다.");

				this.brandCatalogProdImgInfo.setProdId(dpBrandInfo.getCreateBrandId());
				this.brandCatalogProdImgInfo.setImgCls(imgClsCode[i]);
				this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
				this.brandCatalogProdImgInfo.setFilePos(uploadDir);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName);
				this.brandCatalogProdImgInfo.setSeq(seq);
				seq++;
				// TBL_DP_PROD_IMG 테이블에 INSERT/UPDATE
				this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);
			}

		} catch (CouponException e) {
			this.log.error("<brandImgResize> brandResize : ", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "브랜드 이미지 Resize 실패", null);

		}
		return true;
	}

	/**
	 * 카탈로그 이미지파일 리사이즈 처리.
	 * 
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return boolean
	 */
	public boolean catalogImgResize(DpCatalogInfo dpCatalogInfo) {

		// 파일명 끝에 추가할 명칭
		String[] drivedFileNameForDrivedTop = { "_60x60", "_120x120", "_130x130", "_40x40", "_80x80", "_110x110",
				"_180x180", "_182x182", "_31x30" };
		String[] drivedFileNameForDrivedDtl = { "_684x" };

		// TBL_DP_PROD_IMG.IMG_CLS 설정
		String[] imgClsCodeTop = { CouponConstants.CATALOG_TOP_IMG_60x60, CouponConstants.CATALOG_TOP_IMG_120x120,
				CouponConstants.CATALOG_TOP_IMG_130x130, CouponConstants.CATALOG_TOP_IMG_40x40,
				CouponConstants.CATALOG_TOP_IMG_80x80, CouponConstants.CATALOG_TOP_IMG_110x110,
				CouponConstants.CATALOG_TOP_IMG_180x180, CouponConstants.CATALOG_TOP_IMG_182x182,
				CouponConstants.CATALOG_TOP_IMG_31x30 };

		String[] imgClsCodeDtl = { CouponConstants.CATALOG_DTL_IMG_684x };

		// String IMAGE_TYPE = "PNG";

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
			this.log.info("brandImgPath : " + brandImgPath);
			uploadDir = brandImgPath.substring(0, brandImgPath.lastIndexOf(File.separator) + 1); // 이미지 경로
			this.log.info("uploadDir : " + uploadDir);
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

			int nHeightSize = oHeight * 684 / oWidth;

			// 파일 생성 크기
			int[][] imageSizeForDrived = { { 60, 60 }, { 120, 120 }, { 130, 130 }, { 40, 40 }, { 80, 80 },
					{ 110, 110 }, { 180, 180 }, { 182, 182 }, { 31, 30 } };
			int[][] imageSizeForDrivedDtl = { { 684, nHeightSize } };

			// 카탈로그 대표이미지 리사이즈
			for (int i = 0; i < drivedFileNameForDrivedTop.length; i++) {

				targetFileName = tmpFileName + drivedFileNameForDrivedTop[i] + "." + fileExt;

				int width = imageSizeForDrived[i][0];
				int height = imageSizeForDrived[i][1];
				int seq = 1;

				// 이미지 리사이즈 처리
				File srcFile = new File(uploadDir + srcFileName);
				String outFile = uploadDir + targetFileName;

				// 이미지 리사이즈
				ImageUtil.setImgScale(srcFile, outFile, width, height, uploadDir);
				// 이미지 리사이즈
				if (!ImageUtil.setImgScale(srcFile, outFile, width, height, uploadDir)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ", null);
				}
				this.log.info("■■■■■BrandImgResize■■■■■ : " + targetFileName + "을 생성 하였습니다.");

				this.brandCatalogProdImgInfo.setProdId(dpCatalogInfo.getCreateCatalogId());
				this.brandCatalogProdImgInfo.setImgCls(imgClsCodeTop[i]);
				this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
				this.brandCatalogProdImgInfo.setFilePos(uploadDir);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName);
				this.brandCatalogProdImgInfo.setSeq(seq);

				// TBL_DP_PROD_IMG 테이블에 INSERT/UPDATE
				this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);
			}

			// 카탈로그 상세이미지 리사이즈
			for (int i = 0; i < drivedFileNameForDrivedDtl.length; i++) {

				targetFileName1 = tmpFileName1 + drivedFileNameForDrivedDtl[i] + "." + fileExt1;

				int width = imageSizeForDrivedDtl[i][0];
				int height = imageSizeForDrivedDtl[i][1];
				int seq = 1;

				// 이미지 리사이즈 처리
				File srcFile1 = new File(uploadDir1 + srcFileName1);
				String cutOutFile = uploadDir1 + targetFileName1;

				// 이미지 리사이즈
				if (!ImageUtil.setImgScale(srcFile1, cutOutFile, width, height, uploadDir)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ", null);
				}
				ImageUtil.setImgScale(srcFile1, cutOutFile, width, height, uploadDir1);

				this.log.info("■■■■■BrandImgResize■■■■■ : " + targetFileName1 + "을 생성 하였습니다.");

				this.brandCatalogProdImgInfo.setProdId(dpCatalogInfo.getCreateCatalogId());
				this.brandCatalogProdImgInfo.setImgCls(CouponConstants.CATALOG_DTL_IMG_684xY);
				this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
				this.brandCatalogProdImgInfo.setFilePos(uploadDir1);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName1);
				this.brandCatalogProdImgInfo.setSeq(seq);

				// 카탈로그이미지 2013.06.24 이미지 추가684xY
				this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);

				this.brandCatalogProdImgInfo.setImgCls(imgClsCodeDtl[i]);

				// 이미지 세로길이가 1170보다 클경우
				if (nHeightSize > 1170) {
					this.log.info("■■■■■이미지 세로길이가 1170보다 클경우■■■■■");
					int cY = 0; // 이미지 컷처리 시작지

					nHeightSize = 1170;

					// 이미지를 1170사이즈까지 잘라서 저장
					for (seq = 1; nHeightSize < height; seq++) {

						String resizetargetFileName1 = tmpFileName1 + "_684xy" + seq + "." + fileExt1;
						// 이미지 리사이즈
						if (!ImageUtil.setImgScale(srcFile1, cutOutFile, width, height, uploadDir)) {
							throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ",
									null);
						}
						ImageUtil.cutImage2(uploadDir1 + targetFileName1, uploadDir1 + resizetargetFileName1, 0, cY,
								width, 1170);
						this.brandCatalogProdImgInfo.setFileNm(resizetargetFileName1);
						// log.info("■■■■■BrandImgResize■■■■■ : " + resizetargetFileName1 + "을 생성 하였습니다.");

						cY = nHeightSize;
						nHeightSize = nHeightSize + 1170;

						this.brandCatalogProdImgInfo.setSeq(seq);
						this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
						this.brandCatalogProdImgInfo.setFileNm(resizetargetFileName1);

						// TBL_DP_PROD_IMG 테이블에 INSERT/UPDATE
						this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);

						// 이미지 마지막 1170보다 작은 이미지 생성및 DB처리
						if (height <= nHeightSize) {
							this.log.info("■■■■■이미지 마지막 1170보다 작은 이미지 생성■■■■■");
							seq = seq + 1;

							resizetargetFileName1 = tmpFileName1 + "_684xy" + seq + "." + fileExt1;
							this.log.info("resizetargetFileName1" + resizetargetFileName1);

							ImageUtil.cutImage2(uploadDir1 + targetFileName1, uploadDir1 + resizetargetFileName1, 0,
									cY, width, height - cY);
							this.brandCatalogProdImgInfo.setFileNm(resizetargetFileName1);
							// log.info("■■■■■BrandImgResize■■■■■ : " + resizetargetFileName1 + "을 생성 하였습니다.");

							this.brandCatalogProdImgInfo.setSeq(seq);
							this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
							this.brandCatalogProdImgInfo.setFileNm(resizetargetFileName1);

							// TBL_DP_PROD_IMG 테이블에 INSERT/UPDATE
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
			// resize 오류 Catch 및 CouponException 처리 2013.08.07 임선택
			this.log.error("<brandImgResize> brandResize : ", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "카탈로그 이미지 Resize 실패", null);
		}

		return true;
	}

	/**
	 * TBL_TAG_INFO 정보를 설정.
	 * 
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return boolean
	 */
	public boolean catalogTagList(DpCatalogInfo dpCatalogInfo) {

		try {
			ArrayList<DpCatalogTagInfo> tagList = new ArrayList<DpCatalogTagInfo>(); // TBL_DP_TAG_INFO 정보
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

}
