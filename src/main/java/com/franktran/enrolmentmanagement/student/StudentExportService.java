package com.franktran.enrolmentmanagement.student;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class StudentExportService {

    private final Map<String, StudentExporter> studentExporters;

    public StudentExportService(Map<String, StudentExporter> studentExporters) {
        this.studentExporters = studentExporters;
    }

    public void export(HttpServletResponse response, List<Student> students, String type) throws IOException {
        StudentExporter studentExporter;
        String extension;
        switch (type) {
            case "excel":
                studentExporter = studentExporters.get("studentExcelExporter");
                extension = "xlsx";
                break;
            case "pdf":
                studentExporter = studentExporters.get("studentPdfExporter");
                extension = "pdf";
                break;
            default:
                studentExporter = studentExporters.get("studentCsvExporter");
                extension = "csv";
        }
        studentExporter.export(response, students, String.format("Students.%s", extension));
    }
}
