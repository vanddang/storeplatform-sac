package com.skplanet.storeplatform.sac.api.service;

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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.dao.BrandCatalogDao;
import com.skplanet.storeplatform.sac.api.dao.BrandCatalogDaoImpl;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.ImageUtil;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;

public class BrandCatalogService {

	private final static Logger log = Logger.getLogger(BrandCatalogService.class);

	private String errorCode = "";
	private String message = "";
	BrandCatalogProdImgInfo brandCatalogProdImgInfo = new BrandCatalogProdImgInfo();
	private final ArrayList<DpCatalogTagInfo> tagList = new ArrayList<DpCatalogTagInfo>(); // TBL_DP_TAG_INFO 정보
	CouponConstants couponcontants = new CouponConstants();
	private BrandCatalogDao dao = null;

	public BrandCatalogService() {
		this.dao = new BrandCatalogDaoImpl();
	}

	/**
	 * 브랜드 정보를 추가한다.
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 * @throws UnsupportedEncodingException
	 */
	public boolean insertBrandInfo(DpBrandInfo dpBrandInfo) throws CouponException, UnsupportedEncodingException {

		String BrandImgPath = null;
		String DecodeBrandImgPath = null;

		try {

			// System.out.println("dpBrandInfo.getBrandId() = " + dpBrandInfo.getBrandId());
			// System.out.println("+++++++++++++++++++++++++LO555555++++++++++++++++++++" +

			BrandImgPath = dpBrandInfo.getBrandImgPath();
			System.out.println("+++++++++++++++++++++++++LO555555123123++++++++++++++++++++" + BrandImgPath);
			DecodeBrandImgPath = URLDecoder.decode(BrandImgPath, "utf-8");
			System.out.println("+++++++++++++++++++++++++LO55555533333++++++++++++++++++++" + BrandImgPath);
			dpBrandInfo.setBrandImgPath(DecodeBrandImgPath);
			System.out.println("+++++++++++++++++++++++++LO555555555544++++++++++++++++++++"
					+ dpBrandInfo.getBrandImgPath());

			// 트랜잭션 시작
			// this.daoMgr.startTransaction();

			// CUD_TYPE에 따라 INSERT/UPDATE
			if ("C".equalsIgnoreCase(dpBrandInfo.getCudType())) {
				// System.out.println("***** BrandCatalogService.insertBrandInfo *****");
				System.out.println("+++++++++++++++++++++++++LO66666++++++++++++++++++++");
				// 브랜드ID 생성
				// IDGeneratorService idGen = new IDGeneratorService();
				// String brandId = (String) this.commonDAO.queryForObject("", String.class);
				String brandId = "BR00009999";
				// String brandID = idGen.generateId("TBL_DP_TSP_BRAND_INFO.BRAND_ID");

				if (StringUtils.isBlank(brandId)) {
					this.errorCode = CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC;
					this.message = "[COUPON_CONTENT_ID]를 생성하지 못했습니다.";
					return false;
				}

				dpBrandInfo.setCreateBrandId(brandId);
				System.out.println("dpBrandInfo.getCreateBrandId() = " + dpBrandInfo.getCreateBrandId());
				System.out.println("+++++++++++++++++++++++++LO77777++++++++++++++++++++");
				// 신규등록일시 TBL_DP_TSP_BRAND_INFO테이블 INSERT
				this.dao.insertBrandInfo(dpBrandInfo);
				System.out.println("+++++++++++++++++++++++++LO8888++++++++++++++++++++");
			} else if ("U".equalsIgnoreCase(dpBrandInfo.getCudType())) {
				System.out.println("***** BrandCatalogService.updateBrandInfo *****");

				// String brandID = this.getCreateBrandId(dpBrandInfo.getBrandId());
				// dpBrandInfo.setCreateBrandId(brandID);

				// // 수정일시 TBL_DP_TSP_BRAND_INFO테이블 UPDATE
				// this.dao.updateBrandInfo(dpBrandInfo);
				//
				// // 수정일시 TBL_DP_PROD_IMG 테이블 삭제
				// this.dao.deleteDpProdImg(brandID);
			}

			// 이미지 가져옴
			// System.out.println("이미지 처리 시작");
			this.inputBrandFile(dpBrandInfo, null);

			// 이미지 리사이즈 처리
			this.brandImgResize(dpBrandInfo);
			System.out.println("+++++++++++++++++++++++++LOG88888++++++++++++++++++++");
			// 트랜잭션 끝
			// this.daoMgr.commitTransaction();

		} catch (CouponException e) {
			throw new CouponException(e.getErrCode(), e.getMessage(), null);
		} finally {
			// this.daoMgr.endTransaction();
		}

		return true;
	}

