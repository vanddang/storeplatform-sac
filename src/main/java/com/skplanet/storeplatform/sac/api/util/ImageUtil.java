/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 쇼핑상품 이미지 유틸
 * 
 * Updated on : 2013. 12. 31. Updated by : 김형식, SK 플래닛.
 */
public class ImageUtil {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 이미지의 크기를 조절한다.
	 * 
	 * @param inImgfle
	 *            소스파일
	 * @param outFile
	 *            변경 후 이미지 파일명 (경로포함)
	 * @param targetWidth
	 *            targetWidth
	 * @param targetHeight
	 *            targetHeight
	 * @param directoryName
	 *            변경 후 이미지 저장 디렉토리 명
	 * @return boolean
	 */
	public boolean setImgScale(File inImgfle, String outFile, int targetWidth, int targetHeight, String directoryName) {
		try {
			if (!inImgfle.isFile()) {
				return false;
			}
			File outputfile = new File(outFile);
			if (outputfile.exists() == true) {
				this.log.info("변경하려는 파일과 동일한 파일이 존재하여 삭제합나디. 파일명 [" + outputfile.getName() + "]");
				outputfile.delete();
			}

			if (directoryName != null) {
				if (!FileUtil.directoryCheck(directoryName))
					FileUtil.createDirectory(directoryName);
			} else {
				if (!FileUtil.directoryCheck(outFile))
					FileUtil.createDirectory(outFile);
			}
			ImageResizing imgResize = new ImageResizing();

			boolean isOk = false;
			isOk = imgResize.resize(inImgfle, outputfile, targetWidth, targetHeight);

			this.log.info("isOk::::" + isOk);

			if (!isOk) {
				return false;
			}

			File file = new File(outFile); // 파일의 이름을 설정한다
			long L = file.length();
			this.log.info("■■■■■이미지 파일 정보 ■■■■■ :" + file.getAbsoluteFile());
			this.log.info(L + " bytes ■■■■■");
			if (L == 0) {
				return false;
			}
		} catch (Exception e) {
			this.log.info(e.getMessage());
			e.getStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * cutImage2.
	 * 
	 * @param srcFile
	 *            srcFile
	 * @param destFile
	 *            destFile
	 * @param cX
	 *            cX
	 * @param cY
	 *            cY
	 * @param dW
	 *            dW
	 * @param dH
	 *            dH
	 * @return boolean
	 */
	public boolean cutImage2(String srcFile, String destFile, int cX, int cY, int dW, int dH) {

		ImageIcon img = new ImageIcon(srcFile);
		log.info("============srcFile============"+ srcFile);
		try {
			log.info("============width============"+ img.getIconWidth());
			log.info("============height============"+ img.getIconHeight());
			BufferedImage bufImage = this.imageToBufferedImage(img.getImage(), img.getIconWidth(), img.getIconHeight());
			BufferedImage bufImage2 = bufImage.getSubimage(cX, cY, dW, dH);
			File file = new File(destFile); // 파일의 이름을 설정한다
			FileOutputStream fos = new FileOutputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(bufImage2, "jpg", output);
			fos.write(output.toByteArray());
			fos.close();
			long L = file.length();
			this.log.info("■■■■■이미지 상세 파일 정보 ■■■■■ :" + file.getAbsoluteFile());
			this.log.info(L + " bytes ■■■■■");
			if (L == 0) {
				return false;
			}

		} catch (Exception e) {
			this.log.info("쇼핑 상품 Cutting 이미지 생성 실패(cutImage2) : " + e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * imageToBufferedImage.
	 * 
	 * @param im
	 *            im
	 * @param width
	 *            width
	 * @param height
	 *            height
	 * 
	 * @return BufferedImage
	 */
	private BufferedImage imageToBufferedImage(Image im, int width, int height) {
		BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g2d = copy.createGraphics();
		g2d.drawImage(im, 0, 0, null);
		g2d.dispose();
		return copy;
	}

}
