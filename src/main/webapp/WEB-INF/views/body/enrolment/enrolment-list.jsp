<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title>Enrolment Management</title>
</head>
<body>
  <c:if test="${not empty result}">
    <div class="alert alert-${result.status == 'SUCCESS' ? 'success' : 'danger'} ml-auto position-absolute message" role="alert">
        ${result.message}
    </div>
  </c:if>

  <div class="container">
    <div class="card">
      <div class="card-header bg-transparent d-flex justify-content-between align-items-center">
        <h4 class="card-title mb-0">All Enrolments List</h4>
        <c:if test="${isEditable}">
          <a href="${contextPath}/create-enrolment" class="btn btn-primary"><i class="fas fa-user-plus"></i> Add new</a>
        </c:if>
      </div>
      <div class="card-body">
        <table id="enrolment" class="table">
          <thead class="thead-dark">
            <tr class="text-center">
              <th>Id</th>
              <th>Course</th>
              <th>Student</th>
              <th>Semester</th>
              <c:if test="${isEditable}">
                <th>Actions</th>
              </c:if>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="enrolment" items="${enrolments}">
            <tr>
              <td class="text-center">${enrolment.id}</td>
              <td>${enrolment.course.name}</td>
              <td>${enrolment.student.name}</td>
              <td>${enrolment.semester}</td>
              <c:if test="${isEditable}">
                <td class="text-center">
                  <form class="mb-0" action="${contextPath}/update-enrolment" method="post">
                    <input type="hidden" name="id" value="${enrolment.id}" />
                    <button class="btn btn-sm btn-primary"><i class="fas fa-pencil-alt"></i></button>
                    <a href="" class="btn btn-sm btn-danger delete-enrolment-button" data-id="${enrolment.id}"><i class="fas fa-trash"></i></a>
                  </form>
                </td>
              </c:if>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Delete Enrolment Modal -->
    <div class="modal fade" id="delete-enrolment-modal" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Delete Enrolment</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete enrolment permanently?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-danger" id="confirm-delete-enrolment">Delete</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>