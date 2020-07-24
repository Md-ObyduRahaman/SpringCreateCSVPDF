package com.example.PdfRestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.createPDF.CreateStudentPDF;
import com.example.model.Student;
import com.example.repo.StudentRepo;

@RestController
public class StudentPdfRestController 
{
	@Autowired
	private StudentRepo studentRepo;
	
	@GetMapping(value="/download/student.pdf")
	public ResponseEntity<InputStreamResource> studentReport()throws IOException
	{
		List<Student> list = (List<Student>) studentRepo.findAll();
		ByteArrayInputStream bis = CreateStudentPDF.studentPDFReport(list);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; file = student.pdf");
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
				
	}
	
}
