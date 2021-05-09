<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title>Reset Password</title>
  <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${contextPath}/common.css">
</head>
<body>
  <c:if test="${not empty result}">
    <div class="alert alert-${result.status == 'SUCCESS' ? 'success' : 'danger'} ml-auto position-absolute message" role="alert">
        ${result.message}
    </div>
  </c:if>

  <div class="container reset-password">
    <div class="card border-0">
      <div class="card-header bg-transparent">
        <div class="mt-2 mb-4 d-flex">
          <h4 class="card-title mb-0">Reset your password</h4>
        </div>
      </div>
      <div class="card-body">
        <form action="${contextPath}/reset-password" method="post" id="reset-password">
          <input type="hidden" name="token" value="${token}">
          <div class="form-group row">
            <label for="new-password" class="col-6 col-form-label">New password <span class="text-danger">*</span></label>
            <div class="col">
              <input type="password" name="password" class="form-control" id="new-password" aria-describedby="new-password"/>
              <div id="new-password" class="invalid-feedback">
                New password is mandatory
              </div>
            </div>
          </div>
          <div class="form-group row">
            <label for="confirm-new-password" class="col-6 col-form-label">Confirm new password <span class="text-danger">*</span></label>
            <div class="col">
              <input type="password" name="confirmNewPassword" class="form-control" id="confirm-new-password" aria-describedby="confirm-new-password"/>
              <div id="confirm-new-password" class="invalid-feedback mandatory">
                Confirm new password is mandatory
              </div>
              <div id="confirm-new-password" class="invalid-feedback mismatch">
                Confirm new password is mismatch
              </div>
            </div>
          </div>
          <div class="form-group row">
            <div class="col text-right">
              <button type="button" class="btn btn-secondary" id="back">Back</button>
              <button type="submit" class="btn btn-primary">Change</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script src="${contextPath}/webjars/jquery/jquery.min.js"></script>
  <script src="${contextPath}/webjars/lodash/lodash.min.js"></script>
  <script src="${contextPath}/common.js"></script>
  <script src="${contextPath}/login/reset-password.js"></script>
</body>
</html>