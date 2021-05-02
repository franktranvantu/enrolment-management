<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>${action} Enrolment</title>
</head>
<body>
    <c:if test="${not empty result}">
        <div class="alert alert-danger ml-auto position-absolute message" role="alert">
            ${result.message}
        </div>
    </c:if>

    <div class="container w-25">
        <h1 class="text-center mt-4 mb-4">${action} Enrolment</h1>
        <form:form action="${contextPath}/save-enrolment" method="post" modelAttribute="enrolment" id="save-enrolment">
            <form:hidden path="id" />
            <div class="form-group row">
                <label for="course" class="col-sm-2 col-form-label">Course</label>
                <div class="col-sm-10">
                    <form:select path="course" class="form-control" id="course" aria-describedby="course">
                        <c:forEach var="course" items="${courses}">
                            <option value="${course.id}" ${enrolment.course.id == course.id ? 'selected' : ''}>${course.name}</option>
                        </c:forEach>
                    </form:select>
                    <div id="course" class="invalid-feedback">
                        Course is mandatory
                    </div>
                    <div id="course" class="invalid-feedback">
                        <form:errors path="course" />
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label for="student" class="col-sm-2 col-form-label">Student</label>
                <div class="col-sm-10">
                    <form:select path="student" class="form-control" id="student" aria-describedby="student">
                        <c:forEach var="student" items="${students}">
                            <option value="${student.id}" ${enrolment.student.id == student.id ? 'selected' : ''}>${student.name}</option>
                        </c:forEach>
                    </form:select>
                    <div id="student" class="invalid-feedback">
                        Student is mandatory
                    </div>
                    <div id="student" class="invalid-feedback">
                        <form:errors path="student" />
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label for="semester" class="col-sm-2 col-form-label">Semester</label>
                <div class="col-sm-10">
                    <form:input path="semester" class="form-control" id="semester" aria-describedby="semester"/>
                    <div id="semester" class="invalid-feedback">
                        Semester is mandatory
                    </div>
                    <div id="semester" class="invalid-feedback">
                        <form:errors path="semester" />
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-12 text-right">
                    <button type="button" class="btn btn-secondary" id="back">Back</button>
                    <button type="submit" class="btn btn-primary">${action}</button>
                </div>
            </div>
        </form:form>
    </div>
</body>
</html>
