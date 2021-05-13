<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Internal Server Error</title>
  <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css">
</head>
<body class="bg-dark text-white py-5">
<div class="container py-5">
  <div class="row">
    <div class="col-md-2 text-center">
      <p><i class="fa fa-exclamation-triangle fa-5x"></i><br/>Status Code: 500</p>
    </div>
    <div class="col-md-10">
      <h3>OPPSSS!!!! Sorry...</h3>
      <p>Sorry, There was an internal server error.<br/>Please go back to the previous page to continue browsing.</p>
      <a class="btn btn-danger" href="javascript:history.back()">Go Back</a>
    </div>
  </div>
</div>
</body>
</html>