	/**
	 * 브랜드,카탈로그 파일 이미지 처리
	 * 
	 * @return
	 */
	public boolean inputBrandFile(DpBrandInfo dpBrandInfo, DpCatalogInfo dpCatalogInfo) throws CouponException {

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

			// repositoryPath = getLabelForQuery(new String[] { this.couponcontants.PCD_FOR_REPOSITORY_PATH,
			// this.couponcontants.TYPE_FOR_REPOSITORY_PATH });

			repositoryPath = "/data1/SMILE_DATA";
			// System.out.println("repositoryPath : " + repositoryPath);

			// 브랜드 이미지 처리
			if (dpCatalogInfo == null) {

				System.out.println("filepath : " + dpBrandInfo.getBrandImgPath());

				// 다운로드할 파일 리스트
				fileList.add(dpBrandInfo.getBrandImgPath());

				// 파일저장 경로
				uploadPath = // "/data5/SMILE_DATA5/COUPON"
				repositoryPath + File.separator + this.couponcontants.Brand_Catalog_Path + File.separator
						+ DateUtil.getToday("yyyyMM") + File.separator + DateUtil.getToday("dd") + File.separator
						+ dpBrandInfo.getCreateBrandId();

				System.out.println("uploadPath : " + uploadPath);
				// dpBrandInfo.setBrandImgPath((String)uploadPath);
			}

			// 카탈로그 이미지 처리
			if (dpBrandInfo == null) {

				// 다운로드할 파일 리스트
				fileList.add(dpCatalogInfo.getTopImgPath());
				fileList.add(dpCatalogInfo.getDtlImgPath());

				// 파일저장 경로
				uploadPath = // "/data5/SMILE_DATA5/COUPON"
				repositoryPath + File.separator + this.couponcontants.Brand_Catalog_Path + File.separator
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
									throw new CouponException(this.couponcontants.COUPON_IF_ERROR_CODE_FILEACESS_ERR,
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
								throw new CouponException(this.couponcontants.COUPON_IF_ERROR_CODE_FILEACESS_ERR,
										"파일 다운로드 중 오류 발생!!", null);

							}

							// if(copyFile.exists()) {
							// renFileName = orgDownloadFileName.substring(1,fileOk) + "(" + i + ")";
							//
							// System.out.println("renFileName : " + renFileName);
							// destFile = new File(uploadPath, renFileName);
							// FileUtils.copyFile(copyFile, destFile);
							//
							// }

							// System.out.println("copyFile : " + copyFile);

						} catch (Exception e) {
							log.error(e);
							throw new CouponException(this.couponcontants.COUPON_IF_ERROR_CODE_FILEACESS_ERR,
									this.message, null);

						}
					} else {
						System.out.println("filePath url에 파일정보가 없습니다!");
						// this.message = "filePath url에 파일정보가 없습니다!";
						// this.errorCode = this.couponcontants.COUPON_IF_ERROR_CODE_FILEACESS_ERR;
						return false;
					}
				}
			}
		} catch (Exception e) {
			// 정보변경 실패 알림 페이지
			log.error("브랜드,카탈로그 파일 정보 가져오기 실패", e);
			throw new CouponException(this.couponcontants.COUPON_IF_ERROR_CODE_FILEACESS_ERR, this.message, null);
		}
		return true;

	}

	/**
	 * 브랜드 이미지파일 리사이즈 처리
	 * 
	 * @return
	 */
	public boolean brandImgResize(DpBrandInfo dpBrandInfo) throws CouponException {
		System.out.println("+++++++++++++++++++++++++LOG88888++++++++++++++++++++");
		// 파일명 끝에 추가할 명칭
		String[] DERIVED_FILE_NAME_FOR_DERIVED = { "_260x170", "_177x177", "_114x114", "_29x29", "_56x56", "_27x27",
				"_25x25" };

		// TBL_DP_PROD_IMG.IMG_CLS 설정
		String[] IMG_CLS_CODE = { this.couponcontants.BRAND_IMG_260x170, this.couponcontants.BRAND_IMG_177x177,
				this.couponcontants.BRAND_IMG_114x114, this.couponcontants.BRAND_IMG_29x29,
				this.couponcontants.BRAND_IMG_56x56, this.couponcontants.BRAND_IMG_27x27,
				this.couponcontants.BRAND_IMG_25x25 };

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
				this.brandCatalogProdImgInfo.setFilePos(uploadDir);
				this.brandCatalogProdImgInfo.setFileNm(targetFileName);
				this.brandCatalogProdImgInfo.setSeq(seq);
				// TBL_DP_PROD_IMG 테이블에 INSERT/UPDATE
				// dao.insertTblDpProdImg(brandCatalogProdImgInfo);
			}

		} catch (CouponException e) {
			log.error("<brandImgResize> brandResize : ", e);
			throw new CouponException(this.couponcontants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "브랜드 이미지 Resize 실패", null);

		}
		return true;
	}

}
