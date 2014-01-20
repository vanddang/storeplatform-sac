package com.skplanet.storeplatform.sac.api.util;

import java.io.IOException;
import java.util.Iterator;

public class CSVIterator implements Iterator<String[]> {
	private final CSVReader reader;
	private String[] nextLine;

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
