<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SearchApp</title>

</head>
<body>
	<script type="text/javascript" src="app/FunctionsSearch.js"></script>
	<center>
		<H1>Moviepedia</H1>
	</center>
	<form action="NavigatingServlet" method="post">
		Search <input id="SearchBox" name="SearchBox" type="text"
			size="30" /> 
			
			<!-- <INPUT TYPE="submit" id="Submit" VALUE="Search"> -->
			<!-- <button value="Search" name="actionButton" id="actionButton"></button>
			<button value="Reset And Search" name="actionButton" id="actionButton"></button> -->
			
			<INPUT TYPE="submit" id="actionButton" name="actionButton" VALUE="Search">
			<INPUT TYPE="submit" id="actionButton" name="actionButton" VALUE="Reset And Search">
	</form>
</body>
</html>