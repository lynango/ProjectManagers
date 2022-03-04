<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Projects</title>
		<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<script src="/webjars/jquery/jquery.min.js"></script>
		<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body class="p-5 mb-2 bg-secondary text-white">
			<h1 class="text-center my-3">Welcome, <c:out value="${currentUser.firstName}"/> <c:out value="${currentUser.lastName}"/>!</h1>
			<nav class="d-flex justify-content-end col-xs-3">
					<a href="/project/new" class="btn btn-info">Add New Project</a>
				<a href="/logout" class="btn btn-primary">Logout</a>
			</nav>


	<!--  ALL PROJECTS THAT ARE AVAILABLE TO JOIN FOR THIS USER -->
	<h2>Other Projects:</h2>
	<div>
		<table class="table table-striped table-dark">
			<thead class="thead-light">
				<tr>
    				<th>Project</th>
				    <th>Team Lead</th>
				    <th>Due Date</th>
				    <th>Actions</th>
	  			</tr>
			</thead>
			<tbody>
				<c:forEach items="${otherProjects}" var="project">
					<tr>
						<td><a href="/project/${project.id}"><c:out value="${project.title}"/></a></td>
						<td><c:out value="${project.lead.firstName}"/></td>
						<td><fmt:formatDate pattern ="MMMM dd, yyyy" value="${project.dueDate}"/></td>
						<td><form:form action="/project/${project.id}/join" method="post" modelAttribute="project">
								<input type="hidden" name="_method" value="put">
								<form:input type="hidden" path="users" value="${currentID}"/>
								<input type=submit value="Join Team"/>
							</form:form></td>
					</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
	<div>
		<h2>Your Projects:</h2>
		<c:if test="${myProjects.size() == 0}"><h5>You currently have no projects.</h5></c:if>
		<c:if test="${myProjects.size() > 0}"> </c:if>
		<table class="table table-striped table-dark">
			<thead class="thead-light">
				<tr>
    				<th>Project</th>
				    <th>Lead</th>
				    <th>Due Date</th>
				    <th>Actions</th>
		  		</tr>
			</thead>
			<tbody>
				<c:forEach var="project" items="${myProjects }">
					<tr>
						<td><a href="/project/${project.id}"><c:out value="${project.title}"/></a></td>
						<td><c:out value="${project.lead.firstName}"/></td>
						<td><fmt:formatDate pattern ="MMMM dd, yyyy" value="${project.dueDate}"/></td>
						<c:if test="${currentID ==project.lead.id}">
							<td><a href="/project/${project.id}/edit">Edit</a> | <a href="/project/${project.id}/delete">Delete</a></td>
						</c:if>
						<c:if test="${currentID !=project.lead.id }">
							<td><form:form action="/project/${project.id}/leave" method="post" modelAttribute="project">
									<input type="hidden" name="_method" value="put">
									<form:input type="hidden" path="users" value="${currentID}" />
									<input type=submit value="Leave Team" />
								</form:form></td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</body>
</html>