<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>  
 
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
		<title>Project Details</title>
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
				<h1>Project Details</h1>
				<h4>Project: <c:out value="${thisProject.title}"/></h4>
				<h4>Project Lead: <c:out value="${thisProject.lead.firstName}"/> <c:out value="${thisProject.lead.lastName}"/></h4>
				<h4>Description: <c:out value="${thisProject.description}"/></h4>
				<h4>Due Date: <fmt:formatDate pattern ="MMMM dd, yyyy" value ="${thisProject.dueDate}"/></h4>	
				<h4>Team Members:</h4>
					<ul>
					<c:forEach var="user" items="${thisProject.users}">
						<li><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></li>
					</c:forEach>
					</ul>	
			<c:if test="${teamMember!=null}">
				<a href="/project/${thisProject.id}/tasks" class="btn btn-info">See Tasks</a>
			</c:if>
			</div>
		</div>
	</body>
</html>