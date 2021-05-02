<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Course Management</title>
</head>
<body>
  <c:if test="${not empty result}">
    <div class="alert alert-${result.status == 'SUCCESS' ? 'success' : 'danger'} ml-auto position-absolute message"
         role="alert">
        ${result.message}
    </div>
  </c:if>

  <div class="container">
    <div class="card">
      <div class="card-header bg-transparent d-flex justify-content-between align-items-center">
        <h4 class="card-title mb-0">All Courses List</h4>
        <c:if test="${isEditable}">
          <a href="${contextPath}/course/create-course" class="btn btn-primary"><i class="fas fa-user-plus"></i> Add
            new</a>
        </c:if>
      </div>
      <div class="card-body">
        <table id="course" class="table">
          <thead class="thead-dark">
          <tr class="text-center">
            <th>Id</th>
            <th>Name</th>
            <c:if test="${isEditable}">
              <th>Actions</th>
            </c:if>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="course" items="${courses}">
            <tr>
              <td class="text-center">${course.id}</td>
              <td>${course.name}</td>
              <c:if test="${isEditable}">
                <td class="text-center">
                  <form class="mb-0" action="${contextPath}/course/update-course" method="post">
                    <input type="hidden" name="id" value="${course.id}"/>
                    <button class="btn btn-sm btn-primary"><i class="fas fa-pencil-alt"></i></button>
                    <a href="" class="btn btn-sm btn-danger delete-student-button" data-id="${course.id}"><i
                        class="fas fa-trash"></i></a>
                  </form>
                </td>
              </c:if>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Delete Course Modal -->
    <div class="modal fade" id="delete-course-modal" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Delete Course</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete course permanently?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-danger" id="confirm-delete-course">Delete</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
