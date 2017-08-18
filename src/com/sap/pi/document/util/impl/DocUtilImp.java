package com.sap.pi.document.util.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;

import com.sap.pi.document.util.DocUtil;
import com.sap.pi.document.util.dao.CONSTAINTS;
import com.sap.pi.document.util.dao.Item;

public class DocUtilImp implements DocUtil {

	private FileInputStream fips;
	private XWPFDocument document;
	private FileOutputStream fops;

	@Override
	public void generateDocFile(File templateFile, List<Item> items) {

		// initial parameters
		Map<String, String> parameters = new HashMap<String, String>();

		for (Item item : items) {
			parameters.put(item.getParaName(), item.getParaValue());
		}

		try {
			fips = new FileInputStream(templateFile);
			document = new XWPFDocument(fips);

			// create tempt file for document domGroup, named like
			// docDomGroup_<type>_<domName>_tempt.docx
			String icoTemptDocPath = CONSTAINTS.resultPath + "ICO_" + parameters.get("$ICO_Value") + "_tempt.docx";

			File icoTemptDoc = new File(icoTemptDocPath);

			if (icoTemptDoc.exists()) {
				icoTemptDoc.delete();
			}

			icoTemptDoc.createNewFile();

			fops = new FileOutputStream(icoTemptDoc, true);

			// modified the groupName
			// replace content
			String regularExpression = "\\$(.*)_VALUE";

			List<XWPFParagraph> paragraphs = document.getParagraphs();
			for (XWPFParagraph paragraph : paragraphs) {
				List<XWPFRun> runs = paragraph.getRuns();

				if (runs != null) {
					for (XWPFRun run : runs) {
						String text = run.getText(0);
						if (text != null && Pattern.matches(regularExpression, text)) {
							text = parameters.get(text);
							run.setText(text, 0);
						}
					}
				}
			}
			document.write(fops);
			close();

			// copy all the information from dom and domgroup tempt files
			String icoDocPath = CONSTAINTS.resultPath + "ICO_" + parameters.get("$ICO_Value") + ".docx";

			File icoDoc = new File(icoDocPath);
			if (icoDoc.exists()) {
				icoDoc.delete();
			}

			icoDoc.createNewFile();

			// generate File list of input <domXX.docx and
			// domgroup_XXX_tempt.docx>
			List<File> sourceFiles = new ArrayList<>();
			sourceFiles.add(icoTemptDoc);

			File dir = new File(CONSTAINTS.temptDomGroupPath);
			for (File file : dir.listFiles()) {
				if (!file.isDirectory()) {
					sourceFiles.add(file);
				}
			}

			// merge content to the domGroup file
			try {

				ContentMerge.mergeContent(sourceFiles, icoDoc, false);
			} catch (InvalidFormatException | XmlException e) {
				e.printStackTrace();
			}

			// clean up tempt dom folder removeAllDomFile();
			removeAllDomGroupFile();
			// remove tempt file
			if (icoTemptDoc.exists()) {
				icoTemptDoc.delete();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * remove all the generated tempt dom files
	 */
	private void removeAllDomGroupFile() {
		// loop directory and delete tempt file
		File dir = new File(CONSTAINTS.temptDomGroupPath);
		for (File file : dir.listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
	}

	private void close() {
		try {
			fips.close();
			fops.flush();
			fops.close();
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
