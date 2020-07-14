package com.springboot.chapter10.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

public interface PdfExportService {
	public void make(Map<String, Object> model, Document document,
	        PdfWriter writer, HttpServletRequest request,
	        HttpServletResponse response);
}
