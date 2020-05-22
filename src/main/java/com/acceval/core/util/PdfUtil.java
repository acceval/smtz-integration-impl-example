package com.acceval.core.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfUtil {
	
	public static  byte[] textToPdf(String data) {
		Document document = new Document();
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, byteStream);

			document.open();
			Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
			String[] lines = data.split("\n");
			for (String line:lines) {
				Paragraph para = new Paragraph(line + "\n", font);
				para.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(para);
			}
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	    return byteStream.toByteArray();
	}
}
