package com.skplanet.storeplatform.sac.api.util;

import java.io.File;

/**
 * 
 * @author Owner
 */
public class ImageUtil {

	// protected static Log logger = LogFactory.getLog(ImageUtil.class);

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
				System.out.println("소스 파일이 존재하지 않는다.");
				return false;
			}

			File outputfile = new File(out_file);
			if (outputfile.exists() == true) {
				System.out.println("변경하려는 파일과 동일한 파일이 존재하여 삭제합나디. 파일명 [" + outputfile.getName() + "]");
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

}
