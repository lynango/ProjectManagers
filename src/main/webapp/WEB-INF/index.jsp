<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ page isErrorPage="true" %> 
 
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>Welcome</title>
		<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<script src="/webjars/jquery/jquery.min.js"></script>
		<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body class="p-5 bg-secondary text-white">
		<div>
			<h1>Welcome!</h1>
		</div>
		<div>
			<h2>Join our growing community.</h2>
		</div>
		<div class="d-flex justify-content-around card-body p-4 p-md-5">
			<form:form method="post" action="/registration" modelAttribute="newUser" class="col-4 p-4 bg-dark text-light">
			<div>
				<h2 class="text-primary text-white">Register</h2>
				<div class="form-group">
					<form:errors path="firstName" class="error"/>
					<form:label path="firstName">First Name:</form:label>
					<form:input type="text" path="firstName" class="form-control"/>
				</div>
				<div class="form-group">
					<form:errors path="lastName" class="error"/>	
					<form:label path="lastName">Last Name:</form:label>
					<form:input type="text" path="lastName" class="form-control"/>
				</div>
				<div class="form-group">
					<form:errors path="email" class="error"/>
					<form:label path="email">Email:</form:label>
					<form:input type="email" path="email" class="form-control"/>
				</div>
				<div class="form-group">
					<form:errors path="password" class="error"/>
					<form:label path="password">Password:</form:label>
					<form:password path="password" class="form-control"/>
				</div>
				<div class="form-group">
					<form:errors path="confirm" class="error"/>
					<form:label path="confirm">Confirm Password:</form:label>
					<form:password path="confirm" class="form-control"/>
				</div>
				<input type="submit" value="Register" class="btn btn-primary"/>
			</div>
			</form:form>
	
			<div class="col-4 p-4 bg-dark text-light">
				<h2 class="text-primary text-white">Login</h2>
				<c:if test="${errorMessage != null}">
        			<c:out value="${errorMessage}"></c:out>
    			</c:if>
				<form action="/login" method="post">
					<h6>Email:<input type="email" name="email" class="form-control"></h6>
					<h6>Password:<input type="password" name="password" class="form-control"></h6>
					<input type="submit" value="Login" class="btn btn-primary">
				</form>
			</div>
		</div>		
	</body>
</html>