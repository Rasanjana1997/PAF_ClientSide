<%@ page import="com.paf10.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Products Management</title>
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Products.js"></script>
<link rel="stylesheet" href="views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">


				<h1>Products Management</h1>
				<form method='post' action='Products.jsp' id='formProduct' name='formProduct'>
					Product code: <input id='productCode' name='productCode' type='text' class='form-control col-md-3'><br> 
					Product name: <input id='productName' name='productName' type='text' class='form-control col-md-3'><br> 
					Product price: <input id='productPrice' name='productPrice' type='text' class='form-control col-md-3'><br> 
					Product description: <input id='productDesc' name='productDesc' type='text' class='form-control col-md-3'><br> 
					<input id='btnSave' name='btnSave' type='button' value='Save' class='btn btn-primary'> 
					<input type='hidden' id='hidProductIDSave' name='hidProductIDSave' value=''>
				</form>

				<br>

				<div id='alertSuccess' name='alertSuccess' class='alert alert-success'></div>
				<div id='alertError' name='alertError' class='alert alert-danger'></div>

				<br>
				<div id="divProductsGrid">
				<%
					Product productObjRead = new Product();
						out.print(productObjRead.readProducts());
				%>
				</div>

			</div>
		</div>
	</div>
</body>
</html>