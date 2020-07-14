package com.springboot.chapter10.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.springboot.chapter10.service.PdfExportService;

/**** imports ****/
public class PdfView extends AbstractPdfView {
    // 导出服务接口
    private PdfExportService pdfExportService = null;
    
    // 创建对象的时候载入导出服务接口
    public PdfView(PdfExportService pdfExportService) {
        this.pdfExportService = pdfExportService;
    }
    
    // 调用接口实现
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, 
        PdfWriter writer,HttpServletRequest request, 
        HttpServletResponse response) throws Exception {
        // 调用导出服务接口类
        pdfExportService.make(model, document, writer, request, response);
    }
}