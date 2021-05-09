<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title>Register</title>
  <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${contextPath}/common.css">
</head>
<body>
  <c:if test="${not empty result}">
    <div class="alert alert-${result.status == 'SUCCESS' ? 'success' : 'danger'} ml-auto position-absolute message" role="alert">
        ${result.message}
    </div>
  </c:if>
  <div class="container register">
    <h1 class="text-center mt-4 mb-4">Register</h1>
    <form:form id="register" method="post" action="${contextPath}/register" modelAttribute="user">
      <div class="form-group">
        <label for="username">Username <span class="text-danger">*</span></label>
        <form:input path="username" class="form-control" aria-describedby="username" id="username"/>
        <div id="username" class="invalid-feedback">
          Username is mandatory
        </div>
      </div>
      <div class="form-group">
        <label for="email">Email <span class="text-danger">*</span></label>
        <form:input type="email" path="email" class="form-control" aria-describedby="email" id="email"/>
        <div id="email" class="invalid-feedback">
          Email is mandatory
        </div>
      </div>
      <div class="form-group">
        <label for="password">Password <span class="text-danger">*</span></label>
        <form:input type="password" path="password" class="form-control" aria-describedby="password" id="password"/>
        <div id="password" class="invalid-feedback">
          Password is mandatory
        </div>
      </div>
      <div class="form-group">
        <label for="confirm-password">Confirm Password <span class="text-danger">*</span></label>
        <form:input type="password" path="confirmPassword" class="form-control" aria-describedby="confirm-password" id="confirm-password"/>
        <div id="confirm-password" class="invalid-feedback mandatory">
          Confirm password is mandatory
        </div>
        <div id="confirm-password" class="invalid-feedback mismatch">
          Confirm password is mismatch
        </div>
      </div>
      <div class="form-group">
        <button type="submit" class="btn btn-primary w-100">Register</button>
      </div>
    </form:form>
    <hr/>
    <a href="${contextPath}/login" class="btn btn-success w-100">Login</a>
  </div>
  <script src="${contextPath}/webjars/jquery/jquery.min.js"></script>
  <script src="${contextPath}/webjars/lodash/lodash.min.js"></script>
  <script src="${contextPath}/common.js"></script>
  <script src="${contextPath}/login/register.js"></script>
</body>
</html>