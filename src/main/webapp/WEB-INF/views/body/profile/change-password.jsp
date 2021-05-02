<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title>Change Password</title>
  <style>
    .container {
        max-width: 500px;
    }
  </style>
</head>
<body>
  <c:if test="${not empty result}">
    <div class="alert alert-${result.status == 'SUCCESS' ? 'success' : 'danger'} ml-auto position-absolute message" role="alert">
        ${result.message}
    </div>
  </c:if>

  <div class="container">
    <div class="card border-0">
      <div class="card-header bg-transparent">
        <div class="mt-2 mb-4 d-flex">
          <h4 class="card-title mb-0">Change password</h4>
        </div>
      </div>
      <div class="card-body">
        <form action="${contextPath}/change-password" method="post" id="change-password">
          <input type="hidden" name="username" value="${username}">
          <div class="form-group row">
            <label for="password" class="col-5 col-form-label">Current password</label>
            <div class="col">
              <input type="password" name="password" class="form-control" id="password" aria-describedby="password"/>
              <div id="password" class="invalid-feedback">
                Current password is mandatory
              </div>
            </div>
          </div>
          <div class="form-group row">
            <label for="new-password" class="col-5 col-form-label">New password</label>
            <div class="col">
              <input type="password" name="newPassword" class="form-control" id="new-password" aria-describedby="new-password"/>
              <div id="new-password" class="invalid-feedback">
                New password is mandatory
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
</body>
</html>