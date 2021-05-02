<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${contextPath}">Enrolment Management</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${contextPath}/course">Courses <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${contextPath}/student">Students</a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-user"></i> Welcome, ${username}
                </a>
                <div class="dropdown-menu ml-auto position-absolute profile" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${contextPath}/change-password"><i class="fas fa-pen"></i> Change password</a>
                    <div class="dropdown-divider"></div>
                    <form class="dropdown-item mb-0" action="${contextPath}/logout" method="post">
                        <button class="w-100 border-0 bg-transparent text-left p-0"><i class="fas fa-sign-out-alt"></i> Logout</button>
                    </form>
                </div>
            </li>
        </ul>
    </div>
</nav>
