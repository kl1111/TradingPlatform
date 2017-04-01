<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Shares</title>

<spring:url value="/resources/css/bootstrap.css" var="bootstrap" />
<link href="${bootstrap}" rel="stylesheet" />

<spring:url value="/resources/css/landing-page.css" var="landing" />
<link href="${landing}" rel="stylesheet" />

<spring:url value="/resources/font-awesome/css/font-awesome.css"
	var="awesome" />
<link href="${awesome}" rel="stylesheet" />

<spring:url value="/resources/css/bootstrap.css" var="bootstrap" />
<link href="${bootstrap}" rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic"
	rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top topnav"
		role="navigation">
	<div class="container topnav">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand topnav" href="home">Trebuchet Trading!</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<div class=" pull-left container-fluid">
				<sf:form class="navbar-form" role="search" action="submitSearch"
					method="GET">
					<div class="form-group" style="margin-bottom: 5px">
						<input type="text" class="form-control" placeholder="Search"
							name="searchValue"> <input type="submit"
							style="visibility: hidden; position: absolute;"
							name="submitSearch" />
					</div>
				</sf:form>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${userInf.getUsername() != 'Login'}"><li><a>£<fmt:formatNumber type="number" maxFractionDigits="4" value="${wallet.getMoney()}"/></a></li></c:if>
				<li><a href="login">${userInf.getUsername()}</a></li>
				<li><a href="logout">Logout</a></li>
				<li><a href="register">Register</a></li>
				<li><a href="shares">Shares</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container --> </nav>
	</br>
	</br>
	<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 jumbotron">
		<h2>Current Prices</h2>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Company name</th>
					<th>Current price</th>
					<th>Latest transaction</th>
					<th>Stock exchange</th>
					<c:if test="${userInf.getStatus() == 'Admin' || userInf.getStatus() == 'Broker'}"><th>Quantity</th></c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${prices}" var="price">
					<tr>
						<td><a
							onclick="window.location.href='shareInf?share_id=${price.getShare().getId()}'">${price.getShare().getCompany().getCompany_name()}</a></td>
						<td>${price.getPrice()} ${price.getShare().getCurrency().getSymbol()}</td>
						<td>${price.printTime()}</td>
						<td><a onclick="window.location.href='stockInfo?share_id=${price.getShare().getShare_id()}'"> ${price.getShare().getStockExchange().getName()}</a></td> 
						<c:if test="${userInf.getStatus() == 'Admin' || userInf.getStatus() == 'Broker'}">
						<td><sf:form action="buy?share_id=${price.getShare().getId()}" method="POST" modelAttribute="basket">
									<sf:input path="quantity" id="form_quantity" autocomplete="off" type="text"
										name="quantity" class="form-control"
										placeholder="quantity" required="required"
										data-error="quantity is required." />
									<input type="submit" name="commit" value="submit" /></sf:form></td>
									</c:if>

					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>

	<!-- Footer -->
	<footer>
	<div class="container">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 text-center">
				<ul class="list-inline">
					<li><a href="#">Home</a></li>
					<li class="footer-menu-divider">&sdot;</li>
					<li><a href="#about">About</a></li>
					<li class="footer-menu-divider">&sdot;</li>
					<li><a href="#services">Services</a></li>
					<li class="footer-menu-divider">&sdot;</li>
					<li><a href="#contact">Contact</a></li>
				</ul>
				<p class="copyright text-muted small">Copyright &copy; Your
					Company 2014. All Rights Reserved</p>
			</div>
		</div>
	</div>
	</footer>
	<spring:url value="/resources/js/jquery.js" var="jqueryJs" />
	<script src="${jqueryJs}"></script>

	<spring:url value="/resources/js/bootstrap.js" var="boot" />
	<script src="${boot}"></script>

</body>

</html>
