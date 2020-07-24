package com.example.createPDF;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Student;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreateStudentPDF {
	private static Logger logger = LoggerFactory.getLogger(CreateStudentPDF.class);

	public static ByteArrayInputStream studentPDFReport(List<Student> students) throws IOException {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			// Add Text to PDF file->
			Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 14, BaseColor.BLUE);
			Paragraph para = new Paragraph("Student Record", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(3);
			// Add PDF Table Header-->
			Stream.of("Roll", "Name", "Address").forEach(headerTitle -> {
				PdfPCell header = new PdfPCell();
				Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
				header.setBackgroundColor(BaseColor.ORANGE);
				header.setHorizontalAlignment(Element.ALIGN_CENTER);
				header.setBorder(2);
				header.setPhrase(new Phrase(headerTitle, headFont));
				table.addCell(header);
			});

			for (Student stud : students) {
				String rolls = String.valueOf(stud.getRoll());
				PdfPCell rollCell = new PdfPCell(new Phrase(rolls));
				rollCell.setPadding(4);
				rollCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				rollCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(rollCell);

				PdfPCell nameCell = new PdfPCell(new Phrase(stud.getSname()));
				nameCell.setPaddingLeft(4);
				nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				// Set Border Color-->
				nameCell.setBorderColor(BaseColor.RED);
				table.addCell(nameCell);

				PdfPCell addressCell = new PdfPCell(new Phrase(stud.getAddress()));
				nameCell.setPaddingLeft(4);
				nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(addressCell);
			}
			document.add(table);
			document.close();
		} catch (DocumentException e) {
			logger.error(e.toString());
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
}
