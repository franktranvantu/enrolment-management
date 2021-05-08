package com.franktran.enrolmentmanagement.student;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class StudentPdfExporter {

  private final Formatter<LocalDate> dateFormatter;

  public StudentPdfExporter(Formatter<LocalDate> dateFormatter) {
    this.dateFormatter = dateFormatter;
  }

  private void writeTableHeader(PdfPTable table) {
    PdfPCell cell = new PdfPCell();
    cell.setPadding(5);
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

    Font font = FontFactory.getFont(FontFactory.HELVETICA);

    cell.setPhrase(new Phrase("Id", font));
    table.addCell(cell);

    cell.setPhrase(new Phrase("Name", font));
    table.addCell(cell);

    cell.setPhrase(new Phrase("Email", font));
    table.addCell(cell);

    cell.setPhrase(new Phrase("Birthday", font));
    table.addCell(cell);
  }

  private void writeTableData(PdfPTable table, List<Student> students) {
    for (Student user : students) {
      table.addCell(String.valueOf(user.getId()));
      table.addCell(user.getName());
      table.addCell(user.getEmail());
      table.addCell(dateFormatter.print(user.getDob(), LocaleContextHolder.getLocale()));
    }
  }

  public void export(HttpServletResponse response, List<Student> students, String fileName) throws DocumentException, IOException {
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, response.getOutputStream());

    document.open();
    Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    font.setSize(18);
    font.setColor(Color.BLUE);

    Paragraph p = new Paragraph("All Students List", font);
    p.setAlignment(Paragraph.ALIGN_CENTER);

    document.add(p);

    PdfPTable table = new PdfPTable(4);
    table.setWidthPercentage(100f);
    table.setWidths(new float[] {1.5f, 3.0f, 3.5f, 3.0f});
    table.setSpacingBefore(10);

    writeTableHeader(table);
    writeTableData(table, students);

    response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));

    document.add(table);

    document.close();
  }
}
