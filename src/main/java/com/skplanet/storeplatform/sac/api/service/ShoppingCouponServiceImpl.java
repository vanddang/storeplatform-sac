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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.ImageUtil;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;

@Service
@Transactional
public class ShoppingCouponServiceImpl implements ShoppingCouponService {
	private String errorCode = "";
	private String message = "";
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BrandCatalogService brandCatalogService;

	public final BrandCatalogProdImgInfo brandCatalogProdImgInfo;

	public ShoppingCouponServiceImpl() {
		this.brandCatalogProdImgInfo = new BrandCatalogProdImgInfo();
	}

	@Override
	public boolean insertBrandInfo(DpBrandInfo dpBrandInfo) {
		String BrandImgPath = null;
		String DecodeBrandImgPath = null;

		try {
			BrandImgPath = dpBrandInfo.getBrandImgPath();
			DecodeBrandImgPath = URLDecoder.decode(BrandImgPath, "utf-8");
			dpBrandInfo.setBrandImgPath(DecodeBrandImgPath);

			// CUD_TYPE에 따라 INSERT/UPDATE
			if ("C".equalsIgnoreCase(dpBrandInfo.getCudType())) {
				// 브랜드ID 생성
				String brandId = this.brandCatalogService.searchCreateBrandId();

				if (StringUtils.isBlank(brandId)) {
					this.errorCode = CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC;
					this.message = "[COUPON_CONTENT_ID]를 생성하지 못했습니다.";
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
			throw new CouponException(e.getErrCode(), e.getMessage(), null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		} finally {
		}

		return true;
	}

	/**
	 * 브랜드,카탈로그 파일 이미지 처리
	 * 
	 * @return
	 */
	public boolean inputBrandFile(DpBrandInfo dpBrandInfo, DpCatalogInfo dpCatalogInfo) {

		// System.out.println("<inputBrandFile> inputBrandFile...");

		String repositoryPath = null;

		OutputStream os = null;
		URLConnection urlcon = null;
		InputStream is = null;
		String uploadPath = null;

		String downloadUrl = "";
		File downloadFile = null;
		String orgDownloadFileName = "";
		String renFileName = "";
		// String downloadFilePath2 = "";
		ArrayList<String> fileList = new ArrayList<String>();
		File destFile = null;
		File copyFile = null;

		try {

			// repositoryPath = getLabelForQuery(new String[] { CouponConstants.PCD_FOR_REPOSITORY_PATH,
			// CouponConstants.TYPE_FOR_REPOSITORY_PATH });

			repositoryPath = "/data1/SMILE_DATA";
			// System.out.println("repositoryPath : " + repositoryPath);

			// 브랜드 이미지 처리
			if (dpCatalogInfo == null) {

				System.out.println("filepath : " + dpBrandInfo.getBrandImgPath());

				// 다운로드할 파일 리스트
				fileList.add(dpBrandInfo.getBrandImgPath());

				// 파일저장 경로
				uploadPath = // "/data5/SMILE_DATA5/COUPON"
				repositoryPath + File.separator + CouponConstants.Brand_Catalog_Path + File.separator
						+ DateUtil.getToday("yyyyMM") + File.separator + DateUtil.getToday("dd") + File.separator
						+ dpBrandInfo.getCreateBrandId();

				System.out.println("uploadPath : " + uploadPath);
				dpBrandInfo.setBrandImgPath(uploadPath);
			}

			// 카탈로그 이미지 처리
			if (dpBrandInfo == null) {
				System.out.println("dpCatalogInfo.getCreateCatalogId() : " + dpCatalogInfo.getCreateCatalogId());
				// 다운로드할 파일 리스트
				fileList.add(dpCatalogInfo.getTopImgPath());
				fileList.add(dpCatalogInfo.getDtlImgPath());

				// 파일저장 경로
				uploadPath = // "/data5/SMILE_DATA5/COUPON"
				repositoryPath + File.separator + CouponConstants.Brand_Catalog_Path + File.separator
						+ DateUtil.getToday("yyyyMM") + File.separator + DateUtil.getToday("dd") + File.separator
						+ dpCatalogInfo.getCreateCatalogId();

				System.out.println("uploadPath : " + uploadPath);

			}

			File downloadDir = new File(uploadPath);
			// 디렉토리가 존재하지 않으면 만든다.
			if (!downloadDir.exists()) {
				downloadDir.mkdirs();
			}

			System.out.println("BrandImgPath(downloadPath) = " + uploadPath);

			for (int i = 0; i < fileList.size(); i++) {
				downloadUrl = fileList.get(i);

				if (!"".equals(downloadUrl)) {

					// 다운로드 파일명 추출
					orgDownloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf("/")).replace("/", "");
					System.out.println("downloadFileName : " + orgDownloadFileName);

					// 파일명.확장자 형식 체크
					int fileOk = orgDownloadFileName.indexOf(".");

					System.out.println("fileOk : " + fileOk);

					if (fileOk != -1) {

						System.out.println("File DownLoad Start!!");

						String downloadFilePath = uploadPath + File.separator + orgDownloadFileName;
						System.out.println("downloadFilePath : " + downloadFilePath);

						try {

							System.out.println("File URLConnection Start!!");

							URL url = new URL(downloadUrl);
							try {

								urlcon = url.openConnection();
								System.out.println("File URLConnection OK!!");

							} catch (Exception e) {

								System.out.println("다운로드 URL에 접속 1차 실패!!");

								try {

									urlcon = url.openConnection();
									System.out.println("File URLConnection OK!!");

								} catch (Exception e1) {

									System.out.println("다운로드 URL에 접속 2차 실패!!");
									throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR,
											"다운로드 URL에 접속 2차 실패!!", null);
								}
							}

							try {

								System.out.println("File DownLoad Start!!");

								copyFile = new File(downloadFilePath);

								if (copyFile.exists()) {
									renFileName = orgDownloadFileName.substring(0, fileOk) + DateUtil.getTime() + "("
											+ i + ")"
											+ orgDownloadFileName.substring(orgDownloadFileName.lastIndexOf("."));

									System.out.println("DateUtil.getTime()" + DateUtil.getTime());
									System.out.println("renFileName : " + renFileName);
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

									System.out.println("File DownLoad OK!!");

								} else {

									os = new BufferedOutputStream(new FileOutputStream(downloadFilePath));
									is = urlcon.getInputStream();
									int readFileValue;

									while ((readFileValue = is.read()) != -1) {
										os.write(readFileValue);
									}

									is.close();
									os.close();

									System.out.println("File DownLoad OK!!");
								}

								// Catalog 이미지가 두개라 처리.
								if (i == 0) {
									if (dpCatalogInfo == null) {
										dpBrandInfo.setBrandImgPath(downloadFilePath);
										System.out.println("dpBrandInfo.getBrandImgPath() : "
												+ dpBrandInfo.getBrandImgPath());
									}
									if (dpBrandInfo == null) {
										dpCatalogInfo.setTopImgPath(downloadFilePath);
										System.out.println("dpCatalogInfo.setTopImgPath() : "
												+ dpCatalogInfo.getTopImgPath());
									}

								}
								if (i == 1) {
									if (dpBrandInfo == null) {
										// downloadFilePath2 = uploadPath + File.separator + renFileName;
										dpCatalogInfo.setDtlImgPath(downloadFilePath);
										System.out.println("dpCatalogInfo.getDtlImgPath() : "
												+ dpCatalogInfo.getDtlImgPath());
									}

								}

							} catch (Exception e) {
								System.out.println("파일 다운로드 중 오류 발생!!");
								// this.message = "파일 다운로드 중 오류 발생!!";
								throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR,
										"파일 다운로드 중 오류 발생!!", null);

							}

						} catch (Exception e) {
							// this.log.error(e);
							throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR, this.message,
									null);

						}
					} else {
						System.out.println("filePath url에 파일정보가 없습니다!");
						this.message = "filePath url에 파일정보가 없습니다!";
						this.errorCode = CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR;
						return false;
					}
				}
			}
		} catch (Exception e) {
			// 정보변경 실패 알림 페이지
			this.log.error("브랜드,카탈로그 파일 정보 가져오기 실패", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_FILEACESS_ERR, this.message, null);
		}
		return true;

	}

	/**
	 * 카탈로그 정보를 추가한다.
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public boolean insertCatalogInfo(DpCatalogInfo dpCatalogInfo) {

		String TopImgPath = null;
		String DtlImgPath = null;
		String DecodeTopImgPath = null;
		String DecodeTopDtlImgPath = null;

		try {

			this.log.info("dpCatalogInfo.getCatalogId() = " + dpCatalogInfo.getCatalogId());

			TopImgPath = dpCatalogInfo.getTopImgPath();
			DtlImgPath = dpCatalogInfo.getDtlImgPath();

			DecodeTopImgPath = URLDecoder.decode(TopImgPath, "utf-8");
			DecodeTopDtlImgPath = URLDecoder.decode(DtlImgPath, "utf-8");

			dpCatalogInfo.setTopImgPath(DecodeTopImgPath);
			dpCatalogInfo.setDtlImgPath(DecodeTopDtlImgPath);

			// CUD_TYPE에 따라 INSERT/UPDATE
			if ("C".equalsIgnoreCase(dpCatalogInfo.getCudType())) {

				// 카탈로그 ID 생성
				String catalogID = this.brandCatalogService.searchCreateCatalogId();

				if (StringUtils.isBlank(catalogID)) {
					this.errorCode = CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC;
					this.message = "[COUPON_catalogId]를 생성하지 못했습니다.";
					return false;
				}

				dpCatalogInfo.setCreateCatalogId(catalogID);
				String brandId = this.getCreateBrandId(dpCatalogInfo.getBrandId());
				if (StringUtils.isBlank(brandId)) {
					this.errorCode = CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC;
					this.message = "[COUPON_brandId]를 가져오지 못했습니다.";
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
			throw new CouponException(e.getErrCode(), e.getMessage(), null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
		}

		return true;
	}

	/**
	 * 브랜드 이미지파일 리사이즈 처리
	 * 
	 * @return
	 */
	public boolean brandImgResize(DpBrandInfo dpBrandInfo) {
		// 파일명 끝에 추가할 명칭
		String[] DERIVED_FILE_NAME_FOR_DERIVED = { "_260x170", "_177x177", "_114x114", "_29x29", "_56x56", "_27x27",
				"_25x25" };

		// TBL_DP_PROD_IMG.IMG_CLS 설정
		String[] IMG_CLS_CODE = { CouponConstants.BRAND_IMG_260x170, CouponConstants.BRAND_IMG_177x177,
				CouponConstants.BRAND_IMG_114x114, CouponConstants.BRAND_IMG_29x29, CouponConstants.BRAND_IMG_56x56,
				CouponConstants.BRAND_IMG_27x27, CouponConstants.BRAND_IMG_25x25 };

		// 파일 생성 크기
		int[][] IMAGE_SIZE_FOR_DERIVED = { { 260, 170 }, { 177, 177 }, { 114, 114 }, { 29, 29 }, { 56, 56 },
				{ 27, 27 }, { 25, 25 } };
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
			for (int i = 0; i < DERIVED_FILE_NAME_FOR_DERIVED.length; i++) {

				targetFileName = tmpFileName + DERIVED_FILE_NAME_FOR_DERIVED[i] + "." + fileExt; // fileExt 대신 타입으로 확장자
																								 // PNG

				int width = IMAGE_SIZE_FOR_DERIVED[i][0];
				int height = IMAGE_SIZE_FOR_DERIVED[i][1];

				// 이미지 리사이즈 처리
				File srcFile = new File(uploadDir + srcFileName);
				File destFile = new File(uploadDir + targetFileName);
				String out_file = uploadDir + targetFileName;

				// 이미지 리사이즈
				if (!ImageUtil.setImgScale(srcFile, out_file, width, height, uploadDir)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ", null);
				}
				// System.out.println("■■■■■BrandImgResize■■■■■ : " + targetFileName + "을 생성 하였습니다.");

				this.brandCatalogProdImgInfo.setProdId(dpBrandInfo.getCreateBrandId());
				this.brandCatalogProdImgInfo.setImgCls(IMG_CLS_CODE[i]);
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
	 * 카탈로그 이미지파일 리사이즈 처리
	 * 
	 * @return
	 */
	public boolean catalogImgResize(DpCatalogInfo dpCatalogInfo) {

		// 파일명 끝에 추가할 명칭
		String[] DERIVED_FILE_NAME_FOR_DERIVED_TOP = { "_60x60", "_120x120", "_130x130", "_40x40", "_80x80",
				"_110x110", "_180x180", "_182x182", "_31x30" };
		String[] DERIVED_FILE_NAME_FOR_DERIVED_DTL = { "_684x" };

		// TBL_DP_PROD_IMG.IMG_CLS 설정
		String[] IMG_CLS_CODE_TOP = { CouponConstants.CATALOG_TOP_IMG_60x60, CouponConstants.CATALOG_TOP_IMG_120x120,
				CouponConstants.CATALOG_TOP_IMG_130x130, CouponConstants.CATALOG_TOP_IMG_40x40,
				CouponConstants.CATALOG_TOP_IMG_80x80, CouponConstants.CATALOG_TOP_IMG_110x110,
				CouponConstants.CATALOG_TOP_IMG_180x180, CouponConstants.CATALOG_TOP_IMG_182x182,
				CouponConstants.CATALOG_TOP_IMG_31x30 };

		String[] IMG_CLS_CODE_DTL = { CouponConstants.CATALOG_DTL_IMG_684x };

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
			int[][] IMAGE_SIZE_FOR_DERIVED_TOP = { { 60, 60 }, { 120, 120 }, { 130, 130 }, { 40, 40 }, { 80, 80 },
					{ 110, 110 }, { 180, 180 }, { 182, 182 }, { 31, 30 } };
			int[][] IMAGE_SIZE_FOR_DERIVED_DTL = { { 684, nHeightSize } };

			// 카탈로그 대표이미지 리사이즈
			for (int i = 0; i < DERIVED_FILE_NAME_FOR_DERIVED_TOP.length; i++) {

				targetFileName = tmpFileName + DERIVED_FILE_NAME_FOR_DERIVED_TOP[i] + "." + fileExt;

				int width = IMAGE_SIZE_FOR_DERIVED_TOP[i][0];
				int height = IMAGE_SIZE_FOR_DERIVED_TOP[i][1];
				int seq = 1;

				// 이미지 리사이즈 처리
				File srcFile = new File(uploadDir + srcFileName);
				File destFile = new File(uploadDir + targetFileName);
				String out_file = uploadDir + targetFileName;

				// 이미지 리사이즈
				ImageUtil.setImgScale(srcFile, out_file, width, height, uploadDir);
				// 이미지 리사이즈
				if (!ImageUtil.setImgScale(srcFile, out_file, width, height, uploadDir)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ", null);
				}
				this.log.info("■■■■■BrandImgResize■■■■■ : " + targetFileName + "을 생성 하였습니다.");

				this.brandCatalogProdImgInfo.setProdId(dpCatalogInfo.getCreateCatalogId());
				this.brandCatalogProdImgInfo.setImgCls(IMG_CLS_CODE_TOP[i]);
				this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
				this.brandCatalogProdImgInfo.setFilePos(uploadDir);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName);
				this.brandCatalogProdImgInfo.setSeq(seq);

				// TBL_DP_PROD_IMG 테이블에 INSERT/UPDATE
				this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);
			}

			// 카탈로그 상세이미지 리사이즈
			for (int i = 0; i < DERIVED_FILE_NAME_FOR_DERIVED_DTL.length; i++) {

				targetFileName1 = tmpFileName1 + DERIVED_FILE_NAME_FOR_DERIVED_DTL[i] + "." + fileExt1;

				int width = IMAGE_SIZE_FOR_DERIVED_DTL[i][0];
				int height = IMAGE_SIZE_FOR_DERIVED_DTL[i][1];
				int seq = 1;

				// 이미지 리사이즈 처리
				File srcFile1 = new File(uploadDir1 + srcFileName1);
				String cutOut_file = uploadDir1 + targetFileName1;

				// 이미지 리사이즈
				if (!ImageUtil.setImgScale(srcFile1, cutOut_file, width, height, uploadDir)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ", null);
				}
				ImageUtil.setImgScale(srcFile1, cutOut_file, width, height, uploadDir1);

				this.log.info("■■■■■BrandImgResize■■■■■ : " + targetFileName1 + "을 생성 하였습니다.");

				this.brandCatalogProdImgInfo.setProdId(dpCatalogInfo.getCreateCatalogId());
				this.brandCatalogProdImgInfo.setImgCls(CouponConstants.CATALOG_DTL_IMG_684xY);
				this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
				this.brandCatalogProdImgInfo.setFilePos(uploadDir1);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName1);
				this.brandCatalogProdImgInfo.setSeq(seq);

				// 카탈로그이미지 2013.06.24 이미지 추가684xY
				this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);

				this.brandCatalogProdImgInfo.setImgCls(IMG_CLS_CODE_DTL[i]);

				// 이미지 세로길이가 1170보다 클경우
				if (nHeightSize > 1170) {
					this.log.info("■■■■■이미지 세로길이가 1170보다 클경우■■■■■");
					int cY = 0; // 이미지 컷처리 시작지

					nHeightSize = 1170;

					// 이미지를 1170사이즈까지 잘라서 저장
					for (seq = 1; nHeightSize < height; seq++) {

						String ResizetargetFileName1 = tmpFileName1 + "_684xy" + seq + "." + fileExt1;
						// 이미지 리사이즈
						if (!ImageUtil.setImgScale(srcFile1, cutOut_file, width, height, uploadDir)) {
							throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류 ",
									null);
						}
						ImageUtil.cutImage2(uploadDir1 + targetFileName1, uploadDir1 + ResizetargetFileName1, 0, cY,
								width, 1170);
						this.brandCatalogProdImgInfo.setFileNm(ResizetargetFileName1);
						// log.info("■■■■■BrandImgResize■■■■■ : " + ResizetargetFileName1 + "을 생성 하였습니다.");

						cY = nHeightSize;
						nHeightSize = nHeightSize + 1170;

						this.brandCatalogProdImgInfo.setSeq(seq);
						this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
						this.brandCatalogProdImgInfo.setFileNm(ResizetargetFileName1);

						// TBL_DP_PROD_IMG 테이블에 INSERT/UPDATE
						this.brandCatalogService.insertTblDpProdImg(this.brandCatalogProdImgInfo);

						// 이미지 마지막 1170보다 작은 이미지 생성및 DB처리
						if (height <= nHeightSize) {
							this.log.info("■■■■■이미지 마지막 1170보다 작은 이미지 생성■■■■■");
							seq = seq + 1;

							ResizetargetFileName1 = tmpFileName1 + "_684xy" + seq + "." + fileExt1;
							this.log.info("ResizetargetFileName1" + ResizetargetFileName1);

							ImageUtil.cutImage2(uploadDir1 + targetFileName1, uploadDir1 + ResizetargetFileName1, 0,
									cY, width, height - cY);
							this.brandCatalogProdImgInfo.setFileNm(ResizetargetFileName1);
							// log.info("■■■■■BrandImgResize■■■■■ : " + ResizetargetFileName1 + "을 생성 하였습니다.");

							this.brandCatalogProdImgInfo.setSeq(seq);
							this.brandCatalogProdImgInfo.setLangCd(CouponConstants.LANG_CD_KO);
							this.brandCatalogProdImgInfo.setFileNm(ResizetargetFileName1);

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
	 * TBL_TAG_INFO 정보를 설정
	 * 
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
	 * @return
	 */
	public String getCreateBrandId(String brandId) {
		this.log.info("<<<BrandCatalogService>>> getBrandId...");
		return this.brandCatalogService.getCreateBrandId(brandId);
	}

	/**
	 * 해당 쿼리에 해당하는 CATALOG_ID를 리턴한다.
	 * 
	 * @return
	 */
	public String getCreateCatalogId(String catalogId) {
		this.log.info("<<<BrandCatalogService>>> getCreateCatalogId...");
		return this.brandCatalogService.getCreateCatalogId(catalogId);
	}

}
