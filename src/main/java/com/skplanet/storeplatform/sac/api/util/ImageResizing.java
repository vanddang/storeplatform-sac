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

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ImageResizing 이미지 리사이즈 기능을 제공한다.
 */
public class ImageResizing {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public static final int RATIO = 0;
	public static final int SAME = -1;

	/**
	 * ImageResizing 이미지 리사이즈 기능을 제공한다.
	 * 
	 * @param src
	 *            src
	 * @param dest
	 *            dest
	 * @param width
	 *            width
	 * @param height
	 *            height
	 * @return boolean
	 */
	public boolean resize(File src, File dest, int width, int height) {
		try {
			Image srcImg = null;
			String suffix = src.getName().substring(src.getName().lastIndexOf('.') + 1).toLowerCase();
			this.log.info("log8881");
			if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
				srcImg = ImageIO.read(src);
			} else {
				// BMP가 아닌 경우 ImageIcon을 활용해서 Image 생성
				// 이렇게 하는 이유는 getScaledInstance를 통해 구한 이미지를
				// PixelGrabber.grabPixels로 리사이즈 할때
				// 빠르게 처리하기 위함이다.
				srcImg = new ImageIcon(src.toURL()).getImage();
			}
			this.log.info("log8882");

			int srcWidth = srcImg.getWidth(null);
			int srcHeight = srcImg.getHeight(null);

			int destWidth = -1, destHeight = -1;
			this.log.info("log8883");
			if (width == SAME) {
				destWidth = srcWidth;
			} else if (width > 0) {
				destWidth = width;
			}

			if (height == SAME) {
				destHeight = srcHeight;
			} else if (height > 0) {
				destHeight = height;
			}
			this.log.info("log8884");
			if (width == RATIO && height == RATIO) {
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (width == RATIO) {
				double ratio = ((double) destHeight) / ((double) srcHeight);
				destWidth = (int) (srcWidth * ratio);
			} else if (height == RATIO) {
				double ratio = ((double) destWidth) / ((double) srcWidth);
				destHeight = (int) (srcHeight * ratio);
			}
			this.log.info("log8885");
			Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH);
			int pixels[] = new int[destWidth * destHeight];
			PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth, destHeight, pixels, 0, destWidth);
			this.log.info("log8886");
			pg.grabPixels();
			this.log.info("log8887");
			BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);
			this.log.info("log8888");
			ImageIO.write(destImg, "jpg", dest);
			this.log.info("log8889");
			return true;
		} catch (Exception e) {
			this.log.info(e.getMessage());
			return false;
		}
	}

}
