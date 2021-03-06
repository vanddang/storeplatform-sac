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
import java.util.ArrayList;
import java.util.List;

/**
 * 쇼핑 쿠폰용 CSV 파싱 Class
 * 
 * Updated on : 2014. 01. 20. Updated by : 김형식, SK 플래닛
 */
public class CSVParser {
	private final char separator;

	private final char quotechar;

	private final char escape;

	private final boolean strictQuotes;

	private String pending;
	private boolean inField = false;

	private final boolean ignoreLeadingWhiteSpace;

	private final boolean ignoreQuotations;

	/**
	 * The default separator to use if none is supplied to the constructor.
	 */
	public static final char DEFAULT_SEPARATOR = ',';

	public static final int INITIAL_READ_SIZE = 128;

	/**
	 * The default quote character to use if none is supplied to the constructor.
	 */
	public static final char DEFAULT_QUOTE_CHARACTER = '"';

	/**
	 * The default escape character to use if none is supplied to the constructor.
	 */
	public static final char DEFAULT_ESCAPE_CHARACTER = '\\';

	/**
	 * The default strict quote behavior to use if none is supplied to the constructor.
	 */
	public static final boolean DEFAULT_STRICT_QUOTES = false;

	/**
	 * The default leading whitespace behavior to use if none is supplied to the constructor.
	 */
	public static final boolean DEFAULT_IGNORE_LEADING_WHITESPACE = true;

	/**
	 * I.E. if the quote character is set to null then there is no quote character.
	 */
	public static final boolean DEFAULT_IGNORE_QUOTATIONS = false;

	/**
	 * This is the "null" character - if a value is set to this then it is ignored.
	 */
	static final char NULL_CHARACTER = '\0';

	/**
	 * Constructs CSVParser using a comma for the separator.
	 */
	public CSVParser() {
		this(DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER);
	}

	/**
	 * Constructs CSVParser with supplied separator.
	 * 
	 * @param separator
	 *            the delimiter to use for separating entries.
	 */
	public CSVParser(char separator) {
		this(separator, DEFAULT_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER);
	}

