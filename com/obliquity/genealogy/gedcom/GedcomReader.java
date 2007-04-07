package com.obliquity.genealogy.gedcom;

import java.io.*;

import java.net.URL;

import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class GedcomReader {
	protected BufferedReader br;
	protected GedcomRecord lastRecord = new GedcomRecord();
	protected boolean pushback = false;
	protected int linenumber = 0;
	
	public GedcomReader(File file) throws IOException {
		br = new BufferedReader(new FileReader(file));
	}
	
	public GedcomReader(URL url) throws IOException {
		br = new BufferedReader(new InputStreamReader(url.openStream()));
	}
	
	public GedcomReader(InputStream is) throws IOException {
		br = new BufferedReader(new InputStreamReader(is));
	}
	
	public GedcomRecord nextRecord() throws IOException, GedcomException {
		if (pushback && linenumber > 0) {
			pushback = false;
			return lastRecord;
		}
		
		String line = br.readLine();
		
		if (br == null)
			return null;
		
		linenumber++;
		
		return parseLine(line);
	}
	
	public void pushback() {
		if (linenumber > 0)
			pushback = true;
	}
	
	public int getLineNumber() {
		return linenumber;
	}
	
	private GedcomRecord parseLine(String line) throws GedcomException {
		StringTokenizer st = new StringTokenizer(line);

		String strlevel;
		Integer intlevel;
		int level;
		int firstAt = -1, lastAt = -1;

		try {
			strlevel = st.nextToken();
			intlevel = Integer.valueOf(strlevel);
			level = intlevel.intValue();
		} catch (NoSuchElementException e) {
			throw new GedcomException("Malformed GEDCOM record: unable to find level", linenumber);
		} catch (NumberFormatException e) {
			throw new GedcomException("Malformed GEDCOM record: unable to parse level", linenumber);
		}

		String tag = null, xref = null;

		try {
			tag = st.nextToken();
		} catch (NoSuchElementException e) {
			throw new GedcomException("Malformed GEDCOM record: second token missing", linenumber);
		}

		firstAt = tag.indexOf('@');
		lastAt = tag.indexOf('@', firstAt + 1);

		if (firstAt >= 0 && lastAt > firstAt) {
			xref = tag.substring(firstAt + 1, lastAt);
			tag = null;
		} else {
			xref = null;
		}

		if (tag == null) {
			try {
				tag = st.nextToken();
			} catch (NoSuchElementException e) {
				throw new GedcomException("Malformed GEDCOM record: third token missing", linenumber);
			}
		}

		String contents = null;

		if (st.hasMoreElements()) {
			contents = st.nextToken();
		}

		while (st.hasMoreElements()) {
			contents += " " + st.nextToken();
		}

		lastRecord.setProperties(level, tag.intern(), xref, contents);
		
		return lastRecord;
	}

}
