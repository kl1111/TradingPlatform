<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

<title>Share information for ${company.getCompany_name()}</title>

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

<script type="text/javascript">
		window.onload = function () {
			var dps = [];
			var time;
			var price;
			
			<c:forEach items="${sharePrices}" var="sharePrice">
				time = ${sharePrice.getTime()};
				price = ${sharePrice.getPrice()};
				
				dps.push(
							{ x: new Date(time), y: price }
						)
			</c:forEach>
			
			var chart = new CanvasJS.Chart("chartContainer", {
				title: {
					text: "Share Prices",
					fontSize: 24
				},
				animationEnabled: true,
				
				axisX: {
					gridColor: "Silver",
					tickColor: "silver",
					valueFormatString: "MMM YY"
				},
				toolTip: {
					shared: true
				},
				theme: "theme4",
				axisY: {
					gridColor: "Silver",
					tickColor: "silver"
				},
				legend: {
					verticalAlign: "center",
					horizontalAlign: "right"
				},
				
				data: [
				{
					type: "line",
					showInLegend: true,
					lineThickness: 2,
					name: "Prices",
					markerType: "square",
					color: "#F08080",
					dataPoints: dps
				}
				],
				
				legend: {
					cursor: "pointer",
					itemclick: function (e) {
						if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
							e.dataSeries.visible = false;
						}
						else {
							e.dataSeries.visible = true;
						}
						chart.render();
					}
				}
			});

			chart.render();
		}
	</script>
<script src="resources/canvasjs.min.js"></script>
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
				<li><a>£<fmt:formatNumber type="number" maxFractionDigits="4" value="${wallet.getMoney()}"/></a></li>
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
	</br>
	</br>



	<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
		<div class="row">
			<h3>${company.getCompany_name()}</h3>
			<h3>Current Share price: ${prices}
				${share.getCurrency().getSymbol()}</h3>
									<c:if test="${userInf.getStatus() == 'Admin' || userInf.getStatus() == 'Broker'}">
						<td><sf:form action="buy?share_id=${share.getId()}" method="POST" modelAttribute="basket">
									<sf:input path="quantity" autocomplete="off" id="form_quantity" type="text"
										name="quantity" class="form-control"
										placeholder="quantity" required="required"
										data-error="quantity is required." />
									<input type="submit" name="commit" value="Buy Shares" /></sf:form></td>
									</c:if>
			<div class="jumbotron container-fluid text-center">

				<div id="chartContainer" style="height: 400px; width: 80%;"></div>
				<div class="text-center">
					<td><button type="button" class="btn btn-success"
							onclick="window.location.href='shareInf?share_id=${share.getId()}&selection=1'">Future</button></td>
					<td><button type="button" class="btn btn-success"
							onclick="window.location.href='shareInf?share_id=${share.getId()}&selection=2'">Past</button></td>
					<td><button type="button" class="btn btn-success"
							onclick="window.location.href='shareInf?share_id=${share.getId()}'">All</button></td>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer>
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
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