	/**
	 * Constructs CSVParser with supplied separator and quote char.
	 * 
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 */
	public CSVParser(char separator, char quotechar) {
		this(separator, quotechar, DEFAULT_ESCAPE_CHARACTER);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char.
	 * 
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 */
	public CSVParser(char separator, char quotechar, char escape) {
		this(separator, quotechar, escape, DEFAULT_STRICT_QUOTES);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char. Allows setting the "strict quotes" flag
	 * 
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 * @param strictQuotes
	 *            if true, characters outside the quotes are ignored
	 */
	public CSVParser(char separator, char quotechar, char escape, boolean strictQuotes) {
		this(separator, quotechar, escape, strictQuotes, DEFAULT_IGNORE_LEADING_WHITESPACE);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char. Allows setting the "strict quotes" and
	 * "ignore leading whitespace" flags
	 * 
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 * @param strictQuotes
	 *            if true, characters outside the quotes are ignored
	 * @param ignoreLeadingWhiteSpace
	 *            if true, white space in front of a quote in a field is ignored
	 */
	public CSVParser(char separator, char quotechar, char escape, boolean strictQuotes, boolean ignoreLeadingWhiteSpace) {
		this(separator, quotechar, escape, strictQuotes, ignoreLeadingWhiteSpace, DEFAULT_IGNORE_QUOTATIONS);
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char. Allows setting the "strict quotes" and
	 * "ignore leading whitespace" flags
	 * 
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 * @param strictQuotes
	 *            if true, characters outside the quotes are ignored
	 * @param ignoreLeadingWhiteSpace
	 *            if true, white space in front of a quote in a field is ignored
	 * @param ignoreQuotations
	 *            if true, white space in front of a quote in a field is ignored
	 */
	public CSVParser(char separator, char quotechar, char escape, boolean strictQuotes,
			boolean ignoreLeadingWhiteSpace, boolean ignoreQuotations) {
		if (this.anyCharactersAreTheSame(separator, quotechar, escape)) {
			throw new UnsupportedOperationException("The separator, quote, and escape characters must be different!");
		}
		if (separator == NULL_CHARACTER) {
			throw new UnsupportedOperationException("The separator character must be defined!");
		}
		this.separator = separator;
		this.quotechar = quotechar;
		this.escape = escape;
		this.strictQuotes = strictQuotes;
		this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
		this.ignoreQuotations = ignoreQuotations;
	}

	/**
	 * Constructs CSVReader with supplied separator and quote char. Allows setting the "strict quotes" and
	 * "ignore leading whitespace" flags
	 * 
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escape
	 *            the character to use for escaping a separator or quote
	 * @return boolean
	 */
	private boolean anyCharactersAreTheSame(char separator, char quotechar, char escape) {
		return this.isSameCharacter(separator, quotechar) || this.isSameCharacter(separator, escape)
				|| this.isSameCharacter(quotechar, escape);
	}

	/**
	 * <pre>
	 * isSameCharacter.
	 * </pre>
	 * 
	 * @param c1
	 *            c1
	 * @param c2
	 *            c2
	 * @return boolean
	 */
	private boolean isSameCharacter(char c1, char c2) {
		return c1 != NULL_CHARACTER && c1 == c2;
	}

	/**
	 * @return true if something was left over from last call(s)
	 */
	public boolean isPending() {
		return this.pending != null;
	}

	/**
	 * <pre>
	 * parseLineMulti.
	 * </pre>
	 * 
	 * @param nextLine
	 *            nextLine
	 * @return String[]
	 * @throws IOException
	 *             IOException
	 */
	public String[] parseLineMulti(String nextLine) throws IOException {
		return this.parseLine(nextLine, true);
	}

	/**
	 * <pre>
	 * parseLine.
	 * </pre>
	 * 
	 * @param nextLine
	 *            nextLine
	 * @return String[]
	 * @throws IOException
	 *             IOException
	 */
	public String[] parseLine(String nextLine) throws IOException {
		return this.parseLine(nextLine, false);
	}

	/**
	 * Parses an incoming String and returns an array of elements.
	 * 
	 * @param nextLine
	 *            the string to parse
	 * @param multi
	 *            multi
	 * @return the comma-tokenized list of elements, or null if nextLine is null
	 * @throws IOException
	 *             IOException
	 */
	private String[] parseLine(String nextLine, boolean multi) throws IOException {

		if (!multi && this.pending != null) {
			this.pending = null;
		}

		if (nextLine == null) {
			if (this.pending != null) {
				String s = this.pending;
				this.pending = null;
				return new String[] { s };
			} else {
				return null;
			}
		}

		List<String> tokensOnThisLine = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(INITIAL_READ_SIZE);
		boolean inQuotes = false;
		if (this.pending != null) {
			sb.append(this.pending);
			this.pending = null;
			inQuotes = !this.ignoreQuotations; // true;
		}
		for (int i = 0; i < nextLine.length(); i++) {

			char c = nextLine.charAt(i);
			if (c == this.escape) {
				if (this.isNextCharacterEscapable(nextLine, (inQuotes && !this.ignoreQuotations) || this.inField, i)) {
					sb.append(nextLine.charAt(i + 1));
					i++;
				}
			} else if (c == this.quotechar) {
				if (this.isNextCharacterEscapedQuote(nextLine, (inQuotes && !this.ignoreQuotations) || this.inField, i)) {
					sb.append(nextLine.charAt(i + 1));
					i++;
				} else {
					inQuotes = !inQuotes;

					// the tricky case of an embedded quote in the middle: a,bc"d"ef,g
					if (!this.strictQuotes) {
						if (i > 2 // not on the beginning of the line
								&& nextLine.charAt(i - 1) != this.separator // not at the beginning of an escape
																			// sequence
								&& nextLine.length() > (i + 1) && nextLine.charAt(i + 1) != this.separator // not at the
																										   // end of an
																										   // escape
																										   // sequence
						) {

							if (this.ignoreLeadingWhiteSpace && sb.length() > 0 && this.isAllWhiteSpace(sb)) {
								sb = new StringBuilder(INITIAL_READ_SIZE); // discard white space leading up to quote
							} else {
								sb.append(c);
							}

						}
					}
				}
				this.inField = !this.inField;
			} else if (c == this.separator && !(inQuotes && !this.ignoreQuotations)) {
				tokensOnThisLine.add(sb.toString());
				sb = new StringBuilder(INITIAL_READ_SIZE); // start work on next token
				this.inField = false;
			} else {
				if (!this.strictQuotes || (inQuotes && !this.ignoreQuotations)) {
					sb.append(c);
					this.inField = true;
				}
			}
		}
		// line is done - check status
		if ((inQuotes && !this.ignoreQuotations)) {
			if (multi) {
				// continuing a quoted section, re-append newline
				sb.append("\n");
				this.pending = sb.toString();
				sb = null; // this partial content is not to be added to field list yet
			} else {
				throw new IOException("Un-terminated quoted field at end of CSV line");
			}
		}
		if (sb != null) {
			tokensOnThisLine.add(sb.toString());
		}
		return tokensOnThisLine.toArray(new String[tokensOnThisLine.size()]);

	}

	/**
	 * precondition: the current character is a quote or an escape.
	 * 
	 * @param nextLine
	 *            the current line
	 * @param inQuotes
	 *            true if the current context is quoted
	 * @param i
	 *            current index in line
	 * @return true if the following character is a quote
	 */
	private boolean isNextCharacterEscapedQuote(String nextLine, boolean inQuotes, int i) {
		return inQuotes // we are in quotes, therefore there can be escaped quotes in here.
				&& nextLine.length() > (i + 1) // there is indeed another character to check.
				&& nextLine.charAt(i + 1) == this.quotechar;
	}

	/**
	 * precondition: the current character is an escape.
	 * 
	 * @param nextLine
	 *            the current line
	 * @param inQuotes
	 *            true if the current context is quoted
	 * @param i
	 *            current index in line
	 * @return true if the following character is a quote
	 */
	protected boolean isNextCharacterEscapable(String nextLine, boolean inQuotes, int i) {
		return inQuotes // we are in quotes, therefore there can be escaped quotes in here.
				&& nextLine.length() > (i + 1) // there is indeed another character to check.
				&& (nextLine.charAt(i + 1) == this.quotechar || nextLine.charAt(i + 1) == this.escape);
	}

	/**
	 * precondition: sb.length() > 0.
	 * 
	 * @param sb
	 *            A sequence of characters to examine
	 * @return true if every character in the sequence is whitespace
	 */
	protected boolean isAllWhiteSpace(CharSequence sb) {
		boolean result = true;
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);

			if (!Character.isWhitespace(c)) {
				return false;
			}
		}
		return result;
	}
}
