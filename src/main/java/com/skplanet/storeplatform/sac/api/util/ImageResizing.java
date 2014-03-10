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
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ImageResizing 이미지 리사이즈 기능을 제공한다.
 */
public class ImageResizing {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * ImageResizing 이미지 리사이즈 기능을 제공한다.
	 * 
	 * @param soruce
	 *            soruce
	 * @param width
	 *            width
	 * @param height
	 *            height
	 * @return Image
	 * @throws Exception
	 *             Exception
	 */
	public static Image resizing(Image soruce, int width, int height)

	throws Exception {

		int newW = width;

		int newH = height;

		return soruce.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);

	}

	/**
	 * ImageResizing 이미지 리사이즈 기능을 제공한다.
	 * 
	 * @param imgResdFile
	 *            imgResdFile
	 * @param imgWriteFile
	 *            imgWriteFile
	 * @param targetWidth
	 *            targetWidth
	 * @param targetHeight
	 *            targetHeight
	 * @return boolean
	 */
	public boolean imageResizing(String imgResdFile, String imgWriteFile, int targetWidth, int targetHeight) {

		boolean result = false;

		try {
			this.log.info("log9991");
			// img = ImageIO.read( new File(fName));

			FileInputStream fis = new FileInputStream(imgResdFile);

			byte[] data = new byte[fis.available()];
			this.log.info("log9992");
			// this.log.info(fis.available());

			fis.read(data);

			fis.close();

			Image image = Toolkit.getDefaultToolkit().createImage(data);
			this.log.info("log9993");
			Image rtnImage = resizing(image, targetWidth, targetHeight);
			this.log.info("log9994");
			MediaTracker tracker = new MediaTracker(new java.awt.Frame());
			this.log.info("log9995");
			tracker.addImage(rtnImage, 0);

			tracker.waitForAll();
			this.log.info("log9996");
			BufferedImage bi = new BufferedImage(rtnImage.getWidth(null),

			rtnImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
			this.log.info("log9997");
			Graphics g = bi.getGraphics();

			g.drawImage(rtnImage, 0, 0, null);

			g.dispose();

			ByteArrayOutputStream bas = new ByteArrayOutputStream();
			this.log.info("log9998");
			ImageIO.write(bi, "jpeg", bas);

			byte[] writeData = bas.toByteArray();

			DataOutputStream dos = new DataOutputStream(

			new BufferedOutputStream(new FileOutputStream(imgWriteFile)));

			dos.write(writeData);
			this.log.info("log9999");
			dos.close();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
