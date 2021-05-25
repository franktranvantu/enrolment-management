package com.franktran.enrolmentmanagement.student;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class StudentExcelExporter implements StudentExporter {

  private final Formatter<LocalDate> dateFormatter;

  private XSSFWorkbook workbook;
  private XSSFSheet sheet;

  public StudentExcelExporter(Formatter<LocalDate> dateFormatter) {
    this.dateFormatter = dateFormatter;
  }

  private void writeHeaderLine() {
    workbook = new XSSFWorkbook();
    sheet = workbook.createSheet("Students");

    Row row = sheet.createRow(0);

    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setBold(true);
    font.setFontHeight(16);
    style.setFont(font);
    style.setAlignment(HorizontalAlignment.CENTER);

    createCell(row, 0, "Id", style);
    createCell(row, 1, "Name", style);
    createCell(row, 2, "Email", style);
    createCell(row, 3, "Birthday", style);
  }

  private void createCell(Row row, int columnCount, Object value, CellStyle style) {
    sheet.autoSizeColumn(columnCount);
    Cell cell = row.createCell(columnCount);
    if (value instanceof Integer) {
      cell.setCellValue((Integer) value);
    } else if (value instanceof Long) {
      cell.setCellValue((Long) value);
    } else if (value instanceof LocalDate) {
      cell.setCellValue(dateFormatter.print((LocalDate) value, LocaleContextHolder.getLocale()));
    } else {
      cell.setCellValue((String) value);
    }
    cell.setCellStyle(style);
  }

  private void writeDataLines(List<Student> students) {
    int rowCount = 1;

    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontHeight(14);
    style.setFont(font);

    for (Student user : students) {
      Row row = sheet.createRow(rowCount++);
      int columnCount = 0;

      createCell(row, columnCount++, user.getId(), style);
      createCell(row, columnCount++, user.getName(), style);
      createCell(row, columnCount++, user.getEmail(), style);
      createCell(row, columnCount++, user.getDob(), style);
    }
  }

  @Override
  public void export(HttpServletResponse response, List<Student> students, String fileName) throws IOException {
    writeHeaderLine();
    writeDataLines(students);

    response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));
    ServletOutputStream outputStream = response.getOutputStream();
    workbook.write(outputStream);
    outputStream.close();
    workbook.close();
  }

}
