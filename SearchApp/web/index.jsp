<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/w3.css">
<link rel="stylesheet" href="css/searchApp.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<title>SearchApp</title>
</head>
<body>
<header class="w3-light-grey w3-container w3-center w3-padding-32"> 
  <h1><b>Moviepedia</b></h1>
</header>

	<div class = "backgroundImg">
	<div class = "Data"> 
		<br><br>
		<form action="NavigatingServlet" method="post">
		<b>
		<input id="SearchBox" name="SearchBox" type="text" size="30" placeholder = "Enter Keyword"/> 
		<INPUT TYPE="submit" id="actionButton" name="actionButton" value="Search" >
		<INPUT TYPE="submit" id="actionButton" name="actionButton" VALUE="Reset And Search"> </b>
		</form>
	
	</div>
	<div class="backgroundImg_transparency" style="height: 850px"></div>
	</div>
	
</body>
</html>