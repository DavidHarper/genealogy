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
