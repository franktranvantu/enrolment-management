<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title>Login</title>
  <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${contextPath}/common.css">
</head>
<body>
  <c:if test="${not empty result}">
    <div class="alert alert-${result.status == 'SUCCESS' ? 'success' : 'danger'} ml-auto position-absolute message" role="alert">
        ${result.message}
    </div>
  </c:if>
  <div class="container login">
    <h1 class="text-center mt-4 mb-4">Login</h1>
    <form:form method="post">
      <div class="form-group">
        <label for="username">Username</label>
        <input name="username" class="form-control" aria-describedby="username" id="username">
        <div id="username" class="invalid-feedback">
          Username is mandatory
        </div>
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" name="password" class="form-control" aria-describedby="password" id="password">
        <div id="password" class="invalid-feedback">
          Password is mandatory
        </div>
      </div>
      <div class="form-group form-check">
        <input type="checkbox" name="remember-me" class="form-check-input" id="remember-me">
        <label class="form-check-label" for="remember-me">Remember me</label>
      </div>
      <div class="form-group">
        <button type="submit" class="btn btn-primary w-100">Login</button>
      </div>
    </form:form>
    <form:form>
      <div class="form-group text-center">
        <a href="${contextPath}/forgot-password">Forgot password?</a>
      </div>
    </form:form>
    <hr/>
    <a href="${contextPath}/register" class="btn btn-success w-100">Register</a>
  </div>
  <script src="${contextPath}/webjars/jquery/jquery.min.js"></script>
  <script src="${contextPath}/webjars/lodash/lodash.min.js"></script>
  <script src="${contextPath}/common.js"></script>
  <script src="${contextPath}/login/index.js"></script>
</body>
</html>