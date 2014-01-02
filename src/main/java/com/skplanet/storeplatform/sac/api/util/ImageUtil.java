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
import org.apache.log4j.Logger;

/**
 * 쇼핑상품 이미지 유틸
 * 
 * Updated on : 2013. 12. 31. Updated by : 김형식, SK 플래닛.
 */
public class ImageUtil {

	private final static Logger log = Logger.getLogger(ImageUtil.class);;

	/**
	 * 이미지의 크기를 조절한다.
	 * 
	 * @param inImgfle
	 *            소스파일
	 * @param out_file
	 *            변경 후 이미지 파일명 (경로포함)
	 * @param targetWidth
	 * @param targetHeight
	 * @param directoryName
	 *            변경 후 이미지 저장 디렉토리 명
	 * @return
	 */
	public static boolean setImgScale(File inImgfle, String out_file, int targetWidth, int targetHeight,
			String directoryName) {
		try {
			if (inImgfle.isFile()) {
				// System.out.println("소스 파일이 존재한다.");
			} else {
				log.info("소스 파일이 존재하지 않는다.");
				return false;
			}

			File outputfile = new File(out_file);
			if (outputfile.exists() == true) {
				log.info("변경하려는 파일과 동일한 파일이 존재하여 삭제합나디. 파일명 [" + outputfile.getName() + "]");
				outputfile.delete();
			}

			if (directoryName != null) {
				if (!FileUtil.directoryCheck(directoryName))
					FileUtil.createDirectory(directoryName);
			} else {
				if (!FileUtil.directoryCheck(out_file))
					FileUtil.createDirectory(out_file);
			}
			ImageResizing imgResize = new ImageResizing();
			boolean isOk = false;
			isOk = imgResize.ImageResizing(inImgfle.getPath(), out_file, targetWidth, targetHeight);
			// System.out.println("isOk::::"+isOk);

			if (!isOk) {
				return false;
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {

		}

		return true;
	}

	public static String cutImage2(String srcFile, String destFile, int cX, int cY, int dW, int dH) {

		ImageIcon img = new ImageIcon(srcFile);

		BufferedImage bufImage = imageToBufferedImage(img.getImage(), img.getIconWidth(), img.getIconHeight());

		BufferedImage bufImage2 = bufImage.getSubimage(cX, cY, dW, dH);

		try {
			File file = new File(destFile); // 파일의 이름을 설정한다
			FileOutputStream fos = new FileOutputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			ImageIO.write(bufImage2, "jpg", output);
			fos.write(output.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return destFile;
	}

	private static BufferedImage imageToBufferedImage(Image im, int width, int height) {
		BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g2d = copy.createGraphics();
		g2d.drawImage(im, 0, 0, null);
		g2d.dispose();
		return copy;
	}

}
