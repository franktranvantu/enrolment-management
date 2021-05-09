<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title>Forgot Password</title>
  <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${contextPath}/common.css">
</head>
<body>
  <c:if test="${not empty result}">
    <div class="alert alert-${result.status == 'SUCCESS' ? 'success' : 'danger'} ml-auto position-absolute message" role="alert">
        ${result.message}
    </div>
  </c:if>

  <div class="container forgot-password">
    <div class="card border-0">
      <div class="card-header bg-transparent">
        <div class="mt-2 mb-4 d-flex">
          <h4 class="card-title mb-0">Forgot password</h4>
        </div>
      </div>
      <div class="card-body">
        <form action="${contextPath}/forgot-password" method="post" id="forgot-password">
          <div>
            <p>We will be sending a reset password link to your email.</p>
          </div>
          <div class="form-group row">
            <div class="col">
              <input type="email" name="email" class="form-control" id="email" aria-describedby="email"/>
              <div id="email" class="invalid-feedback">
                Current password is mandatory
              </div>
            </div>
          </div>
          <div class="form-group row">
            <div class="col text-right">
              <button type="button" class="btn btn-secondary" id="back">Back</button>
              <button type="submit" class="btn btn-primary">Send</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script src="${contextPath}/webjars/jquery/jquery.min.js"></script>
  <script src="${contextPath}/webjars/lodash/lodash.min.js"></script>
  <script src="${contextPath}/common.js"></script>
  <script src="${contextPath}/login/forgot-password.js"></script>
</body>
</html>