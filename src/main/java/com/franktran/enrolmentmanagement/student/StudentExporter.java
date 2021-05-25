package com.franktran.enrolmentmanagement.student;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface StudentExporter {

    void export(HttpServletResponse response, List<Student> students, String fileName) throws IOException;

}
