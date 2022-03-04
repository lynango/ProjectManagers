<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>  
 
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
		<title>Tasks</title>
		<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<script src="/webjars/jquery/jquery.min.js"></script>
		<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body class="p-5 mb-2 bg-secondary text-white">
		<nav class="d-flex justify-content-end col-xs-3">
			<a href="/dashboard" class="btn btn-info">Back</a>
			<a href="/logout" class="btn btn-primary">Logout</a>
		</nav>
		<div class="col-8 p-4 my-5 bg-dark text-light mx-auto">
			<div class="card-body">
				<h1>Project: <c:out value="${thisProject.title}"></c:out></h1>
				<h4>Project lead: <c:out value="${thisProject.lead.firstName }"/> <c:out value="${thisProject.lead.lastName}"/></h4>
				<div class="p-2">
				<h4>Add a task ticket for this team:</h4>
					<form:form action="/project/${thisProject.id}" method="post" modelAttribute="task">
						<div class="form-group">
							<form:label path="description">Description</form:label>
							<form:errors path="description" class="error"/>
							<textarea name="description" id="floatingTextarea" class="form-control"/></textarea>
						</div>
						<form:input type="hidden" path="user" value="${currentUser.id}" />
						<form:input type="hidden" path="project" value="${thisProject.id}" />
					<input type=submit value="Submit" class="btn btn-primary"/>
					</form:form>
				</div>
		<div class="p-5">
		<c:if test="${allTasks.size() == 0}"><h5>There are currently no tasks.</h5></c:if>
		<c:if test="${allTasks.size() > 0}"> </c:if>
		<c:forEach var="item" items="${allTasks}">
			<p style="font-weight:bold;">Added by User: <c:out value="${item.user.firstName}" /> at <c:out value="${item.createdAt}" /> </p>
			<p>Task: <c:out value="${item.description }" /> </p>
		</c:forEach>	
		</div>
		</div>
		</div>
	</body>
</html>