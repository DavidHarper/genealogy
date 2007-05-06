package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.Date;
import com.obliquity.genealogy.DateRange;
import com.obliquity.genealogy.DatePeriod;
import com.obliquity.genealogy.gedcom.MalformedDateException;

public class DateParser {
	public static final int UNKNOWN = 0;
	public static final int ABOUT = 1;
	public static final int CALCULATED = 2;
	public static final int ESTIMATED = 3;
	public static final int FROM = 4;
	public static final int TO = 5;
	public static final int BEFORE = 6;
	public static final int AFTER = 7;
	public static final int BETWEEN = 8;
	public static final int WFT = 9;

	protected final String[] months = { "JAN", "FEB", "MAR", "APR", "MAY",
			"JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

	public Date parseDate(String line) throws MalformedDateException {
		String[] words = line.trim().split("\\s+");
		
		return parseDate(words);
	}

	protected Date parseDate(String[] words) throws MalformedDateException {
		if (words == null || words.length == 0)
			return null;
		
		int code = decodeKeyword(words[0]);

		switch (code) {
			case ABOUT:
			case CALCULATED:
			case ESTIMATED:
				return parseApproximateDate(words, code);

			case FROM:
			case TO:
				return parseDatePeriod(words, code);

			case BEFORE:
			case AFTER:
			case BETWEEN:
				return parseDateRange(words, code);
				
			case WFT:
				return parseWFTDate(words);

			default:
				return parsePlainDate(words);
		}
	}

	protected int decodeKeyword(String str) {
		if (str.equalsIgnoreCase("ABT"))
			return ABOUT;

		if (str.equalsIgnoreCase("CAL"))
			return CALCULATED;

		if (str.equalsIgnoreCase("EST"))
			return ESTIMATED;

		if (str.equalsIgnoreCase("FROM"))
			return FROM;

		if (str.equalsIgnoreCase("TO"))
			return TO;

		if (str.equalsIgnoreCase("BEF"))
			return BEFORE;

		if (str.equalsIgnoreCase("AFT"))
			return AFTER;

		if (str.equalsIgnoreCase("BET"))
			return BETWEEN;

		if (str.equalsIgnoreCase("WFT"))
			return WFT;

		return UNKNOWN;
	}

	protected Date parsePlainDate(String[] words) throws MalformedDateException {
		return parsePlainDate(words, 0, 0);
	}
	
	protected Date parsePlainDate(String[] words, int offset, int nwords)
	throws MalformedDateException {
		return parsePlainDate(words, offset, nwords, Date.EXACT);
	}

	protected Date parsePlainDate(String[] words, int offset, int nwords, int qualifier)
			throws MalformedDateException {
		if (nwords == 0)
			nwords = words.length - offset;

		int year = 0;
		int month = 0;
		int day = 0;

		String strYear;
		int i;
		boolean julian = false;

		switch (nwords) {
			case 1:
				if (isDigits(words[offset]))
					year = Integer.parseInt(words[offset]);
				else 
					return null;
				break;

			case 2:
				if (isDigits(words[offset])) {
					day = Integer.parseInt(words[offset]);
					month = monthNameToInt(words[offset + 1]);
				} else {
					month = monthNameToInt(words[offset]);
					strYear = words[offset + 1];
					i = strYear.indexOf("/");
					julian = i >= 0;
					year = julian ? Integer.parseInt(strYear.substring(0, i))
							: Integer.parseInt(strYear);
				}
				break;

			case 3:
				day = Integer.parseInt(words[offset]);
				month = monthNameToInt(words[offset + 1]);
				strYear = words[offset + 2];
				i = strYear.indexOf("/");
				julian = i >= 0;
				year = julian ? Integer.parseInt(strYear.substring(0, i))
						: Integer.parseInt(strYear);
				break;

			default:
				throw new MalformedDateException("Invalid number of tokens (" + nwords + ")");
		}

		return new Date(year, month, day, qualifier, julian);
	}

	protected int monthNameToInt(String str) {
		for (int i = 0; i < months.length; i++)
			if (str.equalsIgnoreCase(months[i]))
				return i + 1;

		return 0;
	}

	protected Date parseApproximateDate(String[] words, int code)
			throws MalformedDateException {
		int qualifier = Date.EXACT;
		
		switch (code) {
			case ABOUT:
				qualifier = Date.ABOUT;
				break;

			case CALCULATED:
				qualifier = Date.CALCULATED;
				break;

			case ESTIMATED:
				qualifier = Date.ESTIMATED;
				break;
		}

		return parsePlainDate(words, 1, 0, qualifier);
	}

	protected DatePeriod parseDatePeriod(String[] words, int code)
			throws MalformedDateException {
		int toOffset = 0;
		Date date1 = null;
		Date date2 = null;

		if (code == FROM)
			for (int i = 1; i < words.length; i++) {
				if (words[i].equalsIgnoreCase("TO")) {
					toOffset = i;
					break;
				}
			}

		int flags = 0;
		
		if (toOffset > 0) {
			date1 = parsePlainDate(words, 1, toOffset - 1);
			date2 = parsePlainDate(words, toOffset + 1, 0);
			flags = DatePeriod.FROM_TO;
		} else {
			date1 = parsePlainDate(words, 1, 0);
			flags = (code == FROM) ? DatePeriod.FROM : DatePeriod.TO;
		}

		return new DatePeriod(date1, date2, flags);
	}

	protected DateRange parseDateRange(String[] words, int code)
			throws MalformedDateException {
		Date date1 = null;
		int flags = 0;
		
		switch (code) {
			case BEFORE:
			case AFTER:
				date1 = parsePlainDate(words, 1, 0);
				flags = (code == BEFORE) ? DateRange.BEFORE : DateRange.AFTER;
				return new DateRange(date1, flags);

			case BETWEEN:
				int andOffset = 0;
				date1 = null;
				Date date2 = null;

				for (int i = 1; i < words.length; i++) {
					if (words[i].equalsIgnoreCase("AND")) {
						andOffset = i;
						break;
					}
				}

				if (andOffset > 0) {
					date1 = parsePlainDate(words, 1, andOffset - 1);
					date2 = parsePlainDate(words, andOffset + 1, 0);
					return new DateRange(date1, date2);
				} else
					throw new MalformedDateException("AND keyword missing");
		}
		
		return null;
	}
	
	protected Date parseWFTDate(String[] words) throws MalformedDateException {
		if (!words[1].equalsIgnoreCase("EST"))
			throw new MalformedDateException("Second word \"" + words[1] + "\" not recognised in WFT date");
		
		if (words.length < 3)
			throw new MalformedDateException("Too few words in WFT date");
		
		if (words[2].matches("\\d+-\\d+")) {
			String[] years = words[2].split("-");
			Date date1 = new Date(Integer.parseInt(years[0]), 0, 0, Date.ESTIMATED);
			Date date2 = new Date(Integer.parseInt(years[1]), 0, 0, Date.ESTIMATED);
			return new DateRange(date1, date2);
		}
		
		if (words[2].matches("\\d+")) {
			return new Date(Integer.parseInt(words[2]), 0, 0, Date.ESTIMATED);
		}
			
		if (words[2].equalsIgnoreCase("BEF")) {
			Date date = new Date(Integer.parseInt(words[3]));
			return new DateRange(date, DateRange.BEFORE);
		}
		
		return null;
	}

	protected boolean isDigits(String str) {
		return str.matches("\\d+");
	}

}
