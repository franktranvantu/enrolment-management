<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>${action} Course</title>
</head>
<body>
  <c:if test="${not empty result}">
    <div class="alert alert-danger ml-auto position-absolute message" role="alert">
        ${result.message}
    </div>
  </c:if>

  <div class="container w-25">
    <h1 class="text-center mt-4 mb-4">${action} Course</h1>
    <form:form action="${contextPath}/course/save-course" method="post" modelAttribute="course" id="save-course">
      <form:hidden path="id"/>
      <div class="form-group row">
        <label for="name" class="col-sm-2 col-form-label">Name</label>
        <div class="col-sm-10">
          <form:input path="name" class="form-control" id="name" aria-describedby="name"/>
          <div id="name" class="invalid-feedback">
            Name is mandatory
          </div>
          <div id="name" class="invalid-feedback">
            <form:errors path="name"/>
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
