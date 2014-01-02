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

public class ImageResizing {

	public static Image resizing(Image soruce, int width, int height)

	throws Exception {

		int newW = width;

		int newH = height;

		return soruce.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);

	}

	public boolean ImageResizing(String imgResdFile, String imgWriteFile, int targetWidth, int targetHeight) {

		boolean result = false;

		try {

			// img = ImageIO.read( new File(fName));

			FileInputStream fis = new FileInputStream(imgResdFile);

			byte[] data = new byte[fis.available()];

			// System.out.println(fis.available());

			fis.read(data);

			fis.close();

			Image image = Toolkit.getDefaultToolkit().createImage(data);

			Image rtnImage = resizing(image, targetWidth, targetHeight);

			MediaTracker tracker = new MediaTracker(new java.awt.Frame());

			tracker.addImage(rtnImage, 0);

			tracker.waitForAll();

			BufferedImage bi = new BufferedImage(rtnImage.getWidth(null),

			rtnImage.getHeight(null), BufferedImage.TYPE_INT_RGB);

			Graphics g = bi.getGraphics();

			g.drawImage(rtnImage, 0, 0, null);

			g.dispose();

			ByteArrayOutputStream bas = new ByteArrayOutputStream();

			ImageIO.write(bi, "jpeg", bas);

			byte[] writeData = bas.toByteArray();

			DataOutputStream dos = new DataOutputStream(

			new BufferedOutputStream(new FileOutputStream(imgWriteFile)));

			dos.write(writeData);

			dos.close();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
