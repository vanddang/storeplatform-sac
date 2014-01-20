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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 쇼핑 쿠폰용 CSV 파싱 Class
 * 
 * Updated on : 2014. 01. 20. Updated by : 김형식, SK 플래닛
 */
public class CSVReader implements Closeable, Iterable<String[]> {
	private final BufferedReader br;

	private boolean hasNext = true;

	private final CSVParser parser;

	private final int skipLines;

	private boolean linesSkiped;

	private final boolean outOfLines = false;

	/**
	 * The default line to start reading.
	 */
	public static final int DEFAULT_SKIP_LINES = 0;

	/**
	 * Constructs CSVReader using a comma for the separator.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 */
	public CSVReader(Reader reader) {
		this(reader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER);
	}

	/**
	 * Constructs CSVReader with supplied separator.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries.
	 */
	public CSVReader(Reader reader, char separator) {
		this(reader, separator, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 */
	public CSVReader(Reader reader, char separator, char quotechar) {
		this(reader, separator, quotechar, CSVParser.DEFAULT_ESCAPE_CHARACTER, DEFAULT_SKIP_LINES,
				CSVParser.DEFAULT_STRICT_QUOTES);
	}

	/**
	 * Constructs CSVReader with supplied separator, quote char and quote handling behavior.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param strictQuotes
	 *            sets if characters outside the quotes are ignored
	 */
	public CSVReader(Reader reader, char separator, char quotechar, boolean strictQuotes) {
		this(reader, separator, quotechar, CSVParser.DEFAULT_ESCAPE_CHARACTER, DEFAULT_SKIP_LINES, strictQuotes);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 */

	public CSVReader(Reader reader, char separator, char quotechar, char escape) {
		this(reader, separator, quotechar, escape, DEFAULT_SKIP_LINES, CSVParser.DEFAULT_STRICT_QUOTES);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param line
	 *            the line number to skip for start reading
	 */
	public CSVReader(Reader reader, char separator, char quotechar, int line) {
		this(reader, separator, quotechar, CSVParser.DEFAULT_ESCAPE_CHARACTER, line, CSVParser.DEFAULT_STRICT_QUOTES);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 * @param line
	 *            the line number to skip for start reading
	 */
	public CSVReader(Reader reader, char separator, char quotechar, char escape, int line) {
		this(reader, separator, quotechar, escape, line, CSVParser.DEFAULT_STRICT_QUOTES);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 * @param line
	 *            the line number to skip for start reading
	 * @param strictQuotes
	 *            sets if characters outside the quotes are ignored
	 */
	public CSVReader(Reader reader, char separator, char quotechar, char escape, int line, boolean strictQuotes) {
		this(reader, separator, quotechar, escape, line, strictQuotes, CSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char.
	 * 
	 * @param reader
	 *            the reader to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 * @param line
	 *            the line number to skip for start reading
	 * @param strictQuotes
	 *            sets if characters outside the quotes are ignored
	 * @param ignoreLeadingWhiteSpace
	 *            it true, parser should ignore white space before a quote in a field
	 */
	public CSVReader(Reader reader, char separator, char quotechar, char escape, int line, boolean strictQuotes,
			boolean ignoreLeadingWhiteSpace) {
		this.br = new BufferedReader(reader);
		this.parser = new CSVParser(separator, quotechar, escape, strictQuotes, ignoreLeadingWhiteSpace);
		this.skipLines = line;
	}

	/**
	 * Reads the entire file into a List with each element being a String[] of tokens.
	 * 
	 * @return a List of String[], with each String[] representing a line of the file.
	 * @throws IOException
	 *             if bad things happen during the read
	 */
	public List<String[]> readAll() throws IOException {

		List<String[]> allElements = new ArrayList<String[]>();
		while (this.hasNext) {
			String[] nextLineAsTokens = this.readNext();
			if (nextLineAsTokens != null)
				allElements.add(nextLineAsTokens);
		}
		return allElements;

	}

	/**
	 * Reads the next line from the buffer and converts to a string array.
	 * 
	 * @return a string array with each comma-separated element as a separate entry.
	 * @throws IOException
	 *             if bad things happen during the read
	 */
	public String[] readNext() throws IOException {

		String[] result = null;
		do {
			String nextLine = this.getNextLine();
			if (!this.hasNext) {
				return result; // should throw if still pending?
			}
			String[] r = this.parser.parseLineMulti(nextLine);
			if (r.length > 0) {
				if (result == null) {
					result = r;
				} else {
					String[] t = new String[result.length + r.length];
					System.arraycopy(result, 0, t, 0, result.length);
					System.arraycopy(r, 0, t, result.length, r.length);
					result = t;
				}
			}
		} while (this.parser.isPending());
		return result;
	}

	/**
	 * Reads the next line from the file.
	 * 
	 * @return the next line from the file without trailing newline
	 * @throws IOException
	 *             if bad things happen during the read
	 */
	private String getNextLine() throws IOException {
		if (!this.linesSkiped) {
			for (int i = 0; i < this.skipLines; i++) {
				this.br.readLine();
			}
			this.linesSkiped = true;
		}
		String nextLine = this.br.readLine();
		if (nextLine == null) {
			this.hasNext = false;
		}
		return this.hasNext ? nextLine : null;
	}

	/**
	 * Closes the underlying reader.
	 * 
	 * @throws IOException
	 *             if the close fails
	 */
	@Override
	public void close() throws IOException {
		this.br.close();
	}

	@Override
	public Iterator<String[]> iterator() {
		try {
			return new CSVIterator(this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
