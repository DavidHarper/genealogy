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

package com.obliquity.genealogy.test;

import java.io.*;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.factory.DateParser;

public class TestDateParser {
	protected DateParser parser = new DateParser();
	
	public static void main(String[] args) {
		TestDateParser dpt = new TestDateParser();
		dpt.execute();
	}

	public void execute() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line = null;

		int lineNumber = 0;

		try {
			while (true) {
				System.out.print("> ");
				line = br.readLine();
				if (line == null)
					break;
				lineNumber++;
				try {
					Date date = parser.parseDate(line);
					System.out.println("line --> " + date);
				} catch (Exception e) {
					System.err.println("Error at line " + lineNumber
							+ " of input file: \"" + line + "\"");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
