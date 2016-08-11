package PrepareBasicFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class NavigatingServlet
 */
@WebServlet("/NavigatingServlet")
public class NavigatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * Default constructor. 
     * @throws IOException 
     */
    public NavigatingServlet() throws IOException {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ServletContext servletContext = request.getSession().getServletContext();
		String absoluteDiskPath = servletContext.getRealPath(".");
		FilePathData.setAbsoluteDiskPath(absoluteDiskPath.substring(0, absoluteDiskPath.length()).concat("/"));		
		
		String action = request.getParameter("actionButton");
		System.out.println(action);
		if(action.equalsIgnoreCase("Reset And Search"))
		{
			MainNavigatingClass.ResetAllData();
		}
		
		//Parser_Meeli parser_Meeli_obj = new Parser_Meeli();
		//parser_Meeli_obj.parsingMethod();
		
		PrintWriter out = response.getWriter();
		String SearchData =  request.getParameter("SearchBox");
        System.out.println("SearchData is " + SearchData);
		// Calling Search Method and GUI Display -- meeli
		SearchWordsMainMethod SearchWordsMainMethodObject = new SearchWordsMainMethod();

    	String[][] FinalDisplayData = SearchWordsMainMethodObject.searchWordsMainMethod(SearchData);
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("FinalDisplayData", FinalDisplayData);
    	
    	RequestDispatcher rd = null;
    	if(FinalDisplayData==null)
    		rd = request.getRequestDispatcher("DataNotFound.jsp");
    	else
    		rd = request.getRequestDispatcher("ShowData.jsp");
    	
    	rd.forward(request, response);
    	// Calling Search Method and GUI Display End -- meeli
	}

	}


