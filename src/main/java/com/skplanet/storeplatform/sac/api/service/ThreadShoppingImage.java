/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.service;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.ImageUtil;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;

public class ThreadShoppingImage implements Runnable {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public final BrandCatalogService brandCatalogService;

	public final BrandCatalogProdImgInfo brandCatalogProdImgInfo;

	public final DpCatalogInfo dpCatalogInfo;

	private boolean stopped = false;

	public ThreadShoppingImage(DpCatalogInfo dpCatalogInfo, BrandCatalogService brandCatalogService) {
		this.brandCatalogProdImgInfo = new BrandCatalogProdImgInfo();
		this.dpCatalogInfo = dpCatalogInfo;
		this.brandCatalogService = brandCatalogService;
	}

	public void run() {
		while (!stopped) {
			this.log.info("################ [Thread is alive] ################");
			this.log.info("################ [SAC API Shopping Image] Start : "
					+ DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
			// Thread 시작하는 부분
			ImageUtil imgUtil = new ImageUtil();
			// 파일명 끝에 추가할 명칭
			String[] drivedFileNameForDrivedTop = { "_182x182" };
			String[] drivedFileNameForDrivedDtl = { "_684x" };

			// TBL_DP_PROD_IMG.IMG_CLS 설정
			String[] imgClsCodeTop = { CouponConstants.CATALOG_TOP_IMG_182_182 };

			String[] imgClsCodeDtl = { CouponConstants.CATALOG_DTL_IMG_684_X };

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

				uploadDir = brandImgPath.substring(0, brandImgPath.lastIndexOf(File.separator) + 1); // 이미지 경로
				srcFileName = brandImgPath.substring(brandImgPath.lastIndexOf(File.separator)).replace(File.separator,
						""); // 이미지NAME
				tmpFileName = srcFileName.substring(0, srcFileName.lastIndexOf(".")); // 파일명
				fileExt = srcFileName.substring(srcFileName.lastIndexOf(".") + 1); // 확장자
				// 카탈로그 상세이미지 변수
				brandImgPath1 = dpCatalogInfo.getDtlImgPath(); // 이미지경로 + 이미지NAME
				uploadDir1 = brandImgPath1.substring(0, brandImgPath1.lastIndexOf(File.separator) + 1); // 이미지 경로
				srcFileName1 = brandImgPath1.substring(brandImgPath1.lastIndexOf(File.separator)).replace(
						File.separator, ""); // 이미지NAME
				tmpFileName1 = srcFileName1.substring(0, srcFileName1.lastIndexOf(".")); // 파일명
				fileExt1 = srcFileName1.substring(srcFileName1.lastIndexOf(".") + 1); // 확장자

				Image img = new ImageIcon(uploadDir1 + srcFileName1).getImage(); // 이미지 사이즈를 구하기 위해 이미지를 가져옴.

				int oWidth = img.getWidth(null); // 이미지 가로사이즈
				int oHeight = img.getHeight(null); // 이미지 세로사이즈

				int nHeightSize = oHeight * 684 / oWidth;

				// 파일 생성 크기
				int[][] imageSizeForDrivedDtl = { { 684, nHeightSize } };

				// 카탈로그 대표이미지 리사이즈
				for (int i = 0; i < drivedFileNameForDrivedTop.length; i++) {

					targetFileName = tmpFileName + "." + fileExt;
					int seq = 1;

					// 이미지 리사이즈 처리
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
					if (!imgUtil.setImgScale(srcFile1, cutOutFile, width, height, uploadDir)) {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "이미지 생성 오류!", null);
					}

					this.log.info("■■■■■BrandImgResize■■■■■ : " + targetFileName1 + "을 생성 하였습니다.");

					File outputfile = new File(cutOutFile);
					long fileSize = outputfile.length();

					this.brandCatalogProdImgInfo.setProdId(dpCatalogInfo.getCreateCatalogId());
					this.brandCatalogProdImgInfo.setImgCls(CouponConstants.CATALOG_DTL_IMG_684_XY);
					this.brandCatalogProdImgInfo.setFileSize(fileSize);
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

							if (!imgUtil.cutImage2(uploadDir1 + targetFileName1, uploadDir1 + resizetargetFileName1, 0,
									cY, width, 1170)) {
								throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR,
										"이미지 생성 오류 ", null);
							}
							this.brandCatalogProdImgInfo.setFileNm(resizetargetFileName1);
							this.log.info("■■■■■BrandCatalogImgResize■■■■■ : " + resizetargetFileName1 + "을 생성 하였습니다.");

							cY = nHeightSize;
							nHeightSize = nHeightSize + 1170;
							File cutOutputfile = new File(uploadDir1 + resizetargetFileName1);
							long cutFileSize = cutOutputfile.length();

							this.brandCatalogProdImgInfo.setFileSize(cutFileSize);
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
								this.log.info("■■■■■resizetargetFileName1■■■■■ :" + resizetargetFileName1
										+ "을 생성 하였습니다.");

								if (!imgUtil.cutImage2(uploadDir1 + targetFileName1,
										uploadDir1 + resizetargetFileName1, 0, cY, width, height - cY)) {
									throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR,
											"이미지 생성 오류!!", null);
								}
								File lastCutOutputfile = new File(uploadDir1 + resizetargetFileName1);
								long lastCutFileSize = lastCutOutputfile.length();

								this.brandCatalogProdImgInfo.setFileSize(lastCutFileSize);
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
				this.log.info("################ [SAC API Shopping Image] End : "
						+ DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
			} catch (CouponException e) {
				// resize 오류 Catch 및 CouponException 처리 2013.08.07 임선택
				this.log.info("■■■■■이미지 생성 오류■■■■■ ");
				this.log.error("<brandImgResize> brandResize : ", e);
				// e.printStackTrace();
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_IMGCRE_ERR, "카탈로그 이미지 Resize 실패", null);
			}
		}
		this.log.info("################ [Thread is dead] ################");
		// //////////////////////Thread끝나는 부분/////////////////////////////////////////////////////////////
	}

	public void stop() {
		stopped = true;
	}
}
