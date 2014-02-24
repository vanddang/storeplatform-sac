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

import java.io.IOException;
import java.util.Iterator;

/**
 * 쇼핑 쿠폰용 CSV 파싱 Class
 * 
 * Updated on : 2014. 01. 20. Updated by : 김형식, SK 플래닛
 */
public class CSVIterator implements Iterator<String[]> {
	private final CSVReader reader;
	private String[] nextLine;

	/**
	 * <pre>
	 * CSVIterator.
	 * </pre>
	 * 
	 * @param reader
	 *            reader
	 * @throws IOException
	 *             IOException
	 */
	public CSVIterator(CSVReader reader) throws IOException {
		this.reader = reader;
		this.nextLine = reader.readNext();
	}

	@Override
	public boolean hasNext() {
		return this.nextLine != null;
	}

	@Override
	public String[] next() {
		String[] temp = this.nextLine;
		try {
			this.nextLine = this.reader.readNext();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return temp;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("This is a read only iterator.");
	}
}
