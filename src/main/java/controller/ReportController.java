package com.example.returnsystem.controller;

import com.example.returnsystem.model.Return;
import com.example.returnsystem.repository.ReturnRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReturnRepository repo;

    @GetMapping("/report")
    public void pdf(HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");

        Document doc = new Document();
        PdfWriter.getInstance(doc, response.getOutputStream());

        doc.open();

        doc.add(new Paragraph("Return Report"));
        doc.add(new Paragraph("Seller: seller@gmail.com"));
        doc.add(new Paragraph("----------------------"));

        List<Return> list = repo.findAll();

        for(Return r : list){
            doc.add(new Paragraph(
                    r.getProduct()+" | "+r.getProfit()+" | "+r.getReason()
            ));
        }

        doc.close();
    }
}
