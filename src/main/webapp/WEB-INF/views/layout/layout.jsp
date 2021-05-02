<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPath}/webjars/datatables/css/jquery.dataTables.css">
    <link rel="stylesheet" href="${contextPath}/webjars/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="${contextPath}/webjars/font-awesome/css/all.css">
    <link rel="stylesheet" href="${contextPath}/common.css">
    <c:set var="cssLink"><tiles:getAsString name="cssLink" /></c:set>
    <c:if test="${not empty cssLink}">
        <link rel="stylesheet" href="${contextPath}/${cssLink}">
    </c:if>
</head>
<body>
    <tiles:insertAttribute name="header" />
    <tiles:insertAttribute name="body" />
    <tiles:insertAttribute name="footer" />
    <script src="${contextPath}/webjars/jquery/jquery.min.js"></script>
    <script src="${contextPath}/webjars/lodash/lodash.min.js"></script>
    <script src="${contextPath}/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script src="${contextPath}/webjars/datatables/js/jquery.dataTables.min.js"></script>
    <script src="${contextPath}/webjars/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="${contextPath}/common.js"></script>
    <c:set var="jsLink"><tiles:getAsString name="jsLink" /></c:set>
    <c:if test="${not empty jsLink}">
        <script src="${contextPath}/${jsLink}"></script>
    </c:if>
</body>
</html>
