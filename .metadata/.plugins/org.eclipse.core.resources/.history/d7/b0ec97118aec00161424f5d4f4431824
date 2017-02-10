<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page  import="java.util.StringTokenizer" %>
<%@page import="javax.servlet.http.HttpSession" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data Found</title>
</head>
<body>
	<H1>Search Result</H1>
	<tab align=right> To Return To Home Page Click <A HREF="index.jsp">here</A>
	<%--  <%
	 System.out.println("Reached JSP File");
	String[][] FinalDisplayData= (String[][]) request.getSession().getAttribute("FinalDisplayData");
	System.out.println("From Jsp file :: " +FinalDisplayData.length);
	 for(int abc=0; abc<FinalDisplayData.length ; abc++)
     	       {
        	    	String DataToDisplay = FinalDisplayData[abc][0];
        	    	if(DataToDisplay.equalsIgnoreCase("Data Not Available")) {}
        	    	else
        	    	{
        	    		StringTokenizer st2 = new StringTokenizer(DataToDisplay,";");
        	    		
        	    		 %>
            	    	<H2>Data : </H2>
            	    	<%
            	    	while(st2.hasMoreElements()){
            	        	String OneLineData = st2.nextElement().toString().trim();  %>
            	        	<H3>"+ OneLineData +"</H3>
            	    	<%
            	    	}
            	    	//out.println("<H3>Data : "+ FinalDisplayData[abc][0] +"</H3>");  %>
            	    	Click <A HREF="<%  FinalDisplayData[abc][1].toString().trim();  %>">here</A>
            	    	<%
        	    	}
     	       }
    	   %>
    		
	 --%> 
	
</body>
</html>