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

import com.sap.pi.document.util.DocDomGroupUtil;
import com.sap.pi.document.util.dao.CONSTAINTS;
import com.sap.pi.document.util.dao.Item;

public class DocDomGroupUtilImpl implements DocDomGroupUtil {

	private FileInputStream fips;
	private XWPFDocument document;
	private FileOutputStream fops;

	@Override
	public void generateDomGroupFile(File templateFile, List<Item> items) {

		// initial parameters
		Map<String, String> parameters = new HashMap<String, String>();
		for (Item item : items) {
			parameters.put(item.getParaName(), item.getParaValue());
		}

		// get domGroup type
		String templateFileName = templateFile.getName();
		String type = templateFileName.substring(0, templateFileName.indexOf("."));

		try {
			fips = new FileInputStream(templateFile);
			document = new XWPFDocument(fips);

			// create tempt file for document domGroup, named like
			// docDomGroup_<type>_<domGroupName>_tempt.docx
			// docDomGroup_<type>_tempt.docx
			String domGroupTemptFilePath = "";

			if (parameters.get("$Group_Name") != null && parameters.get("$Group_Name") != ""
					&& parameters.get("$Group_Name").toLowerCase() != "null") {
				domGroupTemptFilePath = CONSTAINTS.temptDomGroupPath + "docDomGroup_" + type + "_"
						+ parameters.get("$Group_Name") + "_tempt.docx";
			} else {
				domGroupTemptFilePath = CONSTAINTS.temptDomGroupPath + "docDomGroup_" + type + "_tempt.docx";
			}

			File domGroupTemptFile = new File(domGroupTemptFilePath);

			if (domGroupTemptFile.exists()) {
				domGroupTemptFile.delete();
			}

			domGroupTemptFile.createNewFile();

			fops = new FileOutputStream(domGroupTemptFile, true);

			// modified the groupName
			// replace content
			String regularExpression = "\\$(.*)_Value";

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
			String domGroupFilePath = "";
			if (parameters.get("$Main_Name") != null && parameters.get("$Main_Name") != ""
					&& parameters.get("$Main_Name") != "null") {
				domGroupFilePath = CONSTAINTS.temptDomGroupPath + "docDomGroup_" + type + "_"
						+ parameters.get("$Main_Name") + ".docx";
			} else {
				domGroupFilePath = CONSTAINTS.temptDomGroupPath + "docDomGroup_" + type + ".docx";
			}

			File domGroupFile = new File(domGroupFilePath);
			if (domGroupFile.exists()) {
				domGroupFile.delete();
			}

			domGroupFile.createNewFile();

			// generate File list of input <domXX.docx and
			// domgroup_XXX_tempt.docx>
			List<File> sourceFiles = new ArrayList<>();
			sourceFiles.add(domGroupTemptFile);

			File dir = new File(CONSTAINTS.temptDomPath);
			for (File file : dir.listFiles()) {
				if (!file.isDirectory()) {
					String fileName = file.getName();

					fileName = fileName.substring(fileName.indexOf("_") + 1);

					if (type.equalsIgnoreCase(fileName.substring(0, fileName.indexOf("_")))) {
						sourceFiles.add(file);
					}

				}
			}

			// merge content to the domGroup file
			try {
				ContentMerge.mergeContent(sourceFiles, domGroupFile, false);
			} catch (InvalidFormatException | XmlException e) {
				e.printStackTrace();
			}

			// clean up tempt dom folder removeAllDomFile();
			removeAllDomFile(type);
			// remove tempt file
			if (domGroupTemptFile.exists()) {
				domGroupTemptFile.delete();
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
	private void removeAllDomFile(String type) {
		int i = 0;
		// loop directory and delete tempt file
		File dir = new File(CONSTAINTS.temptDomPath);
		for (File file : dir.listFiles()) {
			if (!file.isDirectory()) {
				String fileName = file.getName();

				fileName = fileName.substring(fileName.indexOf("_") + 1);

				if (type.equalsIgnoreCase(fileName.substring(0, fileName.indexOf("_")))) {
					i++;
					file.delete();
				}
			}
		}
		System.out.println("delete files number is:\t" + i);
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
