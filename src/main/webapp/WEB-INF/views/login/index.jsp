<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title>Login</title>
  <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${contextPath}/common.css">
  <link rel="stylesheet" href="${contextPath}/login/index.css">
</head>
<body>
  <c:if test="${not empty result}">
  <div class="alert alert-${result.status == 'SUCCESS' ? 'success' : 'danger'} ml-auto position-absolute message" role="alert">
      ${result.message}
  </div>
</c:if>
  <div class="container">
    <h1 class="text-center mt-4 mb-4">Login</h1>
    <form id="login" method="post" action="process-login">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="username" name="username" class="form-control" aria-describedby="username" id="username">
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
      <button type="submit" class="btn btn-primary">Login</button>
    </form>
  </div>
  <script src="${contextPath}/webjars/jquery/jquery.min.js"></script>
  <script src="${contextPath}/webjars/lodash/lodash.min.js"></script>
  <script src="${contextPath}/common.js"></script>
  <script src="${contextPath}/login/index.js"></script>
</body>
</html>