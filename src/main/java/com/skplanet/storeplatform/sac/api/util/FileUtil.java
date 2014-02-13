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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * FileUtil은 Text 또는 Binary 파일 처리에 관련된 기능을 제공한다.
 */
public class FileUtil {
	/** Default reading buffer Size */
	public final static int BUFFER_SIZE = 1024;

	/**
	 * 지정된 fname에 content(Text)를 저장한다.
	 * 
	 * @param fname
	 *            저장할 파일
	 * @param content
	 *            저장할 내용
	 * @throws Exception
	 */
	public synchronized static void write(File fname, String content) throws Exception {
		BufferedWriter _bufferedWriter = null;
		try {
			_bufferedWriter = new BufferedWriter(new FileWriter(fname));

			_bufferedWriter.write(content);
			_bufferedWriter.flush();
		} catch (IOException ioe) {
			throw new Exception(ioe);
		} finally {
			try {
				if (_bufferedWriter != null)
					_bufferedWriter.close();
			} catch (IOException ioe) {
				throw new Exception(ioe);
			}
		}
	}

	/**
	 * 지정된 fname에 content(Binary)를 저장한다.
	 * 
	 * @param fname
	 *            저장할 파일
	 * @param content
	 *            저장할 내용
	 * @throws Exception
	 */
	public synchronized static void write(File fname, byte[] content) throws Exception {
		try {

			FileOutputStream out = new FileOutputStream(fname);
			out.write(content, 0, content.length);
			out.close();
		} catch (IOException ioe) {
			throw new Exception(ioe);
		}
	}

	/**
	 * 지정된 fname에서 데이터를 읽어 byte[]로 반환한다.
	 * 
	 * @param fname
	 *            읽을 파일
	 * @return 파일 내용
	 * @throws Exception
	 */
	public synchronized static byte[] readBytes(File fname) throws Exception {
		try {
			FileInputStream fis = new FileInputStream(fname);
			int bufsize = new Long(fname.length()).intValue();
			byte[] buffer = new byte[bufsize];
			int rsize = 0;

			while (-1 != (rsize = fis.read(buffer)))
				;
			fis.close();

			return buffer;
		} catch (IOException ioe) {
			throw new Exception(ioe);
		}
	}

	/**
	 * 지정된 fname에서 데이터를 읽어 string으로 반환한다.
	 * 
	 * @param fname
	 *            읽을 파일
	 * @return 파일 내용
	 * @throws Exception
	 */
	public static String read(File fname) throws Exception {
		BufferedReader _bufferedReader = null;
		StringBuffer result = new StringBuffer();

		try {
			_bufferedReader = new BufferedReader(new FileReader(fname));
			int rsize = 0;
			char[] buff = new char[BUFFER_SIZE];

			while (-1 != (rsize = _bufferedReader.read(buff, 0, BUFFER_SIZE))) {
				result.append(buff, 0, rsize);
			}
		} catch (IOException ioe) {
			throw new Exception(ioe);
		} finally {
			_bufferedReader.close();
		}

		return result.toString();
	}

	/**
	 * 지정한 fname을 삭제한다.
	 * 
	 * @param fname
	 *            삭제할 파일
	 * @throws IOException
	 */
	public static boolean delete(File fname) throws IOException {
		synchronized (fname) {
			return fname.delete();
		}
	}

	/**
	 * 파일을 카피하기 위해 지정된 in을 읽어 out에 저장한다.
	 * 
	 * @param in
	 *            source file inputstream
	 * @param out
	 *            target file outputstream
	 * @throws IOException
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException {
		synchronized (in) {
			synchronized (out) {
				BufferedInputStream bin = new BufferedInputStream(in);
				BufferedOutputStream bout = new BufferedOutputStream(out);

				while (true) {
					int datum = bin.read();
					if (datum == -1)
						break;
					bout.write(datum);
				}
				bout.flush();
			}
		}
	}

	/**
	 * 디렉토리가 존재하는지 확인한다.
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean directoryCheck(String fileName) {
		String directoryName = "";
		File f = null;
		if (fileName.lastIndexOf(".") > 0) {
			directoryName = fileName.substring(0, fileName.lastIndexOf("."));

		} else {
			directoryName = fileName;
		}

		f = new File(directoryName);

		return f.isDirectory();
	}

	/**
	 * 디렉토리 생성
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean createDirectory(String fileName) {
		String directoryName = "";
		File f = null;
		if (fileName.lastIndexOf(".") > 0) {
			directoryName = fileName.substring(0, fileName.lastIndexOf("."));

		} else {
			directoryName = fileName;
		}
		f = new File(directoryName);

		return f.mkdirs();
	}

	/**
	 * 파일 복사
	 * 
	 * @param src
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public static int fileCopy(String src, String target) throws Exception {
		FileInputStream fis = new FileInputStream(src);
		int len = fis.available();
		byte buf[] = new byte[len];
		fis.read(buf, 0, len);
		fis.close();
		FileOutputStream fos = new FileOutputStream(target);
		fos.write(buf, 0, len);
		fos.close();
		return 1;
	}

	/**
	 * 
	 * TODO 파일 존재 유무
	 * <P/>
	 * 파일이 실제 서버에 존재하는지 확인
	 * 
	 * @param url
	 *            : "http://" 포함한 full URL
	 * @return
	 * @throws Exception
	 */
	public static boolean CheckUrl(String url) {

		if (!url.startsWith("http://")) {
			return false;
		}
		try {
			URL oUrl = new URL(url);
			URLConnection oURLConnection = oUrl.openConnection();
			oURLConnection.connect();
			String szContent = oURLConnection.getContent().toString();

			return true;

		} catch (Exception e) {

			return false;
		}
	}

}
