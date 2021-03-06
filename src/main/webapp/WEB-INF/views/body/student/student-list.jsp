<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Student Management</title>
</head>
<body>
  <div class="container">
    <div class="card border-0">
      <div class="card-header bg-transparent">
        <div class="mt-2 mb-4 d-flex">
          <h4 class="card-title mb-0">Search students</h4>
        </div>
        <form action="${contextPath}/student">
          <div class="row">
            <div class="col">
              <div class="form-group">
                <label for="name" class="form-label">Name</label>
                <input type="input" name="name" class="form-control" id="name" value="${student.name}">
              </div>
            </div>
            <div class="col">
              <div class="form-group">
                <label for="email" class="form-label">Email</label>
                <input type="input" name="email" class="form-control" id="email" value="${student.email}">
              </div>
            </div>
            <div class="col">
              <div class="form-group">
                <label for="dobRange" class="form-label">Birthday Range</label>
                <c:set var="dob" value="" />
                <c:if test="${not empty dobRange}">
                  <c:set var="dob"><spring:eval expression="dobRange"/></c:set>
                </c:if>
                <input class="form-control" name="dob" id="dobRange" value="${dob}" placeholder="dd/mm/yyyy - dd/mm/yyyy">
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col d-flex align-items-end justify-content-end">
              <div class="form-group">
                <c:if test="${isEditable}">
                  <div class="btn-group">
                    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      <i class="fas fa-file-export"></i> Export
                    </button>
                    <div class="dropdown-menu">
                      <a class="dropdown-item" href="" id="export-pdf"><i class="fas fa-file-pdf"></i> Export PDF</a>
                      <a class="dropdown-item" href="" id="export-excel"><i class="fas fa-file-excel"></i> Export Excel</a>
                    </div>
                  </div>
                </c:if>
                <button type="submit" class="btn btn-success" id="search"><i class="fas fa-search"></i> Search</button>
              </div>
            </div>
          </div>
        </form>
      </div>
      <div class="card-body">
        <div class="mt-4 mb-4 d-flex justify-content-between align-items-center">
          <h4 class="card-title mb-0">All Students List</h4>
          <c:if test="${isEditable}">
            <div class="form-group mb-0">
              <div class="btn-group">
                <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <i class="fas fa-file-import"></i> Import
                </button>
                <div class="dropdown-menu">
                  <a class="dropdown-item" href="" id="import-pdf"><i class="fas fa-file-pdf"></i> Import PDF</a>
                  <a class="dropdown-item" href="" id="import-excel"><i class="fas fa-file-excel"></i> Import Excel</a>
                </div>
              </div>
              <a href="${contextPath}/admin/student/create-student" class="btn btn-primary"><i class="fas fa-user-plus"></i> Add new</a>
            </div>
          </c:if>
        </div>
        <table id="student" class="table">
          <thead class="thead-dark">
          <tr class="text-center">
            <th>Id</th>
            <th>Name</th>
            <th>Email</th>
            <th>Birthday</th>
            <c:if test="${isEditable}">
              <th>Actions</th>
            </c:if>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="student" items="${students}">
            <tr>
              <td class="text-center">${student.id}</td>
              <td>${student.name}</td>
              <td>${student.email}</td>
              <td class="text-center"><spring:eval expression="student.dob"/></td>
              <c:if test="${isEditable}">
                <td class="text-center">
                  <form class="mb-0" action="${contextPath}/admin/student/update-student">
                    <input type="hidden" name="id" value="${student.id}"/>
                    <button type="submit" class="btn btn-sm btn-primary"><i class="fas fa-pencil-alt"></i></button>
                    <button type="button" class="btn btn-sm btn-danger delete-student-button" data-id="${student.id}"><i class="fas fa-trash"></i></button>
                  </form>
                </td>
              </c:if>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Delete Student Modal -->
    <div class="modal fade" id="delete-student-modal" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Delete Student</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete student permanently?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-danger" id="confirm-delete-student">Delete</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
