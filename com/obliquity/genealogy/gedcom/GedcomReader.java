/*
 * genealogy - a package for reading genealogy data in GEDCOM format
 *
 * Copyright (C) 2008-2014 David Harper at obliquity.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 *
 * See the COPYING file located in the top-level-directory of
 * the archive of this library for complete text of license.
 */

package com.obliquity.genealogy.gedcom;

import java.io.*;

import java.net.URL;

import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class GedcomReader {
	protected LineNumberReader lnr;
	protected GedcomRecord lastRecord = new GedcomRecord();
	protected boolean pushback = false;
	
	public static final String UTF8_BOM = "\uFEFF";
	
	public GedcomReader(File file) throws IOException {
		lnr = new LineNumberReader(new FileReader(file));
	}
	
	public GedcomReader(URL url) throws IOException {
		lnr = new LineNumberReader(new InputStreamReader(url.openStream()));
	}
	
	public GedcomReader(InputStream is) throws IOException {
		lnr = new LineNumberReader(new InputStreamReader(is));
	}
	
	public GedcomRecord nextRecord() throws IOException, GedcomReaderException {
		if (pushback && getLineNumber() > 0) {
			pushback = false;
			return lastRecord;
		}
		
		String line = lnr.readLine();
		
		if (lnr == null)
			return null;
		
		// Remove the UTF-8 byte-order marker from the first line, if present.
		if (lnr.getLineNumber() == 1 && line.startsWith(UTF8_BOM))
			line = line.substring(1);
		
		return parseLine(line);
	}
	
	public void pushback() {
		if (lnr.getLineNumber() > 0)
			pushback = true;
	}
	
	public int getLineNumber() {
		return lnr.getLineNumber();
	}
	
	private GedcomRecord parseLine(String line) throws GedcomReaderException {
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
			throw new GedcomReaderException("Malformed GEDCOM record: unable to find level", getLineNumber());
		} catch (NumberFormatException e) {
			throw new GedcomReaderException("Malformed GEDCOM record: unable to parse level", getLineNumber());
		}

		String tag = null, xref = null;

		try {
			tag = st.nextToken();
		} catch (NoSuchElementException e) {
			throw new GedcomReaderException("Malformed GEDCOM record: second token missing", getLineNumber());
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
				throw new GedcomReaderException("Malformed GEDCOM record: third token missing", getLineNumber());
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
