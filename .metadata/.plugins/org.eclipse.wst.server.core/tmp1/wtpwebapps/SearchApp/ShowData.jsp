<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page  import="java.util.StringTokenizer" %>
<%@   taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Data</title>
</head>
<body>
	<H1>Search Result</H1>
	<tab align=left> To Return To Home Page Click <A HREF="index.jsp">here</A>
	
	
	<%
	 String[][] FinalDisplayData= (String[][]) request.getSession().getAttribute("FinalDisplayData");
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
            	        	String OneLineData = st2.nextElement().toString().trim(); 
            	        	%>
            	        	<H3><%out.print(OneLineData); %></H3>
            	    	<%
            	    	}
            	    	out.println("Click <A HREF=\"" + FinalDisplayData[abc][1].toString().trim()  + "\">here</A>");
            	    }
     	       }
    	   %>
   </tab> 
</body>
</html>